/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.model;

/**
 * Interface for the modelelementidgenerator extension point, identified by this id:
 * {@link org.eclipse.emf.emfstore.common.model.impl.IdEObjectCollectionImpl#MODELELEMENTID_GENERATOR_EXTENSIONPOINT}.
 * 
 * 
 * @author Julian Sommerfeldt
 * 
 */
public interface ModelElementIdGenerator {

	/**
	 * Generates the next {@link ModelElementId} for the {@link IdEObjectCollection}.
	 * 
	 * @param collection The {@link IdEObjectCollection} for which a new {@link ModelElementId} should be created.
	 * @return The next {@link ModelElementId} for the given {@link IdEObjectCollection}.
	 */
	ModelElementId generateModelElementId(IdEObjectCollection collection);

}
