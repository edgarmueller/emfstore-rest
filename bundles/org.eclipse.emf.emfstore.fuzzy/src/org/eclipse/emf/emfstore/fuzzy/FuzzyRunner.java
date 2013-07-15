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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.emfstore.fuzzy.Annotations.Data;
import org.eclipse.emf.emfstore.fuzzy.Annotations.DataProvider;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Options;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Util;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.InitializationError;

/**
 * A {@link Runner} for JUnit, to realize multiple runs with different values
 * for a data field. <br/>
 * <br/>
 * Activate with the {@link org.junit.runner.RunWith} annotation:
 * <code>@RunWith(FuzzyRunner.class)</code>. <br/>
 * <br/>
 * The test class must have a field, which is not static and annotated with
 * {@link Data}, e.g.<br/>
 * <br/>
 * <code>@Data<br/>private Integer i;</code> <br/>
 * <br/>
 * To provide data an implementation of {@link FuzzyDataProvider} can be set via
 * the {@link DataProvider} annotation, e.g.<br/>
 * <br/>
 * <code>@DataProvider(IntDataProvider.class)</code><br/>
 * <br/>
 * This class must implement the interface {@link FuzzyDataProvider}. The
 * default value is the example implementation: {@link IntDataProvider}.<br/>
 * <br/>
 * The {@link MyTest} class illustrates an example usage of the
 * {@link FuzzyRunner}.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public class FuzzyRunner extends Suite {

	private ArrayList<Runner> runners = new ArrayList<Runner>();

	private FuzzyDataProvider<?> dataProvider;

	/**
	 * The string representing a seperation in a name (e.g. test name).
	 */
	public static final String NAME_SEPARATOR = " ";

	/**
	 * Default constructor, called by JUnit.
	 * 
	 * @param clazz
	 *            The root class of the suite.
	 * @throws InitializationError
	 *             If there
	 */
	public FuzzyRunner(Class<?> clazz) throws InitializationError {
		super(clazz, Collections.<Runner> emptyList());
		dataProvider = getDataProvider();
		dataProvider.setTestClass(getTestClass());
		dataProvider.init();
		FrameworkField dataField = getDataField();
		FrameworkField utilField = getUtilField();
		FrameworkField optionsField = getOptionsField();
		org.eclipse.emf.emfstore.fuzzy.Util util = dataProvider.getUtil();
		for (int i = 0; i < dataProvider.size(); i++) {
			FuzzyTestClassRunner runner = new FuzzyTestClassRunner(clazz,
					dataProvider, dataField, utilField, optionsField, util,
					i + 1);
			if (runner.getChildren().size() > 0) {
				runners.add(runner);
			}
		}
	}

	/*
	 * Override to add RunListeners of the FuzzyDataProvider (non-Javadoc)
	 * 
	 * @see
	 * org.junit.runners.ParentRunner#run(org.junit.runner.notification.RunNotifier
	 * )
	 */
	@Override
	public void run(final RunNotifier notifier) {
		List<RunListener> listener = dataProvider.getListener();
		if (listener != null) {
			for (RunListener runListener : listener) {
				notifier.addListener(runListener);
			}
		}
		super.run(notifier);
	}

	/**
	 * @return The field annotated with {@link Util}.
	 * @throws Exception
	 *             If there is are more than one fitting fields.
	 */
	private FrameworkField getUtilField() {
		return getSingleStaticFrameworkField(Util.class);
	}

	private FrameworkField getOptionsField() {
		return getSingleStaticFrameworkField(Options.class);
	}

	private FrameworkField getSingleStaticFrameworkField(
			Class<? extends Annotation> annotation) {
		List<FrameworkField> fields = getTestClass().getAnnotatedFields(
				annotation);

		// Check if there are more than one Data field in the class
		if (fields.size() > 1) {
			throw new RuntimeException("Only one field annotated with "
					+ annotation.getSimpleName() + " permitted: "
					+ getTestClass().getName() + " contains " + fields.size());
		}

		// get the field and check modifiers
		for (FrameworkField field : fields) {
			int modifiers = field.getField().getModifiers();
			if (!Modifier.isStatic(modifiers)) {
				return field;
			}
		}

		return null;
	}

	/**
	 * @return The field annotated with {@link Data}.
	 * @throws InitializationError
	 * @throws Exception
	 *             If there is not exact one fitting field.
	 */
	private FrameworkField getDataField() throws InitializationError {
		FrameworkField field = getSingleStaticFrameworkField(Data.class);

		if (field == null) {
			throw new InitializationError(
					"No non-static model field anntoted with "
							+ Data.class.getSimpleName() + " in class "
							+ getTestClass().getName());
		}

		return field;
	}

	/**
	 * @return The {@link FuzzyDataProvider} defined by the {@link DataProvider}
	 *         annotation or the default one.
	 * @throws InitializationError
	 * @throws Exception
	 *             If the data provider does not implement the
	 *             {@link FuzzyDataProvider} interface.
	 */
	private FuzzyDataProvider<?> getDataProvider() throws InitializationError {
		// Get the DataProvider Annotation
		Annotation[] annotations = getTestClass().getAnnotations();

		// take default DataProvider, if there is no annotation
		Class<?> dataProviderClass = null;

		// check for the dataprovider annotation
		for (Annotation annotation : annotations) {
			if (annotation instanceof DataProvider) {

				// Check if the given class is an implementation of
				// FuzzyDataProvider
				dataProviderClass = ((DataProvider) annotation).value();
				if (!FuzzyDataProvider.class
						.isAssignableFrom(dataProviderClass)) {
					throw new InitializationError(dataProviderClass
							+ " is not an implementation of "
							+ FuzzyDataProvider.class.getSimpleName());
				}
			}
		}

		// create a new instance of the DataProvider
		try {
			return (FuzzyDataProvider<?>) dataProviderClass.getConstructor()
					.newInstance();
		} catch (InstantiationException e) {
			throw new InitializationError(
					"The DataProvider must have a zero-parameter constructor!");
		} catch (IllegalAccessException e) {
			throw new InitializationError(
					"The DataProvider must have a zero-parameter constructor!");
		} catch (IllegalArgumentException e) {
			throw new InitializationError(
					"The DataProvider must have a zero-parameter constructor!");
		} catch (InvocationTargetException e) {
			throw new InitializationError(
					"The DataProvider must have a zero-parameter constructor!");
		} catch (NoSuchMethodException e) {
			throw new InitializationError(
					"The DataProvider must have a zero-parameter constructor!");
		} catch (SecurityException e) {
			throw new InitializationError(
					"The DataProvider must have a zero-parameter constructor!");
		}
	}

	@Override
	protected List<Runner> getChildren() {
		return runners;
	}
}
