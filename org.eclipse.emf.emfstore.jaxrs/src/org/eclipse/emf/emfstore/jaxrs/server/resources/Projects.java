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
package org.eclipse.emf.emfstore.jaxrs.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author Pascal
 * 
 */

@Path("emfstore/projects")
public class Projects {

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	public Response getProjectList() {
		// TODO implement!

		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Path("/{projectId}")
	@Produces({ MediaType.APPLICATION_XML })
	public Response getProject(@PathParam("projectId") String projectId) {
		// TODO implement!

		return Response.status(Status.NOT_IMPLEMENTED).build();

	}

	@POST
	public void createProject() {

	}

}
