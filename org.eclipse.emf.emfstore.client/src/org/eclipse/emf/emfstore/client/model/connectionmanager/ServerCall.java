package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.controller.CallbackInterface;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.SessionId;

public abstract class ServerCall<T extends CallbackInterface> {

	private ProjectSpaceImpl projectSpace;
	private Usersession usersession;
	private SessionId sessionId;
	private T callback;
	private IProgressMonitor monitor;

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

	protected void handleException(Exception e) {
		getCallBack().handleException(e);
	}

	public T getCallBack() {
		return callback;
	}

	public void setCallback(T callback) {
		this.callback = callback;
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
		run();
	}

	abstract protected void run() throws EmfStoreException;

	public void execute() {
		try {
			WorkspaceManager.getInstance().getSessionManager().execute(this);
		} catch (RuntimeException e) {
			handleException(e);
		}
	}
}
