/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client;

import org.eclipse.emf.ecore.xmi.DanglingHREFException;
import org.eclipse.emf.emfstore.client.exceptions.ESServerStartFailedException;
import org.eclipse.emf.emfstore.common.model.ESFactory;

/**
 * Factory for creating {@link ESServer} instances.
 * 
 * @author wesendon
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESServerFactory extends ESFactory {

	/**
	 * Creates a server without a local name.
	 * The created server is not automatically added to the workspace, i.e.
	 * most users will call {@link org.eclipse.emf.emfstore.client.ESWorkspace#addServer(ESServer)} in order to
	 * to avoid {@link DanglingHREFException}s upon saving.
	 * 
	 * @param url
	 *            the URL of the server
	 * @param port
	 *            the port of the server where EMFStore is listing on
	 * @param certificate
	 *            the certificate alias to be used
	 * 
	 * @return an {@link ESServer} instance representing the remote server
	 * 
	 * @see {@link org.eclipse.emf.emfstore.client.ESWorkspace#addServer(ESServer)}
	 */
	ESServer createServer(String url, int port, String certificate);

	/**
	 * Creates a server with a local name.
	 * The created server is not automatically added to the workspace, i.e.
	 * most users will call {@link org.eclipse.emf.emfstore.client.ESWorkspace#addServer(ESServer)} in order to
	 * to avoid {@link DanglingHREFException}s upon saving.
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
	 * @return an {@link ESServer} instance representing the remote server
	 * 
	 * @see {@link org.eclipse.emf.emfstore.client.ESWorkspace#addServer(ESServer)}
	 */
	ESServer createServer(String name, String url, int port, String certificate);

	/**
	 * Creates a server with a local name and launches a server process locally. Blocks until server is fully running.
	 * Only one local server can be started at any point in time. Calling this method with a serve already running will
	 * not launch another server, but just return a new ESServer.
	 * The created server is not automatically added to the workspace, i.e.
	 * most users will call {@link org.eclipse.emf.emfstore.client.ESWorkspace#addServer(ESServer)} in order to
	 * to avoid {@link DanglingHREFException}s upon saving.
	 * 
	 * @return an {@link ESServer} instance representing the local server on the client side.
	 * @throws ESServerStartFailedException if starting the server fails
	 */
	ESServer createAndStartLocalServer() throws ESServerStartFailedException;

	/**
	 * Stop the local server iff it has been started. Blocks until server has stopped fully.
	 */
	void stopLocalServer();
}
