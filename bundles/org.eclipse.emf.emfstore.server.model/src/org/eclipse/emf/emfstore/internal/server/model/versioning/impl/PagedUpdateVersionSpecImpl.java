/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.internal.server.model.versioning.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPagedUpdateVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paged Update Version Spec</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.impl.PagedUpdateVersionSpecImpl#getMaxChanges
 * <em>Max Changes</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.impl.PagedUpdateVersionSpecImpl#getBaseVersionSpec
 * <em>Base Version Spec</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class PagedUpdateVersionSpecImpl extends VersionSpecImpl implements PagedUpdateVersionSpec
{
	/**
	 * @generated NOT
	 */
	private ESPagedUpdateVersionSpecImpl apiImpl;

	/**
	 * The default value of the '{@link #getMaxChanges() <em>Max Changes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMaxChanges()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_CHANGES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxChanges() <em>Max Changes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMaxChanges()
	 * @generated
	 * @ordered
	 */
	protected int maxChanges = MAX_CHANGES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBaseVersionSpec() <em>Base Version Spec</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getBaseVersionSpec()
	 * @generated
	 * @ordered
	 */
	protected PrimaryVersionSpec baseVersionSpec;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PagedUpdateVersionSpecImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return VersioningPackage.Literals.PAGED_UPDATE_VERSION_SPEC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getMaxChanges()
	{
		return maxChanges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setMaxChanges(int newMaxChanges)
	{
		int oldMaxChanges = maxChanges;
		maxChanges = newMaxChanges;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				VersioningPackage.PAGED_UPDATE_VERSION_SPEC__MAX_CHANGES, oldMaxChanges, maxChanges));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PrimaryVersionSpec getBaseVersionSpec()
	{
		if (baseVersionSpec != null && baseVersionSpec.eIsProxy())
		{
			InternalEObject oldBaseVersionSpec = (InternalEObject) baseVersionSpec;
			baseVersionSpec = (PrimaryVersionSpec) eResolveProxy(oldBaseVersionSpec);
			if (baseVersionSpec != oldBaseVersionSpec)
			{
				InternalEObject newBaseVersionSpec = (InternalEObject) baseVersionSpec;
				NotificationChain msgs = oldBaseVersionSpec.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC, null, null);
				if (newBaseVersionSpec.eInternalContainer() == null)
				{
					msgs = newBaseVersionSpec.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC, oldBaseVersionSpec,
						baseVersionSpec));
			}
		}
		return baseVersionSpec;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PrimaryVersionSpec basicGetBaseVersionSpec()
	{
		return baseVersionSpec;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBaseVersionSpec(PrimaryVersionSpec newBaseVersionSpec, NotificationChain msgs)
	{
		PrimaryVersionSpec oldBaseVersionSpec = baseVersionSpec;
		baseVersionSpec = newBaseVersionSpec;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC, oldBaseVersionSpec, newBaseVersionSpec);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setBaseVersionSpec(PrimaryVersionSpec newBaseVersionSpec)
	{
		if (newBaseVersionSpec != baseVersionSpec)
		{
			NotificationChain msgs = null;
			if (baseVersionSpec != null)
				msgs = ((InternalEObject) baseVersionSpec).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC, null, msgs);
			if (newBaseVersionSpec != null)
				msgs = ((InternalEObject) newBaseVersionSpec).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC, null, msgs);
			msgs = basicSetBaseVersionSpec(newBaseVersionSpec, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC, newBaseVersionSpec, newBaseVersionSpec));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
		case VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC:
			return basicSetBaseVersionSpec(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
		case VersioningPackage.PAGED_UPDATE_VERSION_SPEC__MAX_CHANGES:
			return getMaxChanges();
		case VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC:
			if (resolve)
				return getBaseVersionSpec();
			return basicGetBaseVersionSpec();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
		case VersioningPackage.PAGED_UPDATE_VERSION_SPEC__MAX_CHANGES:
			setMaxChanges((Integer) newValue);
			return;
		case VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC:
			setBaseVersionSpec((PrimaryVersionSpec) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
		case VersioningPackage.PAGED_UPDATE_VERSION_SPEC__MAX_CHANGES:
			setMaxChanges(MAX_CHANGES_EDEFAULT);
			return;
		case VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC:
			setBaseVersionSpec((PrimaryVersionSpec) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
		case VersioningPackage.PAGED_UPDATE_VERSION_SPEC__MAX_CHANGES:
			return maxChanges != MAX_CHANGES_EDEFAULT;
		case VersioningPackage.PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC:
			return baseVersionSpec != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (maxChanges: ");
		result.append(maxChanges);
		result.append(')');
		return result.toString();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#toAPI()
	 * 
	 * @generated NOT
	 */
	public ESPagedUpdateVersionSpecImpl toAPI() {

		if (apiImpl == null) {
			apiImpl = createAPI();
		}

		return apiImpl;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#createAPI()
	 * 
	 * @generated NOT
	 */
	public ESPagedUpdateVersionSpecImpl createAPI() {
		return new ESPagedUpdateVersionSpecImpl(this);
	}
} // PagedUpdateVersionSpecImpl
