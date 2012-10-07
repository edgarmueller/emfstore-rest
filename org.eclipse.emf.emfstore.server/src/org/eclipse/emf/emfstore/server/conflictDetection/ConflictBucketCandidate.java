package org.eclipse.emf.emfstore.server.conflictDetection;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

public class ConflictBucketCandidate {

	private static final int MAX_BUCKET_SIZE = 1000;
	private Set<String> involvedIds;
	private Set<AbstractOperation> myOperations;
	private Set<AbstractOperation> theirOperations;
	private Map<AbstractOperation, Integer> operationToPriorityMap;

	public ConflictBucketCandidate() {
		involvedIds = new LinkedHashSet<String>();
		myOperations = new LinkedHashSet<AbstractOperation>();
		theirOperations = new LinkedHashSet<AbstractOperation>();
		operationToPriorityMap = new LinkedHashMap<AbstractOperation, Integer>();
	}

	public void addOperation(AbstractOperation operation, String modelElementId, boolean isMyOperation, int priority) {
		if (operation == null) {
			return;
		}
		involvedIds.add(modelElementId);
		operationToPriorityMap.put(operation, priority);
		if (isMyOperation) {
			myOperations.add(operation);
		} else {
			theirOperations.add(operation);
		}
	}

	public void addConflictBucketCandidate(ConflictBucketCandidate otherBucket) {
		if (otherBucket == null) {
			return;
		}

		myOperations.addAll(otherBucket.getMyOperations());
		theirOperations.addAll(otherBucket.getTheirOperations());
		involvedIds.addAll(otherBucket.getInvolvedIds());
		operationToPriorityMap.putAll(otherBucket.operationToPriorityMap);
	}

	public void addModelElementId(String modelElementId) {
		involvedIds.add(modelElementId);
	}

	public int size() {
		return theirOperations.size() + myOperations.size();
	}

	public boolean isConflicting() {
		return theirOperations.size() > 0 && myOperations.size() > 0;
	}

	public Set<String> getInvolvedIds() {
		return involvedIds;
	}

	public Set<AbstractOperation> getMyOperations() {
		return myOperations;
	}

	public Set<AbstractOperation> getTheirOperations() {
		return theirOperations;
	}

	public Set<ConflictBucket> calculateConflictBuckets(ConflictDetector detector,
		Set<AbstractOperation> myOperationsNonConflictingOperations) {
		Set<ConflictBucket> conflictBucketsSet = new LinkedHashSet<ConflictBucket>();

		// if bucket is too large, it will not be checked manually
		if (getInvolvedIds().size() * getMyOperations().size() * getTheirOperations().size() > MAX_BUCKET_SIZE) {
			ConflictBucket newConflictBucket = new ConflictBucket(getMyOperations(), getTheirOperations());
			newConflictBucket.setMyOperation(getMyOperations().iterator().next());
			newConflictBucket.setTheirOperation(getTheirOperations().iterator().next());
			conflictBucketsSet.add(newConflictBucket);
			return conflictBucketsSet;
		}

		Map<AbstractOperation, ConflictBucket> operationToConflictBucketMap = new LinkedHashMap<AbstractOperation, ConflictBucket>();

		for (AbstractOperation myOperation : getMyOperations()) {

			boolean involved = false;

			for (AbstractOperation theirOperation : getTheirOperations()) {

				if (detector.doConflict(myOperation, theirOperation)) {
					involved = true;
					ConflictBucket myConflictBucket = operationToConflictBucketMap.get(myOperation);
					ConflictBucket theirConflictBucket = operationToConflictBucketMap.get(theirOperation);

					if (myConflictBucket == null && theirConflictBucket == null) {
						ConflictBucket newConflictBucket = new ConflictBucket(myOperation, theirOperation);
						operationToConflictBucketMap.put(myOperation, newConflictBucket);
						operationToConflictBucketMap.put(theirOperation, newConflictBucket);
						conflictBucketsSet.add(newConflictBucket);
					} else if (myConflictBucket != null && theirConflictBucket == null) {
						myConflictBucket.getTheirOperationsSet().add(theirOperation);
						operationToConflictBucketMap.put(theirOperation, myConflictBucket);
					} else if (myConflictBucket == null && theirConflictBucket != null) {
						theirConflictBucket.getMyOperationsSet().add(myOperation);
						operationToConflictBucketMap.put(myOperation, theirConflictBucket);
					} else {
						myConflictBucket.getMyOperationsSet().addAll(theirConflictBucket.getMyOperationsSet());
						for (AbstractOperation op : theirConflictBucket.getMyOperationsSet()) {
							operationToConflictBucketMap.put(op, myConflictBucket);
						}
						myConflictBucket.getTheirOperationsSet().addAll(theirConflictBucket.getTheirOperationsSet());
						for (AbstractOperation op : theirConflictBucket.getTheirOperationsSet()) {
							operationToConflictBucketMap.put(op, myConflictBucket);
						}

						conflictBucketsSet.remove(theirConflictBucket);
					}
				}
			}
			if (!involved) {
				// only not involved my operations have to be recorded
				myOperationsNonConflictingOperations.add(myOperation);
			}
		}
		return selectMyandTheirOperation(conflictBucketsSet);
	}

	private Set<ConflictBucket> selectMyandTheirOperation(Set<ConflictBucket> conflictBucketsSet) {

		for (ConflictBucket conflictBucket : conflictBucketsSet) {
			Integer maxPriority = -1;
			AbstractOperation maxOperation = null;
			for (AbstractOperation myOperation : conflictBucket.getMyOperationsSet()) {
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
			for (AbstractOperation theirOperation : conflictBucket.getTheirOperationsSet()) {
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
