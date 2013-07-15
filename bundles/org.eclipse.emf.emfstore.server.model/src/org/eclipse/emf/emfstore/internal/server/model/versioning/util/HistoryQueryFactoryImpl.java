/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.versioning.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.impl.ESModelElementIdImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.query.ESModelElementQueryImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.query.ESRangeQueryImpl;
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

		PrimaryVersionSpec sourceVersionSpec = ((ESPrimaryVersionSpecImpl) source).toInternalAPI();

		RangeQuery<?> rangeQuery = HistoryQueryBuilder.rangeQuery(
			sourceVersionSpec,
			upper,
			lower,
			allVersions,
			incoming,
			outgoing,
			includeChangePackages);

		ESRangeQueryImpl<?, ?> apiImpl = (ESRangeQueryImpl<?, ?>) rangeQuery.toAPI();
		return apiImpl;
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
		PrimaryVersionSpec sourceVersionSpec = ((ESPrimaryVersionSpecImpl) source).toInternalAPI();
		PrimaryVersionSpec targetVersionSpec = ((ESPrimaryVersionSpecImpl) target).toInternalAPI();
		PathQuery pathQuery = HistoryQueryBuilder.pathQuery(
			sourceVersionSpec,
			targetVersionSpec,
			allVersions,
			includeChangePackages);
		return pathQuery.toAPI();
	}

	public ESModelElementQuery modelElementQuery(ESPrimaryVersionSpec source, List<ESModelElementId> modelElements,
		int upper, int lower, boolean allVersions, boolean includeChangePackages) {

		// TODO: provide util method for mapping to internal classes
		List<ModelElementId> modelElementIds = new ArrayList<ModelElementId>();
		for (ESModelElementId id : modelElements) {
			modelElementIds.add(((ESModelElementIdImpl) id).toInternalAPI());
		}

		PrimaryVersionSpec sourcePrimaryVersionSpec = ((ESPrimaryVersionSpecImpl) source).toInternalAPI();

		ModelElementQuery modelelementQuery = HistoryQueryBuilder.modelelementQuery(
			sourcePrimaryVersionSpec,
			modelElementIds,
			upper,
			lower,
			allVersions,
			includeChangePackages);

		ESModelElementQueryImpl apiImpl = (ESModelElementQueryImpl) modelelementQuery.toAPI();
		return apiImpl;
	}

	public ESModelElementQuery modelElementQuery(ESPrimaryVersionSpec source, ESModelElementId id, int upper,
		int lower,
		boolean allVersions, boolean includeCp) {
		ModelElementQuery modelelementQuery = HistoryQueryBuilder.modelelementQuery(
			((ESPrimaryVersionSpecImpl) source).toInternalAPI(),
			((ESModelElementIdImpl) id).toInternalAPI(), upper, lower, allVersions, includeCp);
		return modelelementQuery.toAPI();
	}

}
