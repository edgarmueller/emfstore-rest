package org.eclipse.emf.emfstore.client.api;

// TODO OTS really expose this?
public interface IServerCall {

	IServer getServer();

	ILocalProject getLocalProject();

	IUsersession getUsersession();

	void setUsersession(IUsersession usersession);

}
