/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common;

/**
 * Logger to log warnings and exceptions.
 * 
 * @author Koegel
 * 
 */
public interface ILog {

	/**
	 * Log an error.
	 * 
	 * @param message the message
	 * @param exception the causing exception
	 * @param statusInt a status
	 */
	void log(String message, Exception exception, int statusInt);

	/**
	 * Log an exception as an error.
	 * 
	 * @param message the message
	 * @param exception the causing exception.
	 */
	void logException(String message, Exception exception);

	/**
	 * Log an exception as a warning.
	 * 
	 * @param message the message
	 * @param exception the causing exception
	 */
	void logWarning(String message, Exception exception);

}
