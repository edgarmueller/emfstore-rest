package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIDeleteProjectController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class DeleteProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIDeleteProjectController(getShell(), requireSelection(ProjectSpace.class)).execute();
	}
}
