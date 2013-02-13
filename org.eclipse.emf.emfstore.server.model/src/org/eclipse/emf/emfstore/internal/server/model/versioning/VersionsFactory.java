package org.eclipse.emf.emfstore.internal.server.model.versioning;

import org.eclipse.emf.emfstore.server.model.versionspec.IAncestorVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.IBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.IHeadVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ITagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.IVersionFactory;
import org.eclipse.emf.emfstore.server.model.versionspec.IVersionSpec;

public class VersionsFactory implements IVersionFactory {

	public static final VersionsFactory INSTANCE = new VersionsFactory();

	private VersionsFactory() {
	}

	public IHeadVersionSpec createHEAD() {
		return Versions.createHEAD();
	}

	public IHeadVersionSpec createHEAD(String branch) {
		return Versions.createHEAD(branch);
	}

	public IHeadVersionSpec createHEAD(IVersionSpec versionSpec) {
		return Versions.createHEAD((VersionSpec) versionSpec);
	}

	public IPrimaryVersionSpec createPRIMARY(String branch, int index) {
		return Versions.createPRIMARY(branch, index);
	}

	public IPrimaryVersionSpec createPRIMARY(IVersionSpec versionSpec, int index) {
		return Versions.createPRIMARY((VersionSpec) versionSpec, index);
	}

	public IPrimaryVersionSpec createPRIMARY(int i) {
		return Versions.createPRIMARY(i);
	}

	public IBranchVersionSpec createBRANCH(String value) {
		return Versions.createBRANCH(value);
	}

	public IBranchVersionSpec createBRANCH(IVersionSpec spec) {
		return Versions.createBRANCH((VersionSpec) spec);
	}

	public IAncestorVersionSpec createANCESTOR(IPrimaryVersionSpec source, IPrimaryVersionSpec target) {
		return Versions.createANCESTOR((PrimaryVersionSpec) source, (PrimaryVersionSpec) target);
	}

	public boolean isSameBranch(IVersionSpec spec1, IVersionSpec spec2) {
		return Versions.isSameBranch((VersionSpec) spec1, (VersionSpec) spec2);
	}

	public ITagVersionSpec createTAG(String tag, String branch) {
		return Versions.createTAG(tag, branch);
	}

}
