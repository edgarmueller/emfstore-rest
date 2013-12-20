/**
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 */
package org.eclipse.emf.emfstore.test.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getStrings <em>Strings</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getReferences <em>References</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements <em>Contained Elements</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getReference <em>Reference</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElement <em>Contained Element</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getOtherReference <em>Other Reference</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainer <em>Container</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getSrefContainer <em>Sref Container</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getElementMap <em>Element Map</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getStringToStringMap <em>String To String Map</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getElementToStringMap <em>Element To String Map</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getStringToElementMap <em>String To Element Map</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NTo1 <em>Non Contained NTo1</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_1ToN <em>Non Contained 1To N</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NToM <em>Non Contained NTo M</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_MToN <em>Non Contained MTo N</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements2 <em>Contained Elements2</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainer2 <em>Container2</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements_NoOpposite <em>Contained Elements No Opposite</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement()
 * @model
 * @generated
 */
public interface TestElement extends EObject {
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
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_Name()
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel foo='bar'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.test.model.TestElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * If the meaning of the '<em>Strings</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strings</em>' attribute list.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_Strings()
	 * @model
	 * @generated
	 */
	EList<String> getStrings();

	/**
	 * Returns the value of the '<em><b>References</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.test.model.TestElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>References</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>References</em>' reference list.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_References()
	 * @model
	 * @generated
	 */
	EList<TestElement> getReferences();

	/**
	 * Returns the value of the '<em><b>Contained Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.test.model.TestElement}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contained Elements</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_ContainedElements()
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainer
	 * @model opposite="container" containment="true"
	 * @generated
	 */
	EList<TestElement> getContainedElements();

	/**
	 * Returns the value of the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference</em>' reference.
	 * @see #setReference(TestElement)
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_Reference()
	 * @model
	 * @generated
	 */
	TestElement getReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.test.model.TestElement#getReference <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference</em>' reference.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(TestElement value);

	/**
	 * Returns the value of the '<em><b>Contained Element</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.test.model.TestElement#getSrefContainer <em>Sref Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Element</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contained Element</em>' containment reference.
	 * @see #setContainedElement(TestElement)
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_ContainedElement()
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getSrefContainer
	 * @model opposite="srefContainer" containment="true"
	 * @generated
	 */
	TestElement getContainedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElement <em>Contained Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contained Element</em>' containment reference.
	 * @see #getContainedElement()
	 * @generated
	 */
	void setContainedElement(TestElement value);

	/**
	 * Returns the value of the '<em><b>Other Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Other Reference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Other Reference</em>' reference.
	 * @see #setOtherReference(TestElement)
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_OtherReference()
	 * @model
	 * @generated
	 */
	TestElement getOtherReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.test.model.TestElement#getOtherReference <em>Other Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Other Reference</em>' reference.
	 * @see #getOtherReference()
	 * @generated
	 */
	void setOtherReference(TestElement value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.test.model.TestElement#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Container</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements <em>Contained Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Container</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container</em>' container reference.
	 * @see #setContainer(TestElement)
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_Container()
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements
	 * @model opposite="containedElements" transient="false"
	 * @generated
	 */
	TestElement getContainer();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainer <em>Container</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container</em>' container reference.
	 * @see #getContainer()
	 * @generated
	 */
	void setContainer(TestElement value);

	/**
	 * Returns the value of the '<em><b>Sref Container</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElement <em>Contained Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sref Container</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sref Container</em>' container reference.
	 * @see #setSrefContainer(TestElement)
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_SrefContainer()
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainedElement
	 * @model opposite="containedElement" transient="false"
	 * @generated
	 */
	TestElement getSrefContainer();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.test.model.TestElement#getSrefContainer <em>Sref Container</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sref Container</em>' container reference.
	 * @see #getSrefContainer()
	 * @generated
	 */
	void setSrefContainer(TestElement value);

	/**
	 * Returns the value of the '<em><b>Element Map</b></em>' map.
	 * The key is of type {@link org.eclipse.emf.emfstore.test.model.TestElement},
	 * and the value is of type {@link org.eclipse.emf.emfstore.test.model.TestElement},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Map</em>' map.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_ElementMap()
	 * @model mapType="org.eclipse.emf.emfstore.test.model.TestElementToTestElementMap<org.eclipse.emf.emfstore.test.model.TestElement, org.eclipse.emf.emfstore.test.model.TestElement>"
	 * @generated
	 */
	EMap<TestElement, TestElement> getElementMap();

	/**
	 * Returns the value of the '<em><b>String To String Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String To String Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String To String Map</em>' map.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_StringToStringMap()
	 * @model mapType="org.eclipse.emf.emfstore.test.model.StringToStringMap<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getStringToStringMap();

	/**
	 * Returns the value of the '<em><b>Element To String Map</b></em>' map.
	 * The key is of type {@link org.eclipse.emf.emfstore.test.model.TestElement},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element To String Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element To String Map</em>' map.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_ElementToStringMap()
	 * @model mapType="org.eclipse.emf.emfstore.test.model.TestElementToStringMap<org.eclipse.emf.emfstore.test.model.TestElement, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<TestElement, String> getElementToStringMap();

	/**
	 * Returns the value of the '<em><b>String To Element Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.emf.emfstore.test.model.TestElement},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String To Element Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String To Element Map</em>' map.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_StringToElementMap()
	 * @model mapType="org.eclipse.emf.emfstore.test.model.StringToTestElementMap<org.eclipse.emf.ecore.EString, org.eclipse.emf.emfstore.test.model.TestElement>"
	 * @generated
	 */
	EMap<String, TestElement> getStringToElementMap();

	/**
	 * Returns the value of the '<em><b>Non Contained NTo1</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_1ToN <em>Non Contained 1To N</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Contained NTo1</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Contained NTo1</em>' reference.
	 * @see #setNonContained_NTo1(TestElement)
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_NonContained_NTo1()
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_1ToN
	 * @model opposite="nonContained_1ToN"
	 * @generated
	 */
	TestElement getNonContained_NTo1();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NTo1 <em>Non Contained NTo1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Contained NTo1</em>' reference.
	 * @see #getNonContained_NTo1()
	 * @generated
	 */
	void setNonContained_NTo1(TestElement value);

	/**
	 * Returns the value of the '<em><b>Non Contained 1To N</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.test.model.TestElement}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NTo1 <em>Non Contained NTo1</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Contained 1To N</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Contained 1To N</em>' reference list.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_NonContained_1ToN()
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NTo1
	 * @model opposite="nonContained_NTo1"
	 * @generated
	 */
	EList<TestElement> getNonContained_1ToN();

	/**
	 * Returns the value of the '<em><b>Non Contained NTo M</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.test.model.TestElement}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_MToN <em>Non Contained MTo N</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Contained NTo M</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Contained NTo M</em>' reference list.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_NonContained_NToM()
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_MToN
	 * @model opposite="nonContained_MToN"
	 * @generated
	 */
	EList<TestElement> getNonContained_NToM();

	/**
	 * Returns the value of the '<em><b>Non Contained MTo N</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.test.model.TestElement}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NToM <em>Non Contained NTo M</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Contained MTo N</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Contained MTo N</em>' reference list.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_NonContained_MToN()
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NToM
	 * @model opposite="nonContained_NToM"
	 * @generated
	 */
	EList<TestElement> getNonContained_MToN();

	/**
	 * Returns the value of the '<em><b>Contained Elements2</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.test.model.TestElement}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainer2 <em>Container2</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Elements2</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contained Elements2</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_ContainedElements2()
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainer2
	 * @model opposite="container2" containment="true"
	 * @generated
	 */
	EList<TestElement> getContainedElements2();

	/**
	 * Returns the value of the '<em><b>Container2</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements2 <em>Contained Elements2</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Container2</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container2</em>' container reference.
	 * @see #setContainer2(TestElement)
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_Container2()
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements2
	 * @model opposite="containedElements2" transient="false"
	 * @generated
	 */
	TestElement getContainer2();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainer2 <em>Container2</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container2</em>' container reference.
	 * @see #getContainer2()
	 * @generated
	 */
	void setContainer2(TestElement value);

	/**
	 * Returns the value of the '<em><b>Contained Elements No Opposite</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.test.model.TestElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Elements No Opposite</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contained Elements No Opposite</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#getTestElement_ContainedElements_NoOpposite()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestElement> getContainedElements_NoOpposite();

} // TestElement
