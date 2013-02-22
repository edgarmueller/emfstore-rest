/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.impl.api.query;

import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery;
import org.eclipse.emf.emfstore.server.model.query.ESRangeQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Mapping between {@link ESRangeQuery} and {@link RangeQuery}.
 * 
 * @author emueller
 */
public class ESRangeQueryImpl<T extends ESRangeQueryImpl<?>> extends
	ESHistoryQueryImpl<T, RangeQuery<T>> implements ESRangeQuery {

	/**
	 * Constructor.
	 * 
	 * @param rangeQuery
	 *            the delegate
	 */
	public ESRangeQueryImpl(RangeQuery<T> rangeQuery) {
		super(rangeQuery);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#getSource()
	 */
	public ESPrimaryVersionSpec getSource() {
		return getInternalAPIImpl().getSource().getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#setSource(org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec)
	 */
	public void setSource(ESPrimaryVersionSpec versionSpec) {
		if (versionSpec == null) {
			getInternalAPIImpl().setSource(null);
		} else {
			getInternalAPIImpl().setSource(((ESPrimaryVersionSpecImpl) versionSpec).getInternalAPIImpl());
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#setIncludeChangePackages(boolean)
	 */
	public void setIncludeChangePackages(boolean includeChangePackages) {
		getInternalAPIImpl().setIncludeChangePackages(includeChangePackages);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#isIncludeChangePackages()
	 */
	public boolean isIncludeChangePackages() {
		return getInternalAPIImpl().isIncludeChangePackages();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#setIncludeAllVersions(boolean)
	 */
	public void setIncludeAllVersions(boolean includeAllVersion) {
		getInternalAPIImpl().setIncludeAllVersions(includeAllVersion);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery#isIncludeAllVersions()
	 */
	public boolean isIncludeAllVersions() {
		return getInternalAPIImpl().isIncludeAllVersions();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#getUpperLimit()
	 */
	public int getUpperLimit() {
		return getInternalAPIImpl().getUpperLimit();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#setUpperLimit(int)
	 */
	public void setUpperLimit(int upperLimit) {
		getInternalAPIImpl().setUpperLimit(upperLimit);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#getLowerLimit()
	 */
	public int getLowerLimit() {
		return getInternalAPIImpl().getLowerLimit();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#setLowerLimit(int)
	 */
	public void setLowerLimit(int lowerLimit) {
		getInternalAPIImpl().setLowerLimit(lowerLimit);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#setIncludeIncoming(boolean)
	 */
	public void setIncludeIncoming(boolean includeIncomingVersions) {
		getInternalAPIImpl().setIncludeIncoming(includeIncomingVersions);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#isIncludeIncoming()
	 */
	public boolean isIncludeIncoming() {
		return getInternalAPIImpl().isIncludeIncoming();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#setIncludeOutgoing(boolean)
	 */
	public void setIncludeOutgoing(boolean includeOutgoingVersions) {
		getInternalAPIImpl().setIncludeOutgoing(includeOutgoingVersions);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#isIncludeOutgoing()
	 */
	public boolean isIncludeOutgoing() {
		return getInternalAPIImpl().isIncludeOutgoing();
	}

}
