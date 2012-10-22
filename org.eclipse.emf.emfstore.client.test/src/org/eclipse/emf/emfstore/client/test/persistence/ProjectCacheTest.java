package org.eclipse.emf.emfstore.client.test.persistence;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.common.model.Project;
import org.junit.Test;

public class ProjectCacheTest extends WorkspaceTest {

	@Test
	public void testCutElementsCache() {

		Project project = getProject();
		TestElement cutElement = getTestElement();
		TestElement element = getTestElement();
		project.getCutElements().add(cutElement);
		project.addModelElement(element);
		project.initCaches();

		assertNotNull(project.getModelElementId(element));
		assertNotNull(project.getModelElementId(cutElement));
	}
}
