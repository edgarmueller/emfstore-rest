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
package org.eclipse.emf.emfstore.client.test.server.api;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.server.api.util.AuthControlMock;
import org.eclipse.emf.emfstore.client.test.server.api.util.ConnectionMock;
import org.eclipse.emf.emfstore.client.test.server.api.util.ResourceFactoryMock;
import org.eclipse.emf.emfstore.client.test.server.api.util.TestConflictResolver;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.impl.RemoteProject;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.core.EMFStoreImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalEmfStoreException;
import org.eclipse.emf.emfstore.internal.server.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ServerSpace;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;

public abstract class CoreServerTest extends WorkspaceTest {

	private EMFStore emfStore;
	private AuthControlMock authMock;
	private ServerSpace serverSpace;
	private ConnectionMock connectionMock;

	@Override
	protected void configureCompareAtEnd() {
		setCompareAtEnd(false);
	}

	@Override
	public void beforeHook() {
		try {
			initServer();
		} catch (FatalEmfStoreException e) {
			throw new RuntimeException(e);
		}
	}

	public void initServer() throws FatalEmfStoreException {
		ServerConfiguration.setTesting(true);
		serverSpace = initServerSpace();
		authMock = new AuthControlMock();
		emfStore = EMFStoreImpl.createInterface(serverSpace, authMock);
		connectionMock = new ConnectionMock(emfStore, authMock);
		WorkspaceProvider.getInstance().setConnectionManager(connectionMock);
	}

	private ServerSpace initServerSpace() {
		ResourceSetImpl set = new ResourceSetImpl();
		set.setResourceFactoryRegistry(new ResourceFactoryMock());
		Resource resource = set.createResource(URI.createURI(""));
		ServerSpace serverSpace = ModelFactory.eINSTANCE.createServerSpace();
		resource.getContents().add(serverSpace);
		return serverSpace;
	}

	public ConnectionManager initConnectionManager() {
		return connectionMock;
	}

	public EMFStore getEmfStore() {
		return emfStore;
	}

	public ConnectionMock getConnectionMock() {
		return connectionMock;
	}

	public ServerSpace getServerSpace() {
		return serverSpace;
	}

	protected ProjectHistory getProjectHistory(ProjectSpace ps) {
		ProjectId id = ps.getProjectId();
		for (ProjectHistory history : getServerSpace().getProjects()) {
			if (history.getProjectId().equals(id)) {
				return history;
			}
		}
		throw new RuntimeException("Project History not found");
	}

	protected PrimaryVersionSpec branch(final ProjectSpace ps, final String branchName) {
		return new EMFStoreCommandWithResult<PrimaryVersionSpec>() {
			@Override
			protected PrimaryVersionSpec doRun() {
				try {
					// TODO: TQ cast
					return (PrimaryVersionSpec) ps.commitToBranch(Versions.createBRANCH(branchName), null, null, null);
				} catch (EMFStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);
	}

	protected PrimaryVersionSpec share(final ProjectSpace ps) {
		return new EMFStoreCommandWithResult<PrimaryVersionSpec>() {
			@Override
			protected PrimaryVersionSpec doRun() {
				try {
					ps.shareProject(new NullProgressMonitor());
					return ps.getBaseVersion();
				} catch (EMFStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);
	}

	protected PrimaryVersionSpec commit(final ProjectSpace ps) {
		return new EMFStoreCommandWithResult<PrimaryVersionSpec>() {
			@Override
			protected PrimaryVersionSpec doRun() {
				try {
					return (PrimaryVersionSpec) ps.commit();
				} catch (EMFStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);
	}

	protected ProjectSpace reCheckout(final ProjectSpace projectSpace) {
		return new EMFStoreCommandWithResult<ProjectSpace>() {
			@Override
			protected ProjectSpace doRun() {
				try {
					((WorkspaceProvider) WorkspaceProvider.INSTANCE).setConnectionManager(getConnectionMock());
					// TODO: TQ
					return projectSpace.getRemoteProject().checkout(projectSpace.getUsersession(),
						projectSpace.getBaseVersion(), new NullProgressMonitor());

				} catch (EMFStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);
	}

	protected ProjectSpace checkout(final RemoteProject remoteProject, final PrimaryVersionSpec baseVersion) {
		return new EMFStoreCommandWithResult<ProjectSpace>() {
			@Override
			protected ProjectSpace doRun() {
				try {
					((WorkspaceProvider) WorkspaceProvider.INSTANCE).setConnectionManager(getConnectionMock());
					// TODO: TQ
					return remoteProject.checkout(getProjectSpace().getUsersession(), baseVersion,
						new NullProgressMonitor());

				} catch (EMFStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);
	}

	protected void mergeWithBranch(final ProjectSpace trunk, final PrimaryVersionSpec latestOnBranch,
		final int expectedConflicts) {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					// the conflict resolver always prefers the changes from the incoming branch
					((ProjectSpaceBase) trunk).mergeBranch(latestOnBranch, new TestConflictResolver(true,
						expectedConflicts));
				} catch (EMFStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);
	}
}