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

import org.eclipse.emf.emfstore.common.model.IEMFStoreFactory;

/**
 * A factory for creating version specifiers.
 * 
 * @author wesendon
 * @author emueller
 */
public interface IVersionFactory extends IEMFStoreFactory {

	/**
	 * Creates a HEAD revision for the branch "trunk".
	 * 
	 * @return head version
	 */
	IHeadVersionSpec createHEAD();

	/**
	 * Create {@link IHeadVersionSpec}.
	 * 
	 * @param branch
	 *            name of branch
	 * @return version spec
	 */
	IHeadVersionSpec createHEAD(String branch);

	/**
	 * Create {@link IHeadVersionSpec}.
	 * 
	 * @param versionSpec
	 *            copies branch name from
	 * @return version spec
	 */
	IHeadVersionSpec createHEAD(IVersionSpec versionSpec);

	/**
	 * Create {@link IPrimaryVersionSpec}.
	 * 
	 * @param branch
	 *            branch name
	 * @param index
	 *            verison number
	 * @return version spec
	 */
	IPrimaryVersionSpec createPRIMARY(String branch, int index);

	/**
	 * Create {@link IPrimaryVersionSpec}.
	 * 
	 * @param versionSpec
	 *            copy branch name from
	 * @param index
	 *            version number
	 * 
	 * @return version spec
	 */
	IPrimaryVersionSpec createPRIMARY(IVersionSpec versionSpec, int index);

	/**
	 * Create {@link IPrimaryVersionSpec}.
	 * 
	 * @param i
	 *            version number
	 * @return version spec
	 */
	IPrimaryVersionSpec createPRIMARY(int i);

	/**
	 * Create {@link IBranchVersionSpec}.
	 * 
	 * @param value
	 *            branch name
	 * @return version spec
	 */
	IBranchVersionSpec createBRANCH(String value);

	/**
	 * Creates {@link IBranchVersionSpec}.
	 * 
	 * @param spec
	 *            copies branch name from
	 * @return version spec
	 */
	IBranchVersionSpec createBRANCH(IVersionSpec spec);

	/**
	 * Creates {@link IAncestorVersionSpec}.
	 * 
	 * @param source
	 *            source
	 * @param target
	 *            target
	 * @return version spec
	 */
	IAncestorVersionSpec createANCESTOR(IPrimaryVersionSpec source,
		IPrimaryVersionSpec target);

	/**
	 * Checks whether two versions spec target the same branch.
	 * 
	 * @param spec1
	 *            spec 1
	 * @param spec2
	 *            spec 2
	 * @return true, if same branch
	 */
	boolean isSameBranch(IVersionSpec spec1, IVersionSpec spec2);

	/**
	 * Creates {@link ITagVersionSpec}.
	 * 
	 * @param tag
	 *            tag
	 * @param branch
	 *            branch name
	 * @return version spec
	 */
	ITagVersionSpec createTAG(String tag, String branch);
}
