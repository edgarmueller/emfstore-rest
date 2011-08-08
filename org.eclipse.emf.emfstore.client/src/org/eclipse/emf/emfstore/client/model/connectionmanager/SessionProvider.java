package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.Usersession;

public interface SessionProvider {

	final String ID = "org.eclipse.emf.emfstore.client.sessionprovider";

	Usersession getUsersession();

	void loginSession(Usersession usersession);

}
