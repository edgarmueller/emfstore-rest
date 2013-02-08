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
package org.eclipse.emf.emfstore.server.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACUser;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Authentication Information</b></em>'. <!--
 * end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation#getSessionId <em>Session Id</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation#getResolvedACUser <em>Resolved AC User
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.internal.common.model.server.model.ModelPackage#getAuthenticationInformation()
 * @model
 * @generated
 */
public interface AuthenticationInformation extends EObject {
	/**
	 * Returns the value of the '<em><b>Session Id</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Session Id</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Session Id</em>' containment reference.
	 * @see #setSessionId(SessionId)
	 * @see org.eclipse.emf.emfstore.internal.common.model.server.model.ModelPackage#getAuthenticationInformation_SessionId()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	SessionId getSessionId();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation#getSessionId
	 * <em>Session Id</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Session Id</em>' containment reference.
	 * @see #getSessionId()
	 * @generated
	 */
	void setSessionId(SessionId value);

	/**
	 * Returns the value of the '<em><b>Resolved AC User</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resolved AC User</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Resolved AC User</em>' containment reference.
	 * @see #setResolvedACUser(ACUser)
	 * @see org.eclipse.emf.emfstore.internal.common.model.server.model.ModelPackage#getAuthenticationInformation_ResolvedACUser()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	ACUser getResolvedACUser();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation#getResolvedACUser
	 * <em>Resolved AC User</em>}' containment reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Resolved AC User</em>' containment reference.
	 * @see #getResolvedACUser()
	 * @generated
	 */
	void setResolvedACUser(ACUser value);

} // AuthenticationInformation