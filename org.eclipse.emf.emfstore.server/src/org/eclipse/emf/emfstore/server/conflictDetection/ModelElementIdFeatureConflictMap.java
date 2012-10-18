/*******************************************************************************
 * Copyright 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.conflictDetection;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Map from modelelements and their features to conflict candidate buckets.
 * 
 * @author mkoegel
 * 
 */
public class ModelElementIdFeatureConflictMap {

	// map from concatination of modelelement ID and feature name to conflict bucket candidate
	private Map<String, ConflictBucketCandidate> modelElementIdAndFeatureToConflictMap;

	// map from modelelement ID to conflict bucket candidate
	private Map<String, ConflictBucketCandidate> modelElementsWithAllFeaturesToConflictMap;

	// Mapping from modelElementId to a set of feature names
	private ModelElementIdToFeatureSetMapping modelElementIdToFeatureSetMapping;

	// map from model elementid to buckets requiring its existence
	private LinkedHashMap<String, Set<ConflictBucketCandidate>> modelElementExistenceToConflictBucketSetMap;

	/**
	 * Default constructor.
	 */
	public ModelElementIdFeatureConflictMap() {
		modelElementIdAndFeatureToConflictMap = new LinkedHashMap<String, ConflictBucketCandidate>();
		modelElementsWithAllFeaturesToConflictMap = new LinkedHashMap<String, ConflictBucketCandidate>();
		modelElementIdToFeatureSetMapping = new ModelElementIdToFeatureSetMapping();
		modelElementExistenceToConflictBucketSetMap = new LinkedHashMap<String, Set<ConflictBucketCandidate>>();
	}

	/**
	 * Retrieve currently assigned candidate buckets for the requested model element and feature. The magic feature
	 * names {@link ModelElementIdToFeatureSetMapping#EXISTENCE_FEATURE_NAME} and
	 * {@link ModelElementIdToFeatureSetMapping#ALL_FEATURES_NAME} are also valid inputs.
	 * If get is called with {@link ModelElementIdToFeatureSetMapping#ALL_FEATURES_NAME} it will collect all buckets
	 * that block any feature of the given model element.
	 * If get is called with {@link ModelElementIdToFeatureSetMapping#EXISTENCE_FEATURE_NAME}, it will only collect
	 * buckets
	 * which requested {@link ModelElementIdToFeatureSetMapping#ALL_FEATURES_NAME}.
	 * 
	 * @param modelElementId the model element id
	 * @param featureName the feature name including magic names as stated above
	 * @return a set of buckets that conflict with the given model element and feature, the set may be empty
	 */
	public Set<ConflictBucketCandidate> get(String modelElementId, String featureName) {
		ConflictBucketCandidate conflictBucketCandidate = modelElementsWithAllFeaturesToConflictMap.get(modelElementId);
		if (conflictBucketCandidate != null) {
			return Collections.singleton(conflictBucketCandidate);
		}
		if (featureName == ModelElementIdToFeatureSetMapping.ALL_FEATURES_NAME) {
			Set<String> featureSet = modelElementIdToFeatureSetMapping.get(modelElementId);
			Set<ConflictBucketCandidate> buckets = new LinkedHashSet<ConflictBucketCandidate>();
			// get all buckets that already blocked features for this model element
			for (String feature : featureSet) {
				ConflictBucketCandidate subBucket = modelElementIdAndFeatureToConflictMap.get(modelElementId + feature);
				if (subBucket != null) {
					buckets.add(subBucket);
				}
			}
			// get all buckets that require existence of this element
			Set<ConflictBucketCandidate> existenceRequiringSet = modelElementExistenceToConflictBucketSetMap
				.get(modelElementId);
			if (existenceRequiringSet != null) {
				buckets.addAll(existenceRequiringSet);
			}
			return buckets;
		}
		// normal feature collisions including existence
		ConflictBucketCandidate bucketCandidate = modelElementIdAndFeatureToConflictMap.get(modelElementId
			+ featureName);
		// if we find a conflicting bucket for the given feature and it is not just an Existence feature
		if (bucketCandidate != null && featureName != ModelElementIdToFeatureSetMapping.EXISTENCE_FEATURE_NAME) {
			return Collections.singleton(bucketCandidate);
		}
		// in any other case return not found buckets
		return Collections.emptySet();
	}

	/**
	 * Request to map and thereby reserve the given model element and feature for the given bucket.
	 * 
	 * @param modelElementId the model element id
	 * @param featureName the feature name
	 * @param currentOperationsConflictBucket the bucket
	 * @param replace whether existing entries should be overwritten, if this is not enabled and existing entries are
	 *            found an {@link IllegalStateException} will be thrown
	 */
	public void put(String modelElementId, String featureName, ConflictBucketCandidate currentOperationsConflictBucket,
		boolean replace) {
		ConflictBucketCandidate conflictBucketCandidate = modelElementsWithAllFeaturesToConflictMap.get(modelElementId);
		if (conflictBucketCandidate != null && currentOperationsConflictBucket != conflictBucketCandidate && !replace) {
			throw new IllegalStateException("Bucket is already taken!");
		}

		if (featureName == ModelElementIdToFeatureSetMapping.ALL_FEATURES_NAME) {
			modelElementsWithAllFeaturesToConflictMap.put(modelElementId, currentOperationsConflictBucket);
			return;
		}
		// feature is a normal feature
		if (replace && conflictBucketCandidate != null) {
			return;
		}
		conflictBucketCandidate = modelElementIdAndFeatureToConflictMap.get(modelElementId + featureName);
		if (conflictBucketCandidate != null && currentOperationsConflictBucket != conflictBucketCandidate && !replace
			&& featureName != ModelElementIdToFeatureSetMapping.EXISTENCE_FEATURE_NAME) {
			throw new IllegalStateException("Bucket is already taken!");
		}
		if (featureName == ModelElementIdToFeatureSetMapping.EXISTENCE_FEATURE_NAME) {
			Set<ConflictBucketCandidate> existingSet = modelElementExistenceToConflictBucketSetMap.get(modelElementId);
			if (existingSet == null) {
				existingSet = new LinkedHashSet<ConflictBucketCandidate>();
				modelElementExistenceToConflictBucketSetMap.put(modelElementId, existingSet);
			}
			existingSet.add(currentOperationsConflictBucket);
		}
		modelElementIdAndFeatureToConflictMap.put(modelElementId + featureName, currentOperationsConflictBucket);
		modelElementIdToFeatureSetMapping.add(modelElementId, featureName);

	}
}
