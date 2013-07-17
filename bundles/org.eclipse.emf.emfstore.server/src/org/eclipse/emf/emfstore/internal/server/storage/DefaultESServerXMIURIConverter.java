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
import java.util.HashSet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.model.versioning.impl.VersionImpl;
import org.eclipse.emf.emfstore.server.AbstractESServerURIConverter;
import org.eclipse.emf.emfstore.server.ServerURIUtil;

/**
 * The default URI converter of EMFStore Server. Normalizes EMFStore URIs to file URIs.
 * 
 * @author jfaltermeier
 * 
 */
public class DefaultESServerXMIURIConverter extends AbstractESServerURIConverter {

	/**
	 * File prefix for folder: project.
	 */
	public static final String FILE_PREFIX_PROJECTFOLDER = "project-";

	/**
	 * File prefix for file: changepackage.
	 */
	private static final String FILE_PREFIX_CHANGEPACKAGE = VersionImpl.FILE_PREFIX_CHANGEPACKAGE;// "changepackage-";

	/**
	 * File prefix for file: projectstate.
	 */
	private static final String FILE_PREFIX_PROJECTSTATE = VersionImpl.FILE_PREFIX_PROJECTSTATE; // "projectstate-";

	/**
	 * File prefix for file: version.
	 */
	private static final String FILE_PREFIX_VERSION = "version-";

	/**
	 * File extension for main file: emfstore server storage.
	 */
	private static final String FILE_EXTENSION_MAINSTORAGE = ".uss";

	/**
	 * File extension for main file: emfstore project historyF.
	 */
	private static final String FILE_EXTENSION_PROJECTHISTORY = ".uph";

	/**
	 * File extension for main file: emfstore project version.
	 */
	private static final String FILE_EXTENSION_VERSION = ".upv";

	/**
	 * File extension for main file: emfstore project state.
	 */
	private static final String FILE_EXTENSION_PROJECTSTATE = VersionImpl.FILE_EXTENSION_PROJECTSTATE; // ".ups";

	/**
	 * File extension for main file: emfstore change package.
	 */
	private static final String FILE_EXTENSION_CHANGEPACKAGE = VersionImpl.FILE_EXTENSION_CHANGEPACKAGE;// ".ucp";

	/**
	 * Creates an instance including all needed URIHandlers.
	 */
	public DefaultESServerXMIURIConverter() {
		super();
		getURIHandlers().add(0, new ServerSpaceFileURIHandler(getExtensionsMap()));
	}

	@Override
	protected URI normalizeServerSpaceURI(String profile) {
		return URI.createFileURI(ServerConfiguration.getServerHome() + "storage" + FILE_EXTENSION_MAINSTORAGE);
	}

	@Override
	protected URI normalizeDynamicModelsURI(String profile, String ecoreName) {
		return URI.createFileURI(ServerConfiguration.getServerHome() + ServerURIUtil.DYNAMIC_MODELS_SEGMENT + "/"
			+ ecoreName);
	}

	@Override
	protected URI normalizeProjectHistoryURI(String profile, String projectId) {
		return URI.createFileURI(getProjectFolder(projectId) + "projectHistory"
			+ FILE_EXTENSION_PROJECTHISTORY);
	}

	@Override
	protected URI normalizeVersionURI(String profile, String projectId, int version) {
		return URI.createFileURI(getProjectFolder(projectId) + FILE_PREFIX_VERSION
			+ version + FILE_EXTENSION_VERSION);
	}

	@Override
	protected URI normalizeChangePackageURI(String profile, String projectId, int version) {
		return URI.createFileURI(getProjectFolder(projectId) + getChangePackageFile(version));
	}

	@Override
	protected URI normalizeProjectStateURI(String profile, String projectId, int version) {
		return URI.createFileURI(getProjectFolder(projectId) + getProjectFile(version));
	}

	private String getProjectFolder(String projectId) {
		return ServerConfiguration.getServerHome() + FILE_PREFIX_PROJECTFOLDER + projectId
			+ File.separatorChar;
	}

	private String getProjectFile(int versionNumber) {
		return FILE_PREFIX_PROJECTSTATE + versionNumber
			+ FILE_EXTENSION_PROJECTSTATE;
	}

	private String getChangePackageFile(int versionNumber) {
		return FILE_PREFIX_CHANGEPACKAGE + versionNumber
			+ FILE_EXTENSION_CHANGEPACKAGE;
	}

	private HashSet<String> getExtensionsMap() {
		HashSet<String> extensions = new HashSet<String>();
		extensions.add(FILE_EXTENSION_CHANGEPACKAGE);
		extensions.add(FILE_EXTENSION_PROJECTHISTORY);
		extensions.add(FILE_EXTENSION_PROJECTSTATE);
		extensions.add(FILE_EXTENSION_VERSION);
		return extensions;
	}

}
