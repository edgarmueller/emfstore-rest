/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.exceptions;

/**
 * An EmfStore Exception expresses that any exceptional condition in the EmfStore occurred that prevented the store from
 * processing the requested operation. There are subclasses of EmfStore that can be caught to get a more detailed
 * picture of what went wrong and to be able react more specific to different conditions.
 * 
 * @author mkoegel
 * 
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class ESException extends Exception {

	private static final long serialVersionUID = 4258038406861950412L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the causing exception
	 */
	public ESException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the message
	 */
	public ESException(String message) {
		super(message);
	}

	/**
	 * Default constructor.
	 * 
	 * @param cause
	 *            the causing exception
	 */
	public ESException(Throwable cause) {
		super(cause);
	}
}
