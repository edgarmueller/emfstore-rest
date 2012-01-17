package org.eclipse.emf.emfstore.client.ui.commands.handlers;

import org.eclipse.emf.emfstore.client.ui.commands.controller.UIAddServerController;

public class AddServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIAddServerController(getShell()).openAddServerWizardDialog();
	}

}
