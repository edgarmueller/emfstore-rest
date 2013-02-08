/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.server.api.util;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.emfstore.common.model.EMFStoreProperty;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.server.EMFStore;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.filetransfer.FileChunk;
import org.eclipse.emf.emfstore.server.filetransfer.FileTransferInformation;
import org.eclipse.emf.emfstore.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.server.model.ClientVersionInfo;
import org.eclipse.emf.emfstore.server.model.ModelFactory;
import org.eclipse.emf.emfstore.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.SessionId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.server.model.accesscontrol.OrgUnitProperty;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;

public class ConnectionMock implements ConnectionManager {

	private final EMFStore emfStore;
	// TODO: auth mock is never used locally
	private final AuthControlMock authMock;
	private HashSet<SessionId> sessions;

	public ConnectionMock(EMFStore emfStore, AuthControlMock authMock) {
		this.emfStore = emfStore;
		this.authMock = authMock;
		sessions = new LinkedHashSet<SessionId>();
	}

	public AuthenticationInformation logIn(String username, String password, ServerInfo severInfo,
		ClientVersionInfo clientVersionInfo) throws EMFStoreException {
		AuthenticationInformation information = ModelFactory.eINSTANCE.createAuthenticationInformation();
		SessionId sessionId = ModelFactory.eINSTANCE.createSessionId();
		sessions.add(sessionId);
		information.setSessionId(sessionId);
		return information;
	}

	public void logout(SessionId sessionId) throws EMFStoreException {
		sessions.remove(sessionId);
	}

	public boolean isLoggedIn(SessionId id) {
		return sessions.contains(id);
	}

	public void checkSessionId(SessionId sessionId) throws EMFStoreException {
		if (!isLoggedIn(sessionId))
			throw new AccessControlException();
	}

