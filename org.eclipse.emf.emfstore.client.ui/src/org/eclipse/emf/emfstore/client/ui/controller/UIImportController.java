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
package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.importexport.ExportImportControllerFactory;
import org.eclipse.emf.emfstore.client.ui.util.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller class for importing
 * <ul>
 * <li>projects</li>
 * <li>project spaces</li>
 * <li>changes</li>
 * <li>project history</li>
 * <li>a whole workspace</li>
 * </ul>
 * All import calls first open a file selection dialog that enables the user to select the
 * file for import.
 * 
 * @author emueller
 * 
 */
public class UIImportController {

	private final Shell shell;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent shell that is used during imports
	 */
	public UIImportController(Shell shell) {
		this.shell = shell;
	}

	/**
	 * Imports a project history.
	 * 
	 * @param projectInfo
	 *            the project info instance the imported history should get attached to
	 */
	public void importProjectHistory(ProjectInfo projectInfo) {
		try {
			new UIGenericExportImportController(shell,
				ExportImportControllerFactory.Import.getImportProjectHistoryController()).execute();
		} catch (EmfStoreException e) {
			EMFStoreMessageDialog.showExceptionDialog(shell, "Could not import project history", e);
		}
	}

	/**
	 * Imports a set of changes.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} the imported changes should get attached to
	 */
	public void importChanges(ProjectSpace projectSpace) {
		try {
			new UIGenericExportImportController(shell,
				ExportImportControllerFactory.Import.getImportChangesController(projectSpace)).execute();
		} catch (EmfStoreException e) {
			EMFStoreMessageDialog.showExceptionDialog(shell, "Could not import changes", e);
		}
	}

	/**
	 * Imports a project.
	 * 
	 * @param projectName
	 *            the name that will be assigned to the imported project
	 */
	public void importProject(String projectName) {
		try {
			new UIGenericExportImportController(shell,
				ExportImportControllerFactory.Import.getImportProjectController(projectName)).execute();
		} catch (EmfStoreException e) {
			EMFStoreMessageDialog.showExceptionDialog(shell, "Could not import project", e);
		}
	}

	/**
	 * Imports the given project space.
	 * 
	 * @param projectSpace
	 *            the project space that should get imported
	 */
	public void importProjectSpace(ProjectSpace projectSpace) {
		try {
			new UIGenericExportImportController(shell,
				ExportImportControllerFactory.Import.getImportProjectSpaceController(projectSpace)).execute();
		} catch (EmfStoreException e) {
			EMFStoreMessageDialog.showExceptionDialog(shell, "Could not import project space", e);
		}
	}
}
