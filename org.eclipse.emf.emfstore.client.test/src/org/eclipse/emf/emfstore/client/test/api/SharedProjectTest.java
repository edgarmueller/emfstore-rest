package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.IChangeConflict;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.client.IRemoteProject;
import org.eclipse.emf.emfstore.client.test.server.api.util.TestConflictResolver;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.ILogMessage;
import org.eclipse.emf.emfstore.server.model.api.query.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IPrimaryVersionSpec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SharedProjectTest extends BaseSharedProjectTest {

	// TODO: OTS
	private ILogMessage logMessage;
	private ICommitCallback callback;
	private IBranchVersionSpec branch;
	private IUpdateCallback updateCallback;
	private IPrimaryVersionSpec target;
	private IChangeConflict changeConflict;
	private IHistoryQuery query;

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

	@Test(expected = EMFStoreException.class)
	public void testCommitWithoutChange() throws EMFStoreException {
		localProject.commit();
		fail("Expects Exception on empty commit!");
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

	@Test(expected = EMFStoreException.class)
	public void testCommitLogWithoutChange() throws EMFStoreException {
		IPrimaryVersionSpec head = localProject.commit(logMessage, callback, new NullProgressMonitor());
		fail("Expects Exception on empty commit!");
	}

	@Test
	public void testCommitBranch() {

		try {
			IPrimaryVersionSpec base = localProject.getBaseVersion();
			addPlayerToProject();
			IPrimaryVersionSpec head = localProject.commitToBranch(branch, logMessage, callback,
				new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test(expected = EMFStoreException.class)
	public void testCommitBranchWithoutChange() throws EMFStoreException {
		IPrimaryVersionSpec head = localProject.commitToBranch(branch, logMessage, callback, new NullProgressMonitor());
		fail("Expects Exception on empty commit!");
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

			List<? extends IHistoryInfo> infos = localProject.getHistoryInfos(query);
			assertEquals(1, infos.size());

			addPlayerToProject();

			IPrimaryVersionSpec head = localProject.commit(logMessage, callback, new NullProgressMonitor());
			infos = localProject.getHistoryInfos(query);
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
			List<? extends IHistoryInfo> infos = localProject.getHistoryInfos(query);
			assertEquals(1, infos.size());

			addPlayerToProject();

			IPrimaryVersionSpec head = localProject.commitToBranch(branch, logMessage, callback,
				new NullProgressMonitor());
			infos = localProject.getHistoryInfos(query);
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

	@Test
	public void testMerge() throws EMFStoreException {
		ILocalProject localProject2 = workspace.createLocalProject("TestProject2", "My Test Project2");
		localProject2.shareProject();
		ProjectChangeUtil.addPlayerToProject(localProject2);
		localProject2.commitToBranch(branch, logMessage, callback, new NullProgressMonitor());
		assertFalse(localProject.hasUncommitedChanges());
		assertFalse(localProject2.hasUncommitedChanges());
		localProject.merge(target, changeConflict, new TestConflictResolver(true, 0), updateCallback,
			new NullProgressMonitor());
		assertFalse(localProject.hasUncommitedChanges());
		assertEquals(1, localProject.getAllModelElementsByClass(Player.class).size());
	}
}
