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
package org.eclipse.emf.emfstore.client.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.emfstore.internal.common.model.IdentifiableElement;
import org.eclipse.emf.emfstore.server.ServerURIUtil;

/**
 * Helper class for EMFStore Client URIs and accessing segments.
 * 
 * @author jfaltermeier
 * @since 1.1
 */
public final class ClientURIUtil {

	/**
	 * The EMFStore URI scheme.
	 * <p />
	 * Example URI: <b>emfstore</b>://workspaces/<i>profile</i>/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String SCHEME = ServerURIUtil.SCHEME;

	/**
	 * The EMFStore URI segment for client workspaces.
	 * <p />
	 * Example URI: emfstore://<b>workspaces</b>/<i>profile</i>/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String CLIENT_SEGMENT = "workspaces"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for projectspaces.
	 * <p />
	 * Example URI: emfstore://workspaces/<i>profile</i>/<b>projectspaces</b>/_pWleAMkNEeK_G9uCvLFQ5A/project
	 */
	public static final String PROJECTSPACES_SEGMENT = "projectspaces"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for the workspace.
	 * <p />
	 * Example URI: emfstore://workspaces/<i>profile</i>/<b>workspace</b>
	 */
	public static final String WORKSPACE_SEGMENT = "workspace"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for a project.
	 * <p />
	 * Example URI: emfstore://workspaces/<i>profile</i>/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/<b>project</b>
	 */
	public static final String PROJECT_SEGMENT = "project"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for a projectspace.
	 * <p />
	 * Example URI: emfstore://workspaces/<i>profile</i>/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/<b>projectspace</b>
	 */
	public static final String PROJECTSPACE_SEGMENT = "projectspace"; //$NON-NLS-1$

	/**
	 * The EMFStore URI segment for a project's operations.
	 * <p />
	 * Example URI: emfstore://workspaces/<i>profile</i>/projectspaces/_pWleAMkNEeK_G9uCvLFQ5A/<b>operations</b>
	 */
	public static final String OPERATIONS_SEGMENT = "operations"; //$NON-NLS-1$

	private ClientURIUtil() {
		// private constructor
	}

	/**
	 * Creates an EMFStore URI for addressing the client's workspace.
	 * <p />
	 * Example URI: emfstore://workspaces/<i>profile</i>/workspace
	 * 
	 * @return the EMFStore URI
	 */
	public static URI createWorkspaceURI() {
		return URI.createURI(getClientPrefix() + WORKSPACE_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing the project fragment of a projectspace.
	 * <p />
	 * Example URI: emfstore://workspaces/<i>profile</i>/projectspaces/<i>identifier</i>/project
	 * 
	 * @param projectSpace the ProjectSpace
	 * @return the EMFStore URI
	 */
	public static URI createProjectURI(IdentifiableElement projectSpace) {
		return URI.createURI(getProjectspacesPrefix(projectSpace) + PROJECT_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing the operations of a projectspace.
	 * <p />
	 * Example URI: emfstore://workspaces/<i>profile</i>/projectspaces/<i>identifier</i>/operations
	 * 
	 * @param projectSpace the ProjectSpace
	 * @return the EMFStore URI
	 */
	public static URI createOperationsURI(IdentifiableElement projectSpace) {
		return URI.createURI(getProjectspacesPrefix(projectSpace) + OPERATIONS_SEGMENT);
	}

	/**
	 * Creates an EMFStore URI for addressing the projectspace fragment of a project.
	 * <p />
	 * Example URI: emfstore://workspaces/<i>profile</i>/projectspaces/<i>identifier</i>/projectspace
	 * 
	 * @param projectSpace the ProjectSpace
	 * @return the EMFStore URI
	 */
	public static URI createProjectSpaceURI(IdentifiableElement projectSpace) {
		return URI.createURI(getProjectspacesPrefix(projectSpace) + PROJECTSPACE_SEGMENT);
	}

	private static String getClientPrefix() {
		return SCHEME + "://" + CLIENT_SEGMENT + "/" + ServerURIUtil.getProfile() + "/"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private static String getProjectspacesPrefix(IdentifiableElement projectSpace) {
		return getClientPrefix() + PROJECTSPACES_SEGMENT + "/" + projectSpace.getIdentifier() + "/"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
