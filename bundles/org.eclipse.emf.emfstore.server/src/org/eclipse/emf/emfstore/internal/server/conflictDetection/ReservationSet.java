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

import java.util.Set;

/**
 * <p>
 * Mapping from model elements to their features.
 * </p>
 * <p>
 * The mapping uses {@link ReservationSet#ALL_FEATURES_NAME} as a magic feature name to represent all features of a
 * model element. This magic feature name is used also in the singleton set representing the set of all feature,
 * {@link ReservationSet#ALL_FEATURE_NAME_SET}. Furthermore it uses the magic feature name
 * {@link ReservationSet#EXISTENCE_FEATURE_NAME} to represent the feature representing the existence of the model
 * element as such.
 * </p>
 * 
 * @author mkoegel
 * @author emueller
 * 
 */
public class ReservationSet {

	private final ModelElementIdReservationMap modelElementIdReservationMap;

	/**
	 * Default constructor.
	 */
	public ReservationSet() {
		modelElementIdReservationMap = new ModelElementIdReservationMap();
	}

	public void addFullReservation(String modelElementId) {
		addFullReservation(modelElementId, null);
	}

	public void addExistenceReservation(String modelElementId) {
		addExistenceReservation(modelElementId, null);
	}

	public void addContainerReservation(String modelElementId) {
		addFeatureReservation(modelElementId, FeatureNameReservationMap.CONTAINER_FEATURE);
	}

	public void addFeatureReservation(String modelElementId, String featureName) {
		addFeatureReservation(modelElementId, featureName, null);
	}

	public void addMultiReferenceWithOppositeReservation(String modelElementId, String featureName,
		String oppositeModelElementId) {
		addMultiReferenceWithOppositeReservation(modelElementId, featureName, oppositeModelElementId, null);
	}

	public void addMapKeyWithOppositeReservation(String modelElementId, String featureName,
		String key) {
		addFeatureReservation(modelElementId, featureName + key);
		// addMultiReferenceWithOppositeReservation(modelElementId, featureName + key, );
	}

	public void addFullReservation(String modelElementId, ConflictBucketCandidate conflictBucketCandidate) {
		final FeatureNameReservationMap featureNameReservationMap = new FeatureNameReservationMap(true);
		featureNameReservationMap.setConflictBucketCandidate(conflictBucketCandidate);
		modelElementIdReservationMap.put(modelElementId, featureNameReservationMap);
	}

	public void addExistenceReservation(String modelElementId, ConflictBucketCandidate conflictBucketCandidate) {
		FeatureNameReservationMap featureNameReservationMap = modelElementIdReservationMap.get(modelElementId);
		if (featureNameReservationMap == null) {
			featureNameReservationMap = new FeatureNameReservationMap();
			modelElementIdReservationMap.put(modelElementId, featureNameReservationMap);
		}
		ExistenceOppositeReservationMap existenceOppositeReservationMap;
		if (!featureNameReservationMap.hasExistenceFeature()) {
			existenceOppositeReservationMap = new ExistenceOppositeReservationMap();
			featureNameReservationMap.put(FeatureNameReservationMap.EXISTENCE_FEATURE, existenceOppositeReservationMap);
		} else {
			existenceOppositeReservationMap = (ExistenceOppositeReservationMap) featureNameReservationMap
				.get(FeatureNameReservationMap.EXISTENCE_FEATURE);
		}
		existenceOppositeReservationMap.addConflictBucketCandidate(conflictBucketCandidate);
	}

	public void addFeatureReservation(String modelElementId, String featureName,
		ConflictBucketCandidate conflictBucketCandidate) {
		if (featureName == FeatureNameReservationMap.EXISTENCE_FEATURE) {
			addExistenceReservation(modelElementId);
			return;
		}
		FeatureNameReservationMap featureNameReservationMap = modelElementIdReservationMap.get(modelElementId);
		if (featureNameReservationMap == null) {
			featureNameReservationMap = new FeatureNameReservationMap();
			modelElementIdReservationMap.put(modelElementId, featureNameReservationMap);
		}
		OppositeReservationMap oppositeReservationMap;
		if (!featureNameReservationMap.containsKey(featureName)) {
			oppositeReservationMap = new OppositeReservationMap(false);
			featureNameReservationMap.put(featureName, oppositeReservationMap);
		} else {
			oppositeReservationMap = featureNameReservationMap.get(featureName);
		}
		oppositeReservationMap.setConflictBucketCandidate(conflictBucketCandidate);
		if (oppositeReservationMap.hasOpposites()) {
			throw new IllegalStateException("Reservation on same feature with AND without opposites is illegal!");
		}
	}

