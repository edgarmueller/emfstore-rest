package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import java.util.HashSet;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.SessionProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class LoginController implements SessionProvider {

	public Usersession provideUsersession() {
		UsersessionSelectionDialog dialog = new UsersessionSelectionDialog(Display.getCurrent().getActiveShell(), this);
		int open = dialog.open();
		return open == Dialog.OK ? dialog.getResult() : null;
	}

	public void loginSession(Usersession usersession) {
		NewLoginDialog dialog = new NewLoginDialog(Display.getCurrent().getActiveShell(), this);
		dialog.setBlockOnOpen(true);
		dialog.open();
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

}
