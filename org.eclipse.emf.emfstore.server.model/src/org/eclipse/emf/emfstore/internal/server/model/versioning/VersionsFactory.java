package org.eclipse.emf.emfstore.internal.server.model.versioning;

import org.eclipse.emf.emfstore.server.model.versionspec.ESAncestorVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESHeadVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

public class VersionsFactory implements ESVersionFactory {

	public static final VersionsFactory INSTANCE = new VersionsFactory();

	private VersionsFactory() {
	}

	public ESHeadVersionSpec createHEAD() {
		return Versions.createHEAD();
	}

	public ESHeadVersionSpec createHEAD(String branch) {
		return Versions.createHEAD(branch);
	}

	public ESHeadVersionSpec createHEAD(ESVersionSpec versionSpec) {
		return Versions.createHEAD((VersionSpec) versionSpec);
	}

	public ESPrimaryVersionSpec createPRIMARY(String branch, int index) {
		return Versions.createPRIMARY(branch, index);
	}

	public ESPrimaryVersionSpec createPRIMARY(ESVersionSpec versionSpec, int index) {
		return Versions.createPRIMARY((VersionSpec) versionSpec, index);
	}

	public ESPrimaryVersionSpec createPRIMARY(int i) {
		return Versions.createPRIMARY(i);
	}

	public ESBranchVersionSpec createBRANCH(String value) {
		return Versions.createBRANCH(value);
	}

	public ESBranchVersionSpec createBRANCH(ESVersionSpec spec) {
		return Versions.createBRANCH((VersionSpec) spec);
	}

	public ESAncestorVersionSpec createANCESTOR(ESPrimaryVersionSpec source, ESPrimaryVersionSpec target) {
		return Versions.createANCESTOR((PrimaryVersionSpec) source, (PrimaryVersionSpec) target);
	}

	public boolean isSameBranch(ESVersionSpec spec1, ESVersionSpec spec2) {
		return Versions.isSameBranch((VersionSpec) spec1, (VersionSpec) spec2);
	}

	public ESTagVersionSpec createTAG(String tag, String branch) {
		return Versions.createTAG(tag, branch);
	}

}
