package org.eclipse.emf.emfstore.server.internal.conflictDetection;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucketCandidate;

public class ExistenceOppositeReservationMap extends OppositeReservationMap {

	private Set<ConflictBucketCandidate> candidates;

	public ExistenceOppositeReservationMap() {
		super(false);
		candidates = new LinkedHashSet<ConflictBucketCandidate>();
	}

	@Override
	public Set<ConflictBucketCandidate> getAllConflictBucketCandidates() {
		return candidates;
	}

	public void addConflictBucketCandidate(ConflictBucketCandidate conflictBucketCandidate) {
		if (conflictBucketCandidate == null) {
			return;
		}
		candidates.add(conflictBucketCandidate);
	}

	@Override
	public void setConflictBucketCandidate(ConflictBucketCandidate conflictBucketCandidate) {
		super.setConflictBucketCandidate(conflictBucketCandidate);
		addConflictBucketCandidate(conflictBucketCandidate);
	}

}
