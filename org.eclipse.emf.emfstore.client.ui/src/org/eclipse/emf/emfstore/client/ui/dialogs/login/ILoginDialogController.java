/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;

/**
 * The login dialog controller manages a given {@link Usersession} and/or a {@link ServerInfo} instance
 * to determine when it is necessary to open a {@link LoginDialog} in order to authenticate the user.
 * If authentication already took place no such dialog should be opened.
 * 
 * @author ovonwesen
 * @author emueller
 */
public interface ILoginDialogController {

	/**
	 * Tries to login the given {@link Usersession}. If successful, the user session
	 * is attached to the workspace and saved.
	 * 
	 * @param usersession
	 *            the usersession to be validated
	 * @throws EMFStoreException
	 *             in case the log-in of the user session fails
	 */
	void validate(Usersession usersession) throws EMFStoreException;

	/**
	 * Whether the login controller has an {@link Usersession} assigned.
	 * 
	 * @return true, if the login controller has an user session assigned, false otherwise
	 */
	boolean isUsersessionLocked();

	/**
	 * Returns the {@link Usersession} the login dialog controller was assigned to, if any.
	 * 
	 * @return the assigned user session or <code>null</code>, if none exists
	 */
	Usersession getUsersession();

	/**
	 * Returns the available {@link Usersession}s based on server info object, that is retrieved via
	 * {@link #getServerInfo()}.
	 * 
	 * @return all available user sessions as an array.
	 */
	Usersession[] getKnownUsersessions();

	/**
	 * Returns the name of the {@link ServerInfo} that may be retrieved via {@link getServerInfo()}.
	 * 
	 * @return the name of the server info
	 */
	String getServerLabel();

	/**
	 * Returns the {@link ServerInfo} the login dialog controller was assigned to, if any.
	 * If no server info was set, {@link getUsersession() } will be used to try to determine the
	 * relevant server info.
	 * 
	 * @return the server info, if any
	 */
	ServerInfo getServerInfo();
}