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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage;
import org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Mutator Config</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl#getRootEClass
 * <em>Root EClass</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl#getMinObjectsCount
 * <em>Min Objects Count </em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl#isIgnoreAndLog
 * <em>Ignore And Log</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl#isDoNotGenerateRoot
 * <em>Do Not Generate Root</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl#isUseEcoreUtilDelete
 * <em>Use Ecore Util Delete</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl#getEClassesToIgnore
 * <em>EClasses To Ignore</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl#getEStructuralFeaturesToIgnore
 * <em> EStructural Features To Ignore</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl#getEPackages
 * <em>EPackages</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl#getMaxDeleteCount
 * <em>Max Delete Count </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MutatorConfigImpl extends EObjectImpl implements MutatorConfig {
	/**
	 * The cached value of the '{@link #getRootEClass() <em>Root EClass</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRootEClass()
	 * @generated
	 * @ordered
	 */
	protected EClass rootEClass;

	/**
	 * The default value of the '{@link #getMinObjectsCount()
	 * <em>Min Objects Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getMinObjectsCount()
	 * @generated
	 * @ordered
	 */
	protected static final int MIN_OBJECTS_COUNT_EDEFAULT = 100;

	/**
	 * The cached value of the '{@link #getMinObjectsCount()
	 * <em>Min Objects Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getMinObjectsCount()
	 * @generated
	 * @ordered
	 */
	protected int minObjectsCount = MIN_OBJECTS_COUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #isIgnoreAndLog()
	 * <em>Ignore And Log</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isIgnoreAndLog()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORE_AND_LOG_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIgnoreAndLog()
	 * <em>Ignore And Log</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isIgnoreAndLog()
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreAndLog = IGNORE_AND_LOG_EDEFAULT;

	/**
	 * The default value of the '{@link #isDoNotGenerateRoot()
	 * <em>Do Not Generate Root</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isDoNotGenerateRoot()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DO_NOT_GENERATE_ROOT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDoNotGenerateRoot()
	 * <em>Do Not Generate Root</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isDoNotGenerateRoot()
	 * @generated
	 * @ordered
	 */
	protected boolean doNotGenerateRoot = DO_NOT_GENERATE_ROOT_EDEFAULT;

	/**
	 * The default value of the '{@link #isUseEcoreUtilDelete()
	 * <em>Use Ecore Util Delete</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isUseEcoreUtilDelete()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_ECORE_UTIL_DELETE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseEcoreUtilDelete()
	 * <em>Use Ecore Util Delete</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isUseEcoreUtilDelete()
	 * @generated
	 * @ordered
	 */
	protected boolean useEcoreUtilDelete = USE_ECORE_UTIL_DELETE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEClassesToIgnore()
	 * <em>EClasses To Ignore</em>}' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getEClassesToIgnore()
	 * @generated
	 * @ordered
	 */
	protected EList<EClass> eClassesToIgnore;

	/**
	 * The cached value of the '{@link #getEStructuralFeaturesToIgnore()
	 * <em>EStructural Features To Ignore</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEStructuralFeaturesToIgnore()
	 * @generated
	 * @ordered
	 */
	protected EList<EStructuralFeature> eStructuralFeaturesToIgnore;

	/**
	 * The cached value of the '{@link #getEPackages() <em>EPackages</em>}'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEPackages()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> ePackages;

	/**
	 * The default value of the '{@link #getMaxDeleteCount()
	 * <em>Max Delete Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getMaxDeleteCount()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MAX_DELETE_COUNT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMaxDeleteCount()
	 * <em>Max Delete Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getMaxDeleteCount()
	 * @generated
	 * @ordered
	 */
	protected Integer maxDeleteCount = MAX_DELETE_COUNT_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MutatorConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.MUTATOR_CONFIG;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRootEClass() {
		if (rootEClass != null && rootEClass.eIsProxy()) {
			InternalEObject oldRootEClass = (InternalEObject) rootEClass;
			rootEClass = (EClass) eResolveProxy(oldRootEClass);
			if (rootEClass != oldRootEClass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						ConfigPackage.MUTATOR_CONFIG__ROOT_ECLASS,
						oldRootEClass, rootEClass));
			}
		}
		return rootEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass basicGetRootEClass() {
		return rootEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setRootEClass(EClass newRootEClass) {
		EClass oldRootEClass = rootEClass;
		rootEClass = newRootEClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.MUTATOR_CONFIG__ROOT_ECLASS, oldRootEClass,
				rootEClass));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getMinObjectsCount() {
		return minObjectsCount;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setMinObjectsCount(int newMinObjectsCount) {
		int oldMinObjectsCount = minObjectsCount;
		minObjectsCount = newMinObjectsCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.MUTATOR_CONFIG__MIN_OBJECTS_COUNT,
				oldMinObjectsCount, minObjectsCount));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isIgnoreAndLog() {
		return ignoreAndLog;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setIgnoreAndLog(boolean newIgnoreAndLog) {
		boolean oldIgnoreAndLog = ignoreAndLog;
		ignoreAndLog = newIgnoreAndLog;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.MUTATOR_CONFIG__IGNORE_AND_LOG,
				oldIgnoreAndLog, ignoreAndLog));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isDoNotGenerateRoot() {
		return doNotGenerateRoot;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDoNotGenerateRoot(boolean newDoNotGenerateRoot) {
		boolean oldDoNotGenerateRoot = doNotGenerateRoot;
		doNotGenerateRoot = newDoNotGenerateRoot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.MUTATOR_CONFIG__DO_NOT_GENERATE_ROOT,
				oldDoNotGenerateRoot, doNotGenerateRoot));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isUseEcoreUtilDelete() {
		return useEcoreUtilDelete;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setUseEcoreUtilDelete(boolean newUseEcoreUtilDelete) {
		boolean oldUseEcoreUtilDelete = useEcoreUtilDelete;
		useEcoreUtilDelete = newUseEcoreUtilDelete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.MUTATOR_CONFIG__USE_ECORE_UTIL_DELETE,
				oldUseEcoreUtilDelete, useEcoreUtilDelete));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<EClass> getEClassesToIgnore() {
		if (eClassesToIgnore == null) {
			eClassesToIgnore = new EObjectResolvingEList<EClass>(EClass.class,
				this, ConfigPackage.MUTATOR_CONFIG__ECLASSES_TO_IGNORE);
		}
		return eClassesToIgnore;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<EStructuralFeature> getEStructuralFeaturesToIgnore() {
		if (eStructuralFeaturesToIgnore == null) {
			eStructuralFeaturesToIgnore = new EObjectResolvingEList<EStructuralFeature>(
				EStructuralFeature.class,
				this,
				ConfigPackage.MUTATOR_CONFIG__ESTRUCTURAL_FEATURES_TO_IGNORE);
		}
		return eStructuralFeaturesToIgnore;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<EPackage> getEPackages() {
		if (ePackages == null) {
			ePackages = new EObjectResolvingEList<EPackage>(EPackage.class,
				this, ConfigPackage.MUTATOR_CONFIG__EPACKAGES);
		}
		return ePackages;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Integer getMaxDeleteCount() {
		return maxDeleteCount;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setMaxDeleteCount(Integer newMaxDeleteCount) {
		Integer oldMaxDeleteCount = maxDeleteCount;
		maxDeleteCount = newMaxDeleteCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				ConfigPackage.MUTATOR_CONFIG__MAX_DELETE_COUNT,
				oldMaxDeleteCount, maxDeleteCount));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ConfigPackage.MUTATOR_CONFIG__ROOT_ECLASS:
			if (resolve)
				return getRootEClass();
			return basicGetRootEClass();
		case ConfigPackage.MUTATOR_CONFIG__MIN_OBJECTS_COUNT:
			return getMinObjectsCount();
		case ConfigPackage.MUTATOR_CONFIG__IGNORE_AND_LOG:
			return isIgnoreAndLog();
		case ConfigPackage.MUTATOR_CONFIG__DO_NOT_GENERATE_ROOT:
			return isDoNotGenerateRoot();
		case ConfigPackage.MUTATOR_CONFIG__USE_ECORE_UTIL_DELETE:
			return isUseEcoreUtilDelete();
		case ConfigPackage.MUTATOR_CONFIG__ECLASSES_TO_IGNORE:
			return getEClassesToIgnore();
		case ConfigPackage.MUTATOR_CONFIG__ESTRUCTURAL_FEATURES_TO_IGNORE:
			return getEStructuralFeaturesToIgnore();
		case ConfigPackage.MUTATOR_CONFIG__EPACKAGES:
			return getEPackages();
		case ConfigPackage.MUTATOR_CONFIG__MAX_DELETE_COUNT:
			return getMaxDeleteCount();
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
		switch (featureID) {
		case ConfigPackage.MUTATOR_CONFIG__ROOT_ECLASS:
			setRootEClass((EClass) newValue);
			return;
		case ConfigPackage.MUTATOR_CONFIG__MIN_OBJECTS_COUNT:
			setMinObjectsCount((Integer) newValue);
			return;
		case ConfigPackage.MUTATOR_CONFIG__IGNORE_AND_LOG:
			setIgnoreAndLog((Boolean) newValue);
			return;
		case ConfigPackage.MUTATOR_CONFIG__DO_NOT_GENERATE_ROOT:
			setDoNotGenerateRoot((Boolean) newValue);
			return;
		case ConfigPackage.MUTATOR_CONFIG__USE_ECORE_UTIL_DELETE:
			setUseEcoreUtilDelete((Boolean) newValue);
			return;
		case ConfigPackage.MUTATOR_CONFIG__ECLASSES_TO_IGNORE:
			getEClassesToIgnore().clear();
			getEClassesToIgnore().addAll(
				(Collection<? extends EClass>) newValue);
			return;
		case ConfigPackage.MUTATOR_CONFIG__ESTRUCTURAL_FEATURES_TO_IGNORE:
			getEStructuralFeaturesToIgnore().clear();
			getEStructuralFeaturesToIgnore().addAll(
				(Collection<? extends EStructuralFeature>) newValue);
			return;
		case ConfigPackage.MUTATOR_CONFIG__EPACKAGES:
			getEPackages().clear();
			getEPackages().addAll((Collection<? extends EPackage>) newValue);
			return;
		case ConfigPackage.MUTATOR_CONFIG__MAX_DELETE_COUNT:
			setMaxDeleteCount((Integer) newValue);
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
		case ConfigPackage.MUTATOR_CONFIG__ROOT_ECLASS:
			setRootEClass((EClass) null);
			return;
		case ConfigPackage.MUTATOR_CONFIG__MIN_OBJECTS_COUNT:
			setMinObjectsCount(MIN_OBJECTS_COUNT_EDEFAULT);
			return;
		case ConfigPackage.MUTATOR_CONFIG__IGNORE_AND_LOG:
			setIgnoreAndLog(IGNORE_AND_LOG_EDEFAULT);
			return;
		case ConfigPackage.MUTATOR_CONFIG__DO_NOT_GENERATE_ROOT:
			setDoNotGenerateRoot(DO_NOT_GENERATE_ROOT_EDEFAULT);
			return;
		case ConfigPackage.MUTATOR_CONFIG__USE_ECORE_UTIL_DELETE:
			setUseEcoreUtilDelete(USE_ECORE_UTIL_DELETE_EDEFAULT);
			return;
		case ConfigPackage.MUTATOR_CONFIG__ECLASSES_TO_IGNORE:
			getEClassesToIgnore().clear();
			return;
		case ConfigPackage.MUTATOR_CONFIG__ESTRUCTURAL_FEATURES_TO_IGNORE:
			getEStructuralFeaturesToIgnore().clear();
			return;
		case ConfigPackage.MUTATOR_CONFIG__EPACKAGES:
			getEPackages().clear();
			return;
		case ConfigPackage.MUTATOR_CONFIG__MAX_DELETE_COUNT:
			setMaxDeleteCount(MAX_DELETE_COUNT_EDEFAULT);
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
		case ConfigPackage.MUTATOR_CONFIG__ROOT_ECLASS:
			return rootEClass != null;
		case ConfigPackage.MUTATOR_CONFIG__MIN_OBJECTS_COUNT:
			return minObjectsCount != MIN_OBJECTS_COUNT_EDEFAULT;
		case ConfigPackage.MUTATOR_CONFIG__IGNORE_AND_LOG:
			return ignoreAndLog != IGNORE_AND_LOG_EDEFAULT;
		case ConfigPackage.MUTATOR_CONFIG__DO_NOT_GENERATE_ROOT:
			return doNotGenerateRoot != DO_NOT_GENERATE_ROOT_EDEFAULT;
		case ConfigPackage.MUTATOR_CONFIG__USE_ECORE_UTIL_DELETE:
			return useEcoreUtilDelete != USE_ECORE_UTIL_DELETE_EDEFAULT;
		case ConfigPackage.MUTATOR_CONFIG__ECLASSES_TO_IGNORE:
			return eClassesToIgnore != null && !eClassesToIgnore.isEmpty();
		case ConfigPackage.MUTATOR_CONFIG__ESTRUCTURAL_FEATURES_TO_IGNORE:
			return eStructuralFeaturesToIgnore != null
				&& !eStructuralFeaturesToIgnore.isEmpty();
		case ConfigPackage.MUTATOR_CONFIG__EPACKAGES:
			return ePackages != null && !ePackages.isEmpty();
		case ConfigPackage.MUTATOR_CONFIG__MAX_DELETE_COUNT:
			return MAX_DELETE_COUNT_EDEFAULT == null ? maxDeleteCount != null
				: !MAX_DELETE_COUNT_EDEFAULT.equals(maxDeleteCount);
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
		result.append(" (minObjectsCount: ");
		result.append(minObjectsCount);
		result.append(", ignoreAndLog: ");
		result.append(ignoreAndLog);
		result.append(", doNotGenerateRoot: ");
		result.append(doNotGenerateRoot);
		result.append(", useEcoreUtilDelete: ");
		result.append(useEcoreUtilDelete);
		result.append(", maxDeleteCount: ");
		result.append(maxDeleteCount);
		result.append(')');
		return result.toString();
	}

} // MutatorConfigImpl