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
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.sessionprovider.ESAbstractSessionProvider;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Basic implementation of {@link ESAbstractSessionProvider}. It's intended for when using EMFStore headless. The UI
 * counterpart of this would open the login dialog, this implementation throws an exceptions and requires you to login
 * the {@link Usersession} first.
 * 
 * @author wesendon
 */
public class BasicSessionProvider extends ESAbstractSessionProvider {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.sessionprovider.ESAbstractSessionProvider#provideUsersession(org.eclipse.emf.emfstore.client.ESServer)
	 */
	@Override
	public ESUsersession provideUsersession(ESServer serverInfo) throws ESException {
		throw new ESException("No usersession found.");
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.sessionprovider.ESAbstractSessionProvider#login(org.eclipse.emf.emfstore.client.ESUsersession)
	 */
	@Override
	public void login(ESUsersession usersession) throws ESException {
		throw new ESException("Usersession not logged in. Login first.");
	}
}