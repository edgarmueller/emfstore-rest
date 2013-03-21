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
package org.eclipse.emf.emfstore.client.test.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.test.SetupHelper;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESRemoteProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidInputException;
import org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.AccesscontrolFactory;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.util.HistoryQueryBuilder;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * Superclass for server tests.
 * 
 * @author wesendon
 */

public abstract class ServerTests extends WorkspaceTest {

	private static SessionId sessionId;
	private static ConnectionManager connectionManager;
	private int projectsOnServerBeforeTest;
	private static HashMap<Class<?>, Object> arguments;
	private static ESServerImpl server;

	public static void setServerInfo(ESServer newServer) {
		server = (ESServerImpl) newServer;
	}

	public static ESServerImpl getServer() {
		return server;
	}

	/**
	 * @return the sessionId
	 */
	public static SessionId getSessionId() {
		return sessionId;
	}

	public static void setSessionId(SessionId newSessionId) {
		sessionId = newSessionId;
	}

	/**
	 * @return the connectionManager
	 */
	public static ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public static void setConnectionManager(ConnectionManager newConnectionManager) {
		connectionManager = newConnectionManager;
	}

	public ESRemoteProjectImpl getRemoteProject() throws ESException {
		ESLocalProjectImpl apiImpl = getProjectSpace().toAPI();
		return apiImpl.getRemoteProject();
	}

	public ProjectId getProjectId() {
		return getProjectSpace().getProjectId();
	}

	public PrimaryVersionSpec getProjectVersion() throws ESException {
		ESPrimaryVersionSpecImpl headVersion = (ESPrimaryVersionSpecImpl) getRemoteProject().getHeadVersion(
			new NullProgressMonitor());
		return headVersion.toInternalAPI();
	}

	/**
	 * @return the projectsOnServerBeforeTest
	 */
	public int getProjectsOnServerBeforeTest() {
		return projectsOnServerBeforeTest;
	}

	@Override
	@After
	public void teardown() throws IOException, SerializationException, ESException {
		super.teardown();
		for (ESRemoteProject project : getServer().getRemoteProjects()) {
			// TODO: monitor
			project.delete(new NullProgressMonitor());
		}
		Assert.assertEquals(0, getServer().getRemoteProjects().size());
	}

	/**
	 * Start server and gain sessionid.
	 * 
	 * @throws ESException in case of failure
	 * @throws IOException
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws ESException, IOException {
		WorkspaceTest.beforeClass();

		ServerConfiguration.setTesting(true);
		CommonUtil.setTesting(true);

		SetupHelper.addUserFileToServer(false);

		setConnectionManager(ESWorkspaceProviderImpl.getInstance().getConnectionManager());
		setServerInfo(SetupHelper.createServer());
		initArguments();

		SetupHelper.startSever();
		ServerTests.login();

	}

	/**
	 * @param server serverinfo
	 * @throws ESException in case of failure
	 */
	protected static void login() throws ESException {
		SessionId sessionId = login(server.toInternalAPI(), "super", "super").getSessionId();
		ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager()
			.initConnection(server.toInternalAPI(), sessionId);
		setSessionId(sessionId);
	}

	/**
	 * @param server serverinfo
	 * @param username username
	 * @param password password
	 * @return sessionId
	 * @throws ESException in case of failure
	 */
	protected static AuthenticationInformation login(ServerInfo serverInfo, String username, String password)
		throws ESException {
		return getConnectionManager().logIn(username, KeyStoreManager.getInstance().encrypt(password, serverInfo),
			serverInfo, Configuration.getVersioningInfo().getClientVersion());
	}

