package org.eclipse.emf.emfstore.client.test.api;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.client.IRemoteProject;
import org.eclipse.emf.emfstore.client.IWorkspace;
import org.eclipse.emf.emfstore.client.IWorkspaceProvider;
import org.junit.After;
import org.junit.Before;

public abstract class BaseSharedProjectTest extends BaseLoggedInUserTest {
	protected IWorkspace workspace = IWorkspaceProvider.INSTANCE.getWorkspace();
	protected ILocalProject localProject;

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
		for (IRemoteProject project : server.getRemoteProjects())
			project.delete(new NullProgressMonitor());
		for (ILocalProject project : workspace.getLocalProjects())
			project.delete(new NullProgressMonitor());
		super.tearDown();
	}

}
