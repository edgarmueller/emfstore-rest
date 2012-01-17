package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.views.CreateProjectDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

public class UICreateProjectController extends AbstractEMFStoreUIController {

	public UICreateProjectController(Shell shell) {
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

}
