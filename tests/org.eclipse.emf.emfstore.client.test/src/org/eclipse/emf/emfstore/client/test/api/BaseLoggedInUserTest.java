package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;

public abstract class BaseLoggedInUserTest extends BaseEmptyEmfstoreTest {

	protected ESServer server;
	protected ESUsersession usersession;

	@Before
	public void setUp() throws Exception {
		server = ESServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		try {
			usersession = server.login("super", "super");
		} catch (ESException e) {
			log(e);
			fail(e.getMessage());
		}
		Assert.assertEquals(usersession, server.getLastUsersession());
	}

	@Override
	@After
	public void tearDown() throws Exception {
		EMFStoreCommandWithException<ESException> cmd = new EMFStoreCommandWithException<ESException>() {
			@Override
			protected void doRun() {
				((ESServerImpl) server).getInternalAPIImpl().setLastUsersession(null);
				((ESUsersessionImpl) usersession).setServer(null);
				// setUp might have failed
				if (usersession != null && usersession.isLoggedIn()) {
					try {
						usersession.logout();
					} catch (ESException e) {
						setException(e);
					}
					ESWorkspaceProviderImpl.getInstance().getWorkspace().getInternalAPIImpl().getUsersessions().remove(
						usersession);
				}
				ESWorkspaceProvider.INSTANCE.getWorkspace().removeServer(server);
			}
		};

		cmd.run();

		if (cmd.hasException()) {
			throw cmd.getException();
		}

		super.tearDown();
	}
}
