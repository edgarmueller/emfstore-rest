package org.eclipse.emf.emfstore.client.test.alltests;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class DynamicSuite extends Suite {

	/**
	 * @param klass
	 * @param suiteClasses
	 * @throws InitializationError
	 */
	public DynamicSuite(Class<?> klass) throws InitializationError {
		super(klass, getTests());
	}

	private static Class[] getTests() throws InitializationError {

		final Class[] tests = new Class[1];
		try {
			tests[0] = Class.forName("org.eclipse.emf.emfstore.client.changetracking.test.AllChangeTrackingTests");
		} catch (final ClassNotFoundException ex) {
			throw new InitializationError(ex);
		}

		return tests;
	}

}