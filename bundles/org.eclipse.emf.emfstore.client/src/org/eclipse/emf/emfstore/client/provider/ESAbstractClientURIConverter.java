/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.provider;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.emfstore.client.util.ClientURIUtil;

/**
 * Abstract URIConverter for normalizing EMFStore URIs on client side. Delegates normalizing to specialized methods
 * which have to be implemented by extenders.
 * 
 * @author jfaltermeier
 * 
 */
public abstract class ESAbstractClientURIConverter extends ExtensibleURIConverterImpl {

	@Override
	public URI normalize(URI uri) {
		// emfstore:
		if (uri.scheme() != null && uri.scheme().equals(ClientURIUtil.SCHEME)) {

			// emfstore://workspaces/0
			if (uri.authority().equals(ClientURIUtil.CLIENT_SEGMENT)) {

				// emfstore://workspaces/0/workspace
				if (uri.segment(1).equals(ClientURIUtil.WORKSPACE_SEGMENT)) {
					return normalizeWorkspaceURI(uri.segment(0));
				}

				// emfstore://workspaces/0/projectspaces/<identifier>
				else if (uri.segment(1).equals(ClientURIUtil.PROJECTSPACES_SEGMENT)) {
					return normalizeProjectSpaces(uri);
				}
			}
		}

		// unexpected
		return super.normalize(uri);
	}

	private URI normalizeProjectSpaces(URI uri) {
		// emfstore://workspaces/0/projectspaces/<identifier>/project
		if (uri.segment(3).equals(ClientURIUtil.PROJECT_SEGMENT)) {
			return normalizeProjectURI(uri.segment(0), uri.segment(2));
		}

		// emfstore://workspaces/0/projectspaces/<identifier>/operations
		else if (uri.segment(3).equals(ClientURIUtil.OPERATIONS_SEGMENT)) {
			return normalizeOperationsURI(uri.segment(0), uri.segment(2));
		}

		// emfstore://workspaces/0/projectspaces/<identifier>/projectspace
		else if (uri.segment(3).equals(ClientURIUtil.PROJECTSPACE_SEGMENT)) {
			return normalizeProjectSpaceURI(uri.segment(0), uri.segment(2));
		}

		// unexpected
		else {
			return super.normalize(uri);
		}
	}

	/**
	 * Normalizes an EMFStore workspace URI.
	 * 
	 * @param profile the selected profile
	 * @return the normalized URI
	 */
	protected abstract URI normalizeWorkspaceURI(String profile);

	/**
	 * Normalizes an EMFStore project URI.
	 * 
	 * @param profile the selected profile
	 * @param projectId the project's id
	 * @return the normalized URI
	 */
	protected abstract URI normalizeProjectURI(String profile, String projectId);

	/**
	 * Normalizes an EMFStore operations URI.
	 * 
	 * @param profile the selected profile
	 * @param projectId the project's id
	 * @return the normalized URI
	 */
	protected abstract URI normalizeOperationsURI(String profile, String projectId);

	/**
	 * Normalizes an EMFStore projectspace URI.
	 * 
	 * @param profile the selected profile
	 * @param projectId the project's id
	 * @return the normalized URI
	 */
	protected abstract URI normalizeProjectSpaceURI(String profile, String projectId);

}
