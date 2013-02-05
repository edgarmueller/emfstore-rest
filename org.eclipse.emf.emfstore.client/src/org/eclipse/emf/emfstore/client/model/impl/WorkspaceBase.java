/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.emfstore.client.api.IHistoryInfo;
import org.eclipse.emf.emfstore.client.api.IProject;
import org.eclipse.emf.emfstore.client.api.ServerInfo;
import org.eclipse.emf.emfstore.client.api.IUsersession;
import org.eclipse.emf.emfstore.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.client.model.AdminBroker;
import org.eclipse.emf.emfstore.client.model.Configuration;
import org.eclipse.emf.emfstore.client.model.ModelFactory;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.exceptions.ProjectUrlResolutionException;
import org.eclipse.emf.emfstore.client.model.exceptions.ServerUrlResolutionException;
import org.eclipse.emf.emfstore.client.model.exceptions.UnkownProjectException;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ExportProjectSpaceController;
import org.eclipse.emf.emfstore.client.model.importexport.impl.ExportWorkspaceController;
import org.eclipse.emf.emfstore.client.model.observers.CheckoutObserver;
import org.eclipse.emf.emfstore.client.model.observers.DeleteProjectSpaceObserver;
import org.eclipse.emf.emfstore.client.model.util.ResourceHelper;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.IProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.url.ProjectUrlFragment;
import org.eclipse.emf.emfstore.server.model.url.ServerUrl;
import org.eclipse.emf.emfstore.server.model.versioning.DateVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;

/**
 * Workspace space base class that contains custom user methods.
 * 
 * @author koegel
 * @author wesendon
 * @author emueller
 * 
 */
public abstract class WorkspaceBase extends EObjectImpl implements Workspace, IDisposable {

	/**
	 * The current connection manager used to connect to the server(s).
	 */
	private ConnectionManager connectionManager;

	/**
	 * A mapping between project and project spaces.
	 * 
	 * @generated NOT
	 */
	private Map<Project, ProjectSpace> projectToProjectSpaceMap;

	/**
	 * The resource set of the workspace.
	 * 
	 * @generated NOT
	 */
	private ResourceSet workspaceResourceSet;

