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

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController;
import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ImportChangesController;
import org.eclipse.swt.widgets.Shell;

/**
 * UI-related import controller for changes.
 * 
 * @author emueller
 * 
 */
public class UIImportChangesController extends UIGenericExportImportController {

	private final ProjectSpace projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 * @param projectSpace
	 *            the {@link ProjectSpace} upon which to apply the imported changes
	 */
	public UIImportChangesController(Shell shell, ProjectSpace projectSpace) {
		super(shell);
		this.projectSpace = projectSpace;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.commands.handler.controller.importexport.UIGenericExportImportController#getController()
	 */
	@Override
	public IExportImportController getController() {
		return new ImportChangesController(projectSpace);
	}

}
