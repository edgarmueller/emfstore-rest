/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.model;

import org.eclipse.emf.ecore.EObject;

/**
 * Common interface for mapping {@link EObject}s to an ID type.
 * 
 * @author emueller
 * 
 * @param <T>
 *            the type of the ID being used
 */
public interface ESIdToEObjectMapping<ID> {

	/**
	 * Get the {@link EObject} for the given {@link ESModelElementId}, if any.
	 * 
	 * @param modelElementId the ID
	 * @return the object or null if no EObject for the ID is found
	 */
	EObject get(ID modelElementId);
}
