/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.fuzzy.emf;

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

	private static final String RUN_FUZZY_TESTS = "runFuzzyTests";
	
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
		if(Boolean.parseBoolean(System.getProperty(RUN_FUZZY_TESTS))){
			return super.getChildren();
		}
		return new ArrayList<Runner>();
	}
}
