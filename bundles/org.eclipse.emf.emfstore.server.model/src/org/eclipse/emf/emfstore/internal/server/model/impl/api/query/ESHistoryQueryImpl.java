/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;

/**
 * Mapping between {@link ESHistoryQueryImpl} and {@link HistoryQuery}.
 * 
 * @author emueller
 * 
 * @param <U> a subtype of the API implementation class {@link ESHistoryQueryImpl}
 * @param <T> a subtype of the internal type {@link HistoryQuery}
 */
public abstract class ESHistoryQueryImpl<U extends ESHistoryQuery<?>, T extends HistoryQuery<U>>
	extends AbstractAPIImpl<U, T> implements ESHistoryQuery<U> {

	/**
	 * Constructor.
	 * 
	 * @param historyQuery
	 *            the delegate
	 */
	public ESHistoryQueryImpl(T historyQuery) {
		super(historyQuery);
	}

}
