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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage;
import org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Diff Report</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.DiffReportImpl#getDiffs
 * <em>Diffs</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DiffReportImpl extends EObjectImpl implements DiffReport {
	/**
	 * The cached value of the '{@link #getDiffs() <em>Diffs</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDiffs()
	 * @generated
	 * @ordered
	 */
	protected EList<TestDiff> diffs;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DiffReportImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.DIFF_REPORT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TestDiff> getDiffs() {
		if (diffs == null) {
			diffs = new EObjectContainmentEList<TestDiff>(TestDiff.class, this,
				ConfigPackage.DIFF_REPORT__DIFFS);
		}
		return diffs;
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
		case ConfigPackage.DIFF_REPORT__DIFFS:
			return ((InternalEList<?>) getDiffs()).basicRemove(otherEnd, msgs);
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
		case ConfigPackage.DIFF_REPORT__DIFFS:
			return getDiffs();
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
		case ConfigPackage.DIFF_REPORT__DIFFS:
			getDiffs().clear();
			getDiffs().addAll((Collection<? extends TestDiff>) newValue);
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
		case ConfigPackage.DIFF_REPORT__DIFFS:
			getDiffs().clear();
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
		case ConfigPackage.DIFF_REPORT__DIFFS:
			return diffs != null && !diffs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // DiffReportImpl