package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UIEditServerPropertiesController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class ServerPropertiesHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIEditServerPropertiesController(getShell(), requireSelection(ServerInfo.class)).execute(false, false);
	}

}
