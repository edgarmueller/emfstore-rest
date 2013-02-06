package org.eclipse.emf.emfstore.client.api;

public interface IServerFactory {

	IServer getServer(String name, String url, int port, String certificate);

	IServer getServer(String url, int port, String certificate);
}
