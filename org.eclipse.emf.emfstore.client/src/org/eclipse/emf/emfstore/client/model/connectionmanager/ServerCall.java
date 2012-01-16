package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.SessionId;

/**
 * 
 * @author wesendon
 * @author emueller
 * 
 * @param <U> the return type
 */
public abstract class ServerCall<U> {

	private ProjectSpaceBase projectSpace;
	private Usersession usersession;
	private SessionId sessionId;
	private IProgressMonitor monitor;
	private U ret;
	private ServerInfo serverInfo;

	public ServerCall() {
	}

	public ServerCall(Usersession usersession) {
		this.usersession = usersession;
		setProgressMonitor(null);
	}

	public ServerCall(ProjectSpaceBase projectSpace) {
		this.projectSpace = projectSpace;
		setProgressMonitor(null);
	}

	public ServerCall(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
		setProgressMonitor(null);
	}

	public ServerCall(Usersession usersession, IProgressMonitor monitor) {
		this.usersession = usersession;
		setProgressMonitor(monitor);
	}

	public ServerCall(ProjectSpaceImpl projectSpace, IProgressMonitor monitor) {
		this.projectSpace = projectSpace;
		setProgressMonitor(monitor);
	}

	public ServerCall(ServerInfo serverInfo, IProgressMonitor monitor) {
		this.serverInfo = serverInfo;
		setProgressMonitor(monitor);
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	public void setUsersession(Usersession usersession) {
		this.usersession = usersession;
	}

	public Usersession getUsersession() {
		return usersession;
	}

	protected ProjectSpaceBase getProjectSpace() {
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
