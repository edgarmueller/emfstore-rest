package org.eclipse.emf.emfstore.server.model.versioning.util;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.api.IModelElementId;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQueryFactory;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

public class HistoryQueryFactoryImpl implements IHistoryQueryFactory {

	public static final HistoryQueryFactoryImpl INSTANCE = new HistoryQueryFactoryImpl();

	private HistoryQueryFactoryImpl() {
	}

	public IHistoryQuery rangeQuery(IPrimaryVersionSpec source, int upper,
			int lower, boolean allVersions, boolean incoming, boolean outgoing,
			boolean includeCp) {
		return HistoryQueryBuilder.rangeQuery((PrimaryVersionSpec) source,
				upper, lower, allVersions, incoming, outgoing, includeCp);
	}

	public IHistoryQuery pathQuery(IPrimaryVersionSpec source,
			IPrimaryVersionSpec target, boolean allVersions, boolean includeCp) {
		return HistoryQueryBuilder.pathQuery((PrimaryVersionSpec) source,
				(PrimaryVersionSpec) target, allVersions, includeCp);
	}

	public IHistoryQuery modelelementQuery(IPrimaryVersionSpec source,
			List<IModelElementId> modelElements, int upper, int lower,
			boolean allVersions, boolean includeCp) {
		return HistoryQueryBuilder.modelelementQuery(
				(PrimaryVersionSpec) source, modelElements, upper, lower,
				allVersions, includeCp);
	}

	public IHistoryQuery modelelementQuery(IPrimaryVersionSpec source,
			IModelElementId id, int upper, int lower, boolean allVersions,
			boolean includeCp) {
		return HistoryQueryBuilder.modelelementQuery(
				(PrimaryVersionSpec) source, (ModelElementId) id, upper, lower,
				allVersions, includeCp);
	}

}
