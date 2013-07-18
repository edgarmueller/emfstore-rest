/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
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
package org.eclipse.emf.emfstore.server.model.versionspec;

/**
 * A primary version specifier is a version specifier that has
 * a globally unique number that may be used to identify a specific
 * version. Globally unique means that this identifier is unique even
 * in the case of branches.
 * 
 * @author wesendon
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESPrimaryVersionSpec extends ESVersionSpec {

	/**
	 * <p>
	 * Returns the globally unique numeric identifier.
	 * </p>
	 * <p>
	 * This identifier is also unique even in case of branches.
	 * </p>
	 * 
	 * @return the globally unique numeric identifier of this version
	 */
	int getIdentifier();
}
