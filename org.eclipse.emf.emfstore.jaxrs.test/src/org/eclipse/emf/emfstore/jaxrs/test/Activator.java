package org.eclipse.emf.emfstore.jaxrs.test;
import org.eclipse.emf.emfstore.jaxrs.testOsgiConnector.ExampleService;
import org.eclipse.equinox.http.jetty.JettyConstants;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.eclipsesource.jaxrs.publisher.ServiceProperties;


public class Activator implements BundleActivator {

	private static ExampleService exampleService;

	@Override
	public void start(BundleContext context) throws Exception {
		FrameworkUtil.getBundle(JettyConstants.class).start();
		FrameworkUtil.getBundle(ServiceProperties.class).start();

		
		ServiceReference<ExampleService> reference = context.getServiceReference(ExampleService.class);
		exampleService = context.getService(reference);

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		exampleService = null;

	}

	public static ExampleService getExampleService() {
		return exampleService;
	}

}
