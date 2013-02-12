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
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.client.IRemoteProject;
import org.eclipse.emf.emfstore.client.test.Activator;
import org.eclipse.emf.emfstore.client.test.TestSessionProvider;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.common.ResourceFactoryRegistry;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalEmfStoreException;
import org.eclipse.emf.emfstore.internal.server.exceptions.StorageException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class BaseEmptyEmfstoreTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		startEMFStore();
	}

	private static void startEMFStore() {
		ServerConfiguration.setTesting(true);
		try {
			EMFStoreController.runAsNewThread();
		} catch (FatalEmfStoreException e) {
			log(e);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		deleteLocalProjects();
		deleteRemoteProjects();
		stopEMFStore();
		((TestSessionProvider) WorkspaceProvider.getInstance().getSessionManager().getSessionProvider()).clearSession();
	}

	private static void stopEMFStore() {
		EMFStoreController server = EMFStoreController.getInstance();
		if (server != null) {
			server.stop();
		}
		try {
			// give the server some time to unbind from it's ips. Not the nicest solution ...
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			log(e);
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {

	}

	private static void deleteResources(String pathToMainFile) throws FatalEmfStoreException {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistry());
		resourceSet.getLoadOptions().putAll(ModelUtil.getResourceLoadOptions());
		Resource resource = resourceSet.createResource(URI.createFileURI(pathToMainFile));
		try {
			resource.load(ModelUtil.getResourceLoadOptions());
		} catch (IOException e) {
			throw new FatalEmfStoreException(StorageException.NOLOAD, e);
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

	protected static void deleteLocalProjects() throws IOException, FatalEmfStoreException, EMFStoreException {
		for (ILocalProject project : WorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects()) {
			project.delete(new NullProgressMonitor());
		}
	}

	protected static void deleteRemoteProjects() throws IOException, FatalEmfStoreException, EMFStoreException {
		for (IRemoteProject project : WorkspaceProvider.INSTANCE.getWorkspace().getServers().get(0).getRemoteProjects()) {
			project.delete(new NullProgressMonitor());
		}
	}

	protected static void log(Throwable e) {
		Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
	}
}
