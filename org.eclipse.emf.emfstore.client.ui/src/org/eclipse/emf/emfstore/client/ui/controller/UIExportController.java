package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.importexport.ExportImportControllerFactory;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.swt.widgets.Shell;

public class UIExportController extends UIGenericExportImportController {

	public UIExportController(Shell shell) {
		super(shell);
	}

	public void exportProjectHistory(ProjectInfo projectInfo) {
		execute(ExportImportControllerFactory.Export.getExportProjectHistoryController(projectInfo));
	}

	public void exportChanges(ProjectSpace projectSpace) {
		execute(ExportImportControllerFactory.Export.getExportChangesController(projectSpace));
	}

	public void exportProject(ProjectSpace projectSpace) {
		execute(ExportImportControllerFactory.Export.getExportProjectController(projectSpace));
	}

	public void exportWorkspace() {
		execute(ExportImportControllerFactory.Export.getExportWorkspaceController());
	}

	public void exportProjectSpace(ProjectSpace projectSpace) {
		execute(ExportImportControllerFactory.Export.getExportProjectSpaceController(projectSpace));
	}
}
