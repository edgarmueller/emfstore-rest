package org.eclipse.emf.emfstore.jaxrs.test;
import org.eclipse.emf.emfstore.jaxrs.testOsgiConnector.ExampleService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


public class Activator implements BundleActivator {

	private static ExampleService exampleService;

	@Override
	public void start(BundleContext context) throws Exception {
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
