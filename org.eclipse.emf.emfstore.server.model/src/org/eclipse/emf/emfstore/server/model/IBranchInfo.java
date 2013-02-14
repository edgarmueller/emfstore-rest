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
package org.eclipse.emf.emfstore.server.model;

import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Represents information about a specific branch.
 * 
 * @author emueller
 * @author wesendon
 */
public interface IBranchInfo {

	/**
	 * Returns the name of the branch.
	 * 
	 * @return the name of the branch
	 */
	String getName();

	/**
	 * Returns the HEAD version of this branch.
	 * 
	 * @return the {@link ESPrimaryVersionSpec} of the HEAD version of this branch
	 */
	ESPrimaryVersionSpec getHead();

	/**
	 * <p>
	 * Returns the source version of this branch.
	 * </p>
	 * <p>
	 * The source version of a branch is the version this branch was created from.
	 * </p>
	 * 
	 * @return the {@link ESPrimaryVersionSpec} of the source version of this branch
	 */
	ESPrimaryVersionSpec getSource();
}
