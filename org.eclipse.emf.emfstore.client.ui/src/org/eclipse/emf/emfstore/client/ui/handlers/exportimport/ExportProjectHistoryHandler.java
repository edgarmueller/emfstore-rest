package org.eclipse.emf.emfstore.client.ui.handlers.exportimport;

import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ExportProjectHistoryController;
import org.eclipse.emf.emfstore.client.ui.controller.UIGenericExportImportController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

public class ExportProjectHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIGenericExportImportController(getShell(), new ExportProjectHistoryController(
			requireSelection(ProjectInfo.class))).execute();
	}

}
