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
package org.eclipse.emf.emfstore.client.model.controller;

import java.util.List;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;

public class ForceRevertController extends ServerCall<Void> {

	private ProjectSpace projectSpace;
	private PrimaryVersionSpec versionSpec;

	public ForceRevertController(ProjectSpace projectSpace, PrimaryVersionSpec versionSpec) {
		this.projectSpace = projectSpace;
		this.versionSpec = versionSpec;
	}

	public void checkoutHeadAndReverseCommit(final ProjectSpace projectSpace, final PrimaryVersionSpec versionSpec)
		throws EmfStoreException {

		ConnectionManager connectionManager = WorkspaceManager.getInstance().getConnectionManager();

		ProjectSpace revertSpace = WorkspaceManager
			.getInstance()
			.getCurrentWorkspace()
			.checkout(
				projectSpace.getUsersession(),
				projectSpace.getProjectInfo(),
				connectionManager.resolveVersionSpec(projectSpace.getUsersession().getSessionId(),
					projectSpace.getProjectId(), VersionSpec.HEAD_VERSION));
		PrimaryVersionSpec sourceVersion = ModelUtil.clone(versionSpec);
		sourceVersion.setIdentifier(sourceVersion.getIdentifier() - 1);
		List<ChangePackage> changes = revertSpace.getChanges(sourceVersion, versionSpec);
		if (changes.size() != 1) {
			throw new EmfStoreException("Zero or more than 1 Change Package received for one revision!");
		}
		ChangePackage changePackage = changes.get(0);
		ChangePackage reversedChangePackage = changePackage.reverse();
		reversedChangePackage.apply(revertSpace.getProject(), true);
	}

	@Override
	protected Void run() throws EmfStoreException {
		checkoutHeadAndReverseCommit(projectSpace, versionSpec);
		return null;
	}
}
