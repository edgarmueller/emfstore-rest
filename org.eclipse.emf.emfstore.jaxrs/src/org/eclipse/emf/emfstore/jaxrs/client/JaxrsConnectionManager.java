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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;

/**
 * @author Pascal
 * 
 */
public class JaxrsConnectionManager {

	private static final String PATH_PROJECTS = "projects";
	private static String BASE_URI = "http://localhost:8080/services";

	private final WebTarget target;

	public JaxrsConnectionManager() {

		final Client client = ClientBuilder.newClient();
		target = client.target(BASE_URI).path("emfstore");
	}

	public List<ProjectInfo> getProjectList() {
		// TODO: implement!!!
		final Response response = target.path(PATH_PROJECTS).request(MediaType.TEXT_PLAIN).get();

		return null;
	}

	public Project getProject(ProjectId projectId, VersionSpec versionSpec) {
		// TODO: implement!

		return null;
	}

}
