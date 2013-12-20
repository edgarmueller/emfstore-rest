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
package org.eclipse.emf.emfstore.client.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser;
import org.eclipse.emf.emfstore.client.test.common.dsl.Delete;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerCommunicationTest extends ESTestWithLoggedInUser {

	private static final String REMOTE_PROJECT_NAME = "MyProject2"; //$NON-NLS-1$
	private static final String PROJECT_NAME = "MyProject"; //$NON-NLS-1$

	@Override
	@After
	public void after() {
		try {
			Delete.allRemoteProjects(getServer(), getUsersession());
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
		super.after();
	}

	@BeforeClass
	public static void beforeClass() {
		startEMFStore();
	}

	@AfterClass
	public static void tearDownClass() {
		stopEMFStore();

		for (final ServerInfo serverInfo : ESWorkspaceProviderImpl.getInstance().getWorkspace().toInternalAPI()
			.getServerInfos()) {
			final Usersession lastUsersession = serverInfo.getLastUsersession();
			RunESCommand.run(new Callable<Void>() {
				public Void call() throws Exception {
					if (lastUsersession != null) {
						lastUsersession.setServerInfo(null);
					}
					serverInfo.setLastUsersession(null);
					return null;
				}
			});
		}
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				ESWorkspaceProviderImpl.getInstance().getWorkspace().toInternalAPI().getServerInfos().clear();
				ESWorkspaceProviderImpl.getInstance().getWorkspace().toInternalAPI().save();
				return null;
			}
		});
	}

	protected static void deleteRemoteProjects(ESUsersession usersession) throws IOException, FatalESException,
		ESException {
		for (final ESRemoteProject project : ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().get(0)
			.getRemoteProjects(usersession)) {
			project.delete(usersession, new NullProgressMonitor());
		}
	}

	@Test
	public void testUsersession() {
		assertEquals(getUsersession(), getServer().getLastUsersession());
	}

	@Test
	public void testLogin() {
		assertTrue(getUsersession().isLoggedIn());
	}

	@Test
	public void testLogout() {
		assertTrue(getUsersession().isLoggedIn());
		try {
			getUsersession().logout();
			assertFalse(getUsersession().isLoggedIn());
		} catch (final ESException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testCreateRemoteProject() {
		try {
			final ESRemoteProject remoteProject = getServer().createRemoteProject(getUsersession(), PROJECT_NAME,
				new NullProgressMonitor());
			assertNotNull(remoteProject);
			assertEquals(PROJECT_NAME, remoteProject.getProjectName());
			final List<ESRemoteProject> remoteProjects = getServer().getRemoteProjects();
			assertEquals(1, remoteProjects.size());
			// we expect a copy to be returned
			assertFalse(remoteProject.equals(remoteProjects.get(0)));
			assertEquals(remoteProject.getProjectName(), remoteProjects.get(0).getProjectName());
		} catch (final ESException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testCreateRemoteProjectWithoutUsersession() {
		try {
			final ESRemoteProject remoteProject = getServer().createRemoteProject(PROJECT_NAME,
				new NullProgressMonitor());
			assertNotNull(remoteProject);
			assertEquals(PROJECT_NAME, remoteProject.getProjectName());
			final List<? extends ESRemoteProject> remoteProjects = getServer().getRemoteProjects();
			assertEquals(1, remoteProjects.size());
			// we expect a copy to be returned
			assertFalse(remoteProject.equals(remoteProjects.get(0)));
			assertEquals(remoteProject.getProjectName(), remoteProjects.get(0).getProjectName());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDeleteRemoteProject() {
		try {
			final ESRemoteProject remoteProject = getServer().createRemoteProject(getUsersession(), PROJECT_NAME,
				new NullProgressMonitor());
			assertEquals(1, getServer().getRemoteProjects().size());
			remoteProject.delete(new NullProgressMonitor());
			assertEquals(0, getServer().getRemoteProjects().size());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetRemoteProjectsFromServer() {
		try {
			final ESRemoteProject project = getServer().createRemoteProject(getUsersession(), PROJECT_NAME,
				new NullProgressMonitor());
			getServer().createRemoteProject(getUsersession(), REMOTE_PROJECT_NAME, new NullProgressMonitor());
			assertEquals(2, getServer().getRemoteProjects().size());
			getServer().getRemoteProjects().add(project);
			assertEquals(2, getServer().getRemoteProjects().size());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}
}
