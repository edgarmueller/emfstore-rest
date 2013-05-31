/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Test;

public class UsersessionTest extends BaseLoggedInUserTest {

	@Test
	public void testGetUsername() {
		assertEquals("super", usersession.getUsername());
	}

	@Test
	public void testGetPassword() {
		// hashed password is returned
		assertFalse("super".equals(usersession.getPassword()));
	}

	@Test
	public void testGetServer() {
		assertEquals(server, usersession.getServer());
	}

	@Test
	public void testGetSessionId() {
		assertNotNull(usersession.getSessionId());
	}

	@Test
	public void testIsLoggedIn() {
		assertTrue(usersession.isLoggedIn());
	}

	@Test
	public void testLogout() throws ESException {
		assertTrue(usersession.isLoggedIn());
		usersession.logout();
		assertFalse(usersession.isLoggedIn());
	}

	@Test
	public void testRenew() throws ESException {
		assertTrue(usersession.isLoggedIn());
		usersession.logout();
		assertFalse(usersession.isLoggedIn());
		usersession.refresh();
		assertTrue(usersession.isLoggedIn());
	}

}
