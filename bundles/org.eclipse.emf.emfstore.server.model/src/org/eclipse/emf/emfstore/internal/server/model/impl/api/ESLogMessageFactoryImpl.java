/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.impl.api;

import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessageFactory;
import org.eclipse.emf.emfstore.server.model.ESLogMessage;
import org.eclipse.emf.emfstore.server.model.ESLogMessageFactory;

/**
 * Implementation of an {@link ESLogMessageFactory}.
 * 
 * @author emueller
 * 
 */
public class ESLogMessageFactoryImpl implements ESLogMessageFactory {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESLogMessageFactory#createLogMessage(java.lang.String,
	 *      java.lang.String)
	 */
	public ESLogMessage createLogMessage(String message, String author) {
		LogMessage logMessage = LogMessageFactory.INSTANCE.createLogMessage(message, author);
		return logMessage.toAPI();
	}

}
