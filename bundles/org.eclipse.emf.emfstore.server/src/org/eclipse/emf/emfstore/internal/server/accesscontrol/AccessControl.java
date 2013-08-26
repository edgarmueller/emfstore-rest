/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.accesscontrol;

import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.AbstractAuthenticationControl;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;

/**
 * Access control combining authentication and authorization.
 * 
 * @author emueller
 * 
 */
public interface AccessControl extends AuthorizationControl {

	/**
	 * Log in with the given credentials.
	 * 
	 * @param username
	 *            the username as entered by the client
	 * @param password
	 *            the password as entered by the client
	 * @param clientVersionInfo
	 *            the version of the client
	 * @return an {@link AuthenticationInformation} holding information
	 *         about the logged-in session
	 * 
	 * @throws AccessControlException
	 *             in case an error occurs during the login
	 */
	AuthenticationInformation logIn(String username, String password,
		ClientVersionInfo clientVersionInfo) throws AccessControlException;

	/**
	 * Log out the session with the given ID.
	 * 
	 * @param sessionId
	 *            the ID of the session to be logged out
	 * @throws AccessControlException
	 *             in case logout fails
	 */
	void logout(SessionId sessionId) throws AccessControlException;

	/**
	 * Sets the authentication control to be used.
	 * 
	 * @param modelAuthenticationControl
	 *            the authentication control to be used
	 */
	void setAuthenticationControl(AbstractAuthenticationControl modelAuthenticationControl);
}
