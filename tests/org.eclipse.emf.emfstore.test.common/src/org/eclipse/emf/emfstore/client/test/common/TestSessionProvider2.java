/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.sessionprovider.ESAbstractSessionProvider;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * A session provider implementation meant to be used by any tests.
 * 
 * @author emueller
 */
public final class TestSessionProvider2 extends ESAbstractSessionProvider {

	private static Usersession usersession;

	public static final TestSessionProvider2 INSTANCE = new TestSessionProvider2();

	/**
	 * Returns the singleton instance.
	 * 
	 * @return the singleton instance
	 */
	public static TestSessionProvider2 getInstance() {
		return INSTANCE;
	}

	/**
	 * Returns the default {@link Usersession}.
	 * 
	 * @return the default user session
	 * @throws AccessControlException if login fails
	 * @throws ESException if anything else fails
	 */
	public Usersession getDefaultUsersession() throws ESException {

		return RunESCommand.WithException.runWithResult(ESException.class,
			new Callable<Usersession>() {

				public Usersession call() throws Exception {
					usersession.logIn();
					return usersession;
				}
			});
	}

	public TestSessionProvider2() {
		ESUsersessionImpl login;
		final ESServer server = ESServer.FACTORY.createServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		try {
			login = (ESUsersessionImpl) server.login("super", "super");
			usersession = login.toInternalAPI();
		} catch (final ESException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ESUsersession provideUsersession(ESServer serverInfo) throws ESException {
		if (!usersession.isLoggedIn()) {
			usersession.logIn();
		}
		return usersession.toAPI();
	}

	@Override
	public ESUsersession login(ESUsersession usersession) throws ESException {
		usersession.getServer().login(usersession.getUsername(), usersession.getPassword());
		return usersession;
	}

}
