package org.eclipse.emf.emfstore.client.test.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import junit.framework.Assert;

import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.common.model.util.SerializationException;
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

	@Test
	public void testCopyProjectWithCutElements() throws SerializationException {

		final Project project = getProject();
		final TestElement cutElement = getTestElement();
		// final TestElement element = getTestElement();
		// project.initMapping();
		project.getCutElements().add(cutElement);
		project.initMapping();
		// project.addModelElement(element);
		// project.deleteModelElement(element);

		Project clonedProject = ModelUtil.clone(project);

		Assert.assertEquals(ModelUtil.eObjectToString(project), ModelUtil.eObjectToString(clonedProject));
	}

	@Test
	public void testCopyProjectWithCutElementsWithoutDelete() {

		final Project project = getProject();
		final TestElement cutElement = getTestElement();
		final TestElement element = getTestElement();
		project.getCutElements().add(cutElement);

		project.addModelElement(element);
		project.initMapping();
		// project.deleteModelElement(element);

		Project clonedProject = ModelUtil.clone(project);

		Assert.assertEquals(project, clonedProject);
	}
}
