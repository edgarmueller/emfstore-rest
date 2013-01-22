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

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Ancestor Version Spec</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.server.model.versioning.AncestorVersionSpec#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.server.model.versioning.AncestorVersionSpec#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getAncestorVersionSpec()
 * @model
 * @generated
 */
public interface AncestorVersionSpec extends VersionSpec {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(PrimaryVersionSpec)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getAncestorVersionSpec_Target()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	PrimaryVersionSpec getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.server.model.versioning.AncestorVersionSpec#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(PrimaryVersionSpec value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' containment reference.
	 * @see #setSource(PrimaryVersionSpec)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getAncestorVersionSpec_Source()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	PrimaryVersionSpec getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.server.model.versioning.AncestorVersionSpec#getSource <em>Source</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' containment reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(PrimaryVersionSpec value);

} // AncestorVersionSpec