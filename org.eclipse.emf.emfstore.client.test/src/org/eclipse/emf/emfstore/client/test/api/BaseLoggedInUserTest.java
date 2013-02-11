package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.fail;

import org.eclipse.emf.emfstore.client.IServer;
import org.eclipse.emf.emfstore.client.IUsersession;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.junit.After;
import org.junit.Before;

public abstract class BaseLoggedInUserTest extends BaseEmptyEmfstoreTest {
	protected IServer server;
	protected IUsersession usersession;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		try {
			usersession = server.login("super", "super");
		} catch (EMFStoreException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Override
	@After
	public void tearDown() throws Exception {
		// setUp might have failed
		if (usersession != null && usersession.isLoggedIn()) {
			usersession.logout();
		}
		super.tearDown();
	}

}
