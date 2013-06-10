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
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.emfstore.common.URIUtil;

/**
 * Abstract URIConverter for normalizing EMFStore URIs on server side. Delegates normalizing to specialized methods
 * which have to be implemented by extenders.
 * 
 * @author jfaltermeier
 * 
 */
public abstract class AbstractESServerURIConverter extends ExtensibleURIConverterImpl {

	@Override
	public URI normalize(URI uri) {
		// emfstore:
		if (uri.scheme() != null && uri.scheme().equals(URIUtil.SCHEME)) {

			// emfstore:/serverspaces/0
			if (uri.segment(0).equals(URIUtil.SERVER_SEGMENT)) {

				// emfstore:/serverspaces/0/serverspace
				if (uri.segment(2).equals(URIUtil.SERVERSPACE_SEGMENT)) {
					return normalizeServerSpaceURI();
				}

				// emfstore:/serverspaces/0/projects/<identifier>
				else if (uri.segment(2).equals(URIUtil.PROJECTS_SEGMENT)) {
					return normalizeProjects(uri);
				}
			}
		}

		// unexpected
		return super.normalize(uri);
	}

	private URI normalizeProjects(URI uri) {
		// emfstore:/serverspaces/0/projects/<identifier>/projecthistory
		if (uri.segment(4).equals(URIUtil.PROJECTHISTORY_SEGMENT)) {
			return normalizeProjectHistoryURI(uri.segment(3));
		}

		// emfstore:/serverspaces/0/projects/<identifier>/versions/<nr>
		else if (uri.segment(4).equals(URIUtil.VERSIONS_SEGMENT)) {
			return normalizeVersionURI(uri.segment(3), Integer.valueOf(uri.segment(5)));
		}

		// emfstore:/serverspaces/0/projects/<identifier>/changepackages/<nr>
		else if (uri.segment(4).equals(URIUtil.CHANGEPACKAGES_SEGMENT)) {
			return normalizeChangePackageURI(uri.segment(3), Integer.valueOf(uri.segment(5)));
		}

		// emfstore:/serverspaces/0/projects/<identifier>/projectstates/<nr>
		else if (uri.segment(4).equals(URIUtil.PROJECTSTATES_SEGMENT)) {
			return normalizeProjectStateURI(uri.segment(3), Integer.valueOf(uri.segment(5)));
		}

		// unexpected
		else {
			return super.normalize(uri);
		}
	}

	/**
	 * Normalizes an EMFStore serverspace URI.
	 * 
	 * @return the normalized URI
	 */
	protected abstract URI normalizeServerSpaceURI();

	/**
	 * Normalizes an EMFStore projecthistory URI.
	 * 
	 * @param projectId the project's id
	 * @return the normalized URI
	 */
	protected abstract URI normalizeProjectHistoryURI(String projectId);

	/**
	 * Normalizes an EMFStore version URI.
	 * 
	 * @param projectId the project's id
	 * @param version the version
	 * @return the normalized URI
	 */
	protected abstract URI normalizeVersionURI(String projectId, int version);

	/**
	 * Normalizes an EMFStore changepackage URI.
	 * 
	 * @param projectId the project's id
	 * @param version the version
	 * @return the normalized URI
	 */
	protected abstract URI normalizeChangePackageURI(String projectId, int version);

	/**
	 * Normalizes an EMFStore projectstate URI.
	 * 
	 * @param projectId the project's id
	 * @param version the version
	 * @return the normalized URI
	 */
	protected abstract URI normalizeProjectStateURI(String projectId, int version);

}
