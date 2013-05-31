package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.fail;

import java.util.Iterator;

import junit.framework.Assert;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
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
		server = ESServer.FACTORY.getServer("localhost", port, KeyStoreManager.DEFAULT_CERTIFICATE);
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
				((ESServerImpl) server).toInternalAPI().setLastUsersession(null);
				((ESUsersessionImpl) usersession).setServer(null);
				// setUp might have failed
				if (usersession != null && usersession.isLoggedIn()) {
					try {
						usersession.logout();
					} catch (ESException e) {
						setException(e);
					}

					Iterator<Usersession> iter = ESWorkspaceProviderImpl.getInstance().getWorkspace().toInternalAPI()
						.getUsersessions().iterator();
					while (iter.hasNext()) {
						if (iter.next().getServerInfo() == ((ESServerImpl) server).toInternalAPI()) {
							iter.remove();
						}
					}
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
