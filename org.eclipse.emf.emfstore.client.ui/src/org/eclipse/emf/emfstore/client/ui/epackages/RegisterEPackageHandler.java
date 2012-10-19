/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.epackages;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler;

/**
 * RegisterEPackageHandler.
 * 
 * @author Tobias Verhoeven
 */
public class RegisterEPackageHandler extends AbstractEMFStoreHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler#handle()
	 */
	@Override
	public void handle() {
		ServerInfo serverInfo = requireSelection(ServerInfo.class);
		new UIRegisterEPackageController(getShell(), serverInfo).execute();
	}
}
