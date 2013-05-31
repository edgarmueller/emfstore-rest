/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.importexport.impl;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.util.ResourceHelper;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.FileUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;

/**
 * Exports a {@link Project}.
 * 
 * @author emueller
 */
public class ExportProjectController extends ProjectSpaceBasedExportController {

	/**
	 * Constructor.
	 * 
	 * @param projectSpace the {@link ProjectSpace} whose contained {@link Project} should be exported
	 */
	public ExportProjectController(ProjectSpace projectSpace) {
		super(projectSpace);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getFilteredNames()
	 */
	public String[] getFilteredNames() {
		return new String[] { "EMFStore Project Files (*" + ExportImportDataUnits.Project.getExtension() + ")",
			"All Files (*.*)" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getFilteredExtensions()
	 */
	public String[] getFilteredExtensions() {
		return new String[] { "*" + ExportImportDataUnits.Project.getExtension() + ", *.*" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getLabel()
	 */
	public String getLabel() {
		return "project";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.impl.IExportController#getFilename()
	 */
	public String getFilename() {
		PrimaryVersionSpec baseVersion = getProjectSpace().getBaseVersion();
		return getProjectSpace().getProjectName() + "@" + (baseVersion == null ? 0 : baseVersion.getIdentifier())
			+ ExportImportDataUnits.Project.getExtension();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.impl.IExportController#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return "org.eclipse.emf.emfstore.client.ui.exportProjectPath";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#execute(java.io.File,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void execute(File file, IProgressMonitor progressMonitor) throws IOException {

		if (!FileUtil.getExtension(file).equals(ExportImportDataUnits.Project.getExtension())) {
			file = new File(file.getAbsoluteFile() + ExportImportDataUnits.Project.getExtension());
		}

		Project project = ModelUtil.clone(getProjectSpace().getProject());
		ResourceHelper.putElementIntoNewResource(file.getAbsolutePath(), project);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#isExport()
	 */
	public boolean isExport() {
		return true;
	}
}
