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

import org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPagedUpdateVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Mapping between {@link ESPagedUpdateVersionSpec} and {@link PagedUpdateVersionSpec}.
 * 
 * @author emueller
 */
public class ESPagedUpdateVersionSpecImpl extends ESVersionSpecImpl<ESPagedUpdateVersionSpec, PagedUpdateVersionSpec>
	implements
	ESPagedUpdateVersionSpec {

	/**
	 * Constructor.
	 * 
	 * @param versionSpec
	 *            the delegate
	 */
	public ESPagedUpdateVersionSpecImpl(PagedUpdateVersionSpec versionSpec) {
		super(versionSpec);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESPagedUpdateVersionSpec#getBaseVersion()
	 */
	public ESPrimaryVersionSpec getBaseVersion() {
		return toInternalAPI().getBaseVersionSpec().toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESPagedUpdateVersionSpec#getMaxChanges()
	 */
	public int getMaxChanges() {
		return toInternalAPI().getMaxChanges();
	}

}
