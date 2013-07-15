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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.emfstore.client.observer.ESExceptionObserver;
import org.eclipse.emf.emfstore.internal.client.model.Activator;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.IResourceLogger;

/**
 * Workspace utility class.
 * 
 * @author koegel
 */
public final class WorkspaceUtil {

	/**
	 * Resource logger instance.
	 */
	private static IResourceLogger resourceLogger = new IResourceLogger() {

		public void logWarning(String msg) {
			log(msg, Status.WARNING);
		}

		public void logError(String msg) {
			log(msg, Status.ERROR);
		}
	};

	/**
	 * Private constructor.
	 */
	private WorkspaceUtil() {
		// nothing to do
	}

	/**
	 * Returns the resource logger.
	 * 
	 * @return the resource logger
	 */
	public static IResourceLogger getResourceLogger() {
		return resourceLogger;
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
	 * Log a message.
	 * 
	 * @param message the message
	 * @param statusInt the status constant as defined in {@link IStatus}
	 */
	public static void log(String message, int statusInt) {
		Activator activator = Activator.getDefault();
		Status status = new Status(statusInt, activator.getBundle().getSymbolicName(), message);
		activator.getLog().log(status);
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
		Boolean errorHandeled = ESWorkspaceProviderImpl.getObserverBus().notify(ESExceptionObserver.class)
			.handleError(runtimeException);
		logException(exception.getMessage(), exception);
		if (!errorHandeled.booleanValue()) {
			throw runtimeException;
		}
	}
}
