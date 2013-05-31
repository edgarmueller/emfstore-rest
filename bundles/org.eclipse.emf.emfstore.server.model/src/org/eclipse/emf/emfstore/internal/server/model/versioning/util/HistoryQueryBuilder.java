/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.versioning.util;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ModelElementQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PathQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;

/**
 * Helper class for history query creation.
 * 
 * @author wesendon
 */
public final class HistoryQueryBuilder {

	private HistoryQueryBuilder() {
	}

	/**
	 * Factory method for range query.
	 * 
	 * @param source
	 *            source version
	 * @param upper
	 *            upper limit
	 * @param lower
	 *            lower limit
	 * @param allVersions
	 *            include all versions, from all branches
	 * @param incoming
	 *            include incoming versions, only if allVersions is false
	 * @param outgoing
	 *            include outgoing versions
	 * @param includeCp
	 *            include changepackges
	 * @return query
	 */
	public static RangeQuery rangeQuery(PrimaryVersionSpec source, int upper,
		int lower, boolean allVersions, boolean incoming, boolean outgoing,
		boolean includeCp) {
		RangeQuery query = VersioningFactory.eINSTANCE.createRangeQuery();
		query.setSource(ModelUtil.clone(source));
		query.setUpperLimit(upper);
		query.setLowerLimit(lower);
		query.setIncludeAllVersions(allVersions);
		query.setIncludeIncoming(incoming);
		query.setIncludeOutgoing(outgoing);
		query.setIncludeChangePackages(includeCp);
		return query;
	}

	/**
	 * Factory method for path query. Getting all changes from source to target.
	 * 
	 * @param source
	 *            source version
	 * @param target
	 *            target version
	 * @param allVersions
	 *            include all versions, from all branches
	 * @param includeCp
	 *            include changepackages
	 * @return query
	 */
	public static PathQuery pathQuery(PrimaryVersionSpec source,
		PrimaryVersionSpec target, boolean allVersions, boolean includeCp) {
		PathQuery query = VersioningFactory.eINSTANCE.createPathQuery();
		query.setSource(ModelUtil.clone(source));
		query.setTarget(ModelUtil.clone(target));
		query.setIncludeAllVersions(allVersions);
		query.setIncludeChangePackages(includeCp);
		return query;
	}

	/**
	 * Factory method for modelelements range queries.
	 * 
	 * @param source
	 *            source version
	 * @param modelElements
	 *            modelelements
	 * @param upper
	 *            upper limit
	 * @param lower
	 *            lower limit
	 * @param allVersions
	 *            include all versions, from all branches
	 * @param includeCp
	 *            include change packages
	 * @return query
	 */
	public static ModelElementQuery modelelementQuery(
		PrimaryVersionSpec source,
		List<ModelElementId> modelElements, int upper,
		int lower, boolean allVersions, boolean includeCp) {
		ModelElementQuery query = VersioningFactory.eINSTANCE
			.createModelElementQuery();
		query.setSource(ModelUtil.clone(source));
		query.getModelElements().addAll(modelElements);
		query.setUpperLimit(upper);
		query.setLowerLimit(lower);
		query.setIncludeAllVersions(allVersions);
		query.setIncludeChangePackages(includeCp);
		query.setIncludeIncoming(false);
		query.setIncludeOutgoing(false);
		return query;
	}

	/**
	 * Factory method for modelelement range queries.
	 * 
	 * @param source
	 *            source version
	 * @param id
	 *            modelelement
	 * @param upper
	 *            upper limit
	 * @param lower
	 *            lower limit
	 * @param allVersions
	 *            include all versions, from all branches
	 * @param includeCp
	 *            include change packages
	 * @return query
	 */
	public static ModelElementQuery modelelementQuery(
		PrimaryVersionSpec source, ModelElementId id, int upper, int lower,
		boolean allVersions, boolean includeCp) {
		return modelelementQuery(source, Arrays.asList(id), upper, lower,
			allVersions, includeCp);
	}
}