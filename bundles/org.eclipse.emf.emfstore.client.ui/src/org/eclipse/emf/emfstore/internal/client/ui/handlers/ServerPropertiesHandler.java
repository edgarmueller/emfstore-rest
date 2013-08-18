/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.handlers;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIEditServerPropertiesController;

/**
 * Shows the properties dialog for a selected server.<br/>
 * It is assumed that the user previously has selected a {@link ESServer} instance.
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
		new UIEditServerPropertiesController(
			getShell(),
			requireSelection(ESServer.class)).execute();
	}

}