/**
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.fuzzy.emf.config;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Diff Report</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport#getDiffs <em> Diffs</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getDiffReport()
 * @model
 * @generated
 */
public interface DiffReport extends EObject {
	/**
	 * Returns the value of the '<em><b>Diffs</b></em>' containment reference
	 * list. The list contents are of type {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diffs</em>' containment reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Diffs</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getDiffReport_Diffs()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestDiff> getDiffs();

} // DiffReport