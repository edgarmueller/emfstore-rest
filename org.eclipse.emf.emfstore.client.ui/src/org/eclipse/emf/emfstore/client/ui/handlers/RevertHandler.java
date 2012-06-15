package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIUndoLastOperationController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class RevertHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIUndoLastOperationController(getShell(), requireSelection(ProjectSpace.class)).execute();
	}

}
