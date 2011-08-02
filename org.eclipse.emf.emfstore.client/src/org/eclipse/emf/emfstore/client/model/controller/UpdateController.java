package org.eclipse.emf.emfstore.client.model.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.exceptions.NoChangesOnServerException;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.client.model.observers.UpdateObserver;
import org.eclipse.emf.emfstore.server.conflictDetection.ConflictDetector;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;

public class UpdateController extends ServerCall {

	public UpdateController(ProjectSpaceImpl projectSpace) {
		super(projectSpace);
	}

	public void update(VersionSpec version, UpdateCallback callback, IProgressMonitor progress) {
		try {

			/**
			 * SANITY CHECKS
			 */
			if (version == null) {
				version = VersionSpec.HEAD_VERSION;
			}
			if (callback == null) {
				callback = UpdateCallback.NOCALLBACK;
			}
			if (progress == null) {
				progress = new NullProgressMonitor();
			}

			progress.beginTask("Updating Project", 100);

			doUpdate(version, callback, progress);
		} catch (EmfStoreException e) {
			callback.handleException(e);
		}
	}

	private void doUpdate(VersionSpec version, UpdateCallback callback, IProgressMonitor progress)
		throws EmfStoreException {
		progress.subTask("Resolving new version");
		progress.worked(1);
		final PrimaryVersionSpec resolvedVersion = getProjectSpace().resolveVersionSpec(version);

		if (resolvedVersion.compareTo(getProjectSpace().getBaseVersion()) == 0) {
			callback.handleException(new NoChangesOnServerException());
			return;
		}
		progress.worked(5);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}

		progress.subTask("Fetching changes from server");
		List<ChangePackage> changes = new ArrayList<ChangePackage>();
		changes = getConnectionManager().getChanges(getSessionId(), getProjectSpace().getProjectId(),
			getProjectSpace().getBaseVersion(), resolvedVersion);
		ChangePackage localchanges = getProjectSpace().getLocalChangePackage(false);
		progress.worked(65);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}

		progress.subTask("Checking for conflicts");
		ConflictDetector conflictDetector = new ConflictDetector();
		for (ChangePackage change : changes) {
			if (conflictDetector.doConflict(change, localchanges)) {
				callback.handleException(new ChangeConflictException(changes, getProjectSpace(), conflictDetector));
				return;
			}
		}
		progress.worked(15);

		if (callback.inspectChanges(getProjectSpace(), changes)) {
			updateDone(callback, progress, getProjectSpace().getBaseVersion(), null);
		}

		WorkspaceManager.getObserverBus().notify(UpdateObserver.class).inspectChanges(getProjectSpace(), changes);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}

		progress.subTask("Applying changes");
		final List<ChangePackage> cps = changes;
		// revert
		getProjectSpace().revert();
		// apply changes from repo
		for (ChangePackage change : cps) {
			getProjectSpace().applyOperations(change.getCopyOfOperations(), false);
		}
		// reapply local changes
		getProjectSpace().applyOperations(localchanges.getCopyOfOperations(), true);

		PrimaryVersionSpec oldVersion = getProjectSpace().getBaseVersion();

		getProjectSpace().setBaseVersion(resolvedVersion);
		getProjectSpace().saveProjectSpaceOnly();

		// create notifications only if the project is updated to a newer
		// version
		if (resolvedVersion.compareTo(getProjectSpace().getBaseVersion()) == 1) {
			// TODO ASYNC Make update listener
			// projectSpace.generateNotifications(changes);
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}

		WorkspaceManager.getObserverBus().notify(UpdateObserver.class).updateCompleted(getProjectSpace());
		updateDone(callback, progress, oldVersion, getProjectSpace().getBaseVersion());

		// check for operations on file attachments: if version has been
		// increased and file is required offline, add to
		// pending file transfers
		// checkUpdatedFileAttachments(changes);
	}

	private void updateDone(UpdateCallback callback, IProgressMonitor progress, PrimaryVersionSpec oldVersion,
		PrimaryVersionSpec newVersion) {
		callback.updateCompleted(getProjectSpace(), oldVersion, (newVersion == null) ? oldVersion : newVersion);
		progress.done();
	}
}
