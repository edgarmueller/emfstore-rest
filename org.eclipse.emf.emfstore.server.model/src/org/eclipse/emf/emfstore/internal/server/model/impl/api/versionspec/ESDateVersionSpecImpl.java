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

import java.util.Date;

import org.eclipse.emf.emfstore.internal.server.model.versioning.DateVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESDateVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

/**
 * Mapping between {@link ESDateVersionSpecImpl} and {@link ESVersionSpec}.
 * 
 * @author emueller
 * 
 */
public class ESDateVersionSpecImpl extends ESVersionSpecImpl<ESDateVersionSpecImpl, DateVersionSpec> implements
	ESDateVersionSpec {

	/**
	 * Constructor.
	 * 
	 * @param dateVersionSpec
	 *            the delegate
	 */
	public ESDateVersionSpecImpl(DateVersionSpec dateVersionSpec) {
		super(dateVersionSpec);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESDateVersionSpec#getDate()
	 */
	public Date getDate() {
		return getInternalAPIImpl().getDate();
	}

}
