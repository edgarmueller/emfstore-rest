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
package org.eclipse.emf.emfstore.internal.server.model.versioning;

import org.eclipse.emf.emfstore.internal.server.model.api.query.IRangeQuery;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Range Query</b></em>'.
 * 
 * @extends IRangeQuery
 *          <!-- end-user-doc -->
 * 
 *          <p>
 *          The following features are supported:
 *          <ul>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery#getUpperLimit <em>Upper Limit</em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery#getLowerLimit <em>Lower Limit</em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery#isIncludeIncoming <em>Include
 *          Incoming</em>}</li>
 *          <li>{@link org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery#isIncludeOutgoing <em>Include
 *          Outgoing</em>}</li>
 *          </ul>
 *          </p>
 * 
 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getRangeQuery()
 * @model
 * @generated
 */
public interface RangeQuery extends HistoryQuery, IRangeQuery {
	/**
	 * Returns the value of the '<em><b>Upper Limit</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Limit</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Upper Limit</em>' attribute.
	 * @see #setUpperLimit(int)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getRangeQuery_UpperLimit()
	 * @model
	 * @generated
	 */
	int getUpperLimit();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery#getUpperLimit
	 * <em>Upper Limit</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Upper Limit</em>' attribute.
	 * @see #getUpperLimit()
	 * @generated
	 */
	void setUpperLimit(int value);

	/**
	 * Returns the value of the '<em><b>Lower Limit</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Limit</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Lower Limit</em>' attribute.
	 * @see #setLowerLimit(int)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getRangeQuery_LowerLimit()
	 * @model
	 * @generated
	 */
	int getLowerLimit();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery#getLowerLimit
	 * <em>Lower Limit</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Lower Limit</em>' attribute.
	 * @see #getLowerLimit()
	 * @generated
	 */
	void setLowerLimit(int value);

	/**
	 * Returns the value of the '<em><b>Include Incoming</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include Incoming</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Include Incoming</em>' attribute.
	 * @see #setIncludeIncoming(boolean)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getRangeQuery_IncludeIncoming()
	 * @model
	 * @generated
	 */
	boolean isIncludeIncoming();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery#isIncludeIncoming
	 * <em>Include Incoming</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Include Incoming</em>' attribute.
	 * @see #isIncludeIncoming()
	 * @generated
	 */
	void setIncludeIncoming(boolean value);

	/**
	 * Returns the value of the '<em><b>Include Outgoing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include Outgoing</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Include Outgoing</em>' attribute.
	 * @see #setIncludeOutgoing(boolean)
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#getRangeQuery_IncludeOutgoing()
	 * @model
	 * @generated
	 */
	boolean isIncludeOutgoing();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery#isIncludeOutgoing
	 * <em>Include Outgoing</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Include Outgoing</em>' attribute.
	 * @see #isIncludeOutgoing()
	 * @generated
	 */
	void setIncludeOutgoing(boolean value);

} // RangeQuery