/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation, API annotations
 *******************************************************************************/
package org.eclipse.emf.emfstore.server.model.versionspec;

/**
 * <p>
 * Represents a version specifier that enables the pagination of an update.
 * </p>
 * <p>
 * Pagination is recommended in case of large incoming {@link org.eclipse.emf.emfstore.server.model.ESChangePackage}s
 * that possibly may not fit into memory on the client side.
 * </p>
 * 
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESPagedUpdateVersionSpec extends ESVersionSpec {

	/**
	 * The base version from which on counting of changes should occur.
	 * 
	 * @return the {@link ESPrimaryVersionSpec} that contains exactly the number
	 *         of maximally allowed changes or less
	 */
	ESPrimaryVersionSpec getBaseVersion();

	/**
	 * Returns the maximum of allowed changes that make up a page.
	 * 
	 * @return the maximum of allowed changes
	 */
	int getMaxChanges();
}
