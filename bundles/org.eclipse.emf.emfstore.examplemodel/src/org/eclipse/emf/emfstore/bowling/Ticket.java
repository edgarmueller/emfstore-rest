/**
 */
package org.eclipse.emf.emfstore.bowling;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ticket</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Ticket#getVenue <em>Venue</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.Ticket#getAntiTheftModule <em>Anti Theft Module</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTicket()
 * @model
 * @generated
 */
public interface Ticket extends EObject {
	/**
	 * Returns the value of the '<em><b>Venue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Venue</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Venue</em>' attribute.
	 * @see #setVenue(String)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTicket_Venue()
	 * @model
	 * @generated
	 */
	String getVenue();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Ticket#getVenue <em>Venue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Venue</em>' attribute.
	 * @see #getVenue()
	 * @generated
	 */
	void setVenue(String value);

	/**
	 * Returns the value of the '<em><b>Anti Theft Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Anti Theft Module</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Anti Theft Module</em>' containment reference.
	 * @see #setAntiTheftModule(Module)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getTicket_AntiTheftModule()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	Module getAntiTheftModule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Ticket#getAntiTheftModule
	 * <em>Anti Theft Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Anti Theft Module</em>' containment reference.
	 * @see #getAntiTheftModule()
	 * @generated
	 */
	void setAntiTheftModule(Module value);

} // Ticket
