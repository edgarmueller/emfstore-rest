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

import org.eclipse.emf.emfstore.client.test.common.dsl.Roles;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.PAPrivileges;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.RolesPackage;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the missing {@link PAPrivileges#AssignRoleToOrgUnit} privilege of a
 * {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ProjectAdminRole ProjectAdminRole}.
 * 
 * @author emueller
 * 
 */
public class AssignRoleToOrgUnitMissingPrivilegTests extends ProjectAdminTest {

	@BeforeClass
	public static void beforeClass() {
		startEMFStoreWithPAProperties(PAPrivileges.ShareProject);
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

	@Test(expected = AccessControlException.class)
	public void assignReaderRolePA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		getAdminBroker().assignRole(newUser, RolesPackage.eINSTANCE.getReaderRole());
	}

	@Test(expected = AccessControlException.class)
	public void changeRoleToReaderPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), newUser, Roles.reader());
	}

	@Test(expected = AccessControlException.class)
	public void changeRoleToWriterPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), newUser, Roles.writer());
	}

	@Test(expected = AccessControlException.class)
	public void changeRoleToProjectAdminPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), newUser, Roles.projectAdmin());
	}

	@Test(expected = AccessControlException.class)
	public void assignRoleProjectAdminPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		getAdminBroker().assignRole(newUser, Roles.projectAdmin());
	}

	@Test(expected = AccessControlException.class)
	public void assignRoleWriterPA() throws ESException {
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), newUser, RolesPackage.eINSTANCE.getWriterRole());
	}

}