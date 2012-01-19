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
 * Abstract base class for {@link ProjectSpace} based exports.
 * 
 * @author emueller
 */
public abstract class ProjectSpaceBasedExportController implements IExportImportController {

	private ProjectSpace projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param projectSpace the {@link ProjectSpace} to be exported
	 */
	public ProjectSpaceBasedExportController(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	/**
	 * Returns the {@link ProjectSpace} that should be exported.
	 * 
	 * @return the project space
	 */
	public ProjectSpace getProjectSpace() {
		return projectSpace;
	}
}
