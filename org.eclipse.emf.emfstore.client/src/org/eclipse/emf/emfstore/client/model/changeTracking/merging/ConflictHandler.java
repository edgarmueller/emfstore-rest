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
package org.eclipse.emf.emfstore.client.model.changeTracking.merging;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.common.model.ModelElementId;

/**
 * Allows to hook in for custom conflict treatment.
 * Conflicting changes are determined by the ConflictDetector, which can be
 * exchanged as well.
 * 
 * @author wesendon
 */
public interface ConflictHandler {

	/**
	 * Called after a conflict has been created and before it is
	 * added to list of all existing conflicts.
	 * 
	 * @param conflict
	 *            a {@link Conflict} instance that has been created by a {@link DecisionManager}
	 * @param idToEObjectMapping
	 *            a mapping from {@link ModelElementId}s to {@link EObject}s. Contains all
	 *            model elements and their IDs that have been created during conflict detection and
	 *            resolution
	 * 
	 * @return the possibly modified conflict instance that will be
	 *         added to the list of conflicts
	 */
	Conflict handle(Conflict conflict, Map<ModelElementId, EObject> idToEObjectMapping);
}