package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UsersessionTest extends BaseLoggedInUserTest {

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

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
	public void testLogout() throws EMFStoreException {
		assertTrue(usersession.isLoggedIn());
		usersession.logout();
		assertFalse(usersession.isLoggedIn());
	}

	@Test
	public void testRenew() throws EMFStoreException {
		assertTrue(usersession.isLoggedIn());
		usersession.logout();
		assertFalse(usersession.isLoggedIn());
		usersession.renew();
		assertTrue(usersession.isLoggedIn());
	}

}
