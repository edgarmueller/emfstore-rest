package org.eclipse.emf.emfstore.client.model.controller.export;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;

/**
 * Exports a {@link ProjectSpace}.
 */
public class ProjectSpaceExport extends ProjectSpaceBasedExport {

	/**
	 * Constructor.
	 * 
	 * @param projectSpace the {@link ProjectSpace} that should be exported
	 */
	public ProjectSpaceExport(ProjectSpace projectSpace) {
		super(projectSpace);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#getFilteredNames()
	 */
	public String[] getFilteredNames() {
		return FILTER_EXTS;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#getFilteredExtensions()
	 */
	public String[] getFilteredExtensions() {
		return FILTER_EXTS;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#export(java.io.File,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void export(File file, IProgressMonitor progressMonitor) throws IOException {
		WorkspaceManager.getInstance().getCurrentWorkspace()
			.exportProjectSpace(getProjectSpace(), file.getAbsolutePath());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#getLabel()
	 */
	public String getLabel() {
		return "project space";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExport#getFilename()
	 */
	public String getFilename() {
		return "projectspace_" + getProjectSpace().getProjectName() + "@"
			+ getProjectSpace().getBaseVersion().getIdentifier() + ".ucc";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExport#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return "org.eclipse.emf.emfstore.client.ui.exportProjectSpacePath";
	}
}