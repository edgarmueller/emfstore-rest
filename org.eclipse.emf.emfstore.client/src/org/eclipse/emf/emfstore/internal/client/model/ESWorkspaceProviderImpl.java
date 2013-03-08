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
package org.eclipse.emf.emfstore.internal.client.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.observer.ESWorkspaceInitObserver;
import org.eclipse.emf.emfstore.client.provider.ESEditingDomainProvider;
import org.eclipse.emf.emfstore.client.sessionprovider.ESAbstractSessionProvider;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPointException;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands.EMFStoreBasicCommandStack;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.AdminConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.SessionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.xmlrpc.XmlRpcAdminConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.xmlrpc.XmlRpcConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.common.ResourceFactoryRegistry;
import org.eclipse.emf.emfstore.internal.common.model.ModelVersion;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.FileUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.MalformedModelVersionException;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.observer.ObserverBus;
import org.eclipse.emf.emfstore.internal.migration.EMFStoreMigrationException;
import org.eclipse.emf.emfstore.internal.migration.EMFStoreMigratorUtil;

/**
 * Controller for workspaces. Workspace Manager is a singleton.
 * 
 * @author Maximilian Koegel
 * @generated NOT
 */
public final class ESWorkspaceProviderImpl implements ESWorkspaceProvider {

	private static ESWorkspaceProviderImpl instance;

	private Workspace currentWorkspace;
	private SessionManager sessionManager;
	private ObserverBus observerBus;

	private ConnectionManager connectionManager;
	private AdminConnectionManager adminConnectionManager;

	private ResourceSet resourceSet;

	/**
	 * Get an instance of the workspace manager. Will create an instance if no
	 * workspace manager is present.
	 * 
	 * @return the workspace manager singleton
	 * @generated NOT
	 */
	public static synchronized ESWorkspaceProviderImpl getInstance() {
		if (instance == null) {
			try {
				instance = new ESWorkspaceProviderImpl();
				instance.initialize();
				// BEGIN SUPRESS CATCH EXCEPTION
			} catch (RuntimeException e) {
				// END SURPRESS CATCH EXCEPTION
				ModelUtil.logException("Workspace Initialization failed, shutting down", e);
				throw e;
			}

			// init ecore packages
			CommonUtil.getAllModelElementEClasses();

			// notify post workspace observers
			instance.notifyPostWorkspaceInitiators();
		}
		return instance;
	}

	/**
	 * Initialize the Workspace Manager singleton.
	 */
	public static synchronized void init() {
		getInstance();
	}

	/**
	 * Default constructor.
	 * 
	 * @generated NOT
	 */
	private ESWorkspaceProviderImpl() {
	}

	private void initialize() {
		this.observerBus = new ObserverBus();
		this.connectionManager = initConnectionManager();
		this.adminConnectionManager = initAdminConnectionManager();
		this.sessionManager = new SessionManager();
		load();
	}

	private void notifyPostWorkspaceInitiators() {
		for (ESExtensionElement element : new ESExtensionPoint("org.eclipse.emf.emfstore.client.notify.postinit", true)
			.getExtensionElements()) {
			try {
				element.getClass("class", ESWorkspaceInitObserver.class).workspaceInitComplete(
					currentWorkspace
						.getAPIImpl());
			} catch (ESExtensionPointException e) {
				WorkspaceUtil.logException(e.getMessage(), e);
			}
		}
	}

	/**
	 * Initialize the connection manager of the workspace. The connection
	 * manager connects the workspace with the emf store.
	 * 
	 * @return the connection manager
	 * @generated NOT
	 */
	private ConnectionManager initConnectionManager() {
		KeyStoreManager.getInstance().setupKeys();
		// return new RMIConnectionManagerImpl();
		return new XmlRpcConnectionManager();
	}

	/**
	 * Initialize the connection manager of the workspace. The connection
	 * manager connects the workspace with the emf store.
	 * 
	 * @return the admin connection manager
	 * @generated NOT
	 */
	private AdminConnectionManager initAdminConnectionManager() {
		// return new RMIAdminConnectionManagerImpl();
		return new XmlRpcAdminConnectionManager();
	}

	/**
	 * (Re-)Initializes the workspace. Loads workspace from persistent storage
	 * if present. There is always one current Workspace.
	 */
	public void load() {

		// TODO: OTS removed dispose check

		resourceSet = new ResourceSetImpl();
		resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistry());
		((ResourceSetImpl) resourceSet).setURIResourceMap(new LinkedHashMap<URI, Resource>());
		resourceSet.getLoadOptions().putAll(ModelUtil.getResourceLoadOptions());

		// register an editing domain on the resource
		Configuration.getClientBehavior().setEditingDomain(createEditingDomain(resourceSet));

		URI fileURI = URI.createFileURI(Configuration.getFileInfo().getWorkspacePath());
		File workspaceFile = new File(Configuration.getFileInfo().getWorkspacePath());
		final Workspace workspace;
		final Resource resource;
		if (!workspaceFile.exists()) {

			workspace = createNewWorkspace(resourceSet, fileURI);

		} else {
			// file exists load it
			// check if a migration is needed
			migrateModel(resourceSet);

			// resource = resourceSet.getResource(fileURI, true);
			resource = resourceSet.createResource(fileURI);

			try {
				resource.load(ModelUtil.getResourceLoadOptions());
			} catch (IOException e) {
				WorkspaceUtil.logException("Error while loading workspace.", e);
			}

			EList<EObject> directContents = resource.getContents();
			// MK cast
			workspace = (Workspace) directContents.get(0);
		}

		workspace.setResourceSet(resourceSet);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				workspace.init();
			}
		}.run(true);

		currentWorkspace = workspace;

	}

	private EditingDomain createEditingDomain(ResourceSet resourceSet) {
		ESEditingDomainProvider domainProvider = getDomainProvider();
		if (domainProvider != null) {
			return domainProvider.getEditingDomain(resourceSet);
		} else {
			AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new EMFStoreBasicCommandStack(), resourceSet);
			resourceSet.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
			return domain;
		}
	}

	private ESEditingDomainProvider getDomainProvider() {
		// TODO EXPT PRIO
		return new ESExtensionPoint("org.eclipse.emf.emfstore.client.editingDomainProvider")
			.getClass("class",
				ESEditingDomainProvider.class);
	}

	private Workspace createNewWorkspace(ResourceSet resourceSet, URI fileURI) {
		final Workspace workspace;
		final Resource resource;
		// no workspace content found, create a workspace
		resource = resourceSet.createResource(fileURI);
		workspace = ModelFactory.eINSTANCE.createWorkspace();
		workspace.getServerInfos().addAll(Configuration.getClientBehavior().getDefaultServerInfos());
		EList<Usersession> usersessions = workspace.getUsersessions();
		for (ServerInfo serverInfo : workspace.getServerInfos()) {
			Usersession lastUsersession = serverInfo.getLastUsersession();
			if (lastUsersession != null) {
				usersessions.add(lastUsersession);
			}
		}

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				resource.getContents().add(workspace);
			}
		}.run(true);

		try {
			resource.save(ModelUtil.getResourceSaveOptions());
		} catch (IOException e) {
			WorkspaceUtil.logException(
				"Creating new workspace failed! Delete workspace folder: "
					+ Configuration.getFileInfo().getWorkspaceDirectory(), e);
		}
		int modelVersionNumber;
		try {
			modelVersionNumber = ModelUtil.getModelVersionNumber();
			stampCurrentVersionNumber(modelVersionNumber);
		} catch (MalformedModelVersionException e1) {
			WorkspaceUtil.logException("Loading model version failed!", e1);
		}
		return workspace;
	}

	private void stampCurrentVersionNumber(int modelReleaseNumber) {
		URI versionFileUri = URI.createFileURI(Configuration.getFileInfo().getModelReleaseNumberFileName());
		Resource versionResource = new ResourceSetImpl().createResource(versionFileUri);
		ModelVersion modelVersion = org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE
			.createModelVersion();
		modelVersion.setReleaseNumber(modelReleaseNumber);
		versionResource.getContents().add(modelVersion);
		try {
			ModelUtil.saveResource(versionResource, WorkspaceUtil.getResourceLogger());
		} catch (IOException e) {
			WorkspaceUtil.logException(
				"Version stamping workspace failed! Delete workspace folder: "
					+ Configuration.getFileInfo().getWorkspaceDirectory(),
				e);
		}
	}

	private void migrateModel(ResourceSet resourceSet) {
		ModelVersion workspaceModelVersion = getWorkspaceModelVersion();
		int modelVersionNumber;
		try {
			modelVersionNumber = ModelUtil.getModelVersionNumber();
			stampCurrentVersionNumber(modelVersionNumber);
		} catch (MalformedModelVersionException e1) {
			WorkspaceUtil.logException("Loading model version failed, migration skipped!", e1);
			return;
		}
		if (workspaceModelVersion.getReleaseNumber() == modelVersionNumber) {
			return;
		} else if (workspaceModelVersion.getReleaseNumber() > modelVersionNumber) {
			backupAndRecreateWorkspace(resourceSet);
			WorkspaceUtil.logException("Model conforms to a newer version, update client! New workspace was backuped!",
				new IllegalStateException());
			return;
		}

		// we need to migrate
		if (!EMFStoreMigratorUtil.isMigratorAvailable()) {
			WorkspaceUtil.logException("Model requires migration, but no migrators are registered!",
				new IllegalStateException());
			return;
		}

		backupWorkspace(false);
		File workspaceFile = new File(Configuration.getFileInfo().getWorkspaceDirectory());
		for (File file : workspaceFile.listFiles()) {
			if (file.getName().startsWith(Configuration.getFileInfo().getProjectSpaceDirectoryPrefix())) {
				String projectFilePath = file.getAbsolutePath() + File.separatorChar
					+ Configuration.getFileInfo().ProjectFolderName + File.separatorChar + 0
					+ Configuration.getFileInfo().ProjectFragmentExtension;
				URI projectURI = URI.createFileURI(projectFilePath);
				String operationsFilePath = null;
				File[] listFiles = file.listFiles();
				if (listFiles == null) {
					WorkspaceUtil.logException("The migration of the project in projectspace at " + projectFilePath
						+ " failed!", new IllegalStateException("Broken projectSpace!"));
					continue;
				}
				for (File subDirFile : listFiles) {
					if (subDirFile.getName().endsWith(Configuration.getFileInfo().LocalChangePackageExtension)) {
						operationsFilePath = subDirFile.getAbsolutePath();
					}
				}
				if (operationsFilePath == null) {
					WorkspaceUtil.logException("The migration of the project in projectspace at " + projectFilePath
						+ " failed!", new IllegalStateException("Broken workspace!"));
					backupAndRecreateWorkspace(resourceSet);
				}
				URI operationsURI = URI.createFileURI(operationsFilePath);
				try {
					migrate(projectURI, operationsURI, workspaceModelVersion.getReleaseNumber());
				} catch (EMFStoreMigrationException e) {
					WorkspaceUtil.logException("The migration of the project in projectspace at " + projectFilePath
						+ " failed!", e);
					backupAndRecreateWorkspace(resourceSet);
				}
			}
		}

		stampCurrentVersionNumber(modelVersionNumber);
	}

	public void migrate(String absoluteFilename) {
		URI projectURI = URI.createFileURI(absoluteFilename);

		List<URI> modelURIs = new ArrayList<URI>();
		modelURIs.add(projectURI);

		ModelVersion workspaceModelVersion = getWorkspaceModelVersion();
		if (!EMFStoreMigratorUtil.isMigratorAvailable()) {
			ModelUtil.logWarning("No Migrator available to migrate imported file");
			return;
		}

		try {
			EMFStoreMigratorUtil.getEMFStoreMigrator().migrate(modelURIs, workspaceModelVersion.getReleaseNumber() - 1,
				new NullProgressMonitor());
		} catch (EMFStoreMigrationException e) {
			WorkspaceUtil.logWarning("The migration of the project in the file " + absoluteFilename + " failed!", e);
		}
	}

	private void backupAndRecreateWorkspace(ResourceSet resourceSet) {
		backupWorkspace(true);
		URI fileURI = URI.createFileURI(Configuration.getFileInfo().getWorkspacePath());
		createNewWorkspace(resourceSet, fileURI);
	}

	private void backupWorkspace(boolean move) {
		String workspaceDirectory = Configuration.getFileInfo().getWorkspaceDirectory();
		File workspacePath = new File(workspaceDirectory);

		// TODO: if you want the date included in the backup folder you should
		// change the format. the default format
		// does not work with every os due to : and other characters.
		String newWorkspaceDirectory = Configuration.getFileInfo().getLocationProvider().getBackupDirectory()
			+ "emfstore_backup_"
			+ System.currentTimeMillis();

		File workspacebackupPath = new File(newWorkspaceDirectory);
		if (move) {
			workspacePath.renameTo(workspacebackupPath);
		} else {
			try {
				FileUtil.copyDirectory(workspacePath, workspacebackupPath);
			} catch (IOException e) {
				WorkspaceUtil.logException("Workspace backup failed!", e);
			}
		}
	}

	private ModelVersion getWorkspaceModelVersion() {
		// check for legacy workspace
		File versionFile = new File(Configuration.getFileInfo().getModelReleaseNumberFileName());
		if (!versionFile.exists()) {
			int modelVersionNumber;
			try {
				modelVersionNumber = ModelUtil.getModelVersionNumber();
				stampCurrentVersionNumber(modelVersionNumber);
			} catch (MalformedModelVersionException e1) {
				WorkspaceUtil.logException("Loading model version failed!", e1);
			}
		}

		// check if we need to migrate
		URI versionFileUri = URI.createFileURI(Configuration.getFileInfo().getModelReleaseNumberFileName());
		ResourceSet resourceSet = new ResourceSetImpl();
		try {
			Resource resource = resourceSet.getResource(versionFileUri, true);
			EList<EObject> directContents = resource.getContents();
			ModelVersion modelVersion = (ModelVersion) directContents.get(0);
			return modelVersion;
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			// resource can not be loaded, assume version number before
			// metamodel split
			ModelVersion modelVersion = org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE
				.createModelVersion();
			modelVersion.setReleaseNumber(4);
			return modelVersion;
		}
	}

	/**
	 * Migrate the model instance if neccessary.
	 * 
	 * @param projectURI
	 *            the uri of the project state
	 * @param changesURI
	 *            the uri of the local changes of the project state
	 * @param sourceModelReleaseNumber
	 * @throws EMFStoreMigrationException
	 */
	private void migrate(URI projectURI, URI changesURI, int sourceModelReleaseNumber)
		throws EMFStoreMigrationException {
		List<URI> modelURIs = new ArrayList<URI>();
		modelURIs.add(projectURI);
		modelURIs.add(changesURI);
		EMFStoreMigratorUtil.getEMFStoreMigrator().migrate(modelURIs, sourceModelReleaseNumber,
			new NullProgressMonitor());
	}

	/**
	 * Get the current workspace. There is always one current workspace.
	 * 
	 * @return the workspace
	 */
	public ESWorkspaceImpl getWorkspace() {
		return getInternalWorkspace().getAPIImpl();
	}

	public Workspace getInternalWorkspace() {

		if (currentWorkspace == null) {
			load();
		}

		return currentWorkspace;
	}

	/**
	 * Get the connection manager. Return the connection manager for this
	 * workspace.
	 * 
	 * @return the connectionManager
	 */
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	/**
	 * Set the connectionmanager.
	 * 
	 * @param manager
	 *            connection manager.
	 */
	public void setConnectionManager(ConnectionManager manager) {
		connectionManager = manager;
	}

	/**
	 * Get the admin connection manager. Return the admin connection manager for
	 * this workspace.
	 * 
	 * @return the connectionManager
	 */
	public AdminConnectionManager getAdminConnectionManager() {
		return adminConnectionManager;
	}

	/**
	 * Retrieve the project space for a model element.
	 * 
	 * @param modelElement
	 *            the model element
	 * @return the project space
	 */
	public static ProjectSpace getProjectSpace(EObject modelElement) {

		if (modelElement == null) {
			throw new IllegalArgumentException("The model element is null");
		} else if (modelElement instanceof ProjectSpace) {
			return (ProjectSpace) modelElement;
		}

		Project project = ModelUtil.getProject(modelElement);

		if (project == null) {
			throw new IllegalArgumentException("The model element " + modelElement + " has no project");
		}
		return getProjectSpace(project);
	}

	/**
	 * Retrieve the project space for a project.
	 * 
	 * @param project
	 *            the project
	 * @return the project space
	 */
	public static ProjectSpace getProjectSpace(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("The project is null");
		}
		// check if my container is a project space
		if (ModelPackage.eINSTANCE.getProjectSpace().isInstance(project.eContainer())) {
			return (ProjectSpace) project.eContainer();
		} else {
			throw new IllegalStateException("Project is not contained by any project space");
		}
	}

	/**
	 * Returns the {@link ObserverBus}.
	 * 
	 * @return observer bus
	 */
	public static ObserverBus getObserverBus() {
		return getInstance().observerBus;
	}

	/**
	 * Returns the {@link SessionManager}.
	 * 
	 * @return session manager
	 */
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.ESDisposable#dispose()
	 */
	public void dispose() {
		// TODO: OTS
		if (currentWorkspace != null) {
			((WorkspaceImpl) currentWorkspace).dispose();
			currentWorkspace = null;
			// instance = null;
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.IReinitializable#isDisposed()
	 */
	public boolean isDisposed() {
		return currentWorkspace == null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see ESWorkspaceProvider#setSessionProvider(ESAbstractSessionProvider)
	 */
	public void setSessionProvider(ESAbstractSessionProvider sessionProvider) {
		getSessionManager().setSessionProvider(sessionProvider);
	}

}