package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.junit.Test;

public class RemoteProjectTest extends BaseServerWithProjectTest {

	@Test
	public void testGetServer() {
		assertEquals(server, remoteProject.getServer());
	}

	@Test
	public void testGetProjectName() {
		assertEquals(projectName, remoteProject.getProjectName());
	}

	@Test
	public void testGetProjectID() {
		assertNotNull(remoteProject.getGlobalProjectId());
	}

	@Test
	public void testCheckoutSession() {
		try {
			ESLocalProject localProject = remoteProject.checkout(usersession,
				new NullProgressMonitor(), "testCheckout");
			assertEquals(remoteProject.getProjectName(), localProject.getProjectName());
			assertEquals(remoteProject.getGlobalProjectId(), localProject.getRemoteProject().getGlobalProjectId());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheckoutSessionProgress() {
		try {
			ESLocalProject localProject = remoteProject.checkout(usersession,
				new NullProgressMonitor(), "testCheckout");
			assertEquals(remoteProject.getProjectName(), localProject.getProjectName());
			assertEquals(remoteProject.getGlobalProjectId(), localProject.getRemoteProject().getGlobalProjectId());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheckoutSessionProgressNoFetch() {
		try {
			ESLocalProject localProject = remoteProject.checkout(usersession,
				remoteProject.getHeadVersion(new NullProgressMonitor()),
				new NullProgressMonitor(), "testCheckout");
			assertEquals(remoteProject.getProjectName(), localProject.getProjectName());
			assertEquals(remoteProject.getGlobalProjectId(), localProject.getRemoteProject().getGlobalProjectId());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheckoutSessionProgressFetch() {
		try {
			ESLocalProject localProject = remoteProject.checkout(usersession,
				remoteProject.getHeadVersion(new NullProgressMonitor()),
				new NullProgressMonitor(), "testCheckout");
			assertEquals(remoteProject.getProjectName(), localProject.getProjectName());
			assertEquals(remoteProject.getGlobalProjectId(), localProject.getRemoteProject().getGlobalProjectId());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetBranches() {
		try {
			List<? extends ESBranchInfo> branches = remoteProject.getBranches(new NullProgressMonitor());
			assertEquals(1, branches.size());
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetHeadVersion() throws ESException {
		ESPrimaryVersionSpec versionSpec;
		versionSpec = remoteProject.getHeadVersion(new NullProgressMonitor());
		assertNotNull(versionSpec);
	}

	@Test
	public void testGetHeadVersionFetch() throws ESException {
		ESPrimaryVersionSpec versionSpec = remoteProject.getHeadVersion(new NullProgressMonitor());
		assertNotNull(versionSpec);
	}

	@Test
	public void testGetHistoryInfosSession() throws ESException {
		NullProgressMonitor monitor = new NullProgressMonitor();
		;
		List<? extends ESHistoryInfo> historyInfos = remoteProject.getHistoryInfos(
			usersession,
			ESHistoryQuery.FACTORY
				.pathQuery(remoteProject.getHeadVersion(monitor),
					remoteProject.getHeadVersion(monitor), true, true), monitor);
		assertEquals(1, historyInfos.size());
	}

	@Test
	public void testGetHistoryInfos() throws ESException {
		NullProgressMonitor monitor = new NullProgressMonitor();
		;
		List<? extends ESHistoryInfo> historyInfos = remoteProject.getHistoryInfos(ESHistoryQuery.FACTORY.pathQuery(
			remoteProject.getHeadVersion(monitor),
			remoteProject.getHeadVersion(monitor), true, true), monitor);
		assertEquals(1, historyInfos.size());
	}

	@Test
	public void testAddTag() throws ESException {
		ESTagVersionSpec tagSpec = ESVersionSpec.FACTORY.createTAG("MyTag", "trunk");
		NullProgressMonitor monitor = new NullProgressMonitor();
		;
		remoteProject.addTag(remoteProject.getHeadVersion(monitor), tagSpec, monitor);
		assertEquals(remoteProject.getHeadVersion(new NullProgressMonitor()),
			remoteProject.resolveVersionSpec(tagSpec, new NullProgressMonitor()));
	}

	@Test(expected = ESException.class)
	public void testRemoveTag() throws ESException {
		ESTagVersionSpec tagSpec = ESVersionSpec.FACTORY.createTAG("MyTag", "trunk");
		NullProgressMonitor monitor = new NullProgressMonitor();
		remoteProject.addTag(remoteProject.getHeadVersion(monitor), tagSpec, monitor);
		assertEquals(remoteProject.getHeadVersion(new NullProgressMonitor()),
			remoteProject.resolveVersionSpec(tagSpec, new NullProgressMonitor()));
		remoteProject.removeTag(remoteProject.getHeadVersion(new NullProgressMonitor()), tagSpec,
			new NullProgressMonitor());
		remoteProject.resolveVersionSpec(tagSpec, new NullProgressMonitor());
		fail("If no tag is there we should get an exception!");
	}

	@Test
	public void testResolveVersion() throws ESException {
		ESTagVersionSpec tagSpec = ESVersionSpec.FACTORY.createTAG("MyTag", "trunk");
		NullProgressMonitor monitor = new NullProgressMonitor();
		remoteProject.addTag(remoteProject.getHeadVersion(monitor), tagSpec, monitor);
		assertEquals(remoteProject.getHeadVersion(new NullProgressMonitor()),
			remoteProject.resolveVersionSpec(tagSpec, new NullProgressMonitor()));
	}

	@Test
	public void testResolveVersionSession() throws ESException {
		ESTagVersionSpec tagSpec = ESVersionSpec.FACTORY.createTAG("MyTag", "trunk");
		NullProgressMonitor monitor = new NullProgressMonitor();
		remoteProject.addTag(remoteProject.getHeadVersion(monitor), tagSpec, monitor);
		assertEquals(remoteProject.getHeadVersion(new NullProgressMonitor()),
			remoteProject.resolveVersionSpec(usersession, tagSpec, new NullProgressMonitor()));

	}
}
