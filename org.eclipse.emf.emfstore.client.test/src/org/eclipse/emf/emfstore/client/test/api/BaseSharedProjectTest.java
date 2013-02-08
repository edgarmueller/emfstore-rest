package org.eclipse.emf.emfstore.client.test.api;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.api.ILocalProject;
import org.eclipse.emf.emfstore.internal.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.internal.client.api.IWorkspace;
import org.eclipse.emf.emfstore.internal.client.api.IWorkspaceProvider;
import org.junit.After;
import org.junit.Before;

public abstract class BaseSharedProjectTest extends BaseLoggedInUserTest {
	protected IWorkspace workspace=IWorkspaceProvider.INSTANCE.getWorkspace();
	protected ILocalProject localProject;
	@Before
	public void setUp() throws Exception {
		super.setUp();
		workspace.addServer(server);
		localProject = workspace.createLocalProject("TestProject", "My Test Project");
		localProject.shareProject(usersession, new NullProgressMonitor());
	}

	@After
	public void tearDown() throws Exception {
		workspace.removeServer(server);
		for(IRemoteProject project:server.getRemoteProjects())
			project.delete(true);
		for(ILocalProject project:workspace.getLocalProjects())
			project.delete();
		super.tearDown();
	}

}
