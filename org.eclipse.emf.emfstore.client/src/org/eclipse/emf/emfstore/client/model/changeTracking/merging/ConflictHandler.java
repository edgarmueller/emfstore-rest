/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.changeTracking.merging;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.Conflict;

/**
 * Allows to hook in custom conflict treatment for group of conflicting changes.
 * Conflicting changes are determined by the ConflictDetector, which can be
 * exchanged as well.
 * 
 * @author wesendon
 */
public interface ConflictHandler {

	/**
	 * This method is always called before {@link #handle(DecisionManager, Conflicting)} in order to check whether
	 * this handler is relevant for the conflicting changes.
	 * 
	 * @param conflicting
	 *            Conflicting bucket
	 * @return true, if can handle
	 */
	boolean canHandle(ConflictBucket conflicting);

	/**
	 * Is called when {@link #canHandle(Conflicting)} returned true. On basis of
	 * the decisionManager and the confliciting bucket, this method should
	 * return a {@link Conflict} instance.
	 * 
	 * @param dm
	 *            {@link DecisionManager}
	 * @param conflicting
	 *            bucket
	 * @return conflict
	 */
	Conflict handle(DecisionManager dm, ConflictBucket conflicting);
}
