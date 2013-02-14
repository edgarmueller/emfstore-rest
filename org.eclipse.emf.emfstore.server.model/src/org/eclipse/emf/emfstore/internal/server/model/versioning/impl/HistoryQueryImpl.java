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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>History Query</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.impl.HistoryQueryImpl#getSource <em>Source</em>}
 * </li>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.impl.HistoryQueryImpl#isIncludeChangePackages
 * <em>Include Change Packages</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.impl.HistoryQueryImpl#isIncludeAllVersions <em>
 * Include All Versions</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class HistoryQueryImpl extends EObjectImpl implements HistoryQuery {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected PrimaryVersionSpec source;

	/**
	 * The default value of the '{@link #isIncludeChangePackages() <em>Include Change Packages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isIncludeChangePackages()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INCLUDE_CHANGE_PACKAGES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIncludeChangePackages() <em>Include Change Packages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isIncludeChangePackages()
	 * @generated
	 * @ordered
	 */
	protected boolean includeChangePackages = INCLUDE_CHANGE_PACKAGES_EDEFAULT;

	/**
	 * The default value of the '{@link #isIncludeAllVersions() <em>Include All Versions</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isIncludeAllVersions()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INCLUDE_ALL_VERSIONS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIncludeAllVersions() <em>Include All Versions</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isIncludeAllVersions()
	 * @generated
	 * @ordered
	 */
	protected boolean includeAllVersions = INCLUDE_ALL_VERSIONS_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected HistoryQueryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VersioningPackage.Literals.HISTORY_QUERY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PrimaryVersionSpec getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject) source;
			source = (PrimaryVersionSpec) eResolveProxy(oldSource);
			if (source != oldSource) {
				InternalEObject newSource = (InternalEObject) source;
				NotificationChain msgs = oldSource.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.HISTORY_QUERY__SOURCE, null, null);
				if (newSource.eInternalContainer() == null) {
					msgs = newSource.eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - VersioningPackage.HISTORY_QUERY__SOURCE, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersioningPackage.HISTORY_QUERY__SOURCE,
						oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PrimaryVersionSpec basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetSource(PrimaryVersionSpec newSource, NotificationChain msgs) {
		PrimaryVersionSpec oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VersioningPackage.HISTORY_QUERY__SOURCE, oldSource, newSource);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.model.ESHistoryQuery.query.IHistoryQuery#setSource(org.eclipse.emf.emfstore.internal.server.model.api.versionspecs.IPrimaryVersionSpec)
	 * @generated NOT
	 */
	public void setSource(IPrimaryVersionSpec newSource) {
		setSource((PrimaryVersionSpec) newSource);
	}

	/**
	 * .
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSource(PrimaryVersionSpec newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject) source).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.HISTORY_QUERY__SOURCE, null, msgs);
			if (newSource != null)
				msgs = ((InternalEObject) newSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.HISTORY_QUERY__SOURCE, null, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.HISTORY_QUERY__SOURCE, newSource,
				newSource));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isIncludeChangePackages() {
		return includeChangePackages;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setIncludeChangePackages(boolean newIncludeChangePackages) {
		boolean oldIncludeChangePackages = includeChangePackages;
		includeChangePackages = newIncludeChangePackages;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				VersioningPackage.HISTORY_QUERY__INCLUDE_CHANGE_PACKAGES, oldIncludeChangePackages,
				includeChangePackages));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isIncludeAllVersions() {
		return includeAllVersions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setIncludeAllVersions(boolean newIncludeAllVersions) {
		boolean oldIncludeAllVersions = includeAllVersions;
		includeAllVersions = newIncludeAllVersions;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				VersioningPackage.HISTORY_QUERY__INCLUDE_ALL_VERSIONS, oldIncludeAllVersions, includeAllVersions));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VersioningPackage.HISTORY_QUERY__SOURCE:
			return basicSetSource(null, msgs);
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
		case VersioningPackage.HISTORY_QUERY__SOURCE:
			if (resolve)
				return getSource();
			return basicGetSource();
		case VersioningPackage.HISTORY_QUERY__INCLUDE_CHANGE_PACKAGES:
			return isIncludeChangePackages();
		case VersioningPackage.HISTORY_QUERY__INCLUDE_ALL_VERSIONS:
			return isIncludeAllVersions();
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
		case VersioningPackage.HISTORY_QUERY__SOURCE:
			setSource((PrimaryVersionSpec) newValue);
			return;
		case VersioningPackage.HISTORY_QUERY__INCLUDE_CHANGE_PACKAGES:
			setIncludeChangePackages((Boolean) newValue);
			return;
		case VersioningPackage.HISTORY_QUERY__INCLUDE_ALL_VERSIONS:
			setIncludeAllVersions((Boolean) newValue);
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
		case VersioningPackage.HISTORY_QUERY__SOURCE:
			setSource((PrimaryVersionSpec) null);
			return;
		case VersioningPackage.HISTORY_QUERY__INCLUDE_CHANGE_PACKAGES:
			setIncludeChangePackages(INCLUDE_CHANGE_PACKAGES_EDEFAULT);
			return;
		case VersioningPackage.HISTORY_QUERY__INCLUDE_ALL_VERSIONS:
			setIncludeAllVersions(INCLUDE_ALL_VERSIONS_EDEFAULT);
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
		case VersioningPackage.HISTORY_QUERY__SOURCE:
			return source != null;
		case VersioningPackage.HISTORY_QUERY__INCLUDE_CHANGE_PACKAGES:
			return includeChangePackages != INCLUDE_CHANGE_PACKAGES_EDEFAULT;
		case VersioningPackage.HISTORY_QUERY__INCLUDE_ALL_VERSIONS:
			return includeAllVersions != INCLUDE_ALL_VERSIONS_EDEFAULT;
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
		result.append(" (includeChangePackages: ");
		result.append(includeChangePackages);
		result.append(", includeAllVersions: ");
		result.append(includeAllVersions);
		result.append(')');
		return result.toString();
	}

} // HistoryQueryImpl