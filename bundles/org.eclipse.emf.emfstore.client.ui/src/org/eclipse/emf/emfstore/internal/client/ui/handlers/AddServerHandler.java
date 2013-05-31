/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * ovonwesen
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.handlers;

import org.eclipse.emf.emfstore.internal.client.ui.controller.UIAddServerController;

/**
 * Handler for adding a server/repository.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class AddServerHandler extends AbstractEMFStoreHandler {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreHandler#handle()
	 */
	@Override
	public void handle() {
		new UIAddServerController(getShell()).execute();
	}

}
