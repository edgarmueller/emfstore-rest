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

import org.apache.commons.lang.NotImplementedException;
import org.eclipse.emf.emfstore.client.IUsersession;
import org.eclipse.emf.emfstore.client.IWorkspace;
import org.eclipse.emf.emfstore.internal.client.model.PostWorkspaceInitiator;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.internal.client.model.observers.LoginObserver;
import org.eclipse.emf.emfstore.internal.client.model.observers.LogoutObserver;
import org.eclipse.emf.emfstore.internal.client.model.observers.ShareObserver;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;

/**
 * This class is responsible for keeping the workspace's project infos update to date.
 */
public class ProjectListUpdater implements PostWorkspaceInitiator, ShareObserver, LoginObserver, LogoutObserver {

	private IWorkspace workspace;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.PostWorkspaceInitiator#workspaceInitComplete(org.eclipse.emf.emfstore.client.IWorkspace)
	 */
	public void workspaceInitComplete(IWorkspace currentWorkspace) {
		this.workspace = currentWorkspace;
		WorkspaceProvider.getObserverBus().register(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.observers.LoginObserver#loginCompleted(org.eclipse.emf.emfstore.client.IUsersession)
	 */
	public void loginCompleted(IUsersession session) {
		try {
			update(session);
		} catch (EMFStoreException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't project infos upon loginCompleted.", e);
		}
		updateACUser(session);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.observers.ShareObserver#shareDone(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace)
	 */
	public void shareDone(ProjectSpace projectSpace) {
		try {
			update(projectSpace.getUsersession());
		} catch (EMFStoreException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't project infos upon shareDone.", e);
		}
	}

	private void updateACUser(IUsersession session) {
		try {
			((WorkspaceBase) workspace).updateACUser((Usersession) session);
		} catch (EMFStoreException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't update ACUser.", e);
		}
	}

	private void update(final IUsersession session) throws EMFStoreException {
		RunInUI.WithException.run(new Callable<Void>() {
			public Void call() throws Exception {
				throw new NotImplementedException("TODO OTS");
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.observers.LogoutObserver#logoutCompleted(org.eclipse.emf.emfstore.client.IUsersession)
	 */
	public void logoutCompleted(IUsersession session) {
		// TODO OTS cast
		ServerInfo server = (ServerInfo) session.getServer();
		if (server != null) {
			server.getProjectInfos().clear();
		}
	}

}