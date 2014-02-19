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

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl;
import org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.jax.server.resources.Branches;
import org.eclipse.emf.emfstore.jax.server.resources.Projects;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
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
	 * get the ServiceReferences and initialise variables there
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler#init(org.eclipse.emf.emfstore.internal.server.EMFStoreInterface,
	 *      org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl)
	 */
	public void init(EMFStore emfStore, AccessControl accessControl) throws FatalESException, ESException {

		// needs to publish all services
		final BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
//		final BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
//		final BundleContext context = InternalPlatform.getDefault().getBundleContext();
//		final BundleContext context = FrameworkUtil.getBundle(EMFStoreController.class).getBundleContext();

//		final Projects projectsService = new Projects(emfStore, accessControl);
//		projectServiceRegistration = context.registerService(Projects.class, projectsService, null);
//		System.out.println("\n\n\n REGISTERED SERVICE: \t" + context.getService(projectServiceRegistration.getReference()).toString() + "\n\n\n");
		
//		ServiceReference<Projects> projectsServiceReference = context.getServiceReference(Projects.class);
//		Projects projectsService = context.getService(projectsServiceReference);
//		projectsService.setAccessControl(accessControl);
//		projectsService.setEmfStore(emfStore);
//		
//		ServiceReference<Branches> branchesServiceReference = context.getServiceReference(Branches.class);
//		Branches branchesService = context.getService(branchesServiceReference);
//		branchesService.setAccessControl(accessControl);
//		branchesService.setEmfStore(emfStore);
		
		
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler#stop()
	 */
	public void stop() {
				
		// needs to stop the services
//		projectServiceRegistration.unregister(); //TODO: uncommented because now in Activator!
		
//		System.out.println("\n\n\n UNREGISTERED SERVICE!!! \n\n\n");
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
