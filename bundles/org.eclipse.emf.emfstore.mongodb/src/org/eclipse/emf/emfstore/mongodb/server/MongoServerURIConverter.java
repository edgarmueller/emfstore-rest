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
package org.eclipse.emf.emfstore.mongodb.server;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.emfstore.server.AbstractESServerURIConverter;

/**
 * Converts from EMFStore to mongoDB URIs.
 * <p />
 * mongoDB URIs are expected to be of form: <br />
 * mongodb://host[:port]/database/collection/{id}
 * 
 * @author jfaltermeier
 * 
 */
public class MongoServerURIConverter extends AbstractESServerURIConverter {

	@Override
	protected URI normalizeServerSpaceURI(String profile) {
		return URI.createURI(getMongoURIPrefix(profile) + "serverspaces/serverspace");
	}

	@Override
	protected URI normalizeProjectHistoryURI(String profile, String projectId) {
		return URI.createURI(getMongoURIPrefix(profile) + "projecthistory/" + projectId);
	}

	@Override
	protected URI normalizeVersionURI(String profile, String projectId, int version) {
		return URI.createURI(getMongoURIPrefix(profile) + "version/" + projectId + "-v" + version);
	}

	@Override
	protected URI normalizeChangePackageURI(String profile, String projectId, int version) {
		return URI.createURI(getMongoURIPrefix(profile) + "changepackage/" + projectId + "-v" + version);
	}

	@Override
	protected URI normalizeProjectStateURI(String profile, String projectId, int version) {
		return URI.createURI(getMongoURIPrefix(profile) + "projectstate/" + projectId + "-v" + version);
	}

	private String getMongoURIPrefix(String profile) {
		return "mongodb://localhost/esserver-" + profile + "/";
	}

}
