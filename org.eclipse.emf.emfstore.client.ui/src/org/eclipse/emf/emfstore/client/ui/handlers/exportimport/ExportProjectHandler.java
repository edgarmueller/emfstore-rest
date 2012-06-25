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
package org.eclipse.emf.emfstore.client.ui.handlers.exportimport;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIExportController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler;

/**
 * Handler for exporting a {@link Project}.
 * 
 * @author emueller
 */
public class ExportProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIExportController(getShell()).exportProject(requireSelection(ProjectSpace.class));
	}
}
