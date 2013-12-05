/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.util;

import java.util.concurrent.Callable;

/**
 * Convenience class for using {@link RunESCommand} without a return value,
 * but possibly throwing an exception.
 * 
 * @param <E> the type of exception thrown by {@code run}
 */
public abstract class ESVoidCallableWithException<E extends Exception> implements Callable<Void> {
	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public Void call() throws Exception {
		run();
		return null;
	}

	// BEGIN SUPRESS CATCH EXCEPTION
	public abstract void run() throws Exception;
	// END SUPRESS CATCH EXCEPTION

}