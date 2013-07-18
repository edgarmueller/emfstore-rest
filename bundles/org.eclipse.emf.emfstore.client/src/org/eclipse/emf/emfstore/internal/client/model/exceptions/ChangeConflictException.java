/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.exceptions;

import org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet;
import org.eclipse.emf.emfstore.internal.server.impl.api.ESConflictSetImpl;

/**
 * Exception that represents that there are conflicting changes.
 * 
 * @author koegel
 */
@SuppressWarnings("serial")
public class ChangeConflictException extends WorkspaceException {

	private final ChangeConflictSet changeConflict;

	/**
	 * Constructor.
	 * 
	 * @param changeConflict
	 *            the {@link ChangeConflictSet} leading to the conflict
	 */
	public ChangeConflictException(ChangeConflictSet changeConflict) {
		this("Conflict detected on update", changeConflict);
	}

	/**
	 * Constructor.
	 * 
	 * @param conflictSet
	 *            the {@link ESConflictSetImpl} leading to the conflict
	 */
	public ChangeConflictException(String message, ChangeConflictSet conflictSet) {
		super(message);
		this.changeConflict = conflictSet;
	}
}
