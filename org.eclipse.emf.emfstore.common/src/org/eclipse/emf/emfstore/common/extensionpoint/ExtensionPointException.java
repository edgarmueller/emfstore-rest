/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.extensionpoint;


/**
 * Exception thrown by the {@link ExtensionPoint} wrapper.
 * 
 * @author wesendon
 *
 */
public class ExtensionPointException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3805244685057014578L;

	/**
	 * Default constructor.
	 * 
	 * @param e causing exception
	 */
	public ExtensionPointException(Exception e) {
		super(e);
	}

	/**
	 * Default configuration.
	 * 
	 * @param string message
	 */
	public ExtensionPointException(String string) {
		super(string);
	}
}