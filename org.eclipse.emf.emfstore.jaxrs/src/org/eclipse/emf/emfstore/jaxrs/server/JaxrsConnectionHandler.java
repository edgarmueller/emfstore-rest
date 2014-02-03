/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Pascal - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.jaxrs.server;

import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl;
import org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * @author Pascal
 * 
 */
public class JaxrsConnectionHandler implements ConnectionHandler<EMFStore> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler#init(org.eclipse.emf.emfstore.internal.server.EMFStoreInterface,
	 *      org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl)
	 */
	public void init(EMFStore emfStore, AccessControl accessControl) throws FatalESException, ESException {
		// TODO Auto-generated method stub
		// needs to publish all services

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler#stop()
	 */
	public void stop() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler#getName()
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
