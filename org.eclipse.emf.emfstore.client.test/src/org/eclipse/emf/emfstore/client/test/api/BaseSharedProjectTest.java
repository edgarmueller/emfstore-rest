package org.eclipse.emf.emfstore.client.test.api;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.junit.After;
import org.junit.Before;

public abstract class BaseSharedProjectTest extends BaseLoggedInUserTest {
	protected ESWorkspace workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();
	protected ESLocalProject localProject;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		workspace.addServer(server);
		localProject = workspace.createLocalProject("TestProject", "My Test Project");
		localProject.shareProject(usersession, new NullProgressMonitor());
	}

	@Override
	@After
	public void tearDown() throws Exception {
		workspace.removeServer(server);
		for (ESRemoteProject project : server.getRemoteProjects())
			project.delete(new NullProgressMonitor());
		for (ESLocalProject project : workspace.getLocalProjects())
			project.delete(new NullProgressMonitor());
		super.tearDown();
	}

}
