package org.eclipse.emf.emfstore.client.ui.commands.handler.controller.importexport;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController;
import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ExportControllers;
import org.eclipse.swt.widgets.Shell;

public class UIExportProjectController extends UIGenericExportImportController {

	private final ProjectSpace projectSpace;

	public UIExportProjectController(Shell shell, ProjectSpace projectSpace) {
		super(shell);
		this.projectSpace = projectSpace;
	}

	@Override
	public IExportImportController getController() {
		return ExportControllers.getProjectExportController(projectSpace);
	}
}
