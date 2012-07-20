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
package org.eclipse.emf.emfstore.server.model.versioning;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>History Query</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery#getSource
 * <em>Source</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery#isIncludeChangePackages
 * <em>Include Change Packages</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery#isIncludeAllVersions
 * <em>Include All Versions</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getHistoryQuery()
 * @model abstract="true"
 * @generated
 */
public interface HistoryQuery extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Source</em>' containment reference.
	 * @see #setSource(PrimaryVersionSpec)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getHistoryQuery_Source()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	PrimaryVersionSpec getSource();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery#getSource
	 * <em>Source</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Source</em>' containment reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(PrimaryVersionSpec value);

	/**
	 * Returns the value of the '<em><b>Include Change Packages</b></em>'
	 * attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include Change Packages</em>' attribute isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Include Change Packages</em>' attribute.
	 * @see #setIncludeChangePackages(boolean)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getHistoryQuery_IncludeChangePackages()
	 * @model
	 * @generated
	 */
	boolean isIncludeChangePackages();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery#isIncludeChangePackages
	 * <em>Include Change Packages</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Include Change Packages</em>'
	 *            attribute.
	 * @see #isIncludeChangePackages()
	 * @generated
	 */
	void setIncludeChangePackages(boolean value);

	/**
	 * Returns the value of the '<em><b>Include All Versions</b></em>'
	 * attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include All Versions</em>' attribute isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Include All Versions</em>' attribute.
	 * @see #setIncludeAllVersions(boolean)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getHistoryQuery_IncludeAllVersions()
	 * @model
	 * @generated
	 */
	boolean isIncludeAllVersions();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery#isIncludeAllVersions
	 * <em>Include All Versions</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Include All Versions</em>'
	 *            attribute.
	 * @see #isIncludeAllVersions()
	 * @generated
	 */
	void setIncludeAllVersions(boolean value);

} // HistoryQuery
