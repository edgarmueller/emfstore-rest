package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import java.util.HashSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

public class LoginDialogController implements ILoginDialogController {

	public Usersession[] getKnownUsersessions() {
		HashSet<Object> set = new HashSet<Object>();
		for (Usersession session : WorkspaceManager.getInstance().getCurrentWorkspace().getUsersessions()) {
			if (getServerInfo().equals(session.getServerInfo())) {
				set.add(session);
			}
		}
		return set.toArray(new Usersession[set.size()]);
	}

	public Usersession login() throws AccessControlException {
		LoginDialog dialog = new LoginDialog(Display.getCurrent().getActiveShell(), this);
		dialog.setBlockOnOpen(true);

		if (dialog.open() != Window.OK) {
			throw new AccessControlException("Couldn't login.");
		}

		Usersession selectedUsersession = dialog.getSelectedUsersession();
		try {
			selectedUsersession.logIn();
		} catch (EmfStoreException e) {
			e.printStackTrace();
		}
		return selectedUsersession;
	}

	public boolean isUsersessionLocked() {
		if (getUsersession() == null || getUsersession().getUsername() == null
			|| getUsersession().getUsername().equals("")) {
			return false;
		}

		return true;
	}

	public String getServerLabel() {
		return getServerInfo().getName();
	}

	public void validate(Usersession usersession) throws EmfStoreException {
		usersession.logIn();
		EList<Usersession> usersessions = WorkspaceManager.getInstance().getCurrentWorkspace().getUsersessions();
		// TODO login code
		if (!usersessions.contains(usersession)) {
			usersessions.add(usersession);
		}
		// if login successfuly set usersesion to field fo
	}

	private Usersession usersession;
	private ServerInfo serverInfo;

	public LoginDialogController(Usersession usersession) {
		this.usersession = usersession;
		serverInfo = usersession.getServerInfo();
	}

	public LoginDialogController(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
		this.usersession = null;
	}

	public Usersession getUsersession() {
		return usersession;
	}

	public ServerInfo getServerInfo() {
		return usersession.getServerInfo();
	}

}
