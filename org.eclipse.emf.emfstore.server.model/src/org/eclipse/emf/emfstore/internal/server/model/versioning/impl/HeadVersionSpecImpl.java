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
package org.eclipse.emf.emfstore.internal.server.model.versioning.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESHeadVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HeadVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Head Version Spec</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * </p>
 * 
 * @generated
 */
public class HeadVersionSpecImpl extends VersionSpecImpl implements HeadVersionSpec {

	/**
	 * @generated NOT
	 */
	private ESHeadVersionSpecImpl apiImpl;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected HeadVersionSpecImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VersioningPackage.Literals.HEAD_VERSION_SPEC;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#getAPIImpl()
	 * 
	 * @generated NOT
	 */
	public ESHeadVersionSpecImpl getAPIImpl() {

		if (apiImpl == null) {
			apiImpl = createAPIImpl();
		}

		return apiImpl;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#setAPIImpl(java.lang.Object)
	 * 
	 * @generated NOT
	 */
	public void setAPIImpl(ESHeadVersionSpecImpl esHeadVersionSpecImpl) {
		apiImpl = esHeadVersionSpecImpl;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#createAPIImpl()
	 * 
	 * @generated NOTn
	 */
	public ESHeadVersionSpecImpl createAPIImpl() {
		return new ESHeadVersionSpecImpl(this);
	}

} // HeadVersionSpecImpl