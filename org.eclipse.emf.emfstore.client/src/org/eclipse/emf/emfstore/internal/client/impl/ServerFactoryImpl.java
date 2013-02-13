package org.eclipse.emf.emfstore.internal.client.impl;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.IServerFactory;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreClientUtil;

public class ServerFactoryImpl implements IServerFactory {

	public static final ServerFactoryImpl INSTANCE = new ServerFactoryImpl();

	private ServerFactoryImpl() {
	}

	public ESServer getServer(String url, int port, String certificate) {
		return EMFStoreClientUtil.createServerInfo(url, port, certificate);
	}

	public ESServer getServer(String name, String url, int port,
			String certificate) {
		ServerInfo serverInfo = EMFStoreClientUtil.createServerInfo(url, port,
				certificate);
		serverInfo.setName(name);
		return serverInfo;
	}
}
