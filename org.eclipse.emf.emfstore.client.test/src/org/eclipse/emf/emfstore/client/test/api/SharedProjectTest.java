package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// TODO use example model
public class SharedProjectTest extends BaseSharedProjectTest {

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
			List<? extends IBranchInfo> branches = localProject.getBranches();
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
			List<? extends IBranchInfo> branches = localProject.getBranches();
			assertEquals(1, branches.size());

			addPlayerToProject();

			IPrimaryVersionSpec head = localProject.commitToBranch(branch, logMessage, callback,
				new NullProgressMonitor());
			branches = localProject.getBranches();
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
		// TODO add changes
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
	public void testHasUnsavedChanges() {
		assertFalse(localProject.hasUncommitedChanges());
		// TODO add changes
		assertFalse(localProject.hasUncommitedChanges());
	}

	@Test
	public void testMerge() {
		// TODO add changes
		localProject.merge(target, conflictException, conflictResolver, callback, new NullProgressMonitor());
		assertFalse(localProject.hasUncommitedChanges());
		// TODO add changes
		assertFalse(localProject.hasUncommitedChanges());
	}
}
