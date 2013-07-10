/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Aleksander Shterev, Carl Pfeiffer - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.observer;

import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * Observes a login of a session.
 * 
 * @author pfeifferc
 * @author shterev
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESLoginObserver extends ESObserver {

	/**
	 * To be called when login is completed.
	 * 
	 * @param session
	 *            the {@link ESUsersession} which has been logged in
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void loginCompleted(ESUsersession session);

}