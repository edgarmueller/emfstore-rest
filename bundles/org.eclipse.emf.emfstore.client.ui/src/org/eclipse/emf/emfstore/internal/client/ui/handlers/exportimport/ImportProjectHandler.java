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
package org.eclipse.emf.emfstore.internal.client.ui.handlers.exportimport;

import java.util.Date;

import org.eclipse.emf.emfstore.internal.client.ui.controller.UIImportController;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreHandler;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.PlatformUI;

/**
 * Handler for importing a project.
 * 
 * @author emueller
 * 
 */
public class ImportProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		String projectName = showProjectNameDialog("Imported project@" + new Date());
		if (projectName == null) {
			return;
		}
		new UIImportController(getShell()).importProject(projectName);
	}

	/**
	 * Shows a dialog so that the user can provide a name for the imported project.
	 * 
	 * @param initialProjectName the name of the project that should be shown when opening the dialog
	 * @return the entered project name
	 */
	private String showProjectNameDialog(String initialProjectName) {

		IInputValidator inputValidator = new IInputValidator() {
			public String isValid(String newText) {
				if (newText == null || newText.equals("") || newText.matches("\\s*")) {
					return "No project name provided!";
				}
				return null;
			}

		};

		InputDialog inputDialog = new InputDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
			"Project Name", "Please enter a name for the imported project:", initialProjectName, inputValidator);

		if (inputDialog.open() == Dialog.OK) {
			return inputDialog.getValue();
		} else {
			return null;
		}

	}

}
