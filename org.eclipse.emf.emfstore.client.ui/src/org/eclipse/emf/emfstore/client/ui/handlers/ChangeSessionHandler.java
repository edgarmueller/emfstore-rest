package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UIChangeSessionController;

public class ChangeSessionHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIChangeSessionController(getShell(), requireSelection(ServerInfo.class)).changeSession();
	}

}
