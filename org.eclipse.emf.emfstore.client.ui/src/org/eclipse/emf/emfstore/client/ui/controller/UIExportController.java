package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.importexport.ExportImportControllerFactory;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.swt.widgets.Shell;

public class UIExportController extends UIGenericExportImportController {

	public UIExportController(Shell shell) {
		super(shell);
	}

	public void exportProjectHistoryController(ProjectInfo projectInfo) {
		execute(ExportImportControllerFactory.Export.getExportProjectHistoryController(projectInfo));
	}

	public void exportChangesController(ProjectSpace projectSpace) {
		execute(ExportImportControllerFactory.Export.getExportChangesController(projectSpace));
	}

	public void exportProjectController(ProjectSpace projectSpace) {
		execute(ExportImportControllerFactory.Export.getExportProjectController(projectSpace));
	}

	public void exportWorkspaceController() {
		execute(ExportImportControllerFactory.Export.getExportWorkspaceController());
	}

	public void exportProjectSpaceController(ProjectSpace projectSpace) {
		execute(ExportImportControllerFactory.Export.getExportProjectSpaceController(projectSpace));
	}
}
