package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import java.util.HashSet;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.SessionProvider;
import org.eclipse.emf.emfstore.client.ui.dialogs.login.LoginDialog.LoginDialogControllerInterface;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author wesendon
 * 
 */
public class DefaultSessionProvider implements SessionProvider {

	public Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException {

		if (serverInfo == null) {
			ServerInfoSelectionDialog dialog = new ServerInfoSelectionDialog(Display.getCurrent().getActiveShell(),
				WorkspaceManager.getInstance().getCurrentWorkspace().getServerInfos());
			if (dialog.open() == Dialog.OK) {
				serverInfo = dialog.getResult();
			}
		}

		if (serverInfo == null) {
			throw new AccessControlException("Couldn't determine which server to connect.");
		}

		return new LoginDialogController().login(serverInfo);
	}

	public void loginSession(Usersession usersession) throws EmfStoreException {
		if (usersession != null && !usersession.isLoggedIn()) {
			new LoginDialogController().login(usersession);
		}
	}

	// TODO extract to own class
	public class LoginDialogController implements LoginDialogControllerInterface {

		private Usersession usersession;
		private ServerInfo serverInfo;

		public void login(Usersession session) throws AccessControlException {
			if (session == null) {
				throw new AccessControlException("No Session provided.");
			}
			this.usersession = session;
			this.serverInfo = null;
			LoginDialog dialog = new LoginDialog(Display.getCurrent().getActiveShell(), this);
			dialog.setBlockOnOpen(true);
			if (dialog.open() != Window.OK) {
				throw new AccessControlException("Couldn't login.");
			}
		}

		public Usersession login(ServerInfo serverInfo) throws AccessControlException {
			if (serverInfo == null) {
				throw new AccessControlException("No ServerInfo provided.");
			}
			this.usersession = null;
			this.serverInfo = serverInfo;
			LoginDialog dialog = new LoginDialog(Display.getCurrent().getActiveShell(), this);
			dialog.setBlockOnOpen(true);
			if (dialog.open() != Window.OK) {
				throw new AccessControlException("Couldn't login.");
			}
			return this.getUsersession();
		}

		public void validate(Usersession usersession) throws AccessControlException {
			// TODO login code

			// if login successfuly set usersesion to field fo
		}

		public boolean isUsersessionLocked() {
			return usersession != null;
		}

		public Usersession getUsersession() {
			return usersession;
		}

		public String getServerLabel() {
			if (serverInfo != null) {
				return "" + serverInfo.getName();
			} else if (usersession != null && usersession.getServerInfo() != null) {
				return "" + usersession.getServerInfo().getName();
			}
			return "";
		}

		public Usersession[] getKnownUsersessions() {
			HashSet<Object> set = new HashSet<Object>();
			for (Usersession session : WorkspaceManager.getInstance().getCurrentWorkspace().getUsersessions()) {
				if (serverInfo.equals(session.getServerInfo())) {
					set.add(session);
				}
			}
			return (Usersession[]) set.toArray();
		}
	}

}
