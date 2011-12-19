package org.eclipse.emf.emfstore.client.model.controller.export;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;

/**
 * Exports the whole {@link Workspace}.
 */
public class WorkspaceExport implements IExport {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExport#getLabel()
	 */
	public String getLabel() {
		return "workspace";
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
		WorkspaceManager.getInstance().getCurrentWorkspace().exportWorkSpace(file.getAbsolutePath());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExport#getFilename()
	 */
	public String getFilename() {
		return "Workspace_" + new Date();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExport#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return "org.eclipse.emf.emfstore.client.ui.exportWorkSpacePath";
	}
}