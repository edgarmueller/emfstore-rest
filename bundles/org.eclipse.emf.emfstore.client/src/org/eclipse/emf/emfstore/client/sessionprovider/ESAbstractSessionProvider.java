/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.sessionprovider;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * <p>
 * This is the abstract super class for SessionProviders. All SessionProvider should extend this class. SessionProvider
 * derives a user session for a given server request (ESServerCall). When overriding
 * {@link #provideUsersession(ESServerCall)} , it is possible to gain more context for the {@link ESUsersession}
 * selection.
 * </p>
 * <p>
 * However, in most usecases most, users will use the session provider to open a login dialog of some kind. For this
 * purpose it is better to use {@link #provideUsersession(ESServer)}. SessionProviders can be registered via an
 * extension point.<br/>
 * </p>
 * 
 * <p>
 * <b>Note</b>: Implementations of SessionProviders must not assume that they are executed within the UI-Thread.
 * </p>
 * 
 * @author wesendon
 * 
 */
public abstract class ESAbstractSessionProvider {

	/**
	 * <p>
	 * The SessionManager calls this method in order to obtain a user session. In its default implementation it first
	 * looks for specified user session in the {@link ESServerCall}, then it checks whether the local project is
	 * associated with a user session (e.g. in case of update). If there is still no user session,
	 * {@link #provideUsersession(ESServer)} is called, which is meant to be used when implementing an UI to select a
	 * UI.
	 * </p>
	 * 
	 * <p>
	 * In most cases it is sufficient to implement {@link #provideUsersession(ESServer)}. There should be no need to
	 * change this implementation.
	 * </p>
	 * 
	 * @param serverCall
	 *            current server call
	 * @return an user session. It is not specified whether this session is logged in or logged out.
	 * 
	 * @throws ESException in case an exception occurred while obtaining the user session
	 */
	public ESUsersession provideUsersession(ESServerCall serverCall) throws ESException {

		ESUsersession usersession = serverCall.getUsersession();

		if (usersession == null) {
			usersession = getUsersessionFromProject(serverCall.getLocalProject());
		}

		if (usersession == null) {
			usersession = provideUsersession(serverCall.getServer());
		}

		return usersession;
	}

	/**
	 * Tries to obtain a user session from a given {@link ESLocalProject}.
	 * 
	 * @param project
	 *            the local project to obtain the user session from
	 * @return the user session associated with the project or {@code null}, if no session is available
	 */
	protected ESUsersession getUsersessionFromProject(ESLocalProject project) {

		if (project != null && project.getUsersession() != null) {
			return project.getUsersession();
		}

		return null;
	}

	/**
	 * <p>
	 * This is the template method for {@link #provideUsersession(ESServer)}. It is called, if the latter couldn't
	 * determine a suitable user session. Use this in order to implement a session selection UI or a headless selection
	 * logic.
	 * </p>
	 * 
	 * @param server
	 *            This parameter is a hint from the {@link ESServer}. For that reason it can be null. A common
	 *            example is share, where the user first has to select the server before logging in. If {@link ESServer}
	 *            is set you should allow the user to select the account for the given server.
	 * 
	 * @return an user session. It is not specified whether this session is logged in or logged out.
	 * @throws ESException in case an exception occurred while obtaining the user session
	 */
	public abstract ESUsersession provideUsersession(ESServer server) throws ESException;

	/**
	 * <p>
	 * This method is called by the SessionManager in order to login a given user session. If the given session can not
	 * be logged in, it is the provider's responsibility to either throw an exception or to provide another valid
	 * session, e.g. by means of calling a login hdialog that will create a new session.
	 * </p>
	 * 
	 * @param usersession
	 *            the session to be logged in
	 * 
	 * @return a logged in user session, possibly another one as the one passed in. It is the provider's responsibility
	 *         to determine whether the passed in session and the one that is returned need to differ
	 * 
	 * @throws ESException in case an exception occurred while logging in the given session
	 */
	public abstract ESUsersession login(ESUsersession usersession) throws ESException;
}
