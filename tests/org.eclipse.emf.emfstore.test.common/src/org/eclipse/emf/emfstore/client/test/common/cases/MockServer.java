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

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.exceptions.ESServerStartFailedException;
import org.eclipse.emf.emfstore.client.test.common.mocks.ServerMock;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.FileUtil;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Mock server implementation.
 * 
 * @author emueller
 * 
 */
public class MockServer implements IServer {

	private ServerMock server;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.common.cases.IServer#startEMFStore(java.util.Map)
	 */
	public void startEMFStore(Map<String, String> properties) {
		deleteServerHomeDirectory();
		ServerConfiguration.setTesting(true);

		try {
			server = ServerUtil.startMockServer(properties);
		} catch (final IllegalArgumentException ex) {
			fail(ex.getMessage());
		} catch (final ESServerStartFailedException ex) {
			fail(ex.getMessage());
		} catch (final FatalESException ex) {
			fail(ex.getMessage());
		}

	}

	protected void deleteRemoteProjects() throws IOException, FatalESException, ESException {
		for (final ESRemoteProject project : ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().get(0)
			.getRemoteProjects()) {
			project.delete(new NullProgressMonitor());
		}
	}

	private static void deleteServerHomeDirectory() {
		try {
			FileUtil.deleteDirectory(new File(
				ServerConfiguration.getServerHome()), true);
		} catch (final IOException ex) {
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

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.common.cases.IServer#startEMFStore()
	 */
	public void startEMFStore() {
		startEMFStore(Collections.<String, String> emptyMap());
	}

	public ProjectHistory getHistory(ESLocalProject localProject) {
		return server.getHistory(localProject);
	}
}
