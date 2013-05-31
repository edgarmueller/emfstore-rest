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
package org.eclipse.emf.emfstore.internal.server.exceptions;

import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Represents a condition where a version specifier is invalid.
 * 
 * @author koegel
 */
@SuppressWarnings("serial")
public class InvalidVersionSpecException extends ESException {

	/**
	 * Default constructor.
	 */
	public InvalidVersionSpecException() {
		super("");
	}

	/**
	 * Default constructor.
	 * 
	 * @param message the message
	 */
	public InvalidVersionSpecException(String message) {
		super(message);
	}

	/**
	 * Default constructor.
	 * 
	 * @param message the message
	 * @param cause underlying exception
	 */
	public InvalidVersionSpecException(String message, Throwable cause) {
		super(message, cause);
	}

}