	protected static void initArguments() {
		arguments = new LinkedHashMap<Class<?>, Object>();
		arguments.put(boolean.class, false);
		arguments.put(String.class, new String());
		arguments.put(SessionId.class, ModelUtil.clone(getSessionId()));
		arguments.put(ProjectId.class,
			org.eclipse.emf.emfstore.internal.server.model.ModelFactory.eINSTANCE.createProjectId());
		arguments.put(PrimaryVersionSpec.class, VersioningFactory.eINSTANCE.createPrimaryVersionSpec());
		arguments.put(VersionSpec.class, VersioningFactory.eINSTANCE.createPrimaryVersionSpec());
		arguments.put(TagVersionSpec.class, VersioningFactory.eINSTANCE.createTagVersionSpec());
		arguments.put(LogMessage.class, VersioningFactory.eINSTANCE.createLogMessage());
		arguments.put(Project.class,
			org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE.createProject());
		arguments.put(ChangePackage.class, VersioningFactory.eINSTANCE.createChangePackage());
		arguments.put(HistoryQuery.class, VersioningFactory.eINSTANCE.createPathQuery());
		arguments.put(ChangePackage.class, VersioningFactory.eINSTANCE.createChangePackage());
		arguments.put(ACOrgUnitId.class, AccesscontrolFactory.eINSTANCE.createACOrgUnitId());
	}

	/**
	 * Shuts down server after testing.
	 * 
	 * @throws IOException
	 *             in case cleanup/teardown fails
	 * @throws ESException
	 */
	@AfterClass
	public static void tearDownAfterClass() throws IOException, ESException {
		SetupHelper.stopServer();
		ESWorkspaceProviderImpl.getInstance().dispose();
		SetupHelper.cleanupServer();
		SetupHelper.removeServerTestProfile();
	}

	/**
	 * Adds a project to the server before test.
	 * 
	 * @throws ESException in case of failure
	 */
	@Override
	public void beforeHook() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					// getProjectSpace().setUsersession(getServerInfo().getLastUsersession());
					// getProjectSpace().getUsersession().logIn();
					getProjectSpace().shareProject(new NullProgressMonitor());
				} catch (ESException e) {
					Assert.fail();
				}
			}
		}.run(false);
		this.projectsOnServerBeforeTest = 1;
	}

	/**
	 * Removes all projects from server after test.
	 * 
	 * @throws ESException in case of failure
	 */
	@After
	public void afterTest() throws ESException {

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				try {
					for (ESRemoteProject remoteProject : getServer().getRemoteProjects()) {
						remoteProject.delete(new NullProgressMonitor());
					}
				} catch (ESException e) {
				}

				SetupHelper.cleanupServer();
			}
		}.run(false);
	}

	/**
	 * Sets up user on server.
	 * 
	 * @param name name of the user (must be specified in users.properties)
	 * @param role of type RolesPackage.eINSTANCE.getWriterRole() or RolesPackage.eINSTANCE.getReaderRole() ....
	 * @throws ESException in case of failure
	 */

	public ACOrgUnitId setupUsers(String name, EClass role) throws ESException {
		try {
			ACOrgUnitId orgUnitId = SetupHelper.createUserOnServer(name);
			SetupHelper.setUsersRole(orgUnitId, role, getProjectId());
			return orgUnitId;
		} catch (InvalidInputException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sets up usersession.
	 * 
	 * @param name of the user (must be specified in users.properties)
	 * @param password of the user (must be specified in users.properties)
	 * @return established usersession
	 */
	public Usersession setUpUsersession(String username, String password) {
		Usersession usersession = org.eclipse.emf.emfstore.internal.client.model.ModelFactory.eINSTANCE
			.createUsersession();
		usersession.setServerInfo(server.toInternalAPI());
		usersession.setUsername(username);
		usersession.setPassword(password);

		return usersession;
	}

	/**
	 * Compares two projects.
	 * 
	 * @param expected expected
	 * @param compare to be compared
	 */
	public static void assertEqual(Project expected, Project compare) {
		if (!ModelUtil.areEqual(expected, compare)) {
			throw new AssertionError("Projects are not equal.");
		}
	}

	/**
	 * Creates a historyquery.
	 * 
	 * @param ver1 source
	 * @param ver2 target
	 * @return historyquery
	 */
	public static HistoryQuery createHistoryQuery(PrimaryVersionSpec ver1, PrimaryVersionSpec ver2) {
		return HistoryQueryBuilder.pathQuery(ver1, ver2, false, false);
	}

	/**
	 * Get a default Parameter.
	 * 
	 * @param clazz parameter type
	 * @param b if false, null is returned
	 * @return parameter
	 */
	protected static Object getParameter(Class<?> clazz, boolean b) {
		if (clazz.equals(boolean.class)) {
			return false;
		}
		return (b) ? arguments.get(clazz) : null;
	}
}