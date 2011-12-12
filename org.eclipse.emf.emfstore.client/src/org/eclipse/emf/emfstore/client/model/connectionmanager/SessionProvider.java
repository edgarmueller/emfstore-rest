package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public interface SessionProvider {

	final String ID = "org.eclipse.emf.emfstore.client.sessionprovider";

	Usersession provideUsersession();

	void loginSession(Usersession usersession) throws AccessControlException, EmfStoreException;

}
