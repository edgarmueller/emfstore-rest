/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.internal.client.model.AdminBroker;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.AbstractAuthenticationControl;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.AuthenticationControlType;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.internal.factory.AuthenticationControlFactory;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESSessionIdImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class AllAdminBrokerTests extends ServerTests {

	private final String USER_NAME = "quux";
	private final String USER_PW = "pass";
	private AdminBroker adminBroker;
	private ESUsersessionImpl login;

	@Before
	public void setup() throws AccessControlException, ESException, FatalESException {
		final AbstractAuthenticationControl modelAuthenticationControl = AuthenticationControlFactory.INSTANCE
			.createAuthenticationControl(AuthenticationControlType.Model);
		EMFStoreController.getInstance().getAccessControl().setAuthenticationControl(modelAuthenticationControl);
		login = (ESUsersessionImpl) getServer().login("super", "super");
		adminBroker = ESWorkspaceProviderImpl.getInstance().getInternalWorkspace()
			.getAdminBroker(login.toInternalAPI());
		ACUser userToDelete = null;
		for (final ACUser user : adminBroker.getUsers()) {
			if (user.getName().equals(USER_NAME)) {
				userToDelete = user;
			}
		}
		if (userToDelete != null) {
			adminBroker.deleteUser(userToDelete.getId());
		}
	}

	@Test
	public void testCreateUser() throws ESException {
		final int initialSize = adminBroker.getUsers().size();
		adminBroker.createUser(USER_NAME);
		assertEquals(initialSize + 1, adminBroker.getUsers().size());
	}

	@Test
	public void testDeleteUser() throws ESException {
		adminBroker.createUser(USER_NAME);
		final int initialSize = adminBroker.getUsers().size();
		ACUser userToDelete = null;
		for (final ACUser user : adminBroker.getUsers()) {
			if (user.getName().equals(USER_NAME)) {
				userToDelete = user;
			}
		}
		adminBroker.deleteUser(userToDelete.getId());
		assertEquals(initialSize - 1, adminBroker.getUsers().size());
	}

	@Test(expected = AccessControlException.class)
	public void testLoginOfCreatedUserWithNoPasswordSet() throws ESException {
		adminBroker.createUser(USER_NAME);
		ACUser user = null;
		for (final ACUser u : adminBroker.getUsers()) {
			if (u.getName().equals(USER_NAME)) {
				user = u;
			}
		}
		final ESUsersession login2 = getServer().login(user.getName(), user.getPassword());
		assertTrue(login2.isLoggedIn());
	}

	@Test
	public void testLoginOfCreatedUserWithSetPassword() throws ESException {
		final ACOrgUnitId createdUserId = adminBroker.createUser(USER_NAME);
		ACUser user = null;
		for (final ACUser u : adminBroker.getUsers()) {
			if (u.getName().equals(USER_NAME)) {
				user = u;
			}
		}
		user.setPassword(USER_PW);
		ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager().changeUser(
			((ESSessionIdImpl) login.getSessionId()).toInternalAPI(),
			createdUserId, USER_NAME, USER_PW);
		final ESUsersession login2 = getServer().login(user.getName(), user.getPassword());
		assertTrue(login2.isLoggedIn());
	}

	@Test
	public void testCreateGroup() throws ESException {
		adminBroker.createGroup("grp");
		assertEquals(1, adminBroker.getGroups().size());
	}
}
