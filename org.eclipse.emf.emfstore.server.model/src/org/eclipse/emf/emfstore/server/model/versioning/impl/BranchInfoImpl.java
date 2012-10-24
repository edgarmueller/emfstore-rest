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
package org.eclipse.emf.emfstore.server.model.versioning.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Branch Info</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.impl.BranchInfoImpl#getName
 * <em>Name</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.impl.BranchInfoImpl#getHead
 * <em>Head</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.impl.BranchInfoImpl#getSource
 * <em>Source</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class BranchInfoImpl extends EObjectImpl implements BranchInfo {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getHead() <em>Head</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getHead()
	 * @generated
	 * @ordered
	 */
	protected PrimaryVersionSpec head;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected PrimaryVersionSpec source;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected BranchInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VersioningPackage.Literals.BRANCH_INFO;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					VersioningPackage.BRANCH_INFO__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PrimaryVersionSpec getHead() {
		if (head != null && head.eIsProxy()) {
			InternalEObject oldHead = (InternalEObject) head;
			head = (PrimaryVersionSpec) eResolveProxy(oldHead);
			if (head != oldHead) {
				InternalEObject newHead = (InternalEObject) head;
				NotificationChain msgs = oldHead.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- VersioningPackage.BRANCH_INFO__HEAD, null,
						null);
				if (newHead.eInternalContainer() == null) {
					msgs = newHead.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
							- VersioningPackage.BRANCH_INFO__HEAD, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							VersioningPackage.BRANCH_INFO__HEAD, oldHead, head));
			}
		}
		return head;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PrimaryVersionSpec basicGetHead() {
		return head;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetHead(PrimaryVersionSpec newHead,
			NotificationChain msgs) {
		PrimaryVersionSpec oldHead = head;
		head = newHead;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, VersioningPackage.BRANCH_INFO__HEAD,
					oldHead, newHead);
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
	public void setHead(PrimaryVersionSpec newHead) {
		if (newHead != head) {
			NotificationChain msgs = null;
			if (head != null)
				msgs = ((InternalEObject) head).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- VersioningPackage.BRANCH_INFO__HEAD, null,
						msgs);
			if (newHead != null)
				msgs = ((InternalEObject) newHead).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- VersioningPackage.BRANCH_INFO__HEAD, null,
						msgs);
			msgs = basicSetHead(newHead, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					VersioningPackage.BRANCH_INFO__HEAD, newHead, newHead));
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
				NotificationChain msgs = oldSource.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- VersioningPackage.BRANCH_INFO__SOURCE, null,
						null);
				if (newSource.eInternalContainer() == null) {
					msgs = newSource
							.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
									- VersioningPackage.BRANCH_INFO__SOURCE,
									null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							VersioningPackage.BRANCH_INFO__SOURCE, oldSource,
							source));
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
	public NotificationChain basicSetSource(PrimaryVersionSpec newSource,
			NotificationChain msgs) {
		PrimaryVersionSpec oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, VersioningPackage.BRANCH_INFO__SOURCE,
					oldSource, newSource);
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
	public void setSource(PrimaryVersionSpec newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject) source).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- VersioningPackage.BRANCH_INFO__SOURCE, null,
						msgs);
			if (newSource != null)
				msgs = ((InternalEObject) newSource).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- VersioningPackage.BRANCH_INFO__SOURCE, null,
						msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					VersioningPackage.BRANCH_INFO__SOURCE, newSource, newSource));
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
		case VersioningPackage.BRANCH_INFO__HEAD:
			return basicSetHead(null, msgs);
		case VersioningPackage.BRANCH_INFO__SOURCE:
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
		case VersioningPackage.BRANCH_INFO__NAME:
			return getName();
		case VersioningPackage.BRANCH_INFO__HEAD:
			if (resolve)
				return getHead();
			return basicGetHead();
		case VersioningPackage.BRANCH_INFO__SOURCE:
			if (resolve)
				return getSource();
			return basicGetSource();
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
		case VersioningPackage.BRANCH_INFO__NAME:
			setName((String) newValue);
			return;
		case VersioningPackage.BRANCH_INFO__HEAD:
			setHead((PrimaryVersionSpec) newValue);
			return;
		case VersioningPackage.BRANCH_INFO__SOURCE:
			setSource((PrimaryVersionSpec) newValue);
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
		case VersioningPackage.BRANCH_INFO__NAME:
			setName(NAME_EDEFAULT);
			return;
		case VersioningPackage.BRANCH_INFO__HEAD:
			setHead((PrimaryVersionSpec) null);
			return;
		case VersioningPackage.BRANCH_INFO__SOURCE:
			setSource((PrimaryVersionSpec) null);
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
		case VersioningPackage.BRANCH_INFO__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case VersioningPackage.BRANCH_INFO__HEAD:
			return head != null;
		case VersioningPackage.BRANCH_INFO__SOURCE:
			return source != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // BranchInfoImpl