package org.eclipse.emf.emfstore.internal.server.model.api;

import org.eclipse.emf.emfstore.internal.server.model.api.versionspecs.IPrimaryVersionSpec;

public interface IBranchInfo {

	String getName();

	IPrimaryVersionSpec getHead();

	IPrimaryVersionSpec getSource();
}
