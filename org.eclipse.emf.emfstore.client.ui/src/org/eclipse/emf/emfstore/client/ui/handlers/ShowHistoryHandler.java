package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIShowHistoryController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class ShowHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIShowHistoryController(getShell(), requireSelection(ProjectSpace.class), requireSelection(EObject.class))
			.execute(true, true);
	}

}
