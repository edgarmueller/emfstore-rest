/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThreadWithResult;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for deleting a project on the server.
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
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor progressMonitor) {

		try {

			if (projectInfo != null) {
				deleteRemoteProject(projectInfo, progressMonitor);
				return null;
			} else if (session != null) {
				deleteRemoteProject(session, projectId, deleteFiles);
				return null;
			}

			deleteRemoteProject(serverInfo, projectId, deleteFiles, progressMonitor);
		} catch (EmfStoreException e) {
			MessageDialog.openError(getShell(), "Delete project failed.",
				"Deletion of project " + projectInfo.getName() + " failed: " + e.getMessage());
		}

		return null;
	}

	private void deleteRemoteProject(final ProjectInfo projectInfo, IProgressMonitor monitor) throws EmfStoreException {
		Boolean[] ret = new RunInUIThreadWithResult<Boolean[]>(getShell()) {

			@Override
			public Boolean[] doRun(Shell shell) {
				MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(getShell(), "Delete "
					+ projectInfo.getName(),
					String.format("Are you sure you want to delete \'%s\'", projectInfo.getName()),
					"Delete project contents (cannot be undone)", false, null, null);
				return new Boolean[] { dialog.getReturnCode() == MessageDialog.OK, dialog.getToggleState() };
			}
		}.execute();

		boolean shouldDelete = ret[0];
		boolean deleteFiles = ret[1];

		if (!shouldDelete) {
			return;
		}

		if (!(projectInfo.eContainer() instanceof ServerInfo)) {
			throw new EmfStoreException("ServerInfo couldn't be determined for the given project.");
		}

		ServerInfo serverInfo = (ServerInfo) projectInfo.eContainer();
		WorkspaceManager.getInstance().getCurrentWorkspace()
			.deleteRemoteProject(serverInfo, projectInfo.getProjectId(), deleteFiles);
	}

	private void deleteRemoteProject(Usersession session, ProjectId projectId, boolean deleteFiles)
		throws EmfStoreException {
		if (confirm("Confirmation", "Do you really want to delete the remote project?")) {
			WorkspaceManager.getInstance().getCurrentWorkspace().deleteRemoteProject(session, projectId, deleteFiles);
		}
	}

	private void deleteRemoteProject(final ServerInfo serverInfo, final ProjectId projectId, final boolean deleteFiles,
		IProgressMonitor monitor) throws EmfStoreException {
		if (confirm("Confirmation", "Do you really want to delete the remote project?")) {
			WorkspaceManager.getInstance().getCurrentWorkspace()
				.deleteRemoteProject(serverInfo, projectId, deleteFiles);
		}
	}
}
