/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * wesendon
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging;

import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet;

/**
 * Represents a controller that can merge conflicting changes resulting in a
 * list of changes that is not conflicting any more.
 * 
 * @author koegel
 * @author wesendon
 */
public interface ConflictResolver {

	/**
	 * Resolves all conflicts between the given change packages "theirs" and the
	 * given local operations.
	 * 
	 * @param project
	 *            the project at the time were all local changes are already
	 *            applied and their operations are NOT, in other words the base
	 *            version plus local operations
	 * @param changeConflict
	 *            the {@link ChangeConflictSet} that needs to be resolved
	 * @return true if the merge can proceed, false if it has to be cancelled
	 */
	boolean resolveConflicts(Project project, ChangeConflictSet changeConflict);
}
