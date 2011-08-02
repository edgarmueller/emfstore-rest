package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.server.model.SessionId;

public class ServerCall {

	private final ProjectSpaceImpl projectSpace;

	public ServerCall(ProjectSpaceImpl projectSpace) {
		this.projectSpace = projectSpace;
	}

	protected SessionId getSessionId() {
		return getProjectSpace().getUsersession().getSessionId();
	}

	protected ProjectSpaceImpl getProjectSpace() {
		return projectSpace;
	}

	protected ConnectionManager getConnectionManager() {
		return WorkspaceManager.getInstance().getConnectionManager();
	}
}
