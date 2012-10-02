package org.eclipse.emf.emfstore.client.model.changeTracking.merging;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

public class ConflictBucketCandidate {

	private Set<String> involvedIds;
	private Set<AbstractOperation> myOperations;
	private Set<AbstractOperation> theirOperations;

	public ConflictBucketCandidate() {
		involvedIds = new LinkedHashSet<String>();
		myOperations = new LinkedHashSet<AbstractOperation>();
		theirOperations = new LinkedHashSet<AbstractOperation>();
	}

	public void addOperation(AbstractOperation operation, String modelElementId, boolean isMyOperation) {
		if (operation == null) {
			return;
		}
		involvedIds.add(modelElementId);

		if (isMyOperation) {
			myOperations.add(operation);
		} else {
			theirOperations.add(operation);
		}
	}

	public void addModelElementId(String modelElementId) {
		involvedIds.add(modelElementId);
	}

	public int size() {
		return theirOperations.size() + myOperations.size();
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

}
