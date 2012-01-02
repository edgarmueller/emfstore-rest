package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIDeleteServerController;

public class DeleteServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIDeleteServerController(getShell(), requireSelection(ServerInfo.class)).deleteServer();
	}

}
