/**
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.bowling.impl;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Referee;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Referee</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.RefereeImpl#getDateOfBirth <em>Date Of Birth</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.RefereeImpl#getLeague <em>League</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class RefereeImpl extends EObjectImpl implements Referee {
	/**
	 * The default value of the '{@link #getDateOfBirth() <em>Date Of Birth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDateOfBirth()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar DATE_OF_BIRTH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getDateOfBirth() <em>Date Of Birth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDateOfBirth()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar dateOfBirth = DATE_OF_BIRTH_EDEFAULT;
	/**
	 * The cached value of the '{@link #getLeague() <em>League</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLeague()
	 * @generated
	 * @ordered
	 */
	protected League league;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected RefereeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BowlingPackage.Literals.REFEREE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public XMLGregorianCalendar getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDateOfBirth(XMLGregorianCalendar newDateOfBirth) {
		XMLGregorianCalendar oldDateOfBirth = dateOfBirth;
		dateOfBirth = newDateOfBirth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.REFEREE__DATE_OF_BIRTH,
				oldDateOfBirth, dateOfBirth));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public League getLeague() {
		if (league != null && league.eIsProxy()) {
			InternalEObject oldLeague = (InternalEObject) league;
			league = (League) eResolveProxy(oldLeague);
			if (league != oldLeague) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BowlingPackage.REFEREE__LEAGUE,
						oldLeague, league));
			}
		}
		return league;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public League basicGetLeague() {
		return league;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLeague(League newLeague) {
		League oldLeague = league;
		league = newLeague;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.REFEREE__LEAGUE, oldLeague, league));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BowlingPackage.REFEREE__DATE_OF_BIRTH:
			return getDateOfBirth();
		case BowlingPackage.REFEREE__LEAGUE:
			if (resolve)
				return getLeague();
			return basicGetLeague();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case BowlingPackage.REFEREE__DATE_OF_BIRTH:
			setDateOfBirth((XMLGregorianCalendar) newValue);
			return;
		case BowlingPackage.REFEREE__LEAGUE:
			setLeague((League) newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
		case BowlingPackage.REFEREE__DATE_OF_BIRTH:
			setDateOfBirth(DATE_OF_BIRTH_EDEFAULT);
			return;
		case BowlingPackage.REFEREE__LEAGUE:
			setLeague((League) null);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case BowlingPackage.REFEREE__DATE_OF_BIRTH:
			return DATE_OF_BIRTH_EDEFAULT == null ? dateOfBirth != null : !DATE_OF_BIRTH_EDEFAULT.equals(dateOfBirth);
		case BowlingPackage.REFEREE__LEAGUE:
			return league != null;
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
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (dateOfBirth: ");
		result.append(dateOfBirth);
		result.append(')');
		return result.toString();
	}

} // RefereeImpl