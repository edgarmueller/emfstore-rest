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
package org.eclipse.emf.emfstore.internal.server.model.versioning;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>History Info</b></em>'.
 * 
 * @extends ESHistoryInfo
 *          <!-- end-user-doc -->
 * 
 *          <p>
 *          The following features are supported:
 *          <ul>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getPrimerySpec <em>Primery
 *          Spec </em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getNextSpec <em>Next Spec
 *          </em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getPreviousSpec <em>
 *          Previous Spec </em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getMergedFrom <em>Merged
 *          From</em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getMergedTo <em>Merged To
 *          </em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getLogMessage <em>Log
 *          Message</em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getTagSpecs <em>Tag Specs
 *          </em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getVersionProperties <em>
 *          Version Properties</em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getChangePackage <em>Change
 *          Package </em>}</li>
 *          </ul>
 *          </p>
 * 
 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getHistoryInfo()
 * @model
 * @generated
 */
public interface HistoryInfo extends EObject, ESHistoryInfo {
	/**
	 * Returns the value of the '<em><b>Primery Spec</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primery Spec</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Primery Spec</em>' containment reference.
	 * @see #setPrimerySpec(PrimaryVersionSpec)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getHistoryInfo_PrimerySpec()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	PrimaryVersionSpec getPrimarySpec();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getPrimerySpec
	 * <em>Primery Spec</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Primery Spec</em>' containment reference.
	 * @see #getPrimarySpec()
	 * @generated
	 */
	void setPrimerySpec(PrimaryVersionSpec value);

	/**
	 * Returns the value of the '<em><b>Next Spec</b></em>' containment reference list.
	 * The list contents are of type
	 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next Spec</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Next Spec</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getHistoryInfo_NextSpec()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	EList<PrimaryVersionSpec> getNextSpec();

	/**
	 * Returns the value of the '<em><b>Previous Spec</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Previous Spec</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Previous Spec</em>' containment reference.
	 * @see #setPreviousSpec(PrimaryVersionSpec)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getHistoryInfo_PreviousSpec()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	PrimaryVersionSpec getPreviousSpec();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getPreviousSpec
	 * <em>Previous Spec</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Previous Spec</em>' containment reference.
	 * @see #getPreviousSpec()
	 * @generated
	 */
	void setPreviousSpec(PrimaryVersionSpec value);

	/**
	 * Returns the value of the '<em><b>Merged From</b></em>' containment reference list.
	 * The list contents are of type
	 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merged From</em>' containment reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Merged From</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getHistoryInfo_MergedFrom()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	EList<PrimaryVersionSpec> getMergedFrom();

	/**
	 * Returns the value of the '<em><b>Merged To</b></em>' containment reference list.
	 * The list contents are of type
	 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merged To</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Merged To</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getHistoryInfo_MergedTo()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	EList<PrimaryVersionSpec> getMergedTo();

	/**
	 * Returns the value of the '<em><b>Log Message</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Log Message</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Log Message</em>' containment reference.
	 * @see #setLogMessage(LogMessage)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getHistoryInfo_LogMessage()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	LogMessage getLogMessage();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getLogMessage
	 * <em>Log Message</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Log Message</em>' containment reference.
	 * @see #getLogMessage()
	 * @generated
	 */
	void setLogMessage(LogMessage value);

	/**
	 * Returns the value of the '<em><b>Tag Specs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tag Specs</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Tag Specs</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getHistoryInfo_TagSpecs()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<TagVersionSpec> getTagSpecs();

	/**
	 * Returns the value of the '<em><b>Version Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.internal.server.model.versioning.VersionProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version Properties</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Version Properties</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getHistoryInfo_VersionProperties()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<VersionProperty> getVersionProperties();

	/**
	 * Returns the value of the '<em><b>Change Package</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change Package</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Change Package</em>' containment reference.
	 * @see #setChangePackage(ChangePackage)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getHistoryInfo_ChangePackage()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	ChangePackage getChangePackage();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo#getChangePackage
	 * <em>Change Package</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Change Package</em>' containment reference.
	 * @see #getChangePackage()
	 * @generated
	 */
	void setChangePackage(ChangePackage value);

} // HistoryInfo