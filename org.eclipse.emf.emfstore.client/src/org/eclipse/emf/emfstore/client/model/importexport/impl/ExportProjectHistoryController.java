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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.importexport.ExportImportDataUnits;
import org.eclipse.emf.emfstore.client.model.importexport.IExportImportController;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.common.model.util.FileUtil;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

/**
 * Controller class for exporting a {@link ProjectHistory}.
 * 
 * @author emueller
 * 
 */
public class ExportProjectHistoryController extends ServerCall<Void> implements IExportImportController {

	private ProjectHistory projectHistory;
	private ProjectInfo projectInfo;

	/**
	 * Constructor.
	 * 
	 * @param projectInfo
	 *            the project info containing the history to be exported
	 */
	public ExportProjectHistoryController(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#getLabel()
	 */
	public String getLabel() {
		return "Exporting changes";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#getFilteredNames()
	 */
	public String[] getFilteredNames() {
		return new String[] { "EMFStore Project Files (*" + ExportImportDataUnits.History.getExtension() + ")",
			"All Files (*.*)" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#getFilteredExtensions()
	 */
	public String[] getFilteredExtensions() {
		return new String[] { "*" + ExportImportDataUnits.History.getExtension() + ", *.*" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return "org.eclipse.emf.emfstore.client.ui.exportProjectHistoryPath";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#execute(java.io.File,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void execute(File file, IProgressMonitor progressMonitor) throws IOException {

		if (!FileUtil.getExtension(file).equals(ExportImportDataUnits.History.getExtension())) {
			file = new File(file.getAbsoluteFile() + ExportImportDataUnits.History.getExtension());
		}

		saveProjectHistory(projectHistory, file.getAbsolutePath());
	}

	private void saveProjectHistory(ProjectHistory projectHistory, String absoluteFileName) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI.createFileURI(absoluteFileName));
		resource.getContents().add(projectHistory);
		ModelUtil.saveResource(resource, WorkspaceUtil.getResourceLogger());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#getFilename()
	 */
	public String getFilename() {
		return "ProjectHistory_of_" + projectInfo.getName() + "@"
			+ projectHistory.getLastVersion().getPrimarySpec().getIdentifier()
			+ ExportImportDataUnits.History.getExtension();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController#isExport()
	 */
	public boolean isExport() {
		return true;
	}

	@Override
	protected Void run() throws EMFStoreException {
		projectHistory = WorkspaceProvider.getInstance().getConnectionManager()
			.exportProjectHistoryFromServer(getSessionId(), projectInfo.getProjectId());
		return null;
	}

}