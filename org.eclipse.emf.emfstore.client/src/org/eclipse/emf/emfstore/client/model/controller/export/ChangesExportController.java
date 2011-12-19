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

/**
 * Exports pending changes on a given {@link ProjectSpace}.
 */
public class ChangesExportController extends ProjectSpaceBasedExportController {

	private static final String FILE_EXTENSION = "*.esc";
	private static final String[] FILTERED_EXTENSIONS = { FILE_EXTENSION, "*.*" };
	private static final String[] FILTERED_NAMES = { "EMFStore change package (" + FILE_EXTENSION + ")",
		"All Files (*.*)" };

	/**
	 * Constructor.
	 * 
	 * @param projectSpace the {@link ProjectSpace} whose local changes should be exported
	 */
	public ChangesExportController(ProjectSpace projectSpace) {
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
		getProjectSpace().exportLocalChanges(file.getAbsolutePath());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.ExportController.IExportController#getLabel()
	 */
	public String getLabel() {
		return "changes";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExportController#getFilename()
	 */
	public String getFilename() {
		return "LocalChanges_" + getProjectSpace().getProjectName() + "@"
			+ getProjectSpace().getBaseVersion().getIdentifier();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.export.IExportController#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return null;
	}
}