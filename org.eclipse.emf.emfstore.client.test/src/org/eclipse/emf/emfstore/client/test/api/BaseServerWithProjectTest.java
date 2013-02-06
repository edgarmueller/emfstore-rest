package org.eclipse.emf.emfstore.client.test.api;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.junit.After;
import org.junit.Before;

public abstract class BaseServerWithProjectTest extends LoggedInUserTest {

	protected IRemoteProject remoteProject;
	protected String projectName="TestProject";
	protected String projectDescription="TestProject Description";

	@Before
	public void setUp() throws Exception {
		super.setUp();
		createRemoteProject();
	}

	private void createRemoteProject() throws EMFStoreException {
		remoteProject = server.createRemoteProject(usersession, projectName, projectDescription, new NullProgressMonitor());
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		if(remoteProject!=null)
			remoteProject.delete(usersession,true);
	}

}
