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
package org.eclipse.emf.emfstore.client.sessionprovider;

import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.client.IServer;
import org.eclipse.emf.emfstore.client.IUsersession;

/**
 * Represents a call to a server, i.e. a remote action that involves a server.
 * Typical server calls are share and commit.
 * 
 * @author emueller
 */
public interface IServerCall {

	/**
	 * Returns the user session used by the server call.
	 * 
	 * @return the user session in use
	 */
	IUsersession getUsersession();

	/**
	 * Returns the project this server call is associated with.
	 * 
	 * @return the local project associated with this server call
	 */
	ILocalProject getLocalProject();

	/**
	 * Returns the server this server call is meant to be executed against.
	 * 
	 * @return the server this call is meant to be executed against
	 */
	IServer getServer();

}
