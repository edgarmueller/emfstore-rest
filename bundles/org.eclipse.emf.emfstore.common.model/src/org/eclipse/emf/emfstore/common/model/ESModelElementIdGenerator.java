/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * JulianSommerfeldt
 * mkoegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.model;

import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;

/**
 * Allows clients to return a custom ID for new model elements.
 * 
 * @author Julian Sommerfeldt
 * @author mkoegel
 */
public interface ESModelElementIdGenerator {

	/**
	 * Generates the next {@link ModelElementId} for the {@link ESObjectContainer}.
	 * 
	 * @param container The {@link ESObjectContainer} for which a new {@link ModelElementId} should be created.
	 * @return The next {@link ModelElementId} for the given {@link ESObjectContainer}.
	 */
	ModelElementId generateModelElementId(ESObjectContainer container);

}
