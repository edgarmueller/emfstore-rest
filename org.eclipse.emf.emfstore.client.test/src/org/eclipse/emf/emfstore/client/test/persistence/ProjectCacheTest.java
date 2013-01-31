package org.eclipse.emf.emfstore.client.test.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElementContainer;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.common.model.Project;
import org.junit.Test;

public class ProjectCacheTest extends WorkspaceTest {

	@Test
	public void testGetIdForCutElement() {

		final Project project = getProject();
		final TestElement cutElement = getTestElement();
		final TestElement element = getTestElement();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				project.getCutElements().add(cutElement);
				project.addModelElement(element);

			}
		}.run(false);

		assertNotNull(project.getModelElementId(element));
		assertNotNull(project.getModelElementId(cutElement));
	}

	@Test
	public void testGetNoIdForDeletedElement() {

		final Project project = getProject();
		final TestElement element = getTestElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				project.addModelElement(element);
			}
		}.run(false);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				project.deleteModelElement(element);
			}
		}.run(false);

		assertNull(project.getModelElementId(element));
	}

	@Test
	public void testSwitchContainerInDifferentCommands() {

		final Project project = getProject();
		final TestElement element = getTestElement();
		final TestElementContainer container = TestmodelFactory.eINSTANCE.createTestElementContainer();
		container.getElements().add(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				project.addModelElement(container);
				clearOperations();
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				element.setContainer(null);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				container.getElements().add(element);
			}
		}.run(false);

	}

	@Test
	public void testSwitchContainerViaElement() {

		final Project project = getProject();
		final TestElement element = getTestElement();
		final TestElementContainer container = TestmodelFactory.eINSTANCE.createTestElementContainer();
		container.getElements().add(element);
		final TestElementContainer container2 = TestmodelFactory.eINSTANCE.createTestElementContainer();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				project.addModelElement(container);
				project.addModelElement(container2);
				clearOperations();
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				element.setContainer(container2);
			}
		}.run(false);

	}

	@Test
	public void testSwitchContainerViaContainer() {

		final Project project = getProject();
		final TestElement element = getTestElement();
		final TestElementContainer container = TestmodelFactory.eINSTANCE.createTestElementContainer();
		container.getElements().add(element);
		final TestElementContainer container2 = TestmodelFactory.eINSTANCE.createTestElementContainer();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				project.addModelElement(container);
				project.addModelElement(container2);
				clearOperations();
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				container.getElements().remove(element);
				container2.getElements().add(element);
			}
		}.run(false);
	}

	@Test
	public void testElementLosesItsContainer() {

		final Project project = getProject();
		final TestElement element = getTestElement();
		final TestElementContainer container = TestmodelFactory.eINSTANCE.createTestElementContainer();
		container.getElements().add(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				project.addModelElement(container);
				clearOperations();
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				container.getElements().add(element);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				element.setContainer(container);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				container.getElements().remove(element);
				element.setContainer(null);
			}
		}.run(false);
	}
}
