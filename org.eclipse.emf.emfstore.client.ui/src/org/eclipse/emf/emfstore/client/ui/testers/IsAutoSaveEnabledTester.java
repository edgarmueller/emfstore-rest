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
package org.eclipse.emf.emfstore.client.ui.testers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.emfstore.client.model.Configuration;

/**
 * Tests if auto save is enabled.
 * 
 * @author mkoegel
 * 
 */
public class IsAutoSaveEnabledTester extends PropertyTester {

	// TODO: think about a more elegant solution to prevent
	// unconditional contribution of the save button to the toolbar,
	// e.g. by means of parameters or the like
	private static boolean isAutoSaveEnabledTesterEnabled = true;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
	 *      java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		return (expectedValue != null && expectedValue.equals(Configuration.isAutoSaveEnabled()
			&& isAutoSaveEnabledTesterEnabled));
	}

	/**
	 * Whether the tester itself is enabled.
	 * The tester may be disabled, if the the contribution of the save button
	 * that is enabled when autosave is disabled, should be prevented.
	 * 
	 * @return whether the tester is enabled
	 */
	public static boolean isEnabled() {
		return isAutoSaveEnabledTesterEnabled;
	}

	/**
	 * Enables or disables the tester.
	 * 
	 * @param value
	 *            the enabled state of the tester
	 */
	public static void setEnabled(boolean value) {
		isAutoSaveEnabledTesterEnabled = value;
	}
}