/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.internal.conflictDetection;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucketCandidate;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.ContainmentType;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.FeatureOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceMoveOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceSetOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.ReferenceOperation;

/**
 * Map from modelelements and their features to conflict candidate buckets.
 * 
 * @author mkoegel
 * 
 */
public class ReservationToConflictBucketCandidateMap {

	// // map from concatination of modelelement ID and feature name to conflict bucket candidate
	// private Map<String, ConflictBucketCandidate> modelElementIdAndFeatureToConflictMap;
	//
	// // map from modelelement ID to conflict bucket candidate
	// private Map<String, ConflictBucketCandidate> modelElementsWithAllFeaturesToConflictMap;
	//
	// // Mapping from modelElementId to a set of feature names
	// private ReservationSet modelElementIdToFeatureSetMapping;
	//
	// // map from model elementid to buckets requiring its existence
	// private LinkedHashMap<String, Set<ConflictBucketCandidate>> modelElementExistenceToConflictBucketSetMap;
	private ReservationSet reservationToConflictMap;

	/**
	 * Default constructor.
	 */
	public ReservationToConflictBucketCandidateMap() {
		reservationToConflictMap = new ReservationSet();
	}

	/**
	 * Retrieve currently assigned candidate buckets for the requested model element and feature. The magic feature
	 * names {@link ReservationSet#EXISTENCE_FEATURE_NAME} and {@link ReservationSet#ALL_FEATURES_NAME} are also valid
	 * inputs.
	 * If get is called with {@link ReservationSet#ALL_FEATURES_NAME} it will collect all buckets
	 * that block any feature of the given model element.
	 * If get is called with {@link ReservationSet#EXISTENCE_FEATURE_NAME}, it will only collect
	 * buckets
	 * which requested {@link ReservationSet#ALL_FEATURES_NAME}.
	 * 
	 * @param modelElementId the model element id
	 * @param featureName the feature name including magic names as stated above
	 * @return a set of buckets that conflict with the given model element and feature, the set may be empty
	 */
	// public Set<ConflictBucketCandidate> get(String modelElementId, String featureName) {
	// ConflictBucketCandidate conflictBucketCandidate = modelElementsWithAllFeaturesToConflictMap.get(modelElementId);
	// if (conflictBucketCandidate != null) {
	// return Collections.singleton(conflictBucketCandidate);
	// }
	// if (featureName == ReservationSet.ALL_FEATURES_NAME) {
	// Set<String> featureSet = modelElementIdToFeatureSetMapping.get(modelElementId);
	// Set<ConflictBucketCandidate> buckets = new LinkedHashSet<ConflictBucketCandidate>();
	// // get all buckets that already blocked features for this model element
	// for (String feature : featureSet) {
	// ConflictBucketCandidate subBucket = modelElementIdAndFeatureToConflictMap.get(modelElementId + feature);
	// if (subBucket != null) {
	// buckets.add(subBucket);
	// }
	// }
	// // get all buckets that require existence of this element
	// Set<ConflictBucketCandidate> existenceRequiringSet = modelElementExistenceToConflictBucketSetMap
	// .get(modelElementId);
	// if (existenceRequiringSet != null) {
	// buckets.addAll(existenceRequiringSet);
	// }
	// return buckets;
	// }
	// // normal feature collisions including existence
	// ConflictBucketCandidate bucketCandidate = modelElementIdAndFeatureToConflictMap.get(modelElementId
	// + featureName);
	// // if we find a conflicting bucket for the given feature and it is not just an Existence feature
	// if (bucketCandidate != null && featureName != ReservationSet.EXISTENCE_FEATURE_NAME) {
	// return Collections.singleton(bucketCandidate);
	// }
	// // in any other case return not found buckets
	// return Collections.emptySet();
	// }

	private void joinReservationSet(ReservationSet reservationSet,
		ConflictBucketCandidate currentConflictBucketCandidate) {
		Set<String> modelElements = reservationSet.getAllModelElements();
		// iterate incoming reservation set
		for (String modelElement : modelElements) {
			// handle full reservations
			if (reservationSet.hasFullReservation(modelElement)
				|| reservationToConflictMap.hasFullReservation(modelElement)) {
				ConflictBucketCandidate mergedConflictBucketCandidates = mergeConflictBucketCandidates(
					reservationToConflictMap.getConflictBucketCandidates(modelElement), currentConflictBucketCandidate);
				reservationToConflictMap.addFullReservation(modelElement, mergedConflictBucketCandidates);
				continue;
			}

			// at this point: neither full reservation of model element is existing nor requested to be joined

			// handle existence reservations
			if (reservationSet.hasExistenceReservation(modelElement)) {
				reservationToConflictMap.addExistenceReservation(modelElement, currentConflictBucketCandidate);
				continue;
			}

			// handle feature reservations
			Set<String> featureNames = reservationSet.getFeatureNames(modelElement);
			for (String featureName : featureNames) {
				// we do not care about existence reservations since they will not colide with any other features

				// handle normal feature reservations without opposite
				if (!reservationSet.hasOppositeReservations(modelElement, featureName)) {
					handleFeatureVsFeatureReservation(currentConflictBucketCandidate, modelElement, featureName);
				} else {
					handleOppositeVsOppositeReservation(reservationSet, currentConflictBucketCandidate, modelElement,
						featureName);
				}

			}
		}
	}

