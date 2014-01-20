/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 * Edgar Mueller - custom reservation set modifiers
 * Edgar Mueller - Bug 418183: recognizing conflicts on map entries
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.conflictDetection;

import static org.eclipse.emf.emfstore.internal.server.model.versioning.operations.util.OperationUtil.isMultiMoveRef;
import static org.eclipse.emf.emfstore.internal.server.model.versioning.operations.util.OperationUtil.isMultiRef;
import static org.eclipse.emf.emfstore.internal.server.model.versioning.operations.util.OperationUtil.isMultiRefSet;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.common.ExtensionRegistry;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.ContainmentType;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.FeatureOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.ReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;

/**
 * Map from model elements and their features to conflict candidate buckets.
 * 
 * @author mkoegel
 * @author emueller
 */
public class ReservationToConflictBucketCandidateMap {

	private static final String KEY = "key"; //$NON-NLS-1$
	private static ReservationSetModifier reservationSetModifier = initCustomReservationSetModifier();
	private final ReservationSet reservationToConflictMap;
	private final Set<ConflictBucketCandidate> conflictBucketCandidates;
	private final Map<String, Integer> idToKeyHashCode;

	private static ReservationSetModifier initCustomReservationSetModifier() {

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
		idToKeyHashCode = new LinkedHashMap<String, Integer>();

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
				// we do not care about existence reservations since they will not collide with any other features

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
			throw new IllegalStateException(
				Messages.ReservationToConflictBucketCandidateMap_Illegal_Reservation_With_And_Without_Opposite);
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
			throw new IllegalStateException(
				Messages.ReservationToConflictBucketCandidateMap_Illegal_Reservation_With_And_Without_Opposite);
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

		ReservationSet reservationSet = extractReservationFromOperation(operation, new ReservationSet(),
			idToEObjectMapping);
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
		ReservationSet reservationSet, ModelElementIdToEObjectMapping idToEObjectMapping) {

		if (operation instanceof CompositeOperation) {
			final CompositeOperation compositeOperation = (CompositeOperation) operation;
			for (final AbstractOperation subOperation : compositeOperation.getSubOperations()) {
				extractReservationFromOperation(subOperation, reservationSet, idToEObjectMapping);
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
					if (!isMapEntry(eObject)) {
						continue;
					}
					handleMapEntry((Entry<?, ?>) eObject, createDeleteOperation, idToEObjectMapping);
				}
			}

			// handle suboperations
			for (final AbstractOperation subOperation : createDeleteOperation.getSubOperations()) {
				extractReservationFromOperation(subOperation, reservationSet, idToEObjectMapping);
			}
			return reservationSet;
		} else if (operation instanceof FeatureOperation) {
			handleFeatureOperation(operation, reservationSet, idToEObjectMapping);
			return reservationSet;
		}
		throw new IllegalStateException(Messages.ReservationToConflictBucketCandidateMap_Unknown_Operation
			+ operation.getClass().getCanonicalName());
	}

	private boolean isMapEntry(EObject eObject) {
		return eObject instanceof Map.Entry<?, ?>;
	}

	private void handleMapEntry(final Map.Entry<?, ?> mapEntry, CreateDeleteOperation createDeleteOperation,
		ModelElementIdToEObjectMapping idToEObjectMapping) {
		final String mapEntryId = createDeleteOperation.getEObjectToIdMap().get(mapEntry).getId();

		if (mapEntry.getKey() != null) {
			idToKeyHashCode.put(mapEntryId, new Integer(mapEntry.getKey().hashCode()));
			return;
		}

		// if entry's key is null, fetch sub operation, check feature name for key, and resolve ID to get a
		// string representation for the key
		final ReferenceOperation keyReferenceOperation = getKeyReferenceOperation(createDeleteOperation);

		if (keyReferenceOperation != null) {
			final Set<ModelElementId> otherInvolvedModelElements = keyReferenceOperation
				.getOtherInvolvedModelElements();
			final Iterator<ModelElementId> iterator = otherInvolvedModelElements.iterator();
			if (iterator.hasNext()) {
				final ModelElementId otherId = iterator.next();
				final EObject key = idToEObjectMapping.get(otherId);
				if (key != null) {
					idToKeyHashCode.put(mapEntryId, new Integer(key.hashCode()));
				} else {
					ModelUtil.logWarning(Messages.ReservationToConflictBucketCandidateMap_Key_Is_Null);
				}
			}
		} else {
			ModelUtil.logWarning(
				MessageFormat.format(Messages.ReservationToConflictBucketCandidateMap_SingleRefOp_Of_CreateOp_Missing,
					createDeleteOperation.getOperationId()));
		}
	}

	/**
	 * Tries to find the operation that set's the key attribute of a map entry.
	 * 
	 * @param createDeleteOperation
	 * @return
	 */
	private ReferenceOperation getKeyReferenceOperation(CreateDeleteOperation createDeleteOperation) {
		for (final AbstractOperation op : createDeleteOperation.getSubOperations()) {
			if (!SingleReferenceOperation.class.isInstance(op)) {
				continue;
			}

			final SingleReferenceOperation singleReferenceOperation = SingleReferenceOperation.class.cast(op);
			if (singleReferenceOperation.getFeatureName().equals(KEY)) {
				return singleReferenceOperation;
			}
		}

		return null;
	}

	private void handleFeatureOperation(AbstractOperation operation, ReservationSet reservationSet,
		ModelElementIdToEObjectMapping idToEObjectMapping) {
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
		if (isMultiRef(featureOperation) || isMultiRefSet(featureOperation) || isMultiMoveRef(featureOperation)) {

			for (final ModelElementId otherModelElement : featureOperation.getOtherInvolvedModelElements()) {

				reservationSet.addMultiReferenceWithOppositeReservation(modelElementId, featureName,
					otherModelElement.getId());

				Integer hashCode = null;

				if (isCreatedMapEntry(otherModelElement)) {
					hashCode = getKeyHashCode(otherModelElement);
				} else if (isMapEntry(idToEObjectMapping.get(otherModelElement))) {
					hashCode = getKeyHashCode(idToEObjectMapping, otherModelElement);
				}

				if (hashCode != null) {
					reservationSet.addMapKeyReservation(modelElementId, featureName,
						hashCode.toString());
				}
			}
		} else {
			reservationSet.addFeatureReservation(modelElementId, featureName);
		}
		return;
	}

	private Integer getKeyHashCode(ModelElementIdToEObjectMapping idToEObjectMapping, ModelElementId keyId) {
		final Map.Entry<?, ?> mapEntry = (Entry<?, ?>) idToEObjectMapping.get(keyId);
		if (mapEntry.getKey() != null) {
			return new Integer(mapEntry.getKey().hashCode());
		}
		return null;
	}

	private Integer getKeyHashCode(ModelElementId keyId) {
		return idToKeyHashCode.get(keyId);
	}

	private boolean isCreatedMapEntry(ModelElementId modelElementId) {
		return idToKeyHashCode.containsKey(modelElementId.getId());
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