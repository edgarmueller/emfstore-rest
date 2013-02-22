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
package org.eclipse.emf.emfstore.client.provider;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;

/**
 * Represents a call to a server, i.e. a remote action that involves a server.
 * Typical server calls are share and commit.
 * 
 * @author emueller
 */
public interface ESServerCall {

	/**
	 * Returns the user session used by the server call.
	 * 
	 * @return the user session in use
	 */
	ESUsersession getUsersession();

	/**
	 * Returns the project this server call is associated with.
	 * 
	 * @return the local project associated with this server call
	 */
	ESLocalProject getLocalProject();

	/**
	 * Returns the server this server call is meant to be executed against.
	 * 
	 * @return the server this call is meant to be executed against
	 */
	ESServer getServer();

}
