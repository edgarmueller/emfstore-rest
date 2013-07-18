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
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.observer;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * Observer that notifies on new checkouts.
 * 
 * @author wesendon
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESCheckoutObserver extends ESObserver {

	/**
	 * Called on checkout.
	 * 
	 * @param project
	 *            the checked out project
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void checkoutDone(ESLocalProject project);

}