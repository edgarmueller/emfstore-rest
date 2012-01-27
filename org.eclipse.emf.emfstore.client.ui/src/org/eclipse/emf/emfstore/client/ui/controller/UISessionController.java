package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.dialogs.login.LoginDialogController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.swt.widgets.Shell;

public class UISessionController extends AbstractEMFStoreUIController {

	public UISessionController(Shell shell) {
		super(shell);
	}

	public void login(ServerInfo serverInfo) throws EmfStoreException {
		try {
			LoginDialogController loginDialogController = new LoginDialogController();
			loginDialogController.login(serverInfo);
		} catch (AccessControlException e) {
			// don't show user that login failed, duh.
		}
	}

	public void logout(Usersession session) throws EmfStoreException {

		if (session == null) {
			return;
		}

		session.logout();

		// reset the password in the RAM cache
		if (!session.isSavePassword()) {
			session.setPassword(null);
		}

		WorkspaceManager.getInstance().getCurrentWorkspace().save();
	}
}
