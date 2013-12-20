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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UsersessionTest extends ESTestWithLoggedInUser {

	@BeforeClass
	public static void beforeClass() {
		startEMFStore();
	}

	@AfterClass
	public static void afterClass() {
		stopEMFStore();
	}

	@Test
	public void testGetUsername() {
		assertEquals("super", getUsersession().getUsername()); //$NON-NLS-1$
	}

	@Test
	public void testGetPassword() {
		// hashed password is returned
		assertFalse("super".equals(getUsersession().getPassword())); //$NON-NLS-1$
	}

	@Test
	public void testGetServer() {
		assertEquals(getServer(), getUsersession().getServer());
	}

	@Test
	public void testGetSessionId() {
		assertNotNull(getUsersession().getSessionId());
	}

	@Test
	public void testIsLoggedIn() {
		assertTrue(getUsersession().isLoggedIn());
	}

	@Test
	public void testLogout() throws ESException {
		assertTrue(getUsersession().isLoggedIn());
		getUsersession().logout();
		assertFalse(getUsersession().isLoggedIn());
	}

	@Test
	public void testRenew() throws ESException {
		assertTrue(getUsersession().isLoggedIn());
		getUsersession().logout();
		assertFalse(getUsersession().isLoggedIn());
		getUsersession().refresh();
		assertTrue(getUsersession().isLoggedIn());
	}

}
