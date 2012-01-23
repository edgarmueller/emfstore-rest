/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
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
 * This class is inteded to wrap all server calls. It can be done either by subclassing or using anonymous classes. The
 * {@link SessionManager} ensures there's a valid session before executing the call. For call of the
 * {@link ConnectionManager}, always use {@link #getSessionId()}, since it is injected by the {@link SessionManager}.
 * If initialized with an usersession, it will be used for the server class when the SessionProvider isn't extended by
 * the
 * user to change this behavior. Using serverInfo as an input will call the login dialog in the default implemenation.
 * Further, in the default implementation, when the {@link org.eclipse.emf.emfstore.client.model.ProjectSpace} is set,
 * it is checked whether it has an
 * usersession
 * attached to it.
 * 
 * @author wesendon
 * @author emueller
 * 
 * @param <U> the return type of the wrapped action
 */
public abstract class ServerCall<U> {

	private ProjectSpaceBase projectSpace;
	private Usersession usersession;
	private SessionId sessionId;
	private IProgressMonitor monitor;
	private U ret;
	private ServerInfo serverInfo;

	/**
	 * Default constructor.
	 */
	public ServerCall() {
	}

	/**
	 * Default constructor with usersession.
	 * 
	 * @param usersession preselected usersession
	 */
	public ServerCall(Usersession usersession) {
		this.usersession = usersession;
		setProgressMonitor(null);
	}

	/**
	 * Default constructor with projectspace.
	 * 
	 * @param projectSpace relevant projectspace if existent
	 */
	public ServerCall(ProjectSpaceBase projectSpace) {
		this.projectSpace = projectSpace;
		setProgressMonitor(null);
	}

	/**
	 * Default constructor with serverinfo.
	 * 
	 * @param serverInfo a given server
	 */
	public ServerCall(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
		setProgressMonitor(null);
	}

	/**
	 * Default constructor with usersession and progress monitor.
	 * 
	 * @param usersession preselected usersession
	 * @param monitor monitor
	 */
	public ServerCall(Usersession usersession, IProgressMonitor monitor) {
		this.usersession = usersession;
		setProgressMonitor(monitor);
	}

	/**
	 * Default constructor with projectspace and progress monitor.
	 * 
	 * @param projectSpace relevant projectspace if existent
	 * @param monitor monitor
	 */
	public ServerCall(ProjectSpaceImpl projectSpace, IProgressMonitor monitor) {
		this.projectSpace = projectSpace;
		setProgressMonitor(monitor);
	}

	/**
	 * Default constructor with serverinfo and progress monitor.
	 * 
	 * @param serverInfo a given serverInfo
	 * @param monitor monitor
	 */
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
