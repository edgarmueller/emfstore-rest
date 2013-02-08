package org.eclipse.emf.emfstore.client;

import org.eclipse.emf.emfstore.common.model.IEMFStoreFactory;

public interface IServerFactory extends IEMFStoreFactory {

	IServer getServer(String url, int port, String certificate);

	IServer getServer(String name, String url, int port, String certificate);

}
