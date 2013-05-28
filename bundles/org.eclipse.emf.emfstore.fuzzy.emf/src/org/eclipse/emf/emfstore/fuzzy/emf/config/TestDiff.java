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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Diff</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getLastUpdate <em>Last Update</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getConfig <em>Config</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getOldResult <em>Old Result</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getNewResult <em>New Result</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestDiff()
 * @model
 * @generated
 */
public interface TestDiff extends EObject {
	/**
	 * Returns the value of the '<em><b>Last Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Update</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Last Update</em>' attribute.
	 * @see #setLastUpdate(Date)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestDiff_LastUpdate()
	 * @model
	 * @generated
	 */
	Date getLastUpdate();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getLastUpdate
	 * <em>Last Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Last Update</em>' attribute.
	 * @see #getLastUpdate()
	 * @generated
	 */
	void setLastUpdate(Date value);

	/**
	 * Returns the value of the '<em><b>Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Config</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Config</em>' reference.
	 * @see #setConfig(TestConfig)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestDiff_Config()
	 * @model
	 * @generated
	 */
	TestConfig getConfig();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getConfig <em>Config</em>}'
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Config</em>' reference.
	 * @see #getConfig()
	 * @generated
	 */
	void setConfig(TestConfig value);

	/**
	 * Returns the value of the '<em><b>Old Result</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Result</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Old Result</em>' containment reference.
	 * @see #setOldResult(TestResult)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestDiff_OldResult()
	 * @model containment="true"
	 * @generated
	 */
	TestResult getOldResult();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getOldResult
	 * <em>Old Result</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Old Result</em>' containment reference.
	 * @see #getOldResult()
	 * @generated
	 */
	void setOldResult(TestResult value);

	/**
	 * Returns the value of the '<em><b>New Result</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Result</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>New Result</em>' containment reference.
	 * @see #setNewResult(TestResult)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestDiff_NewResult()
	 * @model containment="true"
	 * @generated
	 */
	TestResult getNewResult();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getNewResult
	 * <em>New Result</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>New Result</em>' containment reference.
	 * @see #getNewResult()
	 * @generated
	 */
	void setNewResult(TestResult value);

} // TestDiff