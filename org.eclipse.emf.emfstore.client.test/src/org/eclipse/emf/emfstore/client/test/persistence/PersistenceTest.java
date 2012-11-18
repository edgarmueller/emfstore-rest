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
package org.eclipse.emf.emfstore.client.test.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.model.Configuration;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.junit.Test;

public class PersistenceTest extends WorkspaceTest {

	@Test
	public void testReinitWorkspace() {
		Configuration.setAutoSave(false);
		Project originalProject = ModelUtil.clone(WorkspaceManager.getInstance().getCurrentWorkspace()
			.getProjectSpaces().get(0).getProject());
		project.addModelElement(TestmodelFactory.eINSTANCE.createTestElement());
		assertEquals(WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces().get(0).getProject()
			.getModelElements().size(), 1);
		WorkspaceManager.getInstance().dispose();
		WorkspaceManager.getInstance().reinit();
		projectSpace = WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces().get(0);
		project = projectSpace.getProject();
		assertTrue(ModelUtil.areEqual(WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces().get(0)
			.getProject(), originalProject));
	}

}