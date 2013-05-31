/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * wesendonk
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.exceptions;

/**
 * Is thrown in case of an invalid property.
 * 
 * @author wesendonk
 */
@SuppressWarnings("serial")
public class InvalidPropertyException extends FatalESException {

	/**
	 * Default constructor.
	 */
	public InvalidPropertyException() {
		super("Invalid property.");
	}

	/**
	 * Default constructor.
	 * 
	 * @param message the message
	 * @param cause underlying exception
	 */
	public InvalidPropertyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Default constructor.
	 * 
	 * @param message the message
	 */
	public InvalidPropertyException(String message) {
		super(message);
	}

}
