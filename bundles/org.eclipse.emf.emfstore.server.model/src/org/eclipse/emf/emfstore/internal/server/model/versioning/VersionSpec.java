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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Version Spec</b></em>'.
 * 
 * @extends ESVersionSpec
 *          <!-- end-user-doc -->
 * 
 *          <p>
 *          The following features are supported:
 *          <ul>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec#getBranch <em>Branch</em>}</li>
 *          </ul>
 *          </p>
 * 
 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getVersionSpec()
 * @model abstract="true"
 * @generated
 */
public interface VersionSpec extends EObject {

	/**
	 * The default branch name 'trunk'.
	 */
	String BRANCH_DEFAULT_NAME = "trunk";

	/**
	 * Global identifier.
	 */
	String GLOBAL = "___GLOBAL___";

	/**
	 * Returns the value of the '<em><b>Branch</b></em>' attribute.
	 * The default value is <code>"trunk"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branch</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Branch</em>' attribute.
	 * @see #setBranch(String)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getVersionSpec_Branch()
	 * @model default="trunk"
	 * @generated
	 */
	String getBranch();

	/**
	 * Sets the value of the ' {@link org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec#getBranch
	 * <em>Branch</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Branch</em>' attribute.
	 * @see #getBranch()
	 * @generated
	 */
	void setBranch(String value);

	/**
	 * Use {@link Versions} instead.
	 */
	@Deprecated
	VersionSpec HEAD_VERSION = VersioningFactory.eINSTANCE.createHeadVersionSpec();

} // VersionSpec