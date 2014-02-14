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
package org.eclipse.emf.emfstore.server.test;

import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.addAndCommit;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.addElement;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.commit;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.commitToBranch;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.defaultName;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.share;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.tag;
import static org.eclipse.emf.emfstore.internal.common.APIUtil.toInternal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.exceptions.ESServerStartFailedException;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.CreateAPI;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidProjectIdException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerInterfaceTest {

	private static ESServer server;
	private static ESUsersession session;

	@BeforeClass
	public static void beforeClass() throws IllegalArgumentException, ESServerStartFailedException,
		FatalESException, ESException, IOException {
		// server = ServerUtil.startServer();
		final ServerInfo serverInfo = ModelFactory.eINSTANCE.createServerInfo();
		serverInfo.setCertificateAlias(KeyStoreManager.getInstance().DEFAULT_CERTIFICATE);
		serverInfo.setUrl("localhost");
		serverInfo.setPort(8081);
		server = new ESServerImpl(serverInfo);
		session = server.login("super", "super");
		// deleteRemoteProjects(server, session);
		// deleteLocalProjects();
	}

	@AfterClass
	public static void afterClass() throws ESException {
		// session.logout();
	}

	@After
	public void after() throws Exception {
		ProjectUtil.deleteRemoteProjects(server, session);
		ProjectUtil.deleteLocalProjects();
	}

	@Test
	public void testCreateEmptyProject() throws ESException {

		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		final Usersession usersession = toInternal(Usersession.class, session);
		final ProjectId projectId = Create.projectId();
		projectId.setId(ProjectUtil.defaultName());

		final ProjectInfo projectInfo = connectionManager.createEmptyProject(
			usersession.getSessionId(),
			ProjectUtil.defaultName(),
			"Example Description",
			Create.logMessage());
		assertNotNull(projectInfo);
		final List<ProjectInfo> projectInfos = connectionManager.getProjectList(usersession.getSessionId());

		assertEquals(1, projectInfos.size());
		assertEquals(projectInfo.getName(), projectInfos.get(0).getName());
		assertEquals(projectInfo.getName(), ProjectUtil.defaultName());
	}

	@Test
	public void testCreateProject() throws ESException {

		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		final Usersession usersession = toInternal(Usersession.class, session);
		final ProjectId projectId = Create.projectId();
		projectId.setId(ProjectUtil.defaultName());

		final ProjectInfo projectInfo = connectionManager.createProject(
			usersession.getSessionId(),
			ProjectUtil.defaultName(),
			"Example Description",
			Create.logMessage(),
			ESLocalProjectImpl.class.cast(Create.project("testName")).toInternalAPI().getProject()
			);
		assertNotNull(projectInfo);
		final List<ProjectInfo> projectInfos = connectionManager.getProjectList(usersession.getSessionId());

		assertEquals(1, projectInfos.size());
		assertEquals(projectInfo.getName(), projectInfos.get(0).getName());
		assertEquals(projectInfo.getName(), ProjectUtil.defaultName());
	}

	@Test
	public void testGetProject() throws ESException {

		// create a Project
		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();
		final Usersession usersession = toInternal(Usersession.class, session);
		final ProjectId projectId = Create.projectId();
		projectId.setId(ProjectUtil.defaultName());

		final Project project = ESLocalProjectImpl.class.cast(Create.project("testName")).toInternalAPI().getProject();

		final ProjectInfo projectInfo = connectionManager.createProject(
			usersession.getSessionId(),
			ProjectUtil.defaultName(),
			"Example Description",
			Create.logMessage(),
			project
			);
		assertNotNull(projectInfo);

		// get the Project
		final Project retrievedProject = connectionManager.getProject(usersession.getSessionId(),
			projectInfo.getProjectId(),
			projectInfo.getVersion());

		assertNotNull(retrievedProject);
		assertEquals(project.getAllModelElements().size(), retrievedProject.getAllModelElements().size());
		assertTrue(ModelUtil.areEqual(project, retrievedProject));
	}

	@Test(expected = InvalidProjectIdException.class)
	public void testGetProjectWithInvalidProjectId() throws ESException {

		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		connectionManager.getProject(
			toInternal(Usersession.class, session).getSessionId(),
			Create.projectId(),
			Create.primaryVersionSpec(0));
	}

	@Test(expected = InvalidProjectIdException.class)
	public void testGetProjectWithNullProjectId() throws ESException {

		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		connectionManager.getProject(
			toInternal(Usersession.class, session).getSessionId(),
			Create.projectId(),
			Create.primaryVersionSpec(0));
	}

	// @Test(expected = InvalidProjectIdException.class)
	// public void testCreateVersion() throws ESException {
	//
	// ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();
	//
	// ESLocalProject localProject = ProjectUtil.CreateAPI.project(ProjectUtil.defaultName());
	// ProjectSpace projectSpace = APIUtil.toInternal(ProjectSpace.class, localProject);
	// Usersession usersession = APIUtil.toInternal(Usersession.class, session);
	// PrimaryVersionSpec baseVersionSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
	// BranchVersionSpec branchVersionSpec = VersioningFactory.eINSTANCE.createBranchVersionSpec();
	// ProjectId projectId = ModelFactory.eINSTANCE.CreateAPI.projectId();
	//
	// projectId.setId(ProjectUtil.defaultName());
	// baseVersionSpec.setBranch("trunk");
	// baseVersionSpec.setIdentifier(0);
	// branchVersionSpec.setBranch("trunk");
	//
	// PrimaryVersionSpec versionSpec = connectionManager.createVersion(
	// usersession.getSessionId(),
	// projectId,
	// baseVersionSpec,
	// VersioningFactory.eINSTANCE.createChangePackage(),
	// branchVersionSpec,
	// projectSpace.getMergedVersion(),
	// VersioningFactory.eINSTANCE.createLogMessage());
	// List<ProjectInfo> projectInfos = connectionManager.getProjectList(usersession.getSessionId());
	// }

	@Test
	public void testGetChanges() throws ESException {

		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		final ESLocalProject localProject = commit(
			addElement(
				share(session, CreateAPI.project(defaultName())),
				Create.testElement()));

		final List<ChangePackage> changes = connectionManager.getChanges(
			toInternal(Usersession.class, session).getSessionId(),
			toInternal(ProjectSpace.class, localProject).getProjectId(),
			Create.primaryVersionSpec(0),
			Create.primaryVersionSpec(1));

		assertEquals(1, changes.size());
	}

	@Test
	public void testResolvePagedUpdateVersionSpec() throws ESException {

		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		final ESLocalProject localProject = addAndCommit(share(session, CreateAPI.project(defaultName()))).times(3);

		final PrimaryVersionSpec resolvedVersionSpec = connectionManager.resolveVersionSpec(
			toInternal(Usersession.class, session).getSessionId(),
			toInternal(ProjectSpace.class, localProject).getProjectId(),
			Create.pagedUpdateVersionSpec(
				CreateAPI.primaryVersionSpec(0), 1));

		assertEquals(1, resolvedVersionSpec.getIdentifier());
	}

	@Test
	public void testResolveTagVersionSpec() throws ESException {

		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		final ESLocalProject localProject = addAndCommit(share(session, CreateAPI.project(defaultName()))).times(3);
		tag(localProject, CreateAPI.primaryVersionSpec(1), "trunk", "footag");

		final PrimaryVersionSpec resolvedVersionSpec = connectionManager.resolveVersionSpec(
			toInternal(Usersession.class, session).getSessionId(),
			toInternal(ProjectSpace.class, localProject).getProjectId(),
			Create.tagVersionSpec("trunk", "footag"));

		assertEquals(1, resolvedVersionSpec.getIdentifier());
	}

	@Test
	public void testResolveBranchVersionSpec() throws ESException {

		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		final ESLocalProject localProject = addAndCommit(share(session, CreateAPI.project(defaultName()))).times(3);

		final PrimaryVersionSpec resolvedVersionSpec = connectionManager.resolveVersionSpec(
			toInternal(Usersession.class, session).getSessionId(),
			toInternal(ProjectSpace.class, localProject).getProjectId(),
			Create.branchVersionSpec());

		assertEquals(3, resolvedVersionSpec.getIdentifier());
	}

	@Test
	public void testResolveAncestorVersionSpec() throws ESException {

		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		final ESLocalProject localProject = addAndCommit(share(session, CreateAPI.project(defaultName()))).times(1);

		commitToBranch(addElement(localProject, Create.testElement()), "foo-branch");

		final PrimaryVersionSpec resolvedVersionSpec = connectionManager.resolveVersionSpec(
			toInternal(Usersession.class, session).getSessionId(),
			toInternal(ProjectSpace.class, localProject).getProjectId(),
			Create.ancestorVersionSpec(
				Create.primaryVersionSpec(1),
				Create.primaryVersionSpec(2, "foo-branch")));

		assertEquals(1, resolvedVersionSpec.getIdentifier());
	}

	@Test
	public void testResolveDateVersionSpec() throws ESException {
		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		final ESLocalProject localProject = addAndCommit(share(session, CreateAPI.project(defaultName()))).times(1);

		final Date now = new Date();
		commit(addElement(localProject, Create.testElement()));

		final PrimaryVersionSpec resolvedVersionSpec = connectionManager.resolveVersionSpec(
			toInternal(Usersession.class, session).getSessionId(),
			toInternal(ProjectSpace.class, localProject).getProjectId(),
			Create.dateVersionSpec(now));

		assertEquals(1, resolvedVersionSpec.getIdentifier());
	}

	@Test
	public void testDeleteProject() throws ESException {
		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		final ESLocalProject localProject = addAndCommit(share(session, CreateAPI.project(defaultName()))).times(1);

		connectionManager.deleteProject(
			toInternal(Usersession.class, session).getSessionId(),
			toInternal(ProjectSpace.class, localProject).getProjectId(),
			true);

		assertEquals(0, connectionManager.getProjectList(
			toInternal(Usersession.class, session).getSessionId()).size());
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void testAddTag() throws ESException {

		final ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();

		// add more elements in order to create different VERSIONS
		final ESLocalProject localProject = addAndCommit(share(session, CreateAPI.project(defaultName()))).times(3);

		connectionManager.addTag(
			toInternal(Usersession.class, session).getSessionId(),
			toInternal(ProjectSpace.class, localProject).getProjectId(),
			Create.primaryVersionSpec(1),
			Create.tagVersionSpec("trunk", "footag"));

		connectionManager.removeTag(
			toInternal(Usersession.class, session).getSessionId(),
			toInternal(ProjectSpace.class, localProject).getProjectId(),
			Create.primaryVersionSpec(1),
			Create.tagVersionSpec("trunk", "footag"));

		connectionManager.resolveVersionSpec(
			toInternal(Usersession.class, session).getSessionId(),
			toInternal(ProjectSpace.class, localProject).getProjectId(),
			Create.tagVersionSpec("trunk", "footag"));
	}
}
