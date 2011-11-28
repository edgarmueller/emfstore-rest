package org.eclipse.emf.emfstore.client.model.controller;

import java.util.Date;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.GenericCallback;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.client.model.observers.ShareObserver;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;

public class ShareController extends ServerCall<GenericCallback> {

	public ShareController(ProjectSpaceImpl projectSpaceImpl, Usersession session, GenericCallback callback,
		IProgressMonitor monitor) {
		super(projectSpaceImpl);

		// if session is null, session will be injected by sessionmanager
		setUsersession(session);

		setCallback((callback != null) ? callback : GenericCallback.NOCALLBACK);

		setProgressMonitor(monitor);
	}

	@Override
	protected void run() throws EmfStoreException {
		doRun();
	}

	private void doRun() throws EmfStoreException {

		getProgressMonitor().beginTask("Sharing Project", 100);
		getProgressMonitor().worked(1);
		getProgressMonitor().subTask("Preparing project for sharing");

		LogMessage logMessage = VersioningFactory.eINSTANCE.createLogMessage();
		logMessage.setAuthor(getUsersession().getUsername());
		logMessage.setClientDate(new Date());
		logMessage.setMessage("Initial commit");
		ProjectInfo createdProject;

		getProjectSpace().stopChangeRecording();
		getProjectSpace().getStatePersister().setAutoSave(false);

		getProgressMonitor().worked(10);
		if (getProgressMonitor().isCanceled()) {
			getProjectSpace().getStatePersister().setAutoSave(true);
			getProjectSpace().getStatePersister().saveDirtyResources();
			getProjectSpace().startChangeRecording();
			getProgressMonitor().done();
			shareCompleted(getProjectSpace(), true);
		}
		getProgressMonitor().subTask("Sharing project with server");

		createdProject = WorkspaceManager
			.getInstance()
			.getConnectionManager()
			.createProject(getUsersession().getSessionId(), getProjectSpace().getProjectName(),
				getProjectSpace().getProjectDescription(), logMessage, getProjectSpace().getProject());

		getProgressMonitor().worked(70);
		getProgressMonitor().subTask("Finalizing share");

		// set attributes after server call
		this.setUsersession(getUsersession());
		getUsersession().addLoginObserver(getProjectSpace());

		getProjectSpace().getStatePersister().setAutoSave(true);
		getProjectSpace().getStatePersister().saveDirtyResources();
		getProjectSpace().startChangeRecording();
		getProjectSpace().setBaseVersion(createdProject.getVersion());
		getProjectSpace().setLastUpdated(new Date());
		getProjectSpace().setProjectId(createdProject.getProjectId());
		getProjectSpace().saveProjectSpaceOnly();

		// TODO ASYNC implement File Upload with observer
		// If any files have already been added, upload them.
		// fileTransferManager.uploadQueuedFiles(new NullProgressMonitor());

		getProjectSpace().getOperations().clear();
		getUsersession().updateProjectInfos();
		getProjectSpace().updateDirtyState();

		getProgressMonitor().done();
		shareCompleted(getProjectSpace(), false);
		WorkspaceManager.getObserverBus().notify(ShareObserver.class).shareDone(getProjectSpace());
	}

	private void shareCompleted(ProjectSpaceImpl projectSpace, boolean canceled) {
		HashMap<Object, Object> values = new HashMap<Object, Object>();
		values.put(ProjectSpace.class, projectSpace);
		getCallBack().callCompleted(values, !canceled);
	}
}
