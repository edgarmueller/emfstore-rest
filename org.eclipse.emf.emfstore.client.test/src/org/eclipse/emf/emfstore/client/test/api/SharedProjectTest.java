package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.client.IRemoteProject;
import org.eclipse.emf.emfstore.client.test.CommitCallbackAdapter;
import org.eclipse.emf.emfstore.client.test.UpdateCallbackAdapter;
import org.eclipse.emf.emfstore.client.test.server.api.util.TestConflictResolver;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.internal.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.ILogMessage;
import org.eclipse.emf.emfstore.server.model.api.query.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.query.IRangeQuery;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IVersionSpec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SharedProjectTest extends BaseSharedProjectTest {

	// TODO: OTS
	private ILogMessage logMessage;
	private ICommitCallback callback;
	private IUpdateCallback updateCallback;
	private IPrimaryVersionSpec target;

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
			IRemoteProject remoteProject = localProject.getRemoteProject();
			assertNotNull(remoteProject);
			assertTrue(localProject.isShared());
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCommit() {
		try {
			IPrimaryVersionSpec base = localProject.getBaseVersion();
			addPlayerToProject();
			IPrimaryVersionSpec head = localProject.commit();
			assertNotSame(base, head);
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	public void testCommitWithoutChange() throws EMFStoreException {
		localProject.commit(null, new CommitCallbackAdapter() {
			@Override
			public void noLocalChanges(ILocalProject projectSpace) {
				noLocalChangesOccurred = true;
			};
		}, new NullProgressMonitor());
		assertTrue(noLocalChangesOccurred);
	}

	@Test
	public void testCommitLog() {

		try {
			IPrimaryVersionSpec base = localProject.getBaseVersion();
			addPlayerToProject();
			IPrimaryVersionSpec head = localProject.commit(logMessage, callback, new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCommitBranch() {

		try {
			IPrimaryVersionSpec base = localProject.getBaseVersion();
			addPlayerToProject();
			IBranchVersionSpec branch = IVersionSpec.FACTORY.createBRANCH("newBranch");
			IPrimaryVersionSpec head = localProject.commitToBranch(branch, logMessage, callback,
				new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	public void testCommitBranchWithoutChange() throws EMFStoreException {
		IBranchVersionSpec branch = IVersionSpec.FACTORY.createBRANCH("newBranch");
		localProject.commitToBranch(branch, logMessage, new CommitCallbackAdapter() {
			@Override
			public void noLocalChanges(ILocalProject projectSpace) {
				noLocalChangesOccurred = true;
			};
		}, new NullProgressMonitor());
		assertTrue(noLocalChangesOccurred);
	}

	@Test
	public void testBranchesOnlyTrunk() {
		try {
			List<? extends IBranchInfo> branches = localProject.getBranches(new NullProgressMonitor());
			assertEquals(1, branches.size());
			// TODO assert branch name
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testBranches() {

		try {
			List<? extends IBranchInfo> branches = localProject.getBranches(new NullProgressMonitor());
			assertEquals(1, branches.size());

			addPlayerToProject();
			IBranchVersionSpec branch = IVersionSpec.FACTORY.createBRANCH("newBranch");
			IPrimaryVersionSpec head = localProject.commitToBranch(branch, logMessage, callback,
				new NullProgressMonitor());
			branches = localProject.getBranches(new NullProgressMonitor());
			assertEquals(2, branches.size());
			// TODO assert branch names
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testHistoryInfoOnlyTrunk() {
		try {
			IRangeQuery query = IHistoryQuery.FACTORY.rangeQuery(localProject.getBaseVersion(), 1, 1, true, true, true,
				true);
			NullProgressMonitor monitor = new NullProgressMonitor();
			;
			List<IHistoryInfo> infos = localProject.getHistoryInfos(query, monitor);
			assertEquals(1, infos.size());

			addPlayerToProject();

			assertEquals(0, localProject.getBaseVersion().getIdentifier());
			IPrimaryVersionSpec head = localProject.commit(logMessage, callback, new NullProgressMonitor());
			assertEquals(1, localProject.getBaseVersion().getIdentifier());
			infos = localProject.getHistoryInfos(IHistoryQuery.FACTORY.rangeQuery(head, 1, 1, true, true, true, true),
				monitor);
			assertEquals(2, infos.size());
			// TODO assert infos
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testHistoryInfoBranch() {
		try {
			IRangeQuery query = IHistoryQuery.FACTORY.rangeQuery(localProject.getBaseVersion(), 1, 1, true, true, true,
				true);
			NullProgressMonitor monitor = new NullProgressMonitor();
			;
			List<? extends IHistoryInfo> infos = localProject.getHistoryInfos(query, monitor);
			assertEquals(1, infos.size());

			addPlayerToProject();
			IBranchVersionSpec branch = IVersionSpec.FACTORY.createBRANCH("newBranch");
			IPrimaryVersionSpec head = localProject.commitToBranch(branch, logMessage, callback,
				new NullProgressMonitor());
			infos = localProject.getHistoryInfos(query, monitor);
			assertEquals(2, infos.size());
			// TODO assert infos
		} catch (EMFStoreException e) {
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
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
		assertFalse(localProject.hasUncommitedChanges());

	}

	@Test(expected = BaseVersionOutdatedException.class)
	public void testMergeAndExpectBaseVersionOutOfDateException() throws EMFStoreException {
		NullProgressMonitor monitor = new NullProgressMonitor();
		;
		Player player = ProjectChangeUtil.addPlayerToProject(localProject);
		localProject.commit();
		ILocalProject checkedoutCopy = localProject.getRemoteProject().checkout(monitor);
		Player checkedoutPlayer = (Player) checkedoutCopy.getModelElements().get(0);

		player.setName("A");
		localProject.commit();
		checkedoutPlayer.setName("B");
		checkedoutCopy.commit();
	}

	@Test
	public void testMerge() throws EMFStoreException {

		final NullProgressMonitor monitor = new NullProgressMonitor();
		;
		Player player = ProjectChangeUtil.addPlayerToProject(localProject);
		localProject.commit();
		ILocalProject checkedoutCopy = localProject.getRemoteProject().checkout(monitor);
		Player checkedoutPlayer = (Player) checkedoutCopy.getModelElements().get(0);

		player.setName("A");
		localProject.commit();
		checkedoutPlayer.setName("B");
		checkedoutCopy.commit(null, new CommitCallbackAdapter() {
			@Override
			public boolean baseVersionOutOfDate(final ILocalProject localProject, IProgressMonitor progressMonitor) {
				IPrimaryVersionSpec baseVersion = localProject.getBaseVersion();
				try {
					final IPrimaryVersionSpec version = localProject.resolveVersionSpec(IVersionSpec.FACTORY
						.createHEAD(baseVersion), monitor);
					localProject.update(version, new UpdateCallbackAdapter() {
						@Override
						public boolean conflictOccurred(org.eclipse.emf.emfstore.client.IChangeConflict changeConflict,
							IProgressMonitor progressMonitor) {
							try {
								return localProject.merge(version, changeConflict,
									new TestConflictResolver(
										false, 1), null, new NullProgressMonitor());
							} catch (EMFStoreException e) {
								fail("Merge failed.");
							}
							return false;
						};
					}, new NullProgressMonitor());
				} catch (EMFStoreException e) {
					fail("Expected ChangeConflictException");
				}
				return true;
			}
		}, new NullProgressMonitor());
		assertEquals("B", checkedoutPlayer.getName());
		localProject.update();
		assertEquals("B", player.getName());
	}
}
