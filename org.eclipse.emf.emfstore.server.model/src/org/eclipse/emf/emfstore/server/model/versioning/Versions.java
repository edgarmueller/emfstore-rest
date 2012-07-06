package org.eclipse.emf.emfstore.server.model.versioning;

import org.eclipse.emf.ecore.util.EcoreUtil;

public class Versions {

	public static HeadVersionSpec HEAD_VERSION() {
		return VersioningFactory.eINSTANCE.createHeadVersionSpec();
	}

	public static HeadVersionSpec HEAD_VERSION(String branch) {
		HeadVersionSpec headVersionSpec = VersioningFactory.eINSTANCE.createHeadVersionSpec();
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
		PrimaryVersionSpec spec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
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
		BranchVersionSpec branchSpec = VersioningFactory.eINSTANCE.createBranchVersionSpec();
		branchSpec.setBranch(value);
		return branchSpec;
	}

	public static BranchVersionSpec BRANCH(VersionSpec head) {
		return BRANCH(head.getBranch());
	}

	public static AncestorVersionSpec ANCESTOR(PrimaryVersionSpec source, PrimaryVersionSpec target) {
		AncestorVersionSpec ancestor = VersioningFactory.eINSTANCE.createAncestorVersionSpec();
		ancestor.setBranch(source.getBranch());
		ancestor.setSource(EcoreUtil.copy(source));
		ancestor.setTarget(EcoreUtil.copy(target));
		return ancestor;
	}

	public static boolean isSameBranch(VersionSpec spec1, VersionSpec spec2) {
		if (spec1 == null || spec2 == null) {
			return false;
		}
		if (spec1.getBranch() != null && spec1.getBranch().equals(spec2.getBranch())) {
			return true;
		}
		return false;
	}

	public static TagVersionSpec TAG(String tag, String branch) {
		TagVersionSpec tagSpec = VersioningFactory.eINSTANCE.createTagVersionSpec();
		tagSpec.setBranch(branch);
		tagSpec.setName(tag);
		return tagSpec;
	}
}
