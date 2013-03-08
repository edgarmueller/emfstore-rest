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
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Matchup#getNrSpecators <em>Nr Specators</em>}</li>
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
	 * Returns the value of the '<em><b>Nr Specators</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nr Specators</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nr Specators</em>' attribute.
	 * @see #setNrSpecators(BigInteger)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getMatchup_NrSpecators()
	 * @model
	 * @generated
	 */
	BigInteger getNrSpecators();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Matchup#getNrSpecators <em>Nr Specators</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nr Specators</em>' attribute.
	 * @see #getNrSpecators()
	 * @generated
	 */
	void setNrSpecators(BigInteger value);

} // Matchup