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
