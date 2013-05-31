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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.emfstore.fuzzy.Annotations.Data;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * A {@link org.junit.runner.Runner} for each {@link org.junit.runners.model.TestClass}.
 * Used in the {@link FuzzyRunner}.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public class FuzzyTestClassRunner extends BlockJUnit4ClassRunner {

	/**
	 * Which run is it?
	 */
	private int counter;

	/**
	 * The {@link FuzzyDataProvider}, which "contains" the data for the test.
	 */
	private FuzzyDataProvider<?> dataProvider;

	/**
	 * The {@link FrameworkField} of the {@link TestClass} where to put in the data.
	 */
	private FrameworkField dataField;

	/**
	 * The {@link FrameworkField} for the {@link Util}.
	 */
	private FrameworkField utilField;

	private Util util;

	private FrameworkField optionsField;

	/**
	 * Constructor.
	 * 
	 * @param type The testclass
	 * @param dataProvider The {@link FuzzyDataProvider} providing the data to put into the dataField
	 * @param dataField The datafield in the testclass
	 * @param utilField the utilfield in the testclass
	 * @param optionsField the options field in the testclass
	 * @param util The {@link Util} class
	 * @param counter The counter of the run
	 * @throws InitializationError If there was a problem during the initialization of the test
	 */
	FuzzyTestClassRunner(Class<?> type, FuzzyDataProvider<?> dataProvider, FrameworkField dataField,
		FrameworkField utilField, FrameworkField optionsField, Util util, int counter) throws InitializationError {
		super(type);
		this.counter = counter;
		this.dataField = dataField;
		this.utilField = utilField;
		this.optionsField = optionsField;
		this.util = util;
		this.dataProvider = dataProvider;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object createTest() {
		try {
			// create a new instance of the testclass
			Object testInstance = getTestClass().getOnlyConstructor().newInstance();

			// set the options to dataprovider
			if (optionsField != null) {
				Object options = getValueFromField(optionsField.getField(), testInstance);
				if (options == null) {
					throw new IllegalStateException(
						"The options field has to be not null! Fill it or remove annotation.");
				}
				try {
					dataProvider.setOptions((Map<String, Object>) options);
				} catch (ClassCastException e) {
					throw new ClassCastException("The options field is not of type: Map<String, Object>!");
				}
			}

			// get the new data from dataprovider
			Object data = dataProvider.get(counter);

			// set the data to the datafield
			setValueToField(dataField.getField(), testInstance, data,
				"The field annotated with " + Data.class.getSimpleName()
					+ " does not fit to the type of the dataprovider (" + dataProvider.getClass() + ").");

			// set the util to the util field
			if (util != null && utilField != null) {
				setValueToField(utilField.getField(), testInstance, util,
					"The field annotated " + Util.class.getSimpleName() + " does not fit to the Util type!");
			}

			return testInstance;		
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private Object getValueFromField(Field field, Object instance) throws IllegalAccessException {
		try{
			field.setAccessible(true);
			Object o = field.get(instance);
			return o;
		} finally {
			field.setAccessible(false);
		}
	}

	private void setValueToField(Field field, Object instance, Object value, String errorMsg) throws IllegalAccessException
		 {
		try {
			field.setAccessible(true);
			field.set(instance, value);
		} finally {
			field.setAccessible(false);
		}
	}

	@Override
	protected List<FrameworkMethod> getChildren() {
		List<Test> testsToRun = dataProvider.getTestsToRun();
		List<FrameworkMethod> allChildren = super.getChildren();

		// check if it should filter tests
		if (testsToRun != null) {
			List<FrameworkMethod> filteredChildren = new ArrayList<FrameworkMethod>();
			for (Test test : testsToRun) {
				String name = test.getName();
				int seedCount = test.getSeedCount();
				for (FrameworkMethod child : allChildren) {
					if (seedCount == counter && name.equals(child.getName())) {
						filteredChildren.add(child);
					}
				}
			}
			return filteredChildren;
		}

		// if not return all children
		return allChildren;
	}

	private String testName(String name) {
		return String.format("%s%s[%s]", name, FuzzyRunner.NAME_SEPARATOR, counter);
	}

	@Override
	protected String testName(final FrameworkMethod method) {
		return testName(method.getName());
	}

	@Override
	protected String getName() {
		return String.format("%s%s[%s]", getTestClass().getName(), FuzzyRunner.NAME_SEPARATOR, counter);
	}

	@Override
	protected Statement classBlock(RunNotifier notifier) {
		return childrenInvoker(notifier);
	}
}
