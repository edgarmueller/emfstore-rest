package org.eclipse.emf.emfstore.server.model;

import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

public interface IBranchInfo {

	String getName();

	IPrimaryVersionSpec getHead();

	IPrimaryVersionSpec getSource();
}
