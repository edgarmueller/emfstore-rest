package org.eclipse.emf.emfstore.client.ui.commands.handler.controller.export;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.ExportController;
import org.eclipse.swt.widgets.Shell;

public class UIExportChangesController extends UIGenericExportController {

	private final ProjectSpace projectSpace;

	public UIExportChangesController(Shell shell, ProjectSpace projectSpace) {
		super(shell);
		this.projectSpace = projectSpace;
	}

	@Override
	public void export() {
		execute(ExportController.getChangesExportController(projectSpace));
	}

}
