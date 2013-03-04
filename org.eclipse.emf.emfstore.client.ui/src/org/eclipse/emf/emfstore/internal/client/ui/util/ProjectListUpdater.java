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
package org.eclipse.emf.emfstore.internal.client.ui.util;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.observer.ESLoginObserver;
import org.eclipse.emf.emfstore.client.observer.ESLogoutObserver;
import org.eclipse.emf.emfstore.client.observer.ESShareObserver;
import org.eclipse.emf.emfstore.client.observer.ESWorkspaceInitObserver;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * This class is responsible for keeping the workspace's project infos update to date.
 */
public class ProjectListUpdater implements ESWorkspaceInitObserver, ESShareObserver, ESLoginObserver, ESLogoutObserver {

	private ESWorkspace workspace;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESWorkspaceInitObserver#workspaceInitComplete(org.eclipse.emf.emfstore.client.ESWorkspace)
	 */
	public void workspaceInitComplete(ESWorkspace currentWorkspace) {
		this.workspace = currentWorkspace;
		ESWorkspaceProviderImpl.getObserverBus().register(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESLoginObserver#loginCompleted(org.eclipse.emf.emfstore.client.ESUsersession)
	 */
	public void loginCompleted(ESUsersession session) {
		try {
			update(session);
		} catch (ESException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't project infos upon loginCompleted.", e);
		}
		updateACUser(session);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESShareObserver#shareDone(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace)
	 */
	public void shareDone(ProjectSpace projectSpace) {
		try {
			update(projectSpace.getUsersession().getAPIImpl());
		} catch (ESException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't project infos upon shareDone.", e);
		}
	}

	private void updateACUser(ESUsersession session) {
		try {
			ESWorkspaceImpl w = (ESWorkspaceImpl) workspace;
			Usersession u = ((ESUsersessionImpl) session).getInternalAPIImpl();
			((WorkspaceBase) w.getInternalAPIImpl()).updateACUser(u);
		} catch (ESException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't update ACUser.", e);
		}
	}

	private void update(final ESUsersession session) throws ESException {
		RunInUI.WithException.run(new Callable<Void>() {
			public Void call() throws Exception {
				// throw new NotImplementedException("TODO OTS");
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESLogoutObserver#logoutCompleted(org.eclipse.emf.emfstore.client.ESUsersession)
	 */
	public void logoutCompleted(ESUsersession session) {
		// TODO OTS cast
		ServerInfo server = (ServerInfo) session.getServer();
		if (server != null) {
			server.getProjectInfos().clear();
		}
	}

}