package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UILoginSessionController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class ServerLoginHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		ServerInfo serverInfo = requireSelection(ServerInfo.class);
		new UILoginSessionController(getShell(), serverInfo).execute();
	}

}
