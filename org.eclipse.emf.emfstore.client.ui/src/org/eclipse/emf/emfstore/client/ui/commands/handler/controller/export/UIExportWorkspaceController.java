package org.eclipse.emf.emfstore.client.ui.commands.handler.controller.export;

import org.eclipse.emf.emfstore.client.model.controller.ExportController;
import org.eclipse.swt.widgets.Shell;

public class UIExportWorkspaceController extends UIGenericExportController {

	public UIExportWorkspaceController(Shell shell) {
		super(shell);
	}

	@Override
	public void export() {
		execute(ExportController.getWorkspaceExportController());
	}

}
