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
package org.eclipse.emf.emfstore.client.model.observer;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.IObserver;

/**
 * Observer that notifies on new checkouts.
 * 
 * @author wesendon
 */
public interface ESCheckoutObserver extends IObserver {

	/**
	 * Called on checkout.
	 * 
	 * @param project
	 *            the checked out project
	 */
	void checkoutDone(ESLocalProject project);

}