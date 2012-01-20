package org.eclipse.emf.emfstore.client.ui.handlers.exportimport;

import org.eclipse.emf.emfstore.client.ui.controller.UIImportController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

public class ImportProjectHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIImportController(getShell()).importProjectHistoryController(requireSelection(ProjectInfo.class));
	}

}
