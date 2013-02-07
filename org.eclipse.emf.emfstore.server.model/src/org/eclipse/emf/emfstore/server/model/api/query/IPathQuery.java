package org.eclipse.emf.emfstore.server.model.api.query;

import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;

public interface IPathQuery extends IHistoryQuery {

	IPrimaryVersionSpec getTarget();

	void setTarget(IPrimaryVersionSpec target);

}
