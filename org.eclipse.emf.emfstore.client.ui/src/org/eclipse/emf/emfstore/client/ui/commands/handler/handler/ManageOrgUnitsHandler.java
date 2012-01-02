package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIManageOrgUnitsController;

public class ManageOrgUnitsHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIManageOrgUnitsController(getShell(), requireSelection(ServerInfo.class));
	}

}
