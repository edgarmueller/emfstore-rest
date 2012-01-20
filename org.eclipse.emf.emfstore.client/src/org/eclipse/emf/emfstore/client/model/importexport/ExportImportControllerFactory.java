package org.eclipse.emf.emfstore.client.model.importexport;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ExportChangesController;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ExportProjectController;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ExportProjectHistoryController;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ExportProjectSpaceController;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ExportWorkspaceController;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ImportChangesController;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ImportProjectController;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ImportProjectHistoryController;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ImportProjectSpaceController;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

public class ExportImportControllerFactory {

	public static class Export {
		public static IExportImportController getExportProjectHistoryController(ProjectInfo projectInfo) {
			return new ExportProjectHistoryController(projectInfo);
		}

		public static IExportImportController getExportChangesController(ProjectSpace projectSpace) {
			return new ExportChangesController(projectSpace);
		}

		public static IExportImportController getExportProjectController(ProjectSpace projectSpace) {
			return new ExportProjectController(projectSpace);
		}

		public static IExportImportController getExportWorkspaceController() {
			return new ExportWorkspaceController();
		}

		public static IExportImportController getExportProjectSpaceController(ProjectSpace projectSpace) {
			return new ExportProjectSpaceController(projectSpace);
		}
	}

	public static class Import {

		public static IExportImportController getImportProjectHistoryController() {
			return new ImportProjectHistoryController();
		}

		public static IExportImportController getImportChangesController(ProjectSpace projectSpace) {
			return new ImportChangesController(projectSpace);
		}

		public static IExportImportController getImportProjectController(String projectName) {
			return new ImportProjectController(projectName);
		}

		public static IExportImportController getImportProjectSpaceController(ProjectSpace projectSpace) {
			return new ImportProjectSpaceController();
		}
	}
}
