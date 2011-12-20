package org.eclipse.emf.emfstore.client.ui.commands.handler.handler.exportimport;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.RequiredSelectionException;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.importexport.UIExportProjectSpaceController;

public class ExportProjectSpaceHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		try {
			new UIExportProjectSpaceController(getShell(), requireSelection(ProjectSpace.class)).execute();
		} catch (RequiredSelectionException e) {
			// TODO:
			e.printStackTrace();
		}
	}

}
