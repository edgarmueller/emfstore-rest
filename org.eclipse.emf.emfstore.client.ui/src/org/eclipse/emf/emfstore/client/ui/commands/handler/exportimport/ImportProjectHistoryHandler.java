package org.eclipse.emf.emfstore.client.ui.commands.handler.exportimport;

import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ImportProjectHistoryController;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIGenericExportImportController;

public class ImportProjectHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIGenericExportImportController(getShell(), new ImportProjectHistoryController()).execute();
	}

}
