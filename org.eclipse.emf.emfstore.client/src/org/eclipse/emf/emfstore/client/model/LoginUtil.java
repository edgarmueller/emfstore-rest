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
package org.eclipse.emf.emfstore.client.model;

import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

/**
 * Controller class to carry out the session procedures.
 * 
 * @author wesendon
 */
public final class LoginUtil {

	private LoginUtil() {
	}

	/**
	 * Login using the given session.
	 * 
	 * @throws AccessControlException if login fails.
	 * @throws EmfStoreException if anything else fails.
	 * @param session the usersession
	 */
	public static void login(Usersession session) throws AccessControlException, EmfStoreException {
		session.logIn();
	}

	/**
	 * Sends a logout call to the server.
	 * 
	 * @param session the usersession.
	 * @throws EmfStoreException forwards any exception.
	 */
	public static void logout(Usersession session) throws EmfStoreException {
		session.logout();
	}

	public static ServerInfo createServerInfo(String name, String url, int port, String alias) {
		ServerInfo serverInfo = ModelFactory.eINSTANCE.createServerInfo();
		serverInfo.setName(name);
		serverInfo.setUrl(url);
		serverInfo.setPort(port);
		serverInfo.setCertificateAlias(alias);
		return serverInfo;
	}

	public static Usersession createUsersession(String name, String password, ServerInfo serverInfo) {
		Usersession usersession = ModelFactory.eINSTANCE.createUsersession();
		usersession.setUsername(name);
		usersession.setPassword(password);
		usersession.setServerInfo(serverInfo);
		return usersession;
	}
}