/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model.versioning;

import org.eclipse.emf.emfstore.common.model.util.ModelUtil;

/**
 * Helper class for creating version specifier.
 * 
 * @author wesendon
 */
public final class Versions {

	private Versions() {
	}

	/**
	 * Creates a HEAD revisino for the branch "trunk".
	 * 
	 * @return head version
	 */
	public static HeadVersionSpec createHEAD() {
		return VersioningFactory.eINSTANCE.createHeadVersionSpec();
	}

	/**
	 * Create {@link HeadVersionSpec}.
	 * 
	 * @param branch name of branch
	 * @return version spec
	 */
	public static HeadVersionSpec createHEAD(String branch) {
		HeadVersionSpec headVersionSpec = VersioningFactory.eINSTANCE.createHeadVersionSpec();
		headVersionSpec.setBranch(branch);
		return headVersionSpec;
	}

	/**
	 * Create {@link HeadVersionSpec}.
	 * 
	 * @param versionSpec copies branch name from
	 * @return version spec
	 */
	public static HeadVersionSpec createHEAD(VersionSpec versionSpec) {
		if (versionSpec == null) {
			return createHEAD();
		}
		return createHEAD(versionSpec.getBranch());
	}

	/**
	 * Create {@link PrimaryVersionSpec}.
	 * 
	 * @param branch branch name
	 * @param index verison number
	 * @return version spec
	 */
	public static PrimaryVersionSpec createPRIMARY(String branch, int index) {
		PrimaryVersionSpec spec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
		spec.setIdentifier(index);
		spec.setBranch(branch);
		return spec;
	}

	/**
	 * Create {@link PrimaryVersionSpec}.
	 * 
	 * @param versionSpec copy branch name from
	 * @param index version number
	 * 
	 * @return version spec
	 */
	public static PrimaryVersionSpec createPRIMARY(VersionSpec versionSpec, int index) {
		return createPRIMARY(versionSpec.getBranch(), index);
	}

	/**
	 * Create {@link PrimaryVersionSpec}.
	 * 
	 * @param i version number
	 * @return version spec
	 */
	public static PrimaryVersionSpec createPRIMARY(int i) {
		return createPRIMARY(VersionSpec.BRANCH_DEFAULT_NAME, i);
	}

	/**
	 * Create {@link BranchVersionSpec}.
	 * 
	 * @param value branch name
	 * @return version spec
	 */
	public static BranchVersionSpec createBRANCH(String value) {
		BranchVersionSpec branchSpec = VersioningFactory.eINSTANCE.createBranchVersionSpec();
		branchSpec.setBranch(value);
		return branchSpec;
	}

	/**
	 * Creates {@link BranchVersionSpec}.
	 * 
	 * @param spec copies branch name from
	 * @return version spec
	 */
	public static BranchVersionSpec createBRANCH(VersionSpec spec) {
		return createBRANCH(spec.getBranch());
	}

	/**
	 * Creates {@link AncestorVersionSpec}.
	 * 
	 * @param source source
	 * @param target target
	 * @return version spec
	 */
	public static AncestorVersionSpec createANCESTOR(PrimaryVersionSpec source, PrimaryVersionSpec target) {
		AncestorVersionSpec ancestor = VersioningFactory.eINSTANCE.createAncestorVersionSpec();
		ancestor.setBranch(source.getBranch());
		ancestor.setSource(ModelUtil.clone(source));
		ancestor.setTarget(ModelUtil.clone(target));
		return ancestor;
	}

	/**
	 * Checks whether two versions spec target the same branch.
	 * 
	 * @param spec1
	 *            spec 1
	 * @param spec2
	 *            spec 2
	 * @return true, if same branch
	 */
	public static boolean isSameBranch(VersionSpec spec1, VersionSpec spec2) {
		if (spec1 == null || spec2 == null) {
			return false;
		}
		if (spec1.getBranch() != null && spec1.getBranch().equals(spec2.getBranch())) {
			return true;
		}
		return false;
	}

	/**
	 * Creates {@link TagVersionSpec}.
	 * 
	 * @param tag tag
	 * @param branch branch name
	 * @return version spec
	 */
	public static TagVersionSpec createTAG(String tag, String branch) {
		TagVersionSpec tagSpec = VersioningFactory.eINSTANCE.createTagVersionSpec();
		tagSpec.setBranch(branch);
		tagSpec.setName(tag);
		return tagSpec;
	}
}