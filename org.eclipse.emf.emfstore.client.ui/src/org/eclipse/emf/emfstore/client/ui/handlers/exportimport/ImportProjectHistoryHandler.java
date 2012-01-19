package org.eclipse.emf.emfstore.client.ui.handlers.exportimport;

import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ImportProjectHistoryController;
import org.eclipse.emf.emfstore.client.ui.controller.UIGenericExportImportController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler;

public class ImportProjectHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIGenericExportImportController(getShell(), new ImportProjectHistoryController()).execute();
	}

}
