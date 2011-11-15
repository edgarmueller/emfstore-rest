package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.connectionmanager.SessionProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class LoginController implements SessionProvider {

	public Usersession provideUsersession() {
		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "NOT IMPLEMENTED",
			"THIS DIALOG SHOULD ALLOW YOU TO CHOOSE YOUR USERSESSION.");
		return null;
	}

	public void loginSession(Usersession usersession) {
		NewLoginDialog dialog = new NewLoginDialog(Display.getCurrent().getActiveShell(), this);
		dialog.setBlockOnOpen(true);
		dialog.open();
	}

	public Object getServerInfoInput() {
		return null;
	}

}
