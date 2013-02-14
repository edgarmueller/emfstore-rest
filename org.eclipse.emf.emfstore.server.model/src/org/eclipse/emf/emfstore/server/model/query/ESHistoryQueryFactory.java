/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model.query;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.IEMFStoreFactory;
import org.eclipse.emf.emfstore.common.model.IModelElementId;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

/**
 * Factory for creating history queries.
 * 
 * @author wesendon
 * @author emueller
 */
public interface ESHistoryQueryFactory extends IEMFStoreFactory {

	/**
	 * Factory method for creating a {@link ESRangeQuery}.
	 * 
	 * @param source
	 *            the source version of the query
	 * @param upper
	 *            the upper limit of the query
	 * @param lower
	 *            the lower limit of the query
	 * @param allVersions
	 *            whether to include all versions, from all branches
	 * @param incoming
	 *            whether to include incoming versions, only if {@code allVersions} is set to {@code false}
	 * @param outgoing
	 *            whether to include include outgoing versions
	 * @param includeChangePackages
	 *            whether to include change packges
	 * @return query the constructed range query
	 */
	ESRangeQuery rangeQuery(IPrimaryVersionSpec source, int upper, int lower,
		boolean allVersions, boolean incoming,
		boolean outgoing, boolean includeChangePackages);

	/**
	 * Factory method for creating a {@link ESPathQuery}, which fetches
	 * all changes from {@code source} to {@code target}.
	 * 
	 * @param source
	 *            the source version of the query
	 * @param target
	 *            the target version of the query
	 * @param allVersions
	 *            whether to include all versions, from all branches
	 * @param includeChangePackages
	 *            whether to include change packges
	 * @return query the constructed path query
	 */
	ESPathQuery pathQuery(IPrimaryVersionSpec source, IPrimaryVersionSpec target,
		boolean allVersions, boolean includeChangePackages);

	/**
	 * Factory method for creating a {@link ESModelElementQuery}.
	 * 
	 * @param source
	 *            the source version of the query
	 * @param modelElements
	 *            a list containing the IDs of possibly multiple model elements
	 * @param upper
	 *            the upper limit of the query
	 * @param lower
	 *            the lower limit of the query
	 * @param allVersions
	 *            include all versions, from all branches
	 * @param includeChangePackages
	 *            whether to include change packages
	 * @return query the constructed model element query
	 */
	ESModelElementQuery modelelementQuery(IPrimaryVersionSpec source, List<IModelElementId> modelElements, int upper,
		int lower, boolean allVersions, boolean includeChangePackages);

	/**
	 * Factory method for creating a {@link ESModelElementQuery}.
	 * 
	 * @param source
	 *            the source version of the query
	 * @param id
	 *            the ID of a model element
	 * @param upper
	 *            the upper limit of the query
	 * @param lower
	 *            the lower limit of the query
	 * @param allVersions
	 *            whether to include all versions, from all branches
	 * @param includeChangePackages
	 *            whether to include change packages
	 * @return query the constructed model element query
	 */
	ESModelElementQuery modelelementQuery(IPrimaryVersionSpec source, IModelElementId id,
		int upper, int lower, boolean allVersions, boolean includeChangePackages);
}
