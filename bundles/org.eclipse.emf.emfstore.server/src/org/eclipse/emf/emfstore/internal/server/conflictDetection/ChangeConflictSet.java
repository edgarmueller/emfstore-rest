/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.conflictDetection;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.impl.api.ESConflictSetImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.ESConflictSet;

/**
 * The actual implementation of an {@link ESConflictSetImpl} containing
 * the changes that caused the conflict.
 * 
 * @author wesendon
 * @author emueller
 */
public class ChangeConflictSet implements APIDelegate<ESConflictSet> {

	private final ModelElementIdToEObjectMapping idToEObjectMapping;
	private ESConflictSetImpl apiImpl;
	private Set<ConflictBucket> conflictBuckets;
	private Set<AbstractOperation> notInvolvedInConflict;
	private List<ChangePackage> leftChanges;
	private List<ChangePackage> rightChanges;

	/**
	 * Constructor.
	 * 
	 * 
	 * @param projectSpace
	 *            the ProjectSpace
	 * @param myChangePackages
	 *            my change package
	 * @param newPackages
	 *            the list of change packages that caused the exception
	 * @param conflictBucketCandidates
	 *            a set of conflict candidates
	 * @param idToEObjectMapping
	 *            a mapping from IDs to EObjects and vice versa.<br/>
	 *            Contains all IDs of model elements involved in the {@link ChangePackage}s
	 *            as well as those contained by the project in the {@link ProjectSpace}
	 */
	public ChangeConflictSet(Set<ConflictBucket> conflictBuckets, Set<AbstractOperation> notInvolvedInConflict,
		ModelElementIdToEObjectMapping idToEObjectMapping, List<ChangePackage> leftChanges,
		List<ChangePackage> rightChanges) {
		this.conflictBuckets = conflictBuckets;
		this.notInvolvedInConflict = notInvolvedInConflict;
		this.idToEObjectMapping = idToEObjectMapping;
		this.leftChanges = leftChanges;
		this.rightChanges = rightChanges;
	}

	/**
	 * Returns the mapping from IDs to EObjects and vice versa.<br/>
	 * The mapping contains all IDs of model elements involved in the {@link ChangePackage}s
	 * as well as those contained by the project in the {@link ProjectSpace}
	 * 
	 * @return the mapping from IDs to EObjects and vice versa
	 */
	public ModelElementIdToEObjectMapping getIdToEObjectMapping() {
		return idToEObjectMapping;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#toAPI()
	 */
	public ESConflictSet toAPI() {
		if (apiImpl == null) {
			apiImpl = createAPI();
		}
		return apiImpl;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#createAPI()
	 */
	public ESConflictSetImpl createAPI() {
		return new ESConflictSetImpl(this);
	}

	public Set<AbstractOperation> getNotInvolvedInConflict() {
		return notInvolvedInConflict;
	}

	public Set<ConflictBucket> getConflictBuckets() {
		return conflictBuckets;
	}

	public List<ChangePackage> getLeftChanges() {
		return leftChanges;
	}

	public List<ChangePackage> getRightChanges() {
		return rightChanges;
	}
}