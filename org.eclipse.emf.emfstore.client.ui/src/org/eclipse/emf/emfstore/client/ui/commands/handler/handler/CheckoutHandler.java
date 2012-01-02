package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UICheckoutController;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

/**
 * Handler for checking out a project.
 * 
 * @author emueller
 * 
 */
public class CheckoutHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UICheckoutController(getShell(), requireSelection(ProjectInfo.class));
	}

}
