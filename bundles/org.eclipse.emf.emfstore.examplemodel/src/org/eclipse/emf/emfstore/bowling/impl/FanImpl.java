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
package org.eclipse.emf.emfstore.bowling.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.bowling.Gender;
import org.eclipse.emf.emfstore.bowling.Merchandise;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Tournament;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Fan</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#getDateOfBirth <em>Date Of Birth</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#isHasSeasonTicket <em>Has Season Ticket</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#getEMails <em>EMails</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#getGender <em>Gender</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#getFavouritePlayer <em>Favourite Player</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#getFanMerchandise <em>Fan Merchandise</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#getFavouriteMerchandise <em>Favourite Merchandise</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#getVisitedTournaments <em>Visited Tournaments</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#getNumberOfTournamentsVisited <em>Number Of Tournaments Visited</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl#getMoneySpentOnTickets <em>Money Spent On Tickets</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FanImpl extends EObjectImpl implements Fan {
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
	 * This is true if the Name attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean nameESet;

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
	 * This is true if the Date Of Birth attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean dateOfBirthESet;

	/**
	 * The default value of the '{@link #isHasSeasonTicket() <em>Has Season Ticket</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasSeasonTicket()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_SEASON_TICKET_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasSeasonTicket() <em>Has Season Ticket</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasSeasonTicket()
	 * @generated
	 * @ordered
	 */
	protected boolean hasSeasonTicket = HAS_SEASON_TICKET_EDEFAULT;

	/**
	 * This is true if the Has Season Ticket attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean hasSeasonTicketESet;

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
	 * This is true if the Gender attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean genderESet;

	/**
	 * The cached value of the '{@link #getFavouritePlayer() <em>Favourite Player</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFavouritePlayer()
	 * @generated
	 * @ordered
	 */
	protected Player favouritePlayer;

	/**
	 * This is true if the Favourite Player reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean favouritePlayerESet;

	/**
	 * The cached value of the '{@link #getFanMerchandise() <em>Fan Merchandise</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFanMerchandise()
	 * @generated
	 * @ordered
	 */
	protected EList<Merchandise> fanMerchandise;

	/**
	 * The cached value of the '{@link #getFavouriteMerchandise() <em>Favourite Merchandise</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFavouriteMerchandise()
	 * @generated
	 * @ordered
	 */
	protected Merchandise favouriteMerchandise;

	/**
	 * This is true if the Favourite Merchandise containment reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean favouriteMerchandiseESet;

	/**
	 * The cached value of the '{@link #getVisitedTournaments() <em>Visited Tournaments</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisitedTournaments()
	 * @generated
	 * @ordered
	 */
	protected EList<Tournament> visitedTournaments;

	/**
	 * The default value of the '{@link #getNumberOfTournamentsVisited() <em>Number Of Tournaments Visited</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfTournamentsVisited()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_TOURNAMENTS_VISITED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfTournamentsVisited() <em>Number Of Tournaments Visited</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfTournamentsVisited()
	 * @generated
	 * @ordered
	 */
	protected int numberOfTournamentsVisited = NUMBER_OF_TOURNAMENTS_VISITED_EDEFAULT;

	/**
	 * This is true if the Number Of Tournaments Visited attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean numberOfTournamentsVisitedESet;

	/**
	 * The default value of the '{@link #getMoneySpentOnTickets() <em>Money Spent On Tickets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMoneySpentOnTickets()
	 * @generated
	 * @ordered
	 */
	protected static final double MONEY_SPENT_ON_TICKETS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMoneySpentOnTickets() <em>Money Spent On Tickets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMoneySpentOnTickets()
	 * @generated
	 * @ordered
	 */
	protected double moneySpentOnTickets = MONEY_SPENT_ON_TICKETS_EDEFAULT;

	/**
	 * This is true if the Money Spent On Tickets attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean moneySpentOnTicketsESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FanImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BowlingPackage.Literals.FAN;
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
		boolean oldNameESet = nameESet;
		nameESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.FAN__NAME, oldName, name, !oldNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetName() {
		String oldName = name;
		boolean oldNameESet = nameESet;
		name = NAME_EDEFAULT;
		nameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BowlingPackage.FAN__NAME, oldName, NAME_EDEFAULT, oldNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetName() {
		return nameESet;
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
		boolean oldDateOfBirthESet = dateOfBirthESet;
		dateOfBirthESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.FAN__DATE_OF_BIRTH, oldDateOfBirth, dateOfBirth, !oldDateOfBirthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDateOfBirth() {
		Date oldDateOfBirth = dateOfBirth;
		boolean oldDateOfBirthESet = dateOfBirthESet;
		dateOfBirth = DATE_OF_BIRTH_EDEFAULT;
		dateOfBirthESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BowlingPackage.FAN__DATE_OF_BIRTH, oldDateOfBirth, DATE_OF_BIRTH_EDEFAULT, oldDateOfBirthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDateOfBirth() {
		return dateOfBirthESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasSeasonTicket() {
		return hasSeasonTicket;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasSeasonTicket(boolean newHasSeasonTicket) {
		boolean oldHasSeasonTicket = hasSeasonTicket;
		hasSeasonTicket = newHasSeasonTicket;
		boolean oldHasSeasonTicketESet = hasSeasonTicketESet;
		hasSeasonTicketESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.FAN__HAS_SEASON_TICKET, oldHasSeasonTicket, hasSeasonTicket, !oldHasSeasonTicketESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetHasSeasonTicket() {
		boolean oldHasSeasonTicket = hasSeasonTicket;
		boolean oldHasSeasonTicketESet = hasSeasonTicketESet;
		hasSeasonTicket = HAS_SEASON_TICKET_EDEFAULT;
		hasSeasonTicketESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BowlingPackage.FAN__HAS_SEASON_TICKET, oldHasSeasonTicket, HAS_SEASON_TICKET_EDEFAULT, oldHasSeasonTicketESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetHasSeasonTicket() {
		return hasSeasonTicketESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getEMails() {
		if (eMails == null) {
			eMails = new EDataTypeUniqueEList.Unsettable<String>(String.class, this, BowlingPackage.FAN__EMAILS);
		}
		return eMails;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetEMails() {
		if (eMails != null) ((InternalEList.Unsettable<?>)eMails).unset();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetEMails() {
		return eMails != null && ((InternalEList.Unsettable<?>)eMails).isSet();
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
		boolean oldGenderESet = genderESet;
		genderESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.FAN__GENDER, oldGender, gender, !oldGenderESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetGender() {
		Gender oldGender = gender;
		boolean oldGenderESet = genderESet;
		gender = GENDER_EDEFAULT;
		genderESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BowlingPackage.FAN__GENDER, oldGender, GENDER_EDEFAULT, oldGenderESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetGender() {
		return genderESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Player getFavouritePlayer() {
		if (favouritePlayer != null && favouritePlayer.eIsProxy()) {
			InternalEObject oldFavouritePlayer = (InternalEObject)favouritePlayer;
			favouritePlayer = (Player)eResolveProxy(oldFavouritePlayer);
			if (favouritePlayer != oldFavouritePlayer) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BowlingPackage.FAN__FAVOURITE_PLAYER, oldFavouritePlayer, favouritePlayer));
			}
		}
		return favouritePlayer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Player basicGetFavouritePlayer() {
		return favouritePlayer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFavouritePlayer(Player newFavouritePlayer) {
		Player oldFavouritePlayer = favouritePlayer;
		favouritePlayer = newFavouritePlayer;
		boolean oldFavouritePlayerESet = favouritePlayerESet;
		favouritePlayerESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.FAN__FAVOURITE_PLAYER, oldFavouritePlayer, favouritePlayer, !oldFavouritePlayerESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFavouritePlayer() {
		Player oldFavouritePlayer = favouritePlayer;
		boolean oldFavouritePlayerESet = favouritePlayerESet;
		favouritePlayer = null;
		favouritePlayerESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BowlingPackage.FAN__FAVOURITE_PLAYER, oldFavouritePlayer, null, oldFavouritePlayerESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFavouritePlayer() {
		return favouritePlayerESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Tournament> getVisitedTournaments() {
		if (visitedTournaments == null) {
			visitedTournaments = new EObjectResolvingEList.Unsettable<Tournament>(Tournament.class, this, BowlingPackage.FAN__VISITED_TOURNAMENTS);
		}
		return visitedTournaments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetVisitedTournaments() {
		if (visitedTournaments != null) ((InternalEList.Unsettable<?>)visitedTournaments).unset();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetVisitedTournaments() {
		return visitedTournaments != null && ((InternalEList.Unsettable<?>)visitedTournaments).isSet();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfTournamentsVisited() {
		return numberOfTournamentsVisited;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfTournamentsVisited(int newNumberOfTournamentsVisited) {
		int oldNumberOfTournamentsVisited = numberOfTournamentsVisited;
		numberOfTournamentsVisited = newNumberOfTournamentsVisited;
		boolean oldNumberOfTournamentsVisitedESet = numberOfTournamentsVisitedESet;
		numberOfTournamentsVisitedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.FAN__NUMBER_OF_TOURNAMENTS_VISITED, oldNumberOfTournamentsVisited, numberOfTournamentsVisited, !oldNumberOfTournamentsVisitedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetNumberOfTournamentsVisited() {
		int oldNumberOfTournamentsVisited = numberOfTournamentsVisited;
		boolean oldNumberOfTournamentsVisitedESet = numberOfTournamentsVisitedESet;
		numberOfTournamentsVisited = NUMBER_OF_TOURNAMENTS_VISITED_EDEFAULT;
		numberOfTournamentsVisitedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BowlingPackage.FAN__NUMBER_OF_TOURNAMENTS_VISITED, oldNumberOfTournamentsVisited, NUMBER_OF_TOURNAMENTS_VISITED_EDEFAULT, oldNumberOfTournamentsVisitedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetNumberOfTournamentsVisited() {
		return numberOfTournamentsVisitedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMoneySpentOnTickets() {
		return moneySpentOnTickets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMoneySpentOnTickets(double newMoneySpentOnTickets) {
		double oldMoneySpentOnTickets = moneySpentOnTickets;
		moneySpentOnTickets = newMoneySpentOnTickets;
		boolean oldMoneySpentOnTicketsESet = moneySpentOnTicketsESet;
		moneySpentOnTicketsESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.FAN__MONEY_SPENT_ON_TICKETS, oldMoneySpentOnTickets, moneySpentOnTickets, !oldMoneySpentOnTicketsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMoneySpentOnTickets() {
		double oldMoneySpentOnTickets = moneySpentOnTickets;
		boolean oldMoneySpentOnTicketsESet = moneySpentOnTicketsESet;
		moneySpentOnTickets = MONEY_SPENT_ON_TICKETS_EDEFAULT;
		moneySpentOnTicketsESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BowlingPackage.FAN__MONEY_SPENT_ON_TICKETS, oldMoneySpentOnTickets, MONEY_SPENT_ON_TICKETS_EDEFAULT, oldMoneySpentOnTicketsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMoneySpentOnTickets() {
		return moneySpentOnTicketsESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Merchandise> getFanMerchandise() {
		if (fanMerchandise == null) {
			fanMerchandise = new EObjectContainmentEList.Unsettable.Resolving<Merchandise>(Merchandise.class, this, BowlingPackage.FAN__FAN_MERCHANDISE);
		}
		return fanMerchandise;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFanMerchandise() {
		if (fanMerchandise != null) ((InternalEList.Unsettable<?>)fanMerchandise).unset();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFanMerchandise() {
		return fanMerchandise != null && ((InternalEList.Unsettable<?>)fanMerchandise).isSet();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Merchandise getFavouriteMerchandise() {
		if (favouriteMerchandise != null && favouriteMerchandise.eIsProxy()) {
			InternalEObject oldFavouriteMerchandise = (InternalEObject)favouriteMerchandise;
			favouriteMerchandise = (Merchandise)eResolveProxy(oldFavouriteMerchandise);
			if (favouriteMerchandise != oldFavouriteMerchandise) {
				InternalEObject newFavouriteMerchandise = (InternalEObject)favouriteMerchandise;
				NotificationChain msgs = oldFavouriteMerchandise.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BowlingPackage.FAN__FAVOURITE_MERCHANDISE, null, null);
				if (newFavouriteMerchandise.eInternalContainer() == null) {
					msgs = newFavouriteMerchandise.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BowlingPackage.FAN__FAVOURITE_MERCHANDISE, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BowlingPackage.FAN__FAVOURITE_MERCHANDISE, oldFavouriteMerchandise, favouriteMerchandise));
			}
		}
		return favouriteMerchandise;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Merchandise basicGetFavouriteMerchandise() {
		return favouriteMerchandise;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFavouriteMerchandise(Merchandise newFavouriteMerchandise, NotificationChain msgs) {
		Merchandise oldFavouriteMerchandise = favouriteMerchandise;
		favouriteMerchandise = newFavouriteMerchandise;
		boolean oldFavouriteMerchandiseESet = favouriteMerchandiseESet;
		favouriteMerchandiseESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BowlingPackage.FAN__FAVOURITE_MERCHANDISE, oldFavouriteMerchandise, newFavouriteMerchandise, !oldFavouriteMerchandiseESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFavouriteMerchandise(Merchandise newFavouriteMerchandise) {
		if (newFavouriteMerchandise != favouriteMerchandise) {
			NotificationChain msgs = null;
			if (favouriteMerchandise != null)
				msgs = ((InternalEObject)favouriteMerchandise).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BowlingPackage.FAN__FAVOURITE_MERCHANDISE, null, msgs);
			if (newFavouriteMerchandise != null)
				msgs = ((InternalEObject)newFavouriteMerchandise).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BowlingPackage.FAN__FAVOURITE_MERCHANDISE, null, msgs);
			msgs = basicSetFavouriteMerchandise(newFavouriteMerchandise, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldFavouriteMerchandiseESet = favouriteMerchandiseESet;
			favouriteMerchandiseESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.FAN__FAVOURITE_MERCHANDISE, newFavouriteMerchandise, newFavouriteMerchandise, !oldFavouriteMerchandiseESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicUnsetFavouriteMerchandise(NotificationChain msgs) {
		Merchandise oldFavouriteMerchandise = favouriteMerchandise;
		favouriteMerchandise = null;
		boolean oldFavouriteMerchandiseESet = favouriteMerchandiseESet;
		favouriteMerchandiseESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, BowlingPackage.FAN__FAVOURITE_MERCHANDISE, oldFavouriteMerchandise, null, oldFavouriteMerchandiseESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFavouriteMerchandise() {
		if (favouriteMerchandise != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)favouriteMerchandise).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BowlingPackage.FAN__FAVOURITE_MERCHANDISE, null, msgs);
			msgs = basicUnsetFavouriteMerchandise(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldFavouriteMerchandiseESet = favouriteMerchandiseESet;
			favouriteMerchandiseESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, BowlingPackage.FAN__FAVOURITE_MERCHANDISE, null, null, oldFavouriteMerchandiseESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFavouriteMerchandise() {
		return favouriteMerchandiseESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BowlingPackage.FAN__FAN_MERCHANDISE:
				return ((InternalEList<?>)getFanMerchandise()).basicRemove(otherEnd, msgs);
			case BowlingPackage.FAN__FAVOURITE_MERCHANDISE:
				return basicUnsetFavouriteMerchandise(msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BowlingPackage.FAN__NAME:
				return getName();
			case BowlingPackage.FAN__DATE_OF_BIRTH:
				return getDateOfBirth();
			case BowlingPackage.FAN__HAS_SEASON_TICKET:
				return isHasSeasonTicket();
			case BowlingPackage.FAN__EMAILS:
				return getEMails();
			case BowlingPackage.FAN__GENDER:
				return getGender();
			case BowlingPackage.FAN__FAVOURITE_PLAYER:
				if (resolve) return getFavouritePlayer();
				return basicGetFavouritePlayer();
			case BowlingPackage.FAN__FAN_MERCHANDISE:
				return getFanMerchandise();
			case BowlingPackage.FAN__FAVOURITE_MERCHANDISE:
				if (resolve) return getFavouriteMerchandise();
				return basicGetFavouriteMerchandise();
			case BowlingPackage.FAN__VISITED_TOURNAMENTS:
				return getVisitedTournaments();
			case BowlingPackage.FAN__NUMBER_OF_TOURNAMENTS_VISITED:
				return getNumberOfTournamentsVisited();
			case BowlingPackage.FAN__MONEY_SPENT_ON_TICKETS:
				return getMoneySpentOnTickets();
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
			case BowlingPackage.FAN__NAME:
				setName((String)newValue);
				return;
			case BowlingPackage.FAN__DATE_OF_BIRTH:
				setDateOfBirth((Date)newValue);
				return;
			case BowlingPackage.FAN__HAS_SEASON_TICKET:
				setHasSeasonTicket((Boolean)newValue);
				return;
			case BowlingPackage.FAN__EMAILS:
				getEMails().clear();
				getEMails().addAll((Collection<? extends String>)newValue);
				return;
			case BowlingPackage.FAN__GENDER:
				setGender((Gender)newValue);
				return;
			case BowlingPackage.FAN__FAVOURITE_PLAYER:
				setFavouritePlayer((Player)newValue);
				return;
			case BowlingPackage.FAN__FAN_MERCHANDISE:
				getFanMerchandise().clear();
				getFanMerchandise().addAll((Collection<? extends Merchandise>)newValue);
				return;
			case BowlingPackage.FAN__FAVOURITE_MERCHANDISE:
				setFavouriteMerchandise((Merchandise)newValue);
				return;
			case BowlingPackage.FAN__VISITED_TOURNAMENTS:
				getVisitedTournaments().clear();
				getVisitedTournaments().addAll((Collection<? extends Tournament>)newValue);
				return;
			case BowlingPackage.FAN__NUMBER_OF_TOURNAMENTS_VISITED:
				setNumberOfTournamentsVisited((Integer)newValue);
				return;
			case BowlingPackage.FAN__MONEY_SPENT_ON_TICKETS:
				setMoneySpentOnTickets((Double)newValue);
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
			case BowlingPackage.FAN__NAME:
				unsetName();
				return;
			case BowlingPackage.FAN__DATE_OF_BIRTH:
				unsetDateOfBirth();
				return;
			case BowlingPackage.FAN__HAS_SEASON_TICKET:
				unsetHasSeasonTicket();
				return;
			case BowlingPackage.FAN__EMAILS:
				unsetEMails();
				return;
			case BowlingPackage.FAN__GENDER:
				unsetGender();
				return;
			case BowlingPackage.FAN__FAVOURITE_PLAYER:
				unsetFavouritePlayer();
				return;
			case BowlingPackage.FAN__FAN_MERCHANDISE:
				unsetFanMerchandise();
				return;
			case BowlingPackage.FAN__FAVOURITE_MERCHANDISE:
				unsetFavouriteMerchandise();
				return;
			case BowlingPackage.FAN__VISITED_TOURNAMENTS:
				unsetVisitedTournaments();
				return;
			case BowlingPackage.FAN__NUMBER_OF_TOURNAMENTS_VISITED:
				unsetNumberOfTournamentsVisited();
				return;
			case BowlingPackage.FAN__MONEY_SPENT_ON_TICKETS:
				unsetMoneySpentOnTickets();
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
			case BowlingPackage.FAN__NAME:
				return isSetName();
			case BowlingPackage.FAN__DATE_OF_BIRTH:
				return isSetDateOfBirth();
			case BowlingPackage.FAN__HAS_SEASON_TICKET:
				return isSetHasSeasonTicket();
			case BowlingPackage.FAN__EMAILS:
				return isSetEMails();
			case BowlingPackage.FAN__GENDER:
				return isSetGender();
			case BowlingPackage.FAN__FAVOURITE_PLAYER:
				return isSetFavouritePlayer();
			case BowlingPackage.FAN__FAN_MERCHANDISE:
				return isSetFanMerchandise();
			case BowlingPackage.FAN__FAVOURITE_MERCHANDISE:
				return isSetFavouriteMerchandise();
			case BowlingPackage.FAN__VISITED_TOURNAMENTS:
				return isSetVisitedTournaments();
			case BowlingPackage.FAN__NUMBER_OF_TOURNAMENTS_VISITED:
				return isSetNumberOfTournamentsVisited();
			case BowlingPackage.FAN__MONEY_SPENT_ON_TICKETS:
				return isSetMoneySpentOnTickets();
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
		if (nameESet) result.append(name); else result.append("<unset>");
		result.append(", dateOfBirth: ");
		if (dateOfBirthESet) result.append(dateOfBirth); else result.append("<unset>");
		result.append(", hasSeasonTicket: ");
		if (hasSeasonTicketESet) result.append(hasSeasonTicket); else result.append("<unset>");
		result.append(", eMails: ");
		result.append(eMails);
		result.append(", gender: ");
		if (genderESet) result.append(gender); else result.append("<unset>");
		result.append(", numberOfTournamentsVisited: ");
		if (numberOfTournamentsVisitedESet) result.append(numberOfTournamentsVisited); else result.append("<unset>");
		result.append(", moneySpentOnTickets: ");
		if (moneySpentOnTicketsESet) result.append(moneySpentOnTickets); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //FanImpl
