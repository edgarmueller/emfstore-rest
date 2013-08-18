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
package org.eclipse.emf.emfstore.internal.client.model.util;

/**
 * Command that can buffer a result for later retrieval.
 * 
 * @author koegel
 * @param <T> result type
 * @param <E> exception type
 */
public abstract class EMFStoreCommandWithResultAndException<T, E> extends EMFStoreCommandWithResult<T> {

	private E exception;

	/**
	 * Get the exception that occured during command execution if any.
	 * 
	 * @return the exception or null
	 */
	public E getException() {
		return exception;
	}

	/**
	 * Set the exception that occured during the command execution.
	 * 
	 * @param exception the exception
	 */
	protected void setException(E exception) {
		this.exception = exception;
	}

	/**
	 * Determine if an exception occured during command execution.
	 * 
	 * @return true if an exception has occured
	 */
	public boolean hasException() {
		return exception != null;
	}
}
