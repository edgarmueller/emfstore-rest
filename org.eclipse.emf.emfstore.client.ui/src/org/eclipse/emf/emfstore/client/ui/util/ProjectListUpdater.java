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
package org.eclipse.emf.emfstore.client.ui.util;

import org.eclipse.emf.emfstore.client.model.PostWorkspaceInitiator;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.observers.LoginObserver;
import org.eclipse.emf.emfstore.client.model.observers.LogoutObserver;
import org.eclipse.emf.emfstore.client.model.observers.ShareObserver;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThread;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class is responsible for keeping the workspace's project infos update to date.
 * 
 */
public class ProjectListUpdater implements PostWorkspaceInitiator, ShareObserver, LoginObserver, LogoutObserver {

	private Workspace workspace;
	private EmfStoreException exception;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.PostWorkspaceInitiator#workspaceInitComplete(org.eclipse.emf.emfstore.client.model.Workspace)
	 */
	public void workspaceInitComplete(Workspace currentWorkspace) {
		this.workspace = currentWorkspace;
		WorkspaceManager.getObserverBus().register(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.LoginObserver#loginCompleted(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	public void loginCompleted(Usersession session) {
		try {
			update(session);
		} catch (EmfStoreException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't project infos upon loginCompleted.", e);
		}
		updateACUser(session);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.ShareObserver#shareDone(org.eclipse.emf.emfstore.client.model.ProjectSpace)
	 */
	public void shareDone(ProjectSpace projectSpace) {
		try {
			update(projectSpace.getUsersession());
		} catch (EmfStoreException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't project infos upon shareDone.", e);
		}
	}

	private void updateACUser(Usersession session) {
		try {
			workspace.updateACUser(session);
		} catch (EmfStoreException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't update ACUser.", e);
		}
	}

	private void update(final Usersession session) throws EmfStoreException {
		exception = null;

		new RunInUIThread(Display.getDefault()) {

			@Override
			public Void doRun(Shell shell) {
				try {
					workspace.updateProjectInfos(session);
				} catch (EmfStoreException e) {
					exception = e;
				}
				return null;
			}
		}.execute();

		if (exception != null) {
			throw exception;
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.LogoutObserver#logoutCompleted(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	public void logoutCompleted(Usersession session) {
		ServerInfo serverInfo = session.getServerInfo();
		if (serverInfo != null) {
			serverInfo.getProjectInfos().clear();
		}
	}

}
