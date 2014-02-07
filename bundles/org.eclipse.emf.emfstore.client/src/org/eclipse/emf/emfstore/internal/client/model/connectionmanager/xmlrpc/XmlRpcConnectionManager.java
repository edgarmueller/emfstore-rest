/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager.xmlrpc;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.AbstractConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.common.model.EMFStoreProperty;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.connection.xmlrpc.XmlRpcConnectionHandler;
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
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * XML RPC based Implementation of ConnectionManager.
 * 
 * @author wesendon
 */
public class XmlRpcConnectionManager extends AbstractConnectionManager<XmlRpcClientManager> implements
	ConnectionManager {

	private static final String CONNECTION_MANAGER = "org.eclipse.emf.emfstore.client.connectionManager"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 */
	public AuthenticationInformation logIn(String username, String password, ServerInfo serverInfo,
		ClientVersionInfo clientVersionInfo) throws ESException {
		final XmlRpcClientManager clientManager = new XmlRpcClientManager(XmlRpcConnectionHandler.EMFSTORE);
		clientManager.initConnection(serverInfo);
		final AuthenticationInformation authenticationInformation = clientManager.callWithResult("logIn",
			AuthenticationInformation.class, username, password, clientVersionInfo);
		addConnectionProxy(authenticationInformation.getSessionId(), clientManager);
		return authenticationInformation;
	}

	/**
	 * {@inheritDoc}
	 */
	public void logout(SessionId sessionId) throws ESException {
		getConnectionProxy(sessionId).call("logout", sessionId);
		removeConnectionProxy(sessionId);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addTag(SessionId sessionId, ProjectId projectId, PrimaryVersionSpec versionSpec, TagVersionSpec tag)
		throws ESException {
		getConnectionProxy(sessionId).call("addTag", sessionId, projectId, versionSpec, tag);
	}

	/**
	 * {@inheritDoc}
	 */
	public ProjectInfo createEmptyProject(SessionId sessionId, String name, String description, LogMessage logMessage)
		throws ESException {
		return getConnectionProxy(sessionId).callWithResult("createEmptyProject", ProjectInfo.class, sessionId, name,
			description, logMessage);
	}

	/**
	 * {@inheritDoc}
	 */
	public ProjectInfo createProject(SessionId sessionId, String name, String description, LogMessage logMessage,
		Project project) throws ESException {
		return getConnectionProxy(sessionId).callWithResult("createProject", ProjectInfo.class, sessionId, name,
			description, logMessage, project);
	}

	/**
	 * {@inheritDoc}
	 */
	public PrimaryVersionSpec createVersion(SessionId sessionId, ProjectId projectId,
		PrimaryVersionSpec baseVersionSpec, ChangePackage changePackage, BranchVersionSpec targetBranch,
		PrimaryVersionSpec sourceVersion, LogMessage logMessage) throws ESException, InvalidVersionSpecException {
		return getConnectionProxy(sessionId).callWithResult("createVersion", PrimaryVersionSpec.class, sessionId,
			projectId, baseVersionSpec, changePackage, targetBranch, sourceVersion, logMessage);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteProject(SessionId sessionId, ProjectId projectId, boolean deleteFiles) throws ESException {
		getConnectionProxy(sessionId).call("deleteProject", sessionId, projectId, deleteFiles);
	}

	/**
	 * {@inheritDoc}
	 */
	public FileChunk downloadFileChunk(SessionId sessionId, ProjectId projectId, FileTransferInformation fileInformation)
		throws ESException {
		return getConnectionProxy(sessionId).callWithResult("downloadFileChunk", FileChunk.class, sessionId, projectId,
			fileInformation);
	}

	/**
	 * {@inheritDoc}
	 */
	public ProjectHistory exportProjectHistoryFromServer(SessionId sessionId, ProjectId projectId)
		throws ESException {
		return getConnectionProxy(sessionId).callWithResult("exportProjectHistoryFromServer", ProjectHistory.class,
			sessionId, projectId);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ChangePackage> getChanges(SessionId sessionId, ProjectId projectId, VersionSpec source,
		VersionSpec target)
		throws InvalidVersionSpecException, ESException {
		return getConnectionProxy(sessionId).callWithListResult("getChanges", ChangePackage.class, sessionId,
			projectId, source, target);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.EMFStore#getBranches(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      org.eclipse.emf.emfstore.internal.server.model.ProjectId)
	 */
	public List<BranchInfo> getBranches(SessionId sessionId, ProjectId projectId) throws ESException {
		return getConnectionProxy(sessionId).callWithListResult("getBranches", BranchInfo.class, sessionId, projectId);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<HistoryInfo> getHistoryInfo(SessionId sessionId, ProjectId projectId, HistoryQuery<?> historyQuery)
		throws ESException {
		return getConnectionProxy(sessionId).callWithListResult("getHistoryInfo", HistoryInfo.class, sessionId,
			projectId, historyQuery);
	}

	/**
	 * {@inheritDoc}
	 */
	public Project getProject(SessionId sessionId, ProjectId projectId, VersionSpec versionSpec)
		throws InvalidVersionSpecException, ESException {
		return getConnectionProxy(sessionId).callWithResult("getProject", Project.class, sessionId, projectId,
			versionSpec);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ProjectInfo> getProjectList(SessionId sessionId) throws ESException {
		// return getConnectionProxy(sessionId).callWithListResult("getProjectList", ProjectInfo.class, sessionId);

		// JaxrsConnectionManager cm;
		// cm = new JaxrsConnectionManager();// KeyStoreManager.getInstance().getSSLContext());
		// return cm.getProjectList();

		final ESExtensionPoint extensionPoint = new ESExtensionPoint(
			CONNECTION_MANAGER, true);

		final ConnectionManager connectionManager = extensionPoint.getClass("class",
			ConnectionManager.class);

		return connectionManager.getProjectList(sessionId);
	}

	/**
	 * {@inheritDoc}
	 */
	public ProjectId importProjectHistoryToServer(SessionId sessionId, ProjectHistory projectHistory)
		throws ESException {
		return getConnectionProxy(sessionId).callWithResult("importProjectHistoryToServer", ProjectId.class, sessionId,
			projectHistory);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeTag(SessionId sessionId, ProjectId projectId, PrimaryVersionSpec versionSpec, TagVersionSpec tag)
		throws ESException {
		getConnectionProxy(sessionId).call("removeTag", sessionId, projectId, versionSpec, tag);
	}

	/**
	 * {@inheritDoc}
	 */
	public ACUser resolveUser(SessionId sessionId, ACOrgUnitId id) throws ESException {
		return getConnectionProxy(sessionId).callWithResult("resolveUser", ACUser.class, sessionId, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public PrimaryVersionSpec resolveVersionSpec(SessionId sessionId, ProjectId projectId, VersionSpec versionSpec)
		throws InvalidVersionSpecException, ESException {
		return getConnectionProxy(sessionId).callWithResult("resolveVersionSpec", PrimaryVersionSpec.class, sessionId,
			projectId, versionSpec);
	}

	/**
	 * {@inheritDoc}
	 */
	public void transmitProperty(SessionId sessionId, OrgUnitProperty changedProperty, ACUser tmpUser,
		ProjectId projectId) throws ESException {
		getConnectionProxy(sessionId).call("transmitProperty", sessionId, changedProperty, tmpUser, projectId);
	}

	/**
	 * {@inheritDoc}
	 */
	public FileTransferInformation uploadFileChunk(SessionId sessionId, ProjectId projectId, FileChunk fileChunk)
		throws ESException {
		return getConnectionProxy(sessionId).callWithResult("uploadFileChunk", FileTransferInformation.class,
			sessionId, projectId, fileChunk);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<EMFStoreProperty> setEMFProperties(SessionId sessionId, List<EMFStoreProperty> properties,
		ProjectId projectId) throws ESException {
		return getConnectionProxy(sessionId).callWithListResult("setEMFProperties", EMFStoreProperty.class, sessionId,
			properties, projectId);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<EMFStoreProperty> getEMFProperties(SessionId sessionId, ProjectId projectId) throws ESException {
		return getConnectionProxy(sessionId).callWithListResult("getEMFProperties", EMFStoreProperty.class, sessionId,
			projectId);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager#isLoggedIn(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 */
	public boolean isLoggedIn(SessionId id) {
		return hasConnectionProxy(id);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.EMFStore#registerEPackage(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      org.eclipse.emf.ecore.EPackage)
	 */
	public void registerEPackage(SessionId sessionId, EPackage pkg) throws ESException {
		getConnectionProxy(sessionId).call("registerEPackage", sessionId, pkg);

	}
}
