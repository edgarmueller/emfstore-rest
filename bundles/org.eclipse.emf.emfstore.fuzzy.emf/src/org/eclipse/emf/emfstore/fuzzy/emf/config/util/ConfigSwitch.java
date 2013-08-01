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
package org.eclipse.emf.emfstore.fuzzy.emf.config.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage;
import org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport;
import org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.Root;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method
 * for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage
 * @generated
 */
public class ConfigSwitch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static ConfigPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public ConfigSwitch() {
		if (modelPackage == null) {
			modelPackage = ConfigPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns
	 * a non null result; it yields that result. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns
	 * a non null result; it yields that result. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		} else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch(
				eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns
	 * a non null result; it yields that result. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case ConfigPackage.TEST_CONFIG: {
			TestConfig testConfig = (TestConfig) theEObject;
			T result = caseTestConfig(testConfig);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ConfigPackage.TEST_RUN: {
			TestRun testRun = (TestRun) theEObject;
			T result = caseTestRun(testRun);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ConfigPackage.TEST_RESULT: {
			TestResult testResult = (TestResult) theEObject;
			T result = caseTestResult(testResult);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ConfigPackage.TEST_DIFF: {
			TestDiff testDiff = (TestDiff) theEObject;
			T result = caseTestDiff(testDiff);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ConfigPackage.DIFF_REPORT: {
			DiffReport diffReport = (DiffReport) theEObject;
			T result = caseDiffReport(diffReport);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ConfigPackage.ROOT: {
			Root root = (Root) theEObject;
			T result = caseRoot(root);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ConfigPackage.MUTATOR_CONFIG: {
			MutatorConfig mutatorConfig = (MutatorConfig) theEObject;
			T result = caseMutatorConfig(mutatorConfig);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Test Config</em>'. <!-- begin-user-doc -->
	 * This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Test Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestConfig(TestConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Test Run</em>'. <!-- begin-user-doc -->
	 * This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Test Run</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestRun(TestRun object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Test Result</em>'. <!-- begin-user-doc -->
	 * This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Test Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestResult(TestResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Test Diff</em>'. <!-- begin-user-doc -->
	 * This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Test Diff</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestDiff(TestDiff object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Diff Report</em>'. <!-- begin-user-doc -->
	 * This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Diff Report</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDiffReport(DiffReport object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Root</em>'. <!-- begin-user-doc --> This
	 * implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoot(Root object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Mutator Config</em>'. <!-- begin-user-doc
	 * --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Mutator Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMutatorConfig(MutatorConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>EObject</em>'. <!-- begin-user-doc --> This
	 * implementation returns
	 * null; returning a non-null result will terminate the switch, but this is
	 * the last case anyway. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} // ConfigSwitch