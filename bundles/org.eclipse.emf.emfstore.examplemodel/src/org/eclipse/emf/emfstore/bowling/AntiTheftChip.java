/**
 */
package org.eclipse.emf.emfstore.bowling;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Anti Theft Chip</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.bowling.AntiTheftChip#getDescription <em>Description</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.AntiTheftChip#getModule <em>Module</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getAntiTheftChip()
 * @model
 * @generated
 */
public interface AntiTheftChip extends EObject {
	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getAntiTheftChip_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.AntiTheftChip#getDescription <em>Description</em>}
	 * ' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Module</em>' containment reference.
	 * @see #setModule(Module)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getAntiTheftChip_Module()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	Module getModule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.AntiTheftChip#getModule <em>Module</em>}'
	 * containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Module</em>' containment reference.
	 * @see #getModule()
	 * @generated
	 */
	void setModule(Module value);

} // AntiTheftChip
