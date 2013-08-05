/**
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.fuzzy.emf.config.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Test Diff</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestDiffImpl#getLastUpdate
 * <em>Last Update</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestDiffImpl#getConfig
 * <em>Config</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestDiffImpl#getOldResult
 * <em>Old Result</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestDiffImpl#getNewResult
 * <em>New Result</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TestDiffImpl extends EObjectImpl implements TestDiff {
	/**
	 * The default value of the '{@link #getLastUpdate() <em>Last Update</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLastUpdate()
	 * @generated
	 * @ordered
	 */
	protected static final Date LAST_UPDATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastUpdate() <em>Last Update</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLastUpdate()
	 * @generated
	 * @ordered
	 */
	protected Date lastUpdate = LAST_UPDATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConfig() <em>Config</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getConfig()
	 * @generated
	 * @ordered
	 */
	protected TestConfig config;

	/**
	 * The cached value of the '{@link #getOldResult() <em>Old Result</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOldResult()
	 * @generated
	 * @ordered
	 */
	protected TestResult oldResult;

	/**
	 * The cached value of the '{@link #getNewResult() <em>New Result</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNewResult()
	 * @generated
	 * @ordered
	 */
	protected TestResult newResult;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TestDiffImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.TEST_DIFF;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLastUpdate(Date newLastUpdate) {
		Date oldLastUpdate = lastUpdate;
		lastUpdate = newLastUpdate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.TEST_DIFF__LAST_UPDATE, oldLastUpdate,
				lastUpdate));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestConfig getConfig() {
		if (config != null && config.eIsProxy()) {
			InternalEObject oldConfig = (InternalEObject) config;
			config = (TestConfig) eResolveProxy(oldConfig);
			if (config != oldConfig) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						ConfigPackage.TEST_DIFF__CONFIG, oldConfig, config));
			}
		}
		return config;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestConfig basicGetConfig() {
		return config;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setConfig(TestConfig newConfig) {
		TestConfig oldConfig = config;
		config = newConfig;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.TEST_DIFF__CONFIG, oldConfig, config));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestResult getOldResult() {
		return oldResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetOldResult(TestResult newOldResult,
		NotificationChain msgs) {
		TestResult oldOldResult = oldResult;
		oldResult = newOldResult;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
				Notification.SET, ConfigPackage.TEST_DIFF__OLD_RESULT,
				oldOldResult, newOldResult);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOldResult(TestResult newOldResult) {
		if (newOldResult != oldResult) {
			NotificationChain msgs = null;
			if (oldResult != null)
				msgs = ((InternalEObject) oldResult).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE
						- ConfigPackage.TEST_DIFF__OLD_RESULT, null,
					msgs);
			if (newOldResult != null)
				msgs = ((InternalEObject) newOldResult).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE
						- ConfigPackage.TEST_DIFF__OLD_RESULT, null,
					msgs);
			msgs = basicSetOldResult(newOldResult, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.TEST_DIFF__OLD_RESULT, newOldResult,
				newOldResult));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestResult getNewResult() {
		return newResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetNewResult(TestResult newNewResult,
		NotificationChain msgs) {
		TestResult oldNewResult = newResult;
		newResult = newNewResult;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
				Notification.SET, ConfigPackage.TEST_DIFF__NEW_RESULT,
				oldNewResult, newNewResult);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setNewResult(TestResult newNewResult) {
		if (newNewResult != newResult) {
			NotificationChain msgs = null;
			if (newResult != null)
				msgs = ((InternalEObject) newResult).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE
						- ConfigPackage.TEST_DIFF__NEW_RESULT, null,
					msgs);
			if (newNewResult != null)
				msgs = ((InternalEObject) newNewResult).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE
						- ConfigPackage.TEST_DIFF__NEW_RESULT, null,
					msgs);
			msgs = basicSetNewResult(newNewResult, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.TEST_DIFF__NEW_RESULT, newNewResult,
				newNewResult));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
		int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ConfigPackage.TEST_DIFF__OLD_RESULT:
			return basicSetOldResult(null, msgs);
		case ConfigPackage.TEST_DIFF__NEW_RESULT:
			return basicSetNewResult(null, msgs);
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
		switch (featureID) {
		case ConfigPackage.TEST_DIFF__LAST_UPDATE:
			return getLastUpdate();
		case ConfigPackage.TEST_DIFF__CONFIG:
			if (resolve)
				return getConfig();
			return basicGetConfig();
		case ConfigPackage.TEST_DIFF__OLD_RESULT:
			return getOldResult();
		case ConfigPackage.TEST_DIFF__NEW_RESULT:
			return getNewResult();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ConfigPackage.TEST_DIFF__LAST_UPDATE:
			setLastUpdate((Date) newValue);
			return;
		case ConfigPackage.TEST_DIFF__CONFIG:
			setConfig((TestConfig) newValue);
			return;
		case ConfigPackage.TEST_DIFF__OLD_RESULT:
			setOldResult((TestResult) newValue);
			return;
		case ConfigPackage.TEST_DIFF__NEW_RESULT:
			setNewResult((TestResult) newValue);
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
		switch (featureID) {
		case ConfigPackage.TEST_DIFF__LAST_UPDATE:
			setLastUpdate(LAST_UPDATE_EDEFAULT);
			return;
		case ConfigPackage.TEST_DIFF__CONFIG:
			setConfig((TestConfig) null);
			return;
		case ConfigPackage.TEST_DIFF__OLD_RESULT:
			setOldResult((TestResult) null);
			return;
		case ConfigPackage.TEST_DIFF__NEW_RESULT:
			setNewResult((TestResult) null);
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
		switch (featureID) {
		case ConfigPackage.TEST_DIFF__LAST_UPDATE:
			return LAST_UPDATE_EDEFAULT == null ? lastUpdate != null
				: !LAST_UPDATE_EDEFAULT.equals(lastUpdate);
		case ConfigPackage.TEST_DIFF__CONFIG:
			return config != null;
		case ConfigPackage.TEST_DIFF__OLD_RESULT:
			return oldResult != null;
		case ConfigPackage.TEST_DIFF__NEW_RESULT:
			return newResult != null;
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (lastUpdate: ");
		result.append(lastUpdate);
		result.append(')');
		return result.toString();
	}

} // TestDiffImpl