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
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.ESGlobalProjectId;

/**
 * Mapping between {@link ESGlobalProjectId} and {@link ProjectId}.
 * 
 * @author emueller
 * 
 */
public class ESGlobalProjectIdImpl extends AbstractAPIImpl<ESGlobalProjectIdImpl, ProjectId> implements
	ESGlobalProjectId {

	/**
	 * Constructor.
	 * 
	 * @param projectId
	 *            the delegate
	 */
	public ESGlobalProjectIdImpl(ProjectId projectId) {
		super(projectId);
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

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object otherObject) {

		if (otherObject instanceof ESGlobalProjectIdImpl) {
			ESGlobalProjectIdImpl otherId = (ESGlobalProjectIdImpl) otherObject;
			return otherId.getInternalAPIImpl().equals(getInternalAPIImpl());
		}

		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() + getInternalAPIImpl().hashCode();
	}
}