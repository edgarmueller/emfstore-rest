package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.client.IServer;
import org.eclipse.emf.emfstore.client.IWorkspace;
import org.eclipse.emf.emfstore.client.IWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
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
		assertEquals(0, workspace.getLocalProjects().size());
		localProject = workspace.createLocalProject("TestProject", "My Test Project");
	}

	@After
	public void tearDown() throws Exception {
		for (ILocalProject lp : workspace.getLocalProjects())
			lp.delete(new NullProgressMonitor());
	}

	@Test
	public void testCreateLocalProject() {
		assertNotNull(localProject);
		assertEquals(1, workspace.getLocalProjects().size());
		workspace.createLocalProject("TestProject2", "My Test Project");
		assertEquals(2, workspace.getLocalProjects().size());
	}

	@Test
	public void testServers() {
		int servers = workspace.getServers().size();
		IServer server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		workspace.addServer(server);
		assertEquals(servers + 1, workspace.getServers().size());
		workspace.removeServer(server);
		assertEquals(servers, workspace.getServers().size());
	}

}
