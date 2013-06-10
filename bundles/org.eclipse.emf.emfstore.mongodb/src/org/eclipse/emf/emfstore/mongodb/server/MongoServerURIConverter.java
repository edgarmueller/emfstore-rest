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
import org.eclipse.emf.emfstore.internal.server.storage.AbstractESServerURIConverter;

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
	protected URI normalizeServerSpaceURI() {
		return URI.createURI(getMongoURIPrefix() + "serverspaces/serverspace");
	}

	@Override
	protected URI normalizeProjectHistoryURI(String projectId) {
		return URI.createURI(getMongoURIPrefix() + "projecthistory/" + projectId);
	}

	@Override
	protected URI normalizeVersionURI(String projectId, int version) {
		return URI.createURI(getMongoURIPrefix() + "version/" + projectId + "-v" + version);
	}

	@Override
	protected URI normalizeChangePackageURI(String projectId, int version) {
		return URI.createURI(getMongoURIPrefix() + "changepackage/" + projectId + "-v" + version);
	}

	@Override
	protected URI normalizeProjectStateURI(String projectId, int version) {
		return URI.createURI(getMongoURIPrefix() + "projectstate/" + projectId + "-v" + version);
	}

	private String getMongoURIPrefix() {
		return "mongodb://localhost/emfstoreserver/";
	}

}
