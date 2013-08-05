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
package org.eclipse.emf.emfstore.client.exceptions;

/**
 * Represents an error that states that an server could not be found in the
 * {@link org.eclipse.emf.emfstore.client.ESWorkspace} .
 * 
 * @author ovonwesen
 * @author emueller
 * 
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class ESServerNotFoundException extends Exception {

	private static final long serialVersionUID = 5346143778435394717L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the detailed error message
	 */
	public ESServerNotFoundException(String message) {
		super(message);
	}
}
