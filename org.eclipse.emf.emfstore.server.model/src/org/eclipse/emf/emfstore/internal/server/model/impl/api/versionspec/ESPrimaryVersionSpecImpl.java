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
package org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec;

import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Mapping between {@link ESPrimaryVersionSpec} and {@link PrimaryVersionSpec}.
 * 
 * @author emueller
 * 
 */
public class ESPrimaryVersionSpecImpl extends ESVersionSpecImpl<ESPrimaryVersionSpecImpl, PrimaryVersionSpec> implements
	ESPrimaryVersionSpec {

	/**
	 * Constructor.
	 * 
	 * @param primaryVersionSpec
	 *            the delegate
	 */
	public ESPrimaryVersionSpecImpl(PrimaryVersionSpec primaryVersionSpec) {
		super(primaryVersionSpec);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec#getIdentifier()
	 */
	public int getIdentifier() {
		return getInternalAPIImpl().getIdentifier();
	}

}
