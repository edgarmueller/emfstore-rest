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
package org.eclipse.emf.emfstore.internal.client.model.impl;

import static org.eclipse.emf.emfstore.internal.common.ListUtil.copy;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.internal.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.internal.client.model.AdminBroker;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.Workspace;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ProjectUrlResolutionException;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ServerUrlResolutionException;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnkownProjectException;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ExportProjectSpaceController;
import org.eclipse.emf.emfstore.internal.client.model.importexport.impl.ExportWorkspaceController;
import org.eclipse.emf.emfstore.internal.client.model.observers.DeleteProjectSpaceObserver;
import org.eclipse.emf.emfstore.internal.client.model.util.ResourceHelper;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.url.ProjectUrlFragment;
import org.eclipse.emf.emfstore.internal.server.model.url.ServerUrl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.ESGlobalProjectId;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

/**
 * Workspace space base class that contains custom user methods.
 * 
 * @author koegel
 * @author wesendon
 * @author emueller
 * 
 */
public abstract class WorkspaceBase extends EObjectImpl implements Workspace, IDisposable, DeleteProjectSpaceObserver {

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
	 * {@inheritDoc}
	 */
	public List<ESBranchInfo> getBranches(ESServer serverInfo, final ESGlobalProjectId projectId) throws EMFStoreException {
		return new ServerCall<List<ESBranchInfo>>((ServerInfo) serverInfo) {
			@Override
			protected List<ESBranchInfo> run() throws EMFStoreException {
				final ConnectionManager cm = WorkspaceProvider.getInstance().getConnectionManager();
				return (List<ESBranchInfo>) (List<?>) cm.getBranches(getSessionId(), (ProjectId) projectId);
			};
		}.execute();
	}

