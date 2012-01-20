package org.eclipse.emf.emfstore.client.model.controller;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class ChangeSessionController {

	private final ServerInfo serverInfo;

	public ChangeSessionController(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;

	}

	public void logout() throws EmfStoreException {

		Usersession session = serverInfo.getLastUsersession();

		if (session == null) {
			return;
		}

		session.logout();

		// reset the password in the RAM cache
		if (!session.isSavePassword()) {
			session.setPassword(null);
		}

		WorkspaceManager.getInstance().getCurrentWorkspace().save();
	}
}
