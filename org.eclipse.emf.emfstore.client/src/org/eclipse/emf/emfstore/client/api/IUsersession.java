package org.eclipse.emf.emfstore.client.api;

import org.eclipse.emf.emfstore.server.model.api.ISessionId;

public interface IUsersession {

	String getUsername();

	IServer getServer();

	ISessionId getSessionId();
}