package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIOperationController;

public class UndoLastOperationHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIOperationController(getShell()).undoLastOperation(requireSelection(ProjectSpace.class));
	}

}
