/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Pascal - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.jax.server;

import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl;
import org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.jax.server.resources.Projects;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Pascal
 * 
 */
public class JaxrsConnectionHandler implements ConnectionHandler<EMFStore> {

	private ServiceRegistration<?> projectServiceRegistration;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler#init(org.eclipse.emf.emfstore.internal.server.EMFStoreInterface,
	 *      org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl)
	 */
	public void init(EMFStore emfStore, AccessControl accessControl) throws FatalESException, ESException {

		// needs to publish all services
		final BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();

		final Projects projectsService = new Projects(emfStore, accessControl);
		projectServiceRegistration = context.registerService(Projects.class, projectsService, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler#stop()
	 */
	public void stop() {

		// needs to stop the services
		projectServiceRegistration.unregister();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler#getName()
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
