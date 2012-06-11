package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UICheckoutController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

/**
 * Handler for checking out a project.
 * 
 * @author emueller
 * 
 */
public class CheckoutHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {

		ProjectInfo projectInfo = requireSelection(ProjectInfo.class);

		if (projectInfo == null || projectInfo.eContainer() == null) {
			return;
		}

		// FIXME: eContainer call
		new UICheckoutController(getShell(), (ServerInfo) projectInfo.eContainer(), projectInfo).execute();
	}

}
