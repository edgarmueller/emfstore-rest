package org.eclipse.emf.emfstore.client.ui.handlers.exportimport;

import org.eclipse.emf.emfstore.client.ui.controller.UIExportController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

public class ExportProjectHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIExportController(getShell()).exportProjectHistoryController(requireSelection(ProjectInfo.class));
	}

}
