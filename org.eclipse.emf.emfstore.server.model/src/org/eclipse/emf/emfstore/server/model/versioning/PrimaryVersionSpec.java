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

import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Primary Version Spec</b></em>'.
 * 
 * @extends Comparable<PrimaryVersionSpec>, IPrimaryVersionSpec
 * 
 *          <!-- end-user-doc -->
 * 
 *          <p>
 *          The following features are supported:
 *          <ul>
 *          <li>{@link org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec#getIdentifier <em>Identifier
 *          </em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec#getProjectStateChecksum <em>
 *          Project State Checksum</em>}</li>
 *          </ul>
 *          </p>
 * 
 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getPrimaryVersionSpec()
 * @model
 * @generated
 */
public interface PrimaryVersionSpec extends VersionSpec, Comparable<PrimaryVersionSpec>, IPrimaryVersionSpec {

	/**
	 * Returns the value of the '<em><b>Identifier</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identifier</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Identifier</em>' attribute.
	 * @see #setIdentifier(int)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getPrimaryVersionSpec_Identifier()
	 * @model required="true"
	 * @generated
	 */
	int getIdentifier();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec#getIdentifier
	 * <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Identifier</em>' attribute.
	 * @see #getIdentifier()
	 * @generated
	 */
	void setIdentifier(int value);

	/**
	 * Returns the value of the '<em><b>Project State Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project State Checksum</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Project State Checksum</em>' attribute.
	 * @see #setProjectStateChecksum(long)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getPrimaryVersionSpec_ProjectStateChecksum()
	 * @model
	 * @generated
	 */
	long getProjectStateChecksum();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec#getProjectStateChecksum
	 * <em>Project State Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Project State Checksum</em>' attribute.
	 * @see #getProjectStateChecksum()
	 * @generated
	 */
	void setProjectStateChecksum(long value);

} // PrimaryVersionSpec