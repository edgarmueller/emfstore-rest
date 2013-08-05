/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model.query;

import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * A path query considers a target version besides its source version.
 * 
 * @author wesendon
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESPathQuery extends ESHistoryQuery<ESPathQuery> {

	/**
	 * Returns the target {@link ESPrimaryVersionSpec}.
	 * 
	 * @return the target version
	 */
	ESPrimaryVersionSpec getTarget();

	/**
	 * Sets the target {@link ESPrimaryVersionSpec}.
	 * 
	 * @param target
	 *            the target version to be used by the query
	 */
	void setTarget(ESPrimaryVersionSpec target);
}
