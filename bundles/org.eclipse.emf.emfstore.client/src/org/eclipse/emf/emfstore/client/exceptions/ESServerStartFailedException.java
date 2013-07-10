/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.exceptions;

/**
 * Represents a failure to start an EMFStore Server.
 * 
 * @author mkoegel
 * 
 */
public class ESServerStartFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 * 
	 * @param cause causing exception
	 */
	public ESServerStartFailedException(Throwable cause) {
		super("Server start failed!", cause);
	}
}
