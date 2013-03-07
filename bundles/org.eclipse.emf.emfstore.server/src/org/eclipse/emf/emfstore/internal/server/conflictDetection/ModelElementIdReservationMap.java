/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.conflictDetection;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;


public class ModelElementIdReservationMap extends LinkedHashMap<String, FeatureNameReservationMap> {

	public Set<ConflictBucketCandidate> getConflictBucketCandidates(String modelElementId) {
		FeatureNameReservationMap featureNameReservationMap = get(modelElementId);
		if (featureNameReservationMap == null) {
			return new LinkedHashSet<ConflictBucketCandidate>();
		}
		return featureNameReservationMap.getAllConflictBucketCandidates();
	}

	public Set<ConflictBucketCandidate> getConflictBucketCandidates(String modelElementId, String featureName) {
		FeatureNameReservationMap featureNameReservationMap = get(modelElementId);

		LinkedHashSet<ConflictBucketCandidate> result = new LinkedHashSet<ConflictBucketCandidate>();
		if (featureNameReservationMap == null) {
			return result;
		}

		return featureNameReservationMap.getConflictBucketCandidates(featureName);
	}

	public Set<ConflictBucketCandidate> getConflictBucketCandidates(String modelElementId, String featureName,
		String oppositeModelElement) {
		FeatureNameReservationMap featureNameReservationMap = get(modelElementId);

		LinkedHashSet<ConflictBucketCandidate> result = new LinkedHashSet<ConflictBucketCandidate>();
		if (featureNameReservationMap == null) {
			return result;
		}

		return featureNameReservationMap.getConflictBucketCandidates(featureName, oppositeModelElement);
	}
}
