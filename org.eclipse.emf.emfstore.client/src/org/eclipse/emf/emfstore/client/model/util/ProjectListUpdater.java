package org.eclipse.emf.emfstore.client.model.util;

import org.eclipse.emf.emfstore.client.model.PostWorkspaceInitiator;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.observers.LoginObserver;
import org.eclipse.emf.emfstore.client.model.observers.ShareObserver;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class ProjectListUpdater implements PostWorkspaceInitiator, ShareObserver, LoginObserver {

	private Workspace workspace;

	public void workspaceInitComplete(Workspace currentWorkspace) {
		this.workspace = currentWorkspace;
	}

	public void loginCompleted(Usersession session) {
		update(session);
		updateACUser(session);
	}

	public void shareDone(ProjectSpace projectSpace) {
		update(projectSpace.getUsersession());
	}

	private void updateACUser(Usersession session) {
		try {
			workspace.updateACUser(session);
		} catch (EmfStoreException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't update ACUser.", e);
		}
	}

	private void update(Usersession session) {
		workspace.updateProjectInfos(session);
	}

}
