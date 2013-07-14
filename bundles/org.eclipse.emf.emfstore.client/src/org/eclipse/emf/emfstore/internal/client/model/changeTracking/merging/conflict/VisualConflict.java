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
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.FeatureOperation;

/**
 * Main class representing a conflict. it offers all kind of convenience methods
 * and organizes the conflicts initialization. Read the constructor's
 * description for further implemenation details ( {@link #Conflict(List, List, DecisionManager)})
 * 
 * @author wesendon
 */
public abstract class VisualConflict extends Observable {

	private DecisionManager decisionManager;
	private ArrayList<ConflictOption> options;
	private ConflictOption solution;
	private ConflictContext conflictContext;
	private ConflictDescription conflictDescription;

	/**
	 * List of operations.
	 * 
	 * @see #Conflict(List, List, DecisionManager)
	 */
	private Set<AbstractOperation> leftOperations;
	private Set<AbstractOperation> rightOperations;
	protected boolean leftIsMy;
	private AbstractOperation rightOperation;
	private AbstractOperation leftOperation;
	private ConflictBucket conflictBucket;

	public VisualConflict(ConflictBucket conflictBucket, AbstractOperation leftOperation, AbstractOperation rightOperation,
		DecisionManager decisionManager,
		boolean leftIsMy, boolean init) {
		this(set(leftOperation), set(rightOperation), leftOperation, rightOperation, decisionManager, leftIsMy, init);
		this.conflictBucket = conflictBucket;
	}

	public VisualConflict(ConflictBucket conflictBucket, DecisionManager decisionManager) {
		this(conflictBucket, decisionManager, true, true);
	}

	public VisualConflict(ConflictBucket conflictBucket, DecisionManager decisionManager, boolean isLeftMy, boolean init) {
		this(isLeftMy ? conflictBucket.getMyOperations() : conflictBucket.getTheirOperations(),
			isLeftMy ? conflictBucket.getTheirOperations() : conflictBucket.getMyOperations(),
			isLeftMy ? conflictBucket.getMyOperation() : conflictBucket.getTheirOperation(),
			isLeftMy ? conflictBucket.getTheirOperation() : conflictBucket.getMyOperation(),
			decisionManager, isLeftMy, init);
		this.conflictBucket = conflictBucket;
	}

	/**
	 * Additional constructor, which allows deactivating initialization.
	 * 
	 * @see #Conflict(List, List, DecisionManager)
	 * @param leftOperations
	 *            first list of operations (often: myOperations)
	 * @param rightOperations
	 *            second list of operations (often: theirOperations)
	 * @param leftOperation an operation representing all left operations
	 * @param rightOperation an operation representing all right operations
	 * @param decisionManager
	 *            decision manager
	 * @param leftIsMy
	 *            left operations are my changes
	 * @param init
	 *            allows to deactivate initialization, has to be done manually
	 *            otherwise.
	 */
	private VisualConflict(Set<AbstractOperation> leftOperations, Set<AbstractOperation> rightOperations,
		AbstractOperation leftOperation, AbstractOperation rightOperation, DecisionManager decisionManager,
		boolean leftIsMy, boolean init) {
		this.leftOperation = leftOperation;
		this.rightOperation = rightOperation;
		this.leftIsMy = leftIsMy;
		this.leftOperations = leftOperations;
		this.rightOperations = rightOperations;
		this.decisionManager = decisionManager;

		if (init) {
			init();
		}
	}

	private static <T> Set<T> set(T object) {
		Set<T> set = new LinkedHashSet<T>();
		set.add(object);
		return set;
	}

	/**
	 * Determines whether left operations are my.
	 * 
	 * @return boolean
	 */
	public boolean isLeftMy() {
		return leftIsMy;
	}

	/**
	 * Initiates the conflict.
	 */
	protected void init() {
		conflictContext = initConflictContext();
		conflictDescription = initConflictDescription();
		options = new ArrayList<ConflictOption>();
		initConflictOptions(options);
	}

	/**
	 * Is called in order to init the options.
	 * 
	 * @param options
	 *            list of options
	 */
	protected abstract void initConflictOptions(List<ConflictOption> options);

	/**
	 * Init conflict description.
	 * 
	 * @param description
	 *            pre initialized description
	 * @return description
	 */
	protected abstract ConflictDescription initConflictDescription(ConflictDescription description);

	private ConflictDescription initConflictDescription() {
		ConflictDescription description = new ConflictDescription("");
		description.setImage("notset.gif");
		EObject modelElement = getDecisionManager().getModelElement(getMyOperation().getModelElementId());
		if (modelElement != null) {
			description.add("modelelement", modelElement);
		}
		if (getMyOperation() instanceof FeatureOperation) {
			description.add("feature", ((FeatureOperation) getMyOperation()).getFeatureName());
		}
		description.setDecisionManager(getDecisionManager());
		return initConflictDescription(description);
	}

	/**
	 * Inits the ConflictContext.
	 * 
	 * @return context.
	 */
	protected ConflictContext initConflictContext() {
		return new ConflictContext(getDecisionManager(), getMyOperation(), getTheirOperation());
	}

	/**
	 * Returns the conflict context.
	 * 
	 * @return context.
	 */
	public ConflictContext getConflictContext() {
		return conflictContext;
	}

	/**
	 * Returns the conflict description.
	 * 
	 * @return conflict description
	 */
	public ConflictDescription getConflictDescription() {
		return conflictDescription;
	}

	/**
	 * Returns the list of options.
	 * 
	 * @return list options
	 */
	public List<ConflictOption> getOptions() {
		return options;
	}

	/**
	 * Returns whether this conflict is resolved.
	 * 
	 * @return true if resolved
	 */
	public boolean isResolved() {
		return (solution != null);
	}

	/**
	 * Checks whether the related options have details.
	 * 
	 * @return true, if at least one got details.
	 */
	public boolean hasDetails() {
		for (ConflictOption option : getOptions()) {
			if (option.isDetailsProvider()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets an options as solution for this conflict.
	 * 
	 * @param conflictOption
	 *            option
	 */
	public void setSolution(ConflictOption conflictOption) {
		solution = conflictOption;
		setChanged();
		notifyObservers();
	}

	/**
	 * Returns the {@link DecisionManager}.
	 * 
	 * @return decisionManager
	 */
	public DecisionManager getDecisionManager() {
		return decisionManager;
	}

	/**
	 * Returns the solution.
	 * 
	 * @return solution
	 */
	public ConflictOption getSolution() {
		return solution;
	}

	/**
	 * This method is used by {@link DecisionManager} in order to create the
	 * resulting operations.
	 * 
	 * @return list of ops.
	 */
	public Set<AbstractOperation> getRejectedTheirs() {
		if (!isResolved()) {
			throw new IllegalStateException("Can't call this method, unless conflict is resolved.");
		}
		if (solution.getType() == OptionType.TheirOperation) {
			return Collections.emptySet();
		} else {
			for (ConflictOption options : getOptions()) {
				if (options.getType() == OptionType.TheirOperation) {
					return options.getOperations();
				}
			}
		}
		throw new IllegalStateException("No TheirOperations found.");
		// return new ArrayList<AbstractOperation>();
	}

	/**
	 * This method is used by {@link DecisionManager} in order to create the
	 * resulting operations.
	 * 
	 * @return list of ops
	 */
	public Set<AbstractOperation> getAcceptedMine() {
		if (!isResolved()) {
			throw new IllegalStateException("Can't call this method, unless conflict is resolved.");
		}
		if (solution.getType() == OptionType.TheirOperation) {
			return Collections.emptySet();
		} else {
			return solution.getOperations();
		}
	}

	/**
	 * Get an option by its type.
	 * 
	 * @param type
	 *            type
	 * @return option or null
	 */
	public ConflictOption getOptionOfType(OptionType type) {
		return DecisionUtil.getConflictOptionByType(getOptions(), type);
	}

	/**
	 * Get my operations.
	 * 
	 * @return list of operations
	 */
	public Set<AbstractOperation> getMyOperations() {
		return ((leftIsMy) ? leftOperations : rightOperations);
	}

	/**
	 * Get their operations.
	 * 
	 * @return list of operations
	 */
	public Set<AbstractOperation> getTheirOperations() {
		return ((!leftIsMy) ? leftOperations : rightOperations);
	}

	/**
	 * Get left operations.
	 * 
	 * @return list of operations
	 */
	public Set<AbstractOperation> getLeftOperations() {
		return leftOperations;
	}

	/**
	 * get right operations.
	 * 
	 * @return list of operations
	 */
	public Set<AbstractOperation> getRightOperations() {
		return rightOperations;
	}

	/**
	 * Get first left operation.
	 * 
	 * @return operation
	 */
	public AbstractOperation getLeftOperation() {
		return leftOperation;
	}

	/**
	 * get first right operation.
	 * 
	 * @return operation
	 */
	public AbstractOperation getRightOperation() {
		return rightOperation;
	}

	/**
	 * Get my operation.
	 * 
	 * @return operation
	 */
	public AbstractOperation getMyOperation() {
		return ((leftIsMy) ? leftOperation : rightOperation);
	}

	/**
	 * Get their operation.
	 * 
	 * @return operation
	 */
	public AbstractOperation getTheirOperation() {
		return ((!leftIsMy) ? leftOperation : rightOperation);
	}

	/**
	 * Get my operation and cast.
	 * 
	 * @param <T>
	 *            cast type
	 * @param clazz
	 *            {@link AbstractOperation} class to which will be casted
	 * @return operation
	 */
	@SuppressWarnings("unchecked")
	public <T> T getMyOperation(Class<T> clazz) {
		return (T) getMyOperation();
	}

	/**
	 * Get their operation and cast.
	 * 
	 * @param <T>
	 *            cast type
	 * @param clazz
	 *            {@link AbstractOperation} class to which will be casted
	 * @return operation
	 */
	@SuppressWarnings("unchecked")
	public <T> T getTheirOperation(Class<T> clazz) {
		return (T) getTheirOperation();
	}

	public void resolve() {
		conflictBucket.resolveConflict(getAcceptedMine(), getRejectedTheirs());
	}
}
