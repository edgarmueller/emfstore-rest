/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Card Pfeiffer - initial API and implementation
 * Edgar Mueller - renaming & javadoc, API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.exceptions;

/**
 * Thrown in case problems arise with the KeyStoreManager.
 * 
 * @author pfeifferc
 * @author emueller
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class ESCertificateException extends Exception {

	private static final long serialVersionUID = -6870075780570775081L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the detailed error message
	 * @param cause
	 *            the cause for this exception
	 */
	public ESCertificateException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the detailed error message
	 */
	public ESCertificateException(String message) {
		super(message);
	}

}
