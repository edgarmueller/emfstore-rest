/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
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

/**
 * Represents a change package.
 * 
 * @author emueller
 * @author wesendon
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESChangePackage {

	/**
	 * Returns the {@link ESLogMessage} that was entered by the
	 * user and is supposed to describe the changes within
	 * the change package.
	 * 
	 * @return the log message as entered by the user
	 */
	ESLogMessage getLogMessage();

	/**
	 * Sets the log message of this change package.
	 * 
	 * @param logMessage
	 *            the log message to be set
	 */
	void setLogMessage(ESLogMessage logMessage);

}
