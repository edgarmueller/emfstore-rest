package org.eclipse.emf.emfstore.client.model.changeTracking.merging;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

public abstract class AbstractConflictResolver implements ConflictResolver {

	protected List<AbstractOperation> acceptedMine;
	protected List<AbstractOperation> rejectedTheirs;
	protected final boolean isBranchMerge;

	public AbstractConflictResolver(boolean isBranchMerge) {
		this.isBranchMerge = isBranchMerge;
		acceptedMine = new ArrayList<AbstractOperation>();
		rejectedTheirs = new ArrayList<AbstractOperation>();
	}

	public AbstractConflictResolver() {
		this(false);
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
			target, isBranchMerge);

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

	protected void preDecisionManagerHook() {
	}

	abstract protected boolean controlDecisionManager(DecisionManager decisionManager);

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
