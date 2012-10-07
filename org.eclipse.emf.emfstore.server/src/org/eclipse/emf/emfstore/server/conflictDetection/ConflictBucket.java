/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.conflictDetection;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

/**
 * Represents a bucket of conflicting operations sets. In this context my operations are operations authored/owned by
 * the current user while their operation are incomming operations from another user.
 * 
 * @author koegel
 * 
 */
public class ConflictBucket {

	private Set<AbstractOperation> myOperations;
	private Set<AbstractOperation> theirOperations;
	private AbstractOperation myOperation;
	private AbstractOperation theirOperation;

	/**
	 * Constructor.
	 * 
	 * @param myOperation initial my operation
	 * @param theirOperation initial their operation
	 */
	public ConflictBucket(AbstractOperation myOperation, AbstractOperation theirOperation) {
		myOperations = new LinkedHashSet<AbstractOperation>();
		myOperations.add(myOperation);
		theirOperations = new LinkedHashSet<AbstractOperation>();
		theirOperations.add(theirOperation);
	}

	/**
	 * Constructor.
	 * 
	 * @param myOperations initial set of my operations
	 * @param theirOperations initial set of their operations
	 */
	public ConflictBucket(Set<AbstractOperation> myOperations, Set<AbstractOperation> theirOperations) {
		this.myOperations = myOperations;
		this.theirOperations = theirOperations;
	}

	/**
	 * 
	 * @return
	 */
	public int size() {
		return theirOperations.size() + myOperations.size();
	}

	public Set<AbstractOperation> getMyOperationsSet() {
		return myOperations;
	}

	public Set<AbstractOperation> getTheirOperationsSet() {
		return theirOperations;
	}

	public Set<AbstractOperation> getMyOperations() {
		return myOperations;
	}

	public Set<AbstractOperation> getTheirOperations() {
		return theirOperations;
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
