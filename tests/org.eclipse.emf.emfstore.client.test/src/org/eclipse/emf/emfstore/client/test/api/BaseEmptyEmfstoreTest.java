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
package org.eclipse.emf.emfstore.client.test.api;

import java.io.IOException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.Activator;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.ESEMFStoreController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class BaseEmptyEmfstoreTest {

	protected final static int port = 8080;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		startEMFStore();
	}

	protected static void startEMFStore() {
		ServerConfiguration.setTesting(true);
		ServerConfiguration.getProperties().setProperty(ServerConfiguration.XML_RPC_PORT, String.valueOf(port));
		try {
			ESEMFStoreController.startEMFStore();
		} catch (final FatalESException e) {
			log(e);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		stopEMFStore();
	}

	protected static void stopEMFStore() {
		ESEMFStoreController.stopEMFStore();
		try {
			// give the server some time to unbind from it's ips. Not the nicest solution ...
			Thread.sleep(10000);
		} catch (final InterruptedException e) {
			log(e);
		}
	}

	@After
	public void tearDown() throws Exception {
		deleteLocalProjects();
	}

	protected static void deleteLocalProjects() throws IOException, FatalESException, ESException {
		for (final ESLocalProject project : ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects()) {
			project.delete(new NullProgressMonitor());
		}
	}

	protected static void deleteRemoteProjects() throws IOException, FatalESException, ESException {
		for (final ESRemoteProject project : ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().get(0)
			.getRemoteProjects()) {
			project.delete(new NullProgressMonitor());
		}
	}

	protected static void log(Throwable e) {
		Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
	}
}
