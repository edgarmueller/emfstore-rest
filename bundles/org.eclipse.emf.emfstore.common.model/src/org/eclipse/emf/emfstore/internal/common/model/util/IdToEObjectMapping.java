/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * Edgar
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;

/**
 * @author Edgar
 * 
 */
public interface IdToEObjectMapping<T> {

	/**
	 * Get the {@link EObject} for the given {@link ESModelElementId}, if any.
	 * 
	 * @param modelElementId the ID
	 * @return the object or null if no EObject for the ID is found
	 */
	EObject get(T modelElementId);
}