	private void handleFeatureVsFeatureReservation(ConflictBucketCandidate currentConflictBucketCandidate,
		String modelElement, String featureName) {

		if (reservationToConflictMap.hasFeatureReservation(modelElement, featureName)) {

			Set<ConflictBucketCandidate> existingBuckets = reservationToConflictMap.getConflictBucketCandidates(
				modelElement, featureName);
			ConflictBucketCandidate mergedConflictBucketCandidates = mergeConflictBucketCandidates(existingBuckets,
				currentConflictBucketCandidate);

			reservationToConflictMap.addFeatureReservation(modelElement, featureName, mergedConflictBucketCandidates);

		} else if (reservationToConflictMap.hasOppositeReservations(modelElement, featureName)) {
			throw new IllegalStateException("Reservation for same feature with and without opposites is illegal!");
		} else {
			reservationToConflictMap.addFeatureReservation(modelElement, featureName, currentConflictBucketCandidate);
		}
	}

	private void handleOppositeVsOppositeReservation(ReservationSet reservationSet,
		ConflictBucketCandidate currentConflictBucketCandidate, String modelElement, String featureName) {
		if (reservationToConflictMap.hasOppositeReservations(modelElement, featureName)) {
			Set<String> opposites = reservationSet.getOpposites(modelElement, featureName);
			for (String oppositeModelElement : opposites) {
				if (reservationToConflictMap.hasOppositeReservation(modelElement, featureName, oppositeModelElement)) {

					ConflictBucketCandidate mergedConflictBucketCandidates = mergeConflictBucketCandidates(
						reservationToConflictMap.getConflictBucketCandidates(modelElement, featureName,
							oppositeModelElement), currentConflictBucketCandidate);

					reservationToConflictMap.addMultiReferenceWithOppositeReservation(modelElement, featureName,
						oppositeModelElement, mergedConflictBucketCandidates);
				}
			}
		} else if (reservationToConflictMap.hasFeatureReservation(modelElement, featureName)) {
			throw new IllegalStateException("Reservation for same feature with and without opposites is illegal!");
		} else {
			Set<String> opposites = reservationSet.getOpposites(modelElement, featureName);

			for (String oppositeModelElement : opposites) {
				reservationToConflictMap.addMultiReferenceWithOppositeReservation(modelElement, featureName,
					oppositeModelElement, currentConflictBucketCandidate);
			}
		}

	}

	private ConflictBucketCandidate mergeConflictBucketCandidates(Set<ConflictBucketCandidate> existingBuckets,
		ConflictBucketCandidate currentBucket) {
		for (ConflictBucketCandidate otherBucket : existingBuckets) {
			currentBucket.addConflictBucketCandidate(otherBucket);
		}
		for (ConflictBucketCandidate candidate : existingBuckets) {
			candidate.merged = true;
		}
		return currentBucket;
	}

	// /**
	// * Request to map and thereby reserve the given model element and feature for the given bucket.
	// *
	// * @param modelElementId the model element id
	// * @param featureName the feature name
	// * @param currentOperationsConflictBucket the bucket
	// * @param replace whether existing entries should be overwritten, if this is not enabled and existing entries are
	// * found an {@link IllegalStateException} will be thrown
	// */
	public void scanOperationReservations(AbstractOperation operation, int priority, boolean isMyOperation) {

		ReservationSet reservationSet = extractReservationFromOperation(operation, new ReservationSet());
		ConflictBucketCandidate conflictBucketCandidate = new ConflictBucketCandidate();
		conflictBucketCandidate.addOperation(operation, isMyOperation, priority);
		joinReservationSet(reservationSet, conflictBucketCandidate);
	}

