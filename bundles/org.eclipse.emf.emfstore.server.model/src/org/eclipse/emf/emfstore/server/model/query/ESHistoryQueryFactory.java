/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model.query;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.ESFactory;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Factory for creating history queries.
 * 
 * @author wesendon
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESHistoryQueryFactory extends ESFactory {

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
	 *            whether to include change packages
	 * @return query the constructed range query
	 */
	ESRangeQuery<?> rangeQuery(ESPrimaryVersionSpec source, int upper, int lower,
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
	 *            whether to include change packages
	 * @return query the constructed path query
	 */
	ESPathQuery pathQuery(ESPrimaryVersionSpec source, ESPrimaryVersionSpec target,
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
	ESModelElementQuery modelElementQuery(ESPrimaryVersionSpec source, List<ESModelElementId> modelElements, int upper,
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
	ESModelElementQuery modelElementQuery(ESPrimaryVersionSpec source, ESModelElementId id,
		int upper, int lower, boolean allVersions, boolean includeChangePackages);
}
