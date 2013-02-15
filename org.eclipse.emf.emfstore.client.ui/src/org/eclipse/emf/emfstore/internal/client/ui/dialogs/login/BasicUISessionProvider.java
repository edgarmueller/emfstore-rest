/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Max Hohenegger (bug 371196)
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.dialogs.login;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.sessionprovider.AbstractSessionProvider;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.LoginCanceledException;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;

/**
 * An implementation of a session providers that uses a server selection and a login dialog
 * to authenticate users.
 * 
 * @author wesendon
 * @author emueller
 */
public class BasicUISessionProvider extends AbstractSessionProvider {

	private ServerInfo selectedServerInfo;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.connectionmanager.SessionProvider#provideUsersession(org.eclipse.emf.emfstore.internal.client.model.ServerInfo)
	 */
	@Override
	public ESUsersession provideUsersession(ESServer server) throws ESException {
		if (server == null) {
			Integer userInput = RunInUI.runWithResult(new Callable<Integer>() {
				public Integer call() throws Exception {
					// try to retrieve a server info by showing a server info selection dialog
					ServerInfoSelectionDialog dialog = new ServerInfoSelectionDialog(Display.getCurrent()
						.getActiveShell(), ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace())
						.getServers());
					int input = dialog.open();
					selectedServerInfo = dialog.getResult();
					return input;
				}
			});

			if (userInput == Dialog.OK) {
				server = selectedServerInfo;
			} else if (userInput == Dialog.CANCEL) {
				throw new LoginCanceledException("Operation canceled by user.");
			}
		}
		if (server == null) {
			throw new AccessControlException("Couldn't determine which server to connect.");
		}

		return loginServerInfo(server);
	}

	/**
	 * Extracted from {@link #provideUsersession(ServerInfo)} in order to allow overwriting. This method logs in a given
	 * serverInfo.
	 * 
	 * @param serverInfo given serverInfo
	 * @return Usersession
	 * @throws ESException in case of an exception
	 */
	protected ESUsersession loginServerInfo(ESServer server) throws ESException {
		// TODO Short cut for logged in sessions to avoid loginscreen. We have to discuss whether this is really
		// wanted.
		if (server.getLastUsersession() != null && ((Usersession) server.getLastUsersession()).isLoggedIn()) {
			return server.getLastUsersession();
		}
		return new LoginDialogController().login((ServerInfo) server);
	}

	@Override
	public void login(ESUsersession usersession) throws ESException {
		if (usersession != null) {
			new LoginDialogController().login((Usersession) usersession);
		}
	}
}