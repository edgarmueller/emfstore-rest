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
import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ImportControllers;
import org.eclipse.swt.widgets.Shell;

/**
 * UI-related import controller for {@link Project}s.
 * 
 * @author emueller
 */
public class UIImportProjectController extends UIGenericExportImportController {

	private final String projectName;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 * @param projectName
	 *            the name that should be assigned to the imported project
	 */
	public UIImportProjectController(Shell shell, String projectName) {
		super(shell);
		this.projectName = projectName;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.commands.handler.controller.importexport.UIGenericExportImportController#getController()
	 */
	@Override
	public IExportImportController getController() {
		return ImportControllers.getImportProjectController(projectName);
	}
}
