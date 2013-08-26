/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 * Edgar Mueller - extracted into own class
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication;

import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.exceptions.SessionTimedOutException;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;

/**
 * User container that additionally is capable of performing an activity check
 * of the user being hold.
 * 
 * @author wesendonk
 */
public class ACUserContainer {

	private final ACUser acUser;
	// private long firstActive;
	private long lastActive;

	/**
	 * Constructor.
	 * 
	 * @param acUser
	 *            an user instance
	 */
	public ACUserContainer(ACUser acUser) {
		this.acUser = acUser;
		// firstActive = System.currentTimeMillis();
		active();
	}

	/**
	 * Returns the user and additionally performs a activity check of the user.
	 * 
	 * @return the user
	 * @throws AccessControlException
	 *             in case the user hasn't been active
	 */
	public ACUser getUser() throws AccessControlException {
		// OW: timeout behavior does not work as expected
		checkLastActive();
		active();
		return getRawUser();
	}

	/**
	 * Returns the user instance that this container holds.
	 * 
	 * @return the user instance
	 */
	public ACUser getRawUser() {
		return acUser;
	}

	private void checkLastActive() throws AccessControlException {
		// OW finish implementing this method
		final String property = ServerConfiguration.getProperties().getProperty(
			ServerConfiguration.SESSION_TIMEOUT,
			ServerConfiguration.SESSION_TIMEOUT_DEFAULT);
		if (System.currentTimeMillis() - lastActive > Integer.parseInt(property)
		/*
		 * || System.currentTimeMillis() - firstActive >
		 * Integer.parseInt(property)
		 */) {
			// OW: delete from map
			throw new SessionTimedOutException("Usersession timed out.");
		}
	}

	private void active() {
		lastActive = System.currentTimeMillis();
	}
}