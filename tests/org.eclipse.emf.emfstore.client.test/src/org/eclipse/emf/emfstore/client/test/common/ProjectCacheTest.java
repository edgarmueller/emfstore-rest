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
package org.eclipse.emf.emfstore.client.test.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElementContainer;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver;
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
	public void testAddingObserverMustNotOverwriteExistingIDsWhileExecutingCommand() {

		final ModelElementId[] cutElementIWhileCommand = new ModelElementId[1];

		final Project project = getProject();
		final TestElement cutElement = getTestElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				project.getCutElements().add(cutElement);
				cutElementIWhileCommand[0] = project.getModelElementId(cutElement);
				project.addIdEObjectCollectionChangeObserver(createDummyObserver());
			}
		}.run(false);

		assertEquals(cutElementIWhileCommand[0], project.getModelElementId(cutElement));
	}

	private IdEObjectCollectionChangeObserver createDummyObserver() {
		return new IdEObjectCollectionChangeObserver() {

			public void notify(Notification notification, IdEObjectCollection collection, EObject modelElement) {
				// TODO Auto-generated method stub

			}

			public void modelElementRemoved(IdEObjectCollection collection, EObject eObject) {
				// TODO Auto-generated method stub

			}

			public void modelElementAdded(IdEObjectCollection collection, EObject eObject) {
				// TODO Auto-generated method stub

			}

			public void collectionDeleted(IdEObjectCollection collection) {
				// TODO Auto-generated method stub

			}
		};
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
