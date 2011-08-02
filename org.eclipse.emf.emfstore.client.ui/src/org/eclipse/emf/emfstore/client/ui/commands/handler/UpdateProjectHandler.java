package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;

public class UpdateProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public Object doExecute() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {

				/**
				 * TODO ASYNC
				 * OLD STYLE CODE - REPLACE THIS
				 */
				ProjectSpace projectSpace = getProjectSpace();
				if (projectSpace == null) {
					ProjectSpace activeProjectSpace = WorkspaceManager.getInstance().getCurrentWorkspace()
						.getActiveProjectSpace();
					if (activeProjectSpace == null) {
						MessageDialog.openInformation(getShell(), "Information", "You must select the Project");
						return;
					}
					projectSpace = activeProjectSpace;
				}
				Usersession usersession = projectSpace.getUsersession();
				if (usersession == null) {
					MessageDialog.openInformation(getShell(), null,
						"This project is not yet shared with a server, you cannot update.");
					return;
				}
				try {
					usersession.logIn();
				} catch (AccessControlException e) {
					e.printStackTrace();
				} catch (EmfStoreException e) {
					e.printStackTrace();
				}
				/**
				 * END OLD STYLE
				 */

				new UpdateProjectUIController(getShell()).update(projectSpace, null);

			}
		}.run(true);
		return null;
	}

}
