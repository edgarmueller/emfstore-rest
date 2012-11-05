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
package org.eclipse.emf.emfstore.server.conflictDetection;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Mapping from model elements to their features. The mapping uses
 * {@link ModelElementIdToFeatureSetMapping#ALL_FEATURES_NAME} as a magic feature name to represent all features of a
 * model element. This magic feature name is used also in the singleton set representing the set of all feature,
 * {@link ModelElementIdToFeatureSetMapping#ALL_FEATURE_NAME_SET}. Furthermore it uses the magic feature name
 * {@link ModelElementIdToFeatureSetMapping#EXISTENCE_FEATURE_NAME} to represent the feature representing the existence
 * of the model element as such.
 * 
 * @author mkoegel
 * 
 */
public class ModelElementIdToFeatureSetMapping {

	/**
	 * Magic feature name referring to container feature of a model element.
	 */
	public static final String CONTAINER_FEATURE_NAME = "+ContainerFeature";

	/**
	 * Magic feature name referring to all features of a model element.
	 */
	public static final String ALL_FEATURES_NAME = "+AllFeatures";

	/**
	 * Immutable singleton set representing the set of all features of a model element without explicitly containing
	 * them.
	 * The set only contains the magic feature name representing all features
	 * {@link ModelElementIdToFeatureSetMapping#ALL_FEATURES_NAME}.
	 */
	public static final Set<String> ALL_FEATURE_NAME_SET = Collections.singleton(ALL_FEATURES_NAME);

	/**
	 * Magic feature name referring to a feature representing the existence of a model element. This means no specific
	 * feature of the model element but rather the existence of the element as such.
	 */
	public static final String EXISTENCE_FEATURE_NAME = "+ExistenceFeature";

	private Map<String, Set<String>> modelElementToFeatureSetMap;

	/**
	 * Default construtor.
	 */
	public ModelElementIdToFeatureSetMapping() {
		modelElementToFeatureSetMap = new LinkedHashMap<String, Set<String>>();
	}

	/**
	 * Add a model element and its feature to the mapping.
	 * 
	 * @param modelElementId the model element id
	 * @param featureName the feature name
	 */
	public void add(String modelElementId, String featureName) {
		Set<String> featureSet = modelElementToFeatureSetMap.get(modelElementId);
		if (featureSet == ALL_FEATURE_NAME_SET) {
			return;
		}
		if (featureSet == null) {
			featureSet = new LinkedHashSet<String>();
			modelElementToFeatureSetMap.put(modelElementId, featureSet);
		}
		featureSet.add(featureName);
	}

	/**
	 * Add a model element and its existence (feature) to the mapping.
	 * 
	 * @param modelElementId the model element id
	 */
	public void addRequired(String modelElementId) {
		add(modelElementId, EXISTENCE_FEATURE_NAME);
	}

	/**
	 * Add a model element and all its features to the mapping.
	 * 
	 * @param modelElementId the model element id
	 */
	public void add(String modelElementId) {
		modelElementToFeatureSetMap.put(modelElementId, ALL_FEATURE_NAME_SET);
	}

	/**
	 * Add a model element and as a container features to the mapping.
	 * 
	 * @param modelElementId the model element id
	 */
	public void addForContainerFeature(String modelElementId) {
		add(modelElementId, CONTAINER_FEATURE_NAME);
	}

	/**
	 * Merge this mapping with another mapping.
	 * 
	 * @param otherMapping the other mapping
	 */
	public void merge(ModelElementIdToFeatureSetMapping otherMapping) {
		Map<String, Set<String>> otherMap = otherMapping.modelElementToFeatureSetMap;
		for (String modelElementId : otherMap.keySet()) {
			Set<String> otherFeatureSet = otherMap.get(modelElementId);
			Set<String> myFeatureSet = modelElementToFeatureSetMap.get(modelElementId);
			if (myFeatureSet == ALL_FEATURE_NAME_SET || otherFeatureSet == ALL_FEATURE_NAME_SET) {
				modelElementToFeatureSetMap.put(modelElementId, ALL_FEATURE_NAME_SET);
			} else if (myFeatureSet == null && otherFeatureSet != null) {
				modelElementToFeatureSetMap.put(modelElementId, otherFeatureSet);
			} else if (myFeatureSet != null && otherFeatureSet == null) {
				// nothing to do
			} else {
				// both are not null and not set to ALL FEATURES
				myFeatureSet.addAll(otherFeatureSet);
			}

		}
	}

	/**
	 * @return The number of mapped model element ids.
	 */
	public int size() {
		return modelElementToFeatureSetMap.size();
	}

	/**
	 * Get the set of features mapped to the given model element.
	 * 
	 * @param modelElementId the model element id
	 * @return the set of features, which maybe empty
	 */
	public Set<String> get(String modelElementId) {
		Set<String> featureSet = modelElementToFeatureSetMap.get(modelElementId);
		if (featureSet == null) {
			featureSet = Collections.emptySet();
		}
		return featureSet;
	}

	/**
	 * @return the set of mapped model elements.
	 */
	public Set<String> keySet() {
		return modelElementToFeatureSetMap.keySet();
	}

}