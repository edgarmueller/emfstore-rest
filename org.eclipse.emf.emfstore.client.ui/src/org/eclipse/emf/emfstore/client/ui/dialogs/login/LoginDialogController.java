package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import java.util.HashSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThreadWithReturnValue;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class LoginDialogController implements ILoginDialogController {

	private Usersession usersession;
	private ServerInfo serverInfo;

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
		AccessControlException exception = new RunInUIThreadWithReturnValue<AccessControlException>(
			Display.getDefault()) {

			@Override
			public AccessControlException run(Shell shell) {
				LoginDialog dialog = new LoginDialog(shell, LoginDialogController.this);
				dialog.setBlockOnOpen(true);

				if (dialog.open() != Window.OK || usersession == null) {
					return new AccessControlException("Couldn't login.");
				}

				return null;
			}
		}.execute();

		if (exception != null) {
			throw exception;
		}

		// contract #validate() sets the usersession;
		return usersession;
	}

	public boolean isUsersessionLocked() {
		if (getUsersession() == null) {
			return false;
		}
		return true;
	}

	public String getServerLabel() {
		return getServerInfo().getName();
	}

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

	public Usersession getUsersession() {
		return usersession;
	}

	public ServerInfo getServerInfo() {
		if (serverInfo != null) {
			return serverInfo;
		}
		return usersession.getServerInfo();
	}

	public Usersession login(ServerInfo serverInfo) throws EmfStoreException {
		this.serverInfo = serverInfo;
		this.usersession = null;
		return login();
	}

	public void login(Usersession usersession) throws EmfStoreException {
		this.serverInfo = null;
		this.usersession = usersession;
		login();
	}
}
