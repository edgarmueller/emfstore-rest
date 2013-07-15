/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.extensionpoint;

/**
 * Exception thrown by the {@link ESExtensionPoint} wrapper.
 * 
 * @author wesendon
 * 
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public final class ESExtensionPointException extends RuntimeException {

	private static final long serialVersionUID = 3805244685057014578L;

	/**
	 * Default constructor.
	 * 
	 * @param exception causing exception
	 */
	public ESExtensionPointException(final Exception exception) {
		super(exception);
	}

	/**
	 * Default configuration.
	 * 
	 * @param message
	 *            the exception message
	 */
	public ESExtensionPointException(final String message) {
		super(message);
	}
}
