/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.sessionprovider.ESAbstractSessionProvider;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerCallImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.SessionTimedOutException;
import org.eclipse.emf.emfstore.internal.server.exceptions.UnknownSessionException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Handles session management during the execution of a {@link ServerCall}.
 * 
 * @author wesendon
 */
public class SessionManager {

	private ESAbstractSessionProvider provider;

	/**
	 * Constructor.
	 */
	public SessionManager() {
		initSessionProvider();
	}

	/**
	 * Executes the given {@link ServerCall}.
	 * 
	 * @param serverCall
	 *            the server call to be executed
	 * @throws ESException
	 *             If an error occurs during execution of the server call
	 */
	public void execute(ServerCall<?> serverCall) throws ESException {
		ESUsersessionImpl session = (ESUsersessionImpl) getSessionProvider().provideUsersession(
			new ESServerCallImpl(serverCall));
		serverCall.setUsersession(session.toInternalAPI());
		// TODO OTS
		Usersession loginUsersession = loginUsersession(session.toInternalAPI(), false);
		executeCall(serverCall, loginUsersession, true);
	}

	/**
	 * Tries to login the given {@link Usersession}.<br/>
	 * If the given session is not logged in or the <code>forceLogin</code> parameter is set
	 * to true, the session object first tries to login itself. If the login fails,
	 * the {@link SessionProvider} retrieved by {@link #getSessionProvider()} is asked to
	 * login the session by calling {@link SessionProvider#loginSession(Usersession)}.
	 * 
	 * @param usersession
	 *            The user session to be logged
	 * @param forceLogin
	 *            Whether the login should be forced, i.e. the login is performed even in case the
	 *            given user session is already logged in.
	 * @throws ESException
	 *             In case
	 */
	private Usersession loginUsersession(final Usersession usersession, boolean forceLogin) throws ESException {
		if (usersession == null) {
			// TODO create exception
			throw new RuntimeException("Ouch.");
		}
		if (!isLoggedIn(usersession) || forceLogin) {
			if (!(usersession.getUsername() == null || usersession.getUsername().equals(""))
				&& usersession.getPassword() != null) {
				try {
					// if login fails, let the session provider handle the rest
					RunESCommand.WithException.run(ESException.class, new Callable<Void>() {
						public Void call() throws Exception {
							usersession.logIn();
							return null;
						}
					});
					return usersession;
				} catch (ESException e) {
					// ignore, session provider should try to login
				}
			}
			// TODO: ugly
			ESUsersession session = RunESCommand.WithException.runWithResult(ESException.class,
				new Callable<ESUsersession>() {
					public ESUsersession call() throws Exception {
						return getSessionProvider().login(usersession.toAPI());
					}
				});
			return ((ESUsersessionImpl) session).toInternalAPI();
		}

		return usersession;
	}

	private boolean isLoggedIn(Usersession usersession) {
		ConnectionManager connectionManager = ESWorkspaceProviderImpl.getInstance().getConnectionManager();
		return usersession.isLoggedIn() && connectionManager.isLoggedIn(usersession.getSessionId());
	}

	private void executeCall(ServerCall<?> serverCall, Usersession usersession, boolean retry) throws ESException {
		try {
			serverCall.run(usersession.getSessionId());
		} catch (ESException e) {
			if (retry && (e instanceof SessionTimedOutException || e instanceof UnknownSessionException)) {
				// login & retry
				Usersession loginUsersession = loginUsersession(usersession, true);
				executeCall(serverCall, loginUsersession, false);
			} else {
				throw e;
			}
		}
	}

	/**
	 * Sets the {@link ESAbstractSessionProvider} to be used by this session manager.
	 * 
	 * @param sessionProvider
	 *            the session provider to be used
	 */
	public void setSessionProvider(ESAbstractSessionProvider sessionProvider) {
		provider = sessionProvider;
	}

	/**
	 * Returns the {@link ESAbstractSessionProvider} in use by this session manager.
	 * 
	 * @return the session provider in use
	 */
	public ESAbstractSessionProvider getSessionProvider() {
		return provider;
	}

	private void initSessionProvider() {
		ESExtensionPoint extensionPoint = new ESExtensionPoint("org.eclipse.emf.emfstore.client.usersessionProvider");

		if (extensionPoint.getExtensionElements().size() > 0) {
			ESAbstractSessionProvider sessionProvider = extensionPoint.getFirst().getClass("class",
				ESAbstractSessionProvider.class);
			if (sessionProvider != null) {
				provider = sessionProvider;
			}
		} else {
			provider = new BasicSessionProvider();
		}
	}
}