package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ServerCommunicationTest extends BaseLoggedInUserTest {

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		deleteRemoteProjects(usersession);
		super.tearDown();
		// deleteLocalProjects();
	}

	@AfterClass
	public static void tearDownClass() {
		for (ServerInfo serverInfo : ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace()).getServerInfos()) {
			Usersession lastUsersession = serverInfo.getLastUsersession();
			if (lastUsersession != null) {
				lastUsersession.setServerInfo(null);
			}
			serverInfo.setLastUsersession(null);
		}
		((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace()).getServerInfos().clear();
		((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace()).save();
		System.out.println("");
	}

	protected static void deleteRemoteProjects(ESUsersession usersession) throws IOException, FatalESException,
		ESException {
		for (ESRemoteProject project : WorkspaceProvider.INSTANCE.getWorkspace().getServers().get(0)
			.getRemoteProjects(usersession)) {
			project.delete(usersession, new NullProgressMonitor());
		}
	}

	@Test
	public void testUsersession() {
		assertEquals(usersession, server.getLastUsersession());
	}

	@Test
	public void testLogin() {
		assertTrue(usersession.isLoggedIn());
	}

	@Test
	public void testLogout() {
		assertTrue(usersession.isLoggedIn());
		try {
			usersession.logout();
			assertFalse(usersession.isLoggedIn());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}

	}

	@Test
	public void testCreateRemoteProject() {
		try {
			ESRemoteProject remoteProject = server.createRemoteProject(usersession, "MyProject",
				new NullProgressMonitor());
			assertNotNull(remoteProject);
			assertEquals("MyProject", remoteProject.getProjectName());
			List<ESRemoteProject> remoteProjects = server.getRemoteProjects();
			assertEquals(1, remoteProjects.size());
			// we expect a copy to be returned
			assertFalse(remoteProject.equals(remoteProjects.get(0)));
			assertEquals(remoteProject.getProjectName(), remoteProjects.get(0).getProjectName());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}

	}

	@Test
	public void testCreateRemoteProjectWithoutUsersession() {
		try {
			ESRemoteProject remoteProject = server.createRemoteProject("MyProject",
				new NullProgressMonitor());
			assertNotNull(remoteProject);
			assertEquals("MyProject", remoteProject.getProjectName());
			List<? extends ESRemoteProject> remoteProjects = server.getRemoteProjects();
			assertEquals(1, remoteProjects.size());
			// we expect a copy to be returned
			assertFalse(remoteProject.equals(remoteProjects.get(0)));
			assertEquals(remoteProject.getProjectName(), remoteProjects.get(0).getProjectName());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testDeleteRemoteProject() {
		try {
			ESRemoteProject remoteProject = server.createRemoteProject(usersession, "MyProject",
				new NullProgressMonitor());
			assertEquals(1, server.getRemoteProjects().size());
			remoteProject.delete(new NullProgressMonitor());
			assertEquals(0, server.getRemoteProjects().size());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetRemoteProjectsFromServer() {
		try {
			ESRemoteProject project = server.createRemoteProject(usersession, "MyProject", new NullProgressMonitor());
			server.createRemoteProject(usersession, "MyProject2", new NullProgressMonitor());
			assertEquals(2, server.getRemoteProjects().size());
			server.getRemoteProjects().add(project);
			assertEquals(2, server.getRemoteProjects().size());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}
}
