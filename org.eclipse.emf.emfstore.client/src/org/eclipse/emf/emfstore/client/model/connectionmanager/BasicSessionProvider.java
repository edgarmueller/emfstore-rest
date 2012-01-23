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
package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

/**
 * Basic implementation of {@link AbstractSessionProvider}. It's intended for when using EMFStore headless. The UI
 * counterpart of this would open the login dialog, this implementation throws an exceptions and requires you to login
 * the {@link Usersession} first.
 * 
 * @author wesendon
 */
public class BasicSessionProvider extends AbstractSessionProvider {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.connectionmanager.AbstractSessionProvider#provideUsersession(org.eclipse.emf.emfstore.client.model.ServerInfo)
	 */
	@Override
	public Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException {
		throw new EmfStoreException("No usersession found.");
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.connectionmanager.AbstractSessionProvider#login(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	@Override
	public void login(Usersession usersession) throws EmfStoreException {
		throw new EmfStoreException("Usersession not logged in. Login first.");
	}
}
