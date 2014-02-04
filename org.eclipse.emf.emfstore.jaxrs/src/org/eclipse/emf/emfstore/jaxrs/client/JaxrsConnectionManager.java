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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
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
	private static String BASE_URI = "http://localhost:8080/services";

	private final WebTarget target;

	public JaxrsConnectionManager() {

		final Client client = ClientBuilder.newClient();
		target = client.target(BASE_URI).path("emfstore");
	}

	public List<ProjectInfo> getProjectList() {

		final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(ProjectInfoImpl.class);
		final String fileNameURI = "blabla";
		final XMLResourceImpl resource = (XMLResourceImpl) domain.createResource(fileNameURI);

		final Response response = target.path(PATH_PROJECTS).request(MediaType.TEXT_XML).get();
		final InputStream is = response.readEntity(InputStream.class);
		try {
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
