/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.versioning.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Primary Version Spec</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.impl.PrimaryVersionSpecImpl#getIdentifier <em>
 * Identifier</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.internal.server.model.versioning.impl.PrimaryVersionSpecImpl#getProjectStateChecksum
 * <em>Project State Checksum</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class PrimaryVersionSpecImpl extends VersionSpecImpl implements PrimaryVersionSpec {

	/**
	 * @generated NOT
	 */
	private ESPrimaryVersionSpecImpl apiImpl;

	/**
	 * The default value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getIdentifier()
	 * @generated
	 * @ordered
	 */
	protected static final int IDENTIFIER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getIdentifier()
	 * @generated
	 * @ordered
	 */
	protected int identifier = IDENTIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectStateChecksum() <em>Project State Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getProjectStateChecksum()
	 * @generated
	 * @ordered
	 */
	protected static final long PROJECT_STATE_CHECKSUM_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getProjectStateChecksum() <em>Project State Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getProjectStateChecksum()
	 * @generated
	 * @ordered
	 */
	protected long projectStateChecksum = PROJECT_STATE_CHECKSUM_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PrimaryVersionSpecImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VersioningPackage.Literals.PRIMARY_VERSION_SPEC;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setIdentifier(int newIdentifier) {
		int oldIdentifier = identifier;
		identifier = newIdentifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.PRIMARY_VERSION_SPEC__IDENTIFIER,
				oldIdentifier, identifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public long getProjectStateChecksum() {
		return projectStateChecksum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setProjectStateChecksum(long newProjectStateChecksum) {
		long oldProjectStateChecksum = projectStateChecksum;
		projectStateChecksum = newProjectStateChecksum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				VersioningPackage.PRIMARY_VERSION_SPEC__PROJECT_STATE_CHECKSUM, oldProjectStateChecksum,
				projectStateChecksum));
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
		case VersioningPackage.PRIMARY_VERSION_SPEC__IDENTIFIER:
			return getIdentifier();
		case VersioningPackage.PRIMARY_VERSION_SPEC__PROJECT_STATE_CHECKSUM:
			return getProjectStateChecksum();
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
		case VersioningPackage.PRIMARY_VERSION_SPEC__IDENTIFIER:
			setIdentifier((Integer) newValue);
			return;
		case VersioningPackage.PRIMARY_VERSION_SPEC__PROJECT_STATE_CHECKSUM:
			setProjectStateChecksum((Long) newValue);
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
		case VersioningPackage.PRIMARY_VERSION_SPEC__IDENTIFIER:
			setIdentifier(IDENTIFIER_EDEFAULT);
			return;
		case VersioningPackage.PRIMARY_VERSION_SPEC__PROJECT_STATE_CHECKSUM:
			setProjectStateChecksum(PROJECT_STATE_CHECKSUM_EDEFAULT);
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
		case VersioningPackage.PRIMARY_VERSION_SPEC__IDENTIFIER:
			return identifier != IDENTIFIER_EDEFAULT;
		case VersioningPackage.PRIMARY_VERSION_SPEC__PROJECT_STATE_CHECKSUM:
			return projectStateChecksum != PROJECT_STATE_CHECKSUM_EDEFAULT;
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
		result.append(" (identifier: ");
		result.append(identifier);
		result.append(", projectStateChecksum: ");
		result.append(projectStateChecksum);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof PrimaryVersionSpec) {
			PrimaryVersionSpec otherPrimaryVersionSpec = (PrimaryVersionSpec) object;
			return this.getIdentifier() == otherPrimaryVersionSpec.getIdentifier()
				&& (this.getBranch() != null && this.getBranch().equals(otherPrimaryVersionSpec.getBranch()));
		} else {
			return false;
		}
	}

	public int compareTo(PrimaryVersionSpec o) {
		if (this.getIdentifier() == o.getIdentifier()) {
			return 0;
		} else if (this.getIdentifier() < o.getIdentifier()) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#toAPI()
	 * 
	 * @generated NOT
	 */
	public ESPrimaryVersionSpecImpl toAPI() {

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
	public ESPrimaryVersionSpecImpl createAPI() {
		return new ESPrimaryVersionSpecImpl(this);
	}

} // PrimaryVersionSpecImpl
