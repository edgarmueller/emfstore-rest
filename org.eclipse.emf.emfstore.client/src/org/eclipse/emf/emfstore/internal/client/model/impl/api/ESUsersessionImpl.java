/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.impl.api;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESSessionIdImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESSessionId;

/**
 * Mapping between {@link ESUsersession} and {@link Usersession}.
 * 
 * @author emueller
 * 
 */
public class ESUsersessionImpl extends AbstractAPIImpl<ESUsersessionImpl, Usersession> implements ESUsersession {

	/**
	 * Constructor.
	 * 
	 * @param usersession
	 *            the delegate
	 */
	public ESUsersessionImpl(Usersession usersession) {
		super(usersession);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESUsersession#getServer()
	 */
	public ESServer getServer() {

		if (getInternalAPIImpl().getServerInfo() == null) {
			return null;
		}

		return getInternalAPIImpl().getServerInfo().getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESUsersession#getUsername()
	 */
	public String getUsername() {
		return getInternalAPIImpl().getUsername();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESUsersession#getPassword()
	 */
	public String getPassword() {
		return getInternalAPIImpl().getPassword();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESUsersession#isLoggedIn()
	 */
	public boolean isLoggedIn() {
		return getInternalAPIImpl().isLoggedIn();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESUsersession#renew()
	 */
	public void renew() throws ESException {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getInternalAPIImpl().logIn();
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESUsersession#logout()
	 */
	public void logout() throws ESException {
		RunESCommand.WithException.run(ESException.class, new Callable<Void>() {
			public Void call() throws Exception {
				getInternalAPIImpl().logout();
				return null;
			}
		});
	}

	/**
	 * Returns the session ID.
	 * 
	 * @return the session ID
	 */
	public ESSessionId getESSessionId() {
		return new ESSessionIdImpl(getInternalAPIImpl().getSessionId());
	}

	/**
	 * Sets the user's name to be used by this session when logging in.
	 * 
	 * @param name
	 *            the name of the user
	 */
	public void setUsername(final String name) {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getInternalAPIImpl().setUsername(name);
			}
		}.run(false);
	}

	/**
	 * Sets the password to be used by this session when logging in.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(final String password) {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getInternalAPIImpl().setPassword(password);
			}
		}.run(false);
	}

	/**
	 * Sets the server.
	 * 
	 * @param server
	 *            the server
	 */
	public void setServer(final ESServerImpl server) {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				if (server == null) {
					getInternalAPIImpl().setServerInfo(null);
				} else {
					getInternalAPIImpl().setServerInfo(server.getInternalAPIImpl());
				}
			}
		}.run(false);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESUsersession#getSessionId()
	 */
	public ESSessionId getSessionId() {
		return getInternalAPIImpl().getSessionId().getAPIImpl();
	}
}
