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
package org.eclipse.emf.emfstore.server.model.versionspec;

import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESVersionsFactoryImpl;

/**
 * <p>
 * Represents a version specifier.
 * </p>
 * <p>
 * A version specifier has a branch name which identifies the respective branch.
 * </p>
 * 
 * @author wesendon
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESVersionSpec {

	/**
	 * HEAD identifier.
	 */
	String HEAD = "HEAD";

	/**
	 * BASE identifier.
	 */
	String BASE = "BASE";

	/**
	 * The default branch name 'trunk'.
	 */
	String BRANCH_DEFAULT_NAME = "trunk";

	/**
	 * Global identifier.
	 */
	String GLOBAL = "___GLOBAL___";

	/**
	 * The factory used for creating {@link ESVersionSpec}s.
	 */
	ESVersionFactory FACTORY = ESVersionsFactoryImpl.INSTANCE;

	/**
	 * Returns the branch name.
	 * 
	 * @return the branch name of this version specifier
	 */
	String getBranch();

}
