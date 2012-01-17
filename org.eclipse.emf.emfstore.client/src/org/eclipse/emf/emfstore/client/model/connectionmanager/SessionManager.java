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
package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.common.ExtensionPoint;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.SessionTimedOutException;
import org.eclipse.emf.emfstore.server.exceptions.UnknownSessionException;

/**
 * Handles session management during the execution of a {@link ServerCall}.
 * 
 * @author wesendon
 */
public class SessionManager {

	/**
	 * Executes the given {@link ServerCall}.
	 * 
	 * @param serverCall
	 *            the server call to be executed
	 * @throws EmfStoreException
	 *             If an error occurs during execution of the server call
	 */
	public void execute(ServerCall<?> serverCall) throws EmfStoreException {
		Usersession usersession = prepareUsersession(serverCall);
		loginUsersession(usersession, false);
		WorkspaceManager.getInstance().getCurrentWorkspace().getUsersessions().add(usersession);
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
	 * @throws EmfStoreException
	 *             In case
	 */
	private void loginUsersession(Usersession usersession, boolean forceLogin) throws EmfStoreException {
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
				} catch (EmfStoreException e) {
					// ignore, session provider should try to login
				}
			}
			getSessionProvider().loginSession(usersession);
		}
	}

	private boolean isLoggedIn(Usersession usersession) {
		ConnectionManager connectionManager = WorkspaceManager.getInstance().getConnectionManager();
		return usersession.isLoggedIn() && connectionManager.isLoggedIn(usersession.getSessionId());
	}

	private void executeCall(ServerCall<?> serverCall, Usersession usersession, boolean retry) throws EmfStoreException {
		try {
			serverCall.run(usersession.getSessionId());
		} catch (EmfStoreException e) {
			if (retry && (e instanceof SessionTimedOutException || e instanceof UnknownSessionException)) {
				// login & retry
				loginUsersession(usersession, true);
				executeCall(serverCall, usersession, false);
			} else {
				throw e;
			}
		}
	}

	private Usersession prepareUsersession(ServerCall<?> serverCall) throws EmfStoreException {
		Usersession usersession = serverCall.getUsersession();
		if (usersession == null) {
			usersession = getUsersessionFromProjectSpace(serverCall.getProjectSpace());
		}

		if (usersession == null) {
			SessionProvider sessionProvider = getSessionProvider();
			// serverinfo hint
			usersession = sessionProvider.provideUsersession(serverCall.getServerInfo());
		}
		serverCall.setUsersession(usersession);
		return usersession;
	}

	private Usersession getUsersessionFromProjectSpace(ProjectSpace projectSpace) {
		if (projectSpace != null && projectSpace.getUsersession() != null) {
			return projectSpace.getUsersession();
		}
		return null;
	}

	private SessionProvider getSessionProvider() {
		return new ExtensionPoint(SessionProvider.ID).getClass("class", SessionProvider.class);
	}
}
