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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.exceptions.ESProjectNotSharedException;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.junit.Ignore;
import org.junit.Test;

public class UnsharedLocalProjectTest extends ESTest {

	private static final String COMMIT_AN_UNSHARED_PROJECT_ERROR_MSG = "Should not be able to commit an unshared Project!"; //$NON-NLS-1$
	private static final String UPDATE_AN_UNSHARED_PROJECT_ERROR_MSG = "Should not be able to update an unshared Project!"; //$NON-NLS-1$
	private static final String BRANCH_NAME = "branch"; //$NON-NLS-1$
	private static final String TAG_NAME = "tag"; //$NON-NLS-1$

	// @BeforeClass
	// public static void beforeClass() {
	// startEMFStore();
	// }
	//
	// @AfterClass
	// public static void afterClass() {
	// stopEMFStore();
	// }

	@Test
	public void testProjectName() {
		assertEquals("TestProject", getLocalProject().getProjectName()); //$NON-NLS-1$
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testProjectID() {
		// unshared projects have no project ID
		assertNull(getLocalProject().getGlobalProjectId());
	}

	@Test(expected = RuntimeException.class)
	public void testAddTag() throws ESException {
		// a tag can not be created for an unshared project
		getLocalProject().addTag(getLocalProject().getBaseVersion(), ESVersionSpec.FACTORY.createTAG(TAG_NAME, "test"), //$NON-NLS-1$
			new NullProgressMonitor());
		fail("Cannot add a tag!"); //$NON-NLS-1$
	}

	@Test
	public void testIsShared() {
		assertFalse(getLocalProject().isShared());
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testCommit() throws ESException {
		// can not commit an unshared project
		getLocalProject().commit(new NullProgressMonitor());
		fail(COMMIT_AN_UNSHARED_PROJECT_ERROR_MSG);
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testCommit2() throws ESException {
		// can not commit an unshared project
		getLocalProject().commit("SomeLogMessage", null, new NullProgressMonitor()); //$NON-NLS-1$
		fail(COMMIT_AN_UNSHARED_PROJECT_ERROR_MSG);
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testGetBaseVersion() {
		// unshared project has no base version
		final ESPrimaryVersionSpec version = getLocalProject().getBaseVersion();
		assertNull(version);
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testGetBranches() throws ESException {
		getLocalProject().getBranches(new NullProgressMonitor());
		fail("Should not be able to getBranches from an unshared Project!"); //$NON-NLS-1$
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testGetHistoryInfos() throws ESException {

		final Player player = Create.player(ProjectChangeUtil.PLAYER_HANS_NAME);
		Add.toProject(getLocalProject(), player);
		ProjectUtil.addElement(getLocalProject(), player);

		final ESModelElementId id = getLocalProject().getModelElementId(player);
		final ESHistoryQuery<?> query = ESHistoryQuery.FACTORY.modelElementQuery(getLocalProject().getBaseVersion(),
			id,
			1, 0,
			true,
			true);
		getLocalProject().getHistoryInfos(query, new NullProgressMonitor());
		fail("Should not be able to getHistoryInfos from an unshared Project!"); //$NON-NLS-1$
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testGetLastUpdated() {
		// unshared project has no last updated date
		final Date date = getLocalProject().getLastUpdated();
		assertNull(date);
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testGetRemoteProject() throws ESException {
		final ESRemoteProject remoteProject = getLocalProject().getRemoteProject();
		assertNull(remoteProject);
		fail("Should not be able to getRemoteProject from an unshared Project!"); //$NON-NLS-1$
	}

	public void testGetUsersession() {
		final ESUsersession session = getLocalProject().getUsersession();
		assertNull(session);
	}

	@Test
	public void testHasUncommitedChanges() {
		assertFalse(getLocalProject().hasUncommitedChanges());
		final Player player = Create.player();
		Add.toProject(getLocalProject(), player);
		assertTrue(getLocalProject().hasUncommitedChanges());
	}

	@Test
	public void testHasUnsavedChanges() {
		assertFalse(getLocalProject().hasUnsavedChanges());
		final Player player = Create.player();
		Add.toProject(getLocalProject(), player);
		assertTrue(getLocalProject().hasUnsavedChanges());
		getLocalProject().save();
		assertFalse(getLocalProject().hasUnsavedChanges());
	}

	@Ignore
	public void testImportLocalChanges() throws ESException {
		// TODO: getLocalProject().importLocalChanges(fileName);
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testIsUpdated() throws ESException {
		getLocalProject().isUpdated();
		fail("Should not be able to check update state of an unshared Project!"); //$NON-NLS-1$
	}

	// TODO: API does not support merging currently
	// @Test(expected = RuntimeException.class)
	// public void testMerge() throws ESException {
	// getLocalProject().mergeBranch(getLocalProject().getBaseVersion(), new TestConflictResolver(false, 0),
	// new NullProgressMonitor());
	// fail("Should not be able to merge with head on an unshared Project!");
	// }
	//
	// @Test(expected = RuntimeException.class)
	// public void testMergeBranch() throws ESException {
	// getLocalProject().mergeBranch(getLocalProject().getBaseVersion(),
	// new TestConflictResolver(false, 0), new NullProgressMonitor());
	//
	// fail("Should not be able to merge with head on an unshared Project!");
	// }

	@Test(expected = RuntimeException.class)
	public void testRemoveTag() throws ESException {
		getLocalProject().removeTag(getLocalProject().getBaseVersion(),
			ESVersionSpec.FACTORY.createTAG(TAG_NAME, BRANCH_NAME),
			new NullProgressMonitor());
		fail("Should not remove a tag from an unshared Project!"); //$NON-NLS-1$
	}

	@Test(expected = RuntimeException.class)
	public void testResolveSpec() throws ESException {
		getLocalProject().resolveVersionSpec(ESVersionSpec.FACTORY.createHEAD(), new NullProgressMonitor());
		fail("Should not be able to resolve a version spec from an unshared Project!"); //$NON-NLS-1$
	}

	@Test
	public void testRevert() {

		final Player player1 = Create.player();
		final Player player2 = Create.player();
		final Player player3 = Create.player();

		Add.toProject(getLocalProject(), player1);
		Add.toProject(getLocalProject(), player2);
		Add.toProject(getLocalProject(), player3);

		assertTrue(getLocalProject().getAllModelElements().contains(player1));
		assertTrue(getLocalProject().getAllModelElements().contains(player2));
		assertTrue(getLocalProject().getAllModelElements().contains(player3));

		getLocalProject().revert();

		assertEquals(getLocalProject().getAllModelElements().size(), 0);
	}

	@Test
	public void testUndoLastOperation() {
		final Player player1 = Create.player();
		final Player player2 = Create.player();

		Add.toProject(getLocalProject(), player1);
		Add.toProject(getLocalProject(), player2);

		assertTrue(getLocalProject().getAllModelElements().contains(player1));
		assertTrue(getLocalProject().getAllModelElements().contains(player2));

		getLocalProject().undoLastOperation();

		assertTrue(getLocalProject().getAllModelElements().contains(player1));
		assertFalse(getLocalProject().getAllModelElements().contains(player2));
	}

	@Test
	public void testUndoLastOperationsMore() {
		final Player player = Create.player();

		Add.toProject(getLocalProject(), player);

		assertTrue(getLocalProject().getAllModelElements().contains(player));
		getLocalProject().undoLastOperations(0);
		assertTrue(getLocalProject().getAllModelElements().contains(player));
		getLocalProject().undoLastOperations(1);
		assertFalse(getLocalProject().getAllModelElements().contains(player));
		getLocalProject().undoLastOperations(2);
	}

	@Test
	public void testUndoLastOperations() {
		final Player player1 = Create.player();
		final Player player2 = Create.player();
		final Player player3 = Create.player();

		Add.toProject(getLocalProject(), player1);
		Add.toProject(getLocalProject(), player2);
		Add.toProject(getLocalProject(), player3);

		assertTrue(getLocalProject().getAllModelElements().contains(player1));
		assertTrue(getLocalProject().getAllModelElements().contains(player2));
		assertTrue(getLocalProject().getAllModelElements().contains(player3));

		getLocalProject().undoLastOperations(0);

		assertTrue(getLocalProject().getAllModelElements().contains(player1));
		assertTrue(getLocalProject().getAllModelElements().contains(player2));
		assertTrue(getLocalProject().getAllModelElements().contains(player3));

		getLocalProject().undoLastOperations(1);

		assertTrue(getLocalProject().getAllModelElements().contains(player1));
		assertTrue(getLocalProject().getAllModelElements().contains(player2));
		assertFalse(getLocalProject().getAllModelElements().contains(player3));

		getLocalProject().undoLastOperations(2);

		assertFalse(getLocalProject().getAllModelElements().contains(player1));
		assertFalse(getLocalProject().getAllModelElements().contains(player2));
		assertFalse(getLocalProject().getAllModelElements().contains(player3));
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testUpdate() throws ESException {
		getLocalProject().update(new NullProgressMonitor());
		fail(UPDATE_AN_UNSHARED_PROJECT_ERROR_MSG);
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testUpdateVersion() throws ESException {
		getLocalProject().update(getLocalProject().getBaseVersion(), null, new NullProgressMonitor());
		fail(UPDATE_AN_UNSHARED_PROJECT_ERROR_MSG);
	}

	@Test(expected = ESProjectNotSharedException.class)
	public void testUpdateVersionCallback() throws ESException {
		getLocalProject().update(getLocalProject().getBaseVersion(), null, new NullProgressMonitor());
		fail(UPDATE_AN_UNSHARED_PROJECT_ERROR_MSG);
	}

	@Test
	public void testShare() {
		try {
			getLocalProject().shareProject(new NullProgressMonitor());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testShareSession() {
		try {
			final ESServer server = ESServer.FACTORY.createServer("localhost", //$NON-NLS-1$
				ServerUtil.defaultPort(),
				KeyStoreManager.DEFAULT_CERTIFICATE);
			final ESUsersession usersession = server.login("super", "super"); //$NON-NLS-1$ //$NON-NLS-2$
			getLocalProject().shareProject(usersession, new NullProgressMonitor());
			final ESRemoteProject remote = getLocalProject().getRemoteProject();
			assertNotNull(remote);
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetterMethods() throws ESException {

		final League league = Create.league(ProjectChangeUtil.LEAGUE_AMERICA_NAME);

		final Player player1 = Create.player();
		final Player player2 = Create.player();

		Add.toProject(getLocalProject(), player1);
		Add.toProject(getLocalProject(), player2);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				league.getPlayers().add(player1);
				league.getPlayers().add(player2);
				getLocalProject().getModelElements().add(league);
				return null;
			}
		});

		assertEquals(3, getLocalProject().getAllModelElements().size());
		assertEquals(1, getLocalProject().getModelElements().size());
		assertEquals(2, getLocalProject().getAllModelElementsByClass(Player.class).size());
		assertEquals(1, getLocalProject().getAllModelElementsByClass(League.class, true).size());
	}
}
