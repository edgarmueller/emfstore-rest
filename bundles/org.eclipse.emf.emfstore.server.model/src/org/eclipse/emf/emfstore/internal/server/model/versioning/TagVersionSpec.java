/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.versioning;

import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Tag Version Spec</b></em>'.
 * 
 * @extends APIDelegate<ESTagVersionSpec>
 *          <!-- end-user-doc
 *          -->
 * 
 *          <p>
 *          The following features are supported:
 *          <ul>
 *          <li>
 *          {@link org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec#getName
 *          <em>Name</em>}</li>
 *          </ul>
 *          </p>
 * 
 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getTagVersionSpec()
 * @model
 * @generated
 */
public interface TagVersionSpec extends VersionSpec, APIDelegate<ESTagVersionSpec> {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getTagVersionSpec_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec#getName
	 * <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // TagVersionSpec
