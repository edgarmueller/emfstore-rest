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
package org.eclipse.emf.emfstore.server;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.model.versioning.impl.VersionImpl;

/**
 * Helper class for creating EMFStore Server URIs and accessing segments.
 * 
 * @author jfaltermeier
 * 
 */
public final class ServerURIUtil {
	// TODO introduce name for multiple clients
	// .. workspaces/<name>/ ..

	// TODO missing example uris

	/**
	 * The EMFStore URI scheme.
	 * <p />
	 * Example URI: <b>emfstore</b>:/workspaces/0/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String SCHEME = "emfstore";

	/**
	 * The EMFStore URI segment for serverspaces.
	 */
	public static final String SERVER_SEGMENT = "serverspaces";

	/**
	 * The EMFStore URI segment for projects.
	 */
	public static final String PROJECTS_SEGMENT = "projects";

	/**
	 * The EMFStore URI segment for a version.
	 */
	public static final String VERSIONS_SEGMENT = "versions";

	/**
	 * The EMFStore URI segment for a changepackage.
	 */
	public static final String CHANGEPACKAGES_SEGMENT = VersionImpl.CHANGEPACKAGES_SEGMENT;

	/**
	 * The EMFStore URI segment for a projectstate.
	 */
	public static final String PROJECTSTATES_SEGMENT = VersionImpl.PROJECTSTATES_SEGMENT;

	/**
	 * The EMFStore URI segment for the serverspace.
	 */
	public static final String SERVERSPACE_SEGMENT = "serverspace";

	/**
	 * The EMFStore URI segment for a project's history.
	 */
	public static final String PROJECTHISTORY_SEGMENT = "projecthistory";

	private ServerURIUtil() {
		// private constructor
	}

	/**
	 * Creates an EMFStore URI for addressing the serverspace.
	 * 
	 * @return the EMFStore URI
	 */
	public static URI createServerSpaceURI() {
		return URI.createURI(getServerPrefix() + SERVERSPACE_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing the history of a project.
	 * 
	 * @param identifier the project's id
	 * @return the EMFStore URI
	 */
	public static URI createProjectHistoryURI(String identifier) {
		return URI.createURI(getProjectsPrefix(identifier) + PROJECTHISTORY_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing a specific version of a project.
	 * 
	 * @param identifier the project's id
	 * @param nr the version number
	 * @return the EMFStore URI
	 */
	public static URI createVersionURI(String identifier, int nr) {
		return URI.createURI(getProjectsPrefix(identifier) + VERSIONS_SEGMENT + "/" + nr);
	}

	/**
	 * Creates an EMFStore URI for addressing a specific changepackage of a project.
	 * 
	 * @param identifier the project's id
	 * @param nr the changepackage number
	 * @return the EMFStore URI
	 */
	public static URI createChangePackageURI(String identifier, int nr) {
		return URI.createURI(getProjectsPrefix(identifier) + CHANGEPACKAGES_SEGMENT + "/" + nr);
	}

	/**
	 * Creates an EMFStore URI for addressing a specific state of a project.
	 * 
	 * @param identifier the project's id
	 * @param nr the projectstate number
	 * @return the EMFStore URI
	 */
	public static URI createProjectStateURI(String identifier, int nr) {
		return URI.createURI(getProjectsPrefix(identifier) + PROJECTSTATES_SEGMENT + "/" + nr);
	}

	/**
	 * Returns the used profile.
	 * 
	 * @return the profile
	 */
	public static String getProfile() {
		String parameter = ServerConfiguration.getStartArgument("-profile");
		if (parameter == null) {
			parameter = "default";
			if (ServerConfiguration.isTesting()) {
				parameter += "_test";
			} else if (!ServerConfiguration.isReleaseVersion()) {
				if (ServerConfiguration.isInternalReleaseVersion()) {
					parameter += "_internal";
				} else {
					parameter += "_dev";
				}
			}
		}
		return parameter;
	}

	private static String getServerPrefix() {
		return SCHEME + ":/" + SERVER_SEGMENT + "/" + getProfile() + "/";
	}

	private static String getProjectsPrefix(String identifier) {
		return getServerPrefix() + PROJECTS_SEGMENT + "/" + identifier + "/";
	}

}
