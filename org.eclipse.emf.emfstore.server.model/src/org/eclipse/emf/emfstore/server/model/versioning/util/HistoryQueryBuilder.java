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
package org.eclipse.emf.emfstore.server.model.versioning.util;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.model.versioning.ModelElementQuery;
import org.eclipse.emf.emfstore.server.model.versioning.PathQuery;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.RangeQuery;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;

/**
 * Helper class for history query creation.
 * 
 * @author wesendon
 */
public class HistoryQueryBuilder {

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

	public static PathQuery pathQuery(PrimaryVersionSpec source,
			PrimaryVersionSpec target, boolean allVersions, boolean includeCp) {
		PathQuery query = VersioningFactory.eINSTANCE.createPathQuery();
		query.setSource(ModelUtil.clone(source));
		query.setTarget(ModelUtil.clone(target));
		query.setIncludeAllVersions(allVersions);
		query.setIncludeChangePackages(includeCp);
		return query;
	}

	public static ModelElementQuery modelelementQuery(
			PrimaryVersionSpec source, List<ModelElementId> modelElements,
			int upper, int lower, boolean allVersions, boolean includeCp) {
		ModelElementQuery query = VersioningFactory.eINSTANCE
				.createModelElementQuery();
		query.setSource(ModelUtil.clone(source));
		query.getModelElements().addAll(modelElements);
		query.setUpperLimit(upper);
		query.setLowerLimit(lower);
		query.setIncludeAllVersions(allVersions);
		query.setIncludeChangePackages(includeCp);
		return query;
	}

	public static ModelElementQuery modelelementQuery(
			PrimaryVersionSpec source, ModelElementId id, int upper, int lower,
			boolean allVersions, boolean includeCp) {
		return modelelementQuery(source, Arrays.asList(id), upper, lower,
				allVersions, includeCp);
	}
}
