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
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.impl.VersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.impl.VersioningFactoryImpl;
import org.eclipse.emf.emfstore.jax.common.ProjectDataTO;
import org.eclipse.emf.emfstore.server.ESServerURIUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * @author Pascal
 * 
 */

@Path(Projects.PROJECTS_PATH)
public class Projects {

	// private final EMFStore emfStore;
	// private final AccessControl accessControl;
	
	public static final String BASE_PATH = "http://localhost:9090/services";
	public static final String PROJECTS_PATH = "/projects";
	
	private EMFStore emfStore;
	private AccessControl accessControl;

	public EMFStore getEmfStore() {
		return emfStore;
	}

	public void setEmfStore(EMFStore emfStore) {
		this.emfStore = emfStore;
	}

	public AccessControl getAccessControl() {
		return accessControl;
	}

	public void setAccessControl(AccessControl accessControl) {
		this.accessControl = accessControl;
	}

	/**
	 * @param emfStore
	 * @param accessControl
	 */
	public Projects(EMFStore emfStore, AccessControl accessControl) {

		this.emfStore = emfStore;
		this.accessControl = accessControl;
	}

	public Projects() {
		// TODO: delete this constructor afterwards!
		emfStore = null;
		accessControl = null;
	}

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

	/**
	 * @return
	 */
	private SessionId retrieveSessionId() {
		SessionId sessionId = ModelFactory.eINSTANCE.createSessionId();
		return sessionId;
	}

	/**
	 * @param eObjects
	 * @return
	 */
	private StreamingOutput convertEObjectsToXmlIntoStreamingOutput(
			final Collection<? extends EObject> eObjects) {
		// convert the list into XML and write it to a StreamingOutput
		ResourceSetImpl resourceSetImpl = new ResourceSetImpl();
		final String fileNameURI = "blabla";
		final XMLResourceImpl resource = (XMLResourceImpl) resourceSetImpl
				.createResource(URI.createURI(fileNameURI));
		resource.getContents().addAll(eObjects);

		final StreamingOutput streamingOutput = new StreamingOutput() {

			public void write(OutputStream output) throws IOException,
					WebApplicationException {

				resource.doSave(output, null);

			}
		};
		return streamingOutput;
	}

	@GET
	@Path("/{projectId}")
	@Produces({ MediaType.TEXT_XML })
	public Response getProject(@PathParam("projectId") String projectIdAsString, @QueryParam("versionSpec") String versionSpecAsString) {
		
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
			createdUri = new java.net.URI(BASE_PATH + PROJECTS_PATH + "/" + projectId);  //TODO: is projectID URL-encoded-safe??!
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		List<ProjectInfo> projectInfoList = new ArrayList<ProjectInfo>();
		projectInfoList.add(projectInfo);
		final StreamingOutput streamingOutput = convertEObjectsToXmlIntoStreamingOutput(projectInfoList);
		
		return Response.created(createdUri).entity(streamingOutput).build();
				
	}

}
