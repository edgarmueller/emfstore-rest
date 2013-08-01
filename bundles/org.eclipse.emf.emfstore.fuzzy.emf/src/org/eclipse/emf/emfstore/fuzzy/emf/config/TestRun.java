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

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Test Run</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getConfig <em> Config</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getTime <em>Time </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getResults <em> Results</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestRun()
 * @model
 * @generated
 */
public interface TestRun extends EObject {
	/**
	 * Returns the value of the '<em><b>Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Config</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Config</em>' containment reference.
	 * @see #setConfig(TestConfig)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestRun_Config()
	 * @model containment="true"
	 * @generated
	 */
	TestConfig getConfig();

	/**
	 * Sets the value of the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getConfig
	 * <em>Config</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Config</em>' containment reference.
	 * @see #getConfig()
	 * @generated
	 */
	void setConfig(TestConfig value);

	/**
	 * Returns the value of the '<em><b>Time</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Time</em>' attribute.
	 * @see #setTime(Date)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestRun_Time()
	 * @model
	 * @generated
	 */
	Date getTime();

	/**
	 * Sets the value of the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getTime
	 * <em>Time</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(Date value);

	/**
	 * Returns the value of the '<em><b>Results</b></em>' containment reference
	 * list. The list contents are of type {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Results</em>' containment reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Results</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestRun_Results()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestResult> getResults();

} // TestRun