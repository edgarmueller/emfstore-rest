/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * User - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.observer;

import java.lang.reflect.Method;

import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * 
 * @author mkoegel
 * @since 1.0.1
 */
public interface ESServerCallObserver {

	/**
	 * Notify the observer about a server call execution BEFORE it actually occurred.
	 * 
	 * @param method the called server method in {@link org.eclipse.emf.emfstore.internal.server.EMFStore}
	 * @param args the method arguments.
	 */
	void notifyPreServerCallExecution(Method method, Object[] args);

	/**
	 * Notify the observer about a server call execution failure with a runtime exception.
	 * 
	 * @param method the called server method in {@link org.eclipse.emf.emfstore.internal.server.EMFStore}
	 * @param args the method arguments.
	 * @param runtimeException the runtime exception that occurred
	 */
	void notifyServerCallExecutionRuntimeExceptionFailure(Method method, Object[] args,
		RuntimeException runtimeException);

	/**
	 * Notify the observer about a server call execution failure with a EMFStore specific exception.
	 * 
	 * @param method the called server method in {@link org.eclipse.emf.emfstore.internal.server.EMFStore}
	 * @param args the method arguments.
	 * @param exception the EMFStore specific exception that occurred
	 */
	void notifyServerCallExecutionESExceptionFailure(Method method, Object[] args, ESException exception);

	/**
	 * Notify the observer about a server call execution AFTER it occurred.
	 * 
	 * @param method the called server method in {@link org.eclipse.emf.emfstore.internal.server.EMFStore}
	 * @param args the method arguments.
	 * @param result the result
	 */
	void notifyPostServerCallExecution(Method method, Object[] args, Object result);

}
