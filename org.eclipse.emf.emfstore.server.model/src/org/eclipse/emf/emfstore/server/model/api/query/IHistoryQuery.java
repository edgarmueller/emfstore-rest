package org.eclipse.emf.emfstore.server.model.api.query;

import org.eclipse.emf.emfstore.internal.server.model.versioning.util.HistoryQueryFactoryImpl;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IPrimaryVersionSpec;

public interface IHistoryQuery {

	IHistoryQueryFactory FACTORY = HistoryQueryFactoryImpl.INSTANCE;

	IPrimaryVersionSpec getSource();

	void setSource(IPrimaryVersionSpec spec);

	void setIncludeChangePackages(boolean value);

	boolean isIncludeChangePackages();

	void setIncludeAllVersions(boolean value);

	boolean isIncludeAllVersions();
}
