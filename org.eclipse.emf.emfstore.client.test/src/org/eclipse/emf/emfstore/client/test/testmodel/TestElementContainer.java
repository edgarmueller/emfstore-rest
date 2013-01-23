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
package org.eclipse.emf.emfstore.client.test.testmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Element Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElementContainer#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElementContainer()
 * @model
 * @generated
 */
public interface TestElementContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestmodelPackage#getTestElementContainer_Elements()
	 * @see org.eclipse.emf.emfstore.client.test.testmodel.TestElement#getContainer
	 * @model opposite="container" containment="true"
	 * @generated
	 */
	EList<TestElement> getElements();

} // TestElementContainer
