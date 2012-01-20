package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UIServerPropertiesController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class ServerPropertiesHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIServerPropertiesController(getShell(), requireSelection(ServerInfo.class)).openNewRepositoryWizard();
	}

}
