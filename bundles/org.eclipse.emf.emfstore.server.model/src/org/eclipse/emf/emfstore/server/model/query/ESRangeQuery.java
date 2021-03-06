/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
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

/**
 * A query that selects multiple versions based on a configurable range.
 * 
 * @author wesendon
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESRangeQuery<T extends ESRangeQuery<?>> extends ESHistoryQuery<T> {

	/**
	 * <p>
	 * Returns the upper limit of the range query,
	 * </p>
	 * <p>
	 * The upper limit of a range query specifies how many versions after the source version will be considered by the
	 * query.
	 * </p>
	 * 
	 * @return the upper limit of the range
	 */
	int getUpperLimit();

	/**
	 * <p>
	 * Sets the upper limit of the range query.
	 * </p>
	 * <p>
	 * The upper limit of a range query specifies how many versions after the source version will be considered by the
	 * query.
	 * </p>
	 * 
	 * @param upperLimit
	 *            the upper limit of the query
	 */
	void setUpperLimit(int upperLimit);

	/**
	 * <p>
	 * Returns the lower limit of the range query.
	 * </p>
	 * <p>
	 * The lower limit of a range query specifies how many versions ahead the source version will be considered by the
	 * query.
	 * </p>
	 * 
	 * @return the lower limit of the range
	 */
	int getLowerLimit();

	/**
	 * <p>
	 * Sets the lower limit of the range query.
	 * </p>
	 * <p>
	 * The lower limit of a range query specifies how many versions ahead the source version will be considered by the
	 * query.
	 * </p>
	 * 
	 * @param lowerLimit
	 *            the lower limit of the query
	 */
	void setLowerLimit(int lowerLimit);

	/**
	 * <p>
	 * Determines whether the query should consider incoming versions.
	 * </p>
	 * <p>
	 * Incoming versions are branches which have been merged into the source version of this query.
	 * </p>
	 * 
	 * @param includeIncomingVersions
	 *            should be set to {@code true}, if incoming versions
	 *            should be considered, {@code false} otherwise
	 */
	void setIncludeIncoming(boolean includeIncomingVersions);

	/**
	 * <p>
	 * Whether the query should consider incoming versions.
	 * </p>
	 * <p>
	 * Incoming versions are branches which have been merged into the source version of this query.
	 * </p>
	 * 
	 * @return true, if the query includes incoming versions, false otherwise
	 */
	boolean isIncludeIncoming();

	/**
	 * <p>
	 * Determines whether the query should consider outgoing versions.
	 * </p>
	 * <p>
	 * Incoming versions are branches which have been merged into the source version of this query.
	 * </p>
	 * 
	 * @param includeOutgoingVersions
	 *            should be set to {@code true}, if outgoing versions
	 *            should be considered, {@code false} otherwise
	 */
	void setIncludeOutgoing(boolean includeOutgoingVersions);

	/**
	 * <p>
	 * Whether the query should include outgoing versions.
	 * </p>
	 * <p>
	 * Outgoing versions are branches which were created from the source version of this query.
	 * </p>
	 * 
	 * @return true, if the query includes outgoing versions, false otherwise
	 */
	boolean isIncludeOutgoing();
}
