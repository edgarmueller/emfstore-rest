/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * ovonwesen
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.exceptions;

/**
 * Indicates that a required selection is missing.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class RequiredSelectionException extends RuntimeException {

	private static final long serialVersionUID = 3011252354930520148L;

	/**
	 * Default constructor.
	 */
	public RequiredSelectionException() {
		super("The selected element is invalid for this action.");
	}

	/**
	 * Constructor.
	 * 
	 * @param msg
	 *            detailed message why the selection is invalid
	 */
	public RequiredSelectionException(String msg) {
		super(msg);
	}
}
