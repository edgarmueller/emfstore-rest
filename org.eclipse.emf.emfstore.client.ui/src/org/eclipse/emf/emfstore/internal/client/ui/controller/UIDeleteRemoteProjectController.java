/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.impl.RemoteProject;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for deleting a project on the server.
 * 
 * TODO REVIEW THIS PIECE OF SHIT
 * 
 * @author emueller
 * 
 */
public class UIDeleteRemoteProjectController extends AbstractEMFStoreUIController<Void> {

	private final ServerInfo serverInfo;
	private final ProjectId projectId;
	private final boolean deleteFiles;
	private final Usersession session;
	private final ProjectInfo projectInfo;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that should be used during the deletion of the project
	 * @param serverInfo
	 *            the server info containing the information about the server the project is hosted on
	 * @param projectId
	 *            the {@link ProjectId} of the project to be deleted
	 * @param deleteFiles
	 *            whether to delete the physical files on the server
	 */
	public UIDeleteRemoteProjectController(Shell shell, ServerInfo serverInfo, ProjectId projectId, boolean deleteFiles) {
		super(shell);
		this.serverInfo = serverInfo;
		this.projectId = projectId;
		this.deleteFiles = deleteFiles;
		projectInfo = null;
		session = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that should be used during the deletion of the project
	 * @param session
	 *            the {@link Usersession} that will be used to determine the server where the project
	 *            to be deleted is located on
	 * @param projectId
	 *            the {@link ProjectId} of the project to be deleted
	 * @param deleteFiles
	 *            whether to delete the physical files on the server
	 */
	public UIDeleteRemoteProjectController(Shell shell, Usersession session, ProjectId projectId, boolean deleteFiles) {
		super(shell);
		this.session = session;
		this.projectId = projectId;
		this.deleteFiles = deleteFiles;
		serverInfo = null;
		projectInfo = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that should be used during the deletion of the project
	 * @param projectInfo
	 *            the {@link ProjectInfo} containing the information about the project to be deleted
	 */
	public UIDeleteRemoteProjectController(Shell shell, ProjectInfo projectInfo) {
		super(shell, true, false);
		this.projectInfo = projectInfo;
		session = null;
		serverInfo = null;
		projectId = null;
		deleteFiles = false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor progressMonitor) throws EMFStoreException {

		try {

			if (projectInfo != null) {
				deleteRemoteProject(projectInfo, progressMonitor);
				return null;
			} else if (session != null) {
				deleteRemoteProject(session, projectId, deleteFiles);
				return null;
			}

			deleteRemoteProject(serverInfo, projectId, deleteFiles, progressMonitor);
		} catch (EMFStoreException e) {
			MessageDialog.openError(getShell(), "Delete project failed.",
				"Deletion of project " + projectInfo.getName() + " failed: " + e.getMessage());
		}

		return null;
	}

	private void deleteRemoteProject(final ProjectInfo projectInfo, IProgressMonitor monitor) throws EMFStoreException {

		Boolean[] ret = RunInUI.runWithResult(new Callable<Boolean[]>() {
			public Boolean[] call() throws Exception {
				MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(getShell(), "Delete "
					+ projectInfo.getName(),
					String.format("Are you sure you want to delete \'%s\'", projectInfo.getName()),
					"Delete project contents (cannot be undone)", false, null, null);
				return new Boolean[] { dialog.getReturnCode() == MessageDialog.OK, dialog.getToggleState() };
			}
		});

		boolean shouldDelete = ret[0];
		boolean deleteFiles = ret[1];

		if (!shouldDelete) {
			return;
		}

		if (!(projectInfo.eContainer() instanceof ServerInfo)) {
			throw new EMFStoreException("ServerInfo couldn't be determined for the given project.");
		}

		ServerInfo serverInfo = (ServerInfo) projectInfo.eContainer();
		// TODO: OTS casts
		new RemoteProject(serverInfo, projectInfo).delete(monitor);
	}

	private void deleteRemoteProject(Usersession session, ProjectId projectId, boolean deleteFiles)
		throws EMFStoreException {
		if (confirm("Confirmation", "Do you really want to delete the remote project?")) {
			// TODO: OTS casts + monitor
			new RemoteProject(serverInfo, projectInfo).delete(session, new NullProgressMonitor());
		}
	}

	private void deleteRemoteProject(final ServerInfo serverInfo, final ProjectId projectId, final boolean deleteFiles,
		IProgressMonitor monitor) throws EMFStoreException {
		if (confirm("Confirmation", "Do you really want to delete the remote project?")) {
			// TODO: OTS casts
			new RemoteProject(serverInfo, projectInfo).delete(monitor);
		}
	}
}