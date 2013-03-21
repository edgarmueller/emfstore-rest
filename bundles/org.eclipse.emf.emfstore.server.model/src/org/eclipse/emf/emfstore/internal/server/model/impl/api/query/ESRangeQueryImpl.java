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
 * 
 * @param <T> a subtype of {@link ESRangeQueryImpl}
 */
public class ESRangeQueryImpl<T extends ESRangeQuery<?>, U extends RangeQuery<T>> extends
	ESHistoryQueryImpl<T, U> implements ESRangeQuery<T> {

	/**
	 * Constructor.
	 * 
	 * @param rangeQuery
	 *            the delegate
	 */
	public ESRangeQueryImpl(U rangeQuery) {
		super(rangeQuery);
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
		if (versionSpec == null) {
			toInternalAPI().setSource(null);
		} else {
			toInternalAPI().setSource(((ESPrimaryVersionSpecImpl) versionSpec).toInternalAPI());
		}
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
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#getUpperLimit()
	 */
	public int getUpperLimit() {
		return toInternalAPI().getUpperLimit();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#setUpperLimit(int)
	 */
	public void setUpperLimit(int upperLimit) {
		toInternalAPI().setUpperLimit(upperLimit);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#getLowerLimit()
	 */
	public int getLowerLimit() {
		return toInternalAPI().getLowerLimit();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#setLowerLimit(int)
	 */
	public void setLowerLimit(int lowerLimit) {
		toInternalAPI().setLowerLimit(lowerLimit);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#setIncludeIncoming(boolean)
	 */
	public void setIncludeIncoming(boolean includeIncomingVersions) {
		toInternalAPI().setIncludeIncoming(includeIncomingVersions);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#isIncludeIncoming()
	 */
	public boolean isIncludeIncoming() {
		return toInternalAPI().isIncludeIncoming();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#setIncludeOutgoing(boolean)
	 */
	public void setIncludeOutgoing(boolean includeOutgoingVersions) {
		toInternalAPI().setIncludeOutgoing(includeOutgoingVersions);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESRangeQuery#isIncludeOutgoing()
	 */
	public boolean isIncludeOutgoing() {
		return toInternalAPI().isIncludeOutgoing();
	}

}
