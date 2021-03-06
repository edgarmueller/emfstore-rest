/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.ui.controllers;

import java.io.IOException;

import junit.framework.Assert;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.ui.AllUITests;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBotTestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public abstract class AbstractUIControllerTest extends SWTBotTestCase {

	protected static SWTWorkbenchBot bot = new SWTWorkbenchBot();

	protected ESWorkspace workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();
	protected ESLocalProject localProject;
	protected ESLocalProject checkedoutCopy;
	protected ESServer server;
	protected ESUsersession usersession;

	@Override
	@Before
	public void setUp() throws Exception {
		server = ESServer.FACTORY.createServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		server = ESWorkspaceProvider.INSTANCE.getWorkspace().addServer(server);
		try {
			usersession = server.login("super", "super");
		} catch (final ESException e) {
			fail(e.getMessage());
		}
		Assert.assertEquals(usersession, server.getLastUsersession());
		deleteLocalProjects();
		deleteRemoteProjects(usersession);

		localProject = workspace.createLocalProject("TestProject");
		localProject.shareProject(usersession, new NullProgressMonitor());
	}

	@Override
	protected void tearDown() throws Exception {
		localProject = null;
		checkedoutCopy = null;
		super.tearDown();
	}

	protected static void deleteLocalProjects() throws IOException, FatalESException, ESException {
		for (final ESLocalProject project : ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects()) {
			project.delete(new NullProgressMonitor());
		}
	}

	protected static void deleteRemoteProjects(ESUsersession usersession) throws IOException, FatalESException,
		ESException {
		for (final ESRemoteProject project : ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().get(0)
			.getRemoteProjects(usersession)) {
			project.delete(usersession, new NullProgressMonitor());
		}
	}

	protected int timeout() {
		return AllUITests.TIMEOUT;
	}

	@Test
	public abstract void testController() throws ESException;
}
