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

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Referee</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Referee#getDateOfBirth <em>Date Of Birth</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Referee#getLeague <em>League</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getReferee()
 * @model
 * @generated
 */
public interface Referee extends EObject {
	/**
	 * Returns the value of the '<em><b>Date Of Birth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date Of Birth</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Date Of Birth</em>' attribute.
	 * @see #setDateOfBirth(XMLGregorianCalendar)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getReferee_DateOfBirth()
	 * @model dataType="org.eclipse.emf.emfstore.bowling.XMLDate"
	 * @generated
	 */
	XMLGregorianCalendar getDateOfBirth();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Referee#getDateOfBirth <em>Date Of Birth</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Date Of Birth</em>' attribute.
	 * @see #getDateOfBirth()
	 * @generated
	 */
	void setDateOfBirth(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>League</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>League</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>League</em>' reference.
	 * @see #setLeague(League)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getReferee_League()
	 * @model
	 * @generated
	 */
	League getLeague();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Referee#getLeague <em>League</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>League</em>' reference.
	 * @see #getLeague()
	 * @generated
	 */
	void setLeague(League value);

} // Referee