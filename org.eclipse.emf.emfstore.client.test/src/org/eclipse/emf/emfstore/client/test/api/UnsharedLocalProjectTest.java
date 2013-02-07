package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.api.ILocalProject;
import org.eclipse.emf.emfstore.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.client.api.IServer;
import org.eclipse.emf.emfstore.client.api.IUsersession;
import org.eclipse.emf.emfstore.client.api.IWorkspace;
import org.eclipse.emf.emfstore.client.api.IWorkspaceProvider;
import org.eclipse.emf.emfstore.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnsharedLocalProjectTest extends BaseEmptyEmfstoreTest {

	private final IWorkspace workspace=IWorkspaceProvider.INSTANCE.getWorkspace(); 
	private ILocalProject localProject;
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		workspace.createLocalProject("TestProject", "My Test Project");
	}

	@After
	public void tearDown() throws Exception {
		for(ILocalProject lp:workspace.getLocalProjects()){
			lp.delete();
		}
		super.tearDown();
	}

	@Test
	public void testProjectName() {
		assertEquals("TestProject", localProject.getProjectName());
	}
	@Test
	public void testProjectDescription() {
		assertEquals("My Test Project", localProject.getProjectDescription());
	}
	@Test
	public void testProjectID() {
		assertNotNull(localProject.getProjectId());
	}
	@Test
	public void testAddTag() {
		localProject.addTag(versionSpec, tag);
	}
	@Test
	public void testIsShared(){
		assertFalse(localProject.isShared());
	}
	@Test(expected=EMFStoreException.class)
	public void testCommit() throws EMFStoreException{
		IPrimaryVersionSpec  spec=localProject.commit();
		fail("Should not be able to commit an unshared Project!");
	}
	@Test(expected=EMFStoreException.class)
	public void testCommit2() throws EMFStoreException{
		IPrimaryVersionSpec  spec=localProject.commit(logMessage, callback, new NullProgressMonitor());
		fail("Should not be able to commit an unshared Project!");
	}
	@Test(expected=EMFStoreException.class)
	public void testCommitToBranch() throws EMFStoreException{
		IPrimaryVersionSpec  spec=localProject.commitToBranch(branch, logMessage, callback, new NullProgressMonitor());
		fail("Should not be able to commit an unshared Project!");
	}
	@Test
	public void testGetBaseVersion() {
		IPrimaryVersionSpec version=localProject.getBaseVersion();
		assertNotNull(version);
	}
	@Test(expected=EMFStoreException.class)
	public void testGetBranches() throws EMFStoreException {
		 List<? extends IBranchInfo> branches=localProject.getBranches();
		 fail("Should not be able to getBranches from an unshared Project!");
	}
	@Test(expected=EMFStoreException.class)
	public void testGetHistoryInfos() throws EMFStoreException {
		List<? extends IHistoryInfo> infos=localProject.getHistoryInfos(query);
		 fail("Should not be able to getHistoryInfos from an unshared Project!");
	}
	@Test
	public void testGetLastUpdated(){
		Date date=localProject.getLastUpdated();
		assertNotNull(date);
	}
	@Test(expected=EMFStoreException.class)
	public void testGetRemoteProject() throws EMFStoreException {
		IRemoteProject remoteProject=localProject.getRemoteProject();
		assertNull(remoteProject);
		fail("Should not be able to getRemoteProject from an unshared Project!");
	}
	@Test
	public void testGetUsersession(){
		IUsersession session=localProject.getUsersession();
		assertNull(session);
	}
	@Test
	public void testHasUncommitedChanges()  {
		assertFalse(localProject.hasUncommitedChanges());
		//TODO changes
		assertFalse(localProject.hasUncommitedChanges());
	}
	@Test
	public void testHasUnsavedChanges() {
		assertFalse(localProject.hasUnsavedChanges());
		//TODO changes
		assertTrue(localProject.hasUnsavedChanges());
		localProject.save();
		assertFalse(localProject.hasUnsavedChanges());
	}
	@Test
	public void testImportLocalChanges() throws EMFStoreException {
		localProject.importLocalChanges(fileName);
	}
	@Test(expected=EMFStoreException.class)
	public void testIsUpdated() throws EMFStoreException {
		boolean result=localProject.isUpdated();
		fail("Should not be able to check update state of an unshared Project!");
	}
	@Test(expected=EMFStoreException.class)
	public void testMerge() throws EMFStoreException {
		boolean result=localProject.merge(target, conflictException, conflictResolver, callback, new NullProgressMonitor());
		fail("Should not be able to merge with head on an unshared Project!");
	}
	@Test(expected=EMFStoreException.class)
	public void testMergeBranch() throws EMFStoreException {
		localProject.mergeBranch(branchSpec, conflictResolver);
		fail("Should not be able to merge with head on an unshared Project!");
	}
	@Test(expected=EMFStoreException.class)
	public void testRemoveTag() throws EMFStoreException {
		localProject.removeTag(versionSpec, tag);
		fail("Should not remove a tag from an unshared Project!");
	}
	@Test(expected=EMFStoreException.class)
	public void testRemoveTag() throws EMFStoreException {
		IPrimaryVersionSpec spec=localProject.resolveVersionSpec(versionSpec);
		fail("Should not be able to resolve a version spec from an unshared Project!");
	}
	@Test
	public void testRevert() {
		//TODO add changes
		IPrimaryVersionSpec spec=localProject.revert();
	}
	@Test
	public void testSave() {
		//TODO add changes
		localProject.save();
		localProject=null;
		localProject=workspace.getLocalProjects().get(0);
	}
	
	@Test
	public void testUndoLastOperation() {
		//TODO add changes
		localProject.undoLastOperation();
	}
	@Test
	public void testUndoLastOperations() {
		//TODO add changes
		localProject.undoLastOperations(0);
		//TODO compare
		localProject.undoLastOperations(1);
		//TODO compare
		localProject.undoLastOperations(2);
	}
	@Test(expected=EMFStoreException.class)
	public void testUpdate() throws EMFStoreException {
		IPrimaryVersionSpec spec=localProject.update();
		fail("Should not be able to update an unshared Project!");
	}
	@Test(expected=EMFStoreException.class)
	public void testUpdateVersion() throws EMFStoreException {
		IPrimaryVersionSpec spec=localProject.update(version);
		fail("Should not be able to update an unshared Project!");
	}
	@Test(expected=EMFStoreException.class)
	public void testUpdateVersionCallback() throws EMFStoreException {
		IPrimaryVersionSpec spec=localProject.update(version, callback, new NullProgressMonitor());
		fail("Should not be able to update an unshared Project!");
	}
	@Test
	public void testShare() {
		try {
			localProject.shareProject();
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}
	@Test
	public void testShareSession() {
		try {
			IServer server=IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
			IUsersession usersession=server.login("super", "super");
			localProject.shareProject(usersession,new NullProgressMonitor());
			IRemoteProject remote=localProject.getRemoteProject();
			assertNotNull(remote);
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}
}
