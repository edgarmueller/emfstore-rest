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

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.bowling.Merchandise;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.test.common.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Update;
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
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.ReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.UnsetType;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.impl.MultiReferenceOperationImpl;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.eclipse.emf.emfstore.test.model.TestmodelPackage;
import org.junit.Test;

/**
 * Tests the SingleReferenceOperation.
 * 
 * @author koegel
 */
public class SingleReferenceOperationTest extends ESTest {

	private static final String FAVOURITE_MERCHANDISE = "favouriteMerchandise"; //$NON-NLS-1$
	private static final String FAVOURITE_PLAYER = "favouritePlayer"; //$NON-NLS-1$
	private static final String PROPOSAL2 = "proposal"; //$NON-NLS-1$
	private Project expectedProject;

	/**
	 * Change a single reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void changeSingleReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();

		ProjectUtil.addElement(getProjectSpace().toAPI(), useCase);
		ProjectUtil.addElement(getProjectSpace().toAPI(), actor);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				clearOperations();

				useCase.setNonContained_NTo1(actor);

				assertEquals(actor, useCase.getNonContained_NTo1());
				final EList<TestElement> initiatedTestElements = actor.getNonContained_1ToN();
				assertEquals(1, initiatedTestElements.size());
				assertEquals(useCase, initiatedTestElements.get(0));
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);

		final EList<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(2, subOperations.size());

		operation = subOperations.get(0);
		assertTrue(operation instanceof MultiReferenceOperationImpl);
		final MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) operation;
		assertEquals(TestElementFeatures.nonContained1ToN().getName(), multiReferenceOperation.getFeatureName());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), multiReferenceOperation.getOppositeFeatureName());

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);

		assertEquals(useCaseId, multiReferenceOperation.getReferencedModelElements().get(0));
		assertEquals(actorId, multiReferenceOperation.getModelElementId());
		assertTrue(multiReferenceOperation.isBidirectional());
		assertTrue(multiReferenceOperation.isAdd());
		assertEquals(1, multiReferenceOperation.getOtherInvolvedModelElements().size());

		operation = subOperations.get(1);
		assertTrue(operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) operation;

		assertEquals(null, singleReferenceOperation.getOldValue());
		assertEquals(actorId, singleReferenceOperation.getNewValue());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), singleReferenceOperation.getFeatureName());
		assertEquals(useCaseId, singleReferenceOperation.getModelElementId());
		assertEquals(TestElementFeatures.nonContained1ToN().getName(),
			singleReferenceOperation.getOppositeFeatureName());
		assertTrue(singleReferenceOperation.isBidirectional());
		final Set<ModelElementId> otherInvolvedModelElements = singleReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements.size());
		assertEquals(actorId, otherInvolvedModelElements.iterator().next());
	}

	/**
	 * Change an single reference twice and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	// commented out, single reference operations are not canonized at present
	// @Test
	// public void changeSingleReferenceTwice() throws UnsupportedOperationException, UnsupportedNotificationException {
	// TestElement useCase = Create.testElement;
	// getProject().addModelElement(useCase);
	// Actor oldActor = RequirementFactory.eINSTANCE.createActor();
	// getProject().addModelElement(oldActor);
	// Actor newActor = RequirementFactory.eINSTANCE.createActor();
	// getProject().addModelElement(newActor);
	//
	// clearOperations();
	//
	// useCase.setInitiatingActor(oldActor);
	// assertEquals(oldActor, useCase.getInitiatingActor());
	// EList<TestElement> initiatedTestElements = oldActor.getInitiatedTestElements();
	// assertEquals(1, initiatedTestElements.size());
	// assertEquals(useCase, initiatedTestElements.get(0));
	//
	// useCase.setInitiatingActor(newActor);
	// assertEquals(newActor, useCase.getInitiatingActor());
	// initiatedTestElements = newActor.getInitiatedTestElements();
	// assertEquals(1, initiatedTestElements.size());
	// assertEquals(useCase, initiatedTestElements.get(0));
	//
	// List<AbstractOperation> operations = getProjectSpace().getOperations();
	//
	// assertEquals(1, operations.size());
	// AbstractOperation operation = operations.get(0);
	// assertTrue(operation instanceof SingleReferenceOperation);
	// SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) operation;
	//
	// assertEquals(null, singleReferenceOperation.getOldValue());
	// assertEquals(newActor.getModelElementId(), singleReferenceOperation.getNewValue());
	// assertEquals(nonContained_NTo1, singleReferenceOperation.getFeatureName());
	// assertEquals(useCase.getModelElementId(), singleReferenceOperation.getModelElementId());
	// assertEquals("initiatedTestElements", singleReferenceOperation.getOppositeFeatureName());
	// assertTrue(singleReferenceOperation.isBidirectional());
	// Set<ModelElementId> otherInvolvedModelElements = singleReferenceOperation.getOtherInvolvedModelElements();
	// assertEquals(1, otherInvolvedModelElements.size());
	// assertEquals(newActor.getModelElementId(), otherInvolvedModelElements.iterator().next());
	// }
	/**
	 * Change an single reference and reverse it.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void reverseSingleReference() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		final TestElement oldActor = Create.testElement();
		final TestElement newActor = Create.testElement();

		Add.toProject(getLocalProject(), useCase);
		Add.toProject(getLocalProject(), oldActor);
		Add.toProject(getLocalProject(), newActor);
		Update.testElement(TestElementFeatures.nonContainedNTo1(), useCase, oldActor);
		assertEquals(oldActor, useCase.getNonContained_NTo1());

		assertEquals(1, oldActor.getNonContained_1ToN().size());
		assertEquals(useCase, oldActor.getNonContained_1ToN().get(0));
		clearOperations();

		Update.testElement(TestElementFeatures.nonContainedNTo1(), useCase, newActor);

		assertEquals(newActor, useCase.getNonContained_NTo1());
		assertEquals(1, newActor.getNonContained_1ToN().size());
		assertEquals(useCase, newActor.getNonContained_1ToN().get(0));

		assertEquals(1, getProjectSpace().getOperations().size());
		final List<AbstractOperation> operations = checkAndCast(getProjectSpace().getOperations().get(0),
			CompositeOperation.class).getSubOperations();
		assertEquals(3, operations.size());

		// note: skipping multireferenceop at index 0 in test, as it is not interesting in this context
		final SingleReferenceOperation singleReferenceOperation = checkAndCast(operations.get(2),
			SingleReferenceOperation.class);

		final ModelElementId useCaseId = getProject().getModelElementId(useCase);
		final ModelElementId oldActorId = getProject().getModelElementId(oldActor);
		final ModelElementId newActorId = getProject().getModelElementId(newActor);

		assertEquals(oldActorId, singleReferenceOperation.getOldValue());
		assertEquals(newActorId, singleReferenceOperation.getNewValue());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), singleReferenceOperation.getFeatureName());
		assertEquals(useCaseId, singleReferenceOperation.getModelElementId());
		assertEquals(TestElementFeatures.nonContained1ToN().getName(),
			singleReferenceOperation.getOppositeFeatureName());
		assertTrue(singleReferenceOperation.isBidirectional());
		Set<ModelElementId> otherInvolvedModelElements = singleReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(2, otherInvolvedModelElements.size());
		assertTrue(otherInvolvedModelElements.contains(oldActorId));
		assertTrue(otherInvolvedModelElements.contains(newActorId));

		final AbstractOperation reverse = singleReferenceOperation.reverse();
		assertTrue(reverse instanceof SingleReferenceOperation);
		final SingleReferenceOperation reversedSingleReferenceOperation = (SingleReferenceOperation) reverse;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				reversedSingleReferenceOperation.apply(getProject());
			}
		}.run(false);

		assertEquals(oldActor, useCase.getNonContained_NTo1());

		assertEquals(newActorId, reversedSingleReferenceOperation.getOldValue());
		assertEquals(oldActorId, reversedSingleReferenceOperation.getNewValue());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(),
			reversedSingleReferenceOperation.getFeatureName());
		assertEquals(useCaseId, reversedSingleReferenceOperation.getModelElementId());
		assertEquals(TestElementFeatures.nonContained1ToN().getName(),
			reversedSingleReferenceOperation.getOppositeFeatureName());
		assertTrue(reversedSingleReferenceOperation.isBidirectional());
		otherInvolvedModelElements = reversedSingleReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(2, otherInvolvedModelElements.size());
		assertTrue(otherInvolvedModelElements.contains(oldActorId));
		assertTrue(otherInvolvedModelElements.contains(newActorId));
	}

	/**
	 * Tests reversibility of 1:n single reference feature.
	 */
	@Test
	public void containmentSingleReferenceReversibilityTest() {

		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement section = Create.testElement();
		final TestElement oldSection = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);
				getProject().addModelElement(section);
				getProject().addModelElement(oldSection);
				useCase.setContainer(oldSection);
				actor.setContainer(oldSection);

