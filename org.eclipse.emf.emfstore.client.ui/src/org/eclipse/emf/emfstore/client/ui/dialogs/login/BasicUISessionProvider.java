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
package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.AbstractSessionProvider;
import org.eclipse.emf.emfstore.client.model.exceptions.LoginCanceledException;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThreadWithResult;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author wesendon
 * 
 */
public class BasicUISessionProvider extends AbstractSessionProvider {

	private ServerInfo selectedServerInfo;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.connectionmanager.SessionProvider#provideUsersession(org.eclipse.emf.emfstore.client.model.ServerInfo)
	 */
	@Override
	public Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException {
		if (serverInfo == null) {

			Integer userInput = new RunInUIThreadWithResult<Integer>(Display.getDefault()) {
				@Override
				public Integer run(Shell shell) {
					// try to retrieve a server info by showing a server info selection dialog
					ServerInfoSelectionDialog dialog = new ServerInfoSelectionDialog(Display.getCurrent()
						.getActiveShell(), WorkspaceManager.getInstance().getCurrentWorkspace().getServerInfos());
					int input = dialog.open();
					selectedServerInfo = dialog.getResult();
					return input;
				}
			}.execute();

			if (userInput == Dialog.OK) {
				serverInfo = selectedServerInfo;
			} else if (userInput == Dialog.CANCEL) {
				throw new LoginCanceledException("Operation canceled by user.");
			}
		}
		if (serverInfo == null) {
			throw new AccessControlException("Couldn't determine which server to connect.");
		}

		return new LoginDialogController().login(serverInfo);
	}

	@Override
	public void login(Usersession usersession) throws EmfStoreException {
		if (usersession != null) {
			new LoginDialogController().login(usersession);
		}
	}
}
