/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.controller.importexport.impl;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController;

/**
 * Class for easy retrieval of export controllers.
 * 
 * @author emueller
 */
public final class ExportControllers {

	/**
	 * Private constructor.
	 */
	private ExportControllers() {

	}

	/**
	 * Retrieves an instance of a controller for exporting a {@link Project}.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} that contains the project that should get exported
	 * 
	 * @return an export controller for projects
	 */
	public static IExportImportController getProjectExportController(ProjectSpace projectSpace) {
		return new ExportProjectController(projectSpace);
	}

	/**
	 * Retrieves an instance of a controller for exporting a {@link ProjectSpace}.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} that should get exported
	 * @return an export controller for project spaces
	 */
	public static IExportImportController getProjectSpaceExportController(ProjectSpace projectSpace) {
		return new ExportProjectSpaceController(projectSpace);
	}

	/**
	 * Retrieves an instance of a controller capable of exporting local changes.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} that contains the local changes that should get exported
	 * @return an export controller for local changes
	 */
	public static IExportImportController getChangesExportController(ProjectSpace projectSpace) {
		return new ExportChangesController(projectSpace);
	}

	/**
	 * Retrieves an instance of a controller capable of exporting a workspace.
	 * 
	 * @return an export controller for a workspace
	 */
	public static IExportImportController getWorkspaceExportController() {
		return new ExportWorkspaceController();
	}
}
