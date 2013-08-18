/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.provider;

import java.io.File;
import java.util.HashSet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.emfstore.client.provider.ESAbstractClientURIConverter;
import org.eclipse.emf.emfstore.internal.client.importexport.impl.ExportImportDataUnits;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;

/**
 * The default URI converter of EMFStore Client. Normalizes EMFStore URIs to file URIs.
 * 
 * @author jfaltermeier
 * 
 */
public class XMIClientURIConverter extends ESAbstractClientURIConverter {

	/**
	 * The prefix of project space directories.
	 */
	public static final String PROJECT_SAPCE_DIRECTORY_PREFIX = "ps-";

	private final String projectSpaceFileName = "projectspace";
	private final String projectSpaceFileExtension = ExportImportDataUnits.ProjectSpace.getExtension();
	private final String localChangePackageFileName = "operations";
	private final String localChangePackageExtension = ".eoc";
	private final String projectFragmentFileName = "project";
	private final String projectFragmentExtension = ExportImportDataUnits.Project.getExtension();

	/**
	 * Creates an instance including all needed URIHandlers.
	 */
	public XMIClientURIConverter() {
		super();
		getURIHandlers().add(0, new ProjectSpaceFileURIHandler(getExtensionsMap()));
	}

	@Override
	protected URI normalizeWorkspaceURI(String profile) {
		return URI.createFileURI(Configuration.getFileInfo().getWorkspaceDirectory() + "workspace.ucw");
	}

	@Override
	protected URI normalizeProjectURI(String profile, String projectId) {
		return URI.createFileURI(getProjectSpaceDirectory(projectId)
			+ projectFragmentFileName + projectFragmentExtension);
	}

	@Override
	protected URI normalizeOperationsURI(String profile, String projectId) {
		return URI.createFileURI(getProjectSpaceDirectory(projectId)
			+ localChangePackageFileName + localChangePackageExtension);
	}

	@Override
	protected URI normalizeProjectSpaceURI(String profile, String projectId) {
		return URI.createFileURI(getProjectSpaceDirectory(projectId)
			+ projectSpaceFileName + projectSpaceFileExtension);
	}

	private String getProjectSpaceDirectory(String projectId) {
		return Configuration.getFileInfo().getWorkspaceDirectory()
			+ PROJECT_SAPCE_DIRECTORY_PREFIX + projectId
			+ File.separatorChar;
	}

	private HashSet<String> getExtensionsMap() {
		HashSet<String> extensions = new HashSet<String>();
		extensions.add(projectSpaceFileExtension);
		extensions.add(localChangePackageExtension);
		extensions.add(projectFragmentExtension);
		return extensions;
	}

}
