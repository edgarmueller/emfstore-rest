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
package org.eclipse.emf.emfstore.test.model.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.emfstore.test.model.TestElement;
import org.eclipse.emf.emfstore.test.model.TestmodelFactory;
import org.eclipse.emf.emfstore.test.model.TestmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TestmodelPackageImpl extends EPackageImpl implements TestmodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testElementToStringMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringToStringMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testElementToTestElementMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringToTestElementMapEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.emfstore.test.model.TestmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TestmodelPackageImpl() {
		super(eNS_URI, TestmodelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link TestmodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TestmodelPackage init() {
		if (isInited)
			return (TestmodelPackage) EPackage.Registry.INSTANCE.getEPackage(TestmodelPackage.eNS_URI);

		// Obtain or create and register package
		TestmodelPackageImpl theTestmodelPackage = (TestmodelPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TestmodelPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI) : new TestmodelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theTestmodelPackage.createPackageContents();

		// Initialize created meta-data
		theTestmodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTestmodelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TestmodelPackage.eNS_URI, theTestmodelPackage);
		return theTestmodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestElement() {
		return testElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestElement_Name() {
		return (EAttribute) testElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestElement_Strings() {
		return (EAttribute) testElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_References() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_ContainedElements() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_Reference() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_ContainedElement() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_OtherReference() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestElement_Description() {
		return (EAttribute) testElementEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_Container() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_SrefContainer() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_ElementMap() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_StringToStringMap() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_ElementToStringMap() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_StringToElementMap() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_NonContained_NTo1() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_NonContained_1ToN() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_NonContained_NToM() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_NonContained_MToN() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_ContainedElements2() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_Container2() {
		return (EReference) testElementEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElement_ContainedElements_NoOpposite()
	{
		return (EReference) testElementEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestElementToStringMap() {
		return testElementToStringMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestElementToStringMap_Value() {
		return (EAttribute) testElementToStringMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElementToStringMap_Key() {
		return (EReference) testElementToStringMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringToStringMap() {
		return stringToStringMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringToStringMap_Key() {
		return (EAttribute) stringToStringMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringToStringMap_Value() {
		return (EAttribute) stringToStringMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestElementToTestElementMap() {
		return testElementToTestElementMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElementToTestElementMap_Value() {
		return (EReference) testElementToTestElementMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestElementToTestElementMap_Key() {
		return (EReference) testElementToTestElementMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringToTestElementMap() {
		return stringToTestElementMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStringToTestElementMap_Value() {
		return (EReference) stringToTestElementMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringToTestElementMap_Key() {
		return (EAttribute) stringToTestElementMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestmodelFactory getTestmodelFactory() {
		return (TestmodelFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		testElementEClass = createEClass(TEST_ELEMENT);
		createEAttribute(testElementEClass, TEST_ELEMENT__NAME);
		createEAttribute(testElementEClass, TEST_ELEMENT__STRINGS);
		createEReference(testElementEClass, TEST_ELEMENT__REFERENCES);
		createEReference(testElementEClass, TEST_ELEMENT__CONTAINED_ELEMENTS);
		createEReference(testElementEClass, TEST_ELEMENT__REFERENCE);
		createEReference(testElementEClass, TEST_ELEMENT__CONTAINED_ELEMENT);
		createEReference(testElementEClass, TEST_ELEMENT__OTHER_REFERENCE);
		createEAttribute(testElementEClass, TEST_ELEMENT__DESCRIPTION);
		createEReference(testElementEClass, TEST_ELEMENT__CONTAINER);
		createEReference(testElementEClass, TEST_ELEMENT__SREF_CONTAINER);
		createEReference(testElementEClass, TEST_ELEMENT__ELEMENT_MAP);
		createEReference(testElementEClass, TEST_ELEMENT__STRING_TO_STRING_MAP);
		createEReference(testElementEClass, TEST_ELEMENT__ELEMENT_TO_STRING_MAP);
		createEReference(testElementEClass, TEST_ELEMENT__STRING_TO_ELEMENT_MAP);
		createEReference(testElementEClass, TEST_ELEMENT__NON_CONTAINED_NTO1);
		createEReference(testElementEClass, TEST_ELEMENT__NON_CONTAINED_1TO_N);
		createEReference(testElementEClass, TEST_ELEMENT__NON_CONTAINED_NTO_M);
		createEReference(testElementEClass, TEST_ELEMENT__NON_CONTAINED_MTO_N);
		createEReference(testElementEClass, TEST_ELEMENT__CONTAINED_ELEMENTS2);
		createEReference(testElementEClass, TEST_ELEMENT__CONTAINER2);
		createEReference(testElementEClass, TEST_ELEMENT__CONTAINED_ELEMENTS_NO_OPPOSITE);

		testElementToStringMapEClass = createEClass(TEST_ELEMENT_TO_STRING_MAP);
		createEAttribute(testElementToStringMapEClass, TEST_ELEMENT_TO_STRING_MAP__VALUE);
		createEReference(testElementToStringMapEClass, TEST_ELEMENT_TO_STRING_MAP__KEY);

		stringToStringMapEClass = createEClass(STRING_TO_STRING_MAP);
		createEAttribute(stringToStringMapEClass, STRING_TO_STRING_MAP__KEY);
		createEAttribute(stringToStringMapEClass, STRING_TO_STRING_MAP__VALUE);

		testElementToTestElementMapEClass = createEClass(TEST_ELEMENT_TO_TEST_ELEMENT_MAP);
		createEReference(testElementToTestElementMapEClass, TEST_ELEMENT_TO_TEST_ELEMENT_MAP__VALUE);
		createEReference(testElementToTestElementMapEClass, TEST_ELEMENT_TO_TEST_ELEMENT_MAP__KEY);

		stringToTestElementMapEClass = createEClass(STRING_TO_TEST_ELEMENT_MAP);
		createEReference(stringToTestElementMapEClass, STRING_TO_TEST_ELEMENT_MAP__VALUE);
		createEAttribute(stringToTestElementMapEClass, STRING_TO_TEST_ELEMENT_MAP__KEY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		testElementEClass.getESuperTypes().add(ecorePackage.getEObject());

		// Initialize classes and features; add operations and parameters
		initEClass(testElementEClass, TestElement.class,
			"TestElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getTestElement_Name(),
			ecorePackage.getEString(),
			"name", null, 0, 1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getTestElement_Strings(),
			ecorePackage.getEString(),
			"strings", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_References(),
			this.getTestElement(),
			null,
			"references", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_ContainedElements(),
			this.getTestElement(),
			this.getTestElement_Container(),
			"containedElements", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_Reference(),
			this.getTestElement(),
			null,
			"reference", null, 0, 1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_ContainedElement(),
			this.getTestElement(),
			this.getTestElement_SrefContainer(),
			"containedElement", null, 0, 1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_OtherReference(),
			this.getTestElement(),
			null,
			"otherReference", null, 0, 1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getTestElement_Description(),
			ecorePackage.getEString(),
			"description", null, 0, 1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_Container(),
			this.getTestElement(),
			this.getTestElement_ContainedElements(),
			"container", null, 0, 1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_SrefContainer(),
			this.getTestElement(),
			this.getTestElement_ContainedElement(),
			"srefContainer", null, 0, 1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_ElementMap(),
			this.getTestElementToTestElementMap(),
			null,
			"elementMap", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_StringToStringMap(),
			this.getStringToStringMap(),
			null,
			"stringToStringMap", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_ElementToStringMap(),
			this.getTestElementToStringMap(),
			null,
			"elementToStringMap", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_StringToElementMap(),
			this.getStringToTestElementMap(),
			null,
			"stringToElementMap", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_NonContained_NTo1(),
			this.getTestElement(),
			this.getTestElement_NonContained_1ToN(),
			"nonContained_NTo1", null, 0, 1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_NonContained_1ToN(),
			this.getTestElement(),
			this.getTestElement_NonContained_NTo1(),
			"nonContained_1ToN", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_NonContained_NToM(),
			this.getTestElement(),
			this.getTestElement_NonContained_MToN(),
			"nonContained_NToM", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_NonContained_MToN(),
			this.getTestElement(),
			this.getTestElement_NonContained_NToM(),
			"nonContained_MToN", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_ContainedElements2(),
			this.getTestElement(),
			this.getTestElement_Container2(),
			"containedElements2", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_Container2(),
			this.getTestElement(),
			this.getTestElement_ContainedElements2(),
			"container2", null, 0, 1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElement_ContainedElements_NoOpposite(),
			this.getTestElement(),
			null,
			"containedElements_NoOpposite", null, 0, -1, TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(testElementToStringMapEClass, Map.Entry.class,
			"TestElementToStringMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getTestElementToStringMap_Value(),
			ecorePackage.getEString(),
			"value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElementToStringMap_Key(),
			this.getTestElement(),
			null,
			"key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stringToStringMapEClass, Map.Entry.class,
			"StringToStringMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getStringToStringMap_Key(),
			ecorePackage.getEString(),
			"key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getStringToStringMap_Value(),
			ecorePackage.getEString(),
			"value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(testElementToTestElementMapEClass, Map.Entry.class,
			"TestElementToTestElementMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getTestElementToTestElementMap_Value(),
			this.getTestElement(),
			null,
			"value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getTestElementToTestElementMap_Key(),
			this.getTestElement(),
			null,
			"key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stringToTestElementMapEClass, Map.Entry.class,
			"StringToTestElementMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getStringToTestElementMap_Value(),
			this.getTestElement(),
			null,
			"value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getStringToTestElementMap_Key(),
			ecorePackage.getEString(),
			"key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //TestmodelPackageImpl
