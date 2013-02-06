package org.eclipse.emf.emfstore.client.api;

public interface IServerCall {

	IServer getServer();

	ILocalProject getLocalProject();

	IUsersession getUsersession();

	void setUsersession(IUsersession usersession);

}
