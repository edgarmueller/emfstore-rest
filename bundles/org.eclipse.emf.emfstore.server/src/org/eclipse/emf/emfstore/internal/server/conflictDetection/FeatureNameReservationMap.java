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
		Set<ConflictBucketCandidate> result = new LinkedHashSet<ConflictBucketCandidate>();
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
		Set<ConflictBucketCandidate> result = new LinkedHashSet<ConflictBucketCandidate>();
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
		Set<ConflictBucketCandidate> result = new LinkedHashSet<ConflictBucketCandidate>();
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
