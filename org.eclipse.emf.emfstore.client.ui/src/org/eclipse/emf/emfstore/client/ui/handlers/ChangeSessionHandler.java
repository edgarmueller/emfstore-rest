package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UIChangeSessionController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class ChangeSessionHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIChangeSessionController(getShell(), requireSelection(ServerInfo.class)).changeSession();
	}

}
