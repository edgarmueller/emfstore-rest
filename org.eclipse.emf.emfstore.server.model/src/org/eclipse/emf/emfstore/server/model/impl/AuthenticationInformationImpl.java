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
package org.eclipse.emf.emfstore.server.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.server.model.ModelPackage;
import org.eclipse.emf.emfstore.server.model.SessionId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACUser;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Authentication Information</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.server.model.impl.AuthenticationInformationImpl#getSessionId <em>Session Id</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.server.model.impl.AuthenticationInformationImpl#getResolvedACUser <em>Resolved AC User</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthenticationInformationImpl extends EObjectImpl implements AuthenticationInformation {
	/**
	 * The cached value of the '{@link #getSessionId() <em>Session Id</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSessionId()
	 * @generated
	 * @ordered
	 */
	protected SessionId sessionId;

	/**
	 * The cached value of the '{@link #getResolvedACUser() <em>Resolved AC User</em>}' containment reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getResolvedACUser()
	 * @generated
	 * @ordered
	 */
	protected ACUser resolvedACUser;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthenticationInformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.AUTHENTICATION_INFORMATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SessionId getSessionId() {
		if (sessionId != null && sessionId.eIsProxy()) {
			InternalEObject oldSessionId = (InternalEObject)sessionId;
			sessionId = (SessionId)eResolveProxy(oldSessionId);
			if (sessionId != oldSessionId) {
				InternalEObject newSessionId = (InternalEObject)sessionId;
				NotificationChain msgs = oldSessionId.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID, null, null);
				if (newSessionId.eInternalContainer() == null) {
					msgs = newSessionId.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID, oldSessionId, sessionId));
			}
		}
		return sessionId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SessionId basicGetSessionId() {
		return sessionId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSessionId(SessionId newSessionId, NotificationChain msgs) {
		SessionId oldSessionId = sessionId;
		sessionId = newSessionId;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID, oldSessionId, newSessionId);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setSessionId(SessionId newSessionId) {
		if (newSessionId != sessionId) {
			NotificationChain msgs = null;
			if (sessionId != null)
				msgs = ((InternalEObject)sessionId).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID, null, msgs);
			if (newSessionId != null)
				msgs = ((InternalEObject)newSessionId).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID, null, msgs);
			msgs = basicSetSessionId(newSessionId, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID, newSessionId, newSessionId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ACUser getResolvedACUser() {
		if (resolvedACUser != null && resolvedACUser.eIsProxy()) {
			InternalEObject oldResolvedACUser = (InternalEObject)resolvedACUser;
			resolvedACUser = (ACUser)eResolveProxy(oldResolvedACUser);
			if (resolvedACUser != oldResolvedACUser) {
				InternalEObject newResolvedACUser = (InternalEObject)resolvedACUser;
				NotificationChain msgs = oldResolvedACUser.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER, null, null);
				if (newResolvedACUser.eInternalContainer() == null) {
					msgs = newResolvedACUser.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER, oldResolvedACUser, resolvedACUser));
			}
		}
		return resolvedACUser;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ACUser basicGetResolvedACUser() {
		return resolvedACUser;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResolvedACUser(ACUser newResolvedACUser, NotificationChain msgs) {
		ACUser oldResolvedACUser = resolvedACUser;
		resolvedACUser = newResolvedACUser;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER, oldResolvedACUser, newResolvedACUser);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setResolvedACUser(ACUser newResolvedACUser) {
		if (newResolvedACUser != resolvedACUser) {
			NotificationChain msgs = null;
			if (resolvedACUser != null)
				msgs = ((InternalEObject)resolvedACUser).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER, null, msgs);
			if (newResolvedACUser != null)
				msgs = ((InternalEObject)newResolvedACUser).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER, null, msgs);
			msgs = basicSetResolvedACUser(newResolvedACUser, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER, newResolvedACUser, newResolvedACUser));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID:
				return basicSetSessionId(null, msgs);
			case ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER:
				return basicSetResolvedACUser(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID:
				if (resolve) return getSessionId();
				return basicGetSessionId();
			case ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER:
				if (resolve) return getResolvedACUser();
				return basicGetResolvedACUser();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID:
				setSessionId((SessionId)newValue);
				return;
			case ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER:
				setResolvedACUser((ACUser)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID:
				setSessionId((SessionId)null);
				return;
			case ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER:
				setResolvedACUser((ACUser)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.AUTHENTICATION_INFORMATION__SESSION_ID:
				return sessionId != null;
			case ModelPackage.AUTHENTICATION_INFORMATION__RESOLVED_AC_USER:
				return resolvedACUser != null;
		}
		return super.eIsSet(featureID);
	}

} // AuthenticationInformationImpl