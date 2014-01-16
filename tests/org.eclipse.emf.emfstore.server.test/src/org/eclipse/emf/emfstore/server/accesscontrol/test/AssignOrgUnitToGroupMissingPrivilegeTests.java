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

import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.share;
import static org.eclipse.emf.emfstore.client.test.common.util.ServerUtil.createGroup;
import static org.eclipse.emf.emfstore.client.test.common.util.ServerUtil.createUser;
import static org.junit.Assert.fail;

import org.eclipse.emf.emfstore.client.test.common.dsl.Roles;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.PAPrivileges;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the {@link PAPrivileges#ChangeAssignmentsOfOrgUnits} privilege of a project admin
 * role.
 * 
 * @author emueller
 **/
public class AssignOrgUnitToGroupMissingPrivilegeTests extends ProjectAdminTest {

	private static final String GROUP = "Group"; //$NON-NLS-1$
	private static final String OTHER_GROUP = "OtherGroup"; //$NON-NLS-1$

	@BeforeClass
	public static void beforeClass() {
		startEMFStoreWithPAProperties(PAPrivileges.ShareProject,
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
			ServerUtil.deleteGroup(getSuperUsersession(), GROUP);
			ServerUtil.deleteGroup(getSuperUsersession(), OTHER_GROUP);
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

	@Test(expected = AccessControlException.class)
	public void addMemberToGroup() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUser = createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = createGroup(getSuperUsersession(), getNewGroupName());
		getAdminBroker().addMember(group, newUser);
	}

	@Test(expected = AccessControlException.class)
	public void removeMemberFromGroupWithProject() throws ESException {
		makeUserPA();
		share(getUsersession(), getLocalProject());
		final ACOrgUnitId newUser = createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = createGroup(getSuperUsersession(), getNewGroupName());

		getAdminBroker().changeRole(getProjectSpace().getProjectId(), group, Roles.writer());

		getAdminBroker().addMember(group, newUser);
		getAdminBroker().removeMember(group, newUser);
	}
}
