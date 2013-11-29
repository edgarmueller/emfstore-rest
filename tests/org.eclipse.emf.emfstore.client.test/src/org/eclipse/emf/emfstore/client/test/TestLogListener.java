/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - intial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;

/**
 * Utility class that may be used to verify that a certain log message
 * has been logged.
 * 
 * @author emueller
 * 
 */
public class TestLogListener implements ILogListener {

	private final String expectedMessage;
	private boolean didReceive;

	/**
	 * Constructor.
	 * 
	 * @param expectedMessage
	 *            the message that is expected to be logged.
	 */
	public TestLogListener(String expectedMessage) {
		this.expectedMessage = expectedMessage;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.ILogListener#logging(org.eclipse.core.runtime.IStatus, java.lang.String)
	 */
	public void logging(IStatus status, String plugin) {
		if (status.getMessage().contains(expectedMessage)) {
			didReceive = true;
		}
	}

	/**
	 * Whether the listener received the exepected message.
	 * 
	 * @return {@code true}, if the listener did receive the expected message, {@code false} otherwise
	 */
	public boolean didReceive() {
		return didReceive;
	}
}