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

import org.eclipse.emf.emfstore.common.model.IEMFStoreFactory;

/**
 * A factory for creating ILogMessage objects.
 * 
 * @author Tobias Verhoeven
 */
public interface ILogMessageFactory extends IEMFStoreFactory {

	/**
	 * creates a new logMessage with the specified message and author.
	 * 
	 * @param message the message
	 * @param author the author
	 * @return the log message
	 */
	ILogMessage createLogMessage(String message, String author);
}