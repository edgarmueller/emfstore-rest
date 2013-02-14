/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.observer;

import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * Observes a logout of a session.
 * 
 * @author koegel
 */
public interface ESLogoutObserver extends ESObserver {

	/**
	 * Called to notify about the completion of a logout of the given usersession.
	 * 
	 * @param session
	 *            the usersession that was logged out
	 */
	void logoutCompleted(ESUsersession session);
}