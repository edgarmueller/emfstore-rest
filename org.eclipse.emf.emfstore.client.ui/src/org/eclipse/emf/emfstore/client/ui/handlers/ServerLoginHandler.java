package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UIServerLoginController;

public class ServerLoginHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		ServerInfo serverInfo = requireSelection(ServerInfo.class);
		new UIServerLoginController(getShell(), serverInfo).openLoginDialog();
	}

}
