/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.handlers;

import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICreateRemoteProjectController;

/**
 * Creates an empty project on a server.
 * 
 * @author emueller
 * 
 */
public class CreateProjectOnServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {

		ServerInfo serverInfo = requireSelection(ServerInfo.class);

		if (serverInfo == null || serverInfo.getLastUsersession() == null) {
			return;
		}

		new UICreateRemoteProjectController(getShell(), serverInfo.getLastUsersession().toAPI()).execute();
	}
}