	public List<ProjectInfo> getProjectList(SessionId sessionId) throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.getProjectList(ModelUtil.clone(sessionId)));
	}

	public Project getProject(SessionId sessionId, ProjectId projectId, VersionSpec versionSpec)
		throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.getProject(ModelUtil.clone(sessionId), ModelUtil.clone(projectId),
			ModelUtil.clone(versionSpec)));
	}

	public PrimaryVersionSpec createVersion(SessionId sessionId, ProjectId projectId,
		PrimaryVersionSpec baseVersionSpec, ChangePackage changePackage, BranchVersionSpec targetBranch,
		PrimaryVersionSpec sourceVersion, LogMessage logMessage) throws EMFStoreException, InvalidVersionSpecException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.createVersion(ModelUtil.clone(sessionId), ModelUtil.clone(projectId),
			ModelUtil.clone(baseVersionSpec), ModelUtil.clone(changePackage), ModelUtil.clone(targetBranch),
			ModelUtil.clone(sourceVersion), ModelUtil.clone(logMessage)));
	}

	public PrimaryVersionSpec resolveVersionSpec(SessionId sessionId, ProjectId projectId, VersionSpec versionSpec)
		throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.resolveVersionSpec(ModelUtil.clone(sessionId), ModelUtil.clone(projectId),
			ModelUtil.clone(versionSpec)));
	}

	public List<ChangePackage> getChanges(SessionId sessionId, ProjectId projectId, VersionSpec source,
		VersionSpec target) throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.getChanges(ModelUtil.clone(sessionId), ModelUtil.clone(projectId),
			ModelUtil.clone(source), ModelUtil.clone(target)));
	}

	public List<BranchInfo> getBranches(SessionId sessionId, ProjectId projectId) throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.getBranches(ModelUtil.clone(sessionId), ModelUtil.clone(projectId)));
	}

	public List<HistoryInfo> getHistoryInfo(SessionId sessionId, ProjectId projectId, HistoryQuery historyQuery)
		throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.getHistoryInfo(ModelUtil.clone(sessionId), ModelUtil.clone(projectId),
			ModelUtil.clone(historyQuery)));
	}

	public void addTag(SessionId sessionId, ProjectId projectId, PrimaryVersionSpec versionSpec, TagVersionSpec tag)
		throws EMFStoreException {
		checkSessionId(sessionId);
		emfStore.addTag(ModelUtil.clone(sessionId), ModelUtil.clone(projectId), ModelUtil.clone(versionSpec),
			ModelUtil.clone(tag));
	}

	public void removeTag(SessionId sessionId, ProjectId projectId, PrimaryVersionSpec versionSpec, TagVersionSpec tag)
		throws EMFStoreException {
		checkSessionId(sessionId);
		emfStore.removeTag(ModelUtil.clone(sessionId), ModelUtil.clone(projectId), ModelUtil.clone(versionSpec),
			ModelUtil.clone(tag));
	}

	public ProjectInfo createEmptyProject(SessionId sessionId, String name, String description, LogMessage logMessage)
		throws EMFStoreException {
		checkSessionId(sessionId);
		return emfStore.createEmptyProject(ModelUtil.clone(sessionId), name, description, ModelUtil.clone(logMessage));
	}

	public ProjectInfo createProject(SessionId sessionId, String name, String description, LogMessage logMessage,
		Project project) throws EMFStoreException {
		checkSessionId(sessionId);
		return emfStore.createProject(ModelUtil.clone(sessionId), name, description, ModelUtil.clone(logMessage),
			ModelUtil.clone(project));
	}

	public void deleteProject(SessionId sessionId, ProjectId projectId, boolean deleteFiles) throws EMFStoreException {
		checkSessionId(sessionId);
		emfStore.deleteProject(ModelUtil.clone(sessionId), ModelUtil.clone(projectId), deleteFiles);
	}

	public ACUser resolveUser(SessionId sessionId, ACOrgUnitId id) throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.resolveUser(ModelUtil.clone(sessionId), ModelUtil.clone(id)));
	}

	public ProjectId importProjectHistoryToServer(SessionId sessionId, ProjectHistory projectHistory)
		throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.importProjectHistoryToServer(ModelUtil.clone(sessionId),
			ModelUtil.clone(projectHistory)));
	}

	public ProjectHistory exportProjectHistoryFromServer(SessionId sessionId, ProjectId projectId)
		throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.exportProjectHistoryFromServer(ModelUtil.clone(sessionId),
			ModelUtil.clone(projectId)));
	}

	public FileTransferInformation uploadFileChunk(SessionId sessionId, ProjectId projectId, FileChunk fileChunk)
		throws EMFStoreException {
		checkSessionId(sessionId);
		return emfStore.uploadFileChunk(ModelUtil.clone(sessionId), ModelUtil.clone(projectId), fileChunk);
	}

	public FileChunk downloadFileChunk(SessionId sessionId, ProjectId projectId, FileTransferInformation fileInformation)
		throws EMFStoreException {
		checkSessionId(sessionId);
		return emfStore.downloadFileChunk(ModelUtil.clone(sessionId), ModelUtil.clone(projectId), fileInformation);
	}

	public void transmitProperty(SessionId sessionId, OrgUnitProperty changedProperty, ACUser user, ProjectId projectId)
		throws EMFStoreException {
		checkSessionId(sessionId);
		emfStore.transmitProperty(ModelUtil.clone(sessionId), ModelUtil.clone(changedProperty), ModelUtil.clone(user),
			ModelUtil.clone(projectId));
	}

	public List<EMFStoreProperty> setEMFProperties(SessionId sessionId, List<EMFStoreProperty> property,
		ProjectId projectId) throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.setEMFProperties(ModelUtil.clone(sessionId), ModelUtil.clone(property),
			ModelUtil.clone(projectId)));
	}

	public List<EMFStoreProperty> getEMFProperties(SessionId sessionId, ProjectId projectId) throws EMFStoreException {
		checkSessionId(sessionId);
		return ModelUtil.clone(emfStore.getEMFProperties(ModelUtil.clone(sessionId), ModelUtil.clone(projectId)));
	}

	public void registerEPackage(SessionId sessionId, EPackage pkg) throws EMFStoreException {
		checkSessionId(sessionId);
		emfStore.registerEPackage(ModelUtil.clone(sessionId), ModelUtil.clone(pkg));
	}

	protected AuthControlMock getAuthMock() {
		return authMock;
	}
}