/**
 * <copyright> Copyright (c) 2008-2009 Jonas Helming, Maximilian Koegel. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html </copyright>
 */
package org.eclipse.emf.emfstore.internal.common.model.impl;

import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;

/**
 * Mapping between {@link ESModelElementId} and {@link ModelElementId}.
 * 
 * @author emueller
 */
public class ESModelElementIdImpl extends AbstractAPIImpl<ESModelElementIdImpl, ModelElementId> implements
	ESModelElementId {

	public ESModelElementIdImpl(ModelElementId modelElementId) {
		super(modelElementId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESUniqueIdentifier#getId()
	 */
	public String getId() {
		return toInternalAPI().getId();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ESModelElementIdImpl)) {
			return false;
		}
		return toInternalAPI().equals(((ESModelElementIdImpl) obj).toInternalAPI());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return toInternalAPI().hashCode();
	}
}
