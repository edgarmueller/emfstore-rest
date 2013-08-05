/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.emfstore.internal.server.model.impl.api.query;

import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PathQuery;
import org.eclipse.emf.emfstore.server.model.query.ESPathQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * <p>
 * Mapping between {@link ESPathQuery} and {@link PathQuery}.
 * </p>
 * <p>
 * A path query additionally considers a target version beside the source version, i.e. it is possible to specify a
 * version range.
 * </p>
 * 
 * @author wesendon
 * @author emueller
 */
public class ESPathQueryImpl extends ESHistoryQueryImpl<ESPathQuery, PathQuery> implements ESPathQuery {

	/**
	 * Constructor.
	 * 
	 * @param pathQuery
	 *            the delegate
	 */
	public ESPathQueryImpl(PathQuery pathQuery) {
		super(pathQuery);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#getSource()
	 */
	public ESPrimaryVersionSpec getSource() {
		return toInternalAPI().getSource().toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#setSource(org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec)
	 */
	public void setSource(ESPrimaryVersionSpec versionSpec) {
		toInternalAPI().setSource(((ESPrimaryVersionSpecImpl) versionSpec).toInternalAPI());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#setIncludeChangePackages(boolean)
	 */
	public void setIncludeChangePackages(boolean includeChangePackages) {
		toInternalAPI().setIncludeChangePackages(includeChangePackages);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#isIncludeChangePackages()
	 */
	public boolean isIncludeChangePackages() {
		return toInternalAPI().isIncludeChangePackages();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#setIncludeAllVersions(boolean)
	 */
	public void setIncludeAllVersions(boolean includeAllVersion) {
		toInternalAPI().setIncludeAllVersions(includeAllVersion);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#isIncludeAllVersions()
	 */
	public boolean isIncludeAllVersions() {
		return toInternalAPI().isIncludeAllVersions();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESPathQuery#getTarget()
	 */
	public ESPrimaryVersionSpec getTarget() {
		return toInternalAPI().getTarget().toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESPathQuery#setTarget(org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec)
	 */
	public void setTarget(ESPrimaryVersionSpec target) {
		toInternalAPI().setTarget(((ESPrimaryVersionSpecImpl) target).toInternalAPI());
	}
}
