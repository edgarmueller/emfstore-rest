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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

/**
 * Convenience super class for implementing {@link ConflictResolver}.
 * 
 * @author wesendon
 */
public abstract class AbstractConflictResolver implements ConflictResolver {

	private List<AbstractOperation> acceptedMine;
	private List<AbstractOperation> rejectedTheirs;
	private final boolean isBranchMerge;
	private ChangeConflictException conflictException;

	/**
	 * Default Constructor.
	 * 
	 * @param isBranchMerge
	 *            specifies whether two branches are merged oppossed to changes
	 *            from the same branch.
	 * @param conflictException a conflict exception with preliminary results
	 */
	public AbstractConflictResolver(boolean isBranchMerge, ChangeConflictException conflictException) {
		this.isBranchMerge = isBranchMerge;
		this.conflictException = conflictException;
		acceptedMine = new ArrayList<AbstractOperation>();
		rejectedTheirs = new ArrayList<AbstractOperation>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.changeTracking.merging.ConflictResolver#getAcceptedMine()
	 */
	public List<AbstractOperation> getAcceptedMine() {
		return acceptedMine;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.changeTracking.merging.ConflictResolver#getAcceptedMine()
	 */
	public List<AbstractOperation> getRejectedTheirs() {
		return rejectedTheirs;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.changeTracking.merging.ConflictResolver#resolveConflicts(org.eclipse.emf.emfstore.common.model.Project,
	 *      java.util.List, java.util.List, org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec)
	 */
	public boolean resolveConflicts(Project project, List<ChangePackage> myChangePackages,
		List<ChangePackage> theirChangePackages, PrimaryVersionSpec base, PrimaryVersionSpec target) {

		preDecisionManagerHook();

		DecisionManager decisionManager = new DecisionManager(project, myChangePackages, theirChangePackages, base,
			target, isBranchMerge, conflictException);

		if (decisionManager.isResolved()) {
			setResults(decisionManager);
			return true;
		}

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
	 * @see org.eclipse.emf.emfstore.client.model.changeTracking.merging.ConflictResolver#getMergedResult()
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