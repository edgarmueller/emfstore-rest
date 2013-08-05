/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model;

import java.util.Date;

/**
 * A log message that is used to describe any changes done by a user.
 * 
 * @author emueller
 * @author wesendon
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESLogMessage {

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
