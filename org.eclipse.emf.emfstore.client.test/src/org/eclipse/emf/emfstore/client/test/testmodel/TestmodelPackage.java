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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory
 * @model kind="package"
 * @generated
 */
public interface TestmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "testmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/emf/emfstore/client/test/testmodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.emfstore.client.test";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	TestmodelPackage eINSTANCE = org.eclipse.emf.emfstore.client.test.testmodel.impl.TestmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.client.test.testmodel.impl.TestElementImpl
	 * <em>Test Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.impl.TestElementImpl
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.impl.TestmodelPackageImpl#getTestElement()
	 * @generated
	 */
	int TEST_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	// int TEST_ELEMENT__IDENTIFIER = ModelPackage. 0; // ModelPackage.UNI MetamodelPackage.MODEL_ELEMENT__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Strings</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__STRINGS = EcorePackage.EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__REFERENCES = EcorePackage.EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Contained Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__CONTAINED_ELEMENTS = EcorePackage.EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__REFERENCE = EcorePackage.EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Contained Element</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__CONTAINED_ELEMENT = EcorePackage.EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Other Reference</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__OTHER_REFERENCE = EcorePackage.EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT__DESCRIPTION = EcorePackage.EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Test Element</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_ELEMENT_FEATURE_COUNT = EcorePackage.EOBJECT_FEATURE_COUNT + 8;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement
	 * <em>Test Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Test Element</em>'.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestElement
	 * @generated
	 */
	EClass getTestElement();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getName()
	 * @see #getTestElement()
	 * @generated
	 */
	EAttribute getTestElement_Name();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getStrings <em>Strings</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Strings</em>'.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getStrings()
	 * @see #getTestElement()
	 * @generated
	 */
	EAttribute getTestElement_Strings();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getReferences <em>References</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>References</em>'.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getReferences()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_References();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getContainedElements
	 * <em>Contained Elements</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Contained Elements</em>'.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getContainedElements()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_ContainedElements();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getReference <em>Reference</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Reference</em>'.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getReference()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_Reference();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getContainedElement <em>Contained Element</em>}
	 * '.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Contained Element</em>'.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getContainedElement()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_ContainedElement();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getOtherReference <em>Other Reference</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Other Reference</em>'.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getOtherReference()
	 * @see #getTestElement()
	 * @generated
	 */
	EReference getTestElement_OtherReference();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getDescription <em>Description</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getDescription()
	 * @see #getTestElement()
	 * @generated
	 */
	EAttribute getTestElement_Description();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestmodelFactory getTestmodelFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.client.test.testmodel.impl.TestElementImpl
		 * <em>Test Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.client.test.testmodel.impl.TestElementImpl
		 * @see org.eclipse.emf.emfstore.client.test.testmodel.impl.TestmodelPackageImpl#getTestElement()
		 * @generated
		 */
		EClass TEST_ELEMENT = eINSTANCE.getTestElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_ELEMENT__NAME = eINSTANCE.getTestElement_Name();

		/**
		 * The meta object literal for the '<em><b>Strings</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_ELEMENT__STRINGS = eINSTANCE.getTestElement_Strings();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_ELEMENT__REFERENCES = eINSTANCE.getTestElement_References();

		/**
		 * The meta object literal for the '<em><b>Contained Elements</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_ELEMENT__CONTAINED_ELEMENTS = eINSTANCE.getTestElement_ContainedElements();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_ELEMENT__REFERENCE = eINSTANCE.getTestElement_Reference();

		/**
		 * The meta object literal for the '<em><b>Contained Element</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_ELEMENT__CONTAINED_ELEMENT = eINSTANCE.getTestElement_ContainedElement();

		/**
		 * The meta object literal for the '<em><b>Other Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_ELEMENT__OTHER_REFERENCE = eINSTANCE.getTestElement_OtherReference();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_ELEMENT__DESCRIPTION = eINSTANCE.getTestElement_Description();

	}

} // TestmodelPackage