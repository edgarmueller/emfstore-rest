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
package org.eclipse.emf.emfstore.internal.server.model.accesscontrol.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.AccesscontrolPackage;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.accesscontrol.ESUserImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>AC User</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.impl.ACUserImpl#getFirstName <em>First Name
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.impl.ACUserImpl#getLastName <em>Last Name
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.impl.ACUserImpl#getEffectiveGroups <em>
 * Effective Groups</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.impl.ACUserImpl#getPassword <em>Password
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ACUserImpl extends ACOrgUnitImpl implements ACUser {
	/**
	 * The default value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
	protected static final String FIRST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
	protected String firstName = FIRST_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected String lastName = LAST_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEffectiveGroups()
	 * <em>Effective Groups</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEffectiveGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<ACGroup> effectiveGroups;

	/**
	 * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected String password = PASSWORD_EDEFAULT;

	private ESUserImpl apiImpl;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ACUserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AccesscontrolPackage.Literals.AC_USER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFirstName(String newFirstName) {
		final String oldFirstName = firstName;
		firstName = newFirstName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AccesscontrolPackage.AC_USER__FIRST_NAME,
				oldFirstName, firstName));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLastName(String newLastName) {
		final String oldLastName = lastName;
		lastName = newLastName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AccesscontrolPackage.AC_USER__LAST_NAME, oldLastName,
				lastName));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ACGroup> getEffectiveGroups() {
		if (effectiveGroups == null)
		{
			effectiveGroups = new EObjectContainmentEList.Resolving<ACGroup>(ACGroup.class, this,
				AccesscontrolPackage.AC_USER__EFFECTIVE_GROUPS);
		}
		return effectiveGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPassword(String newPassword)
	{
		final String oldPassword = password;
		password = newPassword;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AccesscontrolPackage.AC_USER__PASSWORD, oldPassword,
				password));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
		case AccesscontrolPackage.AC_USER__EFFECTIVE_GROUPS:
			return ((InternalEList<?>) getEffectiveGroups()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
		case AccesscontrolPackage.AC_USER__FIRST_NAME:
			return getFirstName();
		case AccesscontrolPackage.AC_USER__LAST_NAME:
			return getLastName();
		case AccesscontrolPackage.AC_USER__EFFECTIVE_GROUPS:
			return getEffectiveGroups();
		case AccesscontrolPackage.AC_USER__PASSWORD:
			return getPassword();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
		case AccesscontrolPackage.AC_USER__FIRST_NAME:
			setFirstName((String) newValue);
			return;
		case AccesscontrolPackage.AC_USER__LAST_NAME:
			setLastName((String) newValue);
			return;
		case AccesscontrolPackage.AC_USER__EFFECTIVE_GROUPS:
			getEffectiveGroups().clear();
			getEffectiveGroups().addAll((Collection<? extends ACGroup>) newValue);
			return;
		case AccesscontrolPackage.AC_USER__PASSWORD:
			setPassword((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID)
		{
		case AccesscontrolPackage.AC_USER__FIRST_NAME:
			setFirstName(FIRST_NAME_EDEFAULT);
			return;
		case AccesscontrolPackage.AC_USER__LAST_NAME:
			setLastName(LAST_NAME_EDEFAULT);
			return;
		case AccesscontrolPackage.AC_USER__EFFECTIVE_GROUPS:
			getEffectiveGroups().clear();
			return;
		case AccesscontrolPackage.AC_USER__PASSWORD:
			setPassword(PASSWORD_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID)
		{
		case AccesscontrolPackage.AC_USER__FIRST_NAME:
			return FIRST_NAME_EDEFAULT == null ? firstName != null : !FIRST_NAME_EDEFAULT.equals(firstName);
		case AccesscontrolPackage.AC_USER__LAST_NAME:
			return LAST_NAME_EDEFAULT == null ? lastName != null : !LAST_NAME_EDEFAULT.equals(lastName);
		case AccesscontrolPackage.AC_USER__EFFECTIVE_GROUPS:
			return effectiveGroups != null && !effectiveGroups.isEmpty();
		case AccesscontrolPackage.AC_USER__PASSWORD:
			return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (firstName: ");
		result.append(firstName);
		result.append(", lastName: ");
		result.append(lastName);
		result.append(", password: ");
		result.append(password);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#toAPI()
	 */
	public ESUserImpl toAPI() {
		if (apiImpl == null) {
			apiImpl = createAPI();
		}
		return apiImpl;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#createAPI()
	 */
	public ESUserImpl createAPI() {
		return new ESUserImpl(this);
	}

} // ACUserImpl