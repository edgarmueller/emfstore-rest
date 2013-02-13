package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.client.IRemoteProject;
import org.eclipse.emf.emfstore.client.IServer;
import org.eclipse.emf.emfstore.client.IUsersession;
import org.eclipse.emf.emfstore.client.IWorkspace;
import org.eclipse.emf.emfstore.client.IWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.server.api.util.TestConflictResolver;
import org.eclipse.emf.emfstore.common.model.IModelElementId;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.ILogMessage;
import org.eclipse.emf.emfstore.server.model.query.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.IVersionSpec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnsharedLocalProjectTest extends BaseEmptyEmfstoreTest {

	private final IWorkspace workspace = IWorkspaceProvider.INSTANCE.getWorkspace();
	private ILocalProject localProject;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		localProject = workspace.createLocalProject("TestProject", "My Test Project");
	}

	@Override
	@After
	public void tearDown() throws Exception {
		List<ILocalProject> projects = new ArrayList<ILocalProject>(workspace.getLocalProjects());
		for (ILocalProject lp : projects) {
			lp.delete(new NullProgressMonitor());
		}
		super.tearDown();
	}

	@Test
	public void testProjectName() {
		assertEquals("TestProject", localProject.getProjectName());
	}

	@Test
	public void testProjectID() {
		// unshared projects have no project ID
		assertNull(localProject.getProjectId());
	}

	@Test(expected = RuntimeException.class)
	public void testAddTag() throws EMFStoreException {
		// a tag can not be created for an unshared project
		localProject.addTag(localProject.getBaseVersion(), IVersionSpec.FACTORY.createTAG("test", "test"),
			new NullProgressMonitor());
		fail("Cannot add a tag!");
	}

	@Test
	public void testIsShared() {
		assertFalse(localProject.isShared());
	}

	@Test(expected = RuntimeException.class)
	public void testCommit() throws EMFStoreException {
		// can not commit an unshared project
		localProject.commit();
		fail("Should not be able to commit an unshared Project!");
	}

	@Test(expected = RuntimeException.class)
	public void testCommit2() throws EMFStoreException {
		// can not commit an unshared project
		localProject.commit(ILogMessage.FACTORY.createLogMessage("test", "super"), null, new NullProgressMonitor());
		fail("Should not be able to commit an unshared Project!");
	}

	@Test(expected = RuntimeException.class)
	public void testCommitToBranch() throws EMFStoreException {
		localProject.commitToBranch(IVersionSpec.FACTORY.createBRANCH(localProject.getBaseVersion()),
			ILogMessage.FACTORY.createLogMessage("test", "super"), null, new NullProgressMonitor());
		fail("Should not be able to commit an unshared Project!");
	}

	@Test
	public void testGetBaseVersion() {
		// unshared project has no base version
		IPrimaryVersionSpec version = localProject.getBaseVersion();
		assertNull(version);
	}

	@Test(expected = EMFStoreException.class)
	public void testGetBranches() throws EMFStoreException {
		localProject.getBranches(new NullProgressMonitor());
		fail("Should not be able to getBranches from an unshared Project!");
	}

	@Test(expected = EMFStoreException.class)
	public void testGetHistoryInfos() throws EMFStoreException {
		Player player = ProjectChangeUtil.addPlayerToProject(localProject);
		IModelElementId id = localProject.getModelElementId(player);
		IHistoryQuery query = IHistoryQuery.FACTORY.modelelementQuery(localProject.getBaseVersion(), id, 1, 0, true,
			true);
		localProject.getHistoryInfos(query, new NullProgressMonitor());
		fail("Should not be able to getHistoryInfos from an unshared Project!");
	}

	@Test
	public void testGetLastUpdated() {
		// unshared project has no last updated date
		Date date = localProject.getLastUpdated();
		assertNull(date);
	}

	@Test(expected = EMFStoreException.class)
	public void testGetRemoteProject() throws EMFStoreException {
		IRemoteProject remoteProject = localProject.getRemoteProject();
		assertNull(remoteProject);
		fail("Should not be able to getRemoteProject from an unshared Project!");
	}

	@Test
	public void testGetUsersession() {
		IUsersession session = localProject.getUsersession();
		assertNull(session);
	}

	@Test
	public void testHasUncommitedChanges() {
		assertFalse(localProject.hasUncommitedChanges());
		ProjectChangeUtil.addPlayerToProject(localProject);
		assertTrue(localProject.hasUncommitedChanges());
	}

	@Test
	public void testHasUnsavedChanges() {
		assertFalse(localProject.hasUnsavedChanges());
		ProjectChangeUtil.addPlayerToProject(localProject);
		assertTrue(localProject.hasUnsavedChanges());
		localProject.save();
		assertFalse(localProject.hasUnsavedChanges());
	}

	@Test
	public void testImportLocalChanges() throws EMFStoreException {
		// TODO: localProject.importLocalChanges(fileName);
	}

	@Test(expected = RuntimeException.class)
	public void testIsUpdated() throws EMFStoreException {
		localProject.isUpdated();
		fail("Should not be able to check update state of an unshared Project!");
	}

	@Test(expected = RuntimeException.class)
	public void testMerge() throws EMFStoreException {
		localProject.mergeBranch(localProject.getBaseVersion(), new TestConflictResolver(false, 0),
			new NullProgressMonitor());
		fail("Should not be able to merge with head on an unshared Project!");
	}

	@Test(expected = RuntimeException.class)
	public void testMergeBranch() throws EMFStoreException {
		localProject.mergeBranch(localProject.getBaseVersion(),
			new TestConflictResolver(false, 0), new NullProgressMonitor());

		fail("Should not be able to merge with head on an unshared Project!");
	}

	@Test(expected = RuntimeException.class)
	public void testRemoveTag() throws EMFStoreException {
		localProject.removeTag(localProject.getBaseVersion(), IVersionSpec.FACTORY.createTAG("tag", "branch"),
			new NullProgressMonitor());
		fail("Should not remove a tag from an unshared Project!");
	}

	@Test(expected = RuntimeException.class)
	public void testResolveSpec() throws EMFStoreException {
		localProject.resolveVersionSpec(IVersionSpec.FACTORY.createHEAD(), new NullProgressMonitor());
		fail("Should not be able to resolve a version spec from an unshared Project!");
	}

	@Test
	public void testRevert() {
		Player player1 = ProjectChangeUtil.addPlayerToProject(localProject);
		Player player2 = ProjectChangeUtil.addPlayerToProject(localProject);
		Player player3 = ProjectChangeUtil.addPlayerToProject(localProject);

		assertTrue(localProject.getAllModelElements().contains(player1));
		assertTrue(localProject.getAllModelElements().contains(player2));
		assertTrue(localProject.getAllModelElements().contains(player3));

		localProject.revert();

		assertEquals(localProject.getAllModelElements().size(), 0);
	}

	@Test
	public void testSave() {

		ProjectChangeUtil.addPlayerToProject(localProject);

		localProject.save();
		localProject = null;
		localProject = workspace.getLocalProjects().get(0);
	}

	@Test
	public void testUndoLastOperation() {
		Player player1 = ProjectChangeUtil.addPlayerToProject(localProject);
		Player player2 = ProjectChangeUtil.addPlayerToProject(localProject);

		assertTrue(localProject.getAllModelElements().contains(player1));
		assertTrue(localProject.getAllModelElements().contains(player2));

		localProject.undoLastOperation();

		assertTrue(localProject.getAllModelElements().contains(player1));
		assertFalse(localProject.getAllModelElements().contains(player2));
	}

	@Test
	public void testUndoLastOperationsMore() {
		Player player = ProjectChangeUtil.addPlayerToProject(localProject);
		assertTrue(localProject.getAllModelElements().contains(player));
		localProject.undoLastOperations(0);
		assertTrue(localProject.getAllModelElements().contains(player));
		localProject.undoLastOperations(1);
		assertFalse(localProject.getAllModelElements().contains(player));
		localProject.undoLastOperations(2);
	}

	@Test
	public void testUndoLastOperations() {
		Player player1 = ProjectChangeUtil.addPlayerToProject(localProject);
		Player player2 = ProjectChangeUtil.addPlayerToProject(localProject);
		Player player3 = ProjectChangeUtil.addPlayerToProject(localProject);

		assertTrue(localProject.getAllModelElements().contains(player1));
		assertTrue(localProject.getAllModelElements().contains(player2));
		assertTrue(localProject.getAllModelElements().contains(player3));

		localProject.undoLastOperations(0);

		assertTrue(localProject.getAllModelElements().contains(player1));
		assertTrue(localProject.getAllModelElements().contains(player2));
		assertTrue(localProject.getAllModelElements().contains(player3));

		localProject.undoLastOperations(1);

		assertTrue(localProject.getAllModelElements().contains(player1));
		assertTrue(localProject.getAllModelElements().contains(player2));
		assertFalse(localProject.getAllModelElements().contains(player3));

		localProject.undoLastOperations(2);

		assertFalse(localProject.getAllModelElements().contains(player1));
		assertFalse(localProject.getAllModelElements().contains(player2));
		assertFalse(localProject.getAllModelElements().contains(player3));
	}

	@Test(expected = RuntimeException.class)
	public void testUpdate() throws EMFStoreException {
		localProject.update();
		fail("Should not be able to update an unshared Project!");
	}

	@Test(expected = RuntimeException.class)
	public void testUpdateVersion() throws EMFStoreException {
		localProject.update(localProject.getBaseVersion());
		fail("Should not be able to update an unshared Project!");
	}

	@Test(expected = RuntimeException.class)
	public void testUpdateVersionCallback() throws EMFStoreException {
		localProject.update(localProject.getBaseVersion(), null, new NullProgressMonitor());
		fail("Should not be able to update an unshared Project!");
	}

	@Test
	public void testShare() {
		try {
			assertNull(localProject.getUsersession());
			localProject.shareProject(new NullProgressMonitor());
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testShareSession() {
		try {
			IServer server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
			IUsersession usersession = server.login("super", "super");
			localProject.shareProject(usersession, new NullProgressMonitor());
			IRemoteProject remote = localProject.getRemoteProject();
			assertNotNull(remote);
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}
}