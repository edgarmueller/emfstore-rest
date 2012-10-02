package org.eclipse.emf.emfstore.client.model.changeTracking.merging;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

public class ConflictBucket {

	private Set<AbstractOperation> myOperations;
	private Set<AbstractOperation> theirOperations;
	private AbstractOperation myOperation;
	private AbstractOperation theirOperation;

	public ConflictBucket(AbstractOperation myOperation, AbstractOperation theirOperation) {
		myOperations = new LinkedHashSet<AbstractOperation>();
		myOperations.add(myOperation);
		theirOperations = new LinkedHashSet<AbstractOperation>();
		theirOperations.add(theirOperation);
	}

	public ConflictBucket(Set<AbstractOperation> myOperations, Set<AbstractOperation> theirOperations) {
		this.myOperations = myOperations;
		this.theirOperations = theirOperations;
	}

	public int size() {
		return theirOperations.size() + myOperations.size();
	}

	public Set<AbstractOperation> getMyOperationsSet() {
		return myOperations;
	}

	public Set<AbstractOperation> getTheirOperationsSet() {
		return theirOperations;
	}

	public List<AbstractOperation> getMyOperations() {
		return new ArrayList<AbstractOperation>(myOperations);
	}

	public List<AbstractOperation> getTheirOperations() {
		return new ArrayList<AbstractOperation>(theirOperations);
	}

	public AbstractOperation getMyOperation() {
		return myOperation;
	}

	public void setMyOperation(AbstractOperation myOperation) {
		this.myOperation = myOperation;
	}

	public AbstractOperation getTheirOperation() {
		return theirOperation;
	}

	public void setTheirOperation(AbstractOperation theirOperation) {
		this.theirOperation = theirOperation;
	}

}
