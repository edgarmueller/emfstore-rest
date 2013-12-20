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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithRemoteProject;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemoteProjectTest extends ESTestWithRemoteProject {

	private static final String BRANCH_NAME = "trunk"; //$NON-NLS-1$
	private static final String TAG_NAME = "MyTag"; //$NON-NLS-1$

	@BeforeClass
	public static void beforeClass() {
		startEMFStore();
	}

	@AfterClass
	public static void afterClass() {
		stopEMFStore();
	}

	@Test
	public void testGetServer() {
		assertEquals(getServer(), getRemoteProject().getServer());
	}

	@Test
	public void testGetProjectName() {
		assertEquals(ProjectUtil.defaultName(), getRemoteProject().getProjectName());
	}

	@Test
	public void testGetProjectID() {
		assertNotNull(getRemoteProject().getGlobalProjectId());
	}

	@Test
	public void testFetchAndAddToWorkspace() {
		try {
			final ESLocalProject localProject = getRemoteProject().fetch(getRemoteProject().getProjectName(),
				getUsersession(),
				getRemoteProject().getHeadVersion(new NullProgressMonitor()),
				new NullProgressMonitor());

			final ESWorkspace ws = ESWorkspaceProvider.INSTANCE.getWorkspace();
			// one project is always available
			assertEquals(1, ws.getLocalProjects().size());

			localProject.addToWorkspace(new NullProgressMonitor());

			assertEquals(2, ws.getLocalProjects().size());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheckoutSession() {
		try {
			final ESLocalProject localProject = getRemoteProject().checkout(
				getRemoteProject().getProjectName(),
				getUsersession(),
				new NullProgressMonitor());
			assertEquals(getRemoteProject().getProjectName(), localProject.getProjectName());
			assertEquals(getRemoteProject().getGlobalProjectId(), localProject.getRemoteProject().getGlobalProjectId());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheckoutSessionProgress() {
		try {
			final ESLocalProject localProject = getRemoteProject().checkout(getRemoteProject().getProjectName(),
				getUsersession(),
				new NullProgressMonitor());
			assertEquals(getRemoteProject().getProjectName(), localProject.getProjectName());
			assertEquals(getRemoteProject().getGlobalProjectId(), localProject.getRemoteProject().getGlobalProjectId());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheckoutSessionProgressNoFetch() {
		try {
			final ESLocalProject localProject = getRemoteProject().checkout(
				getRemoteProject().getProjectName(),
				getUsersession(),
				getRemoteProject().getHeadVersion(new NullProgressMonitor()),
				new NullProgressMonitor());
			assertEquals(getRemoteProject().getProjectName(), localProject.getProjectName());
			assertEquals(getRemoteProject().getGlobalProjectId(), localProject.getRemoteProject().getGlobalProjectId());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheckoutSessionProgressFetch() {
		try {
			final ESLocalProject localProject = getRemoteProject().checkout(getRemoteProject().getProjectName(),
				getUsersession(),
				getRemoteProject().getHeadVersion(new NullProgressMonitor()),
				new NullProgressMonitor());
			assertEquals(getRemoteProject().getProjectName(), localProject.getProjectName());
			assertEquals(getRemoteProject().getGlobalProjectId(), localProject.getRemoteProject().getGlobalProjectId());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetBranches() {
		try {
			final List<? extends ESBranchInfo> branches = getRemoteProject().getBranches(new NullProgressMonitor());
			assertEquals(1, branches.size());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetHeadVersion() throws ESException {
		ESPrimaryVersionSpec versionSpec;
		versionSpec = getRemoteProject().getHeadVersion(new NullProgressMonitor());
		assertNotNull(versionSpec);
	}

	@Test
	public void testGetHeadVersionFetch() throws ESException {
		final ESPrimaryVersionSpec versionSpec = getRemoteProject().getHeadVersion(new NullProgressMonitor());
		assertNotNull(versionSpec);
	}

	@Test
	public void testGetHistoryInfosSession() throws ESException {
		final NullProgressMonitor monitor = new NullProgressMonitor();
		final List<? extends ESHistoryInfo> historyInfos = getRemoteProject().getHistoryInfos(
			getUsersession(),
			ESHistoryQuery.FACTORY
				.pathQuery(getRemoteProject().getHeadVersion(monitor),
					getRemoteProject().getHeadVersion(monitor), true, true), monitor);
		assertEquals(1, historyInfos.size());
	}

	@Test
	public void testGetHistoryInfos() throws ESException {
		final NullProgressMonitor monitor = new NullProgressMonitor();
		final List<? extends ESHistoryInfo> historyInfos = getRemoteProject().getHistoryInfos(
			ESHistoryQuery.FACTORY.pathQuery(
				getRemoteProject().getHeadVersion(monitor),
				getRemoteProject().getHeadVersion(monitor), true, true), monitor);
		assertEquals(1, historyInfos.size());
	}

	@Test
	public void testAddTag() throws ESException {
		final ESTagVersionSpec tagSpec = ESVersionSpec.FACTORY.createTAG(TAG_NAME, BRANCH_NAME);
		final NullProgressMonitor monitor = new NullProgressMonitor();
		getRemoteProject().addTag(getRemoteProject().getHeadVersion(monitor), tagSpec, monitor);
		assertEquals(getRemoteProject().getHeadVersion(new NullProgressMonitor()),
			getRemoteProject().resolveVersionSpec(tagSpec, new NullProgressMonitor()));
	}

	@Test(expected = ESException.class)
	public void testRemoveTag() throws ESException {
		final ESTagVersionSpec tagSpec = ESVersionSpec.FACTORY.createTAG(TAG_NAME, BRANCH_NAME);
		final NullProgressMonitor monitor = new NullProgressMonitor();
		getRemoteProject().addTag(getRemoteProject().getHeadVersion(monitor), tagSpec, monitor);
		assertEquals(getRemoteProject().getHeadVersion(new NullProgressMonitor()),
			getRemoteProject().resolveVersionSpec(tagSpec, new NullProgressMonitor()));
		getRemoteProject().removeTag(getRemoteProject().getHeadVersion(new NullProgressMonitor()), tagSpec,
			new NullProgressMonitor());
		getRemoteProject().resolveVersionSpec(tagSpec, new NullProgressMonitor());
		fail("If no tag is there we should get an exception!"); //$NON-NLS-1$
	}

	@Test
	public void testResolveVersion() throws ESException {
		final ESTagVersionSpec tagSpec = ESVersionSpec.FACTORY.createTAG(TAG_NAME, BRANCH_NAME);
		final NullProgressMonitor monitor = new NullProgressMonitor();
		getRemoteProject().addTag(getRemoteProject().getHeadVersion(monitor), tagSpec, monitor);
		assertEquals(getRemoteProject().getHeadVersion(new NullProgressMonitor()),
			getRemoteProject().resolveVersionSpec(tagSpec, new NullProgressMonitor()));
	}

	@Test
	public void testResolveVersionSession() throws ESException {
		final ESTagVersionSpec tagSpec = ESVersionSpec.FACTORY.createTAG(TAG_NAME, BRANCH_NAME);
		final NullProgressMonitor monitor = new NullProgressMonitor();
		getRemoteProject().addTag(getRemoteProject().getHeadVersion(monitor), tagSpec, monitor);
		assertEquals(getRemoteProject().getHeadVersion(new NullProgressMonitor()),
			getRemoteProject().resolveVersionSpec(getUsersession(), tagSpec, new NullProgressMonitor()));

	}
}
