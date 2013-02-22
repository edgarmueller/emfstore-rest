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
package org.eclipse.emf.emfstore.internal.server.model.impl.api.query;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.impl.ESModelElementIdImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ModelElementQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery;
import org.eclipse.emf.emfstore.server.model.query.ESModelElementQuery;

/**
 * <p>
 * Mapping between {@link ESModelElementQuery} and {@link ModelElementQuery}.
 * </p>
 * <p>
 * The model element query is a specialization of the {@link ESRangeQueryImpl}, which allows to additionally alter the
 * results produced by the range query on selected elements.
 * </p>
 * 
 * @author emueller
 * 
 */
public class ESModelElementQueryImpl extends ESRangeQueryImpl<ESModelElementQueryImpl> implements ESModelElementQuery {

	/**
	 * Constructor.
	 * 
	 * @param rangeQuery
	 *            the delegate
	 */
	public ESModelElementQueryImpl(RangeQuery<ESModelElementQueryImpl> rangeQuery) {
		super(rangeQuery);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESModelElementQuery#getModelElementIds()
	 */
	public List<ESModelElementId> getModelElementIds() {
		// TODO: provide util method
		List<ESModelElementId> result = new ArrayList<ESModelElementId>();
		ModelElementQuery query = (ModelElementQuery) getInternalAPIImpl();
		for (ModelElementId id : query.getModelElements()) {
			result.add(id.getAPIImpl());
		}
		return result;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESModelElementQuery#addModelElementId(org.eclipse.emf.emfstore.common.model.ESModelElementId)
	 */
	public void addModelElementId(ESModelElementId id) {
		ESModelElementIdImpl idImpl = (ESModelElementIdImpl) id;
		getQuery().getModelElements().add(idImpl.getInternalAPIImpl());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.query.ESModelElementQuery#removeModelElementId(org.eclipse.emf.emfstore.common.model.ESModelElementId)
	 */
	public void removeModelElementId(ESModelElementId id) {
		getQuery().getModelElements().remove(((ESModelElementIdImpl) id).getInternalAPIImpl());
	}

	private ModelElementQuery getQuery() {
		ModelElementQuery query = (ModelElementQuery) getInternalAPIImpl();
		return query;
	}
}
