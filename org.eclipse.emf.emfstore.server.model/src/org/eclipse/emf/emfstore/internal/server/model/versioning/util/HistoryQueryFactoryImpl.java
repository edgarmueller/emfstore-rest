package org.eclipse.emf.emfstore.internal.server.model.versioning.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.impl.ESModelElementIdImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ModelElementQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PathQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery;
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
		boolean incoming, boolean outgoing, boolean includeChangePackages) {

		PrimaryVersionSpec sourceVersionSpec = ((ESPrimaryVersionSpecImpl) source).getInternalAPIImpl();

		RangeQuery<?> rangeQuery = HistoryQueryBuilder.rangeQuery(
			sourceVersionSpec,
			upper,
			lower,
			allVersions,
			incoming,
			outgoing,
			includeChangePackages);

		return rangeQuery.getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQueryFactory#pathQuery(org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec, boolean, boolean)
	 */
	public ESPathQuery pathQuery(ESPrimaryVersionSpec source, ESPrimaryVersionSpec target, boolean allVersions,
		boolean includeChangePackages) {
		PrimaryVersionSpec sourceVersionSpec = ((ESPrimaryVersionSpecImpl) source).getInternalAPIImpl();
		PrimaryVersionSpec targetVersionSpec = ((ESPrimaryVersionSpecImpl) target).getInternalAPIImpl();
		PathQuery pathQuery = HistoryQueryBuilder.pathQuery(
			sourceVersionSpec,
			targetVersionSpec,
			allVersions,
			includeChangePackages);
		return pathQuery.getAPIImpl();
	}

	public ESModelElementQuery modelelementQuery(ESPrimaryVersionSpec source, List<ESModelElementId> modelElements,
		int upper, int lower, boolean allVersions, boolean includeChangePackages) {

		// TODO: provide util method for mapping to internal classes
		List<ModelElementId> modelElementIds = new ArrayList<ModelElementId>();
		for (ESModelElementId id : modelElements) {
			modelElementIds.add(((ESModelElementIdImpl) id).getInternalAPIImpl());
		}

		PrimaryVersionSpec sourcePrimaryVersionSpec = ((ESPrimaryVersionSpecImpl) source).getInternalAPIImpl();

		ModelElementQuery modelelementQuery = HistoryQueryBuilder.modelelementQuery(
			sourcePrimaryVersionSpec,
			modelElementIds,
			upper,
			lower,
			allVersions,
			includeChangePackages);

		return modelelementQuery.getAPIImpl();
	}

	public ESModelElementQuery modelelementQuery(ESPrimaryVersionSpec source, ESModelElementId id, int upper,
		int lower,
		boolean allVersions, boolean includeCp) {
		ModelElementQuery modelelementQuery = HistoryQueryBuilder.modelelementQuery(
			((ESPrimaryVersionSpecImpl) source).getInternalAPIImpl(),
			((ESModelElementIdImpl) id).getInternalAPIImpl(), upper, lower, allVersions, includeCp);
		return modelelementQuery.getAPIImpl();
	}

}
