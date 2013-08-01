/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging;

import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet;

/**
 * Convenience super class for implementing {@link ConflictResolver}.
 * 
 * @author wesendon
 */
public abstract class AbstractConflictResolver implements ConflictResolver {

	private final boolean isBranchMerge;

	/**
	 * Default Constructor.
	 * 
	 * @param isBranchMerge
	 *            specifies whether two branches are merged oppossed to changes
	 *            from the same branch.
	 */
	public AbstractConflictResolver(boolean isBranchMerge) {
		this.isBranchMerge = isBranchMerge;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.ConflictResolver#resolveConflicts(org.eclipse.emf.emfstore.internal.common.model.Project,
	 *      org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet)
	 */
	public boolean resolveConflicts(Project project, ChangeConflictSet changeConflict) {

		// allow subclasses do execute before the decisionmanager is initialized
		preDecisionManagerHook();

		final DecisionManager decisionManager = new DecisionManager(project, changeConflict, isBranchMerge);

		// if all conflicts are resolved, there's no need for further actions
		if (decisionManager.isResolved()) {
			setResults(decisionManager);
			return true;
		}

		// handle conflicts, most likely using the MergeWizard
		final boolean resolved = controlDecisionManager(decisionManager, changeConflict);
		if (resolved) {
			if (!decisionManager.isResolved()) {
				return false;
			}
			setResults(decisionManager);
		}

		postDecisionManagerHook();
		return resolved;
	}

	private void setResults(DecisionManager decisionManager) {
		decisionManager.calcResult();
	}

	/**
	 * Allows to execute code before the {@link DecisionManager} is
	 * initialized.
	 */
	protected void preDecisionManagerHook() {
		// do nothing by default
	}

	/**
	 * Allows to execute code after the {@link DecisionManager} is
	 * finished.
	 */
	protected void postDecisionManagerHook() {
		// do nothing by default
	}

	/**
	 * Conflict resolution should be implemented in this method.
	 * 
	 * @param decisionManager
	 *            initialized {@link DecisionManager}
	 * @param changeConflictSet the conflict set to be resolved
	 * @return true, if all conflicts could be resolved
	 */
	protected abstract boolean controlDecisionManager(DecisionManager decisionManager,
		ChangeConflictSet changeConflictSet);

}
