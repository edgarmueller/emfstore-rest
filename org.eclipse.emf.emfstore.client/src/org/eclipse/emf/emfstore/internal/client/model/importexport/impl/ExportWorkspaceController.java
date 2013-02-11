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
package org.eclipse.emf.emfstore.internal.client.model.importexport.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.Workspace;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.importexport.ExportImportDataUnits;
import org.eclipse.emf.emfstore.internal.client.model.importexport.IExportImportController;
import org.eclipse.emf.emfstore.internal.client.model.util.ResourceHelper;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.FileUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * Exports the whole {@link Workspace}.
 * 
 * @author emueller
 */
public class ExportWorkspaceController implements IExportImportController {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getLabel()
	 */
	public String getLabel() {
		return "workspace";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getFilteredNames()
	 */
	public String[] getFilteredNames() {
		return new String[] { "EMFStore Workspace Files (*" + ExportImportDataUnits.Workspace.getExtension() + ")",
			"All Files (*.*)" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getFilteredExtensions()
	 */
	public String[] getFilteredExtensions() {
		return new String[] { "*" + ExportImportDataUnits.Workspace.getExtension() + ", *.*" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.impl.IExportController#getFilename()
	 */
	public String getFilename() {
		return "Workspace_" + new Date();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.impl.IExportController#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return "org.eclipse.emf.emfstore.client.ui.exportWorkSpacePath";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#execute(java.io.File,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void execute(File file, IProgressMonitor progressMonitor) throws IOException {

		if (!FileUtil.getExtension(file).equals(ExportImportDataUnits.Workspace.getExtension())) {
			file = new File(file.getAbsoluteFile() + ExportImportDataUnits.Workspace.getExtension());
		}

		Workspace copy = ModelUtil.clone((Workspace) WorkspaceProvider.getInstance().getWorkspace());

		int i = 0;

		for (ProjectSpace copiedProjectSpace : copy.getProjectSpaces()) {
			Project orgProject = ((Workspace) WorkspaceProvider.getInstance().getWorkspace()).getProjectSpaces()
				.get(i++).getProject();
			copiedProjectSpace.setProject(ModelUtil.clone(orgProject));
		}

		ResourceHelper.putWorkspaceIntoNewResource(file.getAbsolutePath(), copy);
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