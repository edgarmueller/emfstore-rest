/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * pfeifferc
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.exceptions;

/**
 * ESCertificateStoreException is thrown when problems arise with the KeyStoreManager.
 * 
 * @author pfeifferc
 */
public class ESCertificateStoreException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message exception message
	 * @param cause reason for exception
	 */
	public ESCertificateStoreException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message exception message
	 */
	public ESCertificateStoreException(String message) {
		super(message);
	}

}
