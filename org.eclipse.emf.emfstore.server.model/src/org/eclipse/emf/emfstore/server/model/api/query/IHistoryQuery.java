package org.eclipse.emf.emfstore.server.model.api.query;

import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;

public interface IHistoryQuery {

	IPrimaryVersionSpec getSource();

	void setSource(IPrimaryVersionSpec spec);

	void setIncludeChangePackages(boolean value);

	boolean isIncludeChangePackages();

	void setIncludeAllVersions(boolean value);

	boolean isIncludeAllVersions();
}
