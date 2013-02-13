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
 * Represents a version specifier that identifier a specific tag.
 * 
 * @author wesendon
 * @author emueller
 */
public interface ITagVersionSpec extends IVersionSpec {

	/**
	 * Return the name of the tag name this version specifier is associated with.
	 * 
	 * @return the name of the tag
	 */
	// TODO OTS currently called getName, rename to getTag?
	String getName();
}
