/*******************************************************************************
 * Copyright 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UILogoutSessionController;

/**
 * 
 * @author Edgar
 * 
 */
public class ChangeSessionHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UILogoutSessionController(getShell(), requireSelection(ServerInfo.class).getLastUsersession()).execute();
	}

}
