/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.impl.api;

import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Mapping between {@link ESBranchInfo} and {@link BranchInfo}.
 * 
 * @author emueller
 * 
 */
public class ESBranchInfoImpl extends AbstractAPIImpl<ESBranchInfoImpl, BranchInfo> implements ESBranchInfo {

	/**
	 * Constructor.
	 * 
	 * @param branchInfo
	 *            the delegate
	 */
	public ESBranchInfoImpl(BranchInfo branchInfo) {
		super(branchInfo);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESBranchInfo#getName()
	 */
	public String getName() {
		return getInternalAPIImpl().getName();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESBranchInfo#getHead()
	 */
	public ESPrimaryVersionSpec getHead() {
		return getInternalAPIImpl().getHead().getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESBranchInfo#getSource()
	 */
	public ESPrimaryVersionSpec getSource() {
		return getInternalAPIImpl().getSource().getAPIImpl();
	}

}
