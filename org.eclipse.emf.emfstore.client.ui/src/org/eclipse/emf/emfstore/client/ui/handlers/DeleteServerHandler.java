package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UIDeleteServerController;

public class DeleteServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIDeleteServerController(getShell(), requireSelection(ServerInfo.class)).deleteServer();
	}

}
