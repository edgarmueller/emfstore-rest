/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.accesscontrol.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.test.common.mocks.ConnectionMock;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.PAPrivileges;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESSessionIdImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test whether a project admin can delete a project without deleting
 * its files on the server
 * 
 * @author emueller
 * 
 */
public class DeleteProjectTest extends ProjectAdminTest {

	@BeforeClass
	public static void beforeClass() {
		startEMFStoreWithPAProperties(
			PAPrivileges.ShareProject,
			PAPrivileges.AssignRoleToOrgUnit);
	}

	@AfterClass
	public static void afterClass() {
		stopEMFStore();
	}

	@Override
	@After
	public void after() {
		try {
			ServerUtil.deleteGroup(getSuperUsersession(), getNewGroupName());
			ServerUtil.deleteGroup(getSuperUsersession(), getNewOtherGroupName());
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
		super.after();
	}

	@Override
	@Before
	public void before() {
		super.before();
	}

	@Test
	public void delteProjectSA() throws ESException {
		makeUserSA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getLocalProject().getRemoteProject().delete(new NullProgressMonitor());
		final List<ProjectInfo> projectList = ESWorkspaceProviderImpl.getInstance().getConnectionManager()
			.getProjectList(
				ESSessionIdImpl.class.cast(getUsersession().getSessionId()).toInternalAPI());
		// TODO: not transparent with mock server
		final ConnectionMock mock = (ConnectionMock) ESWorkspaceProviderImpl.getInstance().getConnectionManager();
		assertTrue(mock.didDeleteFiles());
		assertEquals(0, projectList.size());
	}

	@Test(expected = AccessControlException.class)
	public void delteProjectNotPA() throws ESException {
		ProjectUtil.share(getUsersession(), getLocalProject());
		getLocalProject().getRemoteProject().delete(new NullProgressMonitor());
	}

	@Test
	public void deleteProjectPA() throws ESException, IOException {
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getLocalProject().getRemoteProject().delete(new NullProgressMonitor());
		final List<ProjectInfo> projectList = ESWorkspaceProviderImpl.getInstance().getConnectionManager()
			.getProjectList(
				ESSessionIdImpl.class.cast(getUsersession().getSessionId()).toInternalAPI());
		// TODO: not transparent with mock server
		final ConnectionMock mock = (ConnectionMock) ESWorkspaceProviderImpl.getInstance().getConnectionManager();
		assertFalse(mock.didDeleteFiles());
		assertEquals(0, projectList.size());
	}

	@Test(expected = AccessControlException.class)
	public void deleteProjectNotPA() throws ESException {
		ProjectUtil.share(getUsersession(), getLocalProject());
		getLocalProject().getRemoteProject().delete(new NullProgressMonitor());
	}

}