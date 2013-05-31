/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.conflictDetection;

import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * This strategy will never detect any conflicts.
 * 
 * @author koegel
 */
public class AlwaysFalseConflictDetectionStrategy implements ConflictDetectionStrategy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictDetectionStrategy#doConflict(org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation,
	 *      org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation)
	 */
	public boolean doConflict(AbstractOperation operationA, AbstractOperation operationB) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictDetectionStrategy#isRequired(org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation,
	 *      org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation)
	 */
	public boolean isRequired(AbstractOperation requiredOperation, AbstractOperation operation) {
		return false;
	}

}
