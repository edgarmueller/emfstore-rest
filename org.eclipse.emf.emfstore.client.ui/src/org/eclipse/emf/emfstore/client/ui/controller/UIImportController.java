package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.importexport.ExportImportControllerFactory;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.swt.widgets.Shell;

public class UIImportController extends UIGenericExportImportController {

	public UIImportController(Shell shell) {
		super(shell);
	}

	public void importProjectHistory(ProjectInfo projectInfo) {
		execute(ExportImportControllerFactory.Import.getImportProjectHistoryController());
	}

	public void importChanges(ProjectSpace projectSpace) {
		execute(ExportImportControllerFactory.Import.getImportChangesController(projectSpace));
	}

	public void importProject(String projectName) {
		execute(ExportImportControllerFactory.Import.getImportProjectController(projectName));
	}

	public void importProjectSpace(ProjectSpace projectSpace) {
		execute(ExportImportControllerFactory.Import.getImportProjectSpaceController(projectSpace));
	}
}
