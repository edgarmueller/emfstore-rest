/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.recording.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceMoveOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.OperationsFactory;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.eclipse.emf.emfstore.test.model.TestmodelPackage;
import org.junit.Test;

/**
 * Tests the MultiReferenceMoveOperation.
 * 
 * @author koegel
 */
public class MultiReferenceMoveOperationTest extends ESTest {

	/**
	 * Change a multi reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void makeMultiReferenceMove() throws UnsupportedOperationException, UnsupportedNotificationException {
		final TestElement useCase1 = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement useCase3 = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				// useCase1.setIdentifier("usecase1");
				getProject().addModelElement(useCase1);
				getProject().addModelElement(actor);
				// useCase2.setIdentifier("usecase2");
				getProject().addModelElement(useCase2);
				// useCase3.setIdentifier("usecase3");
				getProject().addModelElement(useCase3);

				actor.getNonContained_1ToN().add(useCase1);
				actor.getNonContained_1ToN().add(useCase2);
				actor.getNonContained_1ToN().add(useCase3);
			}
		}.run(false);

		assertEquals(actor, useCase1.getNonContained_NTo1());
		assertEquals(actor, useCase2.getNonContained_NTo1());
		assertEquals(actor, useCase3.getNonContained_NTo1());
		final EList<TestElement> initiatedTestElements = actor.getNonContained_1ToN();
		assertEquals(3, initiatedTestElements.size());
		assertEquals(useCase1, initiatedTestElements.get(0));
		assertEquals(useCase2, initiatedTestElements.get(1));
		assertEquals(useCase3, initiatedTestElements.get(2));

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				clearOperations();
				actor.getNonContained_1ToN().move(2, 1);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiReferenceMoveOperation);
		final MultiReferenceMoveOperation multiReferenceMoveOperation = (MultiReferenceMoveOperation) operation;

		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		final ModelElementId useCase2Id = ModelUtil.getProject(useCase2).getModelElementId(useCase2);

		assertEquals(TestElementFeatures.nonContained1ToN().getName(),
			multiReferenceMoveOperation.getFeatureName());
		assertEquals(actorId, multiReferenceMoveOperation.getModelElementId());
		assertEquals(2, multiReferenceMoveOperation.getNewIndex());
		assertEquals(1, multiReferenceMoveOperation.getOldIndex());
		assertEquals(useCase2Id, multiReferenceMoveOperation.getReferencedModelElementId());

		assertEquals(actor, useCase1.getNonContained_NTo1());
		assertEquals(actor, useCase2.getNonContained_NTo1());
		assertEquals(actor, useCase3.getNonContained_NTo1());
		assertEquals(3, initiatedTestElements.size());
		assertEquals(useCase1, initiatedTestElements.get(0));
		assertEquals(useCase2, initiatedTestElements.get(2));
		assertEquals(useCase3, initiatedTestElements.get(1));

	}

	/**
	 * Change a multi reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void reverseMultiReferenceMove() throws UnsupportedOperationException, UnsupportedNotificationException {
		final TestElement useCase1 = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement useCase3 = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				// useCase1.setIdentifier("usecase1");
				getProject().addModelElement(useCase1);
				getProject().addModelElement(actor);
				// useCase2.setIdentifier("usecase2");
				getProject().addModelElement(useCase2);
				// useCase3.setIdentifier("usecase3");
				getProject().addModelElement(useCase3);

				actor.getNonContained_1ToN().add(useCase1);
				actor.getNonContained_1ToN().add(useCase2);
				actor.getNonContained_1ToN().add(useCase3);

			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				assertEquals(actor, useCase1.getNonContained_NTo1());
				assertEquals(actor, useCase2.getNonContained_NTo1());
				assertEquals(actor, useCase3.getNonContained_NTo1());
				final EList<TestElement> initiatedTestElements = actor.getNonContained_1ToN();
				assertEquals(3, initiatedTestElements.size());
				assertEquals(useCase1, initiatedTestElements.get(0));
				assertEquals(useCase2, initiatedTestElements.get(1));
				assertEquals(useCase3, initiatedTestElements.get(2));
				clearOperations();

				actor.getNonContained_1ToN().move(2, 1);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		final AbstractOperation reverse = operation.reverse();

		assertEquals(true, reverse instanceof MultiReferenceMoveOperation);
		final MultiReferenceMoveOperation multiReferenceMoveOperation = (MultiReferenceMoveOperation) reverse;

		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		final ModelElementId useCase2Id = ModelUtil.getProject(useCase2).getModelElementId(useCase2);

		assertEquals(TestElementFeatures.nonContained1ToN().getName(),
			multiReferenceMoveOperation.getFeatureName());
		assertEquals(actorId, multiReferenceMoveOperation.getModelElementId());
		assertEquals(1, multiReferenceMoveOperation.getNewIndex());
		assertEquals(2, multiReferenceMoveOperation.getOldIndex());
		assertEquals(useCase2Id, multiReferenceMoveOperation.getReferencedModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				reverse.apply(getProject());
			}
		}.run(false);

		final EList<TestElement> initiatedTestElements = actor.getNonContained_1ToN();
		assertEquals(actor, useCase1.getNonContained_NTo1());
		assertEquals(actor, useCase2.getNonContained_NTo1());
		assertEquals(actor, useCase3.getNonContained_NTo1());
		assertEquals(3, initiatedTestElements.size());
		assertEquals(useCase1, initiatedTestElements.get(0));
		assertEquals(useCase2, initiatedTestElements.get(1));
		assertEquals(useCase3, initiatedTestElements.get(2));

	}

	/**
	 * Tests a false index while moving.
	 */
	@Test
	public void makeOutOfBoundMove() {
		final TestElement actor = Create.testElement();
		final TestElement useCase1 = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement useCase3 = Create.testElement();

		ProjectUtil.addElement(getProjectSpace().toAPI(), actor);
		ProjectUtil.addElement(getProjectSpace().toAPI(), useCase1);
		ProjectUtil.addElement(getProjectSpace().toAPI(), useCase2);
		ProjectUtil.addElement(getProjectSpace().toAPI(), useCase3);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.getNonContained_1ToN().add(useCase1);
				actor.getNonContained_1ToN().add(useCase2);
				actor.getNonContained_1ToN().add(useCase3);
			}
		}.run(false);

		assertEquals(actor, useCase1.getNonContained_NTo1());
		assertEquals(actor, useCase2.getNonContained_NTo1());
		assertEquals(actor, useCase3.getNonContained_NTo1());
		final EList<TestElement> initiatedTestElements = actor.getNonContained_1ToN();
		assertEquals(3, initiatedTestElements.size());
		assertEquals(useCase1, initiatedTestElements.get(0));
		assertEquals(useCase2, initiatedTestElements.get(1));
		assertEquals(useCase3, initiatedTestElements.get(2));

		ProjectUtil.clearOperations(getProjectSpace().toAPI());

		final MultiReferenceMoveOperation multiReferenceMoveOperation = OperationsFactory.eINSTANCE
			.createMultiReferenceMoveOperation();

		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		final ModelElementId useCase1Id = ModelUtil.getProject(useCase1).getModelElementId(useCase1);

		multiReferenceMoveOperation.setModelElementId(actorId);
		multiReferenceMoveOperation.setFeatureName(TestmodelPackage.eINSTANCE.getTestElement_NonContained_NTo1()
			.getName());
		multiReferenceMoveOperation.setReferencedModelElementId(useCase1Id);
		multiReferenceMoveOperation.setOldIndex(0);
		multiReferenceMoveOperation.setNewIndex(3);

		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				multiReferenceMoveOperation.apply(getProject());

				assertEquals(actor, useCase1.getNonContained_NTo1());
				assertEquals(actor, useCase2.getNonContained_NTo1());
				assertEquals(actor, useCase3.getNonContained_NTo1());
				final List<TestElement> initiatedTestElements2 = actor.getNonContained_1ToN();
				assertEquals(3, initiatedTestElements2.size());
				assertEquals(useCase1, initiatedTestElements2.get(0));
				assertEquals(useCase2, initiatedTestElements2.get(1));
				assertEquals(useCase3, initiatedTestElements2.get(2));

				clearOperations();
			}
		});

		final MultiReferenceMoveOperation multiReferenceMoveOperation2 = OperationsFactory.eINSTANCE
			.createMultiReferenceMoveOperation();
		multiReferenceMoveOperation2.setModelElementId(actorId);
		multiReferenceMoveOperation2.setFeatureName(TestmodelPackage.eINSTANCE.getTestElement_NonContained_1ToN()
			.getName());
		multiReferenceMoveOperation2.setReferencedModelElementId(useCase1Id);
		multiReferenceMoveOperation2.setOldIndex(0);
		multiReferenceMoveOperation2.setNewIndex(-1);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				multiReferenceMoveOperation2.apply(getProject());
			}
		};

		assertEquals(actor, useCase1.getNonContained_NTo1());
		assertEquals(actor, useCase2.getNonContained_NTo1());
		assertEquals(actor, useCase3.getNonContained_NTo1());
		final List<TestElement> initiatedTestElements3 = actor.getNonContained_1ToN();
		assertEquals(3, initiatedTestElements3.size());
		assertEquals(useCase1, initiatedTestElements3.get(0));
		assertEquals(useCase2, initiatedTestElements3.get(1));
		assertEquals(useCase3, initiatedTestElements3.get(2));

		final TestElement useCase4 = Create.testElement();
		// useCase4.setIdentifier("usecase4");
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase4);
				clearOperations();
			}
		}.run();

		final MultiReferenceMoveOperation multiReferenceMoveOperation3 = OperationsFactory.eINSTANCE
			.createMultiReferenceMoveOperation();
		multiReferenceMoveOperation3.setModelElementId(actorId);
		multiReferenceMoveOperation3.setFeatureName(TestmodelPackage.eINSTANCE.getTestElement_NonContained_1ToN()
			.getName());

		final ModelElementId useCase4Id = ModelUtil.getProject(useCase4).getModelElementId(useCase4);

		multiReferenceMoveOperation3.setReferencedModelElementId(useCase4Id);
		multiReferenceMoveOperation3.setOldIndex(0);
		multiReferenceMoveOperation3.setNewIndex(2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				multiReferenceMoveOperation3.apply(getProject());
			}
		};

		assertEquals(actor, useCase1.getNonContained_NTo1());
		assertEquals(actor, useCase2.getNonContained_NTo1());
		assertEquals(actor, useCase3.getNonContained_NTo1());
		assertEquals(null, useCase4.getNonContained_NTo1());
		final List<TestElement> initiatedTestElements4 = actor.getNonContained_1ToN();
		assertEquals(3, initiatedTestElements4.size());
		assertEquals(useCase1, initiatedTestElements4.get(0));
		assertEquals(useCase2, initiatedTestElements4.get(1));
		assertEquals(useCase3, initiatedTestElements4.get(2));
	}
}
