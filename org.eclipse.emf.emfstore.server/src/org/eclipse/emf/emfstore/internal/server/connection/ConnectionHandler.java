/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.connection;

// BEGIN IGNORE UNNECCESSARY IMPORT
// import caused by comment
import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.EMFStoreInterface;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthenticationControl;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalEmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * The ConnectionHandler makes the network transport layer transparent for the server. It requires {@link EMFStore} and
 * {@link AuthenticationControl} to delegate the messaeges.
 * 
 * @param <T> server interface type E.g {@link org.eclipse.emf.emfstore.internal.server.EMFStore} or
 *            {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}
 * @author Wesendonk
 * @author koegel
 */
public interface ConnectionHandler<T extends EMFStoreInterface> {

	/**
	 * This method initializes the ConnectionHandler.
	 * 
	 * @param emfStore an implementation of a server interface.
	 * @param accessControl an implementation of the {@link AuthenticationControl}
	 * @throws FatalEmfStoreException is thrown if the server can't initialize
	 * @throws ESException exception within the server
	 */
	void init(T emfStore, AuthenticationControl accessControl) throws FatalEmfStoreException, ESException;

	/**
	 * Stop the handler.
	 * 
	 * @param force true if handler should be stopped forcefully
	 * @generated NOT
	 */
	void stop(boolean force);

	/**
	 * Return the handler name.
	 * 
	 * @return the name of the handler
	 * @generated NOT
	 */
	String getName();
}