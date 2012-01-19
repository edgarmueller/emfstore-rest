package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.ui.controller.UIAddServerController;

public class AddServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIAddServerController(getShell()).openAddServerWizardDialog();
	}

}
