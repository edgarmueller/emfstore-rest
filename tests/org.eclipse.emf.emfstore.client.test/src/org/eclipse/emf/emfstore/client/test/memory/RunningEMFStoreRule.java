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
package org.eclipse.emf.emfstore.client.test.memory;

import java.io.IOException;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.rules.ExternalResource;

/**
 * The JUnit Rule for running an EMFStore.
 */
@SuppressWarnings("restriction")
public class RunningEMFStoreRule extends ExternalResource {

	private ESServer server;
	private ESUsersession session;
	private ESWorkspace workspace;

	/**
	 * Server.
	 * 
	 * @return the Server
	 */
	public ESServer server() {
		return server;
	}

	/**
	 * Default session.
	 * 
	 * @return the user-session.
	 */
	public ESUsersession defaultSession() {
		return session;
	}

	/**
	 * Connected workspace.
	 * 
	 * @return the connected workspace.
	 */
	public ESWorkspace connectedWorkspace() {
		return workspace;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.rules.ExternalResource#before()
	 */
	@Override
	protected void before() throws IOException, FatalESException, ESException {
		ServerConfiguration.setTesting(true);
		CommonUtil.setTesting(true);
		Configuration.getClientBehavior().setAutoSave(false);

		workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();
		server = ESServer.FACTORY.getServer("RunningEMFStoreRuleStore", "localhost", 8080,
			KeyStoreManager.DEFAULT_CERTIFICATE);
		server = workspace.addServer(server);

		startEMFStore();
		session = server.login("super", "super");
		((ESWorkspaceImpl) workspace).toInternalAPI().getUsersessions()
			.add(((ESUsersessionImpl) session).toInternalAPI());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.rules.ExternalResource#after()
	 */
	@Override
	protected void after() {
		stopEMFStore();
		((ESWorkspaceImpl) workspace).toInternalAPI().getUsersessions()
			.remove(((ESUsersessionImpl) session).toInternalAPI());
		workspace.removeServer(server);
	}

	private static void startEMFStore() {
		try {
			EMFStoreController.runAsNewThread();
		} catch (FatalESException e) {
			System.out.println(e.toString());
		}
	}

	private static void stopEMFStore() {
		EMFStoreController server = EMFStoreController.getInstance();
		if (server != null) {
			server.stop();
		}
		try {
			// give the server some time to unbind from it's ips. Not the nicest solution ...
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}

	}
}
