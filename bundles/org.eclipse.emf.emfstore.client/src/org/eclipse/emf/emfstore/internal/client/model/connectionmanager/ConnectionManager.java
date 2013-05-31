/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * MaximilianKoegel
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager;

import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * The connection manager manages the connection to the EmfStore. It will initiate, reinitiate and terminate the
 * connection as needed.
 * 
 * @author Maximilian Koegel
 * @author wesendon
 * @generated NOT
 */
public interface ConnectionManager extends EMFStore {

	/**
	 * Connection related failure message.
	 */
	String REMOTE = "Server could not be reached.\n";

	/**
	 * Connection related failure message.
	 */
	String LOGIN_FIRST = "Session unkown to Connection manager, log in first!";

	/**
	 * Connection related failure message.
	 */
	String UNSUPPORTED_ENCODING = "Problem with en/decoding.";

	/**
	 * Connection related failure message.
	 */
	String LOGIN_REFUSED = "Login refused.";

	/**
	 * Connection related failure message.
	 */
	String INCOMPATIBLE_VERSION = "Client version not compatible with server. Please update your client.";

	/**
	 * Log into the server given by server info. The connection manager will also remember the serverInfo associated
	 * with the session id.
	 * 
	 * @param username the user name
	 * @param password the password
	 * @param severInfo the server info for the server to log into
	 * @param clientVersionInfo the client's version
	 * @return {@link AuthenticationInformation} that can be used for later authentication
	 * @throws ESException in case of failure
	 * @generated NOT
	 */
	AuthenticationInformation logIn(String username, String password, ServerInfo severInfo,
		ClientVersionInfo clientVersionInfo) throws ESException;

	/**
	 * Deletes a session on the server.
	 * 
	 * @param sessionId id to be deleted.
	 * @throws ESException in case of failure on server
	 */
	void logout(SessionId sessionId) throws ESException;

	/**
	 * Checks whether session id is logged in.
	 * 
	 * @param id session id
	 * @return true, if logged in
	 */
	boolean isLoggedIn(SessionId id);
}