	private ReservationSet extractReservationFromOperation(AbstractOperation operation, ReservationSet reservationSet) {
		if (operation instanceof CompositeOperation) {
			CompositeOperation compositeOperation = (CompositeOperation) operation;
			for (AbstractOperation subOperation : compositeOperation.getSubOperations()) {
				extractReservationFromOperation(subOperation, reservationSet);
			}
			return reservationSet;
		} else if (operation instanceof CreateDeleteOperation) {
			// handle containment tree
			CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;
			for (ModelElementId createDeletedElement : createDeleteOperation.getEObjectToIdMap().values()) {
				reservationSet.addFullReservation(createDeletedElement.getId());
			}
			// handle suboperations
			for (AbstractOperation subOperation : createDeleteOperation.getSubOperations()) {
				extractReservationFromOperation(subOperation, reservationSet);
			}
			return reservationSet;
		} else if (operation instanceof FeatureOperation) {
			handleFeatureOperation(operation, reservationSet);
			return reservationSet;
		}
		throw new IllegalStateException("Unkown operation type: " + operation.getClass().getCanonicalName());
	}

	//
	//
	// 1. extractReservationFromOperation
	// 2. create bucket for op and add to reservationSet
	// 3. join set with existing map
	//
	// ConflictBucketCandidate conflictBucketCandidate = modelElementsWithAllFeaturesToConflictMap.get(modelElementId);
	// if (conflictBucketCandidate != null && currentOperationsConflictBucket != conflictBucketCandidate && !replace) {
	// throw new IllegalStateException("Bucket is already taken!");
	// }
	//
	// if (featureName == ReservationSet.ALL_FEATURES_NAME) {
	// modelElementsWithAllFeaturesToConflictMap.put(modelElementId, currentOperationsConflictBucket);
	// return;
	// }
	// // feature is a normal feature
	// if (replace && conflictBucketCandidate != null) {
	// return;
	// }
	// conflictBucketCandidate = modelElementIdAndFeatureToConflictMap.get(modelElementId + featureName);
	// if (conflictBucketCandidate != null && currentOperationsConflictBucket != conflictBucketCandidate && !replace
	// && featureName != ReservationSet.EXISTENCE_FEATURE_NAME) {
	// throw new IllegalStateException("Bucket is already taken!");
	// }
	// if (featureName == ReservationSet.EXISTENCE_FEATURE_NAME) {
	// Set<ConflictBucketCandidate> existingSet = modelElementExistenceToConflictBucketSetMap.get(modelElementId);
	// if (existingSet == null) {
	// existingSet = new LinkedHashSet<ConflictBucketCandidate>();
	// modelElementExistenceToConflictBucketSetMap.put(modelElementId, existingSet);
	// }
	// existingSet.add(currentOperationsConflictBucket);
	// }
	// modelElementIdAndFeatureToConflictMap.put(modelElementId + featureName, currentOperationsConflictBucket);
	// modelElementIdToFeatureSetMapping.add(modelElementId, featureName);
	//
	// }

	private void handleFeatureOperation(AbstractOperation operation, ReservationSet reservationSet) {
		FeatureOperation featureOperation = (FeatureOperation) operation;
		String modelElementId = featureOperation.getModelElementId().getId();
		String featureName = featureOperation.getFeatureName();
		if (featureOperation instanceof ReferenceOperation) {
			ReferenceOperation referenceOperation = (ReferenceOperation) featureOperation;
			// if (isRemovingReferencesOnly(referenceOperation)) {
			// return;
			// }
			for (ModelElementId otherModelElement : referenceOperation.getOtherInvolvedModelElements()) {
				if (referenceOperation.getContainmentType().equals(ContainmentType.CONTAINMENT)
					&& !referenceOperation.isBidirectional()) {
					reservationSet.addContainerReservation(otherModelElement.getId());
				} else {
					reservationSet.addExistenceReservation(otherModelElement.getId());
				}
			}
		}
		if ((featureOperation instanceof MultiReferenceOperation)
			|| (featureOperation instanceof MultiReferenceSetOperation || featureOperation instanceof MultiReferenceMoveOperation)) {
			for (ModelElementId otherModelElement : featureOperation.getOtherInvolvedModelElements()) {
				reservationSet.addMultiReferenceWithOppositeReservation(modelElementId, featureName,
					otherModelElement.getId());
			}
		} else {
			reservationSet.addFeatureReservation(modelElementId, featureName);
		}
		return;

	}

	public Set<ConflictBucketCandidate> getConflictBucketCandidates() {
		Set<ConflictBucketCandidate> result = new LinkedHashSet<ConflictBucketCandidate>();
		for (String modelElement : reservationToConflictMap.getAllModelElements()) {
			Set<ConflictBucketCandidate> conflictBucketCandidates = reservationToConflictMap
				.getConflictBucketCandidates(modelElement);
			for (ConflictBucketCandidate candidate : conflictBucketCandidates) {
				if (candidate.merged) {
					continue;
				} else {
					result.add(candidate);
				}
			}
		}
		return result;
	}
}