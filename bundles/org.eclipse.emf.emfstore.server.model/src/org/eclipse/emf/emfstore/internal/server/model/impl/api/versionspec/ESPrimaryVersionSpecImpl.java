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

import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Mapping between {@link ESPrimaryVersionSpec} and {@link PrimaryVersionSpec}.
 * 
 * @author emueller
 * 
 */
public class ESPrimaryVersionSpecImpl extends ESVersionSpecImpl<ESPrimaryVersionSpec, PrimaryVersionSpec> implements
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
		return toInternalAPI().getIdentifier();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof ESPrimaryVersionSpecImpl) {
			final ESPrimaryVersionSpecImpl otherPrimaryVersionSpecImpl = (ESPrimaryVersionSpecImpl) object;
			if (sameIdentifier(otherPrimaryVersionSpecImpl) && sameBranch(otherPrimaryVersionSpecImpl)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return toInternalAPI().hashCode() + super.hashCode() + getIdentifier();
	}

	private boolean sameIdentifier(ESPrimaryVersionSpecImpl otherPrimaryVersionSpecImpl) {
		return toInternalAPI().getIdentifier() == otherPrimaryVersionSpecImpl.toInternalAPI().getIdentifier();
	}

	private boolean sameBranch(ESPrimaryVersionSpecImpl otherPrimaryVersionSpecImpl) {
		final String branch = toInternalAPI().getBranch();
		return branch != null && branch.equals(otherPrimaryVersionSpecImpl.toInternalAPI().getBranch());
	}
}
