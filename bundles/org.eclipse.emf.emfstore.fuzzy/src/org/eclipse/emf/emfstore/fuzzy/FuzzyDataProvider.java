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
 ******************************************************************************/
package org.eclipse.emf.emfstore.fuzzy;

import java.util.List;
import java.util.Map;

import org.junit.runner.notification.RunListener;
import org.junit.runners.model.TestClass;

/**
 * A Data Provider for the JUnit Runner: {@link FuzzyRunner}.<br>
 * <br>
 * An implementation of it must have a zero-parameter constructor.
 * 
 * @author Julian Sommerfeldt
 * 
 * @param <T>
 *            Type to specify the values created by this data provider.
 */
public interface FuzzyDataProvider<T> {

	/**
	 * Creates and returns the data for the next run.<br>
	 * <br>
	 * Note that it is strongly recommended to instantiate the data in this
	 * method for every call and not in the instantiation of the class, e.g. the
	 * init method, to avoid overloading the memory.
	 * 
	 * @param count
	 *            The count of the testcase.
	 * @return The data for the next run of the test class.
	 */
	T get(int count);

	/**
	 * This method is called after the {@link FuzzyDataProvider} was created and
	 * everything was set BEFORE the first run.<br/>
	 * Should be used to to create internal stuff depending on e.g. the {@link TestClass}.
	 */
	void init();

	/**
	 * @return The total size(count) of the repetition of the tests.
	 */
	int size();

	/**
	 * @param testClass
	 *            The {@link TestClass} of the calling {@link FuzzyRunner}.
	 */
	void setTestClass(TestClass testClass);

	/**
	 * @return A list of listeners to add to the runner, e.g. to get information
	 *         about errors. <code>null</code> permitted.
	 */
	List<RunListener> getListener();

	/**
	 * @return A list of {@link Test}s to specify, which tests the {@link FuzzyRunner} should run. <code>null</code>
	 *         means run all
	 *         tests.
	 */
	List<Test> getTestsToRun();

	/**
	 * @return The {@link Util} for this {@link FuzzyDataProvider}. <code>null</code> permitted.
	 */
	Util getUtil();

	/**
	 * @param options
	 *            The options for the {@link FuzzyDataProvider}. Can be <code>null</code>.
	 */
	void setOptions(Map<String, Object> options);
}
