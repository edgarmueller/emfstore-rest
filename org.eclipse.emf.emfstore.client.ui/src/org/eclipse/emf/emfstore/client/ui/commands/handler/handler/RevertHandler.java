package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIRevertController;

public class RevertHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIRevertController(getShell()).revert(requireSelection(ProjectSpace.class));
	}

}
