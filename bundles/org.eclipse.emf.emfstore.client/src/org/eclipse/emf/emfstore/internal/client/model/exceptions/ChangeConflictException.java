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

import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESChangeConflictImpl;

/**
 * Exception that represents that there are conflicting changes.
 * 
 * @author koegel
 */
@SuppressWarnings("serial")
public class ChangeConflictException extends WorkspaceException {

	private final ESChangeConflict changeConflict;

	/**
	 * Constructor.
	 * 
	 * @param changeConflict
	 *            the {@link ESChangeConflictImpl} leading to the conflict
	 */
	public ChangeConflictException(ESChangeConflict changeConflict) {
		super("Conflict detected on update");
		this.changeConflict = changeConflict;

	}

	/**
	 * Returns the {@link ESChangeConflictImpl} that caused the exception.
	 * 
	 * @return the change conflict that led to the exception
	 */
	public ESChangeConflict getChangeConflict() {
		return changeConflict;
	}
}
