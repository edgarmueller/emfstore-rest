/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Julian Sommerfeldt - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.server;

import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;

/**
 * Controller to start and stop the EMFStore server.
 * 
 * @author jsommerfeldt
 * 
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public final class ESEMFStoreController {

	/**
	 * Private constructor.
	 */
	private ESEMFStoreController() {

	}

	/**
	 * Starts a new EMFStore server.
	 * 
	 * @throws FatalESException If a problem when starting occurs.
	 */
	public static void startEMFStore() throws FatalESException {
		EMFStoreController.runAsNewThread();
	}

	/**
	 * Stop the EMFStore server.
	 * 
	 * @return Was there a server?
	 */
	public static boolean stopEMFStore() {
		EMFStoreController server = EMFStoreController.getInstance();
		if (server != null) {
			server.stop();
			return true;
		}
		return false;
	}
}
