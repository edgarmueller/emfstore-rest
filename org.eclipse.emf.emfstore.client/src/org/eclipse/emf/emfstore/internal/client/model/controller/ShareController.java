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

import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.observer.ESLoginObserver;
import org.eclipse.emf.emfstore.client.model.observer.ESShareObserver;
import org.eclipse.emf.emfstore.internal.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;

/**
 * Shares a project.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class ShareController extends ServerCall<Void> {

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the project space to be shared
	 * @param session
	 *            the session to use during share
	 * @param monitor
	 *            a progress monitor that is used to indicate the progress of the share
	 */
	public ShareController(ProjectSpaceBase projectSpace, Usersession session, IProgressMonitor monitor) {
		super(projectSpace);

		// if session is null, session will be injected by sessionmanager
		setUsersession(session);
		setProgressMonitor(monitor);
	}

	@Override
	protected Void run() throws EMFStoreException {
		doRun();
		return null;
	}

	@SuppressWarnings("unchecked")
	private void doRun() throws EMFStoreException {

		getProgressMonitor().beginTask("Sharing Project", 100);
		getProgressMonitor().worked(1);
		getProgressMonitor().subTask("Preparing project for sharing");

		final LogMessage logMessage = VersioningFactory.eINSTANCE.createLogMessage();
		logMessage.setAuthor(getUsersession().getUsername());
		logMessage.setClientDate(new Date());
		logMessage.setMessage("Initial commit");
		ProjectInfo createdProject = null;

		getLocalProject().stopChangeRecording();

		getProgressMonitor().worked(10);
		if (getProgressMonitor().isCanceled()) {
			getLocalProject().save();
			getLocalProject().startChangeRecording();
			getProgressMonitor().done();
		}
		getProgressMonitor().subTask("Sharing project with server");

		// make sure, current state of caches is written to resource
		getLocalProject().save();

		createdProject = new UnknownEMFStoreWorkloadCommand<ProjectInfo>(getProgressMonitor()) {
			@Override
			public ProjectInfo run(IProgressMonitor monitor) throws EMFStoreException {
				return WorkspaceProvider
					.getInstance()
					.getConnectionManager()
					.createProject(
						getUsersession().getSessionId(),
						getLocalProject().getProjectName() == null ? "Project@" + new Date() : getLocalProject()
							.getProjectName(),
						"", logMessage, getLocalProject().getProject());
			}
		}.execute();

		getProgressMonitor().worked(30);
		getProgressMonitor().subTask("Finalizing share");

		// set attributes after server call
		getProgressMonitor().subTask("Setting attributes");
		this.setUsersession(getUsersession());
		WorkspaceProvider.getObserverBus().register(getLocalProject(), ESLoginObserver.class);

		getLocalProject().save();
		getLocalProject().startChangeRecording();
		getLocalProject().setBaseVersion(createdProject.getVersion());
		getLocalProject().setLastUpdated(new Date());
		getLocalProject().setProjectId(createdProject.getProjectId());
		getLocalProject().setUsersession(getUsersession());
		getLocalProject().saveProjectSpaceOnly();

		// TODO ASYNC implement File Upload with observer
		// If any files have already been added, upload them.
		getProgressMonitor().worked(20);
		getProgressMonitor().subTask("Uploading files");
		getLocalProject().getFileTransferManager().uploadQueuedFiles(getProgressMonitor());

		getProgressMonitor().worked(20);
		getProgressMonitor().subTask("Finalizing share.");
		getLocalProject().getOperations().clear();
		getLocalProject().updateDirtyState();

		getProgressMonitor().done();
		WorkspaceProvider.getObserverBus().notify(ESShareObserver.class).shareDone(getLocalProject());
	}
}