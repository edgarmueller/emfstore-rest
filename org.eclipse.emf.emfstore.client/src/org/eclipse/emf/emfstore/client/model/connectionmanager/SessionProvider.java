package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

/**
 * This interface/extensionpoint allows to modifiy the login/server selection behavior. If an {@link ServerCall} isn't
 * able to execute due to missing usersession or server, it uses this class to gain or login.
 * 
 * @see org.eclipse.emf.emfstore.client.ui.dialogs.login.DefaultSessionProvider
 * 
 * @author wesendon
 * 
 */
public interface SessionProvider {

	/**
	 * ExtensionPoint ID of the SessionProvider.
	 */
	public final String ID = "org.eclipse.emf.emfstore.client.sessionprovider";

	/**
	 * This method is called if the {@link ServerCall} couldn't determine a given usersession or extract it from the
	 * projectspace.
	 * 
	 * @param serverInfo can be null, gives more context to the sessionprovider. Allows to filter for a specific server.
	 * 
	 * @throws EmfStoreException in case of failure
	 * 
	 * @return a usersession, which can but isn't required to be logged in
	 */
	Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException;;

	/**
	 * This method is used to let the {@link SessionProvider} login a given usersession when executing a server call.
	 * Normally one would trigger a login dialog or similar.
	 * 
	 * @param usersession session, which has to be logged in
	 * 
	 * @throws EmfStoreException in case of failure
	 */
	void loginSession(Usersession usersession) throws EmfStoreException;

}
