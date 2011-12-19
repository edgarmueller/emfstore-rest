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
package org.eclipse.emf.emfstore.client.ui.commands.handler.handler.export;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.RequiredSelectionException;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.export.UIExportProjectController;

/**
 * Handler for export project menu item.
 * 
 * @author koegel
 */
public class ExportProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		try {
			new UIExportProjectController(getShell(), requireSelection(ProjectSpace.class)).export();
		} catch (RequiredSelectionException e) {
			// TODO:
			e.printStackTrace();
		}
	}
}
