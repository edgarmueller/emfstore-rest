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

import java.util.LinkedHashSet;
import java.util.Set;


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
