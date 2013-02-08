/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.ui.controller.UIEditServerPropertiesController;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;

/**
 * Shows the properties dialog for a selected server.<br/>
 * It is assumed that the user previously has selected a {@link ServerInfo} instance.
 * 
 * @author emueller
 * 
 */
public class ServerPropertiesHandler extends AbstractEMFStoreHandler {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreHandler#handle()
	 */
	@Override
	public void handle() {
		new UIEditServerPropertiesController(getShell(), requireSelection(ServerInfo.class)).execute();
	}

}