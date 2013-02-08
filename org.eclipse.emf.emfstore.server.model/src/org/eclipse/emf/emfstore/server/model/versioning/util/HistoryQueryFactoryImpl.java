package org.eclipse.emf.emfstore.server.model.versioning.util;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.IModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.server.model.api.query.IHistoryQueryFactory;
import org.eclipse.emf.emfstore.server.model.api.query.IModelElementQuery;
import org.eclipse.emf.emfstore.server.model.api.query.IPathQuery;
import org.eclipse.emf.emfstore.server.model.api.query.IRangeQuery;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

public class HistoryQueryFactoryImpl implements IHistoryQueryFactory {

	public static final HistoryQueryFactoryImpl INSTANCE = new HistoryQueryFactoryImpl();

	private HistoryQueryFactoryImpl() {
	}

	public IRangeQuery rangeQuery(IPrimaryVersionSpec source, int upper, int lower, boolean allVersions,
		boolean incoming, boolean outgoing, boolean includeCp) {
		return HistoryQueryBuilder.rangeQuery((PrimaryVersionSpec) source, upper, lower, allVersions, incoming,
			outgoing, includeCp);
	}

	public IPathQuery pathQuery(IPrimaryVersionSpec source, IPrimaryVersionSpec target, boolean allVersions,
		boolean includeCp) {
		return HistoryQueryBuilder.pathQuery((PrimaryVersionSpec) source, (PrimaryVersionSpec) target, allVersions,
			includeCp);
	}

	public IModelElementQuery modelelementQuery(IPrimaryVersionSpec source, List<IModelElementId> modelElements,
		int upper, int lower, boolean allVersions, boolean includeCp) {
		return HistoryQueryBuilder.modelelementQuery((PrimaryVersionSpec) source, modelElements, upper, lower,
			allVersions, includeCp);
	}

	public IModelElementQuery modelelementQuery(IPrimaryVersionSpec source, IModelElementId id, int upper, int lower,
		boolean allVersions, boolean includeCp) {
		return HistoryQueryBuilder.modelelementQuery((PrimaryVersionSpec) source, (ModelElementId) id, upper, lower,
			allVersions, includeCp);
	}

}
