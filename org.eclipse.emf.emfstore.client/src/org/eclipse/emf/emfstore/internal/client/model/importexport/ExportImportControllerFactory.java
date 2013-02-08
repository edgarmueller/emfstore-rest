/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.importexport;

import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ExportChangesController;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ExportProjectController;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ExportProjectHistoryController;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ExportProjectSpaceController;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ExportWorkspaceController;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ImportChangesController;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ImportProjectController;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ImportProjectHistoryController;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ImportProjectSpaceController;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;

/**
 * Convenience class for easy obtainment of export/import controllers.
 * 
 * @author emueller
 */
public class ExportImportControllerFactory {

	/**
	 * Consolidates all export related controllers.
	 */
	public static class Export {

		/**
		 * Returns the controller for exporting a project history.
		 * 
		 * @param projectInfo
		 *            the project info containing the history to be exported
		 * @return the controller for exporting the given project history
		 */
		public static IExportImportController getExportProjectHistoryController(ProjectInfo projectInfo) {
			return new ExportProjectHistoryController(projectInfo);
		}

		/**
		 * Returns the controller for exporting changes on a given project space.
		 * 
		 * @param projectSpace
		 *            the project space containing the pending changes to be exported
		 * @return the controller for exporting the changes on the given project space
		 */
		public static IExportImportController getExportChangesController(ProjectSpace projectSpace) {
			return new ExportChangesController(projectSpace);
		}

		/**
		 * Returns the controller for exporting a project.
		 * 
		 * @param projectSpace
		 *            the project space containing the project to be exported
		 * @return the controller for exporting the given project within the given project space
		 */
		public static IExportImportController getExportProjectController(ProjectSpace projectSpace) {
			return new ExportProjectController(projectSpace);
		}

		/**
		 * Returns the controller for exporting the whole workspace.
		 * 
		 * @return the controller for exporting the workspace
		 */
		public static IExportImportController getExportWorkspaceController() {
			return new ExportWorkspaceController();
		}

		/**
		 * Returns the controller for exporting a given project space.
		 * 
		 * @param projectSpace
		 *            the project space to be exported
		 * @return the controller for exporting the given project space
		 */
		public static IExportImportController getExportProjectSpaceController(ProjectSpace projectSpace) {
			return new ExportProjectSpaceController(projectSpace);
		}
	}

	/**
	 * Consolidates all import related controllers.
	 */
	public static class Import {

		/**
		 * Returns a controller for import a project history.
		 * 
		 * @return a controller for importing a project history
		 */
		public static IExportImportController getImportProjectHistoryController() {
			return new ImportProjectHistoryController();
		}

		/**
		 * Returns a controller for import the changes on the given project space.
		 * 
		 * @param projectSpace
		 *            the project space containing the changes to be imported
		 * @return a controller for importing changes on the given project space
		 */
		public static IExportImportController getImportChangesController(ProjectSpace projectSpace) {
			return new ImportChangesController(projectSpace);
		}

		/**
		 * Returns a controller for import a project.
		 * 
		 * @param projectName
		 *            a name that will be assigned to the imported project
		 * @return a controller for importing a project
		 */
		public static IExportImportController getImportProjectController(String projectName) {
			return new ImportProjectController(projectName);
		}

		/**
		 * Returns a controller for import the given project space.
		 * 
		 * @return a controller for importing the given project space
		 */
		public static IExportImportController getImportProjectSpaceController() {
			return new ImportProjectSpaceController();
		}
	}
}