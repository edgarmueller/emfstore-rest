package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.SessionId;

public abstract class ServerCall {

	private ProjectSpaceImpl projectSpace;
	private Usersession usersession;
	private SessionId sessionId;

	public ServerCall(ProjectSpaceImpl projectSpace) {
		this.projectSpace = projectSpace;
	}

	public Usersession getUsersession() {
		return usersession;
	}

	protected ProjectSpaceImpl getProjectSpace() {
		return projectSpace;
	}

	protected abstract void handleException(Exception e);

	protected ConnectionManager getConnectionManager() {
		return WorkspaceManager.getInstance().getConnectionManager();
	}

	protected SessionId getSessionId() {
		return sessionId;
	}

	public void setSessionId(SessionId sessionId) {
		this.sessionId = sessionId;
	}

	public void run(SessionId sessionId) throws EmfStoreException {
		setSessionId(sessionId);
		run();
	}

	abstract protected void run() throws EmfStoreException;

	public void execute() {
		WorkspaceManager.getInstance().getSessionManager().execute(this);
	}
}
