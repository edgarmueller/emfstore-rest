package org.eclipse.emf.emfstore.server.model.query;

import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

public interface IPathQuery extends IHistoryQuery {

	IPrimaryVersionSpec getTarget();

	void setTarget(IPrimaryVersionSpec target);

}
