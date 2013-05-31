/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * JulianSommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.fuzzy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations used in tests using the {@link FuzzyRunner}.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public final class Annotations {

	/**
	 * Annotation to declare the field in the test, where to set the data.
	 * 
	 * @author Julian Sommerfeldt
	 * 
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public @interface Data {
	}

	/**
	 * Annotations to declare the field for the {@link Util}.
	 * 
	 * @author Julian Sommerfeldt
	 * 
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public @interface Util {
	}

	/**
	 * An annotation to set the {@link FuzzyDataProvider} for the {@link FuzzyRunner}.
	 * 
	 * @author Julian Sommerfeldt
	 * 
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE })
	public @interface DataProvider {

		/***/
		Class<?> value();
	}

	/**
	 * An optional annotation to declare options to use in the {@link FuzzyDataProvider}.
	 * 
	 * @author Julian Sommerfeldt
	 * 
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public @interface Options {
	}
}
