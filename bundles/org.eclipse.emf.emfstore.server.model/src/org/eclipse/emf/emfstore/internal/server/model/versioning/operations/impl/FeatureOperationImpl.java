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
package org.eclipse.emf.emfstore.internal.server.model.versioning.operations.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.FeatureOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.OperationsPackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.UnkownFeatureException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.UnsetType;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Feature Operation</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.operations.impl.FeatureOperationImpl#getFeatureName
 * <em>Feature Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class FeatureOperationImpl extends AbstractOperationImpl implements FeatureOperation {

	@Override
	protected void reverse(AbstractOperation abstractOperation) {
		super.reverse(abstractOperation);
		if (!(abstractOperation instanceof FeatureOperation)) {
			throw new IllegalArgumentException("Given operation is not a feature operation.");
		}
		FeatureOperation featureOperation = (FeatureOperation) abstractOperation;
		featureOperation.setFeatureName(getFeatureName());
	}

	/**
	 * The default value of the '{@link #getFeatureName() <em>Feature Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFeatureName()
	 * @generated
	 * @ordered
	 */
	protected static final String FEATURE_NAME_EDEFAULT = "";
	/**
	 * The cached value of the '{@link #getFeatureName() <em>Feature Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFeatureName()
	 * @generated
	 * @ordered
	 */
	protected String featureName = FEATURE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getUnset() <em>Unset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getUnset()
	 * @generated
	 * @ordered
	 */
	protected static final UnsetType UNSET_EDEFAULT = UnsetType.NONE;
	/**
	 * The cached value of the '{@link #getUnset() <em>Unset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getUnset()
	 * @generated
	 * @ordered
	 */
	protected UnsetType unset = UNSET_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected FeatureOperationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationsPackage.Literals.FEATURE_OPERATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFeatureName(String newFeatureName) {
		String oldFeatureName = featureName;
		featureName = newFeatureName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OperationsPackage.FEATURE_OPERATION__FEATURE_NAME,
				oldFeatureName, featureName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public UnsetType getUnset()
	{
		return unset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setUnset(UnsetType newUnset)
	{
		UnsetType oldUnset = unset;
		unset = newUnset == null ? UNSET_EDEFAULT : newUnset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OperationsPackage.FEATURE_OPERATION__UNSET, oldUnset,
				unset));
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
		case OperationsPackage.FEATURE_OPERATION__FEATURE_NAME:
			return getFeatureName();
		case OperationsPackage.FEATURE_OPERATION__UNSET:
			return getUnset();
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
		switch (featureID)
		{
		case OperationsPackage.FEATURE_OPERATION__FEATURE_NAME:
			setFeatureName((String) newValue);
			return;
		case OperationsPackage.FEATURE_OPERATION__UNSET:
			setUnset((UnsetType) newValue);
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
		case OperationsPackage.FEATURE_OPERATION__FEATURE_NAME:
			setFeatureName(FEATURE_NAME_EDEFAULT);
			return;
		case OperationsPackage.FEATURE_OPERATION__UNSET:
			setUnset(UNSET_EDEFAULT);
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
		case OperationsPackage.FEATURE_OPERATION__FEATURE_NAME:
			return FEATURE_NAME_EDEFAULT == null ? featureName != null : !FEATURE_NAME_EDEFAULT.equals(featureName);
		case OperationsPackage.FEATURE_OPERATION__UNSET:
			return unset != UNSET_EDEFAULT;
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
		result.append(" (featureName: ");
		result.append(featureName);
		result.append(", unset: ");
		result.append(unset);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.operations.FeatureOperation#getFeature(org.eclipse.emf.emfstore.internal.common.model.Project)
	 * @generated NOT
	 */
	public EStructuralFeature getFeature(Project project) throws UnkownFeatureException {
		EObject modelElement = project.getModelElement(getModelElementId());
		if (modelElement == null) {
			throw new IllegalArgumentException("Model Element is not in the given project");
		}
		return getFeature(modelElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.operations.impl.AbstractOperationImpl#reverse()
	 */
	@Override
	public AbstractOperation reverse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @generated NOT
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation#getLeafOperations()
	 */
	public List<AbstractOperation> getLeafOperations() {
		List<AbstractOperation> result = new ArrayList<AbstractOperation>();
		result.add(this);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @generated NOT
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.operations.FeatureOperation#getFeature(org.eclipse.emf.emfstore.internal.common.model.ModelElement)
	 */
	public EStructuralFeature getFeature(EObject modelElement) throws UnkownFeatureException {
		EList<EStructuralFeature> features = modelElement.eClass().getEAllStructuralFeatures();
		for (EStructuralFeature feature : features) {
			if (feature.getName().equals(this.getFeatureName())) {
				return feature;
			}
		}
		throw new UnkownFeatureException(modelElement.eClass(), getFeatureName());
	}

	/**
	 * Sets the unset field of the reverse operation accordingly.
	 * 
	 * @param operation The reverse operation
	 * @generated NOT
	 */
	protected void setUnsetForReverseOperation(FeatureOperation operation) {
		switch (getUnset().getValue()) {
		case UnsetType.IS_UNSET_VALUE:
			operation.setUnset(UnsetType.WAS_UNSET);
			break;
		case UnsetType.NONE_VALUE:
			operation.setUnset(UnsetType.NONE);
			break;
		case UnsetType.WAS_UNSET_VALUE:
			operation.setUnset(UnsetType.IS_UNSET);
			break;
		}
	}

} // FeatureOperationImpl