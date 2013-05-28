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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Result</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getSeedCount <em>Seed Count</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getTestName <em>Test Name</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getError <em>Error</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getFailure <em>Failure</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getExecutionTime <em>Execution Time</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestResult()
 * @model
 * @generated
 */
public interface TestResult extends EObject {
	/**
	 * Returns the value of the '<em><b>Seed Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Seed Count</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Seed Count</em>' attribute.
	 * @see #setSeedCount(int)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestResult_SeedCount()
	 * @model
	 * @generated
	 */
	int getSeedCount();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getSeedCount
	 * <em>Seed Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Seed Count</em>' attribute.
	 * @see #getSeedCount()
	 * @generated
	 */
	void setSeedCount(int value);

	/**
	 * Returns the value of the '<em><b>Test Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Test Name</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Test Name</em>' attribute.
	 * @see #setTestName(String)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestResult_TestName()
	 * @model
	 * @generated
	 */
	String getTestName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getTestName
	 * <em>Test Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Test Name</em>' attribute.
	 * @see #getTestName()
	 * @generated
	 */
	void setTestName(String value);

	/**
	 * Returns the value of the '<em><b>Error</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Error</em>' attribute.
	 * @see #setError(String)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestResult_Error()
	 * @model
	 * @generated
	 */
	String getError();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getError <em>Error</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Error</em>' attribute.
	 * @see #getError()
	 * @generated
	 */
	void setError(String value);

	/**
	 * Returns the value of the '<em><b>Failure</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Failure</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Failure</em>' attribute.
	 * @see #setFailure(String)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestResult_Failure()
	 * @model
	 * @generated
	 */
	String getFailure();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getFailure <em>Failure</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Failure</em>' attribute.
	 * @see #getFailure()
	 * @generated
	 */
	void setFailure(String value);

	/**
	 * Returns the value of the '<em><b>Execution Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Execution Time</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Execution Time</em>' attribute.
	 * @see #setExecutionTime(long)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getTestResult_ExecutionTime()
	 * @model
	 * @generated
	 */
	long getExecutionTime();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getExecutionTime
	 * <em>Execution Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Execution Time</em>' attribute.
	 * @see #getExecutionTime()
	 * @generated
	 */
	void setExecutionTime(long value);

} // TestResult