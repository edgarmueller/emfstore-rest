package org.eclipse.emf.emfstore.internal.server.model.api.query;

import org.eclipse.emf.emfstore.internal.server.model.api.versionspecs.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.util.HistoryQueryFactoryImpl;

public interface IHistoryQuery {

	IHistoryQueryFactory FACTORY = HistoryQueryFactoryImpl.INSTANCE;

	IPrimaryVersionSpec getSource();

	void setSource(IPrimaryVersionSpec spec);

	void setIncludeChangePackages(boolean value);

	boolean isIncludeChangePackages();

	void setIncludeAllVersions(boolean value);

	boolean isIncludeAllVersions();
}
