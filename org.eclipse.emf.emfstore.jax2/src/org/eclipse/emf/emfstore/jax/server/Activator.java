package org.eclipse.emf.emfstore.jax.server;

import org.eclipse.emf.emfstore.jax.server.resources.Branches;
import org.eclipse.emf.emfstore.jax.server.resources.Projects;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	

	private ServiceRegistration<?> projectServiceRegistration;
	private ServiceRegistration<?> branchesServiceRegistration;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		
		//Thread.sleep(10000);
		
		ServiceReference<Projects> projectsServiceReference = context.getServiceReference(Projects.class);
		
		if(projectsServiceReference == null) {
			final Projects projectsService = new Projects();
			 projectServiceRegistration = context.registerService(Projects.class,
			 projectsService, null);
		}
		
		ServiceReference<Branches> branchesServiceReference = context.getServiceReference(Branches.class);
		
		if(branchesServiceReference == null) {
			final Branches branchesService = new Branches();
			branchesServiceRegistration = context.registerService(Branches.class,
					branchesService, null);
		}
		
		
		
		 

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {

		projectServiceRegistration.unregister();
		branchesServiceRegistration.unregister();
		
	}

}