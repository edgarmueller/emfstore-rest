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
package org.eclipse.emf.emfstore.client.model.importexport.impl;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.importexport.IExportImportController;

public abstract class ProjectSpaceBasedExportController implements IExportImportController {

	private final ProjectSpace projectSpace;

	public ProjectSpaceBasedExportController(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	ProjectSpace getProjectSpace() {
		return projectSpace;
	}
}
