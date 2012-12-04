package org.eclipse.emf.emfstore.server.internal.conflictDetection;

import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucketCandidate;

public interface ConflictBucketCandidateStore {

	ConflictBucketCandidate getConflictBucketCandidate();

	void setConflictBucketCandidate(ConflictBucketCandidate conflictBucketCandidate);
}
