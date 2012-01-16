package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIServerPropertiesController;

public class ServerPropertiesHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIServerPropertiesController(getShell(), requireSelection(ServerInfo.class)).openNewRepositoryWizard();
	}

}
