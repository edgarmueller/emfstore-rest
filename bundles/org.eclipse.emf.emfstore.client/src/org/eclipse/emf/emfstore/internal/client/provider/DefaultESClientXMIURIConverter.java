/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.provider;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.emfstore.client.provider.AbstractESClientURIConverter;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;

/**
 * The default URI converter of EMFStore Client. Normalizes EMFStore URIs to file URIs.
 * 
 * @author jfaltermeier
 * 
 */
public class DefaultESClientXMIURIConverter extends AbstractESClientURIConverter {

	/**
	 * Creates an instance including all needed URIHandlers.
	 */
	public DefaultESClientXMIURIConverter() {
		super();
		getURIHandlers().add(0, new ProjectSpaceFileURIHandler());
	}

	@Override
	protected URI normalizeWorkspaceURI(String profile) {
		return URI.createFileURI(Configuration.getFileInfo().getWorkspacePath());
	}

	@Override
	protected URI normalizeProjectURI(String profile, String projectId) {
		return URI.createFileURI(getProjectSpaceDirectory(projectId)
			+ Configuration.getFileInfo().getProjectFragmentFileName()
			+ Configuration.getFileInfo().getProjectFragmentFileExtension());
	}

	@Override
	protected URI normalizeOperationsURI(String profile, String projectId) {
		return URI.createFileURI(getProjectSpaceDirectory(projectId)
			+ Configuration.getFileInfo().getLocalChangePackageFileName()
			+ Configuration.getFileInfo().getLocalChangePackageFileExtension());
	}

	@Override
	protected URI normalizeProjectSpaceURI(String profile, String projectId) {
		return URI.createFileURI(getProjectSpaceDirectory(projectId)
			+ Configuration.getFileInfo().getProjectSpaceFileName()
			+ Configuration.getFileInfo().getProjectSpaceFileExtension());
	}

	private String getProjectSpaceDirectory(String projectId) {
		return Configuration.getFileInfo().getWorkspaceDirectory()
			+ Configuration.getFileInfo().getProjectSpaceDirectoryPrefix() + projectId
			+ File.separatorChar;
	}

}
