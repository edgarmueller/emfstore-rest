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
package org.eclipse.emf.emfstore.jax.server.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.impl.ProjectImpl;
import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl;
import org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.internal.server.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.impl.ProjectIdImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.impl.VersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.impl.VersioningFactoryImpl;
import org.eclipse.emf.emfstore.jax.common.CallParamStrings;
import org.eclipse.emf.emfstore.jax.common.ProjectDataTO;
import org.eclipse.emf.emfstore.server.ESServerURIUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * @author Pascal
 * 
 */
//TODO: do error handling for all {pathParam} stuff where this pathParam is not valid!!!
//TODO: refactor using CallParamStrings everywhere where possible!
@Path(CallParamStrings.PROJECTS_PATH)
public class Projects extends JaxrsResource {

	@SuppressWarnings("restriction")
	@GET
	@Produces({ MediaType.TEXT_XML })
	// TODO: maybe application/xml instead?
	public Response getProjectList() throws ESException {

		if (emfStore == null || accessControl == null) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		// final EMFStore emfstore = null; not needed because we have class
		// variable
		// final AccessControl ac = null; not needed because we have class
		// variable

		// get a Session id //TODO: not RESTful!
		/*
		final String superuser = ServerConfiguration.getProperties()
				.getProperty(ServerConfiguration.SUPER_USER,
						ServerConfiguration.SUPER_USER_DEFAULT);
		final String superuserpw = ServerConfiguration.getProperties()
				.getProperty(ServerConfiguration.SUPER_USER_PASSWORD,
						ServerConfiguration.SUPER_USER_PASSWORD_DEFAULT);
		final AuthenticationInformation logIn = accessControl.logIn(superuser,
				superuserpw, ModelFactory.eINSTANCE.createClientVersionInfo());
		final SessionId sessionId = logIn.getSessionId();
		*/

		// get the projectList
		final java.util.List<ProjectInfo> projects = emfStore
				.getProjectList(retrieveSessionId());
		
		final StreamingOutput streamingOutput = convertEObjectsToXmlIntoStreamingOutput(projects);

		// return the Response
		return Response.ok(streamingOutput).build();
	}

	

	@GET
	@Path("/{projectId}")
	@Produces({ MediaType.TEXT_XML })
	public Response getProject(@PathParam("projectId") String projectIdAsString, @QueryParam("versionSpec") String versionSpecAsString) {
		
		if (emfStore == null || accessControl == null) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		//create ProjectId and VersionSpec objects
		ProjectId projectId = ModelFactory.eINSTANCE.createProjectId();
		projectId.setId(projectIdAsString);
		
		//TODO: adjust so that it not only supports PrimaryVersionSpec
		VersionSpec versionSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
		versionSpec.setBranch(versionSpecAsString);
		
		//make call to emfstore
		Project project = null;
		try {
			project = emfStore.getProject(retrieveSessionId(), projectId, versionSpec);
		} catch (ESException e) {
			e.printStackTrace();
			return Response.serverError().build();
		} 
		
		if(project == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		//create ProjectDataTO
		ProjectDataTO projectDataTO = new ProjectDataTO(null, null, null, project);
		
		return Response.ok(projectDataTO).build();

	}

	@POST
	@Consumes({ MediaType.TEXT_XML })
	@Produces({ MediaType.TEXT_XML })
	public Response createProject(ProjectDataTO projectDataTO) {
		
		if (emfStore == null || accessControl == null) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		System.out.println("\n\nProjects.createProject invoked...\n\n");
		
		//extract the received data
		String name = projectDataTO.getName();
		String description = projectDataTO.getDescription();
		LogMessage logMessage = projectDataTO.getLogMessage();
		Project project = projectDataTO.getProject();
		
		//make call to EmfStore
		ProjectInfo projectInfo = null;
		if(project == null) {
			//user wants to create an empty project
			try {
				projectInfo = emfStore.createEmptyProject(retrieveSessionId(), name, description, logMessage);
			} catch (ESException e) {
				e.printStackTrace();
				return Response.serverError().build();
			}
		} else {
			//user wants to create a non-empty project
			try {
				projectInfo = emfStore.createProject(retrieveSessionId(), name, description, logMessage, project);
			} catch (ESException e) {
				e.printStackTrace();
				return Response.serverError().build();
			}
		}
		
		//create a proper response which contains: URI of the created project + its projectInfo
		String projectId = projectInfo.getProjectId().getId(); //TODO: change!
		java.net.URI createdUri;
		try {
			createdUri = new java.net.URI(CallParamStrings.BASE_URI + CallParamStrings.PROJECTS_PATH + "/" + projectId);  //TODO: is projectID URL-encoded-safe??!
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		List<ProjectInfo> projectInfoList = new ArrayList<ProjectInfo>();
		projectInfoList.add(projectInfo);
		final StreamingOutput streamingOutput = convertEObjectsToXmlIntoStreamingOutput(projectInfoList);
		
		return Response.created(createdUri).entity(streamingOutput).build();
				
	}
	
	@DELETE
	@Path("/{projectId}")
	public Response deleteProject(@PathParam("projectId") String projectIdAsString, @QueryParam("deleteFiles") boolean deleteFiles) {
		
		if (emfStore == null || accessControl == null) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		//create ProjectId
		ProjectId projectId = ModelFactory.eINSTANCE.createProjectId();
		projectId.setId(projectIdAsString);
		
		try {
			emfStore.deleteProject(retrieveSessionId(), projectId, deleteFiles);
		} catch (ESException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/{projectId}" + "/" + CallParamStrings.PROJECTS_PATH_CHANGES)
	@Produces({ MediaType.TEXT_XML })
	public Response getChanges(@PathParam("projectId") String projectIdAsString, @QueryParam("sourceVersionSpec") String sourceVersionSpecAsString, @QueryParam("targetVersionSpec") String targetVersionSpecAsString) {
		
		//create ProjectId and VersionSpecs
		ProjectId projectId = ModelFactory.eINSTANCE.createProjectId();
		projectId.setId(projectIdAsString);
		//TODO: adjust so that it not only supports PrimaryVersionSpec
		VersionSpec source = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
		source.setBranch(sourceVersionSpecAsString);
		VersionSpec target = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
		target.setBranch(targetVersionSpecAsString);
		
		try {
			//get changes from emfStore
			List<ChangePackage> changes = emfStore.getChanges(retrieveSessionId(), projectId, source, target);
			
			//return the list as streaming output
			final StreamingOutput streamingOutput = convertEObjectsToXmlIntoStreamingOutput(changes);
			return Response.ok(streamingOutput).build();
			
		} catch (ESException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
