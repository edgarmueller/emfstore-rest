/**
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.internal.client.test.testmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage
 * @generated
 */
public interface TestmodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TestmodelFactory eINSTANCE = org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestmodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Test Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Element</em>'.
	 * @generated
	 */
	TestElement createTestElement();

	/**
	 * Returns a new object of class '<em>Test Element Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Element Container</em>'.
	 * @generated
	 */
	TestElementContainer createTestElementContainer();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TestmodelPackage getTestmodelPackage();

} //TestmodelFactory
