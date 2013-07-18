/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * MaximilianKoegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.exceptions;

/**
 * Represents a condition the server or one of its components can not recover from and where a server shutdown is
 * inevitable.
 * 
 * @author Maximilian Koegel
 * @generated NOT
 */
@SuppressWarnings("serial")
public class FatalESException extends Exception {

	/**
	 * Default constructor.
	 * 
	 * @param message the message
	 * @param cause underlying exception
	 */
	public FatalESException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Default constructor.
	 * 
	 * @param message the message
	 */
	public FatalESException(String message) {
		super(message);
	}

	/**
	 * Default constructor.
	 * 
	 * @param cause the cause
	 */
	public FatalESException(Throwable cause) {
		super(cause);
	}

	/**
	 * Default constructor.
	 */
	public FatalESException() {
		this("Fatal exception");
	}

}
