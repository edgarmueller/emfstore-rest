package org.eclipse.emf.emfstore.client.ui.commands.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.commands.controller.UIServerPropertiesController;

public class ServerPropertiesHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIServerPropertiesController(getShell(), requireSelection(ServerInfo.class)).openNewRepositoryWizard();
	}

}
