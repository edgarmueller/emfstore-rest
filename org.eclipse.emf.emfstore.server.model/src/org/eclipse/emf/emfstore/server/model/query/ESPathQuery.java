/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model.query;

import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

/**
 * A path query additionally considers a target version beside the source version,
 * i.e. it is possible to specify a version range.
 * 
 * @author wesendon
 * @author emueller
 */
public interface ESPathQuery extends ESHistoryQuery {

	/**
	 * Returns the target {@link IPrimaryVersionSpec}.
	 * 
	 * @return the target version
	 */
	IPrimaryVersionSpec getTarget();

	/**
	 * Sets the target {@link IPrimaryVersionSpec}.
	 * 
	 * @param target
	 *            the target version to be used by the query
	 */
	void setTarget(IPrimaryVersionSpec target);
}
