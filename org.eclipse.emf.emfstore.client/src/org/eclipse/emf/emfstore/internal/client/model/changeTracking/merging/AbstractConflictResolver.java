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
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * Convenience super class for implementing {@link IConflictResolver}.
 * 
 * @author wesendon
 */
public abstract class AbstractConflictResolver implements IConflictResolver {

	private List<AbstractOperation> acceptedMine;
	private List<AbstractOperation> rejectedTheirs;
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
		acceptedMine = new ArrayList<AbstractOperation>();
		rejectedTheirs = new ArrayList<AbstractOperation>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.IConflictResolver#getAcceptedMine()
	 */
	public List<AbstractOperation> getAcceptedMine() {
		return acceptedMine;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.IConflictResolver#getAcceptedMine()
	 */
	public List<AbstractOperation> getRejectedTheirs() {
		return rejectedTheirs;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.IConflictResolver#resolveConflicts(org.eclipse.emf.emfstore.internal.common.model.internal.common.model.Project,
	 *      java.util.List, java.util.List,
	 *      org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec,
	 *      org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec)
	 */
	public boolean resolveConflicts(Project project, ChangeConflictException conflictException,
		PrimaryVersionSpec base, PrimaryVersionSpec target) {

		// allow subclasses do execute before the decisionmanager is initialized
		preDecisionManagerHook();

		DecisionManager decisionManager = new DecisionManager(project, conflictException, base, target, isBranchMerge);

		// if all conflicts are resolved, there's no need for further actions
		if (decisionManager.isResolved()) {
			setResults(decisionManager);
			return true;
		}

		// handle conflicts, most likely using the MergeWizard
		boolean resolved = controlDecisionManager(decisionManager);
		if (resolved) {
			if (!decisionManager.isResolved()) {
				return false;
			}
			setResults(decisionManager);
		}
		return resolved;
	}

	private void setResults(DecisionManager decisionManager) {
		decisionManager.calcResult();
		acceptedMine = decisionManager.getAcceptedMine();
		rejectedTheirs = decisionManager.getRejectedTheirs();
	}

	/**
	 * Allows to execute code before the {@link DecisionManager} is
	 * initiallized.
	 */
	protected void preDecisionManagerHook() {
	}

	/**
	 * Conflict resolution should be implemented in this method.
	 * 
	 * @param decisionManager
	 *            initialized {@link DecisionManager}
	 * @return true, if all conflicts could be resolved
	 */
	protected abstract boolean controlDecisionManager(DecisionManager decisionManager);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.IConflictResolver#getMergedResult()
	 */
	public ChangePackage getMergedResult() {
		List<AbstractOperation> mergeResult = new ArrayList<AbstractOperation>();
		for (AbstractOperation operationToReverse : getRejectedTheirs()) {
			mergeResult.add(0, operationToReverse.reverse());
		}

		mergeResult.addAll(getAcceptedMine());
		ChangePackage result = VersioningFactory.eINSTANCE.createChangePackage();
		result.getOperations().addAll(mergeResult);

		return result;
	}
}