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

import java.io.IOException;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;

public abstract class BaseSharedProjectTest extends BaseLoggedInUserTest {
	protected ESWorkspace workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();
	protected ESLocalProject localProject;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		localProject = workspace.createLocalProject("TestProject");
		localProject.shareProject(usersession, new NullProgressMonitor());
	}

	@Override
	@After
	public void tearDown() throws Exception {
		deleteRemoteProjects(usersession);
		super.tearDown();
		// deleteLocalProjects();
	}

	protected static void deleteRemoteProjects(ESUsersession usersession) throws IOException, FatalESException,
		ESException {
		for (ESRemoteProject project : ESWorkspaceProviderImpl.INSTANCE.getWorkspace().getServers().get(0)
			.getRemoteProjects(usersession)) {
			project.delete(usersession, new NullProgressMonitor());
		}
	}
}
