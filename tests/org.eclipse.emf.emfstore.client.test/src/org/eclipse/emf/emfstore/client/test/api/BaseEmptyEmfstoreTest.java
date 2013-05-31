/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.test.Activator;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.common.ResourceFactoryRegistry;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.exceptions.StorageException;
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
		} catch (FatalESException e) {
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
		} catch (InterruptedException e) {
			log(e);
		}
	}

	@After
	public void tearDown() throws Exception {
		deleteLocalProjects();
	}

	private static void deleteResources(String pathToMainFile) throws FatalESException {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistry());
		resourceSet.getLoadOptions().putAll(ModelUtil.getResourceLoadOptions());
		Resource resource = resourceSet.createResource(URI.createFileURI(pathToMainFile));
		try {
			resource.load(ModelUtil.getResourceLoadOptions());
		} catch (IOException e) {
			throw new FatalESException(StorageException.NOLOAD, e);
		}
		EcoreUtil.resolveAll(resource);
		List<Resource> loadedResources = new ArrayList<Resource>(resourceSet.getResources());
		for (Resource res : loadedResources) {
			try {
				res.delete(null);
			} catch (IOException e) {
				log(e);
			}
		}
	}

	private static void deleteFiles(String folderPath) {

		File workspaceDirectory = new File(folderPath);
		for (File file : workspaceDirectory.listFiles()) {
			if (file.isDirectory())
				deleteFiles(file.getAbsolutePath());
			file.delete();
		}
	}

	protected static void deleteLocalProjects() throws IOException, FatalESException, ESException {
		for (ESLocalProject project : ESWorkspaceProviderImpl.INSTANCE.getWorkspace().getLocalProjects()) {
			project.delete(new NullProgressMonitor());
		}
	}

	protected static void deleteRemoteProjects() throws IOException, FatalESException, ESException {
		for (ESRemoteProject project : ESWorkspaceProviderImpl.INSTANCE.getWorkspace().getServers().get(0)
			.getRemoteProjects()) {
			project.delete(new NullProgressMonitor());
		}
	}

	protected static void log(Throwable e) {
		Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
	}
}
