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
package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Handler for updating a project.
 * 
 * @author wesendon
 */
public class UpdateProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public Object doExecute() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				/** TODO ASYNC, OLD STYLE CODE - REPLACE THIS */
				ProjectSpace projectSpace = getProjectSpace();
				if (projectSpace == null) {
					projectSpace = WorkspaceManager.getInstance().getCurrentWorkspace().getActiveProjectSpace();
					if (projectSpace == null) {
						MessageDialog.openInformation(getShell(), "Information", "You must select the Project");
						return;
					}
				}
				try {
					projectSpace.getUsersession().logIn();
				} catch (AccessControlException e) {
					e.printStackTrace();
				} catch (EmfStoreException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					MessageDialog.openInformation(getShell(), null,
						"This project is not yet shared with a server, you cannot update.");
					return;
				}
				/** END OLD STYLE */
				new UpdateProjectUIController(getShell()).update(projectSpace, null);

			}
		}.run(true);
		return null;
	}

}
