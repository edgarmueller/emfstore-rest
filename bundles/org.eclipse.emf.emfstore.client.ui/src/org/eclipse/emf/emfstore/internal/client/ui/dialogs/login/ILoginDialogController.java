/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * ovonwesen
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.dialogs.login;

import java.util.List;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

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
	 * @throws ESException
	 *             in case the log-in of the user session fails
	 */
	void validate(ESUsersession usersession) throws ESException;

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
	ESUsersession getUsersession();

	/**
	 * Returns the available {@link Usersession}s based on server info object, that is retrieved via
	 * {@link #getServer()}.
	 * 
	 * @return all available user sessions as an array.
	 */
	List<ESUsersession> getKnownUsersessions();

	/**
	 * Returns the {@link ServerInfo} the login dialog controller was assigned to, if any.
	 * If no server info was set, {@link getUsersession() } will be used to try to determine the
	 * relevant server info.
	 * 
	 * @return the server info, if any
	 */
	ESServer getServer();
}
