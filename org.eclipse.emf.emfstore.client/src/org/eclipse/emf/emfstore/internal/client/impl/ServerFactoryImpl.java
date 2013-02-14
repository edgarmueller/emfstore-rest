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
package org.eclipse.emf.emfstore.internal.client.impl;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESServerFactory;
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
	public ESServer getServer(String url, int port, String certificate) {
		return EMFStoreClientUtil.createServerInfo(url, port, certificate);
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
		return serverInfo;
	}
}
