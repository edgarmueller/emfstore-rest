/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client;

import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESSessionId;

/**
 * User session for a given {@link ESServer}. Can be used with multiple projects.
 * An user session is gained by calling {@link ESServer#login(String, String)}
 * 
 * @author emueller
 * @author wesendon
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESUsersession {

	/**
	 * Returns the usersession's server.
	 * 
	 * @return the server this user session is associated with
	 */
	ESServer getServer();

	/**
	 * Returns the name of the user this user session is associated with.
	 * 
	 * @return username
	 *         the name of the user this user session is associated with
	 */
	String getUsername();

	/**
	 * Returns the password. The password is encrypted with the server's public key, so there's no access to the
	 * cleartext password.
	 * 
	 * @return the encrypted password
	 */
	String getPassword();

	/**
	 * Checks whether the user session has a {@link ESSessionId}.
	 * 
	 * @return true, if session is logged in, false otherwise
	 */
	// TODO: use isLoggedIn of connection layer? or even check with server?
	boolean isLoggedIn();

	/**
	 * Relogins into the server using the same credentials in order to update the {@link ESSessionId}.
	 * 
	 * @throws ESException in case renewal of the session failed
	 */
	// TODO: mention how long an user session valid
	void refresh() throws ESException;

	/**
	 * Logs out the user session.
	 * 
	 * @throws ESException in case an error occurred during logout
	 */
	void logout() throws ESException;

	/**
	 * Returns the session id of this session.
	 * 
	 * @return the current session id
	 */
	ESSessionId getSessionId();

	/**
	 * Whether the password that is used by this session should be saved.
	 * 
	 * @param shouldSavePassword
	 *            {@code true} if the password should be saved, {@code false} otherwise
	 * 
	 * @since 1.1
	 */
	void setSavePassword(boolean shouldSavePassword);

	/**
	 * Whether the password that is used by this session will be saved.
	 * 
	 * @return {@code true} if the password is saved, {@code false} otherwise
	 * 
	 * @since 1.1
	 */
	boolean isSavePassword();

	/**
	 * Deletes this usersession.
	 * 
	 * @throws ESException
	 *             in case removing the session fails
	 * 
	 * @since 1.1
	 */
	void delete() throws ESException;
}
