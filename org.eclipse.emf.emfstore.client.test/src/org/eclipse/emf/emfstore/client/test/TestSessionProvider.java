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

import org.eclipse.emf.emfstore.internal.client.api.IServer;
import org.eclipse.emf.emfstore.internal.client.api.IUsersession;
import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.Workspace;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.AbstractSessionProvider;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;

public class TestSessionProvider extends AbstractSessionProvider {

	private Usersession session;

	public TestSessionProvider() {
		// ServerInfo serverInfo = SetupHelper.getServerInfo();
		session = ModelFactory.eINSTANCE.createUsersession();
		// session.setServerInfo(serverInfo);
		session.setUsername("super");
		session.setPassword("super");
		session.setSavePassword(true);

		Workspace currentWorkspace = (Workspace) WorkspaceProvider.getInstance().getWorkspace();
		// currentWorkspace.getServerInfos().add(serverInfo);
		currentWorkspace.getUsersessions().add(session);
		((WorkspaceBase) currentWorkspace).save();
	}

	@Override
	public IUsersession provideUsersession(IServer serverInfo) throws EMFStoreException {
		if (serverInfo != null && serverInfo.getLastUsersession() != null) {
			return serverInfo.getLastUsersession();

		}

		return session;
	}

	@Override
	public void login(IUsersession usersession) throws EMFStoreException {
		// do nothing
	}

}