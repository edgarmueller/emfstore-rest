/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.model.util;

/**
 * Resource logger interface.
 * 
 * @author emueller
 */
public interface IResourceLogger {

	/**
	 * Logs a warning of a resource.
	 * 
	 * @param msg
	 *            the warning message being logged
	 */
	void logWarning(String msg);

	/**
	 * Logs a error of a resource.
	 * 
	 * @param msg
	 *            the error message being logged
	 */
	void logError(String msg);

}