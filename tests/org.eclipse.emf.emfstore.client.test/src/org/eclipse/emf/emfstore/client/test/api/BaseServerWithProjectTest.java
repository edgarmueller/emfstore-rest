package org.eclipse.emf.emfstore.client.test.api;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;

public abstract class BaseServerWithProjectTest extends BaseLoggedInUserTest {

	protected ESRemoteProject remoteProject;
	protected String projectName = "TestProject";

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		createRemoteProject();
	}

	private void createRemoteProject() throws ESException {
		remoteProject = server.createRemoteProject(usersession, projectName, new NullProgressMonitor());
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		if (remoteProject != null)
			remoteProject.delete(usersession, new NullProgressMonitor());
	}

}
