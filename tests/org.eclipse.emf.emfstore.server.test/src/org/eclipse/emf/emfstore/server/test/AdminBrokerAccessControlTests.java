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
package org.eclipse.emf.emfstore.server.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.client.model.AdminBroker;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.AdminBrokerImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.exceptions.ConnectionException;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Edgar
 * 
 */
public class AdminBrokerAccessControlTests extends ESTestWithLoggedInUser {

	private static final String GROUP_NAME = "grp"; //$NON-NLS-1$
	private static final String USER_NAME = "quux"; //$NON-NLS-1$

	private static AdminBroker adminBroker;

	@BeforeClass
	public static void beforeClass() {
		startEMFStore();
	}

	public static void afterClass() {
		stopEMFStore();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser#getUser()
	 */
	@Override
	public String getUser() {
		return "Hans";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser#getPassword()
	 */
	@Override
	public String getPassword() {
		return "wurst";
	}

	@Test
	public void hansIsLoggedIn() {
		assertTrue(getUsersession().isLoggedIn());
	}

	@Test(expected = AccessControlException.class)
	public void createUserFails() throws ESException {
		ServerUtil.createUser(getUsersession(), USER_NAME);
	}

	@Override
	@Before
	public void before() {
		super.before();
		try {
			adminBroker = new AdminBrokerImpl(
				((ESServerImpl) getServer()).toInternalAPI(),
				((ESUsersessionImpl) getUsersession()).toInternalAPI().getSessionId());
		} catch (final ConnectionException ex) {
			fail(ex.getMessage());
		}
	}

	@Override
	@After
	public void after() {
		ACUser user;
		try {
			user = ServerUtil.getUser(((ESUsersessionImpl) getSuperUsersession()).toInternalAPI().getSessionId(),
				USER_NAME);
			if (user != null) {
				ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager().deleteUser(
					((ESUsersessionImpl) getSuperUsersession()).toInternalAPI().getSessionId(),
					user.getId());
			}
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
		super.after();
	}
}