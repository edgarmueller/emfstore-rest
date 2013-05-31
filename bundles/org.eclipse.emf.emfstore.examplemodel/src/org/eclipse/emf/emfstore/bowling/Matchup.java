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
package org.eclipse.emf.emfstore.bowling;

import java.math.BigInteger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Matchup</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Matchup#getGames <em>Games</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Matchup#getNrSpectators <em>Nr Spectators</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getMatchup()
 * @model
 * @generated
 */
public interface Matchup extends EObject {
	/**
	 * Returns the value of the '<em><b>Games</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.bowling.Game}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.bowling.Game#getMatchup <em>Matchup</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Games</em>' containment reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Games</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getMatchup_Games()
	 * @see org.eclipse.emf.emfstore.bowling.Game#getMatchup
	 * @model opposite="matchup" containment="true" resolveProxies="true" lower="2" upper="2"
	 * @generated
	 */
	EList<Game> getGames();

	/**
	 * Returns the value of the '<em><b>Nr Spectators</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nr Spectators</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nr Spectators</em>' attribute.
	 * @see #setNrSpectators(BigInteger)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getMatchup_NrSpectators()
	 * @model
	 * @generated
	 */
	BigInteger getNrSpectators();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Matchup#getNrSpectators <em>Nr Spectators</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nr Spectators</em>' attribute.
	 * @see #getNrSpectators()
	 * @generated
	 */
	void setNrSpectators(BigInteger value);

} // Matchup
