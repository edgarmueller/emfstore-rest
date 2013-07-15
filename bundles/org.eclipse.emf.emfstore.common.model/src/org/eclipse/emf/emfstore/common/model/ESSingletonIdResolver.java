/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.model;

import org.eclipse.emf.ecore.EObject;

/**
 * Interface for mapping {@link ESModelElementId}s to singleton objects and vice versa.
 * 
 * @author emueller
 */
public interface ESSingletonIdResolver {

	/**
	 * Returns the ID for the given singleton {@link EObject}.
	 * 
	 * @param singleton the singleton {@link EObject} whose {@link ESModelElementId} should get retrieved
	 * @return the {@link ESModelElementId} of the the singleton object or <code>null</code> if the given
	 *         {@link EObject} is not a singleton or if it is <code>null</code>
	 */
	ESModelElementId getSingletonModelElementId(EObject singleton);

	/**
	 * Returns the singleton which belongs to the given {@link ESModelElementId}.
	 * 
	 * @param singletonId a {@link ESModelElementId}
	 * @return the singleton {@link EObject} that belongs to the given {@link ESModelElementId}
	 */
	EObject getSingleton(ESModelElementId singletonId);

	/**
	 * Determines whether the given {@link EObject} is a singleton.
	 * 
	 * @param eObject the EObject to check
	 * @return true, iff the given {@link EObject} is a singleton
	 */
	boolean isSingleton(EObject eObject);
}
