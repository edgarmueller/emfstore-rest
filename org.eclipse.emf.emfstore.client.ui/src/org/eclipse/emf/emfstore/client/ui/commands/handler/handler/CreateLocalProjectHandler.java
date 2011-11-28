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
package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UICreateProjectController;

/**
 * This is a handler for new local project command. It shows new project dialog
 * and the user can create a new project with out being logged in to server.
 * 
 * @author hodaie
 * 
 */
public class CreateLocalProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public Object doExecute() {
		new UICreateProjectController(getShell()).createLocalProject();

		return null;
	}

}
