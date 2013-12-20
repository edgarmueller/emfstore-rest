/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.cases;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.ESEMFStoreController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Common base class for all tests which need an actual EMFStore server started.
 * 
 * @author emueller
 * 
 */
public abstract class ESTestWithServer extends ESTest {

	/**
	 * Start an EMFStore server instance.
	 */
	public static void startEMFStore() {
		ServerConfiguration.setTesting(true);
		ServerConfiguration.getProperties().setProperty(ServerConfiguration.XML_RPC_PORT,
			String.valueOf(ServerUtil.defaultPort()));
		try {
			ESEMFStoreController.startEMFStore();
		} catch (final FatalESException ex) {
			fail(ex.getMessage());
		}
	}

	public static void stopEMFStore() {
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

}
