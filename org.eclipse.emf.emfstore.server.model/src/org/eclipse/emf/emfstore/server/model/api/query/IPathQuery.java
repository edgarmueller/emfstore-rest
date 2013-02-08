package org.eclipse.emf.emfstore.server.model.api.query;

import org.eclipse.emf.emfstore.server.model.api.versionspec.IPrimaryVersionSpec;

public interface IPathQuery extends IHistoryQuery {

	IPrimaryVersionSpec getTarget();

	void setTarget(IPrimaryVersionSpec target);

}
