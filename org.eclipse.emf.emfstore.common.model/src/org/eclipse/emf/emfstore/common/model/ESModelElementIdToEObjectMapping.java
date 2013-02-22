/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.model;

import org.eclipse.emf.ecore.EObject;

/**
 * Interface for representing a mapping from EObjects to their respective IDs and vice versa.
 * 
 * @author emueller
 */
public interface ESModelElementIdToEObjectMapping {

	/**
	 * Get the {@link EObject} for the given {@link ESModelElementId}, if any.
	 * 
	 * @param modelElementId the ID
	 * @return the object or null if no EObject for the ID is found
	 */
	EObject get(ESModelElementId modelElementId);

}