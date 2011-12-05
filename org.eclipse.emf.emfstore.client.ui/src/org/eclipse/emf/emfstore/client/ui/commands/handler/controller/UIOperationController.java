package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class UIOperationController extends AbstractEMFStoreUIController {

	public UIOperationController(Shell shell) {
		super(shell);
	}

	public void revert(ProjectSpace projectSpace) {
		String message = "Do you really want to revert all your changes on project " + projectSpace.getProjectName();
		if (confirmationDialog(message)) {
			// BEGIN SUPRESS CATCH EXCEPTION
			try {
				getProgressMonitor().beginTask("Revert project...", 100);
				getProgressMonitor().worked(10);
				projectSpace.revert();
				MessageDialog.openInformation(null, "Revert", "Reverted project ");
				closeProgress();
			} catch (RuntimeException e) {
				handleException(e);
			}
		}
	}

	public void undoLastOperation(ProjectSpace projectSpace) {
		projectSpace.getOperationManager().undoLastOperation();
	}

}
