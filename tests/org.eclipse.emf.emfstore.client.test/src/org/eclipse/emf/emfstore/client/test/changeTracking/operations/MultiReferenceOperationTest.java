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
package org.eclipse.emf.emfstore.client.test.changeTracking.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.bowling.Merchandise;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.model.requirement.Actor;
import org.eclipse.emf.emfstore.client.test.model.requirement.RequirementFactory;
import org.eclipse.emf.emfstore.client.test.model.requirement.UseCase;
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
import org.junit.Test;

/**
 * Tests the MultiReferenceOperation.
 * 
 * @author koegel
 */
public class MultiReferenceOperationTest extends WorkspaceTest {

	/**
	 * Change a multi reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void changeMultiReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		final Actor actor = RequirementFactory.eINSTANCE.createActor();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				// useCase.setIdentifier("usecase");
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				clearOperations();

				actor.getInitiatedUseCases().add(useCase);

			}
		}.run(false);
		assertEquals(actor, useCase.getInitiatingActor());
		EList<UseCase> initiatedUseCases = actor.getInitiatedUseCases();
		assertEquals(1, initiatedUseCases.size());
		assertEquals(useCase, initiatedUseCases.get(0));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		CompositeOperation compositeOperation = (CompositeOperation) operation;

		List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		operation = subOperations.get(0);
		assertTrue(operation instanceof SingleReferenceOperation);
		SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) operation;

		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);

		assertEquals("initiatingActor", singleReferenceOperation.getFeatureName());
		assertEquals("initiatedUseCases", singleReferenceOperation.getOppositeFeatureName());
		assertEquals(useCaseId, singleReferenceOperation.getModelElementId());
		assertEquals(null, singleReferenceOperation.getOldValue());
		assertEquals(actorId, singleReferenceOperation.getNewValue());
		assertTrue(singleReferenceOperation.isBidirectional());

		operation = subOperations.get(1);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) operation;

		assertEquals("initiatedUseCases", multiReferenceOperation.getFeatureName());
		assertEquals(0, multiReferenceOperation.getIndex());
		assertEquals(actorId, multiReferenceOperation.getModelElementId());
		assertEquals("initiatingActor", multiReferenceOperation.getOppositeFeatureName());
		assertEquals(true, multiReferenceOperation.isAdd());
		assertEquals(true, multiReferenceOperation.isBidirectional());

		EList<ModelElementId> referencedModelElements = multiReferenceOperation.getReferencedModelElements();
		assertEquals(1, referencedModelElements.size());
		assertEquals(useCaseId, referencedModelElements.get(0));

		Set<ModelElementId> otherInvolvedModelElements = multiReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements.size());
		assertEquals(true, otherInvolvedModelElements.contains(useCaseId));

	}

	/**
	 * Change a multi reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void reverseMultiReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		final Actor actor = RequirementFactory.eINSTANCE.createActor();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				// useCase.setIdentifier("usecase1");
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				clearOperations();

				actor.getInitiatedUseCases().add(useCase);

				assertEquals(actor, useCase.getInitiatingActor());
				EList<UseCase> initiatedUseCases = actor.getInitiatedUseCases();
				assertEquals(1, initiatedUseCases.size());
				assertEquals(useCase, initiatedUseCases.get(0));
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation comp = operations.get(0);
		assertEquals(true, comp instanceof CompositeOperation);

		List<AbstractOperation> subOperations = ((CompositeOperation) comp).getSubOperations();

		assertEquals(2, subOperations.size());

		// skipping singlereference op
		AbstractOperation operation = subOperations.get(1);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) operation;

		AbstractOperation reverse = multiReferenceOperation.reverse();
		assertEquals(true, reverse instanceof MultiReferenceOperation);

		ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		final MultiReferenceOperation reversedMultiReferenceOperation = (MultiReferenceOperation) reverse;
		assertEquals("initiatedUseCases", reversedMultiReferenceOperation.getFeatureName());
		assertEquals(0, reversedMultiReferenceOperation.getIndex());
		assertEquals(actorId, reversedMultiReferenceOperation.getModelElementId());
		assertEquals("initiatingActor", reversedMultiReferenceOperation.getOppositeFeatureName());
		assertEquals(false, reversedMultiReferenceOperation.isAdd());
		assertEquals(true, reversedMultiReferenceOperation.isBidirectional());

		EList<ModelElementId> referencedModelElements = reversedMultiReferenceOperation.getReferencedModelElements();
		assertEquals(1, referencedModelElements.size());
		assertEquals(useCaseId, referencedModelElements.get(0));

		Set<ModelElementId> otherInvolvedModelElements = reversedMultiReferenceOperation
			.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements.size());
		assertEquals(true, otherInvolvedModelElements.contains(useCaseId));

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				reversedMultiReferenceOperation.apply(getProject());
			}
		}.run(false);

		assertEquals(0, actor.getInitiatedUseCases().size());
		assertEquals(null, useCase.getInitiatingActor());
	}

	/**
	 * Change a multi reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void addManyMultiReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		final Actor actor = RequirementFactory.eINSTANCE.createActor();
		final UseCase useCase2 = RequirementFactory.eINSTANCE.createUseCase();
		final UseCase useCase3 = RequirementFactory.eINSTANCE.createUseCase();
		final List<UseCase> useCases = new ArrayList<UseCase>();
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

				actor.getInitiatedUseCases().addAll(useCases);

				assertEquals(actor, useCase.getInitiatingActor());
				assertEquals(actor, useCase2.getInitiatingActor());
				assertEquals(actor, useCase3.getInitiatingActor());
				EList<UseCase> initiatedUseCases = actor.getInitiatedUseCases();
				assertEquals(3, initiatedUseCases.size());
				assertEquals(useCase, initiatedUseCases.get(0));
				assertEquals(useCase2, initiatedUseCases.get(1));
				assertEquals(useCase3, initiatedUseCases.get(2));
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		CompositeOperation compositeOperation = (CompositeOperation) operation;

		List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		assertEquals(4, subOperations.size());

		ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		ModelElementId useCase2Id = ModelUtil.getProject(useCase2).getModelElementId(useCase2);
		ModelElementId useCase3Id = ModelUtil.getProject(useCase3).getModelElementId(useCase3);

		for (int i = 0; i < 3; i++) {
			AbstractOperation opn = subOperations.get(i);
			assertTrue(opn instanceof SingleReferenceOperation);
			SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) opn;

			assertEquals("initiatingActor", singleReferenceOperation.getFeatureName());
			assertEquals("initiatedUseCases", singleReferenceOperation.getOppositeFeatureName());
			assertEquals(ModelUtil.getProject(useCases.get(i)).getModelElementId(useCases.get(i)),
				singleReferenceOperation.getModelElementId());
			assertEquals(null, singleReferenceOperation.getOldValue());
			assertEquals(actorId, singleReferenceOperation.getNewValue());
			assertTrue(singleReferenceOperation.isBidirectional());
		}

		operation = subOperations.get(3);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) operation;

		assertEquals("initiatedUseCases", multiReferenceOperation.getFeatureName());
		assertEquals(0, multiReferenceOperation.getIndex());
		assertEquals(actorId, multiReferenceOperation.getModelElementId());
		assertEquals("initiatingActor", multiReferenceOperation.getOppositeFeatureName());
		assertEquals(true, multiReferenceOperation.isAdd());
		assertEquals(true, multiReferenceOperation.isBidirectional());

		EList<ModelElementId> referencedModelElements = multiReferenceOperation.getReferencedModelElements();
		assertEquals(3, referencedModelElements.size());
		assertEquals(useCaseId, referencedModelElements.get(0));
		assertEquals(useCase2Id, referencedModelElements.get(1));
		assertEquals(useCase3Id, referencedModelElements.get(2));

		Set<ModelElementId> otherInvolvedModelElements = multiReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(3, otherInvolvedModelElements.size());
		assertEquals(true, otherInvolvedModelElements.contains(useCaseId));
		assertEquals(true, otherInvolvedModelElements.contains(useCase2Id));
		assertEquals(true, otherInvolvedModelElements.contains(useCase3Id));

	}

	/**
	 * Change a multi reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void removeManyMultiReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		final Actor actor = RequirementFactory.eINSTANCE.createActor();
		final UseCase useCase2 = RequirementFactory.eINSTANCE.createUseCase();
		final UseCase useCase3 = RequirementFactory.eINSTANCE.createUseCase();
		final List<UseCase> useCases = new ArrayList<UseCase>();
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
				actor.getInitiatedUseCases().addAll(useCases);

				clearOperations();

				assertEquals(actor, useCase.getInitiatingActor());
				assertEquals(actor, useCase2.getInitiatingActor());
				assertEquals(actor, useCase3.getInitiatingActor());
				EList<UseCase> initiatedUseCases = actor.getInitiatedUseCases();
				assertEquals(3, initiatedUseCases.size());
				assertEquals(useCase, initiatedUseCases.get(0));
				assertEquals(useCase2, initiatedUseCases.get(1));
				assertEquals(useCase3, initiatedUseCases.get(2));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.getInitiatedUseCases().removeAll(useCases);
			}
		}.run(false);

		assertEquals(null, useCase.getInitiatingActor());
		assertEquals(null, useCase2.getInitiatingActor());
		assertEquals(null, useCase3.getInitiatingActor());
		EList<UseCase> initiatedUseCases = actor.getInitiatedUseCases();
		assertEquals(0, initiatedUseCases.size());

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		CompositeOperation compositeOperation = (CompositeOperation) operation;

		List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		assertEquals(4, subOperations.size());

		ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		ModelElementId useCase2Id = ModelUtil.getProject(useCase2).getModelElementId(useCase2);
		ModelElementId useCase3Id = ModelUtil.getProject(useCase3).getModelElementId(useCase3);

		for (int i = 0; i < 3; i++) {
			AbstractOperation opn = subOperations.get(i);
			assertTrue(opn instanceof SingleReferenceOperation);
			SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) opn;

			assertEquals("initiatingActor", singleReferenceOperation.getFeatureName());
			assertEquals("initiatedUseCases", singleReferenceOperation.getOppositeFeatureName());
			assertEquals(ModelUtil.getProject(useCases.get(i)).getModelElementId(useCases.get(i)),
				singleReferenceOperation.getModelElementId());
			assertEquals(actorId, singleReferenceOperation.getOldValue());
			assertEquals(null, singleReferenceOperation.getNewValue());
			assertTrue(singleReferenceOperation.isBidirectional());
		}

		operation = subOperations.get(3);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) operation;

		assertEquals("initiatedUseCases", multiReferenceOperation.getFeatureName());
		assertEquals(0, multiReferenceOperation.getIndex());
		assertEquals(actorId, multiReferenceOperation.getModelElementId());
		assertEquals("initiatingActor", multiReferenceOperation.getOppositeFeatureName());
		assertEquals(false, multiReferenceOperation.isAdd());
		assertEquals(true, multiReferenceOperation.isBidirectional());

		EList<ModelElementId> referencedModelElements = multiReferenceOperation.getReferencedModelElements();
		assertEquals(3, referencedModelElements.size());
		assertEquals(useCaseId, referencedModelElements.get(0));
		assertEquals(useCase2Id, referencedModelElements.get(1));
		assertEquals(useCase3Id, referencedModelElements.get(2));

		Set<ModelElementId> otherInvolvedModelElements = multiReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(3, otherInvolvedModelElements.size());
		assertEquals(true, otherInvolvedModelElements.contains(useCaseId));
		assertEquals(true, otherInvolvedModelElements.contains(useCase2Id));
		assertEquals(true, otherInvolvedModelElements.contains(useCase3Id));

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

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
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

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
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

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
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

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		AbstractOperation operation3 = operations.get(2);
		assertEquals(true, operation3 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop3 = (MultiReferenceOperation) operation3;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
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

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		AbstractOperation operation3 = operations.get(2);
		assertEquals(true, operation3 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp1 = (CreateDeleteOperation) operation3;

		AbstractOperation operation4 = operations.get(3);
		assertEquals(true, operation4 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp2 = (CreateDeleteOperation) operation4;

		AbstractOperation operation5 = operations.get(4);
		assertEquals(true, operation5 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp3 = (CreateDeleteOperation) operation5;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
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

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		AbstractOperation operation3 = operations.get(2);
		assertEquals(true, operation3 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp1 = (CreateDeleteOperation) operation3;

		AbstractOperation operation4 = operations.get(3);
		assertEquals(true, operation4 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp2 = (CreateDeleteOperation) operation4;

		AbstractOperation operation5 = operations.get(4);
		assertEquals(true, operation5 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp3 = (CreateDeleteOperation) operation5;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
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

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		AbstractOperation operation3 = operations.get(2);
		assertEquals(true, operation3 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp1 = (CreateDeleteOperation) operation3;

		AbstractOperation operation4 = operations.get(3);
		assertEquals(true, operation4 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp2 = (CreateDeleteOperation) operation4;

		AbstractOperation operation5 = operations.get(4);
		assertEquals(true, operation5 instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp3 = (CreateDeleteOperation) operation5;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
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
		merch1.setName("M1");
		merch2.setName("M2");
		merch3.setName("M3");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(merch1);
				getProject().addModelElement(merch2);
				getProject().addModelElement(merch3);
				assertEquals(0, fan.getFanMerchandise().size());
				assertTrue(!fan.isSetFanMerchandise());
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

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop1 = (MultiReferenceOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop2 = (MultiReferenceOperation) operation2;

		AbstractOperation operation3 = operations.get(2);
		assertEquals(true, operation3 instanceof MultiReferenceOperation);
		final MultiReferenceOperation mrop3 = (MultiReferenceOperation) operation3;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
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

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiReferenceOperation);
		final MultiReferenceOperation multRefOp = (MultiReferenceOperation) operation;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				multRefOp.apply(secondProject);
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	// /**
	// * Checks whether the elist contains list works correctly.
	// *
	// * @throws UnsupportedOperationException on test fail
	// * @throws UnsupportedNotificationException on test fail
	// */
	// @Test
	// public void listContainsTest() throws UnsupportedOperationException, UnsupportedNotificationException {
	//
	// UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
	// useCase.setIdentifier("usecase");
	// LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
	// getProject().addModelElement(section);
	//
	// clearOperations();
	// section.getModelElements().add(useCase);
	//
	// List<AbstractOperation> ops = getProjectSpace().getLocalOperations().getOperations();
	//
	// MultiReferenceOperation addOp = (MultiReferenceOperation) ops.get(1);
	//
	// for (ModelElementId id : addOp.getReferencedModelElements()) {
	// if (id.equals(useCase.getModelElementId())) {
	// assertTrue(addOp.getReferencedModelElements().contains(useCase.getModelElementId()));
	// }
	// }
	//
	// }

}