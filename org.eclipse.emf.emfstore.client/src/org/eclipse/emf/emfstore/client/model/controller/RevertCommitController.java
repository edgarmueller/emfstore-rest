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

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.common.model.EObjectContainer;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;

/**
 * Controller that forces a revert of version spec.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class RevertCommitController extends ServerCall<Void> {

	private ProjectSpace projectSpace;
	private PrimaryVersionSpec versionSpec;
	private final boolean headRevert;

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the project to be reverted
	 * @param versionSpec
	 *            the target version to revert to
	 * @param headRevert if true, otherwise just revert individual version
	 */
	public RevertCommitController(EObjectContainer projectSpace, PrimaryVersionSpec versionSpec, boolean headRevert) {
		this.projectSpace = (ProjectSpace) projectSpace;
		this.versionSpec = versionSpec;
		this.headRevert = headRevert;
	}

	private void checkoutHeadAndReverseCommit(final ProjectSpace projectSpace, final PrimaryVersionSpec baseVersion,
		boolean headRevert) throws EmfStoreException {

		PrimaryVersionSpec localHead = getConnectionManager()
			.resolveVersionSpec(projectSpace.getUsersession().getSessionId(), projectSpace.getProjectId(),
				Versions.createHEAD(baseVersion));

		ProjectSpace revertSpace = projectSpace.getRemoteProject().checkout(projectSpace.getUsersession());

		List<ChangePackage> changes = revertSpace.getChanges(baseVersion,
			headRevert ? localHead : ModelUtil.clone(baseVersion));

		Collections.reverse(changes);

		for (ChangePackage cp : changes) {
			cp.reverse().apply(revertSpace.getProject(), true);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall#run()
	 */
	@Override
	protected Void run() throws EmfStoreException {
		checkoutHeadAndReverseCommit(projectSpace, versionSpec, headRevert);
		return null;
	}
}