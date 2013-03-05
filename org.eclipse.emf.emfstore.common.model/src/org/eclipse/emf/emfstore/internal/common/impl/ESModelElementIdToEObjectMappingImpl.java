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
package org.eclipse.emf.emfstore.internal.common.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.common.ModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.impl.ESModelElementIdImpl;

/**
 * Mapping between {@link ESModelElementIdToEObjectMapping} and {@link ModelElementIdToEObjectMapping}.
 * 
 * @author emueller
 * 
 */
public class ESModelElementIdToEObjectMappingImpl
	extends AbstractAPIImpl<ESModelElementIdToEObjectMappingImpl, ModelElementIdToEObjectMapping>
	implements ESModelElementIdToEObjectMapping {

	/**
	 * Constructor.
	 * 
	 * @param mapping
	 *            the internal mapping
	 */
	public ESModelElementIdToEObjectMappingImpl(ModelElementIdToEObjectMapping mapping) {
		super(mapping);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping#get(java.lang.Object)
	 */
	public EObject get(ESModelElementId modelElementId) {
		ModelElementId id = ((ESModelElementIdImpl) modelElementId).getInternalAPIImpl();
		return getInternalAPIImpl().get(id);
	}
}
