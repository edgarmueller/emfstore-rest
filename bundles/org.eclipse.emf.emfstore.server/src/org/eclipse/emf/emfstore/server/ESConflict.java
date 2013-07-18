/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.server;

import java.util.Set;

import org.eclipse.emf.emfstore.server.model.ESOperation;

/**
 * Represents conflict operations. Conflicting operations change the contents of a project, that is its model elements
 * and their containment tree in way that is overlapping. For example the conflicting operations might try to delete and
 * alter an
 * EObject at the same time.
 * 
 * 
 * @author mkoegel
 * 
 */
public interface ESConflict {

	/**
	 * The involved local operations. These operations are negotiable. This means they have not been committed as part
	 * of a revision on the current branch. Rejecting them is easy, they can just be dropped.
	 * The local operations are the users local operations that have not been committed yet or the operations of a
	 * another branch that is merged into the current branch.
	 * 
	 * @return a set of operations
	 */
	Set<ESOperation> getLocalOperations();

	/**
	 * The involved remote operations. These operations are non-negotiable. This means they have already been committed
	 * as part of a revision on the current branch. They have to be reverted if they are rejected. This means a counter
	 * operation canceling out the original operation will need to be calculated.
	 * The remote operations are usually their operations (operations of other committers, that are committed to the
	 * server already) or the operations of the current branch if another branch is merged into that branch.
	 * 
	 * @return a set of operations
	 */
	Set<ESOperation> getRemoteOperations();

	/**
	 * Resolve the conflict by accepting the given local operations and rejecting the given remote operations.
	 * 
	 * @param acceptedLocalOperations a list of local accepted operations
	 * @param rejectedRemoteOperations a list of rejected remote operations
	 */
	void resolveConflict(Set<ESOperation> acceptedLocalOperations, Set<ESOperation> rejectedRemoteOperations);
}
