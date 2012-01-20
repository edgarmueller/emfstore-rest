package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UIServerController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class DeleteServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIServerController(getShell(), requireSelection(ServerInfo.class)).deleteServer();
	}

}
