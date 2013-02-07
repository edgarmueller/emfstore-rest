package org.eclipse.emf.emfstore.internal.client.impl;

import org.eclipse.emf.emfstore.client.api.IServer;
import org.eclipse.emf.emfstore.client.api.IServerFactory;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreClientUtil;

public class ServerFactoryImpl implements IServerFactory {

	public static final ServerFactoryImpl INSTANCE = new ServerFactoryImpl();

	private ServerFactoryImpl() {
	}

	public IServer getServer(String url, int port, String certificate) {
		return EMFStoreClientUtil.createServerInfo(url, port, certificate);
	}

	public IServer getServer(String name, String url, int port,
			String certificate) {
		ServerInfo serverInfo = EMFStoreClientUtil.createServerInfo(url, port,
				certificate);
		serverInfo.setName(name);
		return serverInfo;
	}
}
