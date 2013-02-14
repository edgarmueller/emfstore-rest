/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.versioning;

import java.util.Calendar;

import org.eclipse.emf.emfstore.server.model.ESLogMessage;
import org.eclipse.emf.emfstore.server.model.ESLogMessageFactory;

/**
 * A factory for creating ESLogMessage objects.
 * 
 * @author Tobias Verhoeven
 */
public final class LogMessageFactory implements ESLogMessageFactory {

	/** The factory INSTANCE. */
	public static final LogMessageFactory INSTANCE = new LogMessageFactory();

	private LogMessageFactory() {
	}

	/**
	 * creates a new logMessage with the specified message and author.
	 * 
	 * @param message the message
	 * @param author the author
	 * @return the log message
	 */
	public ESLogMessage createLogMessage(String message, String author) {
		final LogMessage logMessage = VersioningFactory.eINSTANCE.createLogMessage();
		logMessage.setMessage(message);
		logMessage.setAuthor(author);
		logMessage.setClientDate(Calendar.getInstance().getTime());
		return logMessage;
	}
}