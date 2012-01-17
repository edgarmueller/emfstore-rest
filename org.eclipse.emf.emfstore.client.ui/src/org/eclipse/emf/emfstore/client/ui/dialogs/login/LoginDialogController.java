package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import java.util.HashSet;

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

	public void validate(Usersession usersession) throws AccessControlException {
		// TODO login code

		// if login successfuly set usersesion to field fo
	}
	

	private final Usersession usersession;

	public LoginDialogController(Usersession usersession) {
		this.usersession = usersession;
	}

	public Usersession getUsersession() {
		return usersession;
	}

	public ServerInfo getServerInfo() {
		return usersession.getServerInfo();
	}

}
