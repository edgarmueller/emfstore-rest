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
package org.eclipse.emf.emfstore.internal.server.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.emfstore.internal.common.model.impl.UniqueIdentifierImpl;
import org.eclipse.emf.emfstore.internal.server.model.ModelPackage;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESGlobalProjectIdImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Project Id</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public class ProjectIdImpl extends UniqueIdentifierImpl implements ProjectId {

	/**
	 * @generated NOT
	 */
	private ESGlobalProjectIdImpl apiImpl;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProjectIdImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.PROJECT_ID;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#toAPI()
	 * 
	 * @generated NOT
	 */
	public ESGlobalProjectIdImpl toAPI() {
		if (apiImpl == null) {
			apiImpl = createAPI();
		}
		return apiImpl;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#createAPI()
	 * 
	 * @generated NOT
	 */
	public ESGlobalProjectIdImpl createAPI() {
		return new ESGlobalProjectIdImpl(this);
	}

} // ProjectIdImpl