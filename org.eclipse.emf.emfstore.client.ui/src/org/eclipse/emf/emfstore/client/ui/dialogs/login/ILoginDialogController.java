package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public interface ILoginDialogController {

	public void validate(Usersession usersession) throws EmfStoreException;

	public boolean isUsersessionLocked();

	public Usersession getUsersession();

	/**
	 * Returns the available {@link Usersession}s based on server info object, that is retrieved via
	 * {@link #getServerInfo()}.
	 * 
	 * @return all available user sessions as an array.
	 */
	Usersession[] getKnownUsersessions();

	public String getServerLabel();

	public ServerInfo getServerInfo();
}