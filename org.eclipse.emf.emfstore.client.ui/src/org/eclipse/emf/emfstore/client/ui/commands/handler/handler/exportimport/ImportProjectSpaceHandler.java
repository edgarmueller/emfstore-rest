package org.eclipse.emf.emfstore.client.ui.commands.handler.handler.exportimport;

import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.RequiredSelectionException;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.importexport.UIImportProjectSpaceController;

public class ImportProjectSpaceHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		try {
			// TODO
			new UIImportProjectSpaceController(getShell()).execute();
		} catch (RequiredSelectionException e) {
			// TODO:
			e.printStackTrace();
		}
	}

}
