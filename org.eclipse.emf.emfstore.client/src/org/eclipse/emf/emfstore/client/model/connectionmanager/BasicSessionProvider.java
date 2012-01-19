package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class BasicSessionProvider extends AbstractSessionProvider {

	public Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException {
		throw new EmfStoreException("No usersession found.");
	}

	public void login(Usersession usersession) throws EmfStoreException {
		throw new EmfStoreException("Usersession not logged in. Login first.");
	}
}
