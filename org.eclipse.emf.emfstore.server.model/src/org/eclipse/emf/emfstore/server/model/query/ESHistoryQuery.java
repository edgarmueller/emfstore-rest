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

import org.eclipse.emf.emfstore.internal.server.model.versioning.util.HistoryQueryFactoryImpl;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * A query that is used to retrieve information about the version history.
 * 
 * @author emueller
 * @author wesendom
 */
public interface ESHistoryQuery {

	/**
	 * Factory for creating history queries.
	 */
	ESHistoryQueryFactory FACTORY = HistoryQueryFactoryImpl.INSTANCE;

	/**
	 * Returns the {@link ESPrimaryVersionSpec} this history query is pointing to.
	 * 
	 * @return the version specifier this history query points to
	 */
	ESPrimaryVersionSpec getSource();

	/**
	 * Sets the version specifier this history query should be pointing to.
	 * 
	 * @param versionSpec
	 *            the version specifier the history query should be pointing to
	 */
	void setSource(ESPrimaryVersionSpec versionSpec);

	/**
	 * Determines whether {@link org.eclipse.emf.emfstore.server.model.ESChangePackage}s
	 * are included in the query.
	 * 
	 * @param includeChangePackages
	 *            {@code true}, if change packages should be included in the query, {@code false} otherwise
	 */
	void setIncludeChangePackages(boolean includeChangePackages);

	/**
	 * Whether {@link org.eclipse.emf.emfstore.server.model.ESChangePackage}s are included in the query.
	 * 
	 * @return {@code true}, if change packages are included in the query, {@code false} otherwise
	 */
	boolean isIncludeChangePackages();

	/**
	 * Determines whether to include all versions, i.e. whether branches should be
	 * considered by the history query.
	 * 
	 * @param includeAllVersion
	 *            {@code true}, if branches should be considered, {@code false} otherwise
	 */
	void setIncludeAllVersions(boolean includeAllVersion);

	/**
	 * Whether the include all versions, i.e. whether branches should be
	 * considered by the history query.
	 * 
	 * @return {@code true}, if branches are considered by the query, {@code false} otherwise
	 */
	boolean isIncludeAllVersions();
}
