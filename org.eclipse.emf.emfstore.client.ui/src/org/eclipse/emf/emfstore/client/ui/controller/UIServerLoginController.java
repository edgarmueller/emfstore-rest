package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.dialogs.login.LoginDialogController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.swt.widgets.Shell;

public class UIServerLoginController extends AbstractEMFStoreUIController {

	private final ServerInfo serverInfo;

	public UIServerLoginController(Shell shell, ServerInfo serverInfo) {
		super(shell);
		this.serverInfo = serverInfo;
	}

	public void openLoginDialog() {
		try {
			LoginDialogController loginDialogController = new LoginDialogController();
			loginDialogController.login(serverInfo);
		} catch (EmfStoreException e) {
			e.printStackTrace();
		}
	}

}
