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
package org.eclipse.emf.emfstore.fuzzy.emf.config.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Result</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestResultImpl#getSeedCount <em>Seed Count</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestResultImpl#getTestName <em>Test Name</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestResultImpl#getError <em>Error</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestResultImpl#getFailure <em>Failure</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestResultImpl#getExecutionTime <em>Execution Time</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TestResultImpl extends EObjectImpl implements TestResult {
	/**
	 * The default value of the '{@link #getSeedCount() <em>Seed Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSeedCount()
	 * @generated
	 * @ordered
	 */
	protected static final int SEED_COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSeedCount() <em>Seed Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSeedCount()
	 * @generated
	 * @ordered
	 */
	protected int seedCount = SEED_COUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTestName() <em>Test Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTestName()
	 * @generated
	 * @ordered
	 */
	protected static final String TEST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTestName() <em>Test Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTestName()
	 * @generated
	 * @ordered
	 */
	protected String testName = TEST_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getError() <em>Error</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getError()
	 * @generated
	 * @ordered
	 */
	protected static final String ERROR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getError() <em>Error</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getError()
	 * @generated
	 * @ordered
	 */
	protected String error = ERROR_EDEFAULT;

	/**
	 * The default value of the '{@link #getFailure() <em>Failure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFailure()
	 * @generated
	 * @ordered
	 */
	protected static final String FAILURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFailure() <em>Failure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFailure()
	 * @generated
	 * @ordered
	 */
	protected String failure = FAILURE_EDEFAULT;

	/**
	 * The default value of the '{@link #getExecutionTime() <em>Execution Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getExecutionTime()
	 * @generated
	 * @ordered
	 */
	protected static final long EXECUTION_TIME_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getExecutionTime() <em>Execution Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getExecutionTime()
	 * @generated
	 * @ordered
	 */
	protected long executionTime = EXECUTION_TIME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TestResultImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.TEST_RESULT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getSeedCount() {
		return seedCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSeedCount(int newSeedCount) {
		int oldSeedCount = seedCount;
		seedCount = newSeedCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.TEST_RESULT__SEED_COUNT, oldSeedCount,
				seedCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getTestName() {
		return testName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTestName(String newTestName) {
		String oldTestName = testName;
		testName = newTestName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.TEST_RESULT__TEST_NAME, oldTestName,
				testName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getError() {
		return error;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setError(String newError) {
		String oldError = error;
		error = newError;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.TEST_RESULT__ERROR, oldError, error));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getFailure() {
		return failure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFailure(String newFailure) {
		String oldFailure = failure;
		failure = newFailure;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.TEST_RESULT__FAILURE, oldFailure,
				failure));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public long getExecutionTime() {
		return executionTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setExecutionTime(long newExecutionTime) {
		long oldExecutionTime = executionTime;
		executionTime = newExecutionTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.TEST_RESULT__EXECUTION_TIME,
				oldExecutionTime, executionTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ConfigPackage.TEST_RESULT__SEED_COUNT:
			return getSeedCount();
		case ConfigPackage.TEST_RESULT__TEST_NAME:
			return getTestName();
		case ConfigPackage.TEST_RESULT__ERROR:
			return getError();
		case ConfigPackage.TEST_RESULT__FAILURE:
			return getFailure();
		case ConfigPackage.TEST_RESULT__EXECUTION_TIME:
			return getExecutionTime();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ConfigPackage.TEST_RESULT__SEED_COUNT:
			setSeedCount((Integer) newValue);
			return;
		case ConfigPackage.TEST_RESULT__TEST_NAME:
			setTestName((String) newValue);
			return;
		case ConfigPackage.TEST_RESULT__ERROR:
			setError((String) newValue);
			return;
		case ConfigPackage.TEST_RESULT__FAILURE:
			setFailure((String) newValue);
			return;
		case ConfigPackage.TEST_RESULT__EXECUTION_TIME:
			setExecutionTime((Long) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ConfigPackage.TEST_RESULT__SEED_COUNT:
			setSeedCount(SEED_COUNT_EDEFAULT);
			return;
		case ConfigPackage.TEST_RESULT__TEST_NAME:
			setTestName(TEST_NAME_EDEFAULT);
			return;
		case ConfigPackage.TEST_RESULT__ERROR:
			setError(ERROR_EDEFAULT);
			return;
		case ConfigPackage.TEST_RESULT__FAILURE:
			setFailure(FAILURE_EDEFAULT);
			return;
		case ConfigPackage.TEST_RESULT__EXECUTION_TIME:
			setExecutionTime(EXECUTION_TIME_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ConfigPackage.TEST_RESULT__SEED_COUNT:
			return seedCount != SEED_COUNT_EDEFAULT;
		case ConfigPackage.TEST_RESULT__TEST_NAME:
			return TEST_NAME_EDEFAULT == null ? testName != null : !TEST_NAME_EDEFAULT.equals(testName);
		case ConfigPackage.TEST_RESULT__ERROR:
			return ERROR_EDEFAULT == null ? error != null : !ERROR_EDEFAULT.equals(error);
		case ConfigPackage.TEST_RESULT__FAILURE:
			return FAILURE_EDEFAULT == null ? failure != null : !FAILURE_EDEFAULT.equals(failure);
		case ConfigPackage.TEST_RESULT__EXECUTION_TIME:
			return executionTime != EXECUTION_TIME_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (seedCount: ");
		result.append(seedCount);
		result.append(", testName: ");
		result.append(testName);
		result.append(", error: ");
		result.append(error);
		result.append(", failure: ");
		result.append(failure);
		result.append(", executionTime: ");
		result.append(executionTime);
		result.append(')');
		return result.toString();
	}

} // TestResultImpl