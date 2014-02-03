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

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl;
import org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.internal.server.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * @author Pascal
 * 
 */

@Path("emfstore/projects")
public class Projects {

	private final EMFStore emfStore;
	private final AccessControl accessControl;

	/**
	 * @param emfStore
	 * @param accessControl
	 */
	public Projects(EMFStore emfStore, AccessControl accessControl) {
		// TODO Auto-generated constructor stub
		this.emfStore = emfStore;
		this.accessControl = accessControl;
	}

	@SuppressWarnings("restriction")
	@GET
	@Produces({ MediaType.TEXT_XML })
	// TODO: maybe application/xml instead?
	public Response getProjectList() throws ESException {
		// TODO implement!

		// final EMFStore emfstore = null; not needed because we have class variable
		// final AccessControl ac = null; not needed because we have class variable

		// get a Session id //TODO: not RESTful!
		final String superuser = ServerConfiguration.getProperties().getProperty(ServerConfiguration.SUPER_USER,
			ServerConfiguration.SUPER_USER_DEFAULT);
		final String superuserpw = ServerConfiguration.getProperties().getProperty(
			ServerConfiguration.SUPER_USER_PASSWORD,
			ServerConfiguration.SUPER_USER_PASSWORD_DEFAULT);
		final AuthenticationInformation logIn = accessControl.logIn(superuser, superuserpw,
			ModelFactory.eINSTANCE.createClientVersionInfo());
		final SessionId sessionId = logIn.getSessionId();

		// get the projectList
		final java.util.List<ProjectInfo> projects = emfStore.getProjectList(sessionId);

		// convert the list into XML
		final AdapterFactoryEditingDomain domain =
			new AdapterFactoryEditingDomain(new AdapterFactoryImpl(), new BasicCommandStack());

		final String fileNameURI = "blabla";
		final XMLResourceImpl resource = (XMLResourceImpl) domain.createResource(fileNameURI);

		final StreamingOutput streamingOutput = new StreamingOutput() {

			public void write(OutputStream output) throws IOException, WebApplicationException {

				resource.doSave(output, null);

			}
		};

		// return the Response
		return Response.ok(streamingOutput).build();
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
