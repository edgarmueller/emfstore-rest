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
package org.eclipse.emf.emfstore.client.model.util;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.emfstore.client.model.Activator;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.observers.ExceptionObserver;
import org.eclipse.emf.emfstore.common.CommonUtil;

/**
 * Workspace utility class.
 * 
 * @author koegel
 */
public final class WorkspaceUtil {

	/**
	 * Private constructor.
	 */
	private WorkspaceUtil() {
		// nothing to do
	}

	/**
	 * Log an exception to the error log.
	 * 
	 * @param message the message
	 * @param e the exception
	 */
	public static void logException(String message, Exception e) {
		log(message, e, IStatus.ERROR);
	}

	/**
	 * Log a warning to the error log.
	 * 
	 * @param message the message
	 * @param e the exception
	 */
	public static void logWarning(String message, Exception e) {
		log(message, e, IStatus.WARNING);
	}

	/**
	 * Log a warning to the error log.
	 * 
	 * @param message the message
	 * @param exception the exception or null f not applicable
	 * @param statusInt the status constant as defined in {@link IStatus}
	 */
	public static void log(String message, Exception exception, int statusInt) {
		Activator activator = Activator.getDefault();
		Status status = new Status(statusInt, activator.getBundle().getSymbolicName(), statusInt, message, exception);
		activator.getLog().log(status);
		if (CommonUtil.isTesting() && exception != null) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Handles the given exception by wrapping it in a {@link RuntimeException} and propagating it to all registered
	 * exception handlers. If no exception handler did handle the exception
	 * a the wrapped exception will be re-thrown.
	 * 
	 * @param errorMessage
	 *            the error message that should be used for propagating the exception
	 * @param exception
	 *            the actual exception
	 */
	public static void handleException(String errorMessage, Exception exception) {
		wrapAndHandleException(errorMessage, exception);
	}

	/**
	 * Handles the given exception by wrapping it in a {@link RuntimeException} and propagating it to all registered
	 * exception handlers. If no exception handler did handle the exception
	 * a the wrapped exception will be re-thrown.
	 * 
	 * @param exception
	 *            the actual exception
	 */
	public static void handleException(Exception exception) {
		wrapAndHandleException(exception.getMessage(), exception);
	}

	private static void wrapAndHandleException(String errorMessage, Exception exception) {
		RuntimeException runtimeException = new RuntimeException(errorMessage, exception);
		Boolean errorHandeled = WorkspaceManager.getObserverBus().notify(ExceptionObserver.class)
			.handleError(runtimeException);
		logException(exception.getMessage(), exception);
		if (!errorHandeled.booleanValue()) {
			throw runtimeException;
		}
	}

}
