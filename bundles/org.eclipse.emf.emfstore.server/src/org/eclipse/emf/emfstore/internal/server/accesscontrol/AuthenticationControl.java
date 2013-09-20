/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.accesscontrol;

import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;

/**
 * Controller for the Authentication of users.
 * 
 * @author koegel
 */
public interface AuthenticationControl {

	/**
	 * Tries to login the given user.
	 * 
	 * @param resolvedUser
	 *            the user instance as resolved by the user
	 * @param username
	 *            the username as determined by the client
	 * @param password
	 *            the password as entered by the client
	 * @param clientVersionInfo
	 *            the version of the client
	 * @return an {@link AuthenticationInformation} instance holding information about the
	 *         logged-in session
	 * 
	 * @throws AccessControlException in case the login fails
	 */
	AuthenticationInformation logIn(ACUser resolvedUser, String username, String password,
		ClientVersionInfo clientVersionInfo)
		throws AccessControlException;

	/**
	 * Logout/delete a session on the server.
	 * 
	 * @param sessionId
	 *            the id of the session to be logout
	 * @throws AccessControlException
	 *             in case of failure on server
	 */
	void logout(SessionId sessionId) throws AccessControlException;

}
