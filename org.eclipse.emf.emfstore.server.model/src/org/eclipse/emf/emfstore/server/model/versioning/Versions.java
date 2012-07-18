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
	public static HeadVersionSpec HEAD_VERSION() {
		return VersioningFactory.eINSTANCE.createHeadVersionSpec();
	}

	public static HeadVersionSpec HEAD_VERSION(String branch) {
		HeadVersionSpec headVersionSpec = VersioningFactory.eINSTANCE
				.createHeadVersionSpec();
		headVersionSpec.setBranch(branch);
		return headVersionSpec;
	}

	public static HeadVersionSpec HEAD_VERSION(VersionSpec versionSpec) {
		if (versionSpec == null) {
			return HEAD_VERSION();
		}
		return HEAD_VERSION(versionSpec.getBranch());
	}

	public static PrimaryVersionSpec PRIMARY(String branch, int index) {
		PrimaryVersionSpec spec = VersioningFactory.eINSTANCE
				.createPrimaryVersionSpec();
		spec.setIdentifier(index);
		spec.setBranch(branch);
		return spec;
	}

	public static PrimaryVersionSpec PRIMARY(VersionSpec versionSpec, int index) {
		return PRIMARY(versionSpec.getBranch(), index);
	}

	public static PrimaryVersionSpec PRIMARY(int i) {
		return PRIMARY(VersionSpec.BRANCH_DEFAULT_NAME, i);
	}

	public static BranchVersionSpec BRANCH(String value) {
		BranchVersionSpec branchSpec = VersioningFactory.eINSTANCE
				.createBranchVersionSpec();
		branchSpec.setBranch(value);
		return branchSpec;
	}

	public static BranchVersionSpec BRANCH(VersionSpec head) {
		return BRANCH(head.getBranch());
	}

	public static AncestorVersionSpec ANCESTOR(PrimaryVersionSpec source,
			PrimaryVersionSpec target) {
		AncestorVersionSpec ancestor = VersioningFactory.eINSTANCE
				.createAncestorVersionSpec();
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
		if (spec1.getBranch() != null
				&& spec1.getBranch().equals(spec2.getBranch())) {
			return true;
		}
		return false;
	}

	public static TagVersionSpec TAG(String tag, String branch) {
		TagVersionSpec tagSpec = VersioningFactory.eINSTANCE
				.createTagVersionSpec();
		tagSpec.setBranch(branch);
		tagSpec.setName(tag);
		return tagSpec;
	}
}
