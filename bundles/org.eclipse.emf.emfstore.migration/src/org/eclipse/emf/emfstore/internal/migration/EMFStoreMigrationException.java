/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * mkoegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.migration;

/**
 * Represents a failure in migrating models to more recent versions.
 * 
 * @author mkoegel
 * 
 */
@SuppressWarnings("serial")
public class EMFStoreMigrationException extends Exception {

	/**
	 * Default constructor.
	 * 
	 * @param message the message.
	 * @param cause the cause
	 */
	public EMFStoreMigrationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Default constructor.
	 * 
	 * @param message the message.
	 */
	public EMFStoreMigrationException(String message) {
		super(message);
	}

}