	public ProjectInfo createEmptyRemoteProject(final ESUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor progressMonitor) throws EMFStoreException {
		final ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
		final LogMessage log = VersioningFactory.eINSTANCE.createLogMessage();
		final Usersession session = (Usersession) usersession;
		log.setMessage("Creating project '" + projectName + "'");
		log.setAuthor(session.getUsername());
		log.setClientDate(new Date());
		ProjectInfo emptyProject = null;

		new UnknownEMFStoreWorkloadCommand<ProjectInfo>(progressMonitor) {
			@Override
			public ProjectInfo run(IProgressMonitor monitor) throws EMFStoreException {
				return connectionManager.createEmptyProject(session.getSessionId(), projectName, projectDescription,
					log);
			}
		}.execute();

		progressMonitor.worked(10);
		// TODO: OTS update remote project list
		// updateProjectInfos(session);
		return emptyProject;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#createLocalProject(java.lang.String,
	 *      java.lang.String)
	 */
	public ProjectSpace createLocalProject(String projectName, String projectDescription) {

		ProjectSpace projectSpace = ModelFactory.eINSTANCE.createProjectSpace();
		projectSpace.setProject(org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE.createProject());
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#createRemoteProject(org.eclipse.emf.emfstore.internal.client.model.ServerInfo,
	 *      java.lang.String, java.lang.String)
	 */
	public ProjectInfo createRemoteProject(ESServer serverInfo, final String projectName,
		final String projectDescription, final IProgressMonitor monitor) throws EMFStoreException {
		return new ServerCall<ProjectInfo>((ServerInfo) serverInfo) {
			@Override
			protected ProjectInfo run() throws EMFStoreException {
				return createEmptyRemoteProject(getUsersession(), projectName, projectDescription, monitor);
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#createRemoteProject(org.eclipse.emf.emfstore.internal.client.model.Usersession,
	 *      java.lang.String, java.lang.String)
	 */
	public ProjectInfo createRemoteProject(ESUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor monitor) throws EMFStoreException {
		return new ServerCall<ProjectInfo>((Usersession) usersession) {
			@Override
			protected ProjectInfo run() throws EMFStoreException {
				return createEmptyRemoteProject(getUsersession(), projectName, projectDescription, monitor);
			}
		}.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws EMFStoreException
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#deleteProjectSpace(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace)
	 */
	// TODO: OTS: move to ProjectSpaceBase
	public void deleteProjectSpace(ProjectSpace projectSpace) throws IOException, EMFStoreException {

		assert (projectSpace != null);

		// delete project to notify listeners
		projectSpace.getProject().delete();

		getProjectSpaces().remove(projectSpace);
		save();
		projectToProjectSpaceMap.remove(projectSpace.getProject());

		// TODO: OTS pass monitor in
		projectSpace.delete(new NullProgressMonitor());

		WorkspaceProvider.getObserverBus().notify(DeleteProjectSpaceObserver.class).projectSpaceDeleted(projectSpace);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#init(org.eclipse.emf.transaction.TransactionalEditingDomain)
	 */
	public void init() {
		projectToProjectSpaceMap = new LinkedHashMap<Project, ProjectSpace>();
		// initialize all projectSpaces
		for (ProjectSpace projectSpace : getProjectSpaces()) {
			projectSpace.init();
			projectToProjectSpaceMap.put(projectSpace.getProject(), projectSpace);
		}

		WorkspaceProvider.getObserverBus().register(this, DeleteProjectSpaceObserver.class);
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
		getServerInfos().clear();
		getUsersessions().clear();
		save();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#setWorkspaceResourceSet(org.eclipse.emf.ecore.resource.ResourceSet)
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#updateACUser(org.eclipse.emf.emfstore.internal.client.model.ServerInfo)
	 */
	public void updateACUser(ServerInfo serverInfo) throws EMFStoreException {
		new ServerCall<Void>(serverInfo) {
			@Override
			protected Void run() throws EMFStoreException {
				getUsersession().setACUser(getConnectionManager().resolveUser(getSessionId(), null));
				return null;
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#updateACUser(org.eclipse.emf.emfstore.internal.client.model.Usersession)
	 */
	public void updateACUser(Usersession usersession) throws EMFStoreException {
		new ServerCall<Void>(usersession) {
			@Override
			protected Void run() throws EMFStoreException {
				getUsersession().setACUser(getConnectionManager().resolveUser(getSessionId(), null));
				return null;
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESWorkspace#addServer(org.eclipse.emf.emfstore.client.ESServer)
	 */
	public void addServer(ESServer server) {
		getServerInfos().add((ServerInfo) server);
		save();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESWorkspace#removeServer(org.eclipse.emf.emfstore.client.ESServer)
	 */
	public void removeServer(ESServer server) {
		getServerInfos().remove(server);
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#importProject(java.lang.String)
	 */
	public ProjectSpace importProject(String absoluteFileName) throws IOException {
		Project project = ResourceHelper.getElementFromResource(absoluteFileName, Project.class, 0);
		return importProject(project, absoluteFileName.substring(absoluteFileName.lastIndexOf(File.separatorChar) + 1),
			"Imported from " + absoluteFileName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#importProjectSpace(java.lang.String)
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#resolve(org.eclipse.emf.emfstore.internal.server.model.url.ProjectUrlFragment)
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#resolve(org.eclipse.emf.emfstore.internal.server.model.url.ServerUrl)
	 */
	public Set<ServerInfo> resolve(ServerUrl serverUrl) throws ServerUrlResolutionException {
		Set<ServerInfo> result = new LinkedHashSet<ServerInfo>();
		for (ServerInfo serverInfo : getServerInfos()) {
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#resolveVersionSpec(org.eclipse.emf.emfstore.internal.client.model.Usersession,
	 *      org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec,
	 *      org.eclipse.emf.emfstore.internal.server.model.ProjectId)
	 */
	public ESPrimaryVersionSpec resolveVersionSpec(final ESUsersession usersession, final ESVersionSpec versionSpec,
		final ESGlobalProjectId projectId) throws EMFStoreException {
		return new ServerCall<ESPrimaryVersionSpec>((Usersession) usersession) {
			@Override
			protected PrimaryVersionSpec run() throws EMFStoreException {
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#save()
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#exportProjectSpace(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace,
	 *      java.io.File)
	 */
	public void exportProjectSpace(ProjectSpace projectSpace, File file) throws IOException {
		new ExportProjectSpaceController(projectSpace).execute(file, new NullProgressMonitor());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#exportProjectSpace(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace,
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#exportWorkSpace(java.io.File)
	 */
	public void exportWorkSpace(File file) throws IOException {
		new ExportWorkspaceController().execute(file, new NullProgressMonitor());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#exportWorkSpace(java.io.File,
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#getAdminBroker(org.eclipse.emf.emfstore.internal.client.model.ServerInfo)
	 */
	public AdminBroker getAdminBroker(final ServerInfo serverInfo) throws EMFStoreException, AccessControlException {
		return new ServerCall<AdminBroker>(serverInfo) {
			@Override
			protected AdminBroker run() throws EMFStoreException {
				return new AdminBrokerImpl(serverInfo, getSessionId());
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#getAdminBroker(org.eclipse.emf.emfstore.internal.client.model.Usersession)
	 */
	public AdminBroker getAdminBroker(final Usersession usersession) throws EMFStoreException, AccessControlException {
		return new ServerCall<AdminBroker>(usersession) {
			@Override
			protected AdminBroker run() throws EMFStoreException {
				return new AdminBrokerImpl(usersession.getServerInfo(), getSessionId());
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#getEditingDomain()
	 */
	public EditingDomain getEditingDomain() {
		return Configuration.getEditingDomain();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.Workspace#getProjectSpace(org.eclipse.emf.emfstore.internal.common.model.internal.common.model.Project)
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
	 * @see org.eclipse.emf.emfstore.client.ESWorkspace#getLocalProjects()
	 */
	public List<ESLocalProject> getLocalProjects() {
		return copy(getProjectSpaces());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESWorkspace#getServers()
	 */
	public List<ESServer> getServers() {
		return copy(getServerInfos());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.observers.DeleteProjectSpaceObserver#projectSpaceDeleted(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace)
	 */
	public void projectSpaceDeleted(ProjectSpace projectSpace) {
		assert (projectSpace != null);

		getProjectSpaces().remove(projectSpace);
		save();
		projectToProjectSpaceMap.remove(projectSpace.getProject());
	}

}