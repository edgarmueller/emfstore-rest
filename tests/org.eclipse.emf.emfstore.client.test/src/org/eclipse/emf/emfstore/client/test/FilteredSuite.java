/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * JulianSommerfeldt
 * jsommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * {@link Suite} to filter tests and run only when runFuzzyTests system property is true.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public class FilteredSuite extends Suite {

	/**
	 * Called reflectively on classes annotated with <code>@RunWith(Suite.class)</code>.
	 * 
	 * @param klass the root class
	 * @param builder builds runners for classes in the suite
	 * @throws InitializationError if a class could not be initialized.
	 */
	public FilteredSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		super(klass, builder);
	}

	@Override
	protected List<Runner> getChildren() {
		boolean fsParameter = false;
		for (Annotation annotation : getTestClass().getAnnotations()) {
			if (annotation instanceof FilteredSuiteParameter) {
				for (String par : ((FilteredSuiteParameter) annotation).value()) {
					if (Boolean.parseBoolean(System.getProperty(par))) {
						return super.getChildren();
					}
				}
				fsParameter = true;
			}
		}

		if (fsParameter) {
			return new ArrayList<Runner>();
		} else {
			return super.getChildren();
		}
	}

	/**
	 * Annotation to configure the system parameters.
	 * 
	 * @author jsommerfeldt
	 * 
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE })
	public @interface FilteredSuiteParameter {

		/**
		 * The system parameters to enable the suite.
		 */
		String[] value();
	}
}
