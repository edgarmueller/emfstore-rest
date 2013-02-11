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

import org.eclipse.emf.emfstore.client.sessionprovider.AbstractSessionProvider;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.exceptions.SessionTimedOutException;
import org.eclipse.emf.emfstore.internal.server.exceptions.UnknownSessionException;

/**
 * Handles session management during the execution of a {@link ServerCall}.
 * 
 * @author wesendon
 */
public class SessionManager {

	private AbstractSessionProvider provider;

	public SessionManager() {
		initSessionProvider();
	}

	/**
	 * Executes the given {@link ServerCall}.
	 * 
	 * @param serverCall
	 *            the server call to be executed
	 * @throws EMFStoreException
	 *             If an error occurs during execution of the server call
	 */
	public void execute(ServerCall<?> serverCall) throws EMFStoreException {
		Usersession usersession = (Usersession) getSessionProvider().provideUsersession(serverCall);
		serverCall.setUsersession(usersession);
		// TODO OTS
		loginUsersession(usersession, false);
		executeCall(serverCall, usersession, true);
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
	 * @throws EMFStoreException
	 *             In case
	 */
	private void loginUsersession(Usersession usersession, boolean forceLogin) throws EMFStoreException {
		if (usersession == null) {
			// TODO create exception
			throw new RuntimeException("Ouch.");
		}
		if (!isLoggedIn(usersession) || forceLogin) {
			if (!(usersession.getUsername() == null || usersession.getUsername().equals(""))
				&& usersession.getPassword() != null) {
				try {
					// if login fails, let the session provider handle the rest
					usersession.logIn();
					return;
				} catch (EMFStoreException e) {
					// ignore, session provider should try to login
				}
			}
			getSessionProvider().login(usersession);
		}
	}

	private boolean isLoggedIn(Usersession usersession) {
		ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
		return usersession.isLoggedIn() && connectionManager.isLoggedIn(usersession.getSessionId());
	}

	private void executeCall(ServerCall<?> serverCall, Usersession usersession, boolean retry) throws EMFStoreException {
		try {
			serverCall.run(usersession.getSessionId());
		} catch (EMFStoreException e) {
			if (retry && (e instanceof SessionTimedOutException || e instanceof UnknownSessionException)) {
				// login & retry
				loginUsersession(usersession, true);
				executeCall(serverCall, usersession, false);
			} else {
				throw e;
			}
		}
	}

	public void setSessionProvider(AbstractSessionProvider sessionProvider) {
		provider = sessionProvider;
	}

	private AbstractSessionProvider getSessionProvider() {
		return provider;
	}

	private void initSessionProvider() {
		ExtensionPoint extensionPoint = new ExtensionPoint("org.eclipse.emf.emfstore.client.sessionprovider");

		if (extensionPoint.getExtensionElements().size() > 0) {
			AbstractSessionProvider sessionProvider = extensionPoint.getFirst().getClass("class",
				AbstractSessionProvider.class);
			if (sessionProvider != null) {
				provider = sessionProvider;
			}
		} else {
			provider = new BasicSessionProvider();
		}
	}
}