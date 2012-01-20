package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UIDeleteServerController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class DeleteServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIDeleteServerController(getShell(), requireSelection(ServerInfo.class)).deleteServer();
	}

}
