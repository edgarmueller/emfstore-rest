/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Max Hohenegger (initial implementation)
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.exceptions;

import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;

/**
 * Is thrown if an operation is cancleded.
 * 
 * @author wesendon
 */
@SuppressWarnings("serial")
public class CancelOperationException extends EMFStoreException {

	/**
	 * Constructor.
	 * 
	 * @param message reason why this exception will be thrown
	 */
	public CancelOperationException(String message) {
		super(message);
	}

}