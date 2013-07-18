/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * JulianSommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.fuzzy.emf.test;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.emfstore.common.model.ESModelElementIdGenerator;
import org.eclipse.emf.emfstore.common.model.ESObjectContainer;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;

/**
 * Implementation of {@link ModelElementIdGenerator} which increments the
 * ModelElementId for each new modelelement for each IdEObjectCollection
 * starting with 0.
 * 
 * @author Julian Sommerfeldt
 * 
 */
@SuppressWarnings("restriction")
public class FuzzyModelElementIdGenerator implements ESModelElementIdGenerator<ModelElementId> {

	private Map<Object, Integer> collectionsToIds = new HashMap<Object, Integer>();

	/**
	 * Generates a new {@link ModelElementId}.
	 * 
	 * @param collection
	 *            The
	 *            {@link org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection}
	 *            for which to create a new {@link ModelElementId}.
	 * @return A new {@link ModelElementId}.
	 */
	public ModelElementId generateModelElementId(ESObjectContainer<ModelElementId> collection) {
		Integer id = collectionsToIds.get(collection);
		if (id == null) {
			id = new Integer(0);
		}
		ModelElementId modelElementId = ModelFactory.eINSTANCE
				.createModelElementId();
		modelElementId.setId(String.valueOf(id));
		collectionsToIds.put(collection, id + 1);
		return modelElementId;
	}
}