				expectedProject = ModelUtil.clone(getProject());
				assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

				clearOperations();
				useCase.setContainer(section);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// composite operation containing a multiref operation and a singleref operation expected
		assertEquals(operations.size(), 1);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				final AbstractOperation reverse = operations.get(0).reverse();
				reverse.apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Move a containee to another container.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void moveContainmentReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final TestElement oldIssue = Create.testElement();
		final TestElement newIssue = Create.testElement();
		final TestElement proposal = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(oldIssue);
				getProject().addModelElement(newIssue);
				getProject().addModelElement(proposal);
				proposal.setContainer(oldIssue);
				clearOperations();

				assertEquals(0, newIssue.getContainedElements().size());
				assertEquals(1, oldIssue.getContainedElements().size());
				assertEquals(proposal, oldIssue.getContainedElements().get(0));
				assertEquals(oldIssue, proposal.getContainer());

				proposal.setContainer(newIssue);

				assertEquals(0, oldIssue.getContainedElements().size());
				assertEquals(1, newIssue.getContainedElements().size());
				assertEquals(proposal, newIssue.getContainedElements().get(0));
				assertEquals(newIssue, proposal.getContainer());
			}
		}.run(false);

		assertEquals(1, getProjectSpace().getOperations().size());

		final List<AbstractOperation> operations = checkAndCast(getProjectSpace().getOperations().get(0),
			CompositeOperation.class).getSubOperations();

		assertEquals(3, operations.size());

		MultiReferenceOperation multiReferenceOperation = checkAndCast(operations.get(0), MultiReferenceOperation.class);

		final ModelElementId oldIssueId = ModelUtil.getProject(oldIssue).getModelElementId(oldIssue);
		final ModelElementId proposalId = ModelUtil.getProject(proposal).getModelElementId(proposal);
		final ModelElementId newIssueId = ModelUtil.getProject(newIssue).getModelElementId(newIssue);

		assertFalse(multiReferenceOperation.isAdd());
		assertEquals(multiReferenceOperation.getModelElementId(), oldIssueId);
		assertEquals(proposalId, multiReferenceOperation.getReferencedModelElements().get(0));
		assertEquals(1, multiReferenceOperation.getReferencedModelElements().size());
		assertEquals(0, multiReferenceOperation.getIndex());

		multiReferenceOperation = checkAndCast(operations.get(1), MultiReferenceOperation.class);
		assertTrue(multiReferenceOperation.isAdd());
		assertEquals(newIssueId, multiReferenceOperation.getModelElementId());
		assertEquals(proposalId, multiReferenceOperation.getReferencedModelElements().get(0));
		assertEquals(1, multiReferenceOperation.getReferencedModelElements().size());
		assertEquals(0, multiReferenceOperation.getIndex());

		final SingleReferenceOperation singleReferenceOperation = checkAndCast(operations.get(2),
			SingleReferenceOperation.class);

		assertEquals(oldIssueId, singleReferenceOperation.getOldValue());
		assertEquals(newIssueId, singleReferenceOperation.getNewValue());

		assertEquals(TestmodelPackage.eINSTANCE.getTestElement_Container().getName(),
			singleReferenceOperation.getFeatureName());
		assertEquals(proposalId, singleReferenceOperation.getModelElementId());
		assertEquals(TestmodelPackage.eINSTANCE.getTestElement_ContainedElements().getName(),
			singleReferenceOperation.getOppositeFeatureName());

		assertTrue(singleReferenceOperation.isBidirectional());
		final Set<ModelElementId> otherInvolvedModelElements = singleReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(2, otherInvolvedModelElements.size());
		assertTrue(otherInvolvedModelElements.contains(newIssueId));
		assertTrue(otherInvolvedModelElements.contains(oldIssueId));
	}

	/**
	 * Test containment removing.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 */
	@Test
	public void removeContainment() throws UnsupportedOperationException {

		final TestElement issue = Create.testElement();
		final TestElement proposal = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				proposal.setName(PROPOSAL2);
				getProject().addModelElement(issue);
				getProject().addModelElement(proposal);
				proposal.setContainer(issue);
				clearOperations();
			}
		}.run(false);

		assertEquals(1, issue.getContainedElements().size());
		assertEquals(proposal, issue.getContainedElements().get(0));
		assertEquals(issue, proposal.getContainer());
		assertTrue(getProject().contains(issue));
		assertTrue(getProject().contains(proposal));
		assertEquals(getProject(), ModelUtil.getProject(issue));
		assertEquals(getProject(), ModelUtil.getProject(proposal));
		assertEquals(issue, proposal.eContainer());

		final ModelElementId proposalId = getProject().getModelElementId(proposal);
		Update.testElement(TestElementFeatures.container(), proposal, null);

		assertTrue(getProject().contains(issue));
		assertFalse(getProject().contains(proposal));
		assertEquals(getProject(), ModelUtil.getProject(issue));
		assertEquals(0, issue.getContainedElements().size());
		assertNull(proposal.getSrefContainer());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());

		final ModelElementId issueId = ModelUtil.getProject(issue).getModelElementId(issue);

		final List<ReferenceOperation> subOperations = checkAndCast(operations.get(0), CreateDeleteOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		final AbstractOperation op0 = subOperations.get(0);
		assertTrue(op0 instanceof MultiReferenceOperation);
		final MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) op0;
		assertEquals(multiReferenceOperation.getModelElementId(), issueId);
		assertFalse(multiReferenceOperation.isAdd());
		assertEquals(multiReferenceOperation.getReferencedModelElements().get(0), proposalId);
		assertEquals(multiReferenceOperation.getReferencedModelElements().size(), 1);
		assertEquals(multiReferenceOperation.getIndex(), 0);

		final SingleReferenceOperation singleReferenceOperation = checkAndCast(subOperations.get(1),
			SingleReferenceOperation.class);

		assertEquals(issueId, singleReferenceOperation.getOldValue());
		assertNull(singleReferenceOperation.getNewValue());
		assertEquals(TestmodelPackage.eINSTANCE.getTestElement_Container().getName(),
			singleReferenceOperation.getFeatureName());
		assertEquals(proposalId, singleReferenceOperation.getModelElementId());
		assertEquals(TestmodelPackage.eINSTANCE.getTestElement_ContainedElements().getName(),
			singleReferenceOperation.getOppositeFeatureName());
		assertTrue(singleReferenceOperation.isBidirectional());
		final Set<ModelElementId> otherInvolvedModelElements = singleReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements.size());
		assertTrue(otherInvolvedModelElements.contains(issueId));
	}

	@Test
	public void unsetSingleReference() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Player favPlayer = BowlingFactory.eINSTANCE.createPlayer();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(favPlayer);
				fan.setFavouritePlayer(favPlayer);

			}
		}.run(false);

		assertEquals(favPlayer, fan.getFavouritePlayer());
		assertTrue(fan.isSetFavouritePlayer());

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFavouritePlayer();
			}
		}.run(false);

		assertFalse(favPlayer.equals(fan.getFavouritePlayer()));
		assertEquals(null, fan.getFavouritePlayer());
		assertTrue(getProject().getAllModelElements().contains(favPlayer));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		final ModelElementId playerId = ModelUtil.getProject(favPlayer).getModelElementId(favPlayer);
		assertEquals(playerId, singleRefOp.getOldValue());
		assertEquals(null, singleRefOp.getNewValue());
		assertTrue(singleRefOp.getUnset() == UnsetType.IS_UNSET);
		assertEquals(FAVOURITE_PLAYER, singleRefOp.getFeatureName());

		// apply operation to copy of initial project
		singleRefOp.apply(secondProject);
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void reverseUnsetSingleReference() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Player favPlayer = BowlingFactory.eINSTANCE.createPlayer();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(favPlayer);
				fan.setFavouritePlayer(favPlayer);

			}
		}.run(false);

		assertEquals(favPlayer, fan.getFavouritePlayer());
		assertTrue(fan.isSetFavouritePlayer());

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFavouritePlayer();
			}
		}.run(false);

		assertFalse(favPlayer.equals(fan.getFavouritePlayer()));
		assertEquals(null, fan.getFavouritePlayer());
		assertTrue(getProject().getAllModelElements().contains(favPlayer));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		final ModelElementId playerId = ModelUtil.getProject(favPlayer).getModelElementId(favPlayer);
		assertEquals(playerId, singleRefOp.getOldValue());
		assertEquals(null, singleRefOp.getNewValue());
		assertTrue(singleRefOp.getUnset() == UnsetType.IS_UNSET);
		assertEquals(FAVOURITE_PLAYER, singleRefOp.getFeatureName());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				singleRefOp.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(favPlayer, fan.getFavouritePlayer());
		assertTrue(fan.isSetFavouritePlayer());

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void doubleReverseUnsetSingleReference() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Player favPlayer = BowlingFactory.eINSTANCE.createPlayer();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(favPlayer);
				fan.setFavouritePlayer(favPlayer);

			}
		}.run(false);

		assertEquals(favPlayer, fan.getFavouritePlayer());
		assertTrue(fan.isSetFavouritePlayer());

		clearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFavouritePlayer();
			}
		}.run(false);

		assertFalse(favPlayer.equals(fan.getFavouritePlayer()));
		assertEquals(null, fan.getFavouritePlayer());
		assertTrue(getProject().getAllModelElements().contains(favPlayer));

		final Project secondProject = ModelUtil.clone(getProject());
		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		final ModelElementId playerId = ModelUtil.getProject(favPlayer).getModelElementId(favPlayer);
		assertEquals(playerId, singleRefOp.getOldValue());
		assertEquals(null, singleRefOp.getNewValue());
		assertTrue(singleRefOp.getUnset() == UnsetType.IS_UNSET);
		assertEquals(FAVOURITE_PLAYER, singleRefOp.getFeatureName());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				singleRefOp.reverse().reverse().apply(getProject());
			}
		}.run(false);

		assertFalse(favPlayer.equals(fan.getFavouritePlayer()));
		assertEquals(null, fan.getFavouritePlayer());
		assertTrue(getProject().getAllModelElements().contains(favPlayer));

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void reverseSetOfUnsettedSingleReference() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Player favPlayer = BowlingFactory.eINSTANCE.createPlayer();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(favPlayer);
			}
		}.run(false);

		assertEquals(false, fan.isSetFavouritePlayer());

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.setFavouritePlayer(favPlayer);
			}
		}.run(false);

		assertTrue(favPlayer.equals(fan.getFavouritePlayer()));
		assertEquals(favPlayer, fan.getFavouritePlayer());
		assertTrue(getProject().getAllModelElements().contains(favPlayer));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		final ModelElementId playerId = ModelUtil.getProject(favPlayer).getModelElementId(favPlayer);
		assertEquals(null, singleRefOp.getOldValue());
		assertEquals(playerId, singleRefOp.getNewValue());
		assertEquals(FAVOURITE_PLAYER, singleRefOp.getFeatureName());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				singleRefOp.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(null, fan.getFavouritePlayer());
		assertEquals(false, fan.isSetFavouritePlayer());

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void unsetSingleContainmentReference() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Merchandise merch = BowlingFactory.eINSTANCE.createMerchandise();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(merch);
				fan.setFavouriteMerchandise(merch);

			}
		}.run(false);

		assertEquals(merch, fan.getFavouriteMerchandise());
		assertTrue(fan.isSetFavouriteMerchandise());

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFavouriteMerchandise();
			}
		}.run(false);

		assertFalse(merch.equals(fan.getFavouriteMerchandise()));
		assertEquals(null, fan.getFavouriteMerchandise());
		assertFalse(getProject().getAllModelElements().contains(merch));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp = (CreateDeleteOperation) operation;

		// apply operation to copy of initial project
		creaDelOp.apply(secondProject);
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void doubleReverseUnsetSingleContainmentReference() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Merchandise merch = BowlingFactory.eINSTANCE.createMerchandise();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(merch);
				fan.setFavouriteMerchandise(merch);

			}
		}.run(false);

		assertEquals(merch, fan.getFavouriteMerchandise());
		assertTrue(fan.isSetFavouriteMerchandise());

		clearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFavouriteMerchandise();
			}
		}.run(false);

		assertFalse(merch.equals(fan.getFavouriteMerchandise()));
		assertEquals(null, fan.getFavouriteMerchandise());
		assertFalse(getProject().getAllModelElements().contains(merch));

		final Project secondProject = ModelUtil.clone(getProject());
		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp = (CreateDeleteOperation) operation;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				creaDelOp.reverse().reverse().apply(getProject());
			}
		}.run(false);

		assertFalse(merch.equals(fan.getFavouriteMerchandise()));
		assertEquals(null, fan.getFavouriteMerchandise());
		assertFalse(getProject().getAllModelElements().contains(merch));
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void reverseUnsetSingleContainmentReference() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Merchandise merch = BowlingFactory.eINSTANCE.createMerchandise();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(merch);
				fan.setFavouriteMerchandise(merch);

			}
		}.run(false);

		assertEquals(merch, fan.getFavouriteMerchandise());
		assertTrue(fan.isSetFavouriteMerchandise());

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFavouriteMerchandise();
			}
		}.run(false);

		assertFalse(merch.equals(fan.getFavouriteMerchandise()));
		assertEquals(null, fan.getFavouriteMerchandise());
		assertFalse(getProject().getAllModelElements().contains(merch));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		final CreateDeleteOperation creaDelOp = (CreateDeleteOperation) operation;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				creaDelOp.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(merch.getName(), fan.getFavouriteMerchandise().getName());
		assertEquals(merch.getPrice(), fan.getFavouriteMerchandise().getPrice());
		assertEquals(merch.getSerialNumber(), fan.getFavouriteMerchandise().getSerialNumber());
		assertTrue(fan.isSetFavouriteMerchandise());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void reverseSetOfUnsettedSingleContainmentReference() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Merchandise merch = BowlingFactory.eINSTANCE.createMerchandise();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				getProject().addModelElement(merch);
				assertEquals(null, fan.getFavouriteMerchandise());
				assertEquals(false, fan.isSetFavouriteMerchandise());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.setFavouriteMerchandise(merch);
				assertEquals(merch, fan.getFavouriteMerchandise());
				assertTrue(fan.isSetFavouriteMerchandise());
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		final ModelElementId merchId = ModelUtil.getProject(merch).getModelElementId(merch);
		assertEquals(null, singleRefOp.getOldValue());
		assertEquals(merchId, singleRefOp.getNewValue());
		assertEquals(FAVOURITE_MERCHANDISE, singleRefOp.getFeatureName());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				singleRefOp.reverse().apply(getProject());
			}
		}.run(false);

		assertNull(fan.getFavouriteMerchandise());
		assertFalse(fan.isSetFavouriteMerchandise());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void setUnsetSingleReferenceToNull() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				assertEquals(null, fan.getFavouritePlayer());
				assertTrue(!fan.isSetFavouritePlayer());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.setFavouritePlayer(null);
				assertEquals(null, fan.getFavouritePlayer());
				assertTrue(fan.isSetFavouritePlayer());
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final SingleReferenceOperation singleRefOp = checkAndCast(operations.get(0), SingleReferenceOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				singleRefOp.apply(secondProject);
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}
}
