/**
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.client.test.testmodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Element To Test Element Map</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.impl.TestElementToTestElementMapImpl#getTypedValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.impl.TestElementToTestElementMapImpl#getTypedKey <em>Key</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TestElementToTestElementMapImpl extends EObjectImpl implements BasicEMap.Entry<TestElement,TestElement> {
	/**
	 * The cached value of the '{@link #getTypedValue() <em>Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypedValue()
	 * @generated
	 * @ordered
	 */
	protected TestElement value;

	/**
	 * The cached value of the '{@link #getTypedKey() <em>Key</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypedKey()
	 * @generated
	 * @ordered
	 */
	protected TestElement key;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestElementToTestElementMapImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestmodelPackage.Literals.TEST_ELEMENT_TO_TEST_ELEMENT_MAP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement getTypedValue() {
		if (value != null && value.eIsProxy()) {
			InternalEObject oldValue = (InternalEObject)value;
			value = (TestElement)eResolveProxy(oldValue);
			if (value != oldValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__VALUE, oldValue, value));
			}
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement basicGetTypedValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypedValue(TestElement newValue) {
		TestElement oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement getTypedKey() {
		if (key != null && key.eIsProxy()) {
			InternalEObject oldKey = (InternalEObject)key;
			key = (TestElement)eResolveProxy(oldKey);
			if (key != oldKey) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__KEY, oldKey, key));
			}
		}
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement basicGetTypedKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypedKey(TestElement newKey) {
		TestElement oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__VALUE:
				if (resolve) return getTypedValue();
				return basicGetTypedValue();
			case TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__KEY:
				if (resolve) return getTypedKey();
				return basicGetTypedKey();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__VALUE:
				setTypedValue((TestElement)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__KEY:
				setTypedKey((TestElement)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__VALUE:
				setTypedValue((TestElement)null);
				return;
			case TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__KEY:
				setTypedKey((TestElement)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__VALUE:
				return value != null;
			case TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP__KEY:
				return key != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected int hash = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getHash() {
		if (hash == -1) {
			Object theKey = getKey();
			hash = (theKey == null ? 0 : theKey.hashCode());
		}
		return hash;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHash(int hash) {
		this.hash = hash;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement getKey() {
		return getTypedKey();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(TestElement key) {
		setTypedKey(key);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement getValue() {
		return getTypedValue();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement setValue(TestElement value) {
		TestElement oldValue = getValue();
		setTypedValue(value);
		return oldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EMap<TestElement, TestElement> getEMap() {
		EObject container = eContainer();
		return container == null ? null : (EMap<TestElement, TestElement>)container.eGet(eContainmentFeature());
	}

} //TestElementToTestElementMapImpl
