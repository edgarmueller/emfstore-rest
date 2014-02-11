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
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.common.model.EMFStoreProperty;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.filetransfer.FileChunk;
import org.eclipse.emf.emfstore.internal.server.filetransfer.FileTransferInformation;
import org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.OrgUnitProperty;
import org.eclipse.emf.emfstore.internal.server.model.impl.ProjectInfoImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.jax.common.ProjectDataTO;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * @author Pascal
 * 
 */
public class JaxrsConnectionManager implements ConnectionManager {

	private static final String PATH_PROJECTS = "projects";
	private static String BASE_URI = "http://localhost:9090/services";
//	private static String BASE_URI = "https://localhost:9090/services";

	private final WebTarget target;

	public JaxrsConnectionManager() {

		final Client client = ClientBuilder.newClient();
//		final Client client = ClientBuilder.newBuilder().sslContext(ssl).build();
		target = client.target(BASE_URI);
	}

	private List<ProjectInfo> getProjectList() {
		
		//create XMLResource 
		ResourceSetImpl resourceSetImpl = new ResourceSetImpl();
		final String fileNameURI = "blabla";
		final XMLResourceImpl resource = (XMLResourceImpl) resourceSetImpl.createResource(URI.createURI(fileNameURI));
		
		//make the http call and get the input stream
		final Response response = target.path(PATH_PROJECTS).request(MediaType.TEXT_XML).get();
		final InputStream is = response.readEntity(InputStream.class);
		
		try {
			//create the List<ProjectInfo> from the input stream
			resource.doLoad(is, null);   
			List<ProjectInfo> projectInfoList = new ArrayList<ProjectInfo>();
			Object[] array = resource.getContents().toArray(); 
			for(Object o : array) {
				projectInfoList.add((ProjectInfo) o);
			}
			
			return projectInfoList;
		} catch (final IOException ex) {
			System.err.println(ex.getMessage());
		}

		return null; // TODO: was ist best practice?
	}

	private Project getProject(ProjectId projectId, VersionSpec versionSpec) {
		// TODO: implement!

		return null;
	}

	private ProjectInfo createProject(String name, String description, LogMessage logMessage, Project project) {
		
		//create the TransferObject
		ProjectDataTO projectDataTO = new ProjectDataTO(name, description, logMessage, project);
		
		//make the http call
		final Response response = target.path(PATH_PROJECTS).request(MediaType.TEXT_XML).post(Entity.entity(projectDataTO, MediaType.TEXT_XML));
		
		//read the entity
		
		return null;
	}

	public List<ProjectInfo> getProjectList(SessionId sessionId)
			throws ESException {

		return getProjectList();
	}

	public Project getProject(SessionId sessionId, ProjectId projectId,
			VersionSpec versionSpec) throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public PrimaryVersionSpec createVersion(SessionId sessionId,
			ProjectId projectId, PrimaryVersionSpec baseVersionSpec,
			ChangePackage changePackage, BranchVersionSpec targetBranch,
			PrimaryVersionSpec sourceVersion, LogMessage logMessage)
			throws ESException, InvalidVersionSpecException {
		// TODO Auto-generated method stub
		return null;
	}

	public PrimaryVersionSpec resolveVersionSpec(SessionId sessionId,
			ProjectId projectId, VersionSpec versionSpec) throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ChangePackage> getChanges(SessionId sessionId,
			ProjectId projectId, VersionSpec source, VersionSpec target)
			throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<BranchInfo> getBranches(SessionId sessionId, ProjectId projectId)
			throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<HistoryInfo> getHistoryInfo(SessionId sessionId,
			ProjectId projectId, HistoryQuery<?> historyQuery)
			throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addTag(SessionId sessionId, ProjectId projectId,
			PrimaryVersionSpec versionSpec, TagVersionSpec tag)
			throws ESException {
		// TODO Auto-generated method stub
		
	}

	public void removeTag(SessionId sessionId, ProjectId projectId,
			PrimaryVersionSpec versionSpec, TagVersionSpec tag)
			throws ESException {
		// TODO Auto-generated method stub
		
	}

	public ProjectInfo createEmptyProject(SessionId sessionId, String name,
			String description, LogMessage logMessage) throws ESException {
		
		return createProject(name, description, logMessage, null);
	}

	public ProjectInfo createProject(SessionId sessionId, String name,
			String description, LogMessage logMessage, Project project)
			throws ESException {
		
		return createProject(name, description, logMessage, project);
	}

	public void deleteProject(SessionId sessionId, ProjectId projectId,
			boolean deleteFiles) throws ESException {
		// TODO Auto-generated method stub
		
	}

	public ACUser resolveUser(SessionId sessionId, ACOrgUnitId id)
			throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public ProjectId importProjectHistoryToServer(SessionId sessionId,
			ProjectHistory projectHistory) throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public ProjectHistory exportProjectHistoryFromServer(SessionId sessionId,
			ProjectId projectId) throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public FileTransferInformation uploadFileChunk(SessionId sessionId,
			ProjectId projectId, FileChunk fileChunk) throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public FileChunk downloadFileChunk(SessionId sessionId,
			ProjectId projectId, FileTransferInformation fileInformation)
			throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public void transmitProperty(SessionId sessionId,
			OrgUnitProperty changedProperty, ACUser tmpUser, ProjectId projectId)
			throws ESException {
		// TODO Auto-generated method stub
		
	}

	public List<EMFStoreProperty> setEMFProperties(SessionId sessionId,
			List<EMFStoreProperty> property, ProjectId projectId)
			throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<EMFStoreProperty> getEMFProperties(SessionId sessionId,
			ProjectId projectId) throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerEPackage(SessionId sessionId, EPackage pkg)
			throws ESException {
		// TODO Auto-generated method stub
		
	}

	public AuthenticationInformation logIn(String username, String password,
			ServerInfo severInfo, ClientVersionInfo clientVersionInfo)
			throws ESException {
		// TODO Auto-generated method stub
		return null;
	}

	public void logout(SessionId sessionId) throws ESException {
		// TODO Auto-generated method stub
		
	}

	public boolean isLoggedIn(SessionId id) {
		// TODO Auto-generated method stub
		return false;
	}

}
