package org.eclipse.emf.emfstore.internal.server.model.versioning.util;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.IModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQueryFactory;
import org.eclipse.emf.emfstore.server.model.query.ESModelElementQuery;
import org.eclipse.emf.emfstore.server.model.query.ESPathQuery;
import org.eclipse.emf.emfstore.server.model.query.ESRangeQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

public class HistoryQueryFactoryImpl implements ESHistoryQueryFactory {

	public static final HistoryQueryFactoryImpl INSTANCE = new HistoryQueryFactoryImpl();

	private HistoryQueryFactoryImpl() {
	}

	public ESRangeQuery rangeQuery(ESPrimaryVersionSpec source, int upper, int lower, boolean allVersions,
		boolean incoming, boolean outgoing, boolean includeCp) {
		return HistoryQueryBuilder.rangeQuery((PrimaryVersionSpec) source, upper, lower, allVersions, incoming,
			outgoing, includeCp);
	}

	public ESPathQuery pathQuery(ESPrimaryVersionSpec source, ESPrimaryVersionSpec target, boolean allVersions,
		boolean includeCp) {
		return HistoryQueryBuilder.pathQuery((PrimaryVersionSpec) source, (PrimaryVersionSpec) target, allVersions,
			includeCp);
	}

	public ESModelElementQuery modelelementQuery(ESPrimaryVersionSpec source, List<IModelElementId> modelElements,
		int upper, int lower, boolean allVersions, boolean includeCp) {
		return HistoryQueryBuilder.modelelementQuery((PrimaryVersionSpec) source, modelElements, upper, lower,
			allVersions, includeCp);
	}

	public ESModelElementQuery modelelementQuery(ESPrimaryVersionSpec source, IModelElementId id, int upper, int lower,
		boolean allVersions, boolean includeCp) {
		return HistoryQueryBuilder.modelelementQuery((PrimaryVersionSpec) source, (ModelElementId) id, upper, lower,
			allVersions, includeCp);
	}

}
