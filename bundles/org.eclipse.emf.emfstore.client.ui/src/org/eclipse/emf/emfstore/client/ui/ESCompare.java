/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * groeber
 * jsommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui;

import org.eclipse.emf.ecore.EObject;

/**
 * Interface for extension point. This is used to register new comparators for
 * the history browser.
 * 
 * @author groeber
 * @author jsommerfeldt
 */
public interface ESCompare {
	/**
	 * Compares two EObjects to each other
	 * 
	 * @param e1
	 *            EObject one
	 * @param e2
	 *            EObject two
	 */
	public void compare(EObject e1, EObject e2);

	/**
	 * Displays the compare result. This method is called if {@link #compare(EObject e1, EObject e2)} has been called
	 * before and only then.
	 */
	public void display();
}
