package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIServerLoginController;

public class ServerLoginHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIServerLoginController(getShell(), requireSelection(ServerInfo.class)).openLoginDialog();
	}

}
