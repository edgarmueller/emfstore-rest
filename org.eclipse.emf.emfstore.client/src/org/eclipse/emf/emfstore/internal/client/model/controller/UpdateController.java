/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.observers.UpdateObserver;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.BasicModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucketCandidate;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictDetector;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.model.api.IChangePackage;

/**
 * Controller class for updating a project space.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class UpdateController extends ServerCall<PrimaryVersionSpec> {

	private VersionSpec version;
	private IUpdateCallback callback;

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the project space to be updated
	 * @param version
	 *            the target version
	 * @param callback
	 *            an optional update callback instance
	 * @param progress
	 *            a progress monitor that is used to indicate the progress of the update
	 */
	public UpdateController(ProjectSpaceBase projectSpace, VersionSpec version, IUpdateCallback callback,
		IProgressMonitor progress) {
		super(projectSpace);

		// SANITY CHECKS
		if (version == null) {
			version = Versions.createHEAD(projectSpace.getBaseVersion());
		}
		if (callback == null) {
			callback = IUpdateCallback.NOCALLBACK;
		}

		this.version = version;
		this.callback = callback;
		setProgressMonitor(progress);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall#run()
	 */
	@Override
	protected PrimaryVersionSpec run() throws EMFStoreException {
		return doUpdate(version);
	}

	private PrimaryVersionSpec doUpdate(VersionSpec version) throws ChangeConflictException, EMFStoreException {
		getProgressMonitor().beginTask("Updating Project...", 100);
		getProgressMonitor().worked(1);
		getProgressMonitor().subTask("Resolving new version");
		final PrimaryVersionSpec resolvedVersion = getLocalProject().resolveVersionSpec(version);
		if (resolvedVersion.compareTo(getLocalProject().getBaseVersion()) == 0) {
			return resolvedVersion;
		}
		getProgressMonitor().worked(5);

		if (getProgressMonitor().isCanceled()) {
			return getLocalProject().getBaseVersion();
		}

		getProgressMonitor().subTask("Fetching changes from server");
		List<ChangePackage> changes = new UnknownEMFStoreWorkloadCommand<List<ChangePackage>>(getProgressMonitor()) {
			@Override
			public List<ChangePackage> run(IProgressMonitor monitor) throws EMFStoreException {
				return getConnectionManager().getChanges(getSessionId(), getLocalProject().getProjectId(),
					getLocalProject().getBaseVersion(), resolvedVersion);
			}
		}.execute();

		ChangePackage localChanges = getLocalProject().getLocalChangePackage(false);

		// build a mapping including deleted and create model elements in local and incoming change packages
		BasicModelElementIdToEObjectMapping idToEObjectMapping = new BasicModelElementIdToEObjectMapping(
			getLocalProject().getProject(), changes);
		idToEObjectMapping.put(localChanges);

		getProgressMonitor().worked(65);

		if (getProgressMonitor().isCanceled()) {
			return getLocalProject().getBaseVersion();
		}

		getProgressMonitor().subTask("Checking for conflicts");

		ConflictDetector conflictDetector = new ConflictDetector();

		// TODO ASYNC review this cancel
		if (getProgressMonitor().isCanceled()
			|| !callback.inspectChanges(getLocalProject(), changes, idToEObjectMapping)) {
			return getLocalProject().getBaseVersion();
		}
		WorkspaceProvider.getObserverBus().notify(UpdateObserver.class)
			.inspectChanges(getLocalProject(), (List<IChangePackage>) (List<?>) changes, getProgressMonitor());

		boolean potentialConflictsDetected = false;
		if (getLocalProject().getOperations().size() > 0) {
			Set<ConflictBucketCandidate> conflictBucketCandidates = conflictDetector.calculateConflictCandidateBuckets(
				Collections.singletonList(localChanges), changes);
			potentialConflictsDetected = conflictDetector.containsConflictingBuckets(conflictBucketCandidates);
			if (potentialConflictsDetected) {
				getProgressMonitor().subTask("Conflicts detected, calculating conflicts");
				ChangeConflictException conflictException = new ChangeConflictException(getLocalProject(),
					Arrays.asList(localChanges), changes, conflictBucketCandidates, idToEObjectMapping);
				if (callback.conflictOccurred(conflictException, getProgressMonitor())) {
					return getLocalProject().getBaseVersion();
				} else {
					throw conflictException;
				}
			}
		}

		getProgressMonitor().worked(15);

		getProgressMonitor().subTask("Applying changes");

		getLocalProject().applyChanges(resolvedVersion, changes, localChanges, callback, getProgressMonitor());

		WorkspaceProvider.getObserverBus().notify(UpdateObserver.class)
			.updateCompleted(getLocalProject(), getProgressMonitor());

		return getLocalProject().getBaseVersion();
	}

}