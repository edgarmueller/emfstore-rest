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

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.common.ExtensionRegistry;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.ContainmentType;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.FeatureOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceMoveOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceSetOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.ReferenceOperation;

/**
 * Map from modelelements and their features to conflict candidate buckets.
 * 
 * @author mkoegel
 * 
 */
public class ReservationToConflictBucketCandidateMap {

	private static ReservationSetModifier reservationSetModifier = initCustomReservationModifier();
	private final ReservationSet reservationToConflictMap;
	private final Set<ConflictBucketCandidate> conflictBucketCandidates;
	private final Map<String, Map.Entry<?, ?>> createdMapEntries;

	private static ReservationSetModifier initCustomReservationModifier() {

		return ExtensionRegistry.INSTANCE.get(
			ReservationSetModifier.ID,
			ReservationSetModifier.class,
			new ReservationSetModifier() {
				public ReservationSet addCustomReservation(
					AbstractOperation operation, ReservationSet reservationSet,
					ModelElementIdToEObjectMapping mapping) {
					return reservationSet;
				}
			},
			true);
	}

	/**
	 * Default constructor.
	 */
	public ReservationToConflictBucketCandidateMap() {
		reservationToConflictMap = new ReservationSet();
		conflictBucketCandidates = new LinkedHashSet<ConflictBucketCandidate>();
		createdMapEntries = new LinkedHashMap<String, Map.Entry<?, ?>>();

	}

	private void joinReservationSet(ReservationSet reservationSet,
		ConflictBucketCandidate currentConflictBucketCandidate) {
		final Set<String> modelElements = reservationSet.getAllModelElements();
		// iterate incoming reservation set
		for (final String modelElement : modelElements) {
			// handle full reservations
			if (reservationSet.hasFullReservation(modelElement)
				|| reservationToConflictMap.hasFullReservation(modelElement)) {
				final ConflictBucketCandidate mergedConflictBucketCandidates = mergeConflictBucketCandidates(
					reservationToConflictMap
						.getConflictBucketCandidates(modelElement),
					currentConflictBucketCandidate);
				reservationToConflictMap.addFullReservation(modelElement, mergedConflictBucketCandidates);
				continue;
			}

			// at this point: neither full reservation of model element is existing nor requested to be joined

			// handle existence reservations
			if (reservationSet.hasExistenceReservation(modelElement)) {
				reservationToConflictMap.addExistenceReservation(modelElement, currentConflictBucketCandidate);
			}

			// handle feature reservations
			final Set<String> featureNames = reservationSet.getFeatureNames(modelElement);

			for (final String featureName : featureNames) {

				if (featureName.equals(FeatureNameReservationMap.EXISTENCE_FEATURE)) {
					continue;
				}
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

			final Set<ConflictBucketCandidate> existingBuckets = reservationToConflictMap
				.getConflictBucketCandidates(
					modelElement, featureName);
			final ConflictBucketCandidate mergedConflictBucketCandidates = mergeConflictBucketCandidates(
				existingBuckets,
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
			final Set<String> opposites = reservationSet.getOpposites(modelElement, featureName);
			for (final String oppositeModelElement : opposites) {
				if (reservationToConflictMap.hasOppositeReservation(modelElement, featureName, oppositeModelElement)) {

					final ConflictBucketCandidate mergedConflictBucketCandidates = mergeConflictBucketCandidates(
						reservationToConflictMap
							.getConflictBucketCandidates(
								modelElement,
								featureName,
								oppositeModelElement),
						currentConflictBucketCandidate);

					reservationToConflictMap.addMultiReferenceWithOppositeReservation(
						modelElement,
						featureName,
						oppositeModelElement,
						mergedConflictBucketCandidates);
				}
			}
		} else if (reservationToConflictMap.hasFeatureReservation(modelElement, featureName)) {
			throw new IllegalStateException("Reservation for same feature with and without opposites is illegal!");
		} else {
			final Set<String> opposites = reservationSet.getOpposites(modelElement, featureName);

			for (final String oppositeModelElement : opposites) {
				reservationToConflictMap.addMultiReferenceWithOppositeReservation(
					modelElement,
					featureName,
					oppositeModelElement,
					currentConflictBucketCandidate);
			}
		}

	}

	private ConflictBucketCandidate mergeConflictBucketCandidates(Set<ConflictBucketCandidate> existingBuckets,
		ConflictBucketCandidate currentBucket) {
		final ConflictBucketCandidate rootBucket = currentBucket.getRootConflictBucketCandidate();
		for (final ConflictBucketCandidate otherBucket : existingBuckets) {
			otherBucket.getRootConflictBucketCandidate().setParentConflictBucketCandidate(rootBucket);
		}
		return rootBucket;
	}

	/**
	 * Scans the given {@link AbstractOperation} into the reservation set.
	 * 
	 * @param operation
	 *            the operation to be scanned
	 * @param priority
	 *            the global priority of the operation, which is later used to represent a conflict bucket candidate
	 * @param idToEObjectMapping
	 *            a mapping from IDs to model elements containing all involved model elements
	 * @param isMyOperation
	 *            whether this operation is incoming, {@code false} if it is, {@code true} otherwise
	 */
	public void scanOperationReservations(AbstractOperation operation, int priority,
		ModelElementIdToEObjectMapping idToEObjectMapping, boolean isMyOperation) {

		ReservationSet reservationSet = extractReservationFromOperation(operation, new ReservationSet());
		reservationSet = addCustomReservations(operation, reservationSet, idToEObjectMapping);
		final ConflictBucketCandidate conflictBucketCandidate = new ConflictBucketCandidate();
		conflictBucketCandidates.add(conflictBucketCandidate);
		conflictBucketCandidate.addOperation(operation, isMyOperation, priority);
		joinReservationSet(reservationSet, conflictBucketCandidate);
	}

	private ReservationSet addCustomReservations(AbstractOperation operation, ReservationSet reservationSet,
		ModelElementIdToEObjectMapping idToEObjectMapping) {
		return reservationSetModifier.addCustomReservation(operation, reservationSet, idToEObjectMapping);
	}

	private ReservationSet extractReservationFromOperation(final AbstractOperation operation,
		ReservationSet reservationSet) {

		if (operation instanceof CompositeOperation) {
			final CompositeOperation compositeOperation = (CompositeOperation) operation;
			for (final AbstractOperation subOperation : compositeOperation.getSubOperations()) {
				extractReservationFromOperation(subOperation, reservationSet);
			}
			return reservationSet;
		} else if (operation instanceof CreateDeleteOperation) {

			// add full reservations only for delete operations and their deleted elements
			final CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;
			if (createDeleteOperation.isDelete()) {
				// handle containment tree
				for (final ModelElementId modelElementId : createDeleteOperation.getEObjectToIdMap().values()) {
					reservationSet.addFullReservation(modelElementId.getId());
				}
			} else {
				// check for map entries
				for (final EObject eObject : createDeleteOperation.getEObjectToIdMap().keySet()) {
					if (eObject instanceof Map.Entry<?, ?>) {
						final String id = createDeleteOperation.getEObjectToIdMap().get(eObject).getId();
						final Map.Entry<?, ?> mapEntry = (Entry<?, ?>) eObject;
						createdMapEntries.put(id, mapEntry);
					}
				}
			}

			// handle suboperations
			for (final AbstractOperation subOperation : createDeleteOperation.getSubOperations()) {
				extractReservationFromOperation(subOperation, reservationSet);
			}
			return reservationSet;
		} else if (operation instanceof FeatureOperation) {
			handleFeatureOperation(operation, reservationSet);
			return reservationSet;
		}
		throw new IllegalStateException("Unkown operation type: " + operation.getClass().getCanonicalName());
	}

	private void handleFeatureOperation(AbstractOperation operation, ReservationSet reservationSet) {
		final FeatureOperation featureOperation = (FeatureOperation) operation;
		final String modelElementId = featureOperation.getModelElementId().getId();
		final String featureName = featureOperation.getFeatureName();
		if (featureOperation instanceof ReferenceOperation) {
			final ReferenceOperation referenceOperation = (ReferenceOperation) featureOperation;
			// if (isRemovingReferencesOnly(referenceOperation)) {
			// return;
			// }
			for (final ModelElementId otherModelElement : referenceOperation.getOtherInvolvedModelElements()) {
				if (referenceOperation.getContainmentType().equals(ContainmentType.CONTAINMENT)
					&& !referenceOperation.isBidirectional()) {
					reservationSet.addContainerReservation(otherModelElement.getId());
				} else {
					reservationSet.addExistenceReservation(otherModelElement.getId());
				}
			}
		}
		if (featureOperation instanceof MultiReferenceOperation
			|| featureOperation instanceof MultiReferenceSetOperation
			|| featureOperation instanceof MultiReferenceMoveOperation) {
			for (final ModelElementId otherModelElement : featureOperation.getOtherInvolvedModelElements()) {
				reservationSet.addMultiReferenceWithOppositeReservation(modelElementId, featureName,
					otherModelElement.getId());

				if (isCreatedMapEntry(otherModelElement)) {
					final Map.Entry<?, ?> mapEntry = getCreatedMapEntry(otherModelElement);
					// FIXME: currently we only can handle keys which have a valid string representation
					reservationSet.addMapKeyReservation(modelElementId, featureName,
						mapEntry.getKey().toString());
				}
			}
		} else {
			reservationSet.addFeatureReservation(modelElementId, featureName);
		}
		return;
	}

	private Map.Entry<?, ?> getCreatedMapEntry(ModelElementId otherModelElement) {
		return createdMapEntries.get(otherModelElement);
	}

	private boolean isCreatedMapEntry(ModelElementId modelElementId) {
		return createdMapEntries.containsKey(modelElementId.getId());
	}

	/**
	 * Get all Conflict Buckets that are part of the map.
	 * 
	 * @return a set of conflict bucket candidates
	 */
	public Set<ConflictBucketCandidate> getConflictBucketCandidates() {
		final Map<ConflictBucketCandidate, Set<ConflictBucketCandidate>> rootToBucketMergeSetMap = new LinkedHashMap<ConflictBucketCandidate, Set<ConflictBucketCandidate>>();
		for (final ConflictBucketCandidate candidate : conflictBucketCandidates) {
			final ConflictBucketCandidate root = candidate.getRootConflictBucketCandidate();
			Set<ConflictBucketCandidate> bucketMergeSet = rootToBucketMergeSetMap.get(root);
			if (bucketMergeSet == null) {
				bucketMergeSet = new LinkedHashSet<ConflictBucketCandidate>();
				rootToBucketMergeSetMap.put(root, bucketMergeSet);
			}
			bucketMergeSet.add(candidate);
		}
		for (final ConflictBucketCandidate root : rootToBucketMergeSetMap.keySet()) {
			for (final ConflictBucketCandidate sibling : rootToBucketMergeSetMap.get(root)) {
				root.addConflictBucketCandidate(sibling);
			}
		}
		return rootToBucketMergeSetMap.keySet();
	}

}