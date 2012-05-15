package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.ui.controller.UIAddServerController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class AddServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIAddServerController(getShell()).execute(false, false);
	}

}
