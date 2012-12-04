package org.eclipse.emf.emfstore.server.internal.conflictDetection;

import java.util.LinkedHashMap;
import java.util.Set;

import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucketCandidate;

public abstract class LinkedHashMapWithConflictBucketCandidate<V> extends LinkedHashMap<String, V> {

	private ConflictBucketCandidate conflictBucketCandidate;

	public ConflictBucketCandidate getConflictBucketCandidate() {
		return this.conflictBucketCandidate;
	}

	public void setConflictBucketCandidate(ConflictBucketCandidate conflictBucketCandidate) {
		this.conflictBucketCandidate = conflictBucketCandidate;
	}

	public abstract Set<ConflictBucketCandidate> getAllConflictBucketCandidates();
}
