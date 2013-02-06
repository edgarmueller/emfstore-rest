package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.emfstore.client.api.ILocalProject;
import org.eclipse.emf.emfstore.client.api.IServer;
import org.eclipse.emf.emfstore.client.api.IWorkspace;
import org.eclipse.emf.emfstore.client.api.IWorkspaceProvider;
import org.eclipse.emf.emfstore.client.model.connectionmanager.KeyStoreManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorkspaceTest {

	private static IWorkspace workspace;
	private ILocalProject localProject;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		workspace = IWorkspaceProvider.INSTANCE.getWorkspace();
	}
	@Before
	public void setUp() throws Exception {
		assertEquals(0,workspace.getLocalProjects());
		localProject = workspace.createLocalProject("TestProject", "My Test Project");
	}

	@After
	public void tearDown() throws Exception {
		for(ILocalProject lp:workspace.getLocalProjects())
			lp.delete();
	}

	@Test
	public void testCreateLocalProject() {
		assertNotNull(localProject);
		assertEquals(1,workspace.getLocalProjects());
		workspace.createLocalProject("TestProject2", "My Test Project");
		assertEquals(2,workspace.getLocalProjects());
	}
	@Test
	public void testServers(){
		assertEquals(0,workspace.getServers());
		IServer server=IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		workspace.addServer(server);
		assertEquals(1,workspace.getServers());
		workspace.removeServer(server);
		assertEquals(0,workspace.getServers());
	}

}
