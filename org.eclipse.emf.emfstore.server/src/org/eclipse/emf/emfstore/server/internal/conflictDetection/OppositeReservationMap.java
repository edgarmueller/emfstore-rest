package org.eclipse.emf.emfstore.server.internal.conflictDetection;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucketCandidate;

public class OppositeReservationMap extends LinkedHashMapWithConflictBucketCandidate<ConflictBucketCandidate> {

	private boolean hasOpposites;

	public OppositeReservationMap(boolean hasOpposites) {
		this.hasOpposites = hasOpposites;
	}

	@Override
	public Set<ConflictBucketCandidate> getAllConflictBucketCandidates() {
		Set<ConflictBucketCandidate> candidates = new LinkedHashSet<ConflictBucketCandidate>();
		if (!hasOpposites) {
			if (getConflictBucketCandidate() != null) {
				candidates.add(getConflictBucketCandidate());
			}
			return candidates;
		}
		candidates.addAll(values());
		return candidates;
	}

	public boolean hasOpposites() {
		return hasOpposites;
	}

	public Set<ConflictBucketCandidate> getConflictBucketCandidates(String oppositeModelElement) {
		Set<ConflictBucketCandidate> candidates = new LinkedHashSet<ConflictBucketCandidate>();
		if (!hasOpposites) {
			candidates.add(getConflictBucketCandidate());
			return candidates;
		}

		ConflictBucketCandidate matchingBucket = get(oppositeModelElement);
		candidates.add(matchingBucket);
		return candidates;
	}
}
