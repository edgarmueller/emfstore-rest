package org.eclipse.emf.emfstore.server.internal.conflictDetection;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucketCandidate;

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
