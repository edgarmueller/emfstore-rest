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
package org.eclipse.emf.emfstore.internal.server.internal.conflictDetection;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucketCandidate;

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
