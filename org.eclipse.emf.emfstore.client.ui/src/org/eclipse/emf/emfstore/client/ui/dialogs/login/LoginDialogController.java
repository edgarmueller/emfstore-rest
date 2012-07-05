/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import java.util.HashSet;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

/**
 * The login dialog controller manages a given {@link Usersession} and/or a {@link ServerInfo} to determine
 * when it is necessary to open a {@link LoginDialog} in order to authenticate the user.
 * It does not, however, open a dialog, if the usersession is already logged in.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class LoginDialogController implements ILoginDialogController {

	private Usersession usersession;
	private ServerInfo serverInfo;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.login.ILoginDialogController#getKnownUsersessions()
	 */
	public Usersession[] getKnownUsersessions() {
		HashSet<Object> set = new HashSet<Object>();
		for (Usersession session : WorkspaceManager.getInstance().getCurrentWorkspace().getUsersessions()) {
			if (getServerInfo().equals(session.getServerInfo())) {
				set.add(session);
			}
		}
		return set.toArray(new Usersession[set.size()]);
	}

	private Usersession login() throws EmfStoreException {
		return RunInUI.WithException.runWithResult(new Callable<Usersession>() {
			public Usersession call() throws Exception {

				if (serverInfo.getLastUsersession() != null && serverInfo.getLastUsersession().isLoggedIn()) {
					return serverInfo.getLastUsersession();
				}

				LoginDialog dialog = new LoginDialog(Display.getCurrent().getActiveShell(), LoginDialogController.this);
				dialog.setBlockOnOpen(true);

				if (dialog.open() != Window.OK || usersession == null) {
					throw new AccessControlException("Couldn't login.");
				}

				// contract: #validate() sets the usersession;
				return usersession;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.login.ILoginDialogController#isUsersessionLocked()
	 */
	public boolean isUsersessionLocked() {
		if (getUsersession() == null) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.login.ILoginDialogController#getServerLabel()
	 */
	public String getServerLabel() {
		return getServerInfo().getName();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.login.ILoginDialogController#validate(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	public void validate(Usersession usersession) throws EmfStoreException {
		// TODO login code
		usersession.logIn();
		// if successful, else exception is thrown prior reaching this code
		EList<Usersession> usersessions = WorkspaceManager.getInstance().getCurrentWorkspace().getUsersessions();
		if (!usersessions.contains(usersession)) {
			usersessions.add(usersession);
		}
		this.usersession = usersession;
		WorkspaceManager.getInstance().getCurrentWorkspace().save();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.login.ILoginDialogController#getUsersession()
	 */
	public Usersession getUsersession() {
		return usersession;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.login.ILoginDialogController#getServerInfo()
	 */
	public ServerInfo getServerInfo() {
		if (serverInfo != null) {
			return serverInfo;
		}
		return usersession.getServerInfo();
	}

	/**
	 * Perform a login using an {@link Usersession} that can be determined
	 * with the given {@link ServerInfo}.
	 * 
	 * 
	 * @param serverInfo
	 *            the server info to be used in order to determine a valid usersession
	 * @return a logged-in usersession
	 * @throws EmfStoreException
	 *             in case the login fails
	 */
	public Usersession login(ServerInfo serverInfo) throws EmfStoreException {
		this.serverInfo = serverInfo;
		this.usersession = null;
		return login();
	}

	/**
	 * Perform a login using the given {@link Usersession}.
	 * 
	 * @param usersession
	 *            the usersession to be used during login
	 * @throws EmfStoreException
	 *             in case the login fails
	 */
	public void login(Usersession usersession) throws EmfStoreException {
		this.serverInfo = null;
		this.usersession = usersession;
		login();
	}
}
