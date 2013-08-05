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

import org.eclipse.emf.emfstore.internal.server.model.versioning.HeadVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESHeadVersionSpec;

/**
 * Mapping between {@link ESHeadVersionSpec} and {@link HeadVersionSpec}.
 * 
 * @author emueller
 * 
 */
public class ESHeadVersionSpecImpl extends ESVersionSpecImpl<ESHeadVersionSpec, HeadVersionSpec> implements
	ESHeadVersionSpec {

	/**
	 * Constructor.
	 * 
	 * @param headVersionSpec
	 *            the delegate
	 */
	public ESHeadVersionSpecImpl(HeadVersionSpec headVersionSpec) {
		super(headVersionSpec);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof ESHeadVersionSpecImpl) {
			ESHeadVersionSpecImpl headVersionSpecImpl = (ESHeadVersionSpecImpl) object;
			return toInternalAPI().equals(headVersionSpecImpl.toInternalAPI());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return toInternalAPI().hashCode() + super.hashCode();
	}
}
