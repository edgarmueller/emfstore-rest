/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Aleksander Shterev
 * Carl Pfeiffer
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.observer;

import org.eclipse.emf.emfstore.client.IUsersession;
import org.eclipse.emf.emfstore.common.IObserver;

/**
 * Observer interface for logging in.
 * 
 * @author pfeifferc
 * @author shterev
 */
public interface ESLoginObserver extends IObserver {

	/**
	 * To be called when login is completed.
	 * 
	 * @param session
	 *            the usersession which was logged in
	 */
	void loginCompleted(IUsersession session);

}