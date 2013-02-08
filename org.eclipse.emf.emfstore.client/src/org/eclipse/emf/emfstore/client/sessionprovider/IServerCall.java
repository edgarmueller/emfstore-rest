package org.eclipse.emf.emfstore.client.sessionprovider;

import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.client.IServer;
import org.eclipse.emf.emfstore.client.IUsersession;

public interface IServerCall {

	IUsersession getUsersession();

	ILocalProject getLocalProject();

	IServer getServer();

}
