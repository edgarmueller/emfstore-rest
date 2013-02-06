package org.eclipse.emf.emfstore.client.api;

public interface IUsersession {

	String getUsername();

	IServer getServer();

	ISessionId getSessionId();
}
