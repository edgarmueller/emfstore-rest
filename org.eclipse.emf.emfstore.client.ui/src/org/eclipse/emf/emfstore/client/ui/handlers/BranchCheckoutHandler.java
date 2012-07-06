package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UICheckoutController;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

public class BranchCheckoutHandler extends AbstractEMFStoreHandler implements IHandler {

	@Override
	public void handle() {

		ProjectInfo projectInfo = requireSelection(ProjectInfo.class);

		if (projectInfo == null || projectInfo.eContainer() == null) {
			return;
		}

		// FIXME: eContainer call
		new UICheckoutController(getShell(), ((ServerInfo) projectInfo.eContainer()), projectInfo, true).execute();
	}

}
