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
package org.eclipse.emf.emfstore.client.test.common.cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.exceptions.ESServerNotFoundException;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;

public abstract class ESTestWithLoggedInUser extends ESTestWithMockServer {

	private ESServer server;
	private ESUsersession usersession;

	public ESServer getServer() {
		return server;
	}

	public ESUsersession getUsersession() {
		return usersession;
	}

	@Override
	@Before
	public void before() {
		super.before();
		server = ESServer.FACTORY.createServer(
			ServerUtil.localhost(),
			ServerUtil.defaultPort(),
			KeyStoreManager.DEFAULT_CERTIFICATE);
		try {
			usersession = server.login(
				ServerUtil.superUser(),
				ServerUtil.superUserPassword());
		} catch (final ESException e) {
			fail(e.getMessage());
		}
		assertEquals(usersession, server.getLastUsersession());
	}

	@Override
	@After
	public void after() {

		final EMFStoreCommandWithException<ESException> cmd = new EMFStoreCommandWithException<ESException>() {
			@Override
			protected void doRun() {
				((ESServerImpl) server).toInternalAPI().setLastUsersession(null);
				((ESUsersessionImpl) usersession).setServer(null);
				// setUp might have failed
				if (usersession != null && usersession.isLoggedIn()) {
					try {
						usersession.logout();
					} catch (final ESException e) {
						setException(e);
					}

					final Iterator<Usersession> iter = ESWorkspaceProviderImpl.getInstance().getWorkspace()
						.toInternalAPI()
						.getUsersessions().iterator();
					while (iter.hasNext()) {
						if (iter.next().getServerInfo() == ((ESServerImpl) server).toInternalAPI()) {
							iter.remove();
						}
					}
				}
				try {
					ESWorkspaceProvider.INSTANCE.getWorkspace().removeServer(server);
				} catch (final ESServerNotFoundException e) {
					fail(e.getMessage());
				}
			}
		};

		cmd.run();

		if (cmd.hasException()) {
			fail(cmd.getException().getMessage());
		}

		super.after();
	}
}
