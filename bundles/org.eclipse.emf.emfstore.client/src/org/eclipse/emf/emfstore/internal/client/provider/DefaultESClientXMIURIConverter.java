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
 * The default URI converter of EMFStore. Normalizes EMFStore URIs to file URIs.
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
	protected URI normalizeWorkspaceURI(URI uri) {
		return URI.createFileURI(Configuration.getFileInfo().getWorkspacePath());
	}

	@Override
	protected URI normalizeProjectURI(URI uri) {
		return URI.createFileURI(getProjectSpaceDirectory(uri)
			+ Configuration.getFileInfo().getProjectFragmentFileName()
			+ Configuration.getFileInfo().getProjectFragmentFileExtension());
	}

	@Override
	protected URI normalizeOperationsURI(URI uri) {
		return URI.createFileURI(getProjectSpaceDirectory(uri)
			+ Configuration.getFileInfo().getLocalChangePackageFileName()
			+ Configuration.getFileInfo().getLocalChangePackageFileExtension());
	}

	@Override
	protected URI normalizeProjectSpaceURI(URI uri) {
		return URI.createFileURI(getProjectSpaceDirectory(uri)
			+ Configuration.getFileInfo().getProjectSpaceFileName()
			+ Configuration.getFileInfo().getProjectSpaceFileExtension());
	}

	private String getProjectSpaceDirectory(URI uri) {
		return Configuration.getFileInfo().getWorkspaceDirectory()
			+ Configuration.getFileInfo().getProjectSpaceDirectoryPrefix() + uri.segment(3)
			+ File.separatorChar;
	}

}
