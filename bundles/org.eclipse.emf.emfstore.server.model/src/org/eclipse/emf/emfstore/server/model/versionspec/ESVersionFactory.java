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

import org.eclipse.emf.emfstore.common.model.ESFactory;

/**
 * A factory for creating version specifiers.
 * 
 * @author wesendon
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESVersionFactory extends ESFactory {

	/**
	 * Creates a HEAD revision for the branch "trunk".
	 * 
	 * @return head version
	 */
	ESHeadVersionSpec createHEAD();

	/**
	 * Create {@link ESHeadVersionSpec}.
	 * 
	 * @param branch
	 *            name of branch
	 * @return version spec
	 */
	ESHeadVersionSpec createHEAD(String branch);

	/**
	 * Create {@link ESHeadVersionSpec}.
	 * 
	 * @param versionSpec
	 *            copies branch name from
	 * @return version spec
	 */
	ESHeadVersionSpec createHEAD(ESVersionSpec versionSpec);

	/**
	 * Create {@link ESPrimaryVersionSpec}.
	 * 
	 * @param branch
	 *            branch name
	 * @param index
	 *            verison number
	 * @return version spec
	 */
	ESPrimaryVersionSpec createPRIMARY(String branch, int index);

	/**
	 * Create {@link ESPrimaryVersionSpec}.
	 * 
	 * @param versionSpec
	 *            copy branch name from
	 * @param index
	 *            version number
	 * 
	 * @return version spec
	 */
	ESPrimaryVersionSpec createPRIMARY(ESVersionSpec versionSpec, int index);

	/**
	 * Create {@link ESPrimaryVersionSpec}.
	 * 
	 * @param i
	 *            version number
	 * @return version spec
	 */
	ESPrimaryVersionSpec createPRIMARY(int i);

	/**
	 * Create {@link ESBranchVersionSpec}.
	 * 
	 * @param value
	 *            branch name
	 * @return version spec
	 */
	ESBranchVersionSpec createBRANCH(String value);

	/**
	 * Creates {@link ESBranchVersionSpec}.
	 * 
	 * @param spec
	 *            copies branch name from
	 * @return version spec
	 */
	ESBranchVersionSpec createBRANCH(ESVersionSpec spec);

	/**
	 * Creates {@link ESAncestorVersionSpec}.
	 * 
	 * @param source
	 *            source
	 * @param target
	 *            target
	 * @return version spec
	 */
	ESAncestorVersionSpec createANCESTOR(ESPrimaryVersionSpec source,
		ESPrimaryVersionSpec target);

	/**
	 * Checks whether two versions spec target the same branch.
	 * 
	 * @param spec1
	 *            spec 1
	 * @param spec2
	 *            spec 2
	 * @return true, if same branch
	 */
	boolean isSameBranch(ESVersionSpec spec1, ESVersionSpec spec2);

	/**
	 * Creates {@link ESTagVersionSpec}.
	 * 
	 * @param tag
	 *            tag
	 * @param branch
	 *            branch name
	 * @return version spec
	 */
	ESTagVersionSpec createTAG(String tag, String branch);

	/**
	 * Creates an {@link ESPagedUpdateVersionSpec}.
	 * 
	 * @param baseVersion
	 *            the base version from which on to count the maximally allowed changes
	 * @param maxChanges
	 *            the number of maximally allowed changes
	 * @return the created version spec
	 */
	ESPagedUpdateVersionSpec createPAGEDUPDATE(ESPrimaryVersionSpec baseVersion, int maxChanges);
}
