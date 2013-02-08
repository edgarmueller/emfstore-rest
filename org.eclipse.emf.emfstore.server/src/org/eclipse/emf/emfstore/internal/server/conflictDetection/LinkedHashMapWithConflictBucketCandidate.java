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
import java.util.Set;


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
