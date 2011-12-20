package org.eclipse.emf.emfstore.client.ui.commands.handler.handler.exportimport;

import java.util.Date;

import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.RequiredSelectionException;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.importexport.UIImportProjectController;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.PlatformUI;

public class ImportProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		String projectName = showProjectNameDialog("Imported project@" + new Date());
		if (projectName == null) {
			return;
		}
		try {
			// TODO
			new UIImportProjectController(getShell(), projectName).execute();
		} catch (RequiredSelectionException e) {
			// TODO:
			e.printStackTrace();
		}
	}

	/**
	 * Shows a dialog so that user can provide a name for imported project. Suggests a default name.
	 * 
	 * @param initialValue
	 * @return
	 */
	private String showProjectNameDialog(String initialValue) {

		int extensionIndex = initialValue.length();
		if (initialValue.contains(".")) {
			extensionIndex = initialValue.lastIndexOf(".");
		}
		if (initialValue.contains("@")) {
			extensionIndex = initialValue.lastIndexOf("@");
		}
		initialValue = initialValue.substring(0, extensionIndex);

		IInputValidator inputValidator = new IInputValidator() {
			public String isValid(String newText) {
				if (newText == null || newText.equals("") || newText.matches("\\s*")) {
					return "No project name provided!";
				}
				return null;
			}

		};
		InputDialog inputDialog = new InputDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
			"Project Name", "Please enter a name for the imported project:", initialValue, inputValidator);

		if (inputDialog.open() == Dialog.OK) {
			return inputDialog.getValue();
		} else {
			return null;
		}

	}

}
