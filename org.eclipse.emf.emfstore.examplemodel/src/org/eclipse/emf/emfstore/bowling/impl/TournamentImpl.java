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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Game;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Referee;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.bowling.TournamentType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tournament</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.TournamentImpl#getMatchups <em>Matchups</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.TournamentImpl#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.TournamentImpl#getPlayerPoints <em>Player Points</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.TournamentImpl#getPlayers <em>Players</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.TournamentImpl#getReferees <em>Referees</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TournamentImpl extends EObjectImpl implements Tournament {
	/**
	 * The cached value of the '{@link #getMatchups() <em>Matchups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMatchups()
	 * @generated
	 * @ordered
	 */
	protected EList<Matchup> matchups;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final TournamentType TYPE_EDEFAULT = TournamentType.PRO;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected TournamentType type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPlayerPoints() <em>Player Points</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPlayerPoints()
	 * @generated
	 * @ordered
	 */
	protected EMap<Player, Integer> playerPoints;

	/**
	 * The cached value of the '{@link #getPlayers() <em>Players</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPlayers()
	 * @generated
	 * @ordered
	 */
	protected EList<Player> players;

	/**
	 * The cached value of the '{@link #getReferees() <em>Referees</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getReferees()
	 * @generated
	 * @ordered
	 */
	protected EMap<Referee, Game> referees;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TournamentImpl() {
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
		return BowlingPackage.Literals.TOURNAMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Matchup> getMatchups() {
		if (matchups == null) {
			matchups = new EObjectContainmentEList.Resolving<Matchup>(Matchup.class, this,
				BowlingPackage.TOURNAMENT__MATCHUPS);
		}
		return matchups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TournamentType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setType(TournamentType newType) {
		TournamentType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.TOURNAMENT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EMap<Player, Integer> getPlayerPoints() {
		if (playerPoints == null) {
			playerPoints = new EcoreEMap<Player, Integer>(BowlingPackage.Literals.PLAYER_TO_POINTS_MAP,
				PlayerToPointsMapImpl.class, this, BowlingPackage.TOURNAMENT__PLAYER_POINTS);
		}
		return playerPoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Player> getPlayers() {
		if (players == null) {
			players = new EObjectResolvingEList<Player>(Player.class, this, BowlingPackage.TOURNAMENT__PLAYERS);
		}
		return players;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EMap<Referee, Game> getReferees() {
		if (referees == null) {
			referees = new EcoreEMap<Referee, Game>(BowlingPackage.Literals.REFEREE_TO_GAMES_MAP,
				RefereeToGamesMapImpl.class, this, BowlingPackage.TOURNAMENT__REFEREES);
		}
		return referees;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case BowlingPackage.TOURNAMENT__MATCHUPS:
			return ((InternalEList<?>) getMatchups()).basicRemove(otherEnd, msgs);
		case BowlingPackage.TOURNAMENT__PLAYER_POINTS:
			return ((InternalEList<?>) getPlayerPoints()).basicRemove(otherEnd, msgs);
		case BowlingPackage.TOURNAMENT__REFEREES:
			return ((InternalEList<?>) getReferees()).basicRemove(otherEnd, msgs);
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BowlingPackage.TOURNAMENT__MATCHUPS:
			return getMatchups();
		case BowlingPackage.TOURNAMENT__TYPE:
			return getType();
		case BowlingPackage.TOURNAMENT__PLAYER_POINTS:
			if (coreType)
				return getPlayerPoints();
			else
				return getPlayerPoints().map();
		case BowlingPackage.TOURNAMENT__PLAYERS:
			return getPlayers();
		case BowlingPackage.TOURNAMENT__REFEREES:
			if (coreType)
				return getReferees();
			else
				return getReferees().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case BowlingPackage.TOURNAMENT__MATCHUPS:
			getMatchups().clear();
			getMatchups().addAll((Collection<? extends Matchup>) newValue);
			return;
		case BowlingPackage.TOURNAMENT__TYPE:
			setType((TournamentType) newValue);
			return;
		case BowlingPackage.TOURNAMENT__PLAYER_POINTS:
			((EStructuralFeature.Setting) getPlayerPoints()).set(newValue);
			return;
		case BowlingPackage.TOURNAMENT__PLAYERS:
			getPlayers().clear();
			getPlayers().addAll((Collection<? extends Player>) newValue);
			return;
		case BowlingPackage.TOURNAMENT__REFEREES:
			((EStructuralFeature.Setting) getReferees()).set(newValue);
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
		case BowlingPackage.TOURNAMENT__MATCHUPS:
			getMatchups().clear();
			return;
		case BowlingPackage.TOURNAMENT__TYPE:
			setType(TYPE_EDEFAULT);
			return;
		case BowlingPackage.TOURNAMENT__PLAYER_POINTS:
			getPlayerPoints().clear();
			return;
		case BowlingPackage.TOURNAMENT__PLAYERS:
			getPlayers().clear();
			return;
		case BowlingPackage.TOURNAMENT__REFEREES:
			getReferees().clear();
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
		case BowlingPackage.TOURNAMENT__MATCHUPS:
			return matchups != null && !matchups.isEmpty();
		case BowlingPackage.TOURNAMENT__TYPE:
			return type != TYPE_EDEFAULT;
		case BowlingPackage.TOURNAMENT__PLAYER_POINTS:
			return playerPoints != null && !playerPoints.isEmpty();
		case BowlingPackage.TOURNAMENT__PLAYERS:
			return players != null && !players.isEmpty();
		case BowlingPackage.TOURNAMENT__REFEREES:
			return referees != null && !referees.isEmpty();
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
		result.append(" (type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} // TournamentImpl