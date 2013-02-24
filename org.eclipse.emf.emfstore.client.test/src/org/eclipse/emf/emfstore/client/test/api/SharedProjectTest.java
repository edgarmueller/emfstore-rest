package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.test.CommitCallbackAdapter;
import org.eclipse.emf.emfstore.client.test.UpdateCallbackAdapter;
import org.eclipse.emf.emfstore.client.test.server.api.util.TestConflictResolver;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreClientUtil;
import org.eclipse.emf.emfstore.internal.client.model.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.ESLogMessage;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;
import org.eclipse.emf.emfstore.server.model.query.ESRangeQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SharedProjectTest extends BaseSharedProjectTest {

	// TODO: OTS
	private ESLogMessage logMessage;
	private ICommitCallback callback;
	private IUpdateCallback updateCallback;
	private ESPrimaryVersionSpec target;

	private boolean conflictOccurred;
	private boolean noLocalChangesOccurred;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testShare() {
		try {
			ESRemoteProject remoteProject = localProject.getRemoteProject();
			assertNotNull(remoteProject);
			assertTrue(localProject.isShared());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCommit() {
		try {
			ESPrimaryVersionSpec base = localProject.getBaseVersion();
			addPlayerToProject();
			ESPrimaryVersionSpec head = localProject.commit(new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	public void testCommitWithoutChange() throws ESException {
		localProject.commit(null, new CommitCallbackAdapter() {
			@Override
			public void noLocalChanges(ESLocalProject projectSpace) {
				noLocalChangesOccurred = true;
			};
		}, new NullProgressMonitor());
		assertTrue(noLocalChangesOccurred);
	}

	@Test
	public void testCommitLog() {

		try {
			ESPrimaryVersionSpec base = localProject.getBaseVersion();
			addPlayerToProject();
			ESPrimaryVersionSpec head = localProject.commit(logMessage, callback, new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCommitBranch() {

		try {
			ESPrimaryVersionSpec base = localProject.getBaseVersion();
			addPlayerToProject();
			ESBranchVersionSpec branch = ESVersionSpec.FACTORY.createBRANCH("newBranch");
			ESPrimaryVersionSpec head = localProject.commitToBranch(branch, logMessage, callback,
				new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	public void testCommitBranchWithoutChange() throws ESException {
		ESBranchVersionSpec branch = ESVersionSpec.FACTORY.createBRANCH("newBranch");
		localProject.commitToBranch(branch, logMessage, new CommitCallbackAdapter() {
			@Override
			public void noLocalChanges(ESLocalProject projectSpace) {
				noLocalChangesOccurred = true;
			};
		}, new NullProgressMonitor());
		assertTrue(noLocalChangesOccurred);
	}

	@Test
	public void testBranchesOnlyTrunk() {
		try {
			List<? extends ESBranchInfo> branches = localProject.getBranches(new NullProgressMonitor());
			assertEquals(1, branches.size());
			// TODO assert branch name
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testBranches() {

		try {
			List<? extends ESBranchInfo> branches = localProject.getBranches(new NullProgressMonitor());
			assertEquals(1, branches.size());

			addPlayerToProject();
			ESBranchVersionSpec branch = ESVersionSpec.FACTORY.createBRANCH("newBranch");
			ESPrimaryVersionSpec head = localProject.commitToBranch(branch, logMessage, callback,
				new NullProgressMonitor());
			branches = localProject.getBranches(new NullProgressMonitor());
			assertEquals(2, branches.size());
			// TODO assert branch names
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testHistoryInfoOnlyTrunk() {
		try {
			ESRangeQuery query = ESHistoryQuery.FACTORY.rangeQuery(localProject.getBaseVersion(), 1, 1, true, true,
				true,
				true);
			NullProgressMonitor monitor = new NullProgressMonitor();
			List<ESHistoryInfo> infos = localProject.getHistoryInfos(query, monitor);
			assertEquals(1, infos.size());

			addPlayerToProject();

			assertEquals(0, localProject.getBaseVersion().getIdentifier());
			ESPrimaryVersionSpec head = localProject.commit(logMessage, callback, new NullProgressMonitor());
			assertEquals(1, localProject.getBaseVersion().getIdentifier());
			infos = localProject.getHistoryInfos(ESHistoryQuery.FACTORY.rangeQuery(head, 1, 1, true, true, true, true),
				monitor);
			assertEquals(2, infos.size());
			// TODO assert infos
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testHistoryInfoBranch() {
		try {
			ESRangeQuery query = ESHistoryQuery.FACTORY.rangeQuery(localProject.getBaseVersion(), 1, 1, true, true,
				true,
				true);
			NullProgressMonitor monitor = new NullProgressMonitor();

			List<ESHistoryInfo> infos = localProject.getHistoryInfos(query, monitor);
			assertEquals(1, infos.size());

			addPlayerToProject();
			ESBranchVersionSpec branch = ESVersionSpec.FACTORY.createBRANCH("newBranch");
			ESPrimaryVersionSpec head = localProject.commitToBranch(branch, logMessage, callback,
				new NullProgressMonitor());
			infos = localProject.getHistoryInfos(query, monitor);
			assertEquals(2, infos.size());
			// TODO assert infos
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	private void addPlayerToProject() {
		ProjectChangeUtil.addPlayerToProject(localProject);
	}

	@Test
	public void testHasUncommitedChanges() {
		assertFalse(localProject.hasUncommitedChanges());
		addPlayerToProject();
		assertTrue(localProject.hasUncommitedChanges());
		try {
			localProject.commit(logMessage, callback, new NullProgressMonitor());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
		assertFalse(localProject.hasUncommitedChanges());

	}

	@Test(expected = BaseVersionOutdatedException.class)
	public void testMergeAndExpectBaseVersionOutOfDateException() throws ESException {
		NullProgressMonitor monitor = new NullProgressMonitor();
		final Player player = ProjectChangeUtil.addPlayerToProject(localProject);
		localProject.commit(monitor);
		ESLocalProject checkedoutCopy = localProject.getRemoteProject().checkout(monitor);
		final Player checkedoutPlayer = (Player) checkedoutCopy.getModelElements().get(0);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				player.setName("A");
				return null;
			}
		});

		localProject.commit(monitor);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				checkedoutPlayer.setName("B");
				return null;
			}
		});
		checkedoutCopy.commit(monitor);
	}

	@Test
	public void testMerge() throws ESException {

		final NullProgressMonitor monitor = new NullProgressMonitor();
		final Player player = ProjectChangeUtil.addPlayerToProject(localProject);
		localProject.commit(monitor);
		ESLocalProject checkedoutCopy = localProject.getRemoteProject().checkout(monitor);
		final Player checkedoutPlayer = (Player) checkedoutCopy.getModelElements().get(0);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				player.setName("A");
				return null;
			}
		});

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				localProject.commit(monitor);
				return null;
			}
		});

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				checkedoutPlayer.setName("B");
				return null;
			}
		});

		checkedoutCopy.commit(null, new CommitCallbackAdapter() {
			@Override
			public boolean baseVersionOutOfDate(final ESLocalProject localProject, IProgressMonitor progressMonitor) {
				ESPrimaryVersionSpec baseVersion = localProject.getBaseVersion();
				try {
					final ESPrimaryVersionSpec version = localProject.resolveVersionSpec(ESVersionSpec.FACTORY
						.createHEAD(baseVersion), monitor);
					localProject.update(version, new UpdateCallbackAdapter() {
						@Override
						public boolean conflictOccurred(
							org.eclipse.emf.emfstore.client.ESChangeConflict changeConflict,
							IProgressMonitor progressMonitor) {
							try {
								return localProject.merge(version, changeConflict,
									new TestConflictResolver(
										false, 1), null, new NullProgressMonitor());
							} catch (ESException e) {
								fail("Merge failed.");
							}
							return false;
						};
					}, new NullProgressMonitor());
				} catch (ESException e) {
					fail("Expected ChangeConflictException");
				}
				return true;
			}
		}, new NullProgressMonitor());
		assertEquals("B", checkedoutPlayer.getName());
		localProject.update(monitor);
		assertEquals("B", player.getName());
		assertTrue(EMFStoreClientUtil.areEqual(localProject, checkedoutCopy));
	}
}
