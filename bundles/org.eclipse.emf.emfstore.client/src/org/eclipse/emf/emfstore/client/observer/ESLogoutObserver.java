/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.observer;

import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * Observes a logout of a session.
 * 
 * @author koegel
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESLogoutObserver extends ESObserver {

	/**
	 * Called to notify about the completion of a logout of the given usersession.
	 * 
	 * @param session
	 *            the {@link ESUsersession} that was logged out
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void logoutCompleted(ESUsersession session);
}