package org.eclipse.emf.emfstore.server.internal.conflictDetection;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucketCandidate;

public class FeatureNameReservationMap extends LinkedHashMapWithConflictBucketCandidate<OppositeReservationMap> {

	public static final String EXISTENCE_FEATURE = "+existence";

	public static final String CONTAINER_FEATURE = "+container";

	private boolean isAllFeatures;

	public FeatureNameReservationMap(boolean isAllFeatures) {
		this.isAllFeatures = isAllFeatures;
	}

	public FeatureNameReservationMap() {
		this(false);
	}

	@Override
	public Set<ConflictBucketCandidate> getAllConflictBucketCandidates() {
		Set<ConflictBucketCandidate> result = new HashSet<ConflictBucketCandidate>();
		if (isAllFeatures) {
			if (getConflictBucketCandidate() != null) {
				result.add(getConflictBucketCandidate());
			}
			return result;
		}

		for (String featureName : keySet()) {
			result.addAll(get(featureName).getAllConflictBucketCandidates());
		}
		return result;
	}

	public boolean hasExistenceFeature() {
		return containsKey(EXISTENCE_FEATURE);
	}

	public boolean isAllFeatures() {
		return isAllFeatures;
	}

	public Set<ConflictBucketCandidate> getConflictBucketCandidates(String featureName) {
		Set<ConflictBucketCandidate> result = new HashSet<ConflictBucketCandidate>();
		if (isAllFeatures()) {
			result.add(getConflictBucketCandidate());
			return result;
		}
		OppositeReservationMap oppositeReservationMap = get(featureName);
		if (oppositeReservationMap == null) {
			return result;
		}
		return oppositeReservationMap.getAllConflictBucketCandidates();
	}

	public Set<ConflictBucketCandidate> getConflictBucketCandidates(String featureName, String oppositeModelElement) {
		Set<ConflictBucketCandidate> result = new HashSet<ConflictBucketCandidate>();
		if (isAllFeatures()) {
			result.add(getConflictBucketCandidate());
			return result;
		}
		OppositeReservationMap oppositeReservationMap = get(featureName);
		if (oppositeReservationMap == null) {
			return result;
		}
		return oppositeReservationMap.getConflictBucketCandidates(oppositeModelElement);
	}
}
