/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.impl.api;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESServerFactory;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreClientUtil;

/**
 * Implementation of a factory for creating {@link ESServer} instances.
 * 
 * @author wesendon
 * @author emueller
 */
public final class ServerFactoryImpl implements ESServerFactory {

	/**
	 * The factory instance.
	 */
	public static final ServerFactoryImpl INSTANCE = new ServerFactoryImpl();

	/**
	 * Private constructor.
	 */
	private ServerFactoryImpl() {
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESServerFactory#getServer(java.lang.String, int, java.lang.String)
	 */
	public ESServer getServer(final String url, final int port, final String certificate) {

		final ServerInfo serverInfo = EMFStoreClientUtil.createServerInfo(url, port, certificate);
		final ESServerImpl server = serverInfo.getAPIImpl();

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				ESWorkspaceProvider.INSTANCE.getWorkspace().addServer(server);
				return null;
			}
		});

		return server;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESServerFactory#getServer(java.lang.String, java.lang.String, int,
	 *      java.lang.String)
	 */
	public ESServer getServer(String name, String url, int port,
		String certificate) {
		ServerInfo serverInfo = EMFStoreClientUtil.createServerInfo(url, port, certificate);
		serverInfo.setName(name);
		return serverInfo.getAPIImpl();
	}
}
