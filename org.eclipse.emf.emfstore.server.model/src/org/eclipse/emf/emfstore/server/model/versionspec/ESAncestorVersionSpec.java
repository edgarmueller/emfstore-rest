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
package org.eclipse.emf.emfstore.server.model.versionspec;

/**
 * Represents a version specifier that is used to resolve common ancestor version of two versions.
 * 
 * @author emueller
 * @author wesendon
 */
public interface ESAncestorVersionSpec extends ESVersionSpec {

	/**
	 * Returns the {@link ESPrimaryVersionSpec} of the target to resolve.
	 * 
	 * @return the target version specifier
	 */
	ESPrimaryVersionSpec getTarget();

	/**
	 * Returns the {@link ESPrimaryVersionSpec} of the source to resolve.
	 * 
	 * @return the source version specifier
	 */
	ESPrimaryVersionSpec getSource();

}
