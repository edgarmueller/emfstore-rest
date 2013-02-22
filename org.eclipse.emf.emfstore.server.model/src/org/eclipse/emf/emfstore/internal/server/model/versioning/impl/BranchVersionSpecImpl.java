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
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESBranchVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Branch Version Spec</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public class BranchVersionSpecImpl extends VersionSpecImpl implements BranchVersionSpec {

	/**
	 * @generated NOT
	 */
	private ESBranchVersionSpecImpl apiImpl;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected BranchVersionSpecImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VersioningPackage.Literals.BRANCH_VERSION_SPEC;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#getAPIImpl()
	 * 
	 * @generated NOT
	 */
	public ESBranchVersionSpecImpl getAPIImpl() {
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
	public void setAPIImpl(ESBranchVersionSpecImpl esBranchVersionSpecImpl) {
		apiImpl = esBranchVersionSpecImpl;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#createAPIImpl()
	 * 
	 * @generated NOT
	 */
	public ESBranchVersionSpecImpl createAPIImpl() {
		return new ESBranchVersionSpecImpl(this);
	}

} // BranchVersionSpecImpl