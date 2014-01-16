/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.accesscontrol.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.test.common.dsl.Roles;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.PAPrivileges;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ReaderRole;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.RolesPackage;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the {@link PAPrivileges#AssignRoleToOrgUnit} privilege of a
 * {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ProjectAdminRole ProjectAdminRole}.
 * 
 * @author emueller
 * 
 */
public class AssignRoleToOrgUnitTests extends ProjectAdminTest {

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
		super.after();
	}

	@Override
	@Before
	public void before() {
		super.before();
	}

	@Test
	public void assignReaderRolePA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		getAdminBroker().assignRole(newUser, RolesPackage.eINSTANCE.getReaderRole());

		final ACUser user = ServerUtil.getUser(getSuperUsersession(), getNewUsername());
		assertTrue(hasReaderRole(user));
	}

	@Test
	public void changeRoleToReaderPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), newUser, Roles.reader());
		final ACUser user = ServerUtil.getUser(getUsersession(), getNewUsername());
		assertTrue(hasReaderRole(user));
	}

	@Test
	public void getRolePA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), newUser, Roles.reader());
		final ACUser user = ServerUtil.getUser(getUsersession(), getNewUsername());
		assertTrue(getAdminBroker().getRole(getProjectSpace().getProjectId(), user.getId()) instanceof ReaderRole);
	}

	@Test
	public void changeRoleToWriterPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), newUser, Roles.writer());
		final ACUser user = ServerUtil.getUser(getUsersession(), getNewUsername());
		assertTrue(hasWriterRole(user));
	}

	@Test
	public void changeRoleToProjectAdminPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), newUser, Roles.projectAdmin());
		final ACUser user = ServerUtil.getUser(getUsersession(), getNewUsername());
		assertTrue(hasProjectAdminRole(user));
	}

	@Test(expected = AccessControlException.class)
	public void changeRoleToProjectAdminNotPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		ProjectUtil.share(getSuperUsersession(), getLocalProject());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), newUser, Roles.projectAdmin());
	}

	@Test
	public void assignRoleProjectAdminPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		getAdminBroker().assignRole(newUser, Roles.projectAdmin());
		final ACUser user = ServerUtil.getUser(getSuperUsersession(), getNewUsername());
		assertTrue(hasProjectAdminRole(user));
	}

	@Test
	public void assignRoleWriterPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), newUser, RolesPackage.eINSTANCE.getWriterRole());
		final ACUser user = ServerUtil.getUser(getUsersession(), getNewUsername());
		assertTrue(hasRole(user, Roles.writer()));
	}

	@Test(expected = AccessControlException.class)
	public void assignProjectAdminRoleNotPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		getAdminBroker().assignRole(newUser, Roles.projectAdmin());
	}

	@Test(expected = AccessControlException.class)
	public void assignWriterRoleNotPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		getAdminBroker().assignRole(newUser, Roles.writer());
	}

	@Test(expected = AccessControlException.class)
	public void assignReaderRoleNotPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		getAdminBroker().assignRole(newUser, Roles.reader());
	}

	@Test
	public void assignReaderRoleTwicePA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		getAdminBroker().assignRole(newUser, RolesPackage.eINSTANCE.getReaderRole());
		getAdminBroker().assignRole(newUser, RolesPackage.eINSTANCE.getReaderRole());

		final ACUser user = ServerUtil.getUser(getSuperUsersession(), getNewUsername());
		assertTrue(hasReaderRole(user));
	}

}