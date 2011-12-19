package org.eclipse.emf.emfstore.client.ui.commands.handler.handler.export;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.RequiredSelectionException;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.export.UIExportChangesController;

public class ExportChangesHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		try {
			new UIExportChangesController(getShell(), requireSelection(ProjectSpace.class)).export();
		} catch (RequiredSelectionException e) {
			// TODO:
			e.printStackTrace();
		}
	}

}
