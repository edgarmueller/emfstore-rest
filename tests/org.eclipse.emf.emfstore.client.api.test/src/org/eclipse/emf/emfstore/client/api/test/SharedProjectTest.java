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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithSharedProject;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.util.CommitCallbackAdapter;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.Workspace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.exceptions.ESUpdateRequiredException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;
import org.eclipse.emf.emfstore.server.model.query.ESRangeQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SharedProjectTest extends ESTestWithSharedProject {

	private static final String SECOND_TEST_PROJECT_NAME = "SecondTestProject"; //$NON-NLS-1$
	private static final String CHECKOUT_NAME = "testCheckout"; //$NON-NLS-1$
	private static final String NEW_BRANCH_NAME = "newBranch"; //$NON-NLS-1$
	private static final String COMMIT_MESSAGE = "SomeCommitMessage"; //$NON-NLS-1$

	private ESCommitCallback callback;
	private boolean noLocalChangesOccurred;

	@BeforeClass
	public static void beforeClass() {
		startEMFStore();
	}

	@AfterClass
	public static void afterClass() {
		stopEMFStore();
	}

	@Test
	public void testShare() {
		try {
			final ESRemoteProject remoteProject = getLocalProject().getRemoteProject();
			assertNotNull(remoteProject);
			assertTrue(getLocalProject().isShared());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCommit() {
		try {
			final ESPrimaryVersionSpec base = getLocalProject().getBaseVersion();
			Add.toProject(getLocalProject(), Create.player());
			final ESPrimaryVersionSpec head = getLocalProject().commit(new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	public void testCommitWithoutChange() throws ESException {
		getLocalProject().commit(null, new CommitCallbackAdapter() {
			@Override
			public void noLocalChanges(ESLocalProject projectSpace) {
				noLocalChangesOccurred = true;
			}
		}, new NullProgressMonitor());
		assertTrue(noLocalChangesOccurred);
	}

	@Test
	public void testCommitLog() {

		try {
			final ESPrimaryVersionSpec base = getLocalProject().getBaseVersion();
			Add.toProject(getLocalProject(), Create.player());
			final ESPrimaryVersionSpec head = getLocalProject().commit(COMMIT_MESSAGE, callback,
				new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCommitBranch() {

		try {
			final ESPrimaryVersionSpec base = getLocalProject().getBaseVersion();
			Add.toProject(getLocalProject(), Create.player());
			final ESBranchVersionSpec branch = ESVersionSpec.FACTORY.createBRANCH(NEW_BRANCH_NAME);
			final ESPrimaryVersionSpec head = getLocalProject().commitToBranch(branch, COMMIT_MESSAGE, callback,
				new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	public void testCommitBranchWithoutChange() throws ESException {
		final ESBranchVersionSpec branch = ESVersionSpec.FACTORY.createBRANCH(NEW_BRANCH_NAME);
		getLocalProject().commitToBranch(branch, COMMIT_MESSAGE, new CommitCallbackAdapter() {
			@Override
			public void noLocalChanges(ESLocalProject projectSpace) {
				noLocalChangesOccurred = true;
			}
		}, new NullProgressMonitor());
		assertTrue(noLocalChangesOccurred);
	}

	@Test
	public void testBranchesOnlyTrunk() {
		try {
			final List<? extends ESBranchInfo> branches = getLocalProject().getBranches(new NullProgressMonitor());
			assertEquals(1, branches.size());
			// TODO assert branch name
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBranches() {

		try {
			List<? extends ESBranchInfo> branches = getLocalProject().getBranches(new NullProgressMonitor());
			assertEquals(1, branches.size());

			Add.toProject(getLocalProject(), Create.player());

			final ESBranchVersionSpec branch = ESVersionSpec.FACTORY.createBRANCH(NEW_BRANCH_NAME);
			getLocalProject().commitToBranch(branch, COMMIT_MESSAGE, callback,
				new NullProgressMonitor());
			branches = getLocalProject().getBranches(new NullProgressMonitor());
			assertEquals(2, branches.size());
			// TODO assert branch names
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testHistoryInfoOnlyTrunk() {
		try {
			final ESRangeQuery<?> query = ESHistoryQuery.FACTORY.rangeQuery(getLocalProject().getBaseVersion(), 1, 1,
				true,
				true,
				true,
				true);
			final NullProgressMonitor monitor = new NullProgressMonitor();
			List<ESHistoryInfo> infos = getLocalProject().getHistoryInfos(query, monitor);
			assertEquals(1, infos.size());

			Add.toProject(getLocalProject(), Create.player());

			assertEquals(0, getLocalProject().getBaseVersion().getIdentifier());
			final ESPrimaryVersionSpec head = getLocalProject().commit(COMMIT_MESSAGE, callback,
				new NullProgressMonitor());
			assertEquals(1, getLocalProject().getBaseVersion().getIdentifier());
			infos = getLocalProject().getHistoryInfos(
				ESHistoryQuery.FACTORY.rangeQuery(head, 1, 1, true, true, true, true),
				monitor);
			assertEquals(2, infos.size());
			// TODO assert infos
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testHistoryInfoBranch() {
		try {
			final ESRangeQuery<?> query = ESHistoryQuery.FACTORY.rangeQuery(getLocalProject().getBaseVersion(), 1, 1,
				true,
				true,
				true,
				true);
			final NullProgressMonitor monitor = new NullProgressMonitor();

			List<ESHistoryInfo> infos = getLocalProject().getHistoryInfos(query, monitor);
			assertEquals(1, infos.size());

			Add.toProject(getLocalProject(), Create.player());
			final ESBranchVersionSpec branch = ESVersionSpec.FACTORY.createBRANCH(NEW_BRANCH_NAME);
			getLocalProject().commitToBranch(branch, COMMIT_MESSAGE, callback,
				new NullProgressMonitor());
			infos = getLocalProject().getHistoryInfos(query, monitor);
			assertEquals(2, infos.size());
			// TODO assert infos
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testHasUncommitedChanges() {
		assertFalse(getLocalProject().hasUncommitedChanges());
		Add.toProject(getLocalProject(), Create.player());
		assertTrue(getLocalProject().hasUncommitedChanges());
		try {
			getLocalProject().commit(COMMIT_MESSAGE, callback, new NullProgressMonitor());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
		assertFalse(getLocalProject().hasUncommitedChanges());

	}

	@Test(expected = ESUpdateRequiredException.class)
	public void testMergeAndExpectBaseVersionOutOfDateException() throws ESException {
		final NullProgressMonitor monitor = new NullProgressMonitor();
		final Player player = Create.player();
		Add.toProject(getLocalProject(), player);
		getLocalProject().commit(monitor);
		final ESLocalProject checkedoutCopy = getLocalProject().getRemoteProject().checkout(CHECKOUT_NAME, monitor);
		final Player checkedoutPlayer = (Player) checkedoutCopy.getModelElements().get(0);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				player.setName("A"); //$NON-NLS-1$
				return null;
			}
		});

		getLocalProject().commit(monitor);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				checkedoutPlayer.setName("B"); //$NON-NLS-1$
				return null;
			}
		});

		checkedoutCopy.commit(monitor);
	}

	@Test
	public void testMoveElementViaReference() throws ESException {

		final League league = Create.league("Canadian bowling league"); //$NON-NLS-1$
		final Player player = Create.player("Joe"); //$NON-NLS-1$
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				league.getPlayers().add(player);
				getLocalProject().getModelElements().add(league);
				return null;
			}
		});
		assertTrue(getLocalProject().contains(league));

		final ESLocalProject secondProject = getWorkspace().createLocalProject(SECOND_TEST_PROJECT_NAME);
		secondProject.shareProject(new NullProgressMonitor());

		// tournament does not contain players
		final Tournament tournament = Create.tournament(false);
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				secondProject.getModelElements().add(tournament);
				tournament.getPlayers().add(player);
				return null;
			}
		});

		getLocalProject().save();
		secondProject.save();

		assertTrue(secondProject.contains(player));
		stopEMFStore();
		startEMFStore();

		for (final ESLocalProject project : ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects()) {
			if (project.getProjectName().equals(SECOND_TEST_PROJECT_NAME)) {
				final Tournament t = (Tournament) project.getModelElements().get(0);
				assertEquals(t.getPlayers().size(), 1);
			}
		}
	}

	@Test
	public void testShareWithModelInExistingResource() throws IOException, ESException {
		final Resource resource = createResource();
		final League league = createLeague();
		resource.getContents().add(league);
		resource.save(ModelUtil.getResourceSaveOptions());

		final ESLocalProject project = ESWorkspaceProvider.INSTANCE.getWorkspace().createLocalProject("test"); //$NON-NLS-1$
		ProjectUtil.addElement(project, league);
		project.shareProject(getUsersession(), new NullProgressMonitor());

		assertTrue(project.isShared());
	}

	@Test
	public void testDeleteSession() throws ESException {
		final Workspace w = ESWorkspaceImpl.class.cast(getWorkspace()).toInternalAPI();
		final int size = w.getUsersessions().size();
		getUsersession().delete();
		assertEquals(size - 1, w.getUsersessions().size());
	}

	private static Resource createResource() throws IOException {
		final ResourceSetImpl resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.createResource(
			URI.createFileURI(File.createTempFile("league", ".xmi").getAbsolutePath())); //$NON-NLS-1$ //$NON-NLS-2$
		return resource;
	}

	private static League createLeague() {
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		league.getPlayers().add(player);
		return league;
	}

	// TODO: API does not support merging currently
	// @Test
	// public void testMerge() throws ESException {
	//
	// final NullProgressMonitor monitor = new NullProgressMonitor();
	// final Player player = ProjectChangeUtil.addPlayerToProject(getLocalProject());
	// getLocalProject().commit(monitor);
	// ESLocalProject checkedoutCopy = getLocalProject().getRemoteProject().checkout(monitor);
	// final Player checkedoutPlayer = (Player) checkedoutCopy.getModelElements().get(0);
	//
	// RunESCommand.run(new Callable<Void>() {
	// public Void call() throws Exception {
	// player.setName("A");
	// return null;
	// }
	// });
	//
	// RunESCommand.run(new Callable<Void>() {
	// public Void call() throws Exception {
	// getLocalProject().commit(monitor);
	// return null;
	// }
	// });
	//
	// RunESCommand.run(new Callable<Void>() {
	// public Void call() throws Exception {
	// checkedoutPlayer.setName("B");
	// return null;
	// }
	// });
	//
	// checkedoutCopy.commit(null, new CommitCallbackAdapter() {
	// @Override
	// public boolean baseVersionOutOfDate(final ESLocalProject getLocalProject(), IProgressMonitor progressMonitor) {
	// ESPrimaryVersionSpec baseVersion = getLocalProject().getBaseVersion();
	// try {
	// final ESPrimaryVersionSpec version = getLocalProject().resolveVersionSpec(ESVersionSpec.FACTORY
	// .createHEAD(baseVersion), monitor);
	// getLocalProject().update(version, new UpdateCallbackAdapter() {
	// @Override
	// public boolean conflictOccurred(
	// org.eclipse.emf.emfstore.client.ESChangeConflict changeConflict,
	// IProgressMonitor progressMonitor) {
	// try {
	// return getLocalProject().merge(version, changeConflict,
	// new TestConflictResolver(
	// false, 1), null, new NullProgressMonitor());
	// } catch (ESException e) {
	// fail("Merge failed.");
	// }
	// return false;
	// };
	// }, new NullProgressMonitor());
	// } catch (ESException e) {
	// fail("Expected ChangeConflictException");
	// }
	// return true;
	// }
	// }, new NullProgressMonitor());
	// assertEquals("B", checkedoutPlayer.getName());
	// getLocalProject().update(monitor);
	// assertEquals("B", player.getName());
	// assertTrue(EMFStoreClientUtil.areEqual(getLocalProject(), checkedoutCopy));
	// }
}
