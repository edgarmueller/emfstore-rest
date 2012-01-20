package org.eclipse.emf.emfstore.client.ui.controller;

import java.io.IOException;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.views.CreateProjectDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

public class UIProjectController extends AbstractEMFStoreUIController {

	public UIProjectController(Shell shell) {
		super(shell);
	}

	public ProjectSpace createLocalProject() {
		CreateProjectDialog dialog = new CreateProjectDialog(getShell());
		if (dialog.open() == Dialog.OK) {
			return createLocalProject(dialog.getName(), dialog.getDescription());
		}
		return null;
	}

	protected ProjectSpace createLocalProject(String name, String description) {
		return WorkspaceManager.getInstance().getCurrentWorkspace().createLocalProject(name, description);
	}

	public ProjectInfo createRemoteProject() throws EmfStoreException {
		return createRemoteProject(null);
	}

	public ProjectInfo createRemoteProject(Usersession usersession) throws EmfStoreException {
		CreateProjectDialog dialog = new CreateProjectDialog(getShell());
		if (dialog.open() == Dialog.OK) {
			return createRemoteProject(usersession, dialog.getName(), dialog.getDescription());
		}
		return null;
	}

	protected ProjectInfo createRemoteProject(Usersession usersession, String name, String description)
		throws EmfStoreException {
		return WorkspaceManager.getInstance().getCurrentWorkspace().createRemoteProject(usersession, name, description);
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

	public void deleteRemoteProject(ServerInfo serverInfo, ProjectId projectId, boolean deleteFiles)
		throws EmfStoreException {
		if (confirmationDialog("Do you really want to delete the remote project?")) {
			WorkspaceManager.getInstance().getCurrentWorkspace()
				.deleteRemoteProject(serverInfo, projectId, deleteFiles);
		}
	}

	public void deleteRemoteProject(Usersession usersession, ProjectId projectId, boolean deleteFiles)
		throws EmfStoreException {
		if (confirmationDialog("Do you really want to delete the remote project?")) {
			WorkspaceManager.getInstance().getCurrentWorkspace()
				.deleteRemoteProject(usersession, projectId, deleteFiles);
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
