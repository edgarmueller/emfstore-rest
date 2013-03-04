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
package org.eclipse.emf.emfstore.internal.client.model.impl.api;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.observer.ESCheckoutObserver;
import org.eclipse.emf.emfstore.internal.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.Workspace;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.internal.client.model.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.common.ListUtil;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESBranchInfoImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESGlobalProjectIdImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESHistoryInfoImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.query.ESHistoryQueryImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESTagVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.DateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

/**
 * Represents a remote project that is located on a remote server.
 * 
 * @author wesendon
 * @author emueller
 */
public class ESRemoteProjectImpl implements ESRemoteProject {

	/**
	 * The current connection manager used to connect to the server(s).
	 */
	private final ProjectInfo projectInfo;
	private final ServerInfo server;

	/**
	 * Constructor.
	 * 
	 * @param serverInfo
	 *            the server the remote project is located on
	 * @param projectInfo
	 *            information about which project to access
	 */
	public ESRemoteProjectImpl(ServerInfo serverInfo, ProjectInfo projectInfo) {
		this.server = serverInfo;
		this.projectInfo = projectInfo;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#getGlobalProjectId()
	 */
	public ESGlobalProjectIdImpl getGlobalProjectId() {
		return projectInfo.getProjectId().getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#getProjectName()
	 */
	public String getProjectName() {
		return projectInfo.getName();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#getBranches(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<ESBranchInfo> getBranches(IProgressMonitor monitor) throws ESException {
		return new UnknownEMFStoreWorkloadCommand<List<ESBranchInfo>>(monitor) {
			@Override
			public List<ESBranchInfo> run(IProgressMonitor monitor) throws ESException {
				List<ESBranchInfoImpl> mapToInverse = ListUtil.mapToInverse((new ServerCall<List<BranchInfo>>(server) {
					@Override
					protected List<BranchInfo> run() throws ESException {
						final ConnectionManager connectionManager = WorkspaceProvider.getInstance()
							.getConnectionManager();
						return connectionManager.getBranches(
																getSessionId(),
																getGlobalProjectId().getInternalAPIImpl());
					};
				}.execute()));
				return ListUtil.copy(mapToInverse);
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#getBranches(org.eclipse.emf.emfstore.client.ESUsersession,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<ESBranchInfo> getBranches(ESUsersession usersession, IProgressMonitor monitor) throws ESException {
		List<ESBranchInfoImpl> mapToInverse = ListUtil.mapToInverse((new ServerCall<List<BranchInfo>>(server) {
			@Override
			protected List<BranchInfo> run() throws ESException {
				return getConnectionManager().getBranches(
															getSessionId(),
															getGlobalProjectId().getInternalAPIImpl());
			};
		}.execute()));
		return ListUtil.copy(mapToInverse);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#resolveVersionSpec(org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ESPrimaryVersionSpecImpl resolveVersionSpec(final ESVersionSpec versionSpec, IProgressMonitor monitor)
		throws ESException {

		final ESVersionSpecImpl<?, ? extends VersionSpec> versionSpecImpl = ((ESVersionSpecImpl<?, ?>) versionSpec);

		PrimaryVersionSpec primaryVersionSpec = new ServerCall<PrimaryVersionSpec>(server, monitor) {
			@Override
			protected PrimaryVersionSpec run() throws ESException {
				return getConnectionManager().resolveVersionSpec(getSessionId(),
																	getGlobalProjectId().getInternalAPIImpl(),
																	versionSpecImpl.getInternalAPIImpl());
			}
		}.execute();

		return primaryVersionSpec.getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#resolveVersionSpec(org.eclipse.emf.emfstore.client.ESUsersession,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ESPrimaryVersionSpec resolveVersionSpec(ESUsersession session, final ESVersionSpec versionSpec,
		IProgressMonitor monitor) throws ESException {

		Usersession usersession = ((ESUsersessionImpl) session).getInternalAPIImpl();
		final ESVersionSpecImpl<?, ? extends VersionSpec> versionSpecImpl = (ESVersionSpecImpl<?, ?>) versionSpec;

		PrimaryVersionSpec primaryVersionSpec = new ServerCall<PrimaryVersionSpec>(usersession) {
			@Override
			protected PrimaryVersionSpec run() throws ESException {
				return getConnectionManager().resolveVersionSpec(getSessionId(),
																	getGlobalProjectId().getInternalAPIImpl(),
																	versionSpecImpl.getInternalAPIImpl());
			}
		}.execute();

		return primaryVersionSpec.getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#getHistoryInfos(org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<ESHistoryInfo> getHistoryInfos(final ESHistoryQuery query, IProgressMonitor monitor)
		throws ESException {

		final ESHistoryQueryImpl<ESHistoryQuery, ?> queryImpl = (ESHistoryQueryImpl<ESHistoryQuery, ?>) query;

		List<ESHistoryInfoImpl> mapToInverse = ListUtil
			.mapToInverse((new ServerCall<List<HistoryInfo>>(server, monitor) {
				@Override
				protected List<HistoryInfo> run() throws ESException {
					return getConnectionManager().getHistoryInfo(
																	getSessionId(),
																	getGlobalProjectId().getInternalAPIImpl(),
																	queryImpl.getInternalAPIImpl());
				}
			}.execute()));
		return ListUtil.copy(mapToInverse);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#getHistoryInfos(org.eclipse.emf.emfstore.client.ESUsersession,
	 *      org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<ESHistoryInfo> getHistoryInfos(ESUsersession session, final ESHistoryQuery query,
		IProgressMonitor monitor) throws ESException {

		Usersession usersession = ((ESUsersessionImpl) session).getInternalAPIImpl();
		final ESGlobalProjectIdImpl globalProjectIdImpl = (ESGlobalProjectIdImpl) getGlobalProjectId();
		final ESHistoryQueryImpl<?, ?> queryImpl = (ESHistoryQueryImpl<?, ?>) query;

		List<HistoryInfo> historyInfos = new ServerCall<List<HistoryInfo>>(usersession,
			monitor) {
			@Override
			protected List<HistoryInfo> run() throws ESException {
				return getConnectionManager().getHistoryInfo(
																getUsersession().getSessionId(),
																globalProjectIdImpl.getInternalAPIImpl(),
																queryImpl.getInternalAPIImpl());
			}
		}.execute();

		return ListUtil.mapToAPI(ESHistoryInfo.class, historyInfos);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#addTag(org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void addTag(final ESPrimaryVersionSpec primaryVersionSpec, final ESTagVersionSpec tagVersionSpec,
		final IProgressMonitor monitor)
		throws ESException {

		final ESGlobalProjectIdImpl globalProjectIdImpl = (ESGlobalProjectIdImpl) getGlobalProjectId();
		final ESPrimaryVersionSpecImpl primaryVersionSpecImpl = (ESPrimaryVersionSpecImpl) primaryVersionSpec;
		final ESTagVersionSpecImpl tagVersionSpecImpl = (ESTagVersionSpecImpl) tagVersionSpec;

		RunESCommand.WithException.run(ESException.class, new Callable<Void>() {
			public Void call() throws Exception {
				new ServerCall<Void>(server, monitor) {
					@Override
					protected Void run() throws ESException {
						getConnectionManager().addTag(
														getUsersession().getSessionId(),
														globalProjectIdImpl.getInternalAPIImpl(),
														primaryVersionSpecImpl.getInternalAPIImpl(),
														tagVersionSpecImpl.getInternalAPIImpl());
						return null;
					}
				}.execute();
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESProject#removeTag(org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void removeTag(final ESPrimaryVersionSpec versionSpec, final ESTagVersionSpec tag,
		final IProgressMonitor monitor)
		throws ESException {

		final ESPrimaryVersionSpecImpl versionSpecImpl = (ESPrimaryVersionSpecImpl) versionSpec;
		final ESTagVersionSpecImpl tagVersionSpecImpl = (ESTagVersionSpecImpl) tag;

		RunESCommand.WithException.run(ESException.class, new Callable<Void>() {

			public Void call() throws Exception {
				new ServerCall<Void>(server, monitor) {
					@Override
					protected Void run() throws ESException {
						getConnectionManager().removeTag(
															getUsersession().getSessionId(),
															getGlobalProjectId().getInternalAPIImpl(),
															versionSpecImpl.getInternalAPIImpl(),
															tagVersionSpecImpl.getInternalAPIImpl());
						return null;
					}
				}.execute();
				return null;
			}
		});
		;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#checkout(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ESLocalProjectImpl checkout(final IProgressMonitor monitor) throws ESException {
		return RunESCommand.WithException.runWithResult(ESException.class, new Callable<ESLocalProjectImpl>() {
			public ESLocalProjectImpl call() throws Exception {
				return new ServerCall<ESLocalProjectImpl>(server) {
					@Override
					protected ESLocalProjectImpl run() throws ESException {
						return checkout(getUsersession().getAPIImpl(), monitor);
					}
				}.execute();
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#checkout(org.eclipse.emf.emfstore.client.ESUsersession,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ESLocalProjectImpl checkout(final ESUsersession usersession, final IProgressMonitor monitor)
		throws ESException {
		return RunESCommand.WithException.runWithResult(ESException.class, new Callable<ESLocalProjectImpl>() {
			public ESLocalProjectImpl call() throws Exception {
				ESPrimaryVersionSpec primaryVersionSpec = resolveVersionSpec(usersession, Versions.createHEAD()
					.getAPIImpl(), monitor);
				return checkout(usersession, primaryVersionSpec, monitor);
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#checkout(org.eclipse.emf.emfstore.client.ESUsersession,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ESLocalProjectImpl checkout(final ESUsersession session, final ESPrimaryVersionSpec versionSpec,
		final IProgressMonitor progressMonitor)
		throws ESException {

		final SubMonitor parent = SubMonitor.convert(progressMonitor, "Checkout", 100);
		return RunESCommand.WithException.runWithResult(ESException.class, new Callable<ESLocalProjectImpl>() {

			public ESLocalProjectImpl call() throws Exception {
				final Usersession usersession = ((ESUsersessionImpl) session).getInternalAPIImpl();

				// FIXME: MK: hack: set head version manually because esbrowser does not
				// update revisions properly
				final ProjectInfo projectInfoCopy = ModelUtil.clone(projectInfo);
				ESPrimaryVersionSpecImpl primaryVersionSpecImpl = (ESPrimaryVersionSpecImpl) versionSpec;
				projectInfoCopy.setVersion(primaryVersionSpecImpl.getInternalAPIImpl());

				Project project = null;

				SubMonitor newChild = parent.newChild(40);
				parent.subTask("Fetching project from server...");
				project = new UnknownEMFStoreWorkloadCommand<Project>(newChild) {
					@Override
					public Project run(IProgressMonitor monitor) throws ESException {
						return new ServerCall<Project>(usersession) {
							@Override
							protected Project run() throws ESException {
								return getConnectionManager().getProject(usersession.getSessionId(),
																			projectInfo.getProjectId(),
																			projectInfoCopy.getVersion());
							}
						}.execute();
					}
				}.execute();

				if (project == null) {
					throw new ESException("Server returned a null project!");
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
				projectSpace.setUsersession(usersession);
				WorkspaceProvider.getObserverBus().register((ProjectSpaceBase) projectSpace);
				projectSpace.setProject(project);
				projectSpace.setResourceCount(0);
				projectSpace.setLocalOperations(ModelFactory.eINSTANCE.createOperationComposite());
				parent.worked(20);

				// progressMonitor.subTask("Initializing resources...");
				// TODO: OTS cast
				Workspace workspace = WorkspaceProvider.getInstance().getWorkspace().getInternalAPIImpl();
				projectSpace.initResources(workspace.getResourceSet());
				parent.worked(10);

				ESLocalProjectImpl localProject = new ESLocalProjectImpl(projectSpace);

				// retrieve recent changes
				// TODO why are we doing this?? And why HERE?
				parent.subTask("Retrieving recent changes...");
				try {
					DateVersionSpec dateVersionSpec = VersioningFactory.eINSTANCE.createDateVersionSpec();
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DAY_OF_YEAR, -10);
					dateVersionSpec.setDate(calendar.getTime());
					ESPrimaryVersionSpec sourceSpec;
					try {
						sourceSpec = resolveVersionSpec(session, dateVersionSpec.getAPIImpl(), progressMonitor);
					} catch (InvalidVersionSpecException e) {
						PrimaryVersionSpec spec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
						spec.setIdentifier(0);
						sourceSpec = spec.getAPIImpl();
					}
					ModelUtil.saveResource(projectSpace.eResource(), WorkspaceUtil.getResourceLogger());

				} catch (ESException e) {
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
				((WorkspaceBase) workspace).addProjectSpace(projectSpace);
				// TODO: OTS save
				workspace.save();
				WorkspaceProvider.getObserverBus().notify(ESCheckoutObserver.class)
					.checkoutDone(localProject);
				parent.worked(10);
				parent.done();

				return localProject;
			}
		});

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#delete(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(final IProgressMonitor monitor) throws ESException {
		RunESCommand.WithException.run(ESException.class, new Callable<Void>() {
			public Void call() throws Exception {
				getDeleteProjectServerCall()
					.setProgressMonitor(monitor)
					.setServer(server)
					.execute();
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#delete(org.eclipse.emf.emfstore.client.ESUsersession,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(ESUsersession session, final IProgressMonitor monitor) throws ESException {
		final Usersession usersession = ((ESUsersessionImpl) session).getInternalAPIImpl();
		RunESCommand.WithException.run(ESException.class, new Callable<Void>() {
			public Void call() throws Exception {
				getDeleteProjectServerCall()
					.setProgressMonitor(monitor)
					.setUsersession(usersession)
					.execute();
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#getServer()
	 */
	public ESServerImpl getServer() {
		return server.getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESRemoteProject#getHeadVersion(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ESPrimaryVersionSpecImpl getHeadVersion(IProgressMonitor monitor) throws ESException {
		return resolveVersionSpec(Versions.createHEAD().getAPIImpl(), monitor);
	}

	private ServerCall<Void> getDeleteProjectServerCall() {
		return new ServerCall<Void>() {
			@Override
			protected Void run() throws ESException {
				new UnknownEMFStoreWorkloadCommand<Void>(getProgressMonitor()) {
					@Override
					public Void run(IProgressMonitor monitor) throws ESException {
						getConnectionManager().deleteProject(getSessionId(), projectInfo.getProjectId(), true);
						return null;
					}
				}.execute();

				return null;
			}
		};
	}
}
