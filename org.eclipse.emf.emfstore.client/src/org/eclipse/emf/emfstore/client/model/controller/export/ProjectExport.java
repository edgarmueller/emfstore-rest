package org.eclipse.emf.emfstore.client.model.controller.export;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.common.model.Project;

/**
 * Exports a {@link Project}.
 */
public class ProjectExport extends ProjectSpaceBasedExport {

	/**
	 * Constructor.
	 * 
	 * @param projectSpace the {@link ProjectSpace} whose contained {@link Project} should be exported
	 */
	public ProjectExport(ProjectSpace projectSpace) {
		super(projectSpace);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#getFilteredNames()
	 */
	public String[] getFilteredNames() {
		return new String[] { "EMFStore Project Files (*.ecp)", "All Files (*.*)" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#getFilteredExtensions()
	 */
	public String[] getFilteredExtensions() {
		return new String[] { "*.ecp", "*.*" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#export(java.io.File,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void export(File file, IProgressMonitor progressMonitor) throws IOException {
		getProjectSpace().exportProject(file.getAbsolutePath());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#getLabel()
	 */
	public String getLabel() {
		return "project";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExport#getFilename()
	 */
	public String getFilename() {
		return getProjectSpace().getProjectName() + "@" + getProjectSpace().getBaseVersion().getIdentifier() + ".ucp";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExport#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return "org.eclipse.emf.emfstore.client.ui.exportProjectPath";
	}
}