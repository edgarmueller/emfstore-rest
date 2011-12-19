package org.eclipse.emf.emfstore.client.model.controller.export;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;

/**
 * Abstract base class for {@link ProjectSpace} based exports.
 */
public abstract class ProjectSpaceBasedExport implements IExport {

	private ProjectSpace projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param projectSpace the {@link ProjectSpace} to be exported
	 */
	public ProjectSpaceBasedExport(ProjectSpace projectSpace) {
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
