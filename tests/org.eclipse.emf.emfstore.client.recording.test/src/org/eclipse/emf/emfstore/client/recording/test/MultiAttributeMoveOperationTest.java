/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.recording.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeMoveOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.eclipse.emf.emfstore.test.model.TestmodelFactory;
import org.junit.Test;

/**
 * Tests for multiattributemove operations.
 * 
 * @author wesendon
 */
public class MultiAttributeMoveOperationTest extends ESTest {

	private static final String THIRD = "third"; //$NON-NLS-1$
	private static final String SECOND = "second"; //$NON-NLS-1$
	private static final String FIRST = "first"; //$NON-NLS-1$

	private TestElement testElement;

	/**
	 * Simple move element.
	 */
	@Test
	public void moveTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				final TestElement testElement = Create.testElement();
				testElement.getStrings().add(FIRST);
				testElement.getStrings().add(SECOND);
				testElement.getStrings().add(THIRD);

				clearOperations();

				testElement.getStrings().move(0, 2);

				assertTrue(testElement.getStrings().get(0).equals(THIRD));
				assertTrue(testElement.getStrings().get(1).equals(FIRST));
				assertTrue(testElement.getStrings().get(2).equals(SECOND));
			}
		}.run(false);
	}

	/**
	 * Test creation of element with cross references.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void createMultipleChildrenTestAtOnce() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement testElement1 = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement testElement11 = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement testElement12 = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement testElement13 = TestmodelFactory.eINSTANCE.createTestElement();

		final TestElement testElementc11 = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement testElementc12 = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement testElementc13 = TestmodelFactory.eINSTANCE.createTestElement();

		final List<TestElement> list = new ArrayList<TestElement>();
		list.add(testElement11);
		list.add(testElement12);
		list.add(testElement13);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement1);
				getProject().addModelElement(testElementc11);
				getProject().addModelElement(testElementc12);
				getProject().addModelElement(testElementc13);
				clearOperations();
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement11.getReferences().add(testElementc11);
				testElement12.getReferences().add(testElementc11);
				testElement13.getReferences().add(testElementc11);
				testElement1.getContainedElements().addAll(list);
			}
		}.run(false);

		assertEquals(true, getProject().contains(testElement1));
		assertEquals(true, getProject().contains(testElement11));
		assertEquals(true, getProject().contains(testElement12));
		assertEquals(true, getProject().contains(testElement13));

		assertNotNull(getProject().getModelElementId(testElement1));
		assertNotNull(getProject().getModelElementId(testElement11));
		assertNotNull(getProject().getModelElementId(testElement12));
		assertNotNull(getProject().getModelElementId(testElement13));

		assertEquals(4, getProjectSpace().getOperations().size());
	}

	/**
	 * Test creation of element with cross references.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void createWithChildrenTest() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement testElement1 = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement testElement11 = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement testElement12 = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement testElement111 = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement testElement121 = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement testElement122 = TestmodelFactory.eINSTANCE.createTestElement();
		testElement1.getContainedElements().add(testElement11);
		testElement1.getContainedElements().add(testElement12);
		testElement11.getContainedElements().add(testElement111);
		testElement12.getContainedElements().add(testElement121);
		testElement12.getContainedElements().add(testElement122);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement1);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				assertEquals(true, getProject().contains(testElement1));
				assertEquals(true, getProject().contains(testElement11));
				assertEquals(true, getProject().contains(testElement12));
				assertEquals(true, getProject().contains(testElement111));
				assertEquals(true, getProject().contains(testElement121));
				assertEquals(true, getProject().contains(testElement122));

				assertNotNull(getProject().getModelElementId(testElement1));
				assertNotNull(getProject().getModelElementId(testElement11));
				assertNotNull(getProject().getModelElementId(testElement12));
				assertNotNull(getProject().getModelElementId(testElement111));
				assertNotNull(getProject().getModelElementId(testElement121));
				assertNotNull(getProject().getModelElementId(testElement122));

				assertEquals(1, getProjectSpace().getOperations().size());
				assertEquals(true, getProjectSpace().getOperations().get(0) instanceof CreateDeleteOperation);
				CreateDeleteOperation operation = (CreateDeleteOperation) getProjectSpace().getOperations().get(0);
				assertEquals(getProject().getModelElementId(testElement1), operation.getModelElementId());
				assertEquals(0, operation.getSubOperations().size());

				assertEquals(getProject().getModelElementId(testElement1),
					operation.getEObjectToIdMap().get(operation.getModelElement()));
				assertEquals(getProject().getModelElementId(testElement11),
					operation.getEObjectToIdMap().get(operation.getModelElement().eContents().get(0)));
				assertEquals(getProject().getModelElementId(testElement12),
					operation.getEObjectToIdMap().get(operation.getModelElement().eContents().get(1)));
				assertEquals(getProject().getModelElementId(testElement111),
					operation.getEObjectToIdMap()
						.get(operation.getModelElement().eContents().get(0).eContents().get(0)));
				assertEquals(getProject().getModelElementId(testElement121),
					operation.getEObjectToIdMap()
						.get(operation.getModelElement().eContents().get(1).eContents().get(0)));
				assertEquals(getProject().getModelElementId(testElement122),
					operation.getEObjectToIdMap()
						.get(operation.getModelElement().eContents().get(1).eContents().get(1)));

				final CreateDeleteOperation copy = ModelUtil.clone(operation);

				operation = (CreateDeleteOperation) operation.reverse().reverse();

				assertEquals(getProject().getModelElementId(testElement1), operation.getModelElementId());
				assertEquals(0, operation.getSubOperations().size());

				assertEquals(getProject().getModelElementId(testElement1),
					operation.getEObjectToIdMap().get(operation.getModelElement()));
				assertEquals(getProject().getModelElementId(testElement11),
					operation.getEObjectToIdMap().get(operation.getModelElement().eContents().get(0)));
				assertEquals(getProject().getModelElementId(testElement12),
					operation.getEObjectToIdMap().get(operation.getModelElement().eContents().get(1)));
				assertEquals(getProject().getModelElementId(testElement111),
					operation.getEObjectToIdMap()
						.get(operation.getModelElement().eContents().get(0).eContents().get(0)));
				assertEquals(getProject().getModelElementId(testElement121),
					operation.getEObjectToIdMap()
						.get(operation.getModelElement().eContents().get(1).eContents().get(0)));
				assertEquals(getProject().getModelElementId(testElement122),
					operation.getEObjectToIdMap()
						.get(operation.getModelElement().eContents().get(1).eContents().get(1)));

				operation = copy;
				assertEquals(getProject().getModelElementId(testElement1), operation.getModelElementId());
				assertEquals(0, operation.getSubOperations().size());

				assertEquals(getProject().getModelElementId(testElement1),
					operation.getEObjectToIdMap().get(operation.getModelElement()));
				assertEquals(getProject().getModelElementId(testElement11),
					operation.getEObjectToIdMap().get(operation.getModelElement().eContents().get(0)));
				assertEquals(getProject().getModelElementId(testElement12),
					operation.getEObjectToIdMap().get(operation.getModelElement().eContents().get(1)));
				assertEquals(getProject().getModelElementId(testElement111),
					operation.getEObjectToIdMap()
						.get(operation.getModelElement().eContents().get(0).eContents().get(0)));
				assertEquals(getProject().getModelElementId(testElement121),
					operation.getEObjectToIdMap()
						.get(operation.getModelElement().eContents().get(1).eContents().get(0)));
				assertEquals(getProject().getModelElementId(testElement122),
					operation.getEObjectToIdMap()
						.get(operation.getModelElement().eContents().get(1).eContents().get(1)));

			}
		}.run(false);

	}

	/**
	 * Move and validate operation.
	 */
	@Test
	public void moveAndOperationTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				final TestElement testElement = Create.testElement();
				ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
				testElement.getStrings().add(FIRST);
				testElement.getStrings().add(SECOND);

				clearOperations();

				testElement.getStrings().move(0, 1);

				assertTrue(testElement.getStrings().get(0).equals(SECOND));
				assertTrue(testElement.getStrings().get(1).equals(FIRST));

			}
		}.run(false);

		assertEquals(1, getProjectSpace().getOperations().size());
		final AbstractOperation tmp = getProjectSpace().getOperations().get(0);
		assertTrue(tmp instanceof MultiAttributeMoveOperation);
		final MultiAttributeMoveOperation operation = (MultiAttributeMoveOperation) tmp;
		assertTrue(operation.getNewIndex() == 0);
		assertTrue(operation.getOldIndex() == 1);
		assertTrue(operation.getReferencedValue().equals(SECOND));

	}

	/**
	 * Move and reverse.
	 */
	@Test
	public void moveAndReverseTest() {
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testElement = Create.testElement();
				ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
				testElement.getStrings().add(FIRST);
				testElement.getStrings().add(SECOND);
				clearOperations();

				assertTrue(testElement.getStrings().size() == 2);
				assertTrue(testElement.getStrings().get(0).equals(FIRST));
				assertTrue(testElement.getStrings().get(1).equals(SECOND));

				testElement.getStrings().move(0, 1);
				assertTrue(testElement.getStrings().size() == 2);
				assertTrue(testElement.getStrings().get(0).equals(SECOND));
				assertTrue(testElement.getStrings().get(1).equals(FIRST));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				final AbstractOperation operation = getProjectSpace().getOperations().get(0).reverse();
				operation.apply(getProject());
				assertTrue(testElement.getStrings().size() == 2);
				assertTrue(testElement.getStrings().get(0).equals(FIRST));
				assertTrue(testElement.getStrings().get(1).equals(SECOND));
			}
		}.run(false);
	}
}
