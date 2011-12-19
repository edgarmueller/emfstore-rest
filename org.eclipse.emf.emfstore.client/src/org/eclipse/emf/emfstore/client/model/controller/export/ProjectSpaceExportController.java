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
package org.eclipse.emf.emfstore.client.model.controller.export;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;

/**
 * Exports a {@link ProjectSpace}.
 */
public class ProjectSpaceExportController extends ProjectSpaceBasedExportController {

	private static final String FILE_EXTENSION = "*.esp";
	private static final String[] FILTERED_EXTENSIONS = { FILE_EXTENSION, "*.*" };
	private static final String[] FILTERED_NAMES = { "EMFStore project space (" + FILE_EXTENSION + ")",
		"All Files (*.*)" };

	/**
	 * Constructor.
	 * 
	 * @param projectSpace the {@link ProjectSpace} that should be exported
	 */
	public ProjectSpaceExportController(ProjectSpace projectSpace) {
		super(projectSpace);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExportController#getFilteredNames()
	 */
	public String[] getFilteredNames() {
		return FILTERED_NAMES;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExportController#getFilteredExtensions()
	 */
	public String[] getFilteredExtensions() {
		return FILTERED_EXTENSIONS;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExportController#export(java.io.File,
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
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExportController#getLabel()
	 */
	public String getLabel() {
		return "project space";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExportController#getFilename()
	 */
	public String getFilename() {
		return "projectspace_" + getProjectSpace().getProjectName() + "@"
			+ getProjectSpace().getBaseVersion().getIdentifier();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExportController#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return "org.eclipse.emf.emfstore.client.ui.exportProjectSpacePath";
	}
}