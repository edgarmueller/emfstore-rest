/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.common;

/**
 * Provides a context in which a {@link Runnable} is exectued.
 * 
 * @author emueller
 * 
 */
public interface IRunnableContext {

	/**
	 * Executes a given {@link Runnable}.
	 * 
	 * @param runnable
	 *            the {@link Runnable} to be executed
	 */
	void executeRunnable(Runnable runnable);
}
