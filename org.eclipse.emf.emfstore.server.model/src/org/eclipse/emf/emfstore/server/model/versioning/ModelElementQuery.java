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
package org.eclipse.emf.emfstore.server.model.versioning;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.server.model.api.query.IModelElementQuery;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Model Element Query</b></em>'.
 * 
 * @extends IModelElementQuery
 *          <!--
 *          end-user-doc -->
 * 
 *          <p>
 *          The following features are supported:
 *          <ul>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.ModelElementQuery#getModelElements <em>Model
 *          Elements </em>}</li>
 *          </ul>
 *          </p>
 * 
 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getModelElementQuery()
 * @model
 * @generated
 */
public interface ModelElementQuery extends RangeQuery, IModelElementQuery {
	/**
	 * Returns the value of the '<em><b>Model Elements</b></em>' containment
	 * reference list. The list contents are of type {@link org.eclipse.emf.emfstore.internal.common.model.ModelElementId}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Elements</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Model Elements</em>' containment reference
	 *         list.
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getModelElementQuery_ModelElements()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<ModelElementId> getModelElements();

} // ModelElementQuery