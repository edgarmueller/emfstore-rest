package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import java.util.HashSet;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.SessionProvider;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

public class LoginController implements SessionProvider {

	private Usersession session;

	public Usersession provideUsersession() {
		if (session != null) {
			return session;
		}

		UsersessionSelectionDialog dialog = new UsersessionSelectionDialog(Display.getCurrent().getActiveShell(), this);
		if (dialog.open() == Dialog.OK) {
			session = dialog.getResult();
		}

		return session;
	}

	public void loginSession(Usersession usersession) throws AccessControlException, EmfStoreException {
		if (usersession != null && !usersession.isLoggedIn()) {
			NewLoginDialog dialog = new NewLoginDialog(Display.getCurrent().getActiveShell(), usersession);
			dialog.setBlockOnOpen(true);
			if (dialog.open() == Window.OK) {
				usersession.logIn();
			}
		} else if (usersession == null) {
			provideUsersession();
		}
	}

	public Object[] getServerInfoInput() {
		Workspace workspace = WorkspaceManager.getInstance().getCurrentWorkspace();
		return workspace.getServerInfos().toArray();
	}

	public Object[] getUsersessions(ServerInfo serverInfo) {
		HashSet<Object> set = new HashSet<Object>();
		for (Usersession session : WorkspaceManager.getInstance().getCurrentWorkspace().getUsersessions()) {
			if (serverInfo.equals(session.getServerInfo())) {
				set.add(session);
			}
		}
		set.add("<< create new session >>");
		return set.toArray();
	}

	public Usersession getUsersession() {
		return session;
	}

}
