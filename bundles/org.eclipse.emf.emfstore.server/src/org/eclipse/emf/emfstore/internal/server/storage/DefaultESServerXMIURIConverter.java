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
package org.eclipse.emf.emfstore.internal.server.storage;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.AbstractESServerURIConverter;

/**
 * The default URI converter of EMFStore Server. Normalizes EMFStore URIs to file URIs.
 * 
 * @author jfaltermeier
 * 
 */
public class DefaultESServerXMIURIConverter extends AbstractESServerURIConverter {

	@Override
	protected URI normalizeServerSpaceURI() {
		return URI.createFileURI(ServerConfiguration.getServerMainFile());
	}

	@Override
	protected URI normalizeProjectHistoryURI(String projectId) {
		return URI.createFileURI(getProjectFolder(projectId) + "projectHistory"
			+ ServerConfiguration.FILE_EXTENSION_PROJECTHISTORY);
	}

	@Override
	protected URI normalizeVersionURI(String projectId, int version) {
		return URI.createFileURI(getProjectFolder(projectId) + ServerConfiguration.FILE_PREFIX_VERSION
			+ version + ServerConfiguration.FILE_EXTENSION_VERSION);
	}

	@Override
	protected URI normalizeChangePackageURI(String projectId, int version) {
		return URI.createFileURI(getProjectFolder(projectId) + getChangePackageFile(version));
	}

	@Override
	protected URI normalizeProjectStateURI(String projectId, int version) {
		return URI.createFileURI(getProjectFolder(projectId) + getProjectFile(version));
	}

	private String getProjectFolder(String projectId) {
		return ServerConfiguration.getServerHome() + ServerConfiguration.FILE_PREFIX_PROJECTFOLDER + projectId
			+ File.separatorChar;
	}

	private String getProjectFile(int versionNumber) {
		return ServerConfiguration.FILE_PREFIX_PROJECTSTATE + versionNumber
			+ ServerConfiguration.FILE_EXTENSION_PROJECTSTATE;
	}

	private String getChangePackageFile(int versionNumber) {
		return ServerConfiguration.FILE_PREFIX_CHANGEPACKAGE + versionNumber
			+ ServerConfiguration.FILE_EXTENSION_CHANGEPACKAGE;
	}

}
