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
package org.eclipse.emf.emfstore.client.test;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.IUsersession;
import org.eclipse.emf.emfstore.client.sessionprovider.AbstractSessionProvider;
import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.Workspace;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;

public class TestSessionProvider extends AbstractSessionProvider {

	private Usersession session;

	public TestSessionProvider() {

	}

	private void initSession() {
		// ServerInfo serverInfo = SetupHelper.getServerInfo();
		session = ModelFactory.eINSTANCE.createUsersession();
		// session.setServerInfo(serverInfo);
		session.setUsername("super");
		session.setPassword("super");
		session.setSavePassword(true);
		session.setServerInfo(SetupHelper.getServerInfo());

		Workspace currentWorkspace = (Workspace) WorkspaceProvider.INSTANCE.getWorkspace();
		// currentWorkspace.getServerInfos().add(serverInfo);
		currentWorkspace.getUsersessions().add(session);
		((WorkspaceBase) currentWorkspace).save();
	}

	@Override
	public IUsersession provideUsersession(ESServer serverInfo) throws EMFStoreException {
		if (session != null
			&& ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace()).getUsersessions().contains(session)) {
			return session;
		}

		if (serverInfo != null && serverInfo.getLastUsersession() != null) {
			return serverInfo.getLastUsersession();

		}

		if (session == null
			|| !((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace()).getUsersessions().contains(session)) {
			initSession();
		}

		return session;
	}

	@Override
	public void login(IUsersession usersession) throws EMFStoreException {
		// do nothing
		session.logIn();
	}

	public void clearSession() {
		if (session != null
			&& ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace()).getUsersessions().contains(session)) {
			((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace()).getUsersessions().remove(session);
			session = null;
		}
	}
}