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

package org.eclipse.emf.emfstore.client.ui.controller;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThread;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for removing a server from the workspace.
 * 
 * @author emueller
 * 
 */
public class UIRemoveServerController extends AbstractEMFStoreUIController<Void> {

	private final ServerInfo serverInfo;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent shell that should be used during the delet
	 * @param serverInfo
	 *            the server info that contains the information about which server should be removed from the workspace
	 */
	public UIRemoveServerController(Shell shell, ServerInfo serverInfo) {
		super(shell);
		this.serverInfo = serverInfo;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor monitor) {

		boolean shouldDelete = MessageDialog.openQuestion(getShell(), "Confirm deletion",
			String.format("Are you sure you want to delete the server \'%s\'", serverInfo.getName()));

		if (!shouldDelete) {
			return null;
		}

		EList<ProjectSpace> projectSpaces = WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces();
		ArrayList<ProjectSpace> usedSpaces = new ArrayList<ProjectSpace>();

		for (ProjectSpace projectSpace : projectSpaces) {
			if (projectSpace.getUsersession() != null
				&& projectSpace.getUsersession().getServerInfo().equals(serverInfo)) {
				usedSpaces.add(projectSpace);
			}
		}

		new RunInUIThread(getShell()) {

			@Override
			public Void doRun(Shell shell) {
				WorkspaceManager.getInstance().getCurrentWorkspace().getServerInfos().remove(serverInfo);
				return null;
			}
		}.execute();

		if (usedSpaces.size() == 0) {
			// TODO: add code to add & remove server
			new EMFStoreCommand() {
				@Override
				protected void doRun() {

					EcoreUtil.delete(serverInfo);
					WorkspaceManager.getInstance().getCurrentWorkspace().save();
				};
			}.run(false);

			return null;
		}

		final StringBuilder message = new StringBuilder();

		for (ProjectSpace pSpace : usedSpaces) {
			message.append("\n" + pSpace.getProjectName());
		}

		MessageDialog.openError(getShell(), "Error while deleting", String.format(
			"Cannot delete \'%s\' because it is currently used by the following projects: \n" + message.toString(),
			serverInfo.getName()));

		return null;
	}

}
