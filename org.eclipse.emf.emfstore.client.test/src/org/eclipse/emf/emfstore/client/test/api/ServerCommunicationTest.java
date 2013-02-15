package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
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
		super.tearDown();
		deleteRemoteProjects();
		// deleteLocalProjects();
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
