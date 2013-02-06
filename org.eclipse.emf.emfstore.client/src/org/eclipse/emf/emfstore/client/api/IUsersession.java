package org.eclipse.emf.emfstore.client.api;

import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.api.ISessionId;

public interface IUsersession {

	IServer getServer();

	boolean isLoggedIn();

	void renew() throws EMFStoreException;

	void logout() throws EMFStoreException;

	String getUsername();

	ISessionId getSessionId();
}