	// BEGIN OF CUSTOM CODE
	/**
	 * Adds a new ProjectSpace to the workspace.
	 * 
	 * @param projectSpace
	 *            The project space to be added
	 */
	public void addProjectSpace(ProjectSpace projectSpace) {
		getProjectSpaces().add(projectSpace);
		projectToProjectSpaceMap.put(projectSpace.getProject(), projectSpace);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#checkout(org.eclipse.emf.emfstore.client.model.Usersession,
	 *      org.eclipse.emf.emfstore.server.model.ProjectInfo)
	 */
	public ProjectSpace checkout(final IUsersession usersession, final IProjectInfo projectInfo)
		throws EmfStoreException {
		return checkout(usersession, projectInfo, new NullProgressMonitor());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#checkout(org.eclipse.emf.emfstore.client.model.Usersession,
	 *      org.eclipse.emf.emfstore.server.model.ProjectInfo, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ProjectSpace checkout(final IUsersession usersession, final IProjectInfo projectInfo,
		IProgressMonitor progressMonitor) throws EmfStoreException {
		PrimaryVersionSpec targetSpec = this.connectionManager.resolveVersionSpec(
			((Usersession) usersession).getSessionId(), ((ProjectInfo) projectInfo).getProjectId(),
			Versions.createHEAD());
		return checkout(usersession, projectInfo, targetSpec, progressMonitor);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#checkout(org.eclipse.emf.emfstore.client.model.Usersession,
	 *      org.eclipse.emf.emfstore.server.model.ProjectInfo,
	 *      org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ProjectSpace checkout(final IUsersession usersession, final IProjectInfo projectInfo,
		IVersionSpec targetSpec, IProgressMonitor progressMonitor) throws EmfStoreException {

		SubMonitor parent = SubMonitor.convert(progressMonitor, "Checkout", 100);
		final Usersession session = (Usersession) usersession;
		final ProjectInfo info = (ProjectInfo) projectInfo;

		// FIXME: MK: hack: set head version manually because esbrowser does not
		// update
		// revisions properly
		final ProjectInfo projectInfoCopy = ModelUtil.clone(info);
		projectInfoCopy.setVersion((PrimaryVersionSpec) targetSpec);

		// get project from server
		Project project = null;

		SubMonitor newChild = parent.newChild(40);
		parent.subTask("Fetching project from server...");
		project = new UnknownEMFStoreWorkloadCommand<Project>(newChild) {
			@Override
			public Project run(IProgressMonitor monitor) throws EmfStoreException {
				return connectionManager.getProject(session.getSessionId(), info.getProjectId(),
					projectInfoCopy.getVersion());
			}
		}.execute();

		if (project == null) {
			throw new EmfStoreException("Server returned a null project!");
		}

		final PrimaryVersionSpec primaryVersionSpec = projectInfoCopy.getVersion();
		ProjectSpace projectSpace = ModelFactory.eINSTANCE.createProjectSpace();

		// initialize project space
		parent.subTask("Initializing Projectspace...");
		projectSpace.setProjectId(projectInfoCopy.getProjectId());
		projectSpace.setProjectName(projectInfoCopy.getName());
		projectSpace.setProjectDescription(projectInfoCopy.getDescription());
		projectSpace.setBaseVersion(primaryVersionSpec);
		projectSpace.setLastUpdated(new Date());
		projectSpace.setUsersession(session);
		WorkspaceProvider.getObserverBus().register((ProjectSpaceBase) projectSpace);
		projectSpace.setProject(project);
		projectSpace.setResourceCount(0);
		projectSpace.setLocalOperations(ModelFactory.eINSTANCE.createOperationComposite());
		parent.worked(20);

		// progressMonitor.subTask("Initializing resources...");
		projectSpace.initResources(this.workspaceResourceSet);
		parent.worked(10);

		// retrieve recent changes
		// TODO why are we doing this?? And why HERE?
		parent.subTask("Retrieving recent changes...");
		try {
			DateVersionSpec dateVersionSpec = VersioningFactory.eINSTANCE.createDateVersionSpec();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -10);
			dateVersionSpec.setDate(calendar.getTime());
			PrimaryVersionSpec sourceSpec;
			try {
				sourceSpec = this.connectionManager.resolveVersionSpec(session.getSessionId(),
					projectSpace.getProjectId(), dateVersionSpec);
			} catch (InvalidVersionSpecException e) {
				sourceSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
				sourceSpec.setIdentifier(0);
			}
			ModelUtil.saveResource(projectSpace.eResource(), WorkspaceUtil.getResourceLogger());

		} catch (EmfStoreException e) {
			WorkspaceUtil.logException(e.getMessage(), e);
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			WorkspaceUtil.logException(e.getMessage(), e);
		} catch (IOException e) {
			WorkspaceUtil.logException(e.getMessage(), e);
		}
		parent.worked(10);

		parent.subTask("Finishing checkout...");
		addProjectSpace(projectSpace);
		save();
		WorkspaceProvider.getObserverBus().notify(CheckoutObserver.class).checkoutDone(projectSpace);
		parent.worked(10);
		parent.done();

		return projectSpace;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IBranchInfo> getBranches(ServerInfo serverInfo, final IProjectId projectId) throws EmfStoreException {
		return new ServerCall<List<IBranchInfo>>((ServerInfo) serverInfo) {
			@Override
			protected List<IBranchInfo> run() throws EmfStoreException {
				final ConnectionManager cm = WorkspaceProvider.getInstance().getConnectionManager();
				return (List<IBranchInfo>) (List<?>) cm.getBranches(getSessionId(), (ProjectId) projectId);
			};
		}.execute();
	}

	public ProjectInfo createEmptyRemoteProject(final IUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor progressMonitor) throws EmfStoreException {
		final ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
		final LogMessage log = VersioningFactory.eINSTANCE.createLogMessage();
		final Usersession session = (Usersession) usersession;
		log.setMessage("Creating project '" + projectName + "'");
		log.setAuthor(session.getUsername());
		log.setClientDate(new Date());
		ProjectInfo emptyProject = null;

		new UnknownEMFStoreWorkloadCommand<ProjectInfo>(progressMonitor) {
			@Override
			public ProjectInfo run(IProgressMonitor monitor) throws EmfStoreException {
				return connectionManager.createEmptyProject(session.getSessionId(), projectName, projectDescription,
					log);
			}
		}.execute();

		progressMonitor.worked(10);
		updateProjectInfos(session);
		return emptyProject;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#createLocalProject(java.lang.String, java.lang.String)
	 */
	public ProjectSpace createLocalProject(String projectName, String projectDescription) {

		ProjectSpace projectSpace = ModelFactory.eINSTANCE.createProjectSpace();
		projectSpace.setProject(org.eclipse.emf.emfstore.common.model.ModelFactory.eINSTANCE.createProject());
		projectSpace.setProjectName(projectName);
		projectSpace.setProjectDescription(projectDescription);
		projectSpace.setLocalOperations(ModelFactory.eINSTANCE.createOperationComposite());

		projectSpace.initResources(getResourceSet());

		this.addProjectSpace(projectSpace);
		this.save();

		return projectSpace;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#createRemoteProject(org.eclipse.emf.emfstore.client.model.ServerInfo,
	 *      java.lang.String, java.lang.String)
	 */
	public IProjectInfo createRemoteProject(ServerInfo serverInfo, final String projectName,
		final String projectDescription, final IProgressMonitor monitor) throws EmfStoreException {
		return new ServerCall<IProjectInfo>((ServerInfo) serverInfo) {
			@Override
			protected IProjectInfo run() throws EmfStoreException {
				return createEmptyRemoteProject(getUsersession(), projectName, projectDescription, monitor);
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#createRemoteProject(org.eclipse.emf.emfstore.client.model.Usersession,
	 *      java.lang.String, java.lang.String)
	 */
	public IProjectInfo createRemoteProject(IUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor monitor) throws EmfStoreException {
		return new ServerCall<IProjectInfo>((Usersession) usersession) {
			@Override
			protected ProjectInfo run() throws EmfStoreException {
				return createEmptyRemoteProject(getUsersession(), projectName, projectDescription, monitor);
			}
		}.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#deleteProjectSpace(org.eclipse.emf.emfstore.client.model.ProjectSpace)
	 */
	public void deleteProjectSpace(ProjectSpace projectSpace) throws IOException {

		assert (projectSpace != null);

		// delete project to notify listeners
		projectSpace.getProject().delete();

		getProjectSpaces().remove(projectSpace);
		save();
		projectToProjectSpaceMap.remove(projectSpace.getProject());

		projectSpace.delete();

		WorkspaceProvider.getObserverBus().notify(DeleteProjectSpaceObserver.class).projectSpaceDeleted(projectSpace);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#deleteRemoteProject(org.eclipse.emf.emfstore.client.model.ServerInfo,
	 *      org.eclipse.emf.emfstore.server.model.ProjectId, boolean)
	 */
	public void deleteRemoteProject(ServerInfo serverInfo, final IProjectId projectId, final boolean deleteFiles)
		throws EmfStoreException {
		new ServerCall<Void>((ServerInfo) serverInfo) {
			@Override
			protected Void run() throws EmfStoreException {
				new UnknownEMFStoreWorkloadCommand<Void>(getProgressMonitor()) {
					@Override
					public Void run(IProgressMonitor monitor) throws EmfStoreException {
						getConnectionManager().deleteProject(getSessionId(), (ProjectId) projectId, deleteFiles);
						return null;
					}
				}.execute();

				updateProjectInfos(getUsersession());
				return null;
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#deleteRemoteProject(org.eclipse.emf.emfstore.client.model.Usersession,
	 *      org.eclipse.emf.emfstore.server.model.ProjectId, boolean)
	 */
	public void deleteRemoteProject(final IUsersession usersession, final IProjectId projectId,
		final boolean deleteFiles) throws EmfStoreException {
		new ServerCall<Void>((Usersession) usersession) {
			@Override
			protected Void run() throws EmfStoreException {
				getConnectionManager().deleteProject(getSessionId(), (ProjectId) projectId, deleteFiles);
				updateProjectInfos(getUsersession());
				return null;
			}
		}.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#init(org.eclipse.emf.transaction.TransactionalEditingDomain)
	 */
	public void init() {
		projectToProjectSpaceMap = new LinkedHashMap<Project, ProjectSpace>();
		// initialize all projectSpaces
		for (ProjectSpace projectSpace : getProjectSpaces()) {
			projectSpace.init();
			projectToProjectSpaceMap.put(projectSpace.getProject(), projectSpace);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.provider.IDisposable#dispose()
	 */
	public void dispose() {
		for (ProjectSpace projectSpace : getProjectSpaces()) {
			((ProjectSpaceBase) projectSpace).dispose();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#setConnectionManager(org.eclipse.emf.emfstore.client.model.connectionmanager.ConnectionManager)
	 */
	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#setWorkspaceResourceSet(org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	public void setResourceSet(ResourceSet resourceSet) {
		this.workspaceResourceSet = resourceSet;
		for (ProjectSpace projectSpace : getProjectSpaces()) {
			ProjectSpaceBase base = (ProjectSpaceBase) projectSpace;
			base.setResourceSet(workspaceResourceSet);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#updateACUser(org.eclipse.emf.emfstore.client.model.ServerInfo)
	 */
	public void updateACUser(ServerInfo serverInfo) throws EmfStoreException {
		new ServerCall<Void>(serverInfo) {
			@Override
			protected Void run() throws EmfStoreException {
				getUsersession().setACUser(getConnectionManager().resolveUser(getSessionId(), null));
				return null;
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#updateACUser(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	public void updateACUser(Usersession usersession) throws EmfStoreException {
		new ServerCall<Void>(usersession) {
			@Override
			protected Void run() throws EmfStoreException {
				getUsersession().setACUser(getConnectionManager().resolveUser(getSessionId(), null));
				return null;
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#updateProjectInfos(org.eclipse.emf.emfstore.client.model.ServerInfo)
	 */
	public void updateProjectInfos(final ServerInfo serverInfo) throws EmfStoreException {
		new ServerCall<Void>(serverInfo) {
			@Override
			protected Void run() throws EmfStoreException {
				return updateProjectInfos(serverInfo, getUsersession());
			}
		}.execute();
	}

	private Void updateProjectInfos(ServerInfo serverInfo, final Usersession usersession) {
		// BEGIN SUPRESS CATCH EXCEPTION
		try {
			serverInfo.getProjectInfos().clear();
			if (WorkspaceProvider.getInstance().getConnectionManager().isLoggedIn(usersession.getSessionId())) {
				((List<IProjectInfo>) (List<?>) serverInfo.getProjectInfos()).addAll(getRemoteProjectList(usersession));
			}
			save();
		} catch (EmfStoreException e) {
			WorkspaceUtil.logException(e.getMessage(), e);
		} catch (RuntimeException e) {
			WorkspaceUtil.logException(e.getMessage(), e);
		}
		// END SUPRESS CATCH EXCEPTION
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#updateProjectInfos(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	public void updateProjectInfos(final Usersession usersession) throws EmfStoreException {
		new ServerCall<Void>(usersession) {
			@Override
			protected Void run() throws EmfStoreException {
				return updateProjectInfos(usersession.getServerInfo(), usersession);
			}
		}.execute();
	}

	// END OF CUSTOM CODE

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#addServer(org.eclipse.emf.emfstore.client.model.ServerInfo)
	 */
	public void addServer(ServerInfo serverInfo) {
		getServers().add((ServerInfo) serverInfo);
		save();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#removeServer(org.eclipse.emf.emfstore.client.model.ServerInfo)
	 */
	public void removeServer(ServerInfo serverInfo) {
		getServers().remove(serverInfo);
		save();
	}

	// BEGIN OF CUSTOM CODE
	/**
	 * {@inheritDoc}
	 */
	public ResourceSet getResourceSet() {
		return this.workspaceResourceSet;
	}

	/**
	 * {@inheritDoc}
	 */
	public ProjectSpace importProject(Project project, String name, String description) {
		ProjectSpace projectSpace = ModelFactory.eINSTANCE.createProjectSpace();
		projectSpace.setProject(project);
		projectSpace.setProjectName(name);
		projectSpace.setProjectDescription(description);
		projectSpace.setLocalOperations(ModelFactory.eINSTANCE.createOperationComposite());

		projectSpace.initResources(this.workspaceResourceSet);

		addProjectSpace(projectSpace);
		this.save();

		return projectSpace;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#importProject(java.lang.String)
	 */
	public ProjectSpace importProject(String absoluteFileName) throws IOException {
		Project project = ResourceHelper.getElementFromResource(absoluteFileName, Project.class, 0);
		return importProject(project, absoluteFileName.substring(absoluteFileName.lastIndexOf(File.separatorChar) + 1),
			"Imported from " + absoluteFileName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#importProjectSpace(java.lang.String)
	 */
	public ProjectSpace importProjectSpace(String absoluteFileName) throws IOException {

		ProjectSpace projectSpace = ResourceHelper.getElementFromResource(absoluteFileName, ProjectSpace.class, 0);

		projectSpace.initResources(this.workspaceResourceSet);

		addProjectSpace(projectSpace);
		this.save();
		return projectSpace;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#resolve(org.eclipse.emf.emfstore.server.model.url.ProjectUrlFragment)
	 */
	public Set<ProjectSpace> resolve(ProjectUrlFragment projectUrlFragment) throws ProjectUrlResolutionException {
		Set<ProjectSpace> result = new LinkedHashSet<ProjectSpace>();
		for (ProjectSpace projectSpace : getProjectSpaces()) {
			if (projectSpace.getProjectId().equals(projectUrlFragment.getProjectId())) {
				result.add(projectSpace);
			}
		}
		if (result.size() == 0) {
			throw new ProjectUrlResolutionException();
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#resolve(org.eclipse.emf.emfstore.server.model.url.ServerUrl)
	 */
	public Set<ServerInfo> resolve(ServerUrl serverUrl) throws ServerUrlResolutionException {
		Set<ServerInfo> result = new LinkedHashSet<ServerInfo>();
		for (ServerInfo serverInfo : getServers()) {
			boolean matchingHostname = serverInfo.getUrl().equals(serverUrl.getHostName());
			boolean matchingPort = serverInfo.getPort() == serverUrl.getPort();
			if (matchingHostname && matchingPort) {
				result.add(serverInfo);
			}
		}
		if (result.size() == 0) {
			throw new ServerUrlResolutionException();
		}
		return result;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#resolveVersionSpec(org.eclipse.emf.emfstore.client.model.ServerInfo,
	 *      org.eclipse.emf.emfstore.server.model.versioning.VersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.ProjectId)
	 */
	public IPrimaryVersionSpec resolveVersionSpec(ServerInfo serverInfo, final IVersionSpec versionSpec,
		final IProjectId projectId) throws EmfStoreException {
		return new ServerCall<IPrimaryVersionSpec>((ServerInfo) serverInfo) {
			@Override
			protected PrimaryVersionSpec run() throws EmfStoreException {
				ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
				return connectionManager.resolveVersionSpec(getUsersession().getSessionId(), (ProjectId) projectId,
					(VersionSpec) versionSpec);
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#resolveVersionSpec(org.eclipse.emf.emfstore.client.model.Usersession,
	 *      org.eclipse.emf.emfstore.server.model.versioning.VersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.ProjectId)
	 */
	public IPrimaryVersionSpec resolveVersionSpec(final IUsersession usersession, final IVersionSpec versionSpec,
		final IProjectId projectId) throws EmfStoreException {
		return new ServerCall<IPrimaryVersionSpec>((Usersession) usersession) {
			@Override
			protected PrimaryVersionSpec run() throws EmfStoreException {
				ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
				return connectionManager.resolveVersionSpec(getSessionId(), (ProjectId) projectId,
					(VersionSpec) versionSpec);
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#save()
	 */
	public void save() {
		try {
			ModelUtil.saveResource(eResource(), ModelUtil.getResourceLogger());
		} catch (IOException e) {
			// MK Auto-generated catch block
			// FIXME OW MK: also insert code for dangling href handling here
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#exportProjectSpace(org.eclipse.emf.emfstore.client.model.ProjectSpace,
	 *      java.io.File)
	 */
	public void exportProjectSpace(ProjectSpace projectSpace, File file) throws IOException {
		new ExportProjectSpaceController(projectSpace).execute(file, new NullProgressMonitor());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#exportProjectSpace(org.eclipse.emf.emfstore.client.model.ProjectSpace,
	 *      java.io.File, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void exportProjectSpace(ProjectSpace projectSpace, File file, IProgressMonitor progressMonitor)
		throws IOException {
		new ExportProjectSpaceController(projectSpace).execute(file, progressMonitor);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#exportWorkSpace(java.io.File)
	 */
	public void exportWorkSpace(File file) throws IOException {
		new ExportWorkspaceController().execute(file, new NullProgressMonitor());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#exportWorkSpace(java.io.File,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void exportWorkSpace(File file, IProgressMonitor progressMonitor) throws IOException {
		new ExportWorkspaceController().execute(file, progressMonitor);
	}

	/**
	 * {@inheritDoc}<br/>
	 * <br/>
	 * This is to enable the workspace to be root of table views.
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 * @generated NOT
	 */
	public Object getAdapter(Class adapter) {
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getAdminBroker(org.eclipse.emf.emfstore.client.model.ServerInfo)
	 */
	public AdminBroker getAdminBroker(final ServerInfo serverInfo) throws EmfStoreException, AccessControlException {
		return new ServerCall<AdminBroker>(serverInfo) {
			@Override
			protected AdminBroker run() throws EmfStoreException {
				return new AdminBrokerImpl(serverInfo, getSessionId());
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getAdminBroker(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	public AdminBroker getAdminBroker(final Usersession usersession) throws EmfStoreException, AccessControlException {
		return new ServerCall<AdminBroker>(usersession) {
			@Override
			protected AdminBroker run() throws EmfStoreException {
				return new AdminBrokerImpl(usersession.getServerInfo(), getSessionId());
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getEditingDomain()
	 */
	public EditingDomain getEditingDomain() {
		return Configuration.getEditingDomain();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getHistoryInfo(org.eclipse.emf.emfstore.client.model.ServerInfo,
	 *      org.eclipse.emf.emfstore.server.model.ProjectId,
	 *      org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery)
	 */
	public List<IHistoryInfo> getHistoryInfo(ServerInfo serverInfo, final IProjectId projectId, final IHistoryQuery query)
		throws EmfStoreException {
		return new ServerCall<List<IHistoryInfo>>((ServerInfo) serverInfo) {
			@Override
			protected List<IHistoryInfo> run() throws EmfStoreException {
				ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
				return (List<IHistoryInfo>) (List<?>) connectionManager.getHistoryInfo(getUsersession().getSessionId(),
					(ProjectId) projectId, (HistoryQuery) query);
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getHistoryInfo(org.eclipse.emf.emfstore.client.model.Usersession,
	 *      org.eclipse.emf.emfstore.server.model.ProjectId,
	 *      org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery)
	 */
	public List<IHistoryInfo> getHistoryInfo(final IUsersession usersession, final IProjectId projectId,
		final IHistoryQuery query) throws EmfStoreException {
		return new ServerCall<List<IHistoryInfo>>((Usersession) usersession) {
			@Override
			protected List<IHistoryInfo> run() throws EmfStoreException {
				ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
				return (List<IHistoryInfo>) (List<?>) connectionManager.getHistoryInfo(getSessionId(),
					(ProjectId) projectId, (HistoryQuery) query);
			}
		}.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getProjectSpace(org.eclipse.emf.emfstore.common.model.Project)
	 */
	public ProjectSpace getProjectSpace(Project project) throws UnkownProjectException {
		ProjectSpace projectSpace = projectToProjectSpaceMap.get(project);
		if (projectSpace == null) {
			throw new UnkownProjectException();
		}
		return projectSpace;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getRemoteProjectList(org.eclipse.emf.emfstore.client.model.ServerInfo)
	 */
	public List<IProjectInfo> getRemoteProjectList(ServerInfo serverInfo) throws EmfStoreException {
		return new ServerCall<List<IProjectInfo>>((ServerInfo) serverInfo) {
			@Override
			protected List<IProjectInfo> run() throws EmfStoreException {
				return (List<IProjectInfo>) (List<?>) getConnectionManager().getProjectList(getSessionId());
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getRemoteProjectList(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	public List<IProjectInfo> getRemoteProjectList(IUsersession usersession) throws EmfStoreException {
		return new ServerCall<List<IProjectInfo>>((Usersession) usersession) {
			@Override
			protected List<IProjectInfo> run() throws EmfStoreException {
				return (List<IProjectInfo>) (List<?>) getConnectionManager().getProjectList(getSessionId());
			}
		}.execute();
	}

	public List<? extends IProject> getLocalProjects() {
		return getProjectSpaces();
	}
}