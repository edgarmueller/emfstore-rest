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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Gender;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.TournamentType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Player</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl#getDateOfBirth <em>Date Of Birth</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl#isIsProfessional <em>Is Professional</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl#getEMails <em>EMails</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl#getNumberOfVictories <em>Number Of Victories</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl#getPlayedTournamentTypes <em>Played Tournament Types</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl#getWinLossRatio <em>Win Loss Ratio</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl#getGender <em>Gender</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PlayerImpl extends EObjectImpl implements Player {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDateOfBirth() <em>Date Of Birth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateOfBirth()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_OF_BIRTH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDateOfBirth() <em>Date Of Birth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateOfBirth()
	 * @generated
	 * @ordered
	 */
	protected Date dateOfBirth = DATE_OF_BIRTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final double HEIGHT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected double height = HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsProfessional() <em>Is Professional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsProfessional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_PROFESSIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsProfessional() <em>Is Professional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsProfessional()
	 * @generated
	 * @ordered
	 */
	protected boolean isProfessional = IS_PROFESSIONAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEMails() <em>EMails</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEMails()
	 * @generated
	 * @ordered
	 */
	protected EList<String> eMails;

	/**
	 * The default value of the '{@link #getNumberOfVictories() <em>Number Of Victories</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfVictories()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_VICTORIES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfVictories() <em>Number Of Victories</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfVictories()
	 * @generated
	 * @ordered
	 */
	protected int numberOfVictories = NUMBER_OF_VICTORIES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPlayedTournamentTypes() <em>Played Tournament Types</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlayedTournamentTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<TournamentType> playedTournamentTypes;

	/**
	 * The default value of the '{@link #getWinLossRatio() <em>Win Loss Ratio</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWinLossRatio()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal WIN_LOSS_RATIO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWinLossRatio() <em>Win Loss Ratio</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWinLossRatio()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal winLossRatio = WIN_LOSS_RATIO_EDEFAULT;

	/**
	 * The default value of the '{@link #getGender() <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGender()
	 * @generated
	 * @ordered
	 */
	protected static final Gender GENDER_EDEFAULT = Gender.FEMALE;

	/**
	 * The cached value of the '{@link #getGender() <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGender()
	 * @generated
	 * @ordered
	 */
	protected Gender gender = GENDER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlayerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BowlingPackage.Literals.PLAYER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.PLAYER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDateOfBirth(Date newDateOfBirth) {
		Date oldDateOfBirth = dateOfBirth;
		dateOfBirth = newDateOfBirth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.PLAYER__DATE_OF_BIRTH, oldDateOfBirth, dateOfBirth));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeight(double newHeight) {
		double oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.PLAYER__HEIGHT, oldHeight, height));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsProfessional() {
		return isProfessional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsProfessional(boolean newIsProfessional) {
		boolean oldIsProfessional = isProfessional;
		isProfessional = newIsProfessional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.PLAYER__IS_PROFESSIONAL, oldIsProfessional, isProfessional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getEMails() {
		if (eMails == null) {
			eMails = new EDataTypeUniqueEList<String>(String.class, this, BowlingPackage.PLAYER__EMAILS);
		}
		return eMails;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfVictories() {
		return numberOfVictories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfVictories(int newNumberOfVictories) {
		int oldNumberOfVictories = numberOfVictories;
		numberOfVictories = newNumberOfVictories;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.PLAYER__NUMBER_OF_VICTORIES, oldNumberOfVictories, numberOfVictories));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TournamentType> getPlayedTournamentTypes() {
		if (playedTournamentTypes == null) {
			playedTournamentTypes = new EDataTypeUniqueEList<TournamentType>(TournamentType.class, this, BowlingPackage.PLAYER__PLAYED_TOURNAMENT_TYPES);
		}
		return playedTournamentTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigDecimal getWinLossRatio() {
		return winLossRatio;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWinLossRatio(BigDecimal newWinLossRatio) {
		BigDecimal oldWinLossRatio = winLossRatio;
		winLossRatio = newWinLossRatio;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.PLAYER__WIN_LOSS_RATIO, oldWinLossRatio, winLossRatio));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGender(Gender newGender) {
		Gender oldGender = gender;
		gender = newGender == null ? GENDER_EDEFAULT : newGender;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.PLAYER__GENDER, oldGender, gender));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BowlingPackage.PLAYER__NAME:
				return getName();
			case BowlingPackage.PLAYER__DATE_OF_BIRTH:
				return getDateOfBirth();
			case BowlingPackage.PLAYER__HEIGHT:
				return getHeight();
			case BowlingPackage.PLAYER__IS_PROFESSIONAL:
				return isIsProfessional();
			case BowlingPackage.PLAYER__EMAILS:
				return getEMails();
			case BowlingPackage.PLAYER__NUMBER_OF_VICTORIES:
				return getNumberOfVictories();
			case BowlingPackage.PLAYER__PLAYED_TOURNAMENT_TYPES:
				return getPlayedTournamentTypes();
			case BowlingPackage.PLAYER__WIN_LOSS_RATIO:
				return getWinLossRatio();
			case BowlingPackage.PLAYER__GENDER:
				return getGender();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BowlingPackage.PLAYER__NAME:
				setName((String)newValue);
				return;
			case BowlingPackage.PLAYER__DATE_OF_BIRTH:
				setDateOfBirth((Date)newValue);
				return;
			case BowlingPackage.PLAYER__HEIGHT:
				setHeight((Double)newValue);
				return;
			case BowlingPackage.PLAYER__IS_PROFESSIONAL:
				setIsProfessional((Boolean)newValue);
				return;
			case BowlingPackage.PLAYER__EMAILS:
				getEMails().clear();
				getEMails().addAll((Collection<? extends String>)newValue);
				return;
			case BowlingPackage.PLAYER__NUMBER_OF_VICTORIES:
				setNumberOfVictories((Integer)newValue);
				return;
			case BowlingPackage.PLAYER__PLAYED_TOURNAMENT_TYPES:
				getPlayedTournamentTypes().clear();
				getPlayedTournamentTypes().addAll((Collection<? extends TournamentType>)newValue);
				return;
			case BowlingPackage.PLAYER__WIN_LOSS_RATIO:
				setWinLossRatio((BigDecimal)newValue);
				return;
			case BowlingPackage.PLAYER__GENDER:
				setGender((Gender)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BowlingPackage.PLAYER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BowlingPackage.PLAYER__DATE_OF_BIRTH:
				setDateOfBirth(DATE_OF_BIRTH_EDEFAULT);
				return;
			case BowlingPackage.PLAYER__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
			case BowlingPackage.PLAYER__IS_PROFESSIONAL:
				setIsProfessional(IS_PROFESSIONAL_EDEFAULT);
				return;
			case BowlingPackage.PLAYER__EMAILS:
				getEMails().clear();
				return;
			case BowlingPackage.PLAYER__NUMBER_OF_VICTORIES:
				setNumberOfVictories(NUMBER_OF_VICTORIES_EDEFAULT);
				return;
			case BowlingPackage.PLAYER__PLAYED_TOURNAMENT_TYPES:
				getPlayedTournamentTypes().clear();
				return;
			case BowlingPackage.PLAYER__WIN_LOSS_RATIO:
				setWinLossRatio(WIN_LOSS_RATIO_EDEFAULT);
				return;
			case BowlingPackage.PLAYER__GENDER:
				setGender(GENDER_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BowlingPackage.PLAYER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BowlingPackage.PLAYER__DATE_OF_BIRTH:
				return DATE_OF_BIRTH_EDEFAULT == null ? dateOfBirth != null : !DATE_OF_BIRTH_EDEFAULT.equals(dateOfBirth);
			case BowlingPackage.PLAYER__HEIGHT:
				return height != HEIGHT_EDEFAULT;
			case BowlingPackage.PLAYER__IS_PROFESSIONAL:
				return isProfessional != IS_PROFESSIONAL_EDEFAULT;
			case BowlingPackage.PLAYER__EMAILS:
				return eMails != null && !eMails.isEmpty();
			case BowlingPackage.PLAYER__NUMBER_OF_VICTORIES:
				return numberOfVictories != NUMBER_OF_VICTORIES_EDEFAULT;
			case BowlingPackage.PLAYER__PLAYED_TOURNAMENT_TYPES:
				return playedTournamentTypes != null && !playedTournamentTypes.isEmpty();
			case BowlingPackage.PLAYER__WIN_LOSS_RATIO:
				return WIN_LOSS_RATIO_EDEFAULT == null ? winLossRatio != null : !WIN_LOSS_RATIO_EDEFAULT.equals(winLossRatio);
			case BowlingPackage.PLAYER__GENDER:
				return gender != GENDER_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", dateOfBirth: ");
		result.append(dateOfBirth);
		result.append(", height: ");
		result.append(height);
		result.append(", isProfessional: ");
		result.append(isProfessional);
		result.append(", eMails: ");
		result.append(eMails);
		result.append(", numberOfVictories: ");
		result.append(numberOfVictories);
		result.append(", playedTournamentTypes: ");
		result.append(playedTournamentTypes);
		result.append(", winLossRatio: ");
		result.append(winLossRatio);
		result.append(", gender: ");
		result.append(gender);
		result.append(')');
		return result.toString();
	}

} // PlayerImpl