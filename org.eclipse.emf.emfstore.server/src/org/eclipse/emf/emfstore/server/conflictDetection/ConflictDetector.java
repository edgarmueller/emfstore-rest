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
package org.eclipse.emf.emfstore.server.conflictDetection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

/**
 * Detects conflicts with a given {@link ConflictDetectionStrategy}.
 * 
 * @author koegel
 */
public class ConflictDetector {

	private static ConflictDetectionStrategy defaultStrategy;

	private ConflictDetectionStrategy conflictDetectionStrategy;

	/**
	 * Constructor. Uses default conflict detection strategy
	 */
	public ConflictDetector() {
		this(getStrategy());
	}

	private static ConflictDetectionStrategy getStrategy() {
		if (defaultStrategy == null) {
			ConflictDetectionStrategy strategy = new ExtensionPoint(
				"org.eclipse.emf.emfstore.client.merge.conflictDetectorStrategy").getClass("class",
				ConflictDetectionStrategy.class);
			if (strategy != null) {
				defaultStrategy = strategy;
			} else {
				defaultStrategy = new IndexSensitiveConflictDetectionStrategy();
			}
		}
		return defaultStrategy;
	}

	/**
	 * Constructor with a given strategy.
	 * 
	 * @param conflictDetectionStrategy the detection strategy to use
	 */
	public ConflictDetector(ConflictDetectionStrategy conflictDetectionStrategy) {
		this.conflictDetectionStrategy = conflictDetectionStrategy;
	}

	/**
	 * Determines if two changepackages are conflicting.
	 * 
	 * @param operation operation
	 * @param otherOperation otheroperation
	 * @return true, if conflicting
	 */
	public boolean doConflict(AbstractOperation operation, AbstractOperation otherOperation) {
		return conflictDetectionStrategy.doConflict(operation, otherOperation);
	}

	public boolean containsConflictingBuckets(Set<ConflictBucketCandidate> conflictBucketCandidates) {
		for (ConflictBucketCandidate conflictBucketCandidate : conflictBucketCandidates) {
			if (conflictBucketCandidate.isConflicting()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param myOperations
	 * @param theirOperations
	 * @param conflictBucketsCandidateSet
	 */
	public Set<ConflictBucketCandidate> scanOperationsIntoCandidateBuckets(List<ChangePackage> myChangePackages,
		List<ChangePackage> theirChangePackages) {

		List<AbstractOperation> myOperations = flattenChangepackages(myChangePackages);
		List<AbstractOperation> theirOperations = flattenChangepackages(theirChangePackages);

		Set<ConflictBucketCandidate> conflictBucketsCandidateSet = new LinkedHashSet<ConflictBucketCandidate>();
		Map<String, ConflictBucketCandidate> idToConflictBucketMap = new LinkedHashMap<String, ConflictBucketCandidate>();

		int counter = 0;
		for (AbstractOperation myOperation : myOperations) {
			scanOperationIntoConflictBucket(conflictBucketsCandidateSet, idToConflictBucketMap, myOperation, true,
				counter);
			counter++;
		}

		for (AbstractOperation theirOperation : theirOperations) {
			scanOperationIntoConflictBucket(conflictBucketsCandidateSet, idToConflictBucketMap, theirOperation, false,
				counter);
			counter++;
		}
		return conflictBucketsCandidateSet;
	}

	private List<AbstractOperation> flattenChangepackages(List<ChangePackage> cps) {
		List<AbstractOperation> operations = new ArrayList<AbstractOperation>();
		for (ChangePackage cp : cps) {
			operations.addAll(cp.getOperations());
		}
		return operations;
	}

	private void scanOperationIntoConflictBucket(Set<ConflictBucketCandidate> conflictBucketsSet,
		Map<String, ConflictBucketCandidate> idToConflictBucketMap, AbstractOperation operation, boolean isMyOperation,
		int priority) {
		Set<String> allInvolvedModelElements = extractStringSetFromIds(operation.getAllInvolvedModelElements());
		ConflictBucketCandidate currentOperationsConflictBucket = null;
		for (String modelElementId : allInvolvedModelElements) {
			ConflictBucketCandidate conflictBucket = idToConflictBucketMap.get(modelElementId.toString());

			if (conflictBucket == null && currentOperationsConflictBucket == null) {
				// no existing ConflictBucket for id or current op => create new ConflictBucket
				currentOperationsConflictBucket = new ConflictBucketCandidate();
				conflictBucketsSet.add(currentOperationsConflictBucket);
				currentOperationsConflictBucket.addOperation(operation, modelElementId, isMyOperation, priority);
				idToConflictBucketMap.put(modelElementId, currentOperationsConflictBucket);
			} else if (conflictBucket == currentOperationsConflictBucket) {
				idToConflictBucketMap.put(modelElementId, currentOperationsConflictBucket);
				currentOperationsConflictBucket.addModelElementId(modelElementId);
			} else if (conflictBucket == null && currentOperationsConflictBucket != null) {
				// no existing ConflictBucket for id but existing ConflictBucket for operation => keep operations
				// ConflictBucket
				idToConflictBucketMap.put(modelElementId, currentOperationsConflictBucket);
				currentOperationsConflictBucket.addModelElementId(modelElementId);
			} else if (conflictBucket != null && currentOperationsConflictBucket == null) {
				// existing ConflictBucket for id but none for operation => keep id ConflictBucket
				currentOperationsConflictBucket = conflictBucket;
				currentOperationsConflictBucket.addOperation(operation, modelElementId, isMyOperation, priority);
			} else {
				// existing ConflictBucket for both id and operation => merge ConflictBuckets
				currentOperationsConflictBucket = mergeConflictBuckets(conflictBucketsSet, idToConflictBucketMap,
					currentOperationsConflictBucket, conflictBucket);
				currentOperationsConflictBucket.addModelElementId(modelElementId);
			}
		}
	}

	private Set<String> extractStringSetFromIds(Set<ModelElementId> allInvolvedModelElements) {
		Set<String> result = new LinkedHashSet<String>(allInvolvedModelElements.size());
		for (ModelElementId modelElementId : allInvolvedModelElements) {
			result.add(modelElementId.getId());
		}
		return result;
	}

	private ConflictBucketCandidate mergeConflictBuckets(Set<ConflictBucketCandidate> conflictBucketsSet,
		Map<String, ConflictBucketCandidate> idToConflictBucketMap,
		ConflictBucketCandidate currentOperationsConflictBucket, ConflictBucketCandidate conflictBucket) {

		ConflictBucketCandidate biggerBucket = currentOperationsConflictBucket;
		ConflictBucketCandidate smallerBucket = conflictBucket;

		if (conflictBucket.size() > currentOperationsConflictBucket.size()) {
			biggerBucket = conflictBucket;
			smallerBucket = currentOperationsConflictBucket;
		}
		biggerBucket.addConflictBucketCandidate(smallerBucket);

		for (String id : smallerBucket.getInvolvedIds()) {
			idToConflictBucketMap.put(id, biggerBucket);
		}
		conflictBucketsSet.remove(smallerBucket);

		return biggerBucket;
	}

	public Set<ConflictBucket> scanForConflictsWithinCandidates(
		Set<ConflictBucketCandidate> conflictBucketsCandidateSet, Set<AbstractOperation> notInvolvedInConflict) {
		Set<ConflictBucket> conflictBucketsSet = new LinkedHashSet<ConflictBucket>();
		for (ConflictBucketCandidate conflictBucketCandidate : conflictBucketsCandidateSet) {
			conflictBucketsSet.addAll(conflictBucketCandidate.calculateConflictBuckets(this, notInvolvedInConflict));
		}
		return conflictBucketsSet;
	}

	/**
	 * Retrieve all operations in other ops that are conflicting with operations in ops. If any operation is in both
	 * lists, it is not considered to be conflicting.
	 * 
	 * @param ops A list of operations.
	 * @param otherOps A list of the other operations.
	 * @return A set of conflicting operations which is a subset of otherOps.
	 */
	public Set<AbstractOperation> getConflicting(List<AbstractOperation> ops, List<AbstractOperation> otherOps) {
		// the operations that are conflicting
		Set<AbstractOperation> conflicting = new HashSet<AbstractOperation>();

		// check each operation in ops against otherOps
		for (AbstractOperation position : ops) {
			for (AbstractOperation other : otherOps) {
				if (conflicting.contains(other)) {
					// a conflict has already been registered
					continue;
				}
				// if there is a conflict, add the other op to the
				// list of conflicting ops along with all ops that
				// require other ops
				if (conflictDetectionStrategy.doConflict(position, other)) {
					conflicting.addAll(getRequiring(otherOps, other));
					conflicting.add(other);
				}
			}

		}

		// return the set of conflicting operations in other ops
		return conflicting;
	}

	/**
	 * Retrieve all operations in other ops that are conflicting on "index integrity only" with operations in ops. If
	 * any operation is in both lists, it is not considered to be conflicting.
	 * 
	 * @param ops A list of operations.
	 * @param otherOps A list of the other operations.
	 * @return A set of conflicting operations which is a subset of otherOps.
	 */
	public Set<AbstractOperation> getConflictingIndexIntegrity(List<AbstractOperation> ops,
		List<AbstractOperation> otherOps) {
		// the operations that are conflicting
		Set<AbstractOperation> conflicting = new HashSet<AbstractOperation>();

		// works with only one strategy, as of now, hardcoding it
		IndexSensitiveConflictDetectionStrategy indexSensitiveStrategy = new IndexSensitiveConflictDetectionStrategy();

		// check each operation in ops against otherOps
		for (AbstractOperation position : ops) {
			for (AbstractOperation other : otherOps) {
				if (conflicting.contains(other)) {
					// a conflict has already been registered
					continue;
				}
				// if there is a conflict, add the other op to the
				// list of conflicting ops along with all ops that
				// require other ops

				if (indexSensitiveStrategy.doConflictIndexIntegrity(position, other)) {
					conflicting.addAll(getRequiring(otherOps, other));
					conflicting.add(other);
				}
			}

		}

		// return the set of conflicting operations in other ops
		return conflicting;
	}

	/**
	 * Retrieve all operations in ops that are required by op. The operation <code>op</code> must be part of
	 * <code>ops</code>.
	 * 
	 * @param ops A time ordered (ascending) list of operations containing op.
	 * @param op The operation for which the requirements should be determined.
	 * @return A list of operations that are required for op which is a subset of ops. <code>op</code> will not be part
	 *         of the returned list.
	 * @throws IllegalArgumentException If op is not in ops.
	 */
	public List<AbstractOperation> getRequired(List<AbstractOperation> ops, AbstractOperation op)
		throws IllegalArgumentException {
		// sanity check
		if (!ops.contains(op)) {
			throw new IllegalArgumentException("the ops list dos not contain op");
		}

		// Check all operations if they are (transitively) required by op.
		// We make use of the fact here that an operation can only require
		// operations that are before it in time.
		List<AbstractOperation> required = new ArrayList<AbstractOperation>();
		required.add(op);

		for (int i = ops.indexOf(op) - 1; i >= 0; i--) {
			AbstractOperation current = ops.get(i);
			// else check if it is required by any of the already required ops

			for (AbstractOperation req : required) {
				if (conflictDetectionStrategy.isRequired(current, req)) {
					required.add(0, current);
					break;
				}
			}
		}
		required.remove(op);

		// return the sub list of operations required by op
		return required;
	}

	/**
	 * Retrieve all operations in ops that require op.
	 * 
	 * @param ops A time ordered (ascending) list of operations containing op.
	 * @param op The operation for which the dependants should be determined.
	 * @return A list of operations that require op which is a subset of ops.
	 */
	public List<AbstractOperation> getRequiring(List<AbstractOperation> ops, AbstractOperation op) {
		// sanity check
		if (!ops.contains(op)) {
			throw new IllegalArgumentException("the ops list dos not contain op");
		}

		// Check all operations if they (transitively) require op.
		// We make use of the fact here that an operation can only
		// possibly require an operation that is before it in time.
		int opIdx = ops.indexOf(op);
		List<AbstractOperation> requiring = new ArrayList<AbstractOperation>();
		requiring.add(op);

		for (AbstractOperation current : ops) {
			// if the current op is before op, it can not require it
			if (ops.indexOf(current) <= opIdx) {
				continue;
			}
			// else check if it requires any of the already requiring ops
			for (AbstractOperation req : requiring) {
				if (conflictDetectionStrategy.isRequired(req, current)) {
					requiring.add(current);
					break;
				}
			}
		}
		requiring.remove(op);
		// return the sub list of operations requiring op
		return requiring;
	}

}
