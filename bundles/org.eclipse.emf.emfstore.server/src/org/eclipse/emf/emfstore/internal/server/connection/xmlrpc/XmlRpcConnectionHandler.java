/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.connection.xmlrpc;

import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl;
import org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;

/**
 * Connection Handler for XML RPC EMFStore interface.
 * 
 * @author wesendon
 */
public class XmlRpcConnectionHandler implements ConnectionHandler<EMFStore> {

	/**
	 * String interface identifier.
	 */
	public static final String EMFSTORE = "EmfStore";

	private static final String NAME = "XML RPC Connection Handler";

	private static EMFStore emfStore;

	private static AccessControl accessControl;

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler#init(org.eclipse.emf.emfstore.internal.server.EMFStoreInterface,
	 *      org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl)
	 */
	@SuppressWarnings("static-access")
	public synchronized void init(EMFStore emfStore, AccessControl accessControl) throws FatalESException {
		this.emfStore = emfStore;
		this.accessControl = accessControl;
		final XmlRpcWebserverManager webServer = XmlRpcWebserverManager.getInstance();
		webServer.initServer();
		webServer.addHandler(EMFSTORE, XmlRpcEmfStoreImpl.class);
	}

	/**
	 * Returns EMFstore.
	 * 
	 * @return the EMFStore
	 */
	public static EMFStore getEmfStore() {
		return emfStore;
	}

	/**
	 * Returns AccessControl.
	 * 
	 * @return access control
	 */
	public static AccessControl getAccessControl() {
		return accessControl;
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop(boolean force) {
		final XmlRpcWebserverManager webserverManager = XmlRpcWebserverManager.getInstance();
		if (!webserverManager.removeHandler(EMFSTORE)) {
			webserverManager.stopServer(force);
		}
	}

}
