package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import java.io.IOException;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class UIDeleteProjectController extends AbstractEMFStoreUIController {

	public UIDeleteProjectController(Shell shell) {
		setShell(shell);
	}

	public void deleteProject(ProjectSpace projectSpace) {
		try {

			if (!confirmation(projectSpace)) {
				return;
			}
			Workspace currentWorkspace = WorkspaceManager.getInstance().getCurrentWorkspace();
			currentWorkspace.deleteProjectSpace(projectSpace);
		} catch (IOException e) {
			handleException(e);
		}

	}

	protected boolean confirmation(ProjectSpace projectSpace) {
		String message = "Do you really want to delete your local copy of project \"" + projectSpace.getProjectName()
			+ "\n";
		if (projectSpace.getBaseVersion() != null) {
			message += " in version " + projectSpace.getBaseVersion().getIdentifier();
		}
		message += " ?";

		MessageDialog dialog = new MessageDialog(null, "Confirmation", null, message, MessageDialog.QUESTION,
			new String[] { "Yes", "No" }, 0);

		return dialog.open() == Dialog.OK;
	}
}
