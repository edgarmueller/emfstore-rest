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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.bowling.Merchandise;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Delete;
import org.eclipse.emf.emfstore.client.test.common.dsl.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests the MultiReferenceOperation.
 * 
 * @author koegel
 */
public class MultiReferenceOperationTest extends ESTest {

	private static final String M3 = "M3"; //$NON-NLS-1$
	private static final String M2 = "M2"; //$NON-NLS-1$
	private static final String M1 = "M1"; //$NON-NLS-1$

	/**
	 * Change a multi reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void changeMultiReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();
		ProjectUtil.addElement(getProjectSpace().toAPI(), useCase);
		ProjectUtil.addElement(getProjectSpace().toAPI(), actor);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				// useCase.setIdentifier("usecase");
				clearOperations();
				actor.getNonContained_1ToN().add(useCase);
			}
		}.run(false);
		assertEquals(actor, useCase.getNonContained_NTo1());
		final EList<TestElement> initiatedTestElements = actor.getNonContained_1ToN();
		assertEquals(1, initiatedTestElements.size());
		assertEquals(useCase, initiatedTestElements.get(0));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;

		final List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		operation = subOperations.get(0);
		assertTrue(operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) operation;

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);

		assertEquals(TestElementFeatures.nonContainedNTo1().getName(),
			singleReferenceOperation.getFeatureName());
		assertEquals(TestElementFeatures.nonContained1ToN().getName(),
			singleReferenceOperation.getOppositeFeatureName());
		assertEquals(useCaseId, singleReferenceOperation.getModelElementId());
		assertEquals(actorId, singleReferenceOperation.getNewValue());
		assertNull(singleReferenceOperation.getOldValue());
		assertTrue(singleReferenceOperation.isBidirectional());

		operation = subOperations.get(1);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) operation;

		assertEquals(TestElementFeatures.nonContained1ToN().getName(), multiReferenceOperation.getFeatureName());
		assertEquals(0, multiReferenceOperation.getIndex());
		assertEquals(actorId, multiReferenceOperation.getModelElementId());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), multiReferenceOperation.getOppositeFeatureName());
		assertTrue(multiReferenceOperation.isAdd());
		assertTrue(multiReferenceOperation.isBidirectional());

		final EList<ModelElementId> referencedModelElements = multiReferenceOperation.getReferencedModelElements();
		assertEquals(1, referencedModelElements.size());
		assertEquals(useCaseId, referencedModelElements.get(0));

		final Set<ModelElementId> otherInvolvedModelElements = multiReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements.size());
		assertTrue(otherInvolvedModelElements.contains(useCaseId));

	}

	/**
	 * Change a multi reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void reverseMultiReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				// useCase.setIdentifier("usecase1");
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				clearOperations();

				actor.getNonContained_1ToN().add(useCase);

				assertEquals(actor, useCase.getNonContained_NTo1());
				final EList<TestElement> initiatedTestElements = actor.getNonContained_1ToN();
				assertEquals(1, initiatedTestElements.size());
				assertEquals(useCase, initiatedTestElements.get(0));
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation comp = operations.get(0);
		assertTrue(comp instanceof CompositeOperation);

		final List<AbstractOperation> subOperations = ((CompositeOperation) comp).getSubOperations();

		assertEquals(2, subOperations.size());

		// skipping singlereference op
		final AbstractOperation operation = subOperations.get(1);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) operation;

		final AbstractOperation reverse = multiReferenceOperation.reverse();
		assertTrue(reverse instanceof MultiReferenceOperation);

		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		final MultiReferenceOperation reversedMultiReferenceOperation = (MultiReferenceOperation) reverse;
		assertEquals(TestElementFeatures.nonContained1ToN().getName(), reversedMultiReferenceOperation.getFeatureName());
		assertEquals(0, reversedMultiReferenceOperation.getIndex());
		assertEquals(actorId, reversedMultiReferenceOperation.getModelElementId());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(),
			reversedMultiReferenceOperation.getOppositeFeatureName());
		assertFalse(reversedMultiReferenceOperation.isAdd());
		assertTrue(reversedMultiReferenceOperation.isBidirectional());

		final EList<ModelElementId> referencedModelElements = reversedMultiReferenceOperation
			.getReferencedModelElements();
		assertEquals(1, referencedModelElements.size());
		assertEquals(useCaseId, referencedModelElements.get(0));

		final Set<ModelElementId> otherInvolvedModelElements = reversedMultiReferenceOperation
			.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements.size());
		assertTrue(otherInvolvedModelElements.contains(useCaseId));

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				reversedMultiReferenceOperation.apply(getProject());
			}
		}.run(false);

		assertEquals(0, actor.getNonContained_1ToN().size());
		assertNull(useCase.getNonContained_NTo1());
	}

	/**
	 * Change a multi reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void addManyMultiReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement useCase3 = Create.testElement();
		final List<TestElement> useCases = new ArrayList<TestElement>();
		useCases.add(useCase);
		useCases.add(useCase2);
		useCases.add(useCase3);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				// useCase.setIdentifier("usecase1");
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);
				// useCase2.setIdentifier("usecase2");
				getProject().addModelElement(useCase2);
				// useCase3.setIdentifier("usecase3");
				getProject().addModelElement(useCase3);
				clearOperations();

				actor.getNonContained_1ToN().addAll(useCases);

				assertEquals(actor, useCase.getNonContained_NTo1());
				assertEquals(actor, useCase2.getNonContained_NTo1());
				assertEquals(actor, useCase3.getNonContained_NTo1());
				final EList<TestElement> initiatedTestElements = actor.getNonContained_1ToN();
				assertEquals(3, initiatedTestElements.size());
				assertEquals(useCase, initiatedTestElements.get(0));
				assertEquals(useCase2, initiatedTestElements.get(1));
				assertEquals(useCase3, initiatedTestElements.get(2));
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;

		final List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		assertEquals(4, subOperations.size());

		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId useCase2Id = ModelUtil.getProject(useCase2).getModelElementId(useCase2);
		final ModelElementId useCase3Id = ModelUtil.getProject(useCase3).getModelElementId(useCase3);

		for (int i = 0; i < 3; i++) {
			final AbstractOperation opn = subOperations.get(i);
			assertTrue(opn instanceof SingleReferenceOperation);
			final SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) opn;

			assertEquals(TestElementFeatures.nonContainedNTo1().getName(), singleReferenceOperation.getFeatureName());
			assertEquals(TestElementFeatures.nonContained1ToN().getName(),
				singleReferenceOperation.getOppositeFeatureName());
			assertEquals(ModelUtil.getProject(useCases.get(i)).getModelElementId(useCases.get(i)),
				singleReferenceOperation.getModelElementId());
			assertNull(singleReferenceOperation.getOldValue());
			assertEquals(actorId, singleReferenceOperation.getNewValue());
			assertTrue(singleReferenceOperation.isBidirectional());
		}

		operation = subOperations.get(3);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) operation;

		assertEquals(TestElementFeatures.nonContained1ToN().getName(), multiReferenceOperation.getFeatureName());
		assertEquals(0, multiReferenceOperation.getIndex());
		assertEquals(actorId, multiReferenceOperation.getModelElementId());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), multiReferenceOperation.getOppositeFeatureName());
		assertTrue(multiReferenceOperation.isAdd());
		assertTrue(multiReferenceOperation.isBidirectional());

		final EList<ModelElementId> referencedModelElements = multiReferenceOperation.getReferencedModelElements();
		assertEquals(3, referencedModelElements.size());
		assertEquals(useCaseId, referencedModelElements.get(0));
		assertEquals(useCase2Id, referencedModelElements.get(1));
		assertEquals(useCase3Id, referencedModelElements.get(2));

		final Set<ModelElementId> otherInvolvedModelElements = multiReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(3, otherInvolvedModelElements.size());
		assertTrue(otherInvolvedModelElements.contains(useCaseId));
		assertTrue(otherInvolvedModelElements.contains(useCase2Id));
		assertTrue(otherInvolvedModelElements.contains(useCase3Id));

	}

	/**
	 * Change a multi reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void removeManyMultiReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement useCase3 = Create.testElement();
		final List<TestElement> useCases = new ArrayList<TestElement>();
		useCases.add(useCase);
		useCases.add(useCase2);
		useCases.add(useCase3);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);
				getProject().addModelElement(useCase2);
				getProject().addModelElement(useCase3);
				actor.getNonContained_1ToN().addAll(useCases);

				clearOperations();

				assertEquals(actor, useCase.getNonContained_NTo1());
				assertEquals(actor, useCase2.getNonContained_NTo1());
				assertEquals(actor, useCase3.getNonContained_NTo1());
				final EList<TestElement> initiatedTestElements = actor.getNonContained_1ToN();
				assertEquals(3, initiatedTestElements.size());
				assertEquals(useCase, initiatedTestElements.get(0));
				assertEquals(useCase2, initiatedTestElements.get(1));
				assertEquals(useCase3, initiatedTestElements.get(2));
			}
		}.run(false);

		Delete.fromNonContained1ToN(actor, useCases);

		assertNull(useCase.getNonContained_NTo1());
		assertNull(useCase2.getNonContained_NTo1());
		assertNull(useCase3.getNonContained_NTo1());
		assertEquals(0, actor.getNonContained_1ToN().size());

		assertEquals(1, getProjectSpace().getOperations().size());

		final List<AbstractOperation> subOperations = checkAndCast(getProjectSpace().getOperations().get(0),
			CompositeOperation.class).getSubOperations();

		assertEquals(4, subOperations.size());

		final ModelElementId actorId = getProject().getModelElementId(actor);
		final ModelElementId useCaseId = getProject().getModelElementId(useCase);
		final ModelElementId useCase2Id = getProject().getModelElementId(useCase2);
		final ModelElementId useCase3Id = getProject().getModelElementId(useCase3);

		for (int i = 0; i < 3; i++) {
			final SingleReferenceOperation singleReferenceOperation = checkAndCast(subOperations.get(i),
				SingleReferenceOperation.class);

			assertEquals(TestElementFeatures.nonContainedNTo1().getName(),
				singleReferenceOperation.getFeatureName());
			assertEquals(TestElementFeatures.nonContained1ToN().getName(),
				singleReferenceOperation.getOppositeFeatureName());
			assertEquals(ModelUtil.getProject(useCases.get(i)).getModelElementId(useCases.get(i)),
				singleReferenceOperation.getModelElementId());
			assertEquals(actorId, singleReferenceOperation.getOldValue());
			assertNull(singleReferenceOperation.getNewValue());
			assertTrue(singleReferenceOperation.isBidirectional());
		}

		final MultiReferenceOperation multiReferenceOperation = checkAndCast(subOperations.get(3),
			MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.nonContained1ToN().getName(), multiReferenceOperation.getFeatureName());
		assertEquals(0, multiReferenceOperation.getIndex());
		assertEquals(actorId, multiReferenceOperation.getModelElementId());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), multiReferenceOperation.getOppositeFeatureName());
		assertFalse(multiReferenceOperation.isAdd());
		assertTrue(multiReferenceOperation.isBidirectional());

		final EList<ModelElementId> referencedModelElements = multiReferenceOperation.getReferencedModelElements();
		assertEquals(3, referencedModelElements.size());
		assertEquals(useCaseId, referencedModelElements.get(0));
		assertEquals(useCase2Id, referencedModelElements.get(1));
		assertEquals(useCase3Id, referencedModelElements.get(2));

		final Set<ModelElementId> otherInvolvedModelElements = multiReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(3, otherInvolvedModelElements.size());
		assertTrue(otherInvolvedModelElements.contains(useCaseId));
		assertTrue(otherInvolvedModelElements.contains(useCase2Id));
		assertTrue(otherInvolvedModelElements.contains(useCase3Id));

	}

	@Test
	public void unsetMultiReferenceTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Tournament tournament1 = BowlingFactory.eINSTANCE.createTournament();
		final Tournament tournament2 = BowlingFactory.eINSTANCE.createTournament();
		final Tournament tournament3 = BowlingFactory.eINSTANCE.createTournament();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(tournament1);
				getProject().addModelElement(tournament2);
				getProject().addModelElement(tournament3);
				fan.getVisitedTournaments().add(tournament1);
				fan.getVisitedTournaments().add(tournament2);
				fan.getVisitedTournaments().add(tournament3);
				assertEquals(3, fan.getVisitedTournaments().size());
				assertTrue(fan.isSetVisitedTournaments());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetVisitedTournaments();
				assertEquals(0, fan.getVisitedTournaments().size());
				assertTrue(!fan.isSetVisitedTournaments());
				assertTrue(getProject().getAllModelElements().contains(tournament1));
				assertTrue(getProject().getAllModelElements().contains(tournament2));
				assertTrue(getProject().getAllModelElements().contains(tournament3));
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(2, operations.size());

		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		final AbstractOperation operation2 = operations.get(1);
		assertTrue(operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		final ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, mrop1.getModelElementId());
		assertEquals(fanId, mrop2.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				operations.get(0).apply(secondProject);
				operations.get(1).apply(secondProject);
			}
		}.run(false);

		assertEquals(0, ((Fan) secondProject.getModelElements().get(0)).getVisitedTournaments().size());
		assertTrue(!((Fan) secondProject.getModelElements().get(0)).isSetVisitedTournaments());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void reverseUnsetMultiReferenceTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Tournament tournament1 = BowlingFactory.eINSTANCE.createTournament();
		final Tournament tournament2 = BowlingFactory.eINSTANCE.createTournament();
		final Tournament tournament3 = BowlingFactory.eINSTANCE.createTournament();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(tournament1);
				getProject().addModelElement(tournament2);
				getProject().addModelElement(tournament3);
				fan.getVisitedTournaments().add(tournament1);
				fan.getVisitedTournaments().add(tournament2);
				fan.getVisitedTournaments().add(tournament3);
				assertEquals(3, fan.getVisitedTournaments().size());
				assertTrue(fan.isSetVisitedTournaments());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetVisitedTournaments();
				assertEquals(0, fan.getVisitedTournaments().size());
				assertTrue(!fan.isSetVisitedTournaments());
				assertTrue(getProject().getAllModelElements().contains(tournament1));
				assertTrue(getProject().getAllModelElements().contains(tournament2));
				assertTrue(getProject().getAllModelElements().contains(tournament3));
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(2, operations.size());

		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		final AbstractOperation operation2 = operations.get(1);
		assertTrue(operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		final ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, mrop1.getModelElementId());
		assertEquals(fanId, mrop2.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mrop2.reverse().apply(getProject());
				mrop1.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(3, fan.getVisitedTournaments().size());
		assertTrue(fan.getVisitedTournaments().contains(tournament1));
		assertTrue(fan.getVisitedTournaments().contains(tournament2));
		assertTrue(fan.getVisitedTournaments().contains(tournament3));
		assertTrue(fan.isSetVisitedTournaments());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void doubleReverseUnsetMultiReferenceTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Tournament tournament1 = BowlingFactory.eINSTANCE.createTournament();
		final Tournament tournament2 = BowlingFactory.eINSTANCE.createTournament();
		final Tournament tournament3 = BowlingFactory.eINSTANCE.createTournament();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(tournament1);
				getProject().addModelElement(tournament2);
				getProject().addModelElement(tournament3);
				fan.getVisitedTournaments().add(tournament1);
				fan.getVisitedTournaments().add(tournament2);
				fan.getVisitedTournaments().add(tournament3);
				assertEquals(3, fan.getVisitedTournaments().size());
				assertTrue(fan.isSetVisitedTournaments());
			}
		}.run(false);

		clearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetVisitedTournaments();
				assertEquals(0, fan.getVisitedTournaments().size());
				assertTrue(!fan.isSetVisitedTournaments());
				assertTrue(getProject().getAllModelElements().contains(tournament1));
				assertTrue(getProject().getAllModelElements().contains(tournament2));
				assertTrue(getProject().getAllModelElements().contains(tournament3));
			}
		}.run(false);

		final Project secondProject = ModelUtil.clone(getProject());
		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(2, operations.size());

		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		final AbstractOperation operation2 = operations.get(1);
		assertTrue(operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		final ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, mrop1.getModelElementId());
		assertEquals(fanId, mrop2.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mrop1.reverse().reverse().apply(getProject());
				mrop2.reverse().reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(0, fan.getVisitedTournaments().size());
		assertTrue(!fan.isSetVisitedTournaments());
		assertTrue(getProject().getAllModelElements().contains(tournament1));
		assertTrue(getProject().getAllModelElements().contains(tournament2));
		assertTrue(getProject().getAllModelElements().contains(tournament3));
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void reverseSetOfUnsettedMultiReferenceTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Tournament tournament1 = BowlingFactory.eINSTANCE.createTournament();
		final Tournament tournament2 = BowlingFactory.eINSTANCE.createTournament();
		final Tournament tournament3 = BowlingFactory.eINSTANCE.createTournament();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(tournament1);
				getProject().addModelElement(tournament2);
				getProject().addModelElement(tournament3);
				assertEquals(0, fan.getVisitedTournaments().size());
				assertTrue(!fan.isSetVisitedTournaments());
				assertTrue(getProject().getAllModelElements().contains(tournament1));
				assertTrue(getProject().getAllModelElements().contains(tournament2));
				assertTrue(getProject().getAllModelElements().contains(tournament3));
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.getVisitedTournaments().add(tournament1);
				fan.getVisitedTournaments().add(tournament2);
				fan.getVisitedTournaments().add(tournament3);
				assertEquals(3, fan.getVisitedTournaments().size());
				assertTrue(fan.isSetVisitedTournaments());
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(3, operations.size());

		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		final AbstractOperation operation2 = operations.get(1);
		assertTrue(operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		final AbstractOperation operation3 = operations.get(2);
		assertTrue(operation3 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop3 = (MultiReferenceOperation) operation3;

		final ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, mrop1.getModelElementId());
		assertEquals(fanId, mrop2.getModelElementId());
		assertEquals(fanId, mrop3.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mrop3.reverse().apply(getProject());
				mrop2.reverse().apply(getProject());
				mrop1.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(0, fan.getVisitedTournaments().size());
		assertTrue(!fan.isSetVisitedTournaments());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void unsetMultiContainmentReferenceTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Merchandise merch1 = BowlingFactory.eINSTANCE.createMerchandise();
		final Merchandise merch2 = BowlingFactory.eINSTANCE.createMerchandise();
		final Merchandise merch3 = BowlingFactory.eINSTANCE.createMerchandise();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(merch1);
				getProject().addModelElement(merch2);
				getProject().addModelElement(merch3);
				fan.getFanMerchandise().add(merch1);
				fan.getFanMerchandise().add(merch2);
				fan.getFanMerchandise().add(merch3);
				assertEquals(3, fan.getFanMerchandise().size());
				assertTrue(fan.isSetFanMerchandise());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFanMerchandise();
				assertEquals(0, fan.getFanMerchandise().size());
				assertTrue(!fan.isSetFanMerchandise());
				assertTrue(!getProject().getAllModelElements().contains(merch1));
				assertTrue(!getProject().getAllModelElements().contains(merch2));
				assertTrue(!getProject().getAllModelElements().contains(merch3));
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(5, operations.size());

		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		final AbstractOperation operation2 = operations.get(1);
		assertTrue(operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		final AbstractOperation operation3 = operations.get(2);
		assertTrue(operation3 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp1 = (CreateDeleteOperation) operation3;

		final AbstractOperation operation4 = operations.get(3);
		assertTrue(operation4 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp2 = (CreateDeleteOperation) operation4;

		final AbstractOperation operation5 = operations.get(4);
		assertTrue(operation5 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp3 = (CreateDeleteOperation) operation5;

		final ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, mrop1.getModelElementId());
		assertEquals(fanId, mrop2.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mrop1.apply(secondProject);
				mrop2.apply(secondProject);
				creaDelOp1.apply(secondProject);
				creaDelOp2.apply(secondProject);
				creaDelOp3.apply(secondProject);
			}
		}.run(false);

		assertEquals(0, ((Fan) secondProject.getModelElements().get(0)).getFanMerchandise().size());
		assertTrue(!((Fan) secondProject.getModelElements().get(0)).isSetFanMerchandise());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void reverseUnsetMultiContainmentReferenceTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Merchandise merch1 = BowlingFactory.eINSTANCE.createMerchandise();
		final Merchandise merch2 = BowlingFactory.eINSTANCE.createMerchandise();
		final Merchandise merch3 = BowlingFactory.eINSTANCE.createMerchandise();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(merch1);
				getProject().addModelElement(merch2);
				getProject().addModelElement(merch3);
				fan.getFanMerchandise().add(merch1);
				fan.getFanMerchandise().add(merch2);
				fan.getFanMerchandise().add(merch3);
				assertEquals(3, fan.getFanMerchandise().size());
				assertTrue(fan.isSetFanMerchandise());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFanMerchandise();
				assertEquals(0, fan.getFanMerchandise().size());
				assertTrue(!fan.isSetFanMerchandise());
				assertTrue(!getProject().getAllModelElements().contains(merch1));
				assertTrue(!getProject().getAllModelElements().contains(merch2));
				assertTrue(!getProject().getAllModelElements().contains(merch3));
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(5, operations.size());

		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		final AbstractOperation operation2 = operations.get(1);
		assertTrue(operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		final AbstractOperation operation3 = operations.get(2);
		assertTrue(operation3 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp1 = (CreateDeleteOperation) operation3;

		final AbstractOperation operation4 = operations.get(3);
		assertTrue(operation4 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp2 = (CreateDeleteOperation) operation4;

		final AbstractOperation operation5 = operations.get(4);
		assertTrue(operation5 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp3 = (CreateDeleteOperation) operation5;

		final ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, mrop1.getModelElementId());
		assertEquals(fanId, mrop2.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				creaDelOp3.reverse().apply(getProject());
				creaDelOp2.reverse().apply(getProject());
				creaDelOp1.reverse().apply(getProject());
				mrop2.reverse().apply(getProject());
				mrop1.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(3, fan.getFanMerchandise().size());
		assertTrue(fan.isSetFanMerchandise());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void doubleReverseUnsetMultiContainmentReferenceTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Merchandise merch1 = BowlingFactory.eINSTANCE.createMerchandise();
		final Merchandise merch2 = BowlingFactory.eINSTANCE.createMerchandise();
		final Merchandise merch3 = BowlingFactory.eINSTANCE.createMerchandise();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(merch1);
				getProject().addModelElement(merch2);
				getProject().addModelElement(merch3);
				fan.getFanMerchandise().add(merch1);
				fan.getFanMerchandise().add(merch2);
				fan.getFanMerchandise().add(merch3);
				assertEquals(3, fan.getFanMerchandise().size());
				assertTrue(fan.isSetFanMerchandise());
			}
		}.run(false);

		clearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFanMerchandise();
				assertEquals(0, fan.getFanMerchandise().size());
				assertTrue(!fan.isSetFanMerchandise());
				assertTrue(!getProject().getAllModelElements().contains(merch1));
				assertTrue(!getProject().getAllModelElements().contains(merch2));
				assertTrue(!getProject().getAllModelElements().contains(merch3));
			}
		}.run(false);

		final Project secondProject = ModelUtil.clone(getProject());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(5, operations.size());

		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		final AbstractOperation operation2 = operations.get(1);
		assertTrue(operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		final AbstractOperation operation3 = operations.get(2);
		assertTrue(operation3 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp1 = (CreateDeleteOperation) operation3;

		final AbstractOperation operation4 = operations.get(3);
		assertTrue(operation4 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp2 = (CreateDeleteOperation) operation4;

		final AbstractOperation operation5 = operations.get(4);
		assertTrue(operation5 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp3 = (CreateDeleteOperation) operation5;

		final ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, mrop1.getModelElementId());
		assertEquals(fanId, mrop2.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mrop1.reverse().reverse().apply(getProject());
				mrop2.reverse().reverse().apply(getProject());
				creaDelOp1.reverse().reverse().apply(getProject());
				creaDelOp2.reverse().reverse().apply(getProject());
				creaDelOp3.reverse().reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(0, fan.getFanMerchandise().size());
		assertTrue(!fan.isSetFanMerchandise());
		assertTrue(!getProject().getAllModelElements().contains(merch1));
		assertTrue(!getProject().getAllModelElements().contains(merch2));
		assertTrue(!getProject().getAllModelElements().contains(merch3));
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void reverseSetOfUnsettedMultiContainmentReferenceTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Merchandise merch1 = BowlingFactory.eINSTANCE.createMerchandise();
		final Merchandise merch2 = BowlingFactory.eINSTANCE.createMerchandise();
		final Merchandise merch3 = BowlingFactory.eINSTANCE.createMerchandise();
		merch1.setName(M1);
		merch2.setName(M2);
		merch3.setName(M3);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(merch1);
				getProject().addModelElement(merch2);
				getProject().addModelElement(merch3);
				assertEquals(0, fan.getFanMerchandise().size());
				assertFalse(fan.isSetFanMerchandise());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.getFanMerchandise().add(merch1);
				fan.getFanMerchandise().add(merch2);
				fan.getFanMerchandise().add(merch3);
				assertEquals(3, fan.getFanMerchandise().size());
				assertTrue(fan.isSetFanMerchandise());
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(3, operations.size());

		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		final AbstractOperation operation2 = operations.get(1);
		assertTrue(operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		final AbstractOperation operation3 = operations.get(2);
		assertTrue(operation3 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop3 = (MultiReferenceOperation) operation3;

		final ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, mrop1.getModelElementId());
		assertEquals(fanId, mrop2.getModelElementId());
		assertEquals(fanId, mrop3.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mrop3.reverse().apply(getProject());
				mrop2.reverse().apply(getProject());
				mrop1.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(0, fan.getFanMerchandise().size());
		assertTrue(!fan.isSetFanMerchandise());
		assertEquals(secondProject.getModelElements().size(), getProject().getModelElements().size());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void setUnsetMultiReferenceToEmpty() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				assertEquals(0, fan.getVisitedTournaments().size());
				assertTrue(!fan.isSetVisitedTournaments());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.getVisitedTournaments().clear();
				assertEquals(0, fan.getVisitedTournaments().size());
				assertTrue(fan.isSetVisitedTournaments());
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation multRefOp = (MultiReferenceOperation) operation;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				multRefOp.apply(secondProject);
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}
}
