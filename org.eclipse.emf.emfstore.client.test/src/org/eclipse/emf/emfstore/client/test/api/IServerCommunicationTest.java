package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IServerCommunicationTest extends LoggedInUserTest {

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testUsersession() {
		assertEquals(usersession,server.getLastUsersession());
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
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
		
	}
	@Test
	public void testCreateRemoteProject() {
		try {
			IRemoteProject remoteProject=server.createRemoteProject(usersession, "MyProject", "MyProject Description", new NullProgressMonitor());
			assertNotNull(remoteProject);
			assertEquals("MyProject",remoteProject.getProjectName());
			assertEquals("MyProject Description",remoteProject.getProjectDescription());
			List<? extends IRemoteProject> remoteProjects=server.getRemoteProjects();
			assertEquals(1, remoteProjects.size());
			assertEquals(remoteProject, remoteProjects.get(0));
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
		
		
	}
	@Test
	public void testCreateRemoteProjectWithoutUsersession() {
		try {
			IRemoteProject remoteProject=server.createRemoteProject("MyProject", "MyProject Description", new NullProgressMonitor());
			assertNotNull(remoteProject);
			assertEquals("MyProject",remoteProject.getProjectName());
			assertEquals("MyProject Description",remoteProject.getProjectDescription());
			List<? extends IRemoteProject> remoteProjects=server.getRemoteProjects();
			assertEquals(1, remoteProjects.size());
			assertEquals(remoteProject, remoteProjects.get(0));
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}		
	}
	@Test
	public void testDeleteRemoteProject() {
		try {
			IRemoteProject remoteProject=server.createRemoteProject(usersession, "MyProject", "MyProject Description", new NullProgressMonitor());
			assertEquals(1, server.getRemoteProjects().size());
			remoteProject.delete(true);
			assertEquals(0, server.getRemoteProjects().size());
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}
}
