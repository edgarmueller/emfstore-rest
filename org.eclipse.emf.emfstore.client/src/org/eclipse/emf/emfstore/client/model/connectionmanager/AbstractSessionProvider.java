package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public abstract class AbstractSessionProvider {

	/**
	 * ExtensionPoint ID of the SessionProvider.
	 */
	public final static String ID = "org.eclipse.emf.emfstore.client.sessionprovider";

	protected Usersession provideUsersession(ServerCall<?> serverCall) throws EmfStoreException {

		Usersession usersession = serverCall.getUsersession();
		if (usersession == null) {
			usersession = getUsersessionFromProjectSpace(serverCall.getProjectSpace());
		}

		if (usersession == null) {
			usersession = provideUsersession(serverCall.getServerInfo());
		}

		serverCall.setUsersession(usersession);
		return usersession;
	}

	protected Usersession getUsersessionFromProjectSpace(ProjectSpace projectSpace) {
		if (projectSpace != null && projectSpace.getUsersession() != null) {
			return projectSpace.getUsersession();
		}
		return null;
	}

	public abstract Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException;

	public abstract void login(Usersession usersession) throws EmfStoreException;
}