	public void addMultiReferenceWithOppositeReservation(String modelElementId, String featureName,
		String oppositeModelElementId, ConflictBucketCandidate conflictBucketCandidate) {
		FeatureNameReservationMap featureNameReservationMap = modelElementIdReservationMap.get(modelElementId);
		if (featureNameReservationMap == null) {
			featureNameReservationMap = new FeatureNameReservationMap();
			modelElementIdReservationMap.put(modelElementId, featureNameReservationMap);
		}
		OppositeReservationMap oppositeReservationMap;
		if (!featureNameReservationMap.containsKey(featureName)) {
			oppositeReservationMap = new OppositeReservationMap(true);
			featureNameReservationMap.put(featureName, oppositeReservationMap);
		} else {
			oppositeReservationMap = featureNameReservationMap.get(featureName);
		}

		// oppositeReservationMap.put(oppositeModelElementId, null);
		// oppositeReservationMap.setConflictBucketCandidate(conflictBucketCandidate);

		if (oppositeReservationMap.hasOpposites()) {
			// oppositeReservationMap.setConflictBucketCandidate(conflictBucketCandidate);
			oppositeReservationMap.put(oppositeModelElementId, conflictBucketCandidate);
		} else {
			throw new IllegalStateException("Reservation on same feature with AND without opposites is illegal!");
		}
	}

	public Set<String> getAllModelElements() {
		return modelElementIdReservationMap.keySet();
	}

	public boolean hasExistenceReservation(String modelElementId) {
		final FeatureNameReservationMap featureNameReservationMap = modelElementIdReservationMap.get(modelElementId);
		return featureNameReservationMap != null && featureNameReservationMap.hasExistenceFeature();
	}

	public boolean hasFullReservation(String modelElementId) {
		final FeatureNameReservationMap featureNameReservationMap = modelElementIdReservationMap.get(modelElementId);
		return featureNameReservationMap != null && featureNameReservationMap.isAllFeatures();
	}

	public Set<String> getFeatureNames(String modelElementId) {
		return modelElementIdReservationMap.get(modelElementId).keySet();
	}

	public boolean hasFeatureReservation(String modelElementId, String featureName) {
		final FeatureNameReservationMap featureNameReservationMap = modelElementIdReservationMap.get(modelElementId);
		if (featureNameReservationMap == null) {
			return false;
		}
		final OppositeReservationMap oppositeReservationMap = featureNameReservationMap.get(featureName);
		return oppositeReservationMap != null && !oppositeReservationMap.hasOpposites();
	}

	public boolean hasOppositeReservations(String modelElementId, String featureName) {
		final FeatureNameReservationMap featureNameReservationMap = modelElementIdReservationMap.get(modelElementId);
		if (featureNameReservationMap == null) {
			return false;
		}
		final OppositeReservationMap oppositeReservationMap = featureNameReservationMap.get(featureName);
		return oppositeReservationMap != null && oppositeReservationMap.hasOpposites();
	}

	public Set<String> getOpposites(String modelElementId, String featureName) {
		return modelElementIdReservationMap.get(modelElementId).get(featureName).keySet();
	}

	public Set<ConflictBucketCandidate> getConflictBucketCandidates(String modelElementId) {
		return modelElementIdReservationMap.getConflictBucketCandidates(modelElementId);
	}

	public Set<ConflictBucketCandidate> getConflictBucketCandidates(String modelElementId, String featureName) {
		return modelElementIdReservationMap.getConflictBucketCandidates(modelElementId, featureName);
	}

	public Set<ConflictBucketCandidate> getConflictBucketCandidates(String modelElementId, String featureName,
		String oppositeModelElement) {
		return modelElementIdReservationMap.getConflictBucketCandidates(modelElementId, featureName,
			oppositeModelElement);
	}

	public boolean hasOppositeReservation(String modelElement, String featureName, String oppositeModelElement) {
		final FeatureNameReservationMap featureNameReservationMap = modelElementIdReservationMap.get(modelElement);
		if (featureNameReservationMap == null) {
			return false;
		}
		final OppositeReservationMap oppositeReservationMap = featureNameReservationMap.get(featureName);
		if (oppositeReservationMap == null || !oppositeReservationMap.hasOpposites()) {
			return false;
		}
		return oppositeReservationMap.containsKey(oppositeModelElement);
	}

}