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
package org.eclipse.emf.emfstore.server.model.api;

import java.util.Date;

/**
 * A LogMessage.
 */
public interface ILogMessage {

	/** Factory for creating ILogMessages. */
	ILogMessageFactory FACTORY = new ILogMessageFactory();

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	String getMessage();

	/**
	 * Gets the author.
	 * 
	 * @return the author
	 */
	String getAuthor();

	/**
	 * Gets the client date.
	 * 
	 * @return the client date
	 */
	Date getClientDate();

}
