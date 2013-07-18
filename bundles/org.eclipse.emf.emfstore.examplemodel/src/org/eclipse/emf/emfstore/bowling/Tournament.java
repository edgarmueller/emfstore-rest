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
package org.eclipse.emf.emfstore.bowling;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tournament</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Tournament#getMatchups <em>Matchups</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Tournament#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Tournament#getPlayerPoints <em>Player Points</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Tournament#getPlayers <em>Players</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Tournament#getReferees <em>Referees</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Tournament#getPriceMoney <em>Price Money</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Tournament#getReceivesTrophy <em>Receives Trophy</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Tournament#getMatchDays <em>Match Days</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTournament()
 * @model
 * @generated
 */
public interface Tournament extends EObject {
	/**
	 * Returns the value of the '<em><b>Matchups</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.bowling.Matchup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Matchups</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Matchups</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTournament_Matchups()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Matchup> getMatchups();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.emfstore.bowling.TournamentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.emfstore.bowling.TournamentType
	 * @see #setType(TournamentType)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTournament_Type()
	 * @model
	 * @generated
	 */
	TournamentType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Tournament#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.emfstore.bowling.TournamentType
	 * @see #getType()
	 * @generated
	 */
	void setType(TournamentType value);

	/**
	 * Returns the value of the '<em><b>Player Points</b></em>' map.
	 * The key is of type {@link org.eclipse.emf.emfstore.bowling.Player},
	 * and the value is of type {@link java.lang.Integer},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Player Points</em>' map isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Player Points</em>' map.
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTournament_PlayerPoints()
	 * @model mapType=
	 *        "org.eclipse.emf.emfstore.bowling.PlayerToPointsMap<org.eclipse.emf.emfstore.bowling.Player, org.eclipse.emf.ecore.EIntegerObject>"
	 * @generated
	 */
	EMap<Player, Integer> getPlayerPoints();

	/**
	 * Returns the value of the '<em><b>Players</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.bowling.Player}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Players</em>' reference list isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Players</em>' reference list.
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTournament_Players()
	 * @model
	 * @generated
	 */
	EList<Player> getPlayers();

	/**
	 * Returns the value of the '<em><b>Referees</b></em>' map.
	 * The key is of type {@link org.eclipse.emf.emfstore.bowling.Referee},
	 * and the value is of type {@link org.eclipse.emf.emfstore.bowling.Game},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referees</em>' map isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Referees</em>' map.
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTournament_Referees()
	 * @model mapType=
	 *        "org.eclipse.emf.emfstore.bowling.RefereeToGamesMap<org.eclipse.emf.emfstore.bowling.Referee, org.eclipse.emf.emfstore.bowling.Game>"
	 * @generated
	 */
	EMap<Referee, Game> getReferees();

	/**
	 * Returns the value of the '<em><b>Price Money</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Double}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Price Money</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Price Money</em>' attribute list.
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTournament_PriceMoney()
	 * @model unique="false"
	 * @generated
	 */
	EList<Double> getPriceMoney();

	/**
	 * Returns the value of the '<em><b>Receives Trophy</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Boolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Receives Trophy</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Receives Trophy</em>' attribute list.
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTournament_ReceivesTrophy()
	 * @model unique="false"
	 * @generated
	 */
	EList<Boolean> getReceivesTrophy();

	/**
	 * Returns the value of the '<em><b>Match Days</b></em>' attribute list.
	 * The list contents are of type {@link java.util.Date}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Match Days</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Match Days</em>' attribute list.
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTournament_MatchDays()
	 * @model
	 * @generated
	 */
	EList<Date> getMatchDays();

} // Tournament