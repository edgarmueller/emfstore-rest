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
package org.eclipse.emf.emfstore.common;

import org.eclipse.emf.common.util.URI;

/**
 * Helper class for creating EMFStore URIs and accessing segments.
 * 
 * @author jfaltermeier
 * 
 */
public final class URIUtil {
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
	 * The EMFStore URI segment for client workspaces.
	 * <p />
	 * Example URI: emfstore:/<b>workspaces</b>/0/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String CLIENT_SEGMENT = "workspaces";

	/**
	 * The EMFStore URI segment for projectspaces.
	 * <p />
	 * Example URI: emfstore:/workspaces/0/<b>projectspaces</b>/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String PROJECTSPACES_SEGMENT = "projectspaces";

	/**
	 * The EMFStore URI segment for the workspace.
	 */
	public static final String WORKSPACE_SEGMENT = "workspace";

	/**
	 * The EMFStore URI segment for a project.
	 * <p />
	 * Example URI: emfstore:/workspaces/0/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/<b>project</b>
	 */
	public static final String PROJECT_SEGMENT = "project";

	/**
	 * The EMFStore URI segment for a projectspace.
	 * <p />
	 * Example URI: emfstore:/workspaces/0/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/<b>projectspace</b>
	 */
	public static final String PROJECTSPACE_SEGMENT = "projectspace";

	/**
	 * The EMFStore URI segment for a project's operations.
	 * <p />
	 * Example URI: emfstore:/workspaces/0/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/<b>operations</b>
	 */
	public static final String OPERATIONS_SEGMENT = "operations";

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
	public static final String CHANGEPACKAGES_SEGMENT = "changepackages";

	/**
	 * The EMFStore URI segment for a projectstate.
	 */
	public static final String PROJECTSTATES_SEGMENT = "projectstates";

	/**
	 * The EMFStore URI segment for the serverspace.
	 */
	public static final String SERVERSPACE_SEGMENT = "serverspace";

	/**
	 * The EMFStore URI segment for a project's history.
	 */
	public static final String PROJECTHISTORY_SEGMENT = "projecthistory";

	private URIUtil() {
		// private constructor
	}

	/**
	 * Creates an EMFStore URI for addressing the client's workspace.
	 * 
	 * @return the EMFStore URI
	 */
	public static URI createWorkspaceURI() {
		return URI.createURI(getClientPrefix() + WORKSPACE_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing the project fragment of a projectspace.
	 * <p />
	 * Example URI: emfstore:/workspaces/0/projectspaces/<i>identifier</i>/project
	 * 
	 * @param identifier the projectspace's id
	 * @return the EMFStore URI
	 */
	public static URI createProjectURI(String identifier) {
		return URI.createURI(getProjectspacesPrefix(identifier) + PROJECT_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing the operations of a projectspace.
	 * <p />
	 * Example URI: emfstore:/workspaces/0/projectspaces/<i>identifier</i>/operations
	 * 
	 * @param identifier the projectspace's id
	 * @return the EMFStore URI
	 */
	public static URI createOperationsURI(String identifier) {
		return URI.createURI(getProjectspacesPrefix(identifier) + OPERATIONS_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing the projectspace fragment of a project.
	 * <p />
	 * Example URI: emfstore:/workspaces/0/projectspaces/<i>identifier</i>/projectspace
	 * 
	 * @param identifier the project's id
	 * @return the EMFStore URI
	 */
	public static URI createProjectSpaceURI(String identifier) {
		return URI.createURI(getProjectspacesPrefix(identifier) + PROJECTSPACE_SEGMENT);
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

	private static String getClientPrefix() {
		return SCHEME + ":/" + CLIENT_SEGMENT + "/0/";
	}

	private static String getProjectspacesPrefix(String identifier) {
		return getClientPrefix() + PROJECTSPACES_SEGMENT + "/" + identifier + "/";
	}

	private static String getServerPrefix() {
		return SCHEME + ":/" + SERVER_SEGMENT + "/0/";
	}

	private static String getProjectsPrefix(String identifier) {
		return getServerPrefix() + PROJECTS_SEGMENT + "/" + identifier + "/";
	}

}
