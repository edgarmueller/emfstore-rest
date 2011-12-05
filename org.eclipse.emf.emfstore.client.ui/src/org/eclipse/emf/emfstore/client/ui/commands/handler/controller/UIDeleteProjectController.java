package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import java.io.IOException;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.swt.widgets.Shell;

public class UIDeleteProjectController extends AbstractEMFStoreUIController {

	public UIDeleteProjectController(Shell shell) {
		super(shell);
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

		return confirmationDialog(message);
	}
}
