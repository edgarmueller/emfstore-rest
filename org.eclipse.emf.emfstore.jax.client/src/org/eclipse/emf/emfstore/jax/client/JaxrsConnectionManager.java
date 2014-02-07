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
package org.eclipse.emf.emfstore.jax.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.impl.ProjectInfoImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;

/**
 * @author Pascal
 * 
 */
public class JaxrsConnectionManager {

	private static final String PATH_PROJECTS = "projects";
	private static String BASE_URI = "http://localhost:9090/services";
//	private static String BASE_URI = "https://localhost:9090/services";

	private final WebTarget target;

	public JaxrsConnectionManager() {

		final Client client = ClientBuilder.newClient();
//		final Client client = ClientBuilder.newBuilder().sslContext(ssl).build();
		target = client.target(BASE_URI);
	}

	public List<ProjectInfo> getProjectList() {
		
		//create XMLResource 
		ResourceSetImpl resourceSetImpl = new ResourceSetImpl();
		final String fileNameURI = "blabla";
		final XMLResourceImpl resource = (XMLResourceImpl) resourceSetImpl.createResource(URI.createURI(fileNameURI));
		
		//make the http call and get the input stream
		final Response response = target.path(PATH_PROJECTS).request(MediaType.TEXT_XML).get();
		final InputStream is = response.readEntity(InputStream.class);
		
		try {
			//
			resource.doLoad(is, null);
			return (List<ProjectInfo>) resource.getAllContents();
		} catch (final IOException ex) {
			// TODO Auto-generated catch block
			// Do NOT catch all Exceptions ("catch (Exception e)")
			// Log AND handle Exceptions if possible
			//
			// You can just uncomment one of the lines below to log an exception:
			// logException will show the logged excpetion to the user
			// ModelUtil.logException(ex);
			// ModelUtil.logException("YOUR MESSAGE HERE", ex);
			// logWarning will only add the message to the error log
			// ModelUtil.logWarning("YOUR MESSAGE HERE", ex);
			// ModelUtil.logWarning("YOUR MESSAGE HERE");
			//
			// If handling is not possible declare and rethrow Exception
		}

		return null; // TODO: was ist best practice?
	}

	public Project getProject(ProjectId projectId, VersionSpec versionSpec) {
		// TODO: implement!

		return null;
	}

	public ProjectInfo createEmptyProject(String name, String description, LogMessage logMessage) {
		// TODO: implement!

		return null;
	}

}
