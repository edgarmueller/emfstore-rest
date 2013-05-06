/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.versioning.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.server.model.versioning.*;
import org.eclipse.emf.emfstore.internal.server.model.versioning.AncestorVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.DateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HeadVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ModelElementQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PathQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Version;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionProperty;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method
 * for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage
 * @generated
 */
public class VersioningSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static VersioningPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public VersioningSwitch() {
		if (modelPackage == null)
		{
			modelPackage = VersioningPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
	 * result.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
	 * result.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage)
		{
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else
		{
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return eSuperTypes.isEmpty() ?
				defaultCase(theEObject) :
				doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
	 * result.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID)
		{
		case VersioningPackage.TAG_VERSION_SPEC: {
			TagVersionSpec tagVersionSpec = (TagVersionSpec) theEObject;
			T result = caseTagVersionSpec(tagVersionSpec);
			if (result == null)
				result = caseVersionSpec(tagVersionSpec);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.DATE_VERSION_SPEC: {
			DateVersionSpec dateVersionSpec = (DateVersionSpec) theEObject;
			T result = caseDateVersionSpec(dateVersionSpec);
			if (result == null)
				result = caseVersionSpec(dateVersionSpec);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.PRIMARY_VERSION_SPEC: {
			PrimaryVersionSpec primaryVersionSpec = (PrimaryVersionSpec) theEObject;
			T result = casePrimaryVersionSpec(primaryVersionSpec);
			if (result == null)
				result = caseVersionSpec(primaryVersionSpec);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.VERSION_SPEC: {
			VersionSpec versionSpec = (VersionSpec) theEObject;
			T result = caseVersionSpec(versionSpec);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.LOG_MESSAGE: {
			LogMessage logMessage = (LogMessage) theEObject;
			T result = caseLogMessage(logMessage);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.CHANGE_PACKAGE: {
			ChangePackage changePackage = (ChangePackage) theEObject;
			T result = caseChangePackage(changePackage);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.HISTORY_INFO: {
			HistoryInfo historyInfo = (HistoryInfo) theEObject;
			T result = caseHistoryInfo(historyInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.HISTORY_QUERY: {
			HistoryQuery historyQuery = (HistoryQuery) theEObject;
			T result = caseHistoryQuery(historyQuery);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.RANGE_QUERY: {
			RangeQuery rangeQuery = (RangeQuery) theEObject;
			T result = caseRangeQuery(rangeQuery);
			if (result == null)
				result = caseHistoryQuery(rangeQuery);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.PATH_QUERY: {
			PathQuery pathQuery = (PathQuery) theEObject;
			T result = casePathQuery(pathQuery);
			if (result == null)
				result = caseHistoryQuery(pathQuery);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.MODEL_ELEMENT_QUERY: {
			ModelElementQuery modelElementQuery = (ModelElementQuery) theEObject;
			T result = caseModelElementQuery(modelElementQuery);
			if (result == null)
				result = caseRangeQuery(modelElementQuery);
			if (result == null)
				result = caseHistoryQuery(modelElementQuery);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.VERSION: {
			Version version = (Version) theEObject;
			T result = caseVersion(version);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.HEAD_VERSION_SPEC: {
			HeadVersionSpec headVersionSpec = (HeadVersionSpec) theEObject;
			T result = caseHeadVersionSpec(headVersionSpec);
			if (result == null)
				result = caseVersionSpec(headVersionSpec);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.VERSION_PROPERTY: {
			VersionProperty versionProperty = (VersionProperty) theEObject;
			T result = caseVersionProperty(versionProperty);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.BRANCH_VERSION_SPEC: {
			BranchVersionSpec branchVersionSpec = (BranchVersionSpec) theEObject;
			T result = caseBranchVersionSpec(branchVersionSpec);
			if (result == null)
				result = caseVersionSpec(branchVersionSpec);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.BRANCH_INFO: {
			BranchInfo branchInfo = (BranchInfo) theEObject;
			T result = caseBranchInfo(branchInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VersioningPackage.ANCESTOR_VERSION_SPEC: {
			AncestorVersionSpec ancestorVersionSpec = (AncestorVersionSpec) theEObject;
			T result = caseAncestorVersionSpec(ancestorVersionSpec);
			if (result == null)
				result = caseVersionSpec(ancestorVersionSpec);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tag Version Spec</em>'.
	 * <!-- begin-user-doc
	 * --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tag Version Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTagVersionSpec(TagVersionSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Date Version Spec</em>'. <!--
	 * begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Date Version Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDateVersionSpec(DateVersionSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Primary Version Spec</em>'. <!--
	 * begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Primary Version Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrimaryVersionSpec(PrimaryVersionSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Spec</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionSpec(VersionSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Log Message</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Log Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLogMessage(LogMessage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Change Package</em>'.
	 * <!-- begin-user-doc
	 * --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Change Package</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChangePackage(ChangePackage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>History Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>History Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHistoryInfo(HistoryInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>History Query</em>'.
	 * <!-- begin-user-doc
	 * --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>History Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHistoryQuery(HistoryQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Range Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Range Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRangeQuery(RangeQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Path Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Path Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePathQuery(PathQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Model Element Query</em>'. <!--
	 * begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Model Element Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelElementQuery(ModelElementQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersion(Version object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Head Version Spec</em>'. <!--
	 * begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Head Version Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHeadVersionSpec(HeadVersionSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Property</em>'.
	 * <!-- begin-user-doc
	 * --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionProperty(VersionProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Branch Version Spec</em>'. <!--
	 * begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Branch Version Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBranchVersionSpec(BranchVersionSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Branch Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Branch Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBranchInfo(BranchInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Ancestor Version Spec</em>'. <!--
	 * begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Ancestor Version Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAncestorVersionSpec(AncestorVersionSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns
	 * null; returning a non-null result will terminate the switch, but this is
	 * the last case anyway. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} // VersioningSwitch