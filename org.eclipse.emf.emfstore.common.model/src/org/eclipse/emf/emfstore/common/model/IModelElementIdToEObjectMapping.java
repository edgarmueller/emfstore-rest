/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
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

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * Interface for representing a mapping from EObjects to their respective IDs and vice versa.
 * 
 * @author emueller
 */
public interface IModelElementIdToEObjectMapping {

	/**
	 * Retrieves the complete mapping from model element IDs to model elements.
	 * 
	 * @return the mapping from model element IDs to EObjects
	 */
	Map<String, EObject> getIdToEObjectMapping();

	/**
	 * Retrieves the complete reverse mapping from model elements to their respective model element IDs.
	 * 
	 * @return the mapping from EObjects to model element IDs
	 */
	Map<EObject, String> getEObjectToIdMapping();
}
