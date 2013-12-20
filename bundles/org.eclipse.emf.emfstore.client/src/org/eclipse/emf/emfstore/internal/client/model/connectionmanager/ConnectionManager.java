/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel, Otto von Wesendonk - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager;

import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * The connection manager manages the connection to the EMFStore.
 * It will initiate, reinitiate and terminate the connection as needed.
 * 
 * @author mkoegel
 * @author wesendon
 */
public interface ConnectionManager extends EMFStore {

	/**
	 * Connection related failure message.
	 */
	String REMOTE = Messages.ConnectionManager_Server_Could_Not_Be_Reached;

	/**
	 * Connection related failure message.
	 */
	String LOGIN_FIRST = Messages.ConnectionManager_Session_Unknown;

	/**
	 * Connection related failure message.
	 */
	String UNSUPPORTED_ENCODING = Messages.ConnectionManager_Encoding_Problem;

	/**
	 * Connection related failure message.
	 */
	String LOGIN_REFUSED = Messages.ConnectionManager_Login_Refused;

	/**
	 * Connection related failure message.
	 */
	String INCOMPATIBLE_VERSION = Messages.ConnectionManager_Incompatible_Client_Version;

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
