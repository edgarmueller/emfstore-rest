package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.RevertCommitController;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

public class UIRevertCommitController extends AbstractEMFStoreUIController {

	private final ProjectSpace projectSpace;
	private final PrimaryVersionSpec versionSpec;

	public UIRevertCommitController(Shell shell, final ProjectSpace projectSpace, final PrimaryVersionSpec versionSpec) {
		super(shell);
		this.projectSpace = projectSpace;
		this.versionSpec = versionSpec;
	}

	/**
	 * Reverts the commit from a certain revision in a local workspace that can be commited later.
	 * 
	 * @param versionSpec the version of the commit to revert
	 */
	public void revertCommit() {
		MessageDialog dialog = new MessageDialog(null, "Confirmation", null,
			"Do you really want to revert changes of this version on project " + projectSpace.getProjectName(),
			MessageDialog.QUESTION, new String[] { "Yes", "No" }, 0);
		int result = dialog.open();
		if (result == Window.OK) {
			try {
				new RevertCommitController(projectSpace, versionSpec).execute();
			} catch (EmfStoreException e) {
				MessageDialog.openError(getShell(), "Error",
					"An error occurred while revert the commit: " + e.getMessage());
			}
		}
	}
}
