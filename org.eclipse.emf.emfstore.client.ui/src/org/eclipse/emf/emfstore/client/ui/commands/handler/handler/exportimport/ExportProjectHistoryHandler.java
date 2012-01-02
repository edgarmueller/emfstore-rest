package org.eclipse.emf.emfstore.client.ui.commands.handler.handler.exportimport;

import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ExportProjectHistoryController;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIGenericExportImportController;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

public class ExportProjectHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIGenericExportImportController(getShell(), new ExportProjectHistoryController(
			requireSelection(ProjectInfo.class))).execute();
	}

}
