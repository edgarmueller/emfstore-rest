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

import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.share;
import static org.eclipse.emf.emfstore.client.test.common.util.ServerUtil.createGroup;
import static org.eclipse.emf.emfstore.client.test.common.util.ServerUtil.createUser;
import static org.eclipse.emf.emfstore.client.test.common.util.ServerUtil.deleteGroup;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.emf.emfstore.client.test.common.dsl.Roles;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
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
 * Tests whether organizational units can be assigned to groups.
 * 
 * @author emueller
 * 
 */
public class AssignOrgUnitToGroupTests extends ProjectAdminTest {

	@BeforeClass
	public static void beforeClass() {
		startEMFStoreWithPAProperties(
			PAPrivileges.ShareProject,
			PAPrivileges.AssignRoleToOrgUnit,
			PAPrivileges.ChangeAssignmentsOfOrgUnits);
	}

	@AfterClass
	public static void afterClass() {
		stopEMFStore();
	}

	@Override
	@After
	public void after() {
		try {
			deleteGroup(getSuperUsersession(), getNewGroupName());
			deleteGroup(getSuperUsersession(), getNewOtherGroupName());
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
	public void addMemberToGroupWhichBelongsToOtherProject() throws ESException {
		makeUserPA();
		share(getSuperUsersession(), getLocalProject());
		final ACOrgUnitId newUser = createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = createGroup(getSuperUsersession(), getNewGroupName());

		share(getUsersession(), getLocalProject());
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		share(getSuperUsersession(), clonedProjectSpace.toAPI());

		getSuperAdminBroker().changeRole(clonedProjectSpace.getProjectId(), group, Roles.writer());

		// fails
		getAdminBroker().addMember(group, newUser);
	}

	@Test(expected = AccessControlException.class)
	public void removeMemberFromGroupWhichBelongsToOtherProject() throws ESException {
		makeUserPA();
		share(getSuperUsersession(), getLocalProject());
		final ACOrgUnitId newUser = createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = createGroup(getSuperUsersession(), getNewGroupName());

		share(getUsersession(), getLocalProject());
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		share(getSuperUsersession(), clonedProjectSpace.toAPI());

		getSuperAdminBroker().changeRole(clonedProjectSpace.getProjectId(), group, Roles.writer());

		getSuperAdminBroker().addMember(group, newUser);
		getAdminBroker().removeMember(group, newUser);
	}

	@Test
	public void removeMemberFromGroupWithProject() throws ESException {
		makeUserPA();
		share(getUsersession(), getLocalProject());
		final ACOrgUnitId newUser = createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = createGroup(getSuperUsersession(), getNewGroupName());

		getAdminBroker().changeRole(getProjectSpace().getProjectId(), group, Roles.writer());

		getAdminBroker().addMember(group, newUser);
		getAdminBroker().removeMember(group, newUser);
		assertEquals(0, getAdminBroker().getMembers(group).size());
	}

	@Test
	public void addMemberToGroup() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUser = createUser(getSuperUsersession(), getNewUsername());
		final ACOrgUnitId group = createGroup(getSuperUsersession(), getNewGroupName());
		getAdminBroker().addMember(group, newUser);
		assertEquals(1, getAdminBroker().getMembers(group).size());
	}

	@Test
	public void addParticipantPA() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUserId = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		share(getUsersession(), getLocalProject());
		final int oldSize = getAdminBroker().getParticipants(getProjectSpace().getProjectId()).size();
		getAdminBroker().addParticipant(getProjectSpace().getProjectId(), newUserId, Roles.reader());
		assertEquals(oldSize + 1, getAdminBroker().getParticipants(getProjectSpace().getProjectId()).size());
	}

	@Test(expected = AccessControlException.class)
	public void addParticipantNotPA() throws ESException {
		final ACOrgUnitId newUserId = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		share(getUsersession(), getLocalProject());
		getAdminBroker().addParticipant(getProjectSpace().getProjectId(), newUserId, Roles.reader());
	}

	@Test
	public void removeParticipantPA() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUserId = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		share(getUsersession(), getLocalProject());
		getAdminBroker().addParticipant(getProjectSpace().getProjectId(), newUserId, Roles.reader());
		final int oldSize = getAdminBroker().getParticipants(getProjectSpace().getProjectId()).size();
		getAdminBroker().removeParticipant(getProjectSpace().getProjectId(), newUserId);
		assertEquals(oldSize - 1, getAdminBroker().getParticipants(getProjectSpace().getProjectId()).size());
	}

	@Test(expected = AccessControlException.class)
	public void removeParticipantNotPA() throws ESException {
		makeUserPA();
		final ACOrgUnitId newUserId = ServerUtil.createUser(getSuperUsersession(), getNewUsername());
		share(getUsersession(), getLocalProject());
		getAdminBroker().addParticipant(getProjectSpace().getProjectId(), newUserId, Roles.reader());
		// downgrade project admin
		getAdminBroker().changeRole(getProjectSpace().getProjectId(),
			ESUsersessionImpl.class.cast(getUsersession()).toInternalAPI().getACUser().getId(),
			Roles.writer());
		getAdminBroker().removeParticipant(getProjectSpace().getProjectId(), newUserId);
	}
}
