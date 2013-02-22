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

import org.eclipse.emf.emfstore.internal.server.model.versioning.AncestorVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESAncestorVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Mapping between {@link ESAncestorVersionSpec} and {@link AncestorVersionSpec}.
 * 
 * @author emueller
 * 
 */
public class ESAncestorVersionSpecImpl extends ESVersionSpecImpl<ESAncestorVersionSpecImpl, AncestorVersionSpec>
	implements ESAncestorVersionSpec {

	/**
	 * Constructor.
	 * 
	 * @param ancestorVersion
	 *            the delegate
	 */
	public ESAncestorVersionSpecImpl(AncestorVersionSpec ancestorVersion) {
		super(ancestorVersion);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESAncestorVersionSpec#getTarget()
	 */
	public ESPrimaryVersionSpec getTarget() {
		return getInternalAPIImpl().getTarget().getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESAncestorVersionSpec#getSource()
	 */
	public ESPrimaryVersionSpec getSource() {
		return getInternalAPIImpl().getSource().getAPIImpl();
	}
}
