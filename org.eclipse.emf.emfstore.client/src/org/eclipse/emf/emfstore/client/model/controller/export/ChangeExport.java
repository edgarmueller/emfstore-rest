package org.eclipse.emf.emfstore.client.model.controller.export;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;

/**
 * Exports pending changes on a given {@link ProjectSpace}.
 */
public class ChangeExport extends ProjectSpaceBasedExport {

	/**
	 * Constructor.
	 * 
	 * @param projectSpace the {@link ProjectSpace} whose local changes should be exported
	 */
	public ChangeExport(ProjectSpace projectSpace) {
		super(projectSpace);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#getFilteredNames()
	 */
	public String[] getFilteredNames() {
		return FILTER_NAMES;
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
		getProjectSpace().exportLocalChanges(file.getAbsolutePath());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#getLabel()
	 */
	public String getLabel() {
		return "changes";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExport#getFilename()
	 */
	public String getFilename() {
		return "LocalChanges_" + getProjectSpace().getProjectName() + "@"
			+ getProjectSpace().getBaseVersion().getIdentifier() + ".ucp";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExport#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return null;
	}
}