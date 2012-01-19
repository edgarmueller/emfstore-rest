package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIOperationController;

public class RevertHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIOperationController(getShell()).revert(requireSelection(ProjectSpace.class));
	}

}
