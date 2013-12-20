/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.cases;

import static org.junit.Assert.fail;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.common.dsl.Delete;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;

public abstract class ESTestWithSharedProject extends ESTestWithLoggedInUser {

	private final ESWorkspace workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();

	@Override
	@Before
	public void before() {
		super.before();
		try {
			getLocalProject().shareProject(getUsersession(), new NullProgressMonitor());
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
	}

	@Override
	@After
	public void after() {
		try {
			Delete.allRemoteProjects(getServer(), getUsersession());
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
		// delete all local projects by delegating to super
		super.after();
	}

	public ESWorkspace getWorkspace() {
		return workspace;
	}
}
