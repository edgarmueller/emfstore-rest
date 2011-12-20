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
 * Class for easy retrieval of import controllers.
 * 
 * @author emueller
 */
public final class ImportControllers {

	/**
	 * Private constructor.
	 */
	private ImportControllers() {

	}

	/**
	 * Retrieves an instance of a controller for importing changes.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} upon which the imported changes should get applied upon
	 * @return an import controller for changes.
	 */
	public static IExportImportController getImportChangesController(ProjectSpace projectSpace) {
		return new ImportChangesController(projectSpace);
	}

	/**
	 * Returns an import controller that is capable of importing a {@link Project}.
	 * 
	 * @param projectName
	 *            the name that should be used for the imported project
	 * @return an import controller for projects
	 */
	public static IExportImportController getImportProjectController(String projectName) {
		return new ImportProjectController(projectName);
	}

	/**
	 * Retrieves an import controller that is capable of importing a {@link ProjectSpace}.
	 * 
	 * @return an import controller for project spaces
	 */
	public static IExportImportController getImportProjectSpaceController() {
		return new ImportProjectSpaceController();
	}
}
