/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.testmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Test Element</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getStrings <em>Strings</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getReferences <em>References</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getContainedElements <em>Contained Elements
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getReference <em>Reference</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getContainedElement <em>Contained Element</em>}
 * </li>
 * <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getOtherReference <em>Other Reference</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElement()
 * @model
 * @generated
 */
public interface TestElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElement_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getName <em>Name</em>}'
	 * attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Strings</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strings</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Strings</em>' attribute list.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElement_Strings()
	 * @model
	 * @generated
	 */
	EList<String> getStrings();

	/**
	 * Returns the value of the '<em><b>References</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>References</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>References</em>' reference list.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElement_References()
	 * @model
	 * @generated
	 */
	EList<TestElement> getReferences();

	/**
	 * Returns the value of the '<em><b>Contained Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Elements</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Contained Elements</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElement_ContainedElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestElement> getContainedElements();

	/**
	 * Returns the value of the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Reference</em>' reference.
	 * @see #setReference(TestElement)
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElement_Reference()
	 * @model
	 * @generated
	 */
	TestElement getReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getReference
	 * <em>Reference</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Reference</em>' reference.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(TestElement value);

	/**
	 * Returns the value of the '<em><b>Contained Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Element</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Contained Element</em>' containment reference.
	 * @see #setContainedElement(TestElement)
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElement_ContainedElement()
	 * @model containment="true"
	 * @generated
	 */
	TestElement getContainedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getContainedElement
	 * <em>Contained Element</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Contained Element</em>' containment reference.
	 * @see #getContainedElement()
	 * @generated
	 */
	void setContainedElement(TestElement value);

	/**
	 * Returns the value of the '<em><b>Other Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Other Reference</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Other Reference</em>' reference.
	 * @see #setOtherReference(TestElement)
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElement_OtherReference()
	 * @model
	 * @generated
	 */
	TestElement getOtherReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getOtherReference
	 * <em>Other Reference</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Other Reference</em>' reference.
	 * @see #getOtherReference()
	 * @generated
	 */
	void setOtherReference(TestElement value);

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
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElement_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getDescription
	 * <em>Description</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

} // TestElement