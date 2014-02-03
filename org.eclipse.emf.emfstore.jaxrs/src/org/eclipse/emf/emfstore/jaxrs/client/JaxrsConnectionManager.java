/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Pascal - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.jaxrs.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;

/**
 * @author Pascal
 * 
 */
public class JaxrsConnectionManager {

	private final WebTarget target;

	private static String BASE_URI = "http://localhost:8080/services";

	public JaxrsConnectionManager() {
		final Client client = ClientBuilder.newClient();
		target = client.target(BASE_URI).path("emfstore");
	}

	public List<ProjectInfo> getProjectList() {
		// TODO: implement!

		return null;
	}

	public ProjectInfo createEmptyProject(String name, String description, LogMessage logMessage) {
		// TODO: implement!!!

		return null;
	}

}
