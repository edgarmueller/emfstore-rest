/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 ******************************************************************************/
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
