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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Player</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Player#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Player#getDateOfBirth <em>Date Of Birth</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Player#getHeight <em>Height</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Player#isIsProfessional <em>Is Professional</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Player#getEMails <em>EMails</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Player#getNumberOfVictories <em>Number Of Victories</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Player#getPlayedTournamentTypes <em>Played Tournament Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getPlayer()
 * @model
 * @generated
 */
public interface Player extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getPlayer_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Player#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Date Of Birth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date Of Birth</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date Of Birth</em>' attribute.
	 * @see #setDateOfBirth(Date)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getPlayer_DateOfBirth()
	 * @model
	 * @generated
	 */
	Date getDateOfBirth();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Player#getDateOfBirth <em>Date Of Birth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date Of Birth</em>' attribute.
	 * @see #getDateOfBirth()
	 * @generated
	 */
	void setDateOfBirth(Date value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Height</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(double)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getPlayer_Height()
	 * @model
	 * @generated
	 */
	double getHeight();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Player#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(double value);

	/**
	 * Returns the value of the '<em><b>Is Professional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Professional</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Professional</em>' attribute.
	 * @see #setIsProfessional(boolean)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getPlayer_IsProfessional()
	 * @model
	 * @generated
	 */
	boolean isIsProfessional();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Player#isIsProfessional <em>Is Professional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Professional</em>' attribute.
	 * @see #isIsProfessional()
	 * @generated
	 */
	void setIsProfessional(boolean value);

	/**
	 * Returns the value of the '<em><b>EMails</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EMails</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EMails</em>' attribute list.
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getPlayer_EMails()
	 * @model
	 * @generated
	 */
	EList<String> getEMails();

	/**
	 * Returns the value of the '<em><b>Number Of Victories</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Victories</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Victories</em>' attribute.
	 * @see #setNumberOfVictories(int)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getPlayer_NumberOfVictories()
	 * @model
	 * @generated
	 */
	int getNumberOfVictories();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Player#getNumberOfVictories <em>Number Of Victories</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Victories</em>' attribute.
	 * @see #getNumberOfVictories()
	 * @generated
	 */
	void setNumberOfVictories(int value);

	/**
	 * Returns the value of the '<em><b>Played Tournament Types</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.bowling.TournamentType}.
	 * The literals are from the enumeration {@link org.eclipse.emf.emfstore.bowling.TournamentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Played Tournament Types</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Played Tournament Types</em>' attribute list.
	 * @see org.eclipse.emf.emfstore.bowling.TournamentType
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getPlayer_PlayedTournamentTypes()
	 * @model
	 * @generated
	 */
	EList<TournamentType> getPlayedTournamentTypes();

} // Player