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
package org.eclipse.emf.emfstore.client.mongodb;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.emfstore.common.URIUtil;

/**
 * Abstract URIConverter for normalizing EMFStore URIs on client side. Delegates normalizing to specialized methods
 * which have to be implemented by extenders.
 * 
 * @author jfaltermeier
 * 
 */
public abstract class AbstractESClientURIConverter extends ExtensibleURIConverterImpl {

	@Override
	public URI normalize(URI uri) {
		// emfstore:
		if (uri.scheme() != null && uri.scheme().equals(URIUtil.SCHEME)) {

			// emfstore:/workspaces/0
			if (uri.segment(0).equals(URIUtil.CLIENT_SEGMENT)) {

				// emfstore:/workspaces/0/workspace
				if (uri.segment(2).equals(URIUtil.WORKSPACE_SEGMENT)) {
					return normalizeWorkspaceURI(uri);
				}

				// emfstore:/workspaces/0/projectspaces/<identifier>
				else if (uri.segment(2).equals(URIUtil.PROJECTSPACES_SEGMENT)) {
					return normalizeProjectSpaces(uri);
				}
			}
		}

		// TODO maybe throw exception?
		// unexpected
		return super.normalize(uri);
	}

	private URI normalizeProjectSpaces(URI uri) {
		// emfstore:/workspaces/0/projectspaces/<identifier>/project
		if (uri.segment(4).equals(URIUtil.PROJECT_SEGMENT)) {
			return normalizeProjectURI(uri);
		}

		// emfstore:/workspaces/0/projectspaces/<identifier>/operations
		else if (uri.segment(4).equals(URIUtil.OPERATIONS_SEGMENT)) {
			return normalizeOperationsURI(uri);
		}

		// emfstore:/workspaces/0/projectspaces/<identifier>/projectspace
		else if (uri.segment(4).equals(URIUtil.PROJECTSPACE_SEGMENT)) {
			return normalizeProjectSpaceURI(uri);
		}

		// unexpected
		else {
			return super.normalize(uri);
		}
	}

	/**
	 * Normalizes an EMFStore workspace URI.
	 * 
	 * @param uri the EMFStore URI
	 * @return the normalized URI
	 */
	protected abstract URI normalizeWorkspaceURI(URI uri);

	/**
	 * Normalizes an EMFStore project URI.
	 * 
	 * @param uri the EMFStore URI
	 * @return the normalized URI
	 */
	protected abstract URI normalizeProjectURI(URI uri);

	/**
	 * Normalizes an EMFStore operations URI.
	 * 
	 * @param uri the EMFStore URI
	 * @return the normalized URI
	 */
	protected abstract URI normalizeOperationsURI(URI uri);

	/**
	 * Normalizes an EMFStore projectspace URI.
	 * 
	 * @param uri the EMFStore URI
	 * @return the normalized URI
	 */
	protected abstract URI normalizeProjectSpaceURI(URI uri);

}
