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
import static org.junit.Assert.fail;

import org.eclipse.emf.emfstore.client.test.common.dsl.Roles;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
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
 * Test the {@link PAPrivileges#DeleteOrgUnit} privilege of a
 * {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ProjectAdminRole ProjectAdminRole}.
 * 
 * @author emueller
 * 
 */
public class DeleteOrgUnitTest extends ProjectAdminTest {

	@BeforeClass
	public static void beforeClass() {
		startEMFStoreWithPAProperties(
			PAPrivileges.CreateGroup,
			PAPrivileges.ChangeAssignmentsOfOrgUnits,
			PAPrivileges.AssignRoleToOrgUnit,
			PAPrivileges.ShareProject,
			PAPrivileges.DeleteOrgUnit);
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
	public void createGroup() throws ESException {
		makeUserPA();
		getAdminBroker().createGroup(getNewGroupName());
	}

	@Test(expected = AccessControlException.class)
	public void deleteUserFailsIsPartOfNonProjectGroup() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = ServerUtil.createGroup(getSuperUsersession(), getNewGroupName());
		final ACOrgUnitId otherGroup = ServerUtil.createGroup(getSuperUsersession(), getNewOtherGroupName());
		getAdminBroker().addMember(group, otherGroup);
		getAdminBroker().addMember(otherGroup, newUser);

		ProjectUtil.share(getUsersession(), getLocalProject());
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		ProjectUtil.share(getSuperUsersession(), clonedProjectSpace.toAPI());

		getAdminBroker().changeRole(clonedProjectSpace.getProjectId(), group, Roles.writer());

		getAdminBroker().deleteUser(newUser);
	}

	@Test
	public void deleteGroup() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = ServerUtil.createGroup(getSuperUsersession(), getNewGroupName());
		final ACOrgUnitId otherGroup = ServerUtil.createGroup(getSuperUsersession(), getNewOtherGroupName());
		getAdminBroker().addMember(group, otherGroup);
		getAdminBroker().addMember(otherGroup, newUser);

		ProjectUtil.share(getUsersession(), getLocalProject());
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		ProjectUtil.share(getSuperUsersession(), clonedProjectSpace.toAPI());

		getAdminBroker().changeRole(getProjectSpace().getProjectId(), group, Roles.writer());
		final int oldSize = getAdminBroker().getGroups().size();
		getAdminBroker().deleteGroup(group);
		assertEquals(oldSize - 1, getAdminBroker().getGroups().size());
	}

	/**
	 * @throws ESException
	 */
	@Test
	public void deleteUser() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = ServerUtil.createGroup(getSuperUsersession(), getNewGroupName());
		final ACOrgUnitId otherGroup = ServerUtil.createGroup(getSuperUsersession(), getNewOtherGroupName());
		getAdminBroker().addMember(group, otherGroup);
		getAdminBroker().addMember(otherGroup, newUser);

		ProjectUtil.share(getUsersession(), getLocalProject());
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		ProjectUtil.share(getSuperUsersession(), clonedProjectSpace.toAPI());

		getAdminBroker().changeRole(getProjectSpace().getProjectId(), group, Roles.writer());
		final int oldSize = getAdminBroker().getUsers().size();
		getAdminBroker().deleteUser(newUser);
		assertEquals(oldSize - 1, getAdminBroker().getUsers().size());
	}

	@Test
	public void deleteGroupBothGroupArePartOfProject() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = ServerUtil.createGroup(getSuperUsersession(), getNewGroupName());
		final ACOrgUnitId otherGroup = ServerUtil.createGroup(getSuperUsersession(), getNewOtherGroupName());
		getAdminBroker().addMember(group, otherGroup);
		getAdminBroker().addMember(otherGroup, newUser);

		ProjectUtil.share(getUsersession(), getLocalProject());
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		ProjectUtil.share(getSuperUsersession(), clonedProjectSpace.toAPI());

		getAdminBroker().changeRole(getProjectSpace().getProjectId(), group, Roles.writer());
		getAdminBroker().changeRole(getProjectSpace().getProjectId(), otherGroup, Roles.reader());

		final int oldSize = getAdminBroker().getGroups().size();
		getAdminBroker().deleteGroup(group);
		assertEquals(oldSize - 1, getAdminBroker().getGroups().size());
	}

	@Test(expected = AccessControlException.class)
	public void deleteUserFailsUserIsInTransitiveGroup() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = ServerUtil.createGroup(getSuperUsersession(), getNewGroupName());
		final ACOrgUnitId otherGroup = ServerUtil.createGroup(getSuperUsersession(), getNewOtherGroupName());
		getAdminBroker().addMember(group, otherGroup);
		getAdminBroker().addMember(otherGroup, newUser);

		ProjectUtil.share(getUsersession(), getLocalProject());
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		ProjectUtil.share(getSuperUsersession(), clonedProjectSpace.toAPI());

		getSuperAdminBroker().changeRole(clonedProjectSpace.getProjectId(), group, Roles.writer());
		getAdminBroker().deleteUser(newUser);
	}

	@Test(expected = AccessControlException.class)
	public void deleteGroupFailsIsInTransitiveGroup() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUser = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = ServerUtil.createGroup(getSuperUsersession(), getNewGroupName());
		final ACOrgUnitId otherGroup = ServerUtil.createGroup(getSuperUsersession(), getNewOtherGroupName());
		getAdminBroker().addMember(group, otherGroup);
		getAdminBroker().addMember(otherGroup, newUser);

		ProjectUtil.share(getUsersession(), getLocalProject());
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		ProjectUtil.share(getSuperUsersession(), clonedProjectSpace.toAPI());

		getSuperAdminBroker().changeRole(clonedProjectSpace.getProjectId(), group, Roles.writer());

		getAdminBroker().deleteGroup(otherGroup);
	}
}
