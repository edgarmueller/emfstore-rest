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
package org.eclipse.emf.emfstore.internal.common;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Implements a log for EMFStore Common.
 * 
 * @author koegel
 * 
 */
// TODO: merge into activator
public class LogAdapter implements ILog {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.ILog#log(java.lang.String, java.lang.Exception, int)
	 */
	public void log(final String message, final Exception exception, final int statusInt) {
		final Status status = new Status(statusInt, Activator.getDefault().getBundle().getSymbolicName(), statusInt, message,
			exception);
		Activator.getDefault().getLog().log(status);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.ILog#logException(java.lang.String, java.lang.Exception)
	 */
	public void logException(final String message, final Exception exception) {
		log(message, exception, IStatus.ERROR);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.ILog#logWarning(java.lang.String, java.lang.Exception)
	 */
	public void logWarning(final String message, final Exception exception) {
		log(message, exception, IStatus.WARNING);
	}

}