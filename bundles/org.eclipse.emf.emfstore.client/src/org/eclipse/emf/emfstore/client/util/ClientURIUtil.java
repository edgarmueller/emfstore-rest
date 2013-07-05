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
package org.eclipse.emf.emfstore.client.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.emfstore.server.ServerURIUtil;

/**
 * Helper class for EMFStore Client URIs and accessing segments.
 * 
 * @author jfaltermeier
 */
public final class ClientURIUtil {

	/**
	 * The EMFStore URI scheme.
	 * <p />
	 * Example URI: <b>emfstore</b>:/workspaces/<i>profile</i>/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String SCHEME = ServerURIUtil.SCHEME;

	/**
	 * The EMFStore URI segment for client workspaces.
	 * <p />
	 * Example URI: emfstore:/<b>workspaces</b>/<i>profile</i>/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String CLIENT_SEGMENT = "workspaces";

	/**
	 * The EMFStore URI segment for projectspaces.
	 * <p />
	 * Example URI: emfstore:/workspaces/<i>profile</i>/<b>projectspaces</b>/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String PROJECTSPACES_SEGMENT = "projectspaces";

	/**
	 * The EMFStore URI segment for the workspace.
	 * <p />
	 * Example URI: emfstore:/workspaces/<i>profile</i>/<b>workspace</b>
	 */
	public static final String WORKSPACE_SEGMENT = "workspace";

	/**
	 * The EMFStore URI segment for a project.
	 * <p />
	 * Example URI: emfstore:/workspaces/<i>profile</i>/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/<b>project</b>
	 */
	public static final String PROJECT_SEGMENT = "project";

	/**
	 * The EMFStore URI segment for a projectspace.
	 * <p />
	 * Example URI: emfstore:/workspaces/<i>profile</i>/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/<b>projectspace</b>
	 */
	public static final String PROJECTSPACE_SEGMENT = "projectspace";

	/**
	 * The EMFStore URI segment for a project's operations.
	 * <p />
	 * Example URI: emfstore:/workspaces/<i>profile</i>/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/<b>operations</b>
	 */
	public static final String OPERATIONS_SEGMENT = "operations";

	private ClientURIUtil() {
		// private constructor
	}

	/**
	 * Creates an EMFStore URI for addressing the client's workspace.
	 * <p />
	 * Example URI: emfstore:/workspaces/<i>profile</i>/workspace
	 * 
	 * @return the EMFStore URI
	 */
	public static URI createWorkspaceURI() {
		return URI.createURI(getClientPrefix() + WORKSPACE_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing the project fragment of a projectspace.
	 * <p />
	 * Example URI: emfstore:/workspaces/<i>profile</i>/projectspaces/<i>identifier</i>/project
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
	 * Example URI: emfstore:/workspaces/<i>profile</i>/projectspaces/<i>identifier</i>/operations
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
	 * Example URI: emfstore:/workspaces/<i>profile</i>/projectspaces/<i>identifier</i>/projectspace
	 * 
	 * @param identifier the project's id
	 * @return the EMFStore URI
	 */
	public static URI createProjectSpaceURI(String identifier) {
		return URI.createURI(getProjectspacesPrefix(identifier) + PROJECTSPACE_SEGMENT);
	}

	private static String getClientPrefix() {
		return SCHEME + ":/" + CLIENT_SEGMENT + "/" + ServerURIUtil.getProfile() + "/";
	}

	private static String getProjectspacesPrefix(String identifier) {
		return getClientPrefix() + PROJECTSPACES_SEGMENT + "/" + identifier + "/";
	}

}
