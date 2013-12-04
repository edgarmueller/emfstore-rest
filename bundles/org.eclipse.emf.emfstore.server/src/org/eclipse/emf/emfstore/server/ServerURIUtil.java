/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.impl.VersionImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Helper class for creating EMFStore Server URIs and accessing segments.
 * 
 * @author jfaltermeier
 * @since 1.1
 * 
 */
public final class ServerURIUtil {

	/**
	 * The EMFStore URI scheme.
	 * <p />
	 * Example URI: <b>emfstore</b>://workspaces/0/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String SCHEME = "emfstore"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for serverspaces.
	 * <p />
	 * Example URI: emfstore://<b>serverspaces</b>/<i>profile</i>/serverspace
	 */
	public static final String SERVER_SEGMENT = "serverspaces"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for projects.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/<b>projects</b>/<i>identifier</i>/versions/<i>nr</i>
	 */
	public static final String PROJECTS_SEGMENT = "projects"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for a version.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/projects/<i>identifier</i>/<b>versions</b>/<i>nr</i>
	 */
	public static final String VERSIONS_SEGMENT = "versions"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for a changepackage.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/projects/<i>identifier</i>/<b>changepackages</b>/<i>nr</i>
	 */
	public static final String CHANGEPACKAGES_SEGMENT = VersionImpl.CHANGEPACKAGES_SEGMENT;

	/**
	 * The EMFStore URI segment for a projectstate.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/projects/<i>identifier</i>/<b>projectstates</b>/<i>nr</i>
	 */
	public static final String PROJECTSTATES_SEGMENT = VersionImpl.PROJECTSTATES_SEGMENT;

	/**
	 * The EMFStore URI segment for the serverspace.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/<b>serverspace</b>
	 */
	public static final String SERVERSPACE_SEGMENT = "serverspace"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for a project's history.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/projects/<i>identifier</i>/<b>projecthistory</b>
	 */
	public static final String PROJECTHISTORY_SEGMENT = "projecthistory"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for dynamic models.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/<b>dynamic-models</b>
	 */
	public static final String DYNAMIC_MODELS_SEGMENT = "dynamic-models"; //$NON-NLS-1$

	private ServerURIUtil() {
		// private constructor
	}

	/**
	 * Creates an EMFStore URI for addressing the serverspace.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/serverspace
	 * 
	 * @return the EMFStore URI
	 */
	public static URI createServerSpaceURI() {
		return URI.createURI(getServerPrefix() + SERVERSPACE_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing a dynamic model.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/dynamic-models/example.ecore
	 * 
	 * @param ePackage the ePackage
	 * @return the EMFStore URI
	 * @throws ESException if namespace Uri can't be converted to filename
	 */
	public static URI createDynamicModelsURI(EPackage ePackage) throws ESException {
		String uriFileName = null;
		try {
			uriFileName = URLEncoder.encode(ePackage.getNsURI(), CommonUtil.getEncoding());
		} catch (final UnsupportedEncodingException e1) {
			throw new ESException("Registration failed: Could not convert NsUri to filename!");
		}
		return URI.createURI(getServerPrefix() + DYNAMIC_MODELS_SEGMENT + "/" + uriFileName //$NON-NLS-1$
			+ (uriFileName.endsWith(".ecore") ? "" : ".ecore")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Creates an EMFStore URI for addressing the history of a project.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/projects/<i>identifier</i>/projecthistory
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
	 * Example URI: emfstore://serverspaces/<i>profile</i>/projects/<i>identifier</i>/versions/<i>nr</i>
	 * 
	 * @param projectId the project id
	 * @param versionId the version id
	 * @return the EMFStore URI
	 */
	public static URI createVersionURI(ProjectId projectId, PrimaryVersionSpec versionId) {
		return URI.createURI(getProjectsPrefix(projectId) + VERSIONS_SEGMENT + "/" //$NON-NLS-1$
			+ versionId.getIdentifier());
	}

	/**
	 * Creates an EMFStore URI for addressing a specific changepackage of a project.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/projects/<i>identifier</i>/changepackages/<i>nr</i>
	 * 
	 * @param projectId the project id
	 * @param versionId the version id
	 * @return the EMFStore URI
	 */
	public static URI createChangePackageURI(ProjectId projectId, PrimaryVersionSpec versionId) {
		return URI.createURI(getProjectsPrefix(projectId) + CHANGEPACKAGES_SEGMENT + "/" + versionId.getIdentifier()); //$NON-NLS-1$
	}

	/**
	 * Creates an EMFStore URI for addressing a specific state of a project.
	 * <p />
	 * Example URI: emfstore://serverspaces/<i>profile</i>/projects/<i>identifier</i>/projectstates/<i>nr</i>
	 * 
	 * @param projectId the project id
	 * @param versionId the version id
	 * @return the EMFStore URI
	 */
	public static URI createProjectStateURI(ProjectId projectId, PrimaryVersionSpec versionId) {
		return URI.createURI(getProjectsPrefix(projectId) + PROJECTSTATES_SEGMENT + "/" + versionId.getIdentifier()); //$NON-NLS-1$
	}

	/**
	 * Returns the used profile.
	 * <p />
	 * Example URI: emfstore://serverspaces/<b><i>profile</i></b>/projects/<i>identifier</i>/versions/<i>nr</i>
	 * 
	 * @return the profile
	 */
	public static String getProfile() {
		String parameter = ServerConfiguration.getStartArgument("-profile"); //$NON-NLS-1$
		if (parameter == null) {
			parameter = "default"; //$NON-NLS-1$
			if (ServerConfiguration.isTesting()) {
				parameter += "_test"; //$NON-NLS-1$
			} else if (!ServerConfiguration.isReleaseVersion()) {
				if (ServerConfiguration.isInternalReleaseVersion()) {
					parameter += "_internal"; //$NON-NLS-1$
				} else {
					parameter += "_dev"; //$NON-NLS-1$
				}
			}
		}
		return parameter;
	}

	private static String getServerPrefix() {
		return SCHEME + "://" + SERVER_SEGMENT + "/" + getProfile() + "/"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private static String getProjectsPrefix(ProjectId projectId) {
		return getServerPrefix() + PROJECTS_SEGMENT + "/" + projectId.getId() + "/"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
