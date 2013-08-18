/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.util;

/**
 * Abstract superclass for commands that are run on the EMFStore command stack and can throw eceptions.
 * 
 * @author mkoegel
 * 
 * @param <T> type of the exception
 */
public abstract class EMFStoreCommandWithException<T> extends EMFStoreCommand {

	private T exception;

	/**
	 * Get the exception that occured if any.
	 * 
	 * @return the exception or null
	 */
	public T getException() {
		return exception;
	}

	/**
	 * Set the exception that occured during the command.
	 * 
	 * @param exception the exception
	 */
	protected void setException(T exception) {
		this.exception = exception;
	}

	/**
	 * Determine if exception occured on execution.
	 * 
	 * @return true if exception occured.
	 */
	public boolean hasException() {
		return exception != null;
	}
}
