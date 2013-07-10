/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.internal.common.model.ModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * Detects conflicts between operation.
 * 
 * @author koegel
 */
public class ConflictDetector {

	/**
	 * Constructor.
	 */
	public ConflictDetector() {
	}

	public ChangeConflictSet calculateConflicts(List<ChangePackage> myChangePackages,
		List<ChangePackage> theirChangePackages, Project project) {
		ModelElementIdToEObjectMappingImpl idToEObjectMappingImpl = new ModelElementIdToEObjectMappingImpl(project,
			myChangePackages);
		idToEObjectMappingImpl.put(theirChangePackages);
		return calculateConflicts(myChangePackages, theirChangePackages, idToEObjectMappingImpl);
	}

	public ChangeConflictSet calculateConflicts(List<ChangePackage> myChangePackages,
		List<ChangePackage> theirChangePackages, ModelElementIdToEObjectMapping idToEObjectMapping) {
		Set<ConflictBucketCandidate> conflictCandidateBuckets = calculateConflictCandidateBuckets(myChangePackages,
			theirChangePackages, idToEObjectMapping);
		Set<AbstractOperation> notInvolvedInConflict = new LinkedHashSet<AbstractOperation>();
		Set<ConflictBucket> conflictBuckets = calculateConflictBucketsFromConflictCandidateBuckets(
			conflictCandidateBuckets, notInvolvedInConflict);
		return new ChangeConflictSet(conflictBuckets, notInvolvedInConflict, idToEObjectMapping, myChangePackages,
			theirChangePackages);
	}

	/**
	 * Calculate a set of conflict candidate buckets from a list of my and their change packages.
	 * 
	 * @param myChangePackages their operations in a list of change packages
	 * @param theirChangePackages their operations in a list of change packages
	 * @return a set of buckets with potentially conflicting operations
	 */
	private Set<ConflictBucketCandidate> calculateConflictCandidateBuckets(List<ChangePackage> myChangePackages,
		List<ChangePackage> theirChangePackages, ModelElementIdToEObjectMapping idToEObjectMapping) {

		List<AbstractOperation> myOperations = flattenChangepackages(myChangePackages);
		List<AbstractOperation> theirOperations = flattenChangepackages(theirChangePackages);

		return calculateConflictCandidateBuckets(idToEObjectMapping, myOperations, theirOperations);
	}

	private Set<ConflictBucketCandidate> calculateConflictCandidateBuckets(Project project,
		List<AbstractOperation> myOperations, List<AbstractOperation> theirOperations) {
		ModelElementIdToEObjectMappingImpl idToEObjectMappingImpl = new ModelElementIdToEObjectMappingImpl(project,
			myOperations, theirOperations);
		return calculateConflictCandidateBuckets(idToEObjectMappingImpl, myOperations, theirOperations);

	}

	private Set<ConflictBucketCandidate> calculateConflictCandidateBuckets(
		ModelElementIdToEObjectMapping idToEObjectMapping,
		List<AbstractOperation> myOperations, List<AbstractOperation> theirOperations) {

		ReservationToConflictBucketCandidateMap conflictMap = new ReservationToConflictBucketCandidateMap();

		int counter = 0;
		for (AbstractOperation myOperation : myOperations) {
			conflictMap.scanOperationReservations(myOperation, counter, idToEObjectMapping, true);
			counter++;
		}

		for (AbstractOperation theirOperation : theirOperations) {
			conflictMap.scanOperationReservations(theirOperation, counter, idToEObjectMapping, false);
			counter++;
		}
		return conflictMap.getConflictBucketCandidates();
	}

	private List<AbstractOperation> flattenChangepackages(List<ChangePackage> cps) {
		List<AbstractOperation> operations = new ArrayList<AbstractOperation>();
		for (ChangePackage cp : cps) {
			operations.addAll(cp.getOperations());
		}
		return operations;
	}

	/**
	 * Calculate a set of conflict buckets from an existing set of conflict candidate buckets. the resulting set may be
	 * empty.
	 * 
	 * @param conflictBucketsCandidateSet a set of conflict candidate buckets
	 * @param notInvolvedInConflict all my operations that are not involved in a conflict are collected in this
	 *            transient parameter set
	 * @return a set of conflict buckets
	 */
	private Set<ConflictBucket> calculateConflictBucketsFromConflictCandidateBuckets(
		Set<ConflictBucketCandidate> conflictBucketsCandidateSet, Set<AbstractOperation> notInvolvedInConflict) {

		Set<ConflictBucket> conflictBucketsSet = new LinkedHashSet<ConflictBucket>();
		for (ConflictBucketCandidate conflictBucketCandidate : conflictBucketsCandidateSet) {
			Set<ConflictBucket> buckets = conflictBucketCandidate.calculateConflictBuckets(this, notInvolvedInConflict);
			for (ConflictBucket bucket : buckets) {
				conflictBucketsSet.add(bucket);
			}
		}
		return conflictBucketsSet;
	}

	public Set<AbstractOperation> getConflicting(List<AbstractOperation> ops1, List<AbstractOperation> ops2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<AbstractOperation> getConflictingIndexIntegrity(List<AbstractOperation> ops1,
		List<AbstractOperation> ops2) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AbstractOperation> getRequired(List<AbstractOperation> ops, AbstractOperation addActor) {
		// TODO Auto-generated method stub
		return null;
	}
}