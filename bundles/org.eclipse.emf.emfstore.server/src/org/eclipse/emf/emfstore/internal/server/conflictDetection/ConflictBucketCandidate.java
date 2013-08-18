/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.conflictDetection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * <p>
 * Represents a bucket containing operations that potentially conflict but that do not neccessarily conflict. It also
 * includes the involved model element ids and the priority of each operation.
 * </p>
 * <p>
 * The operation with the highest priority is used to determine which of the operations is used to represent all my and
 * all their operations in a conflict. The operation with the highest priority is selected for representation.
 * </p>
 * 
 * @author koegel
 * 
 */
public class ConflictBucketCandidate {

	private Set<AbstractOperation> myOperations;
	private Set<AbstractOperation> theirOperations;
	private Map<AbstractOperation, Integer> operationToPriorityMap;
	private ConflictBucketCandidate parentConflictBucketCandidate;

	/**
	 * Default constructor.
	 */
	public ConflictBucketCandidate() {
		myOperations = new LinkedHashSet<AbstractOperation>();
		theirOperations = new LinkedHashSet<AbstractOperation>();
		operationToPriorityMap = new LinkedHashMap<AbstractOperation, Integer>();
	}

	/**
	 * Add an operation for a model element id and its feature to the bucket.
	 * 
	 * @param operation the operation
	 * @param isMyOperation a boolean to determine if the operation is to be added to mz or their operations
	 * @param priority the global priority of the operation
	 */
	public void addOperation(AbstractOperation operation, boolean isMyOperation, int priority) {
		if (operation == null) {
			return;
		}
		operationToPriorityMap.put(operation, priority);
		if (isMyOperation) {
			myOperations.add(operation);
		} else {
			theirOperations.add(operation);
		}
	}

	/**
	 * Add another another conflict candidate bucket to this bucket including all their collected operations and
	 * invoveld ids.
	 * 
	 * @param otherBucket the other bucket
	 */
	public void addConflictBucketCandidate(ConflictBucketCandidate otherBucket) {
		if (otherBucket == null) {
			return;
		}

		myOperations.addAll(otherBucket.getMyOperations());
		theirOperations.addAll(otherBucket.getTheirOperations());
		operationToPriorityMap.putAll(otherBucket.operationToPriorityMap);
	}

	/**
	 * Returns the root conflict bucket this bucket belongs to.
	 * 
	 * @return the root conflict bucket
	 */
	public ConflictBucketCandidate getRootConflictBucketCandidate() {
		if (parentConflictBucketCandidate == null) {
			return this;
		}
		return getParentConflictBucketCandidate(new ArrayList<ConflictBucketCandidate>());
	}

	private ConflictBucketCandidate getParentConflictBucketCandidate(List<ConflictBucketCandidate> pathToRoot) {
		if (parentConflictBucketCandidate == null) {
			// this is root, compress path
			for (ConflictBucketCandidate conflictBucketCandidate : pathToRoot) {
				conflictBucketCandidate.setParentConflictBucketCandidate(this);
			}
			return this;
		}
		// root not yet found
		pathToRoot.add(this);
		return parentConflictBucketCandidate.getParentConflictBucketCandidate(pathToRoot);
	}

	/**
	 * Sets the parent conflict bucket of this bucket.
	 * 
	 * @param parentConflictBucketCandidate
	 *            the parent bucket of this bucket
	 */
	public void setParentConflictBucketCandidate(ConflictBucketCandidate parentConflictBucketCandidate) {
		// disallow loops
		if (this == parentConflictBucketCandidate) {
			return;
		}
		this.parentConflictBucketCandidate = parentConflictBucketCandidate;
	}

	/**
	 * @return the size of the bucket in the total number of involved operations
	 */
	public int size() {
		return theirOperations.size() + myOperations.size();
	}

	/**
	 * @return true, if the set is conflicting, that is my and their operations are not empty
	 */
	public boolean isConflicting() {
		return theirOperations.size() > 0 && myOperations.size() > 0;
	}

	/**
	 * @return my operations
	 */
	public Set<AbstractOperation> getMyOperations() {
		return myOperations;
	}

	/**
	 * @return their operations
	 */
	public Set<AbstractOperation> getTheirOperations() {
		return theirOperations;
	}

	/**
	 * Calculate a set of conflict buckets from this candidate bucket. The result set may be empty if no conflicts are
	 * found within
	 * the candidate bucket.
	 * 
	 * @param detector the conflict detector
	 * @param myOperationsNonConflictingOperations a transient set where all non conflicting my operations are added
	 *            to
	 *            during this operation
	 * @return a set of conflict buckets
	 */
	public Set<ConflictBucket> calculateConflictBuckets(ConflictDetector detector,
		Set<AbstractOperation> myOperationsNonConflictingOperations) {
		Set<ConflictBucket> conflictBucketsSet = new LinkedHashSet<ConflictBucket>();

		// if the bucket is not conflicting (empty my or their) just add all my operations to non conflicting set
		if (!isConflicting()) {
			myOperationsNonConflictingOperations.addAll(myOperations);
			return conflictBucketsSet;
		}

		ConflictBucket newConflictBucket = new ConflictBucket(getMyOperations(), getTheirOperations());
		conflictBucketsSet.add(newConflictBucket);
		return selectMyandTheirOperation(conflictBucketsSet);
	}

	private Set<ConflictBucket> selectMyandTheirOperation(Set<ConflictBucket> conflictBucketsSet) {

		for (ConflictBucket conflictBucket : conflictBucketsSet) {
			Integer maxPriority = -1;
			AbstractOperation maxOperation = null;
			for (AbstractOperation myOperation : conflictBucket.getMyOperations()) {
				Integer currentPrio = operationToPriorityMap.get(myOperation);
				if (currentPrio > maxPriority) {
					maxPriority = currentPrio;
					maxOperation = myOperation;
				}
			}
			conflictBucket.setMyOperation(maxOperation);
		}

		for (ConflictBucket conflictBucket : conflictBucketsSet) {
			Integer maxPriority = -1;
			AbstractOperation maxOperation = null;
			for (AbstractOperation theirOperation : conflictBucket.getTheirOperations()) {
				Integer currentPrio = operationToPriorityMap.get(theirOperation);
				if (currentPrio > maxPriority) {
					maxPriority = currentPrio;
					maxOperation = theirOperation;
				}
			}
			conflictBucket.setTheirOperation(maxOperation);
		}

		return conflictBucketsSet;
	}
}