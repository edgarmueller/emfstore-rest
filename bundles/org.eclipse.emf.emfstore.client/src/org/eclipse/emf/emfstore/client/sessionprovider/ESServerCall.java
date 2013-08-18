/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.sessionprovider;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;

/**
 * Represents a call to a server, i.e. a remote action that involves a server.
 * Typical server calls are, for instance, share and commit.
 * 
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESServerCall {

	/**
	 * Returns the user session used by the server call.
	 * 
	 * @return the user session in use
	 */
	ESUsersession getUsersession();

	/**
	 * Returns the {@link ESLocalProject} this server call is associated with.
	 * 
	 * @return the local project associated with this server call
	 */
	ESLocalProject getLocalProject();

	/**
	 * Returns the {@link ESServer} this server call is meant to be executed against.
	 * 
	 * @return the server this call is meant to be executed against
	 */
	ESServer getServer();

}
