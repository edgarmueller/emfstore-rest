package org.eclipse.emf.emfstore.client.model.controller;

import java.util.Date;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

public class CommitController extends ServerCall<CommitCallback> {

	private LogMessage logMessage;

	public CommitController(ProjectSpaceImpl projectSpace, LogMessage logMessage, CommitCallback callback,
		IProgressMonitor monitor) {
		super(projectSpace);
		this.logMessage = (logMessage == null) ? createLogMessage() : logMessage;
		setCallback(callback == null ? CommitCallback.NOCALLBACK : callback);
		setProgressMonitor(monitor);
	}

	@Override
	protected void run() throws EmfStoreException {
		commit(this.logMessage);
	}

	private void commit(LogMessage logMessage) throws EmfStoreException {
		getProgressMonitor().beginTask("Commiting Changes", 100);
		getProgressMonitor().worked(1);

		getProgressMonitor().subTask("Checking changes");
		// check if there are any changes
		if (!getProjectSpace().isDirty()) {
			getCallBack().noLocalChanges(getProjectSpace());
			commitFinished(null);
		}
		getProjectSpace().cleanCutElements();

		getProgressMonitor().subTask("Resolving new version");

		// check if we need to update first
		PrimaryVersionSpec resolvedVersion = getProjectSpace().resolveVersionSpec(VersionSpec.HEAD_VERSION);
		while (!getProjectSpace().getBaseVersion().equals(resolvedVersion)) {
			if (getCallBack().baseVersionOutOfDate(getProjectSpace()) || getProgressMonitor().isCanceled()) {
				commitFinished(null);
			}
			resolvedVersion = getProjectSpace().resolveVersionSpec(VersionSpec.HEAD_VERSION);
		}

		getProgressMonitor().worked(10);
		getProgressMonitor().subTask("Gathering changes");
		ChangePackage changePackage = getProjectSpace().getLocalChangePackage(true);
		if (changePackage.getOperations().isEmpty()) {
			for (AbstractOperation operation : getProjectSpace().getOperations()) {
				getProjectSpace().getOperationManager().notifyOperationUndone(operation);
			}
			getProjectSpace().getOperations().clear();
			getProjectSpace().updateDirtyState();
			// finally, no local changes
			getCallBack().noLocalChanges(getProjectSpace());
			commitFinished(null);
		}

		// TODO remove and replace by observerbus
		getProjectSpace().notifyPreCommitObservers(changePackage);

		// if (commitObserver != null && !commitObserver.inspectChanges(this, changePackage)) {
		// throw new CommitCanceledException("Changes have been canceld by the user.");
		// }

		getProgressMonitor().subTask("Presenting Changes");
		if (getCallBack().inspectChanges(getProjectSpace(), changePackage) || getProgressMonitor().isCanceled()) {
			commitFinished(null);
		}

		getProgressMonitor().subTask("Sending changes to server");
		PrimaryVersionSpec newBaseVersion = getConnectionManager().createVersion(getUsersession().getSessionId(),
			getProjectSpace().getProjectId(), getProjectSpace().getBaseVersion(), changePackage, logMessage);

		getProgressMonitor().worked(75);
		getProgressMonitor().subTask("Finalizing commit");
		getProjectSpace().setBaseVersion(newBaseVersion);
		getProjectSpace().getOperations().clear();
		getProjectSpace().getEventsFromComposite().clear();

		getProjectSpace().saveProjectSpaceOnly();

		// TODO reimplement with ObserverBus and think about subtasks for commit
		// fileTransferManager.uploadQueuedFiles(new NullProgressMonitor());
		// notifyPostCommitObservers(newBaseVersion);

		getProjectSpace().updateDirtyState();
		commitFinished(newBaseVersion);
	}

	private void commitFinished(PrimaryVersionSpec newBaseVersion) {
		if (newBaseVersion != null) {
			HashMap<Object, Object> results = new HashMap<Object, Object>();
			results.put(CommitCallback.NEWVERSION, newBaseVersion);
			getCallBack().callCompleted(results, true);
		} else {
			getCallBack().callCompleted(null, false);
		}
	}

	private LogMessage createLogMessage() {
		LogMessage logMessage = VersioningFactory.eINSTANCE.createLogMessage();
		String commiter = "UNKOWN";
		if (getProjectSpace().getUsersession() != null && getProjectSpace().getUsersession().getACUser() != null
			&& getProjectSpace().getUsersession().getACUser().getName() != null) {
			commiter = getProjectSpace().getUsersession().getACUser().getName();
		}
		logMessage.setAuthor(commiter);
		logMessage.setClientDate(new Date());
		logMessage.setMessage("");
		return logMessage;
	}

}
