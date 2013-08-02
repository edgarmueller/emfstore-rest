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
package org.eclipse.emf.emfstore.mongodb.client;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.emfstore.client.provider.ESAbstractClientURIConverter;

/**
 * Converts from EMFStore to mongoDB URIs.
 * <p />
 * mongoDB URIs are expected to be of form: <br />
 * mongodb://host[:port]/database/collection/{id}
 * 
 * @author jfaltermeier
 * 
 */
public class MongoClientURIConverter extends ESAbstractClientURIConverter {

	@Override
	protected URI normalizeWorkspaceURI(String profile) {
		return URI.createURI(getMongoURIPrefix(profile) + "workspace/workspace");
	}

	@Override
	protected URI normalizeProjectURI(String profile, String projectId) {
		return URI.createURI(getMongoURIPrefix(profile) + "project/" + projectId);
	}

	@Override
	protected URI normalizeOperationsURI(String profile, String projectId) {
		return URI.createURI(getMongoURIPrefix(profile) + "operations/" + projectId);
	}

	@Override
	protected URI normalizeProjectSpaceURI(String profile, String projectId) {
		return URI.createURI(getMongoURIPrefix(profile) + "projectspace/" + projectId);
	}

	private String getMongoURIPrefix(String profile) {
		return "mongodb://localhost/esclient-" + profile + "/";
	}

}
