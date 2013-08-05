/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec;

import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

/**
 * Mapping between {@link ESVersionSpecImpl} and {@link VersionSpec}.
 * 
 * @author emueller
 * 
 * @param <U> a subtype of the API implementation class {@link ESVersionSpecImpl}
 * @param <T> a subtype of the internal type {@link VersionSpec}
 */
public class ESVersionSpecImpl<U extends ESVersionSpec, T extends VersionSpec & APIDelegate<U>>
	extends AbstractAPIImpl<U, T> implements ESVersionSpec {

	/**
	 * Constructor.
	 * 
	 * @param versionSpec
	 *            the delegate
	 */
	public ESVersionSpecImpl(T versionSpec) {
		super(versionSpec);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec#getBranch()
	 */
	public String getBranch() {
		return toInternalAPI().getBranch();
	}

}
