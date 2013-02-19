package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.fail;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;

public abstract class BaseLoggedInUserTest extends BaseEmptyEmfstoreTest {
	protected ESServer server;
	protected ESUsersession usersession;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		server = ESServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		try {
			usersession = server.login("super", "super");
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
	}

	@Override
	@After
	public void tearDown() throws Exception {
		((ServerInfo) server).setLastUsersession(null);
		((Usersession) usersession).setServerInfo(null);
		// setUp might have failed
		if (usersession != null && usersession.isLoggedIn()) {
			usersession.logout();
			((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace()).getUsersessions().remove(usersession);
		}
		((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace()).removeServer(server);
		super.tearDown();
	}

}
