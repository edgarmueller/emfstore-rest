package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import org.eclipse.emf.emfstore.client.model.ModelFactory;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.SessionProvider;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author wesendon
 * 
 */
public class DefaultSessionProvider implements SessionProvider {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.connectionmanager.SessionProvider#provideUsersession(org.eclipse.emf.emfstore.client.model.ServerInfo)
	 */
	public Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException {

		if (serverInfo == null) {
			// try to retrieve a server info by showing a server info selection dialog
			ServerInfoSelectionDialog dialog = new ServerInfoSelectionDialog(Display.getCurrent().getActiveShell(),
				WorkspaceManager.getInstance().getCurrentWorkspace().getServerInfos());
			if (dialog.open() == Dialog.OK) {
				serverInfo = dialog.getResult();
			}
		}

		if (serverInfo == null) {
			throw new AccessControlException("Couldn't determine which server to connect.");
		}

		if (serverInfo.getLastUsersession() != null) {
			return serverInfo.getLastUsersession();
		}

		Usersession createdUsersession = ModelFactory.eINSTANCE.createUsersession();
		createdUsersession.setServerInfo(serverInfo);
		return createdUsersession;
		// return new ServerInfoLoginDialogController(serverInfo).login();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.connectionmanager.SessionProvider#loginSession(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	public void loginSession(Usersession usersession) throws EmfStoreException {
		if (usersession != null && !usersession.isLoggedIn()) {
			new LoginDialogController(usersession).login();
		}
	}
}
