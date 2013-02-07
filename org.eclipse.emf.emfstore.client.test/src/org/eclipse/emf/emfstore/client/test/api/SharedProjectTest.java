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
import org.eclipse.emf.emfstore.client.api.ILocalProject;
import org.eclipse.emf.emfstore.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.client.test.server.api.util.TestConflictResolver;
import org.eclipse.emf.emfstore.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IChangePackage;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.ILogMessage;
import org.eclipse.emf.emfstore.server.model.api.query.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IVersionSpec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		IPrimaryVersionSpec spec = localProject.commit();
		assertEquals(localProject.getBaseVersion().getIdentifier(), spec.getIdentifier());
		fail("Expects Exception on empty commit!");
	}

	@Test
	public void testCommitLogNoCallback() {

		try {
			IPrimaryVersionSpec base = localProject.getBaseVersion();
			addPlayerToProject();
			IPrimaryVersionSpec head = localProject.commit(ILogMessage.FACTORY.createLogMessage("MyLog", "me"), null,
				new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCommitLog() {

		try {
			IPrimaryVersionSpec base = localProject.getBaseVersion();
			addPlayerToProject();
			IPrimaryVersionSpec head = localProject.commit(ILogMessage.FACTORY.createLogMessage("MyLog", "me"),
				new ICommitCallback() {

					public void noLocalChanges(ILocalProject projectSpace) {
					}

					public boolean inspectChanges(ILocalProject project, IChangePackage changePackage,
						IModelElementIdToEObjectMapping idToEObjectMapping) {
						return true;
					}

					public boolean checksumCheckFailed(ILocalProject projectSpace, IPrimaryVersionSpec versionSpec,
						IProgressMonitor monitor) throws EMFStoreException {
						fail("Checksum should not fail!");
						return false;
					}

					public boolean baseVersionOutOfDate(ILocalProject project, IProgressMonitor progressMonitor) {
						fail("Base Version should not be outdated!");
						return false;
					}
				}, new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test(expected = EMFStoreException.class)
	public void testCommitLogWithoutChangeNoCallback() throws EMFStoreException {
		IPrimaryVersionSpec head = localProject.commit(ILogMessage.FACTORY.createLogMessage("MyLog", "me"), null,
			new NullProgressMonitor());
		assertEquals(localProject.getBaseVersion().getIdentifier(), head.getIdentifier());
		fail("Expects Exception on empty commit!");
	}

	@Test(expected = EMFStoreException.class)
	public void testCommitLogWithoutChange() throws EMFStoreException {
		IPrimaryVersionSpec head = localProject.commit(ILogMessage.FACTORY.createLogMessage("MyLog", "me"),
			new ICommitCallback() {

				public void noLocalChanges(ILocalProject projectSpace) {
				}

				public boolean inspectChanges(ILocalProject project, IChangePackage changePackage,
					IModelElementIdToEObjectMapping idToEObjectMapping) {
					fail("Merging should not be necessary!");
					return false;
				}

				public boolean checksumCheckFailed(ILocalProject projectSpace, IPrimaryVersionSpec versionSpec,
					IProgressMonitor monitor) throws EMFStoreException {
					fail("Checksum should not fail!");
					return false;
				}

				public boolean baseVersionOutOfDate(ILocalProject project, IProgressMonitor progressMonitor) {
					fail("Base Version should not be outdated!");
					return false;
				}
			}, new NullProgressMonitor());
		fail("Expects Exception on empty commit!");
	}

	@Test
	public void testCommitBranchNoCallback() {

		try {
			IPrimaryVersionSpec base = localProject.getBaseVersion();
			addPlayerToProject();
			IPrimaryVersionSpec head = localProject.commitToBranch(IVersionSpec.FACTORY.createBRANCH("MyBranch"),
				ILogMessage.FACTORY.createLogMessage("MyLog", "Me"), null,
				new NullProgressMonitor());
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
			IPrimaryVersionSpec head = localProject.commitToBranch(IVersionSpec.FACTORY.createBRANCH("MyBranch"),
				ILogMessage.FACTORY.createLogMessage("MyLog", "Me"), new ICommitCallback() {

					public void noLocalChanges(ILocalProject projectSpace) {
					}

					public boolean inspectChanges(ILocalProject project, IChangePackage changePackage,
						IModelElementIdToEObjectMapping idToEObjectMapping) {
						return true;
					}

					public boolean checksumCheckFailed(ILocalProject projectSpace, IPrimaryVersionSpec versionSpec,
						IProgressMonitor monitor) throws EMFStoreException {
						fail("Checksum should not fail!");
						return false;
					}

					public boolean baseVersionOutOfDate(ILocalProject project, IProgressMonitor progressMonitor) {
						fail("Base Version should not be outdated!");
						return false;
					}
				},
				new NullProgressMonitor());
			assertNotSame(base, head);
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test(expected = EMFStoreException.class)
	public void testCommitBranchWithoutChangeNoCallback() throws EMFStoreException {
		IPrimaryVersionSpec head = localProject.commitToBranch(IVersionSpec.FACTORY.createBRANCH("MyBranch"),
			ILogMessage.FACTORY.createLogMessage("MyLog", "Me"), null, new NullProgressMonitor());
		fail("Expects Exception on empty commit!");
	}

	@Test(expected = EMFStoreException.class)
	public void testCommitBranchWithoutChange() throws EMFStoreException {
		IPrimaryVersionSpec head = localProject.commitToBranch(IVersionSpec.FACTORY.createBRANCH("MyBranch"),
			ILogMessage.FACTORY.createLogMessage("MyLog", "Me"), new ICommitCallback() {

				public void noLocalChanges(ILocalProject projectSpace) {
				}

				public boolean inspectChanges(ILocalProject project, IChangePackage changePackage,
					IModelElementIdToEObjectMapping idToEObjectMapping) {
					fail("Merging should not be necessary!");
					return false;
				}

				public boolean checksumCheckFailed(ILocalProject projectSpace, IPrimaryVersionSpec versionSpec,
					IProgressMonitor monitor) throws EMFStoreException {
					fail("Checksum should not fail!");
					return false;
				}

				public boolean baseVersionOutOfDate(ILocalProject project, IProgressMonitor progressMonitor) {
					fail("Base Version should not be outdated!");
					return false;
				}
			}, new NullProgressMonitor());
		fail("Expects Exception on empty commit!");
	}

	@Test
	public void testBranchesOnlyTrunk() {
		try {
			List<? extends IBranchInfo> branches = localProject.getBranches();
			assertEquals(1, branches.size());
			assertEquals(IVersionSpec.BRANCH_DEFAULT_NAME, branches.get(0).getName());
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

			IPrimaryVersionSpec head = localProject.commitToBranch(IVersionSpec.FACTORY.createBRANCH("A"),
				ILogMessage.FACTORY.createLogMessage("MyLog", "Me"), null,
				new NullProgressMonitor());
			branches = localProject.getBranches();
			assertEquals(2, branches.size());
			assertEquals("A", branches.get(1).getName());
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testHistoryInfoOnlyTrunk() {
		try {
			List<? extends IHistoryInfo> infos = localProject.getHistoryInfos(IHistoryQuery.FACTORY.pathQuery(
				localProject.getBaseVersion(), localProject.getBaseVersion(), true, true));
			assertEquals(1, infos.size());

			addPlayerToProject();

			IPrimaryVersionSpec head = localProject.commit(ILogMessage.FACTORY.createLogMessage("MyLog", "Me"), null,
				new NullProgressMonitor());
			infos = localProject.getHistoryInfos(IHistoryQuery.FACTORY.rangeQuery(localProject.getBaseVersion(), 1, 1,
				true, false, false, false));
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
			List<? extends IHistoryInfo> infos = localProject.getHistoryInfos(IHistoryQuery.FACTORY.pathQuery(
				localProject.getBaseVersion(), localProject.getBaseVersion(), true, true));
			assertEquals(1, infos.size());

			addPlayerToProject();

			IPrimaryVersionSpec head = localProject.commitToBranch(IVersionSpec.FACTORY.createBRANCH("A"),
				ILogMessage.FACTORY.createLogMessage("MyLog", "Me"), null,
				new NullProgressMonitor());
			infos = localProject.getHistoryInfos(IHistoryQuery.FACTORY.rangeQuery(localProject.getBaseVersion(), 1, 1,
				true, false, false, false));

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
			localProject.commit(ILogMessage.FACTORY.createLogMessage("MyLog", "Me"), null, new NullProgressMonitor());
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
		IPrimaryVersionSpec branchSpec = localProject2.commitToBranch(IVersionSpec.FACTORY.createBRANCH("A"),
			ILogMessage.FACTORY.createLogMessage("MyLog", "Me"), null, new NullProgressMonitor());
		assertFalse(localProject.hasUncommitedChanges());
		assertFalse(localProject2.hasUncommitedChanges());
		localProject.mergeBranch(branchSpec, new TestConflictResolver(true, 0));
		assertFalse(localProject.hasUncommitedChanges());
		assertEquals(1, localProject.getAllModelElementsByClass(Player.class).size());
	}
}
