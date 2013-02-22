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
package org.eclipse.emf.emfstore.internal.client.impl;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.sessionprovider.ESServerCall;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.common.api.InternalAPIDelegator;

/**
 * <p>
 * Mapping between {@link ESServerCall} and {@link ServerCall}.
 * </p>
 * <p>
 * Note that this class does not inherit from {@link AbstractAPIImpl} since {@link ServerCall} is not a modeled class.
 * </p>
 * 
 * @author emueller
 * 
 * @param <U> the actual return type of the call
 */
public class ESServerCallImpl<U> implements ESServerCall, InternalAPIDelegator<ESServerCall, ServerCall<U>> {

	private ServerCall<U> serverCall;

	public ESServerCallImpl(ServerCall<U> serverCall) {
		this.serverCall = serverCall;
	}

	public ESUsersession getUsersession() {

		if (serverCall.getUsersession() == null) {
			return null;
		}

		return serverCall.getUsersession().getAPIImpl();
	}

	public ESLocalProject getLocalProject() {

		if (serverCall.getProjectSpace() == null) {
			return null;
		}

		return serverCall.getProjectSpace().getAPIImpl();
	}

	public ESServer getServer() {

		if (serverCall.getServer() == null) {
			return null;
		}

		return serverCall.getServer().getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.InternalAPIDelegator#getInternalAPIImpl()
	 */
	public ServerCall<U> getInternalAPIImpl() {
		return serverCall;
	}
}
