/**
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.fuzzy.emf.config.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage;
import org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Test Config</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestConfigImpl#getSeed
 * <em>Seed</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestConfigImpl#getCount
 * <em>Count</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestConfigImpl#getTestClass
 * <em>Test Class</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestConfigImpl#getId
 * <em>Id</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestConfigImpl#getMutatorConfig
 * <em>Mutator Config</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TestConfigImpl extends EObjectImpl implements TestConfig {
	/**
	 * The default value of the '{@link #getSeed() <em>Seed</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSeed()
	 * @generated
	 * @ordered
	 */
	protected static final long SEED_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getSeed() <em>Seed</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSeed()
	 * @generated
	 * @ordered
	 */
	protected long seed = SEED_EDEFAULT;

	/**
	 * The default value of the '{@link #getCount() <em>Count</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCount()
	 * @generated
	 * @ordered
	 */
	protected static final int COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCount() <em>Count</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCount()
	 * @generated
	 * @ordered
	 */
	protected int count = COUNT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTestClass() <em>Test Class</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTestClass()
	 * @generated
	 * @ordered
	 */
	protected Class<?> testClass;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMutatorConfig()
	 * <em>Mutator Config</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMutatorConfig()
	 * @generated
	 * @ordered
	 */
	protected MutatorConfig mutatorConfig;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TestConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.TEST_CONFIG;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSeed(long newSeed) {
		long oldSeed = seed;
		seed = newSeed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.TEST_CONFIG__SEED, oldSeed, seed));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getCount() {
		return count;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCount(int newCount) {
		int oldCount = count;
		count = newCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.TEST_CONFIG__COUNT, oldCount, count));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Class<?> getTestClass() {
		return testClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTestClass(Class<?> newTestClass) {
		Class<?> oldTestClass = testClass;
		testClass = newTestClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.TEST_CONFIG__TEST_CLASS, oldTestClass,
				testClass));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.TEST_CONFIG__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MutatorConfig getMutatorConfig() {
		return mutatorConfig;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetMutatorConfig(
		MutatorConfig newMutatorConfig, NotificationChain msgs) {
		MutatorConfig oldMutatorConfig = mutatorConfig;
		mutatorConfig = newMutatorConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
				Notification.SET,
				ConfigPackage.TEST_CONFIG__MUTATOR_CONFIG,
				oldMutatorConfig, newMutatorConfig);
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
	public void setMutatorConfig(MutatorConfig newMutatorConfig) {
		if (newMutatorConfig != mutatorConfig) {
			NotificationChain msgs = null;
			if (mutatorConfig != null)
				msgs = ((InternalEObject) mutatorConfig).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE
						- ConfigPackage.TEST_CONFIG__MUTATOR_CONFIG,
					null, msgs);
			if (newMutatorConfig != null)
				msgs = ((InternalEObject) newMutatorConfig).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE
						- ConfigPackage.TEST_CONFIG__MUTATOR_CONFIG,
					null, msgs);
			msgs = basicSetMutatorConfig(newMutatorConfig, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.TEST_CONFIG__MUTATOR_CONFIG,
				newMutatorConfig, newMutatorConfig));
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
		case ConfigPackage.TEST_CONFIG__MUTATOR_CONFIG:
			return basicSetMutatorConfig(null, msgs);
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
		case ConfigPackage.TEST_CONFIG__SEED:
			return getSeed();
		case ConfigPackage.TEST_CONFIG__COUNT:
			return getCount();
		case ConfigPackage.TEST_CONFIG__TEST_CLASS:
			return getTestClass();
		case ConfigPackage.TEST_CONFIG__ID:
			return getId();
		case ConfigPackage.TEST_CONFIG__MUTATOR_CONFIG:
			return getMutatorConfig();
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
		case ConfigPackage.TEST_CONFIG__SEED:
			setSeed((Long) newValue);
			return;
		case ConfigPackage.TEST_CONFIG__COUNT:
			setCount((Integer) newValue);
			return;
		case ConfigPackage.TEST_CONFIG__TEST_CLASS:
			setTestClass((Class<?>) newValue);
			return;
		case ConfigPackage.TEST_CONFIG__ID:
			setId((String) newValue);
			return;
		case ConfigPackage.TEST_CONFIG__MUTATOR_CONFIG:
			setMutatorConfig((MutatorConfig) newValue);
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
		case ConfigPackage.TEST_CONFIG__SEED:
			setSeed(SEED_EDEFAULT);
			return;
		case ConfigPackage.TEST_CONFIG__COUNT:
			setCount(COUNT_EDEFAULT);
			return;
		case ConfigPackage.TEST_CONFIG__TEST_CLASS:
			setTestClass((Class<?>) null);
			return;
		case ConfigPackage.TEST_CONFIG__ID:
			setId(ID_EDEFAULT);
			return;
		case ConfigPackage.TEST_CONFIG__MUTATOR_CONFIG:
			setMutatorConfig((MutatorConfig) null);
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
		case ConfigPackage.TEST_CONFIG__SEED:
			return seed != SEED_EDEFAULT;
		case ConfigPackage.TEST_CONFIG__COUNT:
			return count != COUNT_EDEFAULT;
		case ConfigPackage.TEST_CONFIG__TEST_CLASS:
			return testClass != null;
		case ConfigPackage.TEST_CONFIG__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case ConfigPackage.TEST_CONFIG__MUTATOR_CONFIG:
			return mutatorConfig != null;
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
		result.append(" (seed: ");
		result.append(seed);
		result.append(", count: ");
		result.append(count);
		result.append(", testClass: ");
		result.append(testClass);
		result.append(", id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} // TestConfigImpl