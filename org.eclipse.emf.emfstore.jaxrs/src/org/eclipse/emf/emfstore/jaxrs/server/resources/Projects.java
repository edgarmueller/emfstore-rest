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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;

/**
 * @author Pascal
 * 
 */

@Path("/emfstore/projects")
public class Projects {

	@GET
	@Produces({ MediaType.APPLICATION_XML })
	public List<ProjectInfo> getProjectList() {
		// TODO

		return null;
	}

	@GET
	@Path("/{projectId}")
	@Produces({ MediaType.APPLICATION_XML })
	public Project getProject(@PathParam("projectId") String projectId) {
		// /TODO

		return null;
	}
}
