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
package org.eclipse.emf.emfstore.internal.server.model.versioning.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.query.ESModelElementQueryImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ModelElementQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage;
import org.eclipse.emf.emfstore.server.model.query.ESModelElementQuery;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Model Element Query</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.impl.ModelElementQueryImpl#getModelElements <em>
 * Model Elements</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated NOT
 */
public class ModelElementQueryImpl extends RangeQueryImpl<ESModelElementQuery> implements ModelElementQuery {

	/**
	 * The cached value of the '{@link #getModelElements() <em>Model Elements</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getModelElements()
	 * @generated
	 * @ordered
	 */
	protected EList<ModelElementId> modelElements;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModelElementQueryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VersioningPackage.Literals.MODEL_ELEMENT_QUERY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ModelElementId> getModelElements() {
		if (modelElements == null) {
			modelElements = new EObjectContainmentEList.Resolving<ModelElementId>(ModelElementId.class, this,
				VersioningPackage.MODEL_ELEMENT_QUERY__MODEL_ELEMENTS);
		}
		return modelElements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VersioningPackage.MODEL_ELEMENT_QUERY__MODEL_ELEMENTS:
			return ((InternalEList<?>) getModelElements()).basicRemove(otherEnd, msgs);
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
		case VersioningPackage.MODEL_ELEMENT_QUERY__MODEL_ELEMENTS:
			return getModelElements();
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
		case VersioningPackage.MODEL_ELEMENT_QUERY__MODEL_ELEMENTS:
			getModelElements().clear();
			getModelElements().addAll((Collection<? extends ModelElementId>) newValue);
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
		case VersioningPackage.MODEL_ELEMENT_QUERY__MODEL_ELEMENTS:
			getModelElements().clear();
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
		case VersioningPackage.MODEL_ELEMENT_QUERY__MODEL_ELEMENTS:
			return modelElements != null && !modelElements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public ESModelElementQueryImpl createAPI() {
		return new ESModelElementQueryImpl(this);
	}
} // ModelElementQueryImpl