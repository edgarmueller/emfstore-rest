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

import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;

/**
 * Mapping between {@link ESTagVersionSpec} and {@link TagVersionSpec}.
 * 
 * @author emueller
 * 
 */
public class ESTagVersionSpecImpl extends ESVersionSpecImpl<ESTagVersionSpecImpl, TagVersionSpec> implements
	ESTagVersionSpec {

	/**
	 * Constructor.
	 * 
	 * @param tagVersion
	 *            the delegate
	 */
	public ESTagVersionSpecImpl(TagVersionSpec tagVersion) {
		super(tagVersion);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec#getName()
	 */
	public String getName() {
		return getInternalAPIImpl().getName();
	}

}
