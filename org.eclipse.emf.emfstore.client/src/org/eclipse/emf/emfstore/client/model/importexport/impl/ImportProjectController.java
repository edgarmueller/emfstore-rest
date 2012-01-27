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

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.importexport.ExportImportDataUnits;
import org.eclipse.emf.emfstore.client.model.importexport.IExportImportController;

/**
 * Imports a project.
 * 
 * @author emueller
 */
public class ImportProjectController implements IExportImportController {

	private final String projectName;

	/**
	 * Constructor.
	 * 
	 * @param projectName
	 *            the name that should be used for the imported project
	 */
	public ImportProjectController(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#getLabel()
	 */
	public String getLabel() {
		return "project";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#getFilteredNames()
	 */
	public String[] getFilteredNames() {
		return new String[] { "EMFStore project space (*" + ExportImportDataUnits.Project.getExtension() + ")",
			"All Files (*.*)" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#getFilteredExtensions()
	 */
	public String[] getFilteredExtensions() {
		return new String[] { "*" + ExportImportDataUnits.Project.getExtension(), "*.*" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return "org.eclipse.emf.emfstore.client.ui.importProjectPath";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#execute(java.io.File,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 * 
	 * @throws IOException
	 *             in case an error occurs during the import of the project
	 */
	public void execute(File file, IProgressMonitor progressMonitor) throws IOException {
		Workspace currentWorkspace = WorkspaceManager.getInstance().getCurrentWorkspace();
		ProjectSpace projectSpace = currentWorkspace.importProject(file.getAbsolutePath());
		projectSpace.setProjectName(projectName);
		projectSpace.eResource().save(null);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#getFilename()
	 */
	public String getFilename() {
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#isExport()
	 */
	public boolean isExport() {
		return false;
	}
}
