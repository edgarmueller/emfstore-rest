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

import org.eclipse.emf.emfstore.internal.client.model.controller.ChangeConflict;

/**
 * Represents the exception that there are conflicting changes.
 * 
 * @author koegel
 */
@SuppressWarnings("serial")
public class ChangeConflictException extends WorkspaceException {

	private final ChangeConflict changeConflict;

	public ChangeConflictException(ChangeConflict changeConflict) {
		super("Conflict detected on update");
		this.changeConflict = changeConflict;

	}

	public ChangeConflict getChangeConflict() {
		return changeConflict;
	}
}