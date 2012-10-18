/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
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
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.server.api.util.AuthControlMock;
import org.eclipse.emf.emfstore.client.test.server.api.util.ConnectionMock;
import org.eclipse.emf.emfstore.client.test.server.api.util.ResourceFactoryMock;
import org.eclipse.emf.emfstore.client.test.server.api.util.TestConflictResolver;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.EmfStore;
import org.eclipse.emf.emfstore.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.core.EmfStoreImpl;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.FatalEmfStoreException;
import org.eclipse.emf.emfstore.server.model.ModelFactory;
import org.eclipse.emf.emfstore.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.ServerSpace;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;

public abstract class CoreServerTest extends WorkspaceTest {

	private EmfStore emfStore;
	private AuthControlMock authMock;
	private ServerSpace serverSpace;
	private ConnectionMock connectionMock;

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
		emfStore = EmfStoreImpl.createInterface(serverSpace, authMock);
		connectionMock = new ConnectionMock(emfStore, authMock);
	}

	private ServerSpace initServerSpace() {
		ResourceSetImpl set = new ResourceSetImpl();
		set.setResourceFactoryRegistry(new ResourceFactoryMock());
		Resource resource = set.createResource(URI.createURI(""));
		ServerSpace serverSpace = ModelFactory.eINSTANCE.createServerSpace();
		resource.getContents().add(serverSpace);
		return serverSpace;
	}

	@Override
	public ConnectionManager initConnectionManager() {
		return connectionMock;
	}

	public EmfStore getEmfStore() {
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
					return ps.commitToBranch(Versions.createBRANCH(branchName), null, null, null);
				} catch (EmfStoreException e) {
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
					ps.shareProject();
					return ps.getBaseVersion();
				} catch (EmfStoreException e) {
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
					return ps.commit();
				} catch (EmfStoreException e) {
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
					Workspace workspace = getWorkspace();
					workspace.setConnectionManager(getConnectionMock());
					return workspace.checkout(projectSpace.getUsersession(),
						ModelUtil.clone(projectSpace.getProjectInfo()), ModelUtil.clone(projectSpace.getBaseVersion()),
						new NullProgressMonitor());
				} catch (EmfStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);
	}

	protected ProjectSpace checkout(final ProjectInfo projectInfo, final PrimaryVersionSpec baseVersion) {
		return new EMFStoreCommandWithResult<ProjectSpace>() {
			@Override
			protected ProjectSpace doRun() {
				try {
					Workspace workspace = getWorkspace();
					workspace.setConnectionManager(getConnectionMock());
					return workspace.checkout(projectSpace.getUsersession(), ModelUtil.clone(projectInfo),
						ModelUtil.clone(baseVersion), new NullProgressMonitor());
				} catch (EmfStoreException e) {
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
				} catch (EmfStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);
	}
}
