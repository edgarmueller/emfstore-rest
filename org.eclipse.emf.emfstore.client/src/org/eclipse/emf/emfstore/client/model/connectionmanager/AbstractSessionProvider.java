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
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

/**
 * This is the abstract super class for SessionProviders. All SessionProvider should extend this class. SessionProvider
 * derives a usersession for a given serverrequest (ServerCall). When overriding {@link #provideUsersession(ServerCall)}
 * , it is possible to gain more context for the {@link Usersession} selection. However, in most usecases most users
 * will use the session provider to open a login dialog of kind. For this purpose
 * it is better to use {@link #provideUsersession(ServerInfo)}. SessionProviders can be registered via an extension
 * point.
 * 
 * @author wesendon
 * 
 */
public abstract class AbstractSessionProvider {

	/**
	 * ExtensionPoint ID of the SessionProvider.
	 */
	public static final String ID = "org.eclipse.emf.emfstore.client.sessionprovider";

	/**
	 * The {@link SessionManager} calls this method in order to gain a usersession. In its default implementation it
	 * first looks for specified usersession in the {@link ServerCall}, then it checks whether the projectspace is
	 * associated with a usersession (e.g. in case of update) and if there's still no usersession
	 * {@link #provideUsersession(ServerInfo)} is called, which should be used when implementing an usersession
	 * selection UI.
	 * 
	 * In most cases it is sufficient to implement {@link #provideUsersession(ServerInfo)} and there's no need to change
	 * this implementation.
	 * 
	 * 
	 * @param serverCall current server call
	 * @return a usersession, can be logged in or logged out. SessionManager will double check that either way.
	 * @throws EmfStoreException in case of an exception
	 */
	protected Usersession provideUsersession(ServerCall<?> serverCall) throws EmfStoreException {

		Usersession usersession = serverCall.getUsersession();
		if (usersession == null) {
			usersession = getUsersessionFromProjectSpace(serverCall.getProjectSpace());
		}

		if (usersession == null) {
			usersession = provideUsersession(serverCall.getServerInfo());
		}

		serverCall.setUsersession(usersession);
		return usersession;
	}

	/**
	 * Tries to gain a usersession for a given projectspace.
	 * 
	 * @param projectSpace projectspace
	 * @return {@link Usersession} or null
	 */
	protected Usersession getUsersessionFromProjectSpace(ProjectSpace projectSpace) {
		if (projectSpace != null && projectSpace.getUsersession() != null) {
			return projectSpace.getUsersession();
		}
		return null;
	}

	/**
	 * This is the template method for {@link #provideUsersession(ServerCall)}. It is called, if the latter couldn't
	 * determine a suitable usersession. Use this in order to implement a session selection UI or headless selection
	 * logic.
	 * 
	 * @param serverInfo This parameter is a hint from the {@link ServerCall}. For that reason it can be null. A common
	 *            example is share, where the user first has to select the server before logging in. If
	 *            {@link ServerInfo} is set you should allow the user to select the account for the given server.
	 * @return a usersession, can be logged in or logged out. SessionManager will double check that either way
	 * @throws EmfStoreException in case of an exception
	 */
	public abstract Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException;

	/**
	 * This method is called by the {@link SessionManager} in order to login a given usersession. Either you are able to
	 * login the given session or should throw an exception.
	 * 
	 * @param usersession session to be logged in.
	 * @throws EmfStoreException in case of an exception
	 */
	public abstract void login(Usersession usersession) throws EmfStoreException;
}
