/**
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.fuzzy.emf.config;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage
 * @generated
 */
public interface ConfigFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	ConfigFactory eINSTANCE = org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigFactoryImpl
		.init();

	/**
	 * Returns a new object of class '<em>Test Config</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Test Config</em>'.
	 * @generated
	 */
	TestConfig createTestConfig();

	/**
	 * Returns a new object of class '<em>Test Run</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Test Run</em>'.
	 * @generated
	 */
	TestRun createTestRun();

	/**
	 * Returns a new object of class '<em>Test Result</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Test Result</em>'.
	 * @generated
	 */
	TestResult createTestResult();

	/**
	 * Returns a new object of class '<em>Test Diff</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Test Diff</em>'.
	 * @generated
	 */
	TestDiff createTestDiff();

	/**
	 * Returns a new object of class '<em>Diff Report</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Diff Report</em>'.
	 * @generated
	 */
	DiffReport createDiffReport();

	/**
	 * Returns a new object of class '<em>Root</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Root</em>'.
	 * @generated
	 */
	Root createRoot();

	/**
	 * Returns a new object of class '<em>Mutator Config</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Mutator Config</em>'.
	 * @generated
	 */
	MutatorConfig createMutatorConfig();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	ConfigPackage getConfigPackage();

} // ConfigFactory