/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.cases;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.server.ESEMFStoreController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Actual server implementation meant to be used within tests.
 * 
 * @author emueller
 * 
 */
public class Server implements IServer {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.common.cases.IServer#startEMFStore(java.util.Map)
	 */
	public void startEMFStore(Map<String, String> properties) {
		ServerConfiguration.setTesting(true);
		ServerConfiguration.getProperties().setProperty(ServerConfiguration.XML_RPC_PORT,
			String.valueOf(ServerUtil.defaultPort()));
		try {
			ESEMFStoreController.startEMFStore();
		} catch (final FatalESException ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.common.cases.IServer#stopEMFStore()
	 */
	public void stopEMFStore() {
		ESEMFStoreController.stopEMFStore();
		// give the server some time to unbind from it's ips. Not the nicest solution ...
		try {
			Thread.sleep(10000);
		} catch (final InterruptedException ex) {
			fail(ex.getMessage());
		}
	}

	protected static void deleteRemoteProjects() throws IOException, FatalESException, ESException {
		for (final ESRemoteProject project : ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().get(0)
			.getRemoteProjects()) {
			project.delete(new NullProgressMonitor());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.common.cases.IServer#startEMFStore()
	 */
	public void startEMFStore() {
		startEMFStore(Collections.<String, String> emptyMap());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.common.cases.IServer#getHistory(org.eclipse.emf.emfstore.client.ESLocalProject)
	 */
	public ProjectHistory getHistory(ESLocalProject localProject) {
		final EMFStoreController server = EMFStoreController.getInstance();
		final EList<ProjectHistory> projects = server.getServerSpace().getProjects();
		for (final ProjectHistory projectHistory : projects) {
			if (projectHistory.getProjectId().equals(
				ESLocalProjectImpl.class.cast(localProject).toInternalAPI().getProjectId())) {
				return projectHistory;
			}
		}

		return null;
	}

}
