package org.eclipse.emf.emfstore.client.api;

import org.eclipse.emf.emfstore.common.model.api.IEMFStoreFactory;

public interface IServerFactory extends IEMFStoreFactory {

	IServer getServer(String name, String url, int port, String certificate);

	IServer getServer(String url, int port, String certificate);
}
