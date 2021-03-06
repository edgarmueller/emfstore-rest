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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.internal.server.impl.api.ESConflictImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.ESConflict;

/**
 * Represents a bucket of conflicting operations sets. In this context my operations are operations authored/owned by
 * the current user while their operation are incomming operations from another user.
 * 
 * @author koegel
 * 
 */
/**
 * @author User
 * 
 */
public class ConflictBucket implements APIDelegate<ESConflict> {

	private Set<AbstractOperation> myOperations;
	private Set<AbstractOperation> theirOperations;
	private AbstractOperation myOperation;
	private AbstractOperation theirOperation;
	private ESConflict apiImpl;
	private Set<AbstractOperation> acceptedLocalOperations;
	private boolean isResolved;
	private Set<AbstractOperation> rejectedRemoteOperations;

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
	 * @return the set of my operations
	 */
	public Set<AbstractOperation> getMyOperations() {
		return myOperations;
	}

	/**
	 * @return the set of their operations
	 */
	public Set<AbstractOperation> getTheirOperations() {
		return theirOperations;
	}

	/**
	 * @return one of my operations representing all my operations
	 */
	public AbstractOperation getMyOperation() {
		return myOperation;
	}

	/**
	 * Set one of my operations representing all my operations.
	 * 
	 * @param myOperation the operation
	 */
	public void setMyOperation(AbstractOperation myOperation) {
		this.myOperation = myOperation;
	}

	/**
	 * @return one of their operations representing all their operations
	 */
	public AbstractOperation getTheirOperation() {
		return theirOperation;
	}

	/**
	 * Set one of their operations representing all their operations.
	 * * @param theirOperation the operation
	 */
	public void setTheirOperation(AbstractOperation theirOperation) {
		this.theirOperation = theirOperation;
	}

	public ESConflict toAPI() {
		if (apiImpl == null) {
			apiImpl = createAPI();
		}
		return apiImpl;
	}

	public ESConflict createAPI() {
		return new ESConflictImpl(this);
	}

	public void resolveConflict(Set<AbstractOperation> acceptedLocalOperations,
		Set<AbstractOperation> rejectedRemoteOperations) {
		this.acceptedLocalOperations = acceptedLocalOperations;
		this.rejectedRemoteOperations = rejectedRemoteOperations;
		this.isResolved = true;
	}

	public Set<AbstractOperation> getAcceptedLocalOperations() {
		return acceptedLocalOperations;
	}

	public Set<AbstractOperation> getRejectedRemoteOperations() {
		return rejectedRemoteOperations;
	}

	public boolean isResolved() {
		return isResolved;
	}
}