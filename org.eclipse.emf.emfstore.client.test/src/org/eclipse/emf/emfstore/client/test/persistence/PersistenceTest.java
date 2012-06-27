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
		assertTrue(ModelUtil.areEqual(WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces().get(0)
			.getProject(), originalProject));
	}

}
