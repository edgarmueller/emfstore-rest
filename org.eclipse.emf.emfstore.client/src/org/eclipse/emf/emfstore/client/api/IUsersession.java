package org.eclipse.emf.emfstore.client.api;

import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.api.ISessionId;

public interface IUsersession {

	String getUsername();

	String getPassword();

	IServer getServer();

	boolean isLoggedIn();

	void renew() throws EMFStoreException;

	void logout() throws EMFStoreException;

	ISessionId getSessionId();
}
