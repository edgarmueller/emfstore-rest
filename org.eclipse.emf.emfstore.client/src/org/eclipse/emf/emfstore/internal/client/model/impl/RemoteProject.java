/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.impl;

import static org.eclipse.emf.emfstore.internal.common.ListUtil.copy;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.IUsersession;
import org.eclipse.emf.emfstore.client.model.observer.ESCheckoutObserver;
import org.eclipse.emf.emfstore.internal.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.DateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.model.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.IGlobalProjectId;
import org.eclipse.emf.emfstore.server.model.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.query.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ITagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.IVersionSpec;

/**
 * Represents a remote project that is located on a remote server.
 * 
 * @author wesendon
 * @author emueller
 */
public class RemoteProject implements ESRemoteProject {

	/**
	 * The current connection manager used to connect to the server(s).
	 */
	private final ProjectInfo projectInfo;
	private final ESServer server;

	/**
	 * Constructor.
	 * 
	 * @param server
	 *            the server the remote project is located on
	 * @param projectInfo
	 *            information about which project to access
	 */
	public RemoteProject(ESServer server, ProjectInfo projectInfo) {
		this.server = server;
		this.projectInfo = projectInfo;
	}

	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}

	public IGlobalProjectId getGlobalProjectId() {
		return projectInfo.getProjectId();
	}

	public String getProjectName() {
		return projectInfo.getName();
	}

	public String getProjectDescription() {
		return projectInfo.getDescription();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#getBranches(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<IBranchInfo> getBranches(IProgressMonitor monitor) throws EMFStoreException {
		return new UnknownEMFStoreWorkloadCommand<List<IBranchInfo>>(monitor) {
			@Override
			public List<IBranchInfo> run(IProgressMonitor monitor) throws EMFStoreException {
				return copy(new ServerCall<List<BranchInfo>>(server) {
					@Override
					protected List<BranchInfo> run() throws EMFStoreException {
						final ConnectionManager connectionManager = WorkspaceProvider.getInstance()
							.getConnectionManager();
						return connectionManager.getBranches(getSessionId(), (ProjectId) getGlobalProjectId());
					};
				}.execute());
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#getBranches(org.eclipse.emf.emfstore.client.IUsersession,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<IBranchInfo> getBranches(IUsersession usersession, IProgressMonitor monitor) throws EMFStoreException {
		return copy(new ServerCall<List<BranchInfo>>(server) {
			@Override
			protected List<BranchInfo> run() throws EMFStoreException {
				final ConnectionManager cm = WorkspaceProvider.getInstance().getConnectionManager();
				return cm.getBranches(getSessionId(), (ProjectId) getGlobalProjectId());
			};
		}.execute());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#resolveVersionSpec(org.eclipse.emf.emfstore.server.model.versionspec.IVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public PrimaryVersionSpec resolveVersionSpec(final IVersionSpec versionSpec, IProgressMonitor monitor)
		throws EMFStoreException {
		return new ServerCall<PrimaryVersionSpec>(server, monitor) {
			@Override
			protected PrimaryVersionSpec run() throws EMFStoreException {
				return getConnectionManager().resolveVersionSpec(getSessionId(), (ProjectId) getGlobalProjectId(),
					(VersionSpec) versionSpec);
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#resolveVersionSpec(org.eclipse.emf.emfstore.client.IUsersession,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.IVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public PrimaryVersionSpec resolveVersionSpec(IUsersession session, final IVersionSpec versionSpec,
		IProgressMonitor monitor) throws EMFStoreException {
		return new ServerCall<PrimaryVersionSpec>(session) {
			@Override
			protected PrimaryVersionSpec run() throws EMFStoreException {
				return getConnectionManager().resolveVersionSpec(getSessionId(), (ProjectId) getGlobalProjectId(),
					(VersionSpec) versionSpec);
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#getHistoryInfos(org.eclipse.emf.emfstore.server.model.query.IHistoryQuery,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<IHistoryInfo> getHistoryInfos(final IHistoryQuery query, IProgressMonitor monitor)
		throws EMFStoreException {
		return copy(new ServerCall<List<HistoryInfo>>(server, monitor) {
			@Override
			protected List<HistoryInfo> run() throws EMFStoreException {
				return getConnectionManager().getHistoryInfo(getSessionId(), (ProjectId) getGlobalProjectId(),
					(HistoryQuery) query);
			}
		}.execute());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#getHistoryInfos(org.eclipse.emf.emfstore.client.IUsersession,
	 *      org.eclipse.emf.emfstore.server.model.query.IHistoryQuery, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<IHistoryInfo> getHistoryInfos(IUsersession usersession, final IHistoryQuery query,
		IProgressMonitor monitor) throws EMFStoreException {
		return copy(new ServerCall<List<HistoryInfo>>(usersession, monitor) {
			@Override
			protected List<HistoryInfo> run() throws EMFStoreException {
				return getConnectionManager().getHistoryInfo(getUsersession().getSessionId(),
					(ProjectId) getGlobalProjectId(), (HistoryQuery) query);
			}
		}.execute());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#addTag(org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ITagVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void addTag(final IPrimaryVersionSpec versionSpec, final ITagVersionSpec tag, IProgressMonitor monitor)
		throws EMFStoreException {
		new ServerCall<Void>(server, monitor) {
			@Override
			protected Void run() throws EMFStoreException {
				getConnectionManager().addTag(getUsersession().getSessionId(), (ProjectId) getGlobalProjectId(),
					(PrimaryVersionSpec) versionSpec, (TagVersionSpec) tag);
				return null;
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#removeTag(org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ITagVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void removeTag(final IPrimaryVersionSpec versionSpec, final ITagVersionSpec tag, IProgressMonitor monitor)
		throws EMFStoreException {
		new ServerCall<Void>(server, monitor) {
			@Override
			protected Void run() throws EMFStoreException {
				getConnectionManager().removeTag(getUsersession().getSessionId(), (ProjectId) getGlobalProjectId(),
					(PrimaryVersionSpec) versionSpec, (TagVersionSpec) tag);
				return null;
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#checkout(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ProjectSpace checkout(final IProgressMonitor monitor) throws EMFStoreException {
		return new ServerCall<ProjectSpace>(server) {
			@Override
			protected ProjectSpace run() throws EMFStoreException {
				return checkout(getUsersession(), monitor);
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#checkout(org.eclipse.emf.emfstore.client.IUsersession,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ProjectSpace checkout(IUsersession usersession, IProgressMonitor monitor) throws EMFStoreException {
		PrimaryVersionSpec targetSpec = resolveVersionSpec(usersession, Versions.createHEAD(), monitor);
		return checkout(usersession, targetSpec, monitor);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#checkout(org.eclipse.emf.emfstore.client.IUsersession,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.IVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ProjectSpace checkout(IUsersession usersession, IVersionSpec versionSpec, IProgressMonitor progressMonitor)
		throws EMFStoreException {

		SubMonitor parent = SubMonitor.convert(progressMonitor, "Checkout", 100);
		final Usersession session = (Usersession) usersession;

		// FIXME: MK: hack: set head version manually because esbrowser does not
		// update
		// revisions properly
		final ProjectInfo projectInfoCopy = ModelUtil.clone(projectInfo);
		projectInfoCopy.setVersion((PrimaryVersionSpec) versionSpec);

		// get project from server
		Project project = null;

		SubMonitor newChild = parent.newChild(40);
		parent.subTask("Fetching project from server...");
		project = new UnknownEMFStoreWorkloadCommand<Project>(newChild) {
			@Override
			public Project run(IProgressMonitor monitor) throws EMFStoreException {
				return new ServerCall<Project>(session) {
					@Override
					protected Project run() throws EMFStoreException {
						return getConnectionManager().getProject(session.getSessionId(), projectInfo.getProjectId(),
							projectInfoCopy.getVersion());
					}
				}.execute();
			}
		}.execute();

		if (project == null) {
			throw new EMFStoreException("Server returned a null project!");
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
		// TODO: OTS cast
		projectSpace.initResources(((WorkspaceBase) WorkspaceProvider.INSTANCE.getWorkspace()).getResourceSet());
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
				sourceSpec = this.resolveVersionSpec(session, dateVersionSpec, progressMonitor);
			} catch (InvalidVersionSpecException e) {
				sourceSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
				sourceSpec.setIdentifier(0);
			}
			ModelUtil.saveResource(projectSpace.eResource(), WorkspaceUtil.getResourceLogger());

		} catch (EMFStoreException e) {
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
		// TODO: OTS cast
		((WorkspaceBase) WorkspaceProvider.INSTANCE.getWorkspace()).addProjectSpace(projectSpace);
		// TODO: OTS save
		((WorkspaceBase) WorkspaceProvider.INSTANCE.getWorkspace()).save();
		WorkspaceProvider.getObserverBus().notify(ESCheckoutObserver.class).checkoutDone(projectSpace);
		parent.worked(10);
		parent.done();

		return projectSpace;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#delete(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(IProgressMonitor monitor) throws EMFStoreException {
		getDeleteProjectServerCall().setProgressMonitor(monitor).setServer(server).execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#delete(org.eclipse.emf.emfstore.client.IUsersession,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(IUsersession usersession, IProgressMonitor monitor) throws EMFStoreException {
		getDeleteProjectServerCall().setProgressMonitor(monitor).setUsersession(usersession).execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#getServer()
	 */
	public ESServer getServer() {
		return (ServerInfo) projectInfo.eContainer();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#getHeadVersion(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public PrimaryVersionSpec getHeadVersion(IProgressMonitor monitor) throws EMFStoreException {
		return resolveVersionSpec(Versions.createHEAD(), monitor);
	}

	private ServerCall<Void> getDeleteProjectServerCall() {
		return new ServerCall<Void>() {
			@Override
			protected Void run() throws EMFStoreException {
				new UnknownEMFStoreWorkloadCommand<Void>(getProgressMonitor()) {
					@Override
					public Void run(IProgressMonitor monitor) throws EMFStoreException {
						getConnectionManager().deleteProject(getSessionId(), projectInfo.getProjectId(), true);
						return null;
					}
				}.execute();

				getServer().getRemoteProjects().remove(this);
				return null;
			}
		};
	}
}
