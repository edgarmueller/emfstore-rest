/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Aleksander Shterev
 * Carl Pfeiffer
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.observer;

import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * Observer interface for logging in.
 * 
 * @author pfeifferc
 * @author shterev
 */
public interface ESLoginObserver extends ESObserver {

	/**
	 * To be called when login is completed.
	 * 
	 * @param session
	 *            the usersession which was logged in
	 */
	void loginCompleted(ESUsersession session);

}
