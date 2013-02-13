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
package org.eclipse.emf.emfstore.client;

import org.eclipse.emf.emfstore.common.model.IEMFStoreFactory;

/**
 * Factory for creating {@link IServer} instances.
 * 
 * @author wesendon
 * @author emueller
 */
public interface IServerFactory extends IEMFStoreFactory {

	/**
	 * Creates a server without a local name.
	 * 
	 * @param url
	 *            the URL of the server
	 * @param port
	 *            the port of the server where EMFStore is listing on
	 * @param certificate
	 *            the certificate alias to be used
	 * 
	 * @return an {@link IServer} instance representing the remote server
	 */
	IServer getServer(String url, int port, String certificate);

	/**
	 * Creates a server with a local name.
	 * 
	 * @param name
	 *            the local name of the server
	 * @param url
	 *            the URL of the server
	 * @param port
	 *            the port of the server where EMFStore is listing on
	 * @param certificate
	 *            the certificate alias to be used
	 * 
	 * @return an {@link IServer} instance representing the remote server
	 */
	IServer getServer(String name, String url, int port, String certificate);

}
