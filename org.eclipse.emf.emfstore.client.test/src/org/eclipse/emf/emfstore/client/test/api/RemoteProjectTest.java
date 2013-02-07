package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.api.ILocalProject;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.ITagVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IVersionSpec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RemoteProjectTest extends BaseServerWithProjectTest {

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
	public void testGetServer() {
		assertEquals(server, remoteProject.getServer());
	}

	@Test
	public void testGetProjectDescription() {
		assertEquals(projectDescription, remoteProject.getProjectDescription());
	}

	@Test
	public void testGetProjectName() {
		assertEquals(projectName, remoteProject.getProjectName());
	}

	@Test
	public void testGetProjectID() {
		assertNotNull(remoteProject.getProjectId());
	}

	@Test
	public void testCheckoutSession() {
		try {
			ILocalProject localProject = remoteProject.checkout(usersession);
			assertEquals(remoteProject.getProjectName(), localProject.getProjectName());
			assertEquals(remoteProject.getProjectId(), localProject.getRemoteProject().getProjectId());
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheckoutSessionProgress() {
		try {
			ILocalProject localProject = remoteProject.checkout(usersession, new NullProgressMonitor());
			assertEquals(remoteProject.getProjectName(), localProject.getProjectName());
			assertEquals(remoteProject.getProjectId(), localProject.getRemoteProject().getProjectId());
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheckoutSessionProgressNoFetch() {
		try {
			ILocalProject localProject = remoteProject.checkout(usersession, remoteProject.getHeadVersion(false),
				new NullProgressMonitor());
			assertEquals(remoteProject.getProjectName(), localProject.getProjectName());
			assertEquals(remoteProject.getProjectId(), localProject.getRemoteProject().getProjectId());
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheckoutSessionProgressFetch() {
		try {
			ILocalProject localProject = remoteProject.checkout(usersession, remoteProject.getHeadVersion(true),
				new NullProgressMonitor());
			assertEquals(remoteProject.getProjectName(), localProject.getProjectName());
			assertEquals(remoteProject.getProjectId(), localProject.getRemoteProject().getProjectId());
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetBranches() {
		try {
			List<? extends IBranchInfo> branches = remoteProject.getBranches();
			assertEquals(1, branches.size());
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetHeadVersion() throws EMFStoreException {
		IPrimaryVersionSpec versionSpec;
		versionSpec = remoteProject.getHeadVersion(false);
		assertNotNull(versionSpec);
	}

	@Test
	public void testGetHeadVersionFetch() throws EMFStoreException {
		IPrimaryVersionSpec versionSpec = remoteProject.getHeadVersion(true);
		assertNotNull(versionSpec);
	}

	@Test
	public void testGetHistoryInfosSession() throws EMFStoreException {
		List<? extends IHistoryInfo> historyInfos = remoteProject.getHistoryInfos(usersession, IHistoryQuery.FACTORY
			.pathQuery(remoteProject.getHeadVersion(false), remoteProject.getHeadVersion(false), true, true));
		assertEquals(1, historyInfos.size());
	}

	@Test
	public void testGetHistoryInfos() throws EMFStoreException {
		List<? extends IHistoryInfo> historyInfos = remoteProject.getHistoryInfos(IHistoryQuery.FACTORY.pathQuery(
			remoteProject.getHeadVersion(false), remoteProject.getHeadVersion(false), true, true));
		assertEquals(1, historyInfos.size());
	}

	@Test
	public void testAddTag() throws EMFStoreException {
		ITagVersionSpec tagSpec = IVersionSpec.FACTORY.createTAG("MyTag", "trunk");
		remoteProject.addTag(remoteProject.getHeadVersion(true), tagSpec);
		assertEquals(remoteProject.getHeadVersion(true), remoteProject.resolveVersionSpec(tagSpec));
	}

	@Test(expected = EMFStoreException.class)
	public void testRemoveTag() throws EMFStoreException {
		ITagVersionSpec tagSpec = IVersionSpec.FACTORY.createTAG("MyTag", "trunk");
		remoteProject.addTag(remoteProject.getHeadVersion(true), tagSpec);
		assertEquals(remoteProject.getHeadVersion(true), remoteProject.resolveVersionSpec(tagSpec));
		remoteProject.removeTag(remoteProject.getHeadVersion(true), tagSpec);
		remoteProject.resolveVersionSpec(tagSpec);
		fail("If no tag is there we should get an exception!");
	}

	@Test
	public void testResolveVersion() throws EMFStoreException {
		ITagVersionSpec tagSpec = IVersionSpec.FACTORY.createTAG("MyTag", "trunk");
		remoteProject.addTag(remoteProject.getHeadVersion(true), tagSpec);
		assertEquals(remoteProject.getHeadVersion(true), remoteProject.resolveVersionSpec(tagSpec));
	}

	@Test
	public void testResolveVersionSession() throws EMFStoreException {
		ITagVersionSpec tagSpec = IVersionSpec.FACTORY.createTAG("MyTag", "trunk");
		remoteProject.addTag(remoteProject.getHeadVersion(true), tagSpec);
		assertEquals(remoteProject.getHeadVersion(true), remoteProject.resolveVersionSpec(usersession, tagSpec));

	}
}
