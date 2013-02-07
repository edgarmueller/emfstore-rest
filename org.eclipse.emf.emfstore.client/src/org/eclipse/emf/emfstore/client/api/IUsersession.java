/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.api;

import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.SessionId;
import org.eclipse.emf.emfstore.server.model.api.ISessionId;

/**
 * Usersession for a given {@link IServer}. Can be used with multiple projects.
 * A Usersession is gained by calling {@link IServer#login(String, String)}
 * 
 * @author emueller
 * @author wesendon
 */
public interface IUsersession {

	/**
	 * Returns the usersession's server.
	 * 
	 * @return server
	 */
	IServer getServer();

	/**
	 * Returns the username.
	 * 
	 * @return username
	 */
	String getUsername();

	/**
	 * Returns the password. The password is encrypted with the server's public key, so there's no access to the
	 * cleartext password.
	 * 
	 * @return encrypted password
	 */
	String getPassword();

	/**
	 * Checks whether the usersession has a {@link SessionId}.
	 * 
	 * TODO OTS: use isloggedin of connection layer? or even check with server?
	 * 
	 * @return true, if session is logged in
	 */
	boolean isLoggedIn();

	/**
	 * Relogins into the server using the same credentials in order to update the sessionid.
	 * 
	 * @throws EMFStoreException
	 */
	void renew() throws EMFStoreException;

	/**
	 * Logs out the usersession on the server and locally.
	 * 
	 * @throws EMFStoreException
	 */
	void logout() throws EMFStoreException;

	/**
	 * Returns the session id of this session. Can be reused by calling {@link #renew()}
	 * 
	 * @return current session id
	 */
	ISessionId getSessionId();
}
