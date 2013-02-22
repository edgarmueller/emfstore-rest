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
package org.eclipse.emf.emfstore.internal.server.model.impl.api;

import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.server.model.ESSessionId;

/**
 * Mapping between {@link ESSessionId} and {@link SessionId}.
 * 
 * @author emueller
 * 
 */
public class ESSessionIdImpl extends AbstractAPIImpl<ESSessionIdImpl, SessionId> implements ESSessionId {

	/**
	 * Constructor.
	 * 
	 * @param sessionId
	 *            the delegate
	 */
	public ESSessionIdImpl(SessionId sessionId) {
		super(sessionId);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESUniqueIdentifier#getId()
	 */
	public String getId() {
		return getInternalAPIImpl().getId();
	}

}
