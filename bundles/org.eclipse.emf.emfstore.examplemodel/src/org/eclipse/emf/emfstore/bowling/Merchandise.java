/**
 */
package org.eclipse.emf.emfstore.bowling;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Merchandise</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Merchandise#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Merchandise#getPrice <em>Price</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Merchandise#getSerialNumber <em>Serial Number</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getMerchandise()
 * @model
 * @generated
 */
public interface Merchandise extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getMerchandise_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Merchandise#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Price</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Price</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Price</em>' attribute.
	 * @see #isSetPrice()
	 * @see #unsetPrice()
	 * @see #setPrice(BigDecimal)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getMerchandise_Price()
	 * @model unsettable="true"
	 * @generated
	 */
	BigDecimal getPrice();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Merchandise#getPrice <em>Price</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Price</em>' attribute.
	 * @see #isSetPrice()
	 * @see #unsetPrice()
	 * @see #getPrice()
	 * @generated
	 */
	void setPrice(BigDecimal value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Merchandise#getPrice <em>Price</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPrice()
	 * @see #getPrice()
	 * @see #setPrice(BigDecimal)
	 * @generated
	 */
	void unsetPrice();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Merchandise#getPrice <em>Price</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Price</em>' attribute is set.
	 * @see #unsetPrice()
	 * @see #getPrice()
	 * @see #setPrice(BigDecimal)
	 * @generated
	 */
	boolean isSetPrice();

	/**
	 * Returns the value of the '<em><b>Serial Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Serial Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Serial Number</em>' attribute.
	 * @see #isSetSerialNumber()
	 * @see #unsetSerialNumber()
	 * @see #setSerialNumber(BigInteger)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getMerchandise_SerialNumber()
	 * @model unsettable="true"
	 * @generated
	 */
	BigInteger getSerialNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Merchandise#getSerialNumber <em>Serial Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serial Number</em>' attribute.
	 * @see #isSetSerialNumber()
	 * @see #unsetSerialNumber()
	 * @see #getSerialNumber()
	 * @generated
	 */
	void setSerialNumber(BigInteger value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Merchandise#getSerialNumber <em>Serial Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSerialNumber()
	 * @see #getSerialNumber()
	 * @see #setSerialNumber(BigInteger)
	 * @generated
	 */
	void unsetSerialNumber();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Merchandise#getSerialNumber <em>Serial Number</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Serial Number</em>' attribute is set.
	 * @see #unsetSerialNumber()
	 * @see #getSerialNumber()
	 * @see #setSerialNumber(BigInteger)
	 * @generated
	 */
	boolean isSetSerialNumber();

} // Merchandise
