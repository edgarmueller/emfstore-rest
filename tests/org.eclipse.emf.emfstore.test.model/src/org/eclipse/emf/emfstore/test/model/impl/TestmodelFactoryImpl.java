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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.eclipse.emf.emfstore.test.model.TestmodelFactory;
import org.eclipse.emf.emfstore.test.model.TestmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TestmodelFactoryImpl extends EFactoryImpl implements TestmodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TestmodelFactory init() {
		try
		{
			TestmodelFactory theTestmodelFactory = (TestmodelFactory) EPackage.Registry.INSTANCE
				.getEFactory(TestmodelPackage.eNS_URI);
			if (theTestmodelFactory != null)
			{
				return theTestmodelFactory;
			}
		} catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TestmodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestmodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID())
		{
		case TestmodelPackage.TEST_ELEMENT:
			return createTestElement();
		case TestmodelPackage.TEST_ELEMENT_TO_STRING_MAP:
			return (EObject) createTestElementToStringMap();
		case TestmodelPackage.STRING_TO_STRING_MAP:
			return (EObject) createStringToStringMap();
		case TestmodelPackage.TEST_ELEMENT_TO_TEST_ELEMENT_MAP:
			return (EObject) createTestElementToTestElementMap();
		case TestmodelPackage.STRING_TO_TEST_ELEMENT_MAP:
			return (EObject) createStringToTestElementMap();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement createTestElement() {
		TestElementImpl testElement = new TestElementImpl();
		return testElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<TestElement, String> createTestElementToStringMap() {
		TestElementToStringMapImpl testElementToStringMap = new TestElementToStringMapImpl();
		return testElementToStringMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, String> createStringToStringMap() {
		StringToStringMapImpl stringToStringMap = new StringToStringMapImpl();
		return stringToStringMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<TestElement, TestElement> createTestElementToTestElementMap() {
		TestElementToTestElementMapImpl testElementToTestElementMap = new TestElementToTestElementMapImpl();
		return testElementToTestElementMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, TestElement> createStringToTestElementMap() {
		StringToTestElementMapImpl stringToTestElementMap = new StringToTestElementMapImpl();
		return stringToTestElementMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestmodelPackage getTestmodelPackage() {
		return (TestmodelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TestmodelPackage getPackage() {
		return TestmodelPackage.eINSTANCE;
	}

} // TestmodelFactoryImpl
