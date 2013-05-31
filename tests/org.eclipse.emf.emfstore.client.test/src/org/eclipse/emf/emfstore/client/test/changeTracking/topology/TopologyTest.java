/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Slabo Chodnick
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.changeTracking.topology;

import java.util.Date;

import junit.framework.Assert;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.junit.Before;

/**
 * Abstract super class for operation tests, contains setup.
 * 
 * @author chodnick
 * @author emueller
 */
public abstract class TopologyTest {

	private ProjectSpace projectSpace;

	/**
	 * Setup a dummy project for testing.
	 */
	@Before
	public void setupProjectSpace() {
		ProjectSpace projectSpace = ModelFactory.eINSTANCE.createProjectSpace();
		projectSpace.setBaseVersion(VersioningFactory.eINSTANCE.createPrimaryVersionSpec());
		projectSpace.setIdentifier("testProjectSpace");
		projectSpace.setLastUpdated(new Date());
		projectSpace.setLocalOperations(ModelFactory.eINSTANCE.createOperationComposite());
		projectSpace.setProjectDescription("ps description");
		projectSpace.setProjectId(org.eclipse.emf.emfstore.internal.server.model.ModelFactory.eINSTANCE
			.createProjectId());
		projectSpace.setProjectName("ps name");
		projectSpace.setProject(org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE.createProject());
		projectSpace.makeTransient();
		projectSpace.init();

		this.projectSpace = projectSpace;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return projectSpace.getProject();
	}

	/**
	 * @return the projectSpace
	 */
	public ProjectSpace getProjectSpace() {
		return projectSpace;
	}

	/**
	 * Clear all operations from project space.
	 */
	protected void clearOperations() {
		getProjectSpace().getOperations().clear();
		getProjectSpace().getOperationManager().clearOperations();
	}

	private <T extends AbstractOperation> T asInstanceOf(AbstractOperation op, Class<T> clazz) {
		return clazz.cast(op);
	}

	public <T extends AbstractOperation> T checkAndCast(AbstractOperation op, Class<T> clazz) {
		Assert.assertTrue(clazz.isInstance(op));
		return asInstanceOf(op, clazz);
	}

	public void addToProject(EObject... eObjects) {
		for (EObject eObject : eObjects) {
			getProject().getModelElements().add(eObject);
		}
	}
}
