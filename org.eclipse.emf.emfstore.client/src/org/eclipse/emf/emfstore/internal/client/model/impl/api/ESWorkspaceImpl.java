/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.impl.api;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.Workspace;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.client.model.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.common.ListUtil;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * Mapping between {@link ESWorkspace} and {@link Workspace}.
 * 
 * @author emueller
 * 
 */
public class ESWorkspaceImpl extends AbstractAPIImpl<ESWorkspaceImpl, Workspace> implements ESWorkspace {

	/**
	 * Constructor.
	 * 
	 * @param workspace
	 *            the internal delegate
	 */
	public ESWorkspaceImpl(Workspace workspace) {
		super(workspace);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESWorkspace#getLocalProjects()
	 */
	public List<ESLocalProject> getLocalProjects() {
		return ListUtil.mapToAPI(ESLocalProject.class, getInternalAPIImpl().getProjectSpaces());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESWorkspace#createLocalProject(java.lang.String)
	 */
	public ESLocalProjectImpl createLocalProject(final String projectName) {
		return new EMFStoreCommandWithResult<ESLocalProjectImpl>() {
			@Override
			protected ESLocalProjectImpl doRun() {
				ProjectSpace projectSpace = getInternalAPIImpl().createLocalProject(projectName);
				return projectSpace.getAPIImpl();
			}
		}.run(false);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESWorkspace#getServers()
	 */
	public List<ESServer> getServers() {
		return ListUtil.mapToAPI(ESServer.class, getInternalAPIImpl().getServerInfos());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESWorkspace#addServer(org.eclipse.emf.emfstore.client.ESServer)
	 */
	public void addServer(ESServer server) {
		final ESServerImpl serverImpl = (ESServerImpl) server;
		// TODO: perform contains check?
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getInternalAPIImpl().addServerInfo(serverImpl.getInternalAPIImpl());
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESWorkspace#removeServer(org.eclipse.emf.emfstore.client.ESServer)
	 */
	public void removeServer(final ESServer server) {
		final ESServerImpl serverImpl = (ESServerImpl) server;
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getInternalAPIImpl().removeServerInfo(serverImpl.getInternalAPIImpl());
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ESWorkspace#getLocalProject(org.eclipse.emf.ecore.EObject)
	 */
	public ESLocalProject getLocalProject(final EObject modelElement) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				Project project = ModelUtil.getProject(modelElement);
				ProjectSpace projectSpace = getInternalAPIImpl().getProjectSpace(project);
				return projectSpace.getAPIImpl();
			}
		});
	}

}
