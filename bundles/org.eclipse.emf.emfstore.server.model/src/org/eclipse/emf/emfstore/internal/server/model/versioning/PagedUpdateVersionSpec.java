/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.internal.server.model.versioning;

import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPagedUpdateVersionSpec;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paged Update Version Spec</b></em>'.
 * 
 * @extends APIDelegate<ESPagedUpdateVersionSpec>
 *          <!-- end-user-doc -->
 * 
 *          <p>
 *          The following features are supported:
 *          <ul>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec#getMaxChanges
 *          <em>Max Changes</em>}</li>
 *          <li>
 *          {@link org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec#getBaseVersionSpec
 *          <em>Base Version Spec</em>}</li>
 *          </ul>
 *          </p>
 * 
 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getPagedUpdateVersionSpec()
 * @model
 * @generated
 */
public interface PagedUpdateVersionSpec extends VersionSpec, APIDelegate<ESPagedUpdateVersionSpec>
{
	/**
	 * Returns the value of the '<em><b>Max Changes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Changes</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Max Changes</em>' attribute.
	 * @see #setMaxChanges(int)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getPagedUpdateVersionSpec_MaxChanges()
	 * @model
	 * @generated
	 */
	int getMaxChanges();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec#getMaxChanges
	 * <em>Max Changes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Max Changes</em>' attribute.
	 * @see #getMaxChanges()
	 * @generated
	 */
	void setMaxChanges(int value);

	/**
	 * Returns the value of the '<em><b>Base Version Spec</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Version Spec</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Base Version Spec</em>' containment reference.
	 * @see #setBaseVersionSpec(PrimaryVersionSpec)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getPagedUpdateVersionSpec_BaseVersionSpec()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	PrimaryVersionSpec getBaseVersionSpec();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec#getBaseVersionSpec
	 * <em>Base Version Spec</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Base Version Spec</em>' containment reference.
	 * @see #getBaseVersionSpec()
	 * @generated
	 */
	void setBaseVersionSpec(PrimaryVersionSpec value);

} // PagedUpdateVersionSpec
