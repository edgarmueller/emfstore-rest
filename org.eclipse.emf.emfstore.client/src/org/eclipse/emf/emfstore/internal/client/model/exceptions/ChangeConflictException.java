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
package org.eclipse.emf.emfstore.internal.client.model.exceptions;

import org.eclipse.emf.emfstore.client.IChangeConflict;

/**
 * Exception that represents that there are conflicting changes.
 * 
 * @author koegel
 */
@SuppressWarnings("serial")
public class ChangeConflictException extends WorkspaceException {

	private final IChangeConflict changeConflict;

	/**
	 * Constructor.
	 * 
	 * @param changeConflict
	 *            the {@link IChangeConflict} leading to the conflict
	 */
	public ChangeConflictException(IChangeConflict changeConflict) {
		super("Conflict detected on update");
		this.changeConflict = changeConflict;

	}

	/**
	 * Returns the {@link IChangeConflict} that caused the exception.
	 * 
	 * @return the change conflict that led to the exception
	 */
	public IChangeConflict getChangeConflict() {
		return changeConflict;
	}
}