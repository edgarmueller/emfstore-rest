/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.impl.api;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESServerFactory;
import org.eclipse.emf.emfstore.client.exceptions.ESServerStartFailedException;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreClientUtil;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;

/**
 * Implementation of a factory for creating {@link ESServer} instances.
 * 
 * @author wesendon
 * @author emueller
 */
public final class ESServerFactoryImpl implements ESServerFactory {

	/**
	 * The factory instance.
	 */
	public static final ESServerFactoryImpl INSTANCE = new ESServerFactoryImpl();
	private EMFStoreController localEMFStoreServer;

	/**
	 * Private constructor.
	 */
	private ESServerFactoryImpl() {
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESServerFactory#createServer(java.lang.String, int, java.lang.String)
	 */
	public ESServer createServer(final String url, final int port, final String certificate) {
		final ServerInfo serverInfo = EMFStoreClientUtil.createServerInfo(url, port, certificate);
		return serverInfo.toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESServerFactory#createServer(java.lang.String, java.lang.String, int,
	 *      java.lang.String)
	 */
	public ESServer createServer(String name, String url, int port,
		String certificate) {
		ServerInfo serverInfo = EMFStoreClientUtil.createServerInfo(url, port, certificate);
		serverInfo.setName(name);
		return serverInfo.toAPI();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESServerFactory#createAndStartLocalServer()
	 */
	public ESServer createAndStartLocalServer() throws ESServerStartFailedException {
		if (localEMFStoreServer == null) {
			try {
				localEMFStoreServer = EMFStoreController.runAsNewThread();
			} catch (FatalESException e) {
				throw new ESServerStartFailedException(e);
			}
		}
		ESServer server = createServer("Local Server", "localhost",
			Integer.parseInt(ServerConfiguration.XML_RPC_PORT_DEFAULT),
			KeyStoreManager.DEFAULT_CERTIFICATE);
		return server;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESServerFactory#stopLocalServer()
	 */
	public void stopLocalServer() {
		if (localEMFStoreServer != null) {
			localEMFStoreServer.stop();
			localEMFStoreServer = null;
		}
	}
}
