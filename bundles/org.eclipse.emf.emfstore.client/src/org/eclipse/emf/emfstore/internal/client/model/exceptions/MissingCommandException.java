/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.exceptions;

/**
 * Thrown in case the <code>forceCommands</code> extension point option is set and a call
 * altering a model element managed by EMFStore is not wrapped in a command.
 * 
 * @author emueller
 */
public class MissingCommandException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param errorMsg
	 *            a detailed error message
	 */
	public MissingCommandException(String errorMsg) {
		super(errorMsg);
	}
}
