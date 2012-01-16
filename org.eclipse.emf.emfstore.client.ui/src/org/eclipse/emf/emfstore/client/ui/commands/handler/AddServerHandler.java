package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIAddServerController;

public class AddServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIAddServerController(getShell()).openAddServerWizardDialog();
	}

}
