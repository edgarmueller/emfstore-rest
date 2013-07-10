/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.handler;

import org.eclipse.emf.emfstore.client.ESLocalProject;

/**
 * <p>
 * Provides a context in which a {@link Runnable} is executed.
 * </p>
 * <p>
 * This may be used to provide a context while applying operations on a {@link ESLocalProject}.
 * </p>
 * 
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESRunnableContext {

	/**
	 * Executes a given {@link Runnable}.
	 * 
	 * @param runnable
	 *            the {@link Runnable} to be executed
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void executeRunnable(Runnable runnable);
}
