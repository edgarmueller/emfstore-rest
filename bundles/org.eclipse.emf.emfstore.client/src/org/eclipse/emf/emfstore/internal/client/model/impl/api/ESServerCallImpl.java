/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.impl.api;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.sessionprovider.ESServerCall;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.common.api.InternalAPIDelegator;

/**
 * <p>
 * Mapping between {@link ESServerCall} and {@link ServerCall}.
 * </p>
 * <p>
 * Note that this class does not inherit from {@link org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl} since
 * {@link ServerCall} is not a modeled class.
 * </p>
 * 
 * @author emueller
 * 
 * @param <U> the actual return type of the call
 */
public class ESServerCallImpl<U> implements ESServerCall, InternalAPIDelegator<ESServerCall, ServerCall<U>> {

	private final ServerCall<U> serverCall;

	/**
	 * Constructor.
	 * 
	 * @param serverCall
	 *            the server call to be wrapped
	 */
	public ESServerCallImpl(ServerCall<U> serverCall) {
		this.serverCall = serverCall;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.sessionprovider.ESServerCall#getUsersession()
	 */
	public ESUsersession getUsersession() {

		if (serverCall.getUsersession() == null) {
			return null;
		}

		return serverCall.getUsersession().toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.sessionprovider.ESServerCall#getLocalProject()
	 */
	public ESLocalProject getLocalProject() {

		if (serverCall.getProjectSpace() == null) {
			return null;
		}

		return serverCall.getProjectSpace().toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.sessionprovider.ESServerCall#getServer()
	 */
	public ESServer getServer() {

		if (serverCall.getServer() == null) {
			return null;
		}

		return serverCall.getServer().toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.InternalAPIDelegator#toInternalAPI()
	 */
	public ServerCall<U> toInternalAPI() {
		return serverCall;
	}
}
