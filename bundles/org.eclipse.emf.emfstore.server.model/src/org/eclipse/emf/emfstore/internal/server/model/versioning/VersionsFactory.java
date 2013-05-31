package org.eclipse.emf.emfstore.internal.server.model.versioning;

import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

public class VersionsFactory {

	public static final VersionsFactory INSTANCE = new VersionsFactory();

	private VersionsFactory() {
	}

	public HeadVersionSpec createHEAD() {
		return Versions.createHEAD();
	}

	public HeadVersionSpec createHEAD(String branch) {
		return Versions.createHEAD(branch);
	}

	public HeadVersionSpec createHEAD(ESVersionSpec versionSpec) {
		return Versions.createHEAD((VersionSpec) versionSpec);
	}

	public BranchVersionSpec createBRANCH(String value) {
		return Versions.createBRANCH(value);
	}

	public BranchVersionSpec createBRANCH(ESVersionSpec spec) {
		return Versions.createBRANCH((VersionSpec) spec);
	}

	public boolean isSameBranch(VersionSpec spec1, VersionSpec spec2) {
		return Versions.isSameBranch(spec1, spec2);
	}

	public TagVersionSpec createTAG(String tag, String branch) {
		return Versions.createTAG(tag, branch);
	}

}
