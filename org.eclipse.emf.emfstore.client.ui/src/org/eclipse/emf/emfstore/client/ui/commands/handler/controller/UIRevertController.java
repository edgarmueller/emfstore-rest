package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

public class UIRevertController extends AbstractEMFStoreUIController {

	public UIRevertController(Shell shell) {
		super(shell);
	}

	public void revert(ProjectSpace projectSpace) {
		Boolean resultValue = false;
		MessageDialog dialog = new MessageDialog(null, "Confirmation", null,
			"Do you really want to revert all your changes on project " + projectSpace.getProjectName(),
			MessageDialog.QUESTION, new String[] { "Yes", "No" }, 0);
		int result = dialog.open();
		if (result == Window.OK) {
			getProgressMonitor().beginTask("Revert project...", 100);
			getProgressMonitor().worked(10);
			// BEGIN SUPRESS CATCH EXCEPTION
			try {
				projectSpace.revert();
				resultValue = true;
			} catch (RuntimeException e) {
				handleException(e);
			}
		}

		if (resultValue) {
			MessageDialog.openInformation(null, "Revert", "Reverted project ");
		}
	}
}
