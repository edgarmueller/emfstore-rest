package org.eclipse.emf.emfstore.client.test.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
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
		project.initMapping();

		assertNotNull(project.getModelElementId(element));
		assertNotNull(project.getModelElementId(cutElement));
	}

	@Test
	public void testDeletedElementsCache() {

		final Project project = getProject();
		TestElement cutElement = getTestElement();
		final TestElement element = getTestElement();
		project.getCutElements().add(cutElement);

		project.addModelElement(element);
		project.initMapping();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				project.deleteModelElement(element);
				ModelElementId modelElementId = project.getModelElementId(element);
				assertNull(modelElementId);
			}
		}.run(false);

		assertNull(project.getModelElementId(element));
	}
}
