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
package org.eclipse.emf.emfstore.internal.client.model.observers;

import org.eclipse.emf.emfstore.client.IUsersession;
import org.eclipse.emf.emfstore.common.IObserver;

/**
 * Observes a logout of a session.
 * 
 * @author koegel
 */
public interface LogoutObserver extends IObserver {

	/**
	 * Called to notify about the completion of a logout of the given usersession.
	 * 
	 * @param session
	 *            the usersession that was logged out
	 */
	void logoutCompleted(IUsersession session);
}