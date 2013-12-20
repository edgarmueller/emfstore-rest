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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.emfstore.test.model.TestmodelFactory
 * @model kind="package"
 * @generated
 */
public interface TestmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "test"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/emf/emfstore/test/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.emfstore.test.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TestmodelPackage eINSTANCE = org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl <em>Test Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.emfstore.test.model.impl.TestElementImpl
	 * @see org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl#getTestElement()
	 * @generated
	 */
	int TEST_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Strings</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__STRINGS = EcorePackage.EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__REFERENCES = EcorePackage.EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Contained Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__CONTAINED_ELEMENTS = EcorePackage.EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__REFERENCE = EcorePackage.EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Contained Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__CONTAINED_ELEMENT = EcorePackage.EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Other Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__OTHER_REFERENCE = EcorePackage.EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__DESCRIPTION = EcorePackage.EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__CONTAINER = EcorePackage.EOBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Sref Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__SREF_CONTAINER = EcorePackage.EOBJECT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Element Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__ELEMENT_MAP = EcorePackage.EOBJECT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>String To String Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__STRING_TO_STRING_MAP = EcorePackage.EOBJECT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Element To String Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__ELEMENT_TO_STRING_MAP = EcorePackage.EOBJECT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>String To Element Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__STRING_TO_ELEMENT_MAP = EcorePackage.EOBJECT_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Non Contained NTo1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__NON_CONTAINED_NTO1 = EcorePackage.EOBJECT_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Non Contained 1To N</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__NON_CONTAINED_1TO_N = EcorePackage.EOBJECT_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Non Contained NTo M</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__NON_CONTAINED_NTO_M = EcorePackage.EOBJECT_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Non Contained MTo N</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__NON_CONTAINED_MTO_N = EcorePackage.EOBJECT_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Contained Elements2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__CONTAINED_ELEMENTS2 = EcorePackage.EOBJECT_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Container2</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__CONTAINER2 = EcorePackage.EOBJECT_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Contained Elements No Opposite</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__CONTAINED_ELEMENTS_NO_OPPOSITE = EcorePackage.EOBJECT_FEATURE_COUNT + 20;

	/**
	 * The number of structural features of the '<em>Test Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT_FEATURE_COUNT = EcorePackage.EOBJECT_FEATURE_COUNT + 21;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.test.model.impl.TestElementToStringMapImpl <em>Test Element To String Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.emfstore.test.model.impl.TestElementToStringMapImpl
	 * @see org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl#getTestElementToStringMap()
	 * @generated
	 */
	int TEST_ELEMENT_TO_STRING_MAP = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT_TO_STRING_MAP__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT_TO_STRING_MAP__KEY = 1;

	/**
	 * The number of structural features of the '<em>Test Element To String Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT_TO_STRING_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.test.model.impl.StringToStringMapImpl <em>String To String Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.emfstore.test.model.impl.StringToStringMapImpl
	 * @see org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl#getStringToStringMap()
	 * @generated
	 */
	int STRING_TO_STRING_MAP = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To String Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.test.model.impl.TestElementToTestElementMapImpl <em>Test Element To Test Element Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.emfstore.test.model.impl.TestElementToTestElementMapImpl
	 * @see org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl#getTestElementToTestElementMap()
	 * @generated
	 */
	int TEST_ELEMENT_TO_TEST_ELEMENT_MAP = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT_TO_TEST_ELEMENT_MAP__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT_TO_TEST_ELEMENT_MAP__KEY = 1;

	/**
	 * The number of structural features of the '<em>Test Element To Test Element Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT_TO_TEST_ELEMENT_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.test.model.impl.StringToTestElementMapImpl <em>String To Test Element Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.emfstore.test.model.impl.StringToTestElementMapImpl
	 * @see org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl#getStringToTestElementMap()
	 * @generated
	 */
	int STRING_TO_TEST_ELEMENT_MAP = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_TEST_ELEMENT_MAP__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_TEST_ELEMENT_MAP__KEY = 1;

	/**
	 * The number of structural features of the '<em>String To Test Element Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_TEST_ELEMENT_MAP_FEATURE_COUNT = 2;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.test.model.TestElement <em>Test Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Element</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement
	 * @generated
	 */
	EClass getTestElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.test.model.TestElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getName()
	 * @see #getTestElement()
	 * @generated
	 */
	EAttribute getTestElement_Name();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emf.emfstore.test.model.TestElement#getStrings <em>Strings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Strings</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getStrings()
	 * @see #getTestElement()
	 * @generated
	 */
	EAttribute getTestElement_Strings();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.emfstore.test.model.TestElement#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>References</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getReferences()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_References();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements <em>Contained Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contained Elements</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_ContainedElements();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.emfstore.test.model.TestElement#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reference</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getReference()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_Reference();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElement <em>Contained Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Contained Element</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainedElement()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_ContainedElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.emfstore.test.model.TestElement#getOtherReference <em>Other Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Other Reference</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getOtherReference()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_OtherReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.test.model.TestElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getDescription()
	 * @see #getTestElement()
	 * @generated
	 */
	EAttribute getTestElement_Description();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Container</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainer()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_Container();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.emfstore.test.model.TestElement#getSrefContainer <em>Sref Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Sref Container</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getSrefContainer()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_SrefContainer();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.emfstore.test.model.TestElement#getElementMap <em>Element Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Element Map</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getElementMap()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_ElementMap();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.emfstore.test.model.TestElement#getStringToStringMap <em>String To String Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>String To String Map</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getStringToStringMap()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_StringToStringMap();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.emfstore.test.model.TestElement#getElementToStringMap <em>Element To String Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Element To String Map</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getElementToStringMap()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_ElementToStringMap();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.emfstore.test.model.TestElement#getStringToElementMap <em>String To Element Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>String To Element Map</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getStringToElementMap()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_StringToElementMap();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NTo1 <em>Non Contained NTo1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Non Contained NTo1</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NTo1()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_NonContained_NTo1();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_1ToN <em>Non Contained 1To N</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Non Contained 1To N</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_1ToN()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_NonContained_1ToN();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NToM <em>Non Contained NTo M</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Non Contained NTo M</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_NToM()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_NonContained_NToM();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_MToN <em>Non Contained MTo N</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Non Contained MTo N</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getNonContained_MToN()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_NonContained_MToN();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements2 <em>Contained Elements2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contained Elements2</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements2()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_ContainedElements2();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainer2 <em>Container2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Container2</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainer2()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_Container2();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements_NoOpposite <em>Contained Elements No Opposite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contained Elements No Opposite</em>'.
	 * @see org.eclipse.emf.emfstore.test.model.TestElement#getContainedElements_NoOpposite()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_ContainedElements_NoOpposite();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Test Element To String Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Element To String Map</em>'.
	 * @see java.util.Map.Entry
	 * @model features="value key" 
	 *        valueDataType="org.eclipse.emf.ecore.EString"
	 *        keyType="org.eclipse.emf.emfstore.test.model.TestElement"
	 * @generated
	 */
	EClass getTestElementToStringMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getTestElementToStringMap()
	 * @generated
	 */
	EAttribute getTestElementToStringMap_Value();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getTestElementToStringMap()
	 * @generated
	 */
	EReference getTestElementToStringMap_Key();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To String Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To String Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getStringToStringMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToStringMap()
	 * @generated
	 */
	EAttribute getStringToStringMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToStringMap()
	 * @generated
	 */
	EAttribute getStringToStringMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Test Element To Test Element Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Element To Test Element Map</em>'.
	 * @see java.util.Map.Entry
	 * @model features="value key" 
	 *        valueType="org.eclipse.emf.emfstore.test.model.TestElement"
	 *        keyType="org.eclipse.emf.emfstore.test.model.TestElement"
	 * @generated
	 */
	EClass getTestElementToTestElementMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getTestElementToTestElementMap()
	 * @generated
	 */
	EReference getTestElementToTestElementMap_Value();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getTestElementToTestElementMap()
	 * @generated
	 */
	EReference getTestElementToTestElementMap_Key();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Test Element Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Test Element Map</em>'.
	 * @see java.util.Map.Entry
	 * @model features="value key" 
	 *        valueType="org.eclipse.emf.emfstore.test.model.TestElement"
	 *        keyDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getStringToTestElementMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToTestElementMap()
	 * @generated
	 */
	EReference getStringToTestElementMap_Value();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToTestElementMap()
	 * @generated
	 */
	EAttribute getStringToTestElementMap_Key();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestmodelFactory getTestmodelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl <em>Test Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.emfstore.test.model.impl.TestElementImpl
		 * @see org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl#getTestElement()
		 * @generated
		 */
		EClass TEST_ELEMENT = eINSTANCE.getTestElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_ELEMENT__NAME = eINSTANCE.getTestElement_Name();

		/**
		 * The meta object literal for the '<em><b>Strings</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_ELEMENT__STRINGS = eINSTANCE.getTestElement_Strings();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__REFERENCES = eINSTANCE.getTestElement_References();

		/**
		 * The meta object literal for the '<em><b>Contained Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__CONTAINED_ELEMENTS = eINSTANCE.getTestElement_ContainedElements();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__REFERENCE = eINSTANCE.getTestElement_Reference();

		/**
		 * The meta object literal for the '<em><b>Contained Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__CONTAINED_ELEMENT = eINSTANCE.getTestElement_ContainedElement();

		/**
		 * The meta object literal for the '<em><b>Other Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__OTHER_REFERENCE = eINSTANCE.getTestElement_OtherReference();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_ELEMENT__DESCRIPTION = eINSTANCE.getTestElement_Description();

		/**
		 * The meta object literal for the '<em><b>Container</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__CONTAINER = eINSTANCE.getTestElement_Container();

		/**
		 * The meta object literal for the '<em><b>Sref Container</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__SREF_CONTAINER = eINSTANCE.getTestElement_SrefContainer();

		/**
		 * The meta object literal for the '<em><b>Element Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__ELEMENT_MAP = eINSTANCE.getTestElement_ElementMap();

		/**
		 * The meta object literal for the '<em><b>String To String Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__STRING_TO_STRING_MAP = eINSTANCE.getTestElement_StringToStringMap();

		/**
		 * The meta object literal for the '<em><b>Element To String Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__ELEMENT_TO_STRING_MAP = eINSTANCE.getTestElement_ElementToStringMap();

		/**
		 * The meta object literal for the '<em><b>String To Element Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__STRING_TO_ELEMENT_MAP = eINSTANCE.getTestElement_StringToElementMap();

		/**
		 * The meta object literal for the '<em><b>Non Contained NTo1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__NON_CONTAINED_NTO1 = eINSTANCE.getTestElement_NonContained_NTo1();

		/**
		 * The meta object literal for the '<em><b>Non Contained 1To N</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__NON_CONTAINED_1TO_N = eINSTANCE.getTestElement_NonContained_1ToN();

		/**
		 * The meta object literal for the '<em><b>Non Contained NTo M</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__NON_CONTAINED_NTO_M = eINSTANCE.getTestElement_NonContained_NToM();

		/**
		 * The meta object literal for the '<em><b>Non Contained MTo N</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__NON_CONTAINED_MTO_N = eINSTANCE.getTestElement_NonContained_MToN();

		/**
		 * The meta object literal for the '<em><b>Contained Elements2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__CONTAINED_ELEMENTS2 = eINSTANCE.getTestElement_ContainedElements2();

		/**
		 * The meta object literal for the '<em><b>Container2</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__CONTAINER2 = eINSTANCE.getTestElement_Container2();

		/**
		 * The meta object literal for the '<em><b>Contained Elements No Opposite</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT__CONTAINED_ELEMENTS_NO_OPPOSITE = eINSTANCE
			.getTestElement_ContainedElements_NoOpposite();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.test.model.impl.TestElementToStringMapImpl <em>Test Element To String Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.emfstore.test.model.impl.TestElementToStringMapImpl
		 * @see org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl#getTestElementToStringMap()
		 * @generated
		 */
		EClass TEST_ELEMENT_TO_STRING_MAP = eINSTANCE.getTestElementToStringMap();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_ELEMENT_TO_STRING_MAP__VALUE = eINSTANCE.getTestElementToStringMap_Value();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT_TO_STRING_MAP__KEY = eINSTANCE.getTestElementToStringMap_Key();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.test.model.impl.StringToStringMapImpl <em>String To String Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.emfstore.test.model.impl.StringToStringMapImpl
		 * @see org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl#getStringToStringMap()
		 * @generated
		 */
		EClass STRING_TO_STRING_MAP = eINSTANCE.getStringToStringMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_STRING_MAP__KEY = eINSTANCE.getStringToStringMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_STRING_MAP__VALUE = eINSTANCE.getStringToStringMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.test.model.impl.TestElementToTestElementMapImpl <em>Test Element To Test Element Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.emfstore.test.model.impl.TestElementToTestElementMapImpl
		 * @see org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl#getTestElementToTestElementMap()
		 * @generated
		 */
		EClass TEST_ELEMENT_TO_TEST_ELEMENT_MAP = eINSTANCE.getTestElementToTestElementMap();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT_TO_TEST_ELEMENT_MAP__VALUE = eINSTANCE.getTestElementToTestElementMap_Value();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_ELEMENT_TO_TEST_ELEMENT_MAP__KEY = eINSTANCE.getTestElementToTestElementMap_Key();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.test.model.impl.StringToTestElementMapImpl <em>String To Test Element Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.emfstore.test.model.impl.StringToTestElementMapImpl
		 * @see org.eclipse.emf.emfstore.test.model.impl.TestmodelPackageImpl#getStringToTestElementMap()
		 * @generated
		 */
		EClass STRING_TO_TEST_ELEMENT_MAP = eINSTANCE.getStringToTestElementMap();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_TEST_ELEMENT_MAP__VALUE = eINSTANCE.getStringToTestElementMap_Value();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_TEST_ELEMENT_MAP__KEY = eINSTANCE.getStringToTestElementMap_Key();

	}

} // TestmodelPackage
