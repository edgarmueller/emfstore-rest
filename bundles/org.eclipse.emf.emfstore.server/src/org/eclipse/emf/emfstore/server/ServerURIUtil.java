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
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.impl.VersionImpl;

/**
 * Helper class for creating EMFStore Server URIs and accessing segments.
 * 
 * @author jfaltermeier
 * 
 */
public final class ServerURIUtil {

	/**
	 * The EMFStore URI scheme.
	 * <p />
	 * Example URI: <b>emfstore</b>:/workspaces/0/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String SCHEME = "emfstore";

	/**
	 * The EMFStore URI segment for serverspaces.
	 * <p />
	 * Example URI: emfstore:/<b>serverspaces</b>/<i>profile</i>/serverspace
	 */
	public static final String SERVER_SEGMENT = "serverspaces";

	/**
	 * The EMFStore URI segment for projects.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/<b>projects</b>/<i>identifier</i>/versions/<i>nr</i>
	 */
	public static final String PROJECTS_SEGMENT = "projects";

	/**
	 * The EMFStore URI segment for a version.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/projects/<i>identifier</i>/<b>versions</b>/<i>nr</i>
	 */
	public static final String VERSIONS_SEGMENT = "versions";

	/**
	 * The EMFStore URI segment for a changepackage.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/projects/<i>identifier</i>/<b>changepackages</b>/<i>nr</i>
	 */
	public static final String CHANGEPACKAGES_SEGMENT = VersionImpl.CHANGEPACKAGES_SEGMENT;

	/**
	 * The EMFStore URI segment for a projectstate.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/projects/<i>identifier</i>/<b>projectstates</b>/<i>nr</i>
	 */
	public static final String PROJECTSTATES_SEGMENT = VersionImpl.PROJECTSTATES_SEGMENT;

	/**
	 * The EMFStore URI segment for the serverspace.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/<b>serverspace</b>
	 */
	public static final String SERVERSPACE_SEGMENT = "serverspace";

	/**
	 * The EMFStore URI segment for a project's history.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/projects/<i>identifier</i>/<b>projecthistory</b>
	 */
	public static final String PROJECTHISTORY_SEGMENT = "projecthistory";

	private ServerURIUtil() {
		// private constructor
	}

	/**
	 * Creates an EMFStore URI for addressing the serverspace.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/serverspace
	 * 
	 * @return the EMFStore URI
	 */
	public static URI createServerSpaceURI() {
		return URI.createURI(getServerPrefix() + SERVERSPACE_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing the history of a project.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/projects/<i>identifier</i>/projecthistory
	 * 
	 * @param projectId the project id
	 * @return the EMFStore URI
	 */
	public static URI createProjectHistoryURI(ProjectId projectId) {
		return URI.createURI(getProjectsPrefix(projectId) + PROJECTHISTORY_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing a specific version of a project.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/projects/<i>identifier</i>/versions/<i>nr</i>
	 * 
	 * @param projectId the project id
	 * @param versionId the version id
	 * @return the EMFStore URI
	 */
	public static URI createVersionURI(ProjectId projectId, PrimaryVersionSpec versionId) {
		return URI.createURI(getProjectsPrefix(projectId) + VERSIONS_SEGMENT + "/"
			+ versionId.getIdentifier());
	}

	/**
	 * Creates an EMFStore URI for addressing a specific changepackage of a project.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/projects/<i>identifier</i>/changepackages/<i>nr</i>
	 * 
	 * @param projectId the project id
	 * @param versionId the version id
	 * @return the EMFStore URI
	 */
	public static URI createChangePackageURI(ProjectId projectId, PrimaryVersionSpec versionId) {
		return URI.createURI(getProjectsPrefix(projectId) + CHANGEPACKAGES_SEGMENT + "/" + versionId.getIdentifier());
	}

	/**
	 * Creates an EMFStore URI for addressing a specific state of a project.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<i>profile</i>/projects/<i>identifier</i>/projectstates/<i>nr</i>
	 * 
	 * @param projectId the project id
	 * @param versionId the version id
	 * @return the EMFStore URI
	 */
	public static URI createProjectStateURI(ProjectId projectId, PrimaryVersionSpec versionId) {
		return URI.createURI(getProjectsPrefix(projectId) + PROJECTSTATES_SEGMENT + "/" + versionId.getIdentifier());
	}

	/**
	 * Returns the used profile.
	 * <p />
	 * Example URI: emfstore:/serverspaces/<b><i>profile</i></b>/projects/<i>identifier</i>/versions/<i>nr</i>
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

	private static String getProjectsPrefix(ProjectId projectId) {
		return getServerPrefix() + PROJECTS_SEGMENT + "/" + projectId.getId() + "/";
	}

}
