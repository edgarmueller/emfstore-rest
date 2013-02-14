/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model;

import java.util.Date;

import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessageFactory;

/**
 * A log message that is used to describe any changes done by a user.
 * 
 * @author emueller
 * @author wesendon
 */
public interface ESLogMessage {

	/** Factory for creating ILogMessages. */
	ESLogMessageFactory FACTORY = LogMessageFactory.INSTANCE;

	/**
	 * Returns the actual log message.
	 * 
	 * @return the message
	 */
	String getMessage();

	/**
	 * Returns the author that wrote the log message.
	 * 
	 * @return the author of the log message
	 */
	String getAuthor();

	/**
	 * Gets the client date this log message was created.
	 * 
	 * @return the client date of the log message
	 */
	Date getClientDate();

}
