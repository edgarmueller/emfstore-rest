package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.dialogs.login.LoginDialog;
import org.eclipse.swt.widgets.Shell;

public class UIServerLoginController extends AbstractEMFStoreUIController {

	private final ServerInfo serverInfo;

	public UIServerLoginController(Shell shell, ServerInfo serverInfo) {
		super(shell);
		this.serverInfo = serverInfo;
	}

	public void openLoginDialog() {
		LoginDialog loginDialog = new LoginDialog(shell, serverInfo);
		loginDialog.open(false);
	}

}
