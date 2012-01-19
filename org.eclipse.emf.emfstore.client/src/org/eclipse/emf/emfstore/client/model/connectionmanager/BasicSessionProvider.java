package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class BasicSessionProvider extends AbstractSessionProvider {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.connectionmanager.AbstractSessionProvider#provideUsersession(org.eclipse.emf.emfstore.client.model.ServerInfo)
	 */
	@Override
	public Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException {
		throw new EmfStoreException("No usersession found.");
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.connectionmanager.AbstractSessionProvider#login(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	@Override
	public void login(Usersession usersession) throws EmfStoreException {
		throw new EmfStoreException("Usersession not logged in. Login first.");
	}
}
