/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.caching;

import java.util.Date;

import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.junit.Before;

/**
 * Abstract super class for project cache tests, contains setup.
 * 
 * @author koegel
 */
public abstract class CachingTest {

	private Project project;

	/**
	 * Setup a dummy project for testing.
	 */
	@Before
	public void setupProjectSpace() {
		ProjectSpace projectSpace = ModelFactory.eINSTANCE.createProjectSpace();
		projectSpace.setBaseVersion(VersioningFactory.eINSTANCE.createPrimaryVersionSpec());
		projectSpace.setIdentifier("testProjectSpace");
		projectSpace.setLastUpdated(new Date());
		projectSpace.setProjectDescription("ps description");
		projectSpace.setProjectId(org.eclipse.emf.emfstore.internal.server.model.ModelFactory.eINSTANCE
			.createProjectId());
		projectSpace.setProjectName("ps name");

		project = org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE.createProject();
		projectSpace.setProject(getProject());

		projectSpace.makeTransient();
		projectSpace.init();
	}

	protected Project getProject() {
		return project;
	}
}
