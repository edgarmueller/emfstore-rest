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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.client.model.importexport.ExportImportDataUnits;
import org.eclipse.emf.emfstore.internal.client.model.importexport.IExportImportController;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectHistory;

/**
 * Controller for import a {@link ProjectHistory}.
 * 
 * @author emueller
 * 
 */
public class ImportProjectHistoryController extends ServerCall<Void> implements IExportImportController {

	private ProjectHistory projectHistory;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getLabel()
	 */
	public String getLabel() {
		return "Importing project history";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getFilteredNames()
	 */
	public String[] getFilteredNames() {
		return new String[] { "EMFStore Project History Files (*" + ExportImportDataUnits.History.getExtension() + ")",
			"All Files (*.*)" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getFilteredExtensions()
	 */
	public String[] getFilteredExtensions() {
		return new String[] { "*" + ExportImportDataUnits.History.getExtension(), "*.*" };
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getParentFolderPropertyKey()
	 */
	public String getParentFolderPropertyKey() {
		return "org.eclipse.emf.emfstore.internal.client.ui.importProjectHistoryPath";
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#execute(java.io.File,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void execute(File file, IProgressMonitor progressMonitor) throws IOException {
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);
		EList<EObject> directContents = resource.getContents();

		// sanity check
		if (directContents.size() != 1 && (!(directContents.get(0) instanceof ProjectHistory))) {
			throw new IOException("File is corrupt, does not contain a ProjectHistory.");
		}

		projectHistory = (ProjectHistory) directContents.get(0);
		resource.getContents().remove(projectHistory);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#getFilename()
	 */
	public String getFilename() {
		// no suggestion
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.importexport.IExportImportController#isExport()
	 */
	public boolean isExport() {
		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall#run()
	 */
	@Override
	protected Void run() throws EMFStoreException {
		WorkspaceProvider.getInstance().getConnectionManager()
			.importProjectHistoryToServer(getUsersession().getSessionId(), projectHistory);
		return null;
	}

}