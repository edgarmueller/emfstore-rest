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
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;

public abstract class ESTestWithRemoteProject extends ESTestWithLoggedInUser {

	private ESRemoteProject remoteProject;

	@Override
	@Before
	public void before() {
		super.before();
		try {
			createRemoteProject();
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
	}

	private void createRemoteProject() throws ESException {
		remoteProject = getServer().createRemoteProject(getUsersession(),
			ProjectUtil.defaultName(), new NullProgressMonitor());
	}

	@Override
	@After
	public void after() {
		if (remoteProject != null) {
			try {
				remoteProject.delete(getUsersession(), new NullProgressMonitor());
			} catch (final ESException ex) {
				fail(ex.getMessage());
			}
		}
		super.after();
	}

	public ESRemoteProject getRemoteProject() {
		return remoteProject;
	}
}
