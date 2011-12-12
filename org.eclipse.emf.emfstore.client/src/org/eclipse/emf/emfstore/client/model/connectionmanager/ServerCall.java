package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.SessionId;

public abstract class ServerCall<U> {

	private ProjectSpaceImpl projectSpace;
	private Usersession usersession;
	private SessionId sessionId;
	private IProgressMonitor monitor;
	private U ret;

	public ServerCall() {
	}

	public ServerCall(Usersession usersession) {
		this.usersession = usersession;
	}

	public ServerCall(ProjectSpaceImpl projectSpace) {
		this.projectSpace = projectSpace;
	}

	public void setUsersession(Usersession usersession) {
		this.usersession = usersession;
	}

	public Usersession getUsersession() {
		return usersession;
	}

	protected ProjectSpaceImpl getProjectSpace() {
		return projectSpace;
	}

	public void setProgressMonitor(IProgressMonitor monitor) {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		this.monitor = monitor;
	}

	public IProgressMonitor getProgressMonitor() {
		return this.monitor;
	}

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
		ret = run();
	}

	abstract protected U run() throws EmfStoreException;

	public U execute() throws EmfStoreException {
		WorkspaceManager.getInstance().getSessionManager().execute(this);
		return ret;
	}
}
