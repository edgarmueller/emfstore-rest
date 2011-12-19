package org.eclipse.emf.emfstore.client.ui.commands.handler.handler.export;

import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.RequiredSelectionException;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.export.UIExportWorkspaceController;

public class ExportWorkspaceHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		try {
			new UIExportWorkspaceController(getShell()).export();
		} catch (RequiredSelectionException e) {
			// TODO:
			e.printStackTrace();
		}
	}

}
