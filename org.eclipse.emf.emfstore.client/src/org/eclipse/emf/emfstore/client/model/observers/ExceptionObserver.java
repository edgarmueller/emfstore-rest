/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.observers;

import org.eclipse.emf.emfstore.common.observer.IObserver;

/**
 * Observes Exceptions and tries to handle them.
 * 
 * @author Maximilian Koegel
 * 
 */
public interface ExceptionObserver extends IObserver {

	/**
	 * Handle the exception.
	 * 
	 * @param exception the exception
	 * @return true if exception was successfully handled
	 */
	boolean handleError(RuntimeException exception);
}