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
package org.eclipse.emf.emfstore.client.ui.commands.handler.controller.importexport;

import org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController;
import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ExportControllers;
import org.eclipse.swt.widgets.Shell;

/**
 * UI-related controller for exporting a Workspace.
 * 
 * @author emueller
 */
public class UIExportWorkspaceController extends UIGenericExportImportController {

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 */
	public UIExportWorkspaceController(Shell shell) {
		super(shell);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.commands.handler.controller.importexport.UIGenericExportImportController#getController()
	 */
	@Override
	public IExportImportController getController() {
		return ExportControllers.getWorkspaceExportController();
	}
}
