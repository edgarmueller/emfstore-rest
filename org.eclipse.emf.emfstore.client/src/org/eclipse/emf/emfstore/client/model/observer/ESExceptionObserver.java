/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.observer;

import org.eclipse.emf.emfstore.common.IObserver;

/**
 * Observes exceptions and tries to handle them.
 * 
 * @author koegel
 */
public interface ESExceptionObserver extends IObserver {

	/**
	 * Handle the exception.
	 * 
	 * @param exception the exception
	 * @return true if exception was successfully handled
	 */
	boolean handleError(RuntimeException exception);
}