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
package org.eclipse.emf.emfstore.client.test.changeTracking.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.bowling.Merchandise;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.model.document.DocumentFactory;
import org.eclipse.emf.emfstore.client.test.model.document.LeafSection;
import org.eclipse.emf.emfstore.client.test.model.rationale.Issue;
import org.eclipse.emf.emfstore.client.test.model.rationale.Proposal;
import org.eclipse.emf.emfstore.client.test.model.rationale.RationaleFactory;
import org.eclipse.emf.emfstore.client.test.model.rationale.RationalePackage;
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
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.ReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.UnsetType;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.impl.MultiReferenceOperationImpl;
import org.junit.Test;

/**
 * Tests the SingleReferenceOperation.
 * 
 * @author koegel
 */
public class SingleReferenceOperationTest extends WorkspaceTest {

	private Project expectedProject;

	/**
	 * Change a single reference and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void changeSingleReference() throws UnsupportedOperationException, UnsupportedNotificationException {
		final UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		final Actor actor = RequirementFactory.eINSTANCE.createActor();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				clearOperations();

				useCase.setInitiatingActor(actor);

				assertEquals(actor, useCase.getInitiatingActor());
				EList<UseCase> initiatedUseCases = actor.getInitiatedUseCases();
				assertEquals(1, initiatedUseCases.size());
				assertEquals(useCase, initiatedUseCases.get(0));
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		EList<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(2, subOperations.size());

		operation = subOperations.get(0);
		assertEquals(true, operation instanceof MultiReferenceOperationImpl);
		MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) operation;
		assertEquals("initiatedUseCases", multiReferenceOperation.getFeatureName());
		assertEquals("initiatingActor", multiReferenceOperation.getOppositeFeatureName());

		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);

		assertEquals(useCaseId, multiReferenceOperation.getReferencedModelElements().get(0));
		assertEquals(actorId, multiReferenceOperation.getModelElementId());
		assertTrue(multiReferenceOperation.isBidirectional());
		assertTrue(multiReferenceOperation.isAdd());
		assertEquals(1, multiReferenceOperation.getOtherInvolvedModelElements().size());

		operation = subOperations.get(1);
		assertEquals(true, operation instanceof SingleReferenceOperation);
		SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) operation;

		assertEquals(null, singleReferenceOperation.getOldValue());
		assertEquals(actorId, singleReferenceOperation.getNewValue());
		assertEquals("initiatingActor", singleReferenceOperation.getFeatureName());
		assertEquals(useCaseId, singleReferenceOperation.getModelElementId());
		assertEquals("initiatedUseCases", singleReferenceOperation.getOppositeFeatureName());
		assertEquals(true, singleReferenceOperation.isBidirectional());
		Set<ModelElementId> otherInvolvedModelElements = singleReferenceOperation.getOtherInvolvedModelElements();
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
	// UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
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
	// EList<UseCase> initiatedUseCases = oldActor.getInitiatedUseCases();
	// assertEquals(1, initiatedUseCases.size());
	// assertEquals(useCase, initiatedUseCases.get(0));
	//
	// useCase.setInitiatingActor(newActor);
	// assertEquals(newActor, useCase.getInitiatingActor());
	// initiatedUseCases = newActor.getInitiatedUseCases();
	// assertEquals(1, initiatedUseCases.size());
	// assertEquals(useCase, initiatedUseCases.get(0));
	//
	// List<AbstractOperation> operations = getProjectSpace().getOperations();
	//
	// assertEquals(1, operations.size());
	// AbstractOperation operation = operations.get(0);
	// assertEquals(true, operation instanceof SingleReferenceOperation);
	// SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) operation;
	//
	// assertEquals(null, singleReferenceOperation.getOldValue());
	// assertEquals(newActor.getModelElementId(), singleReferenceOperation.getNewValue());
	// assertEquals("initiatingActor", singleReferenceOperation.getFeatureName());
	// assertEquals(useCase.getModelElementId(), singleReferenceOperation.getModelElementId());
	// assertEquals("initiatedUseCases", singleReferenceOperation.getOppositeFeatureName());
	// assertEquals(true, singleReferenceOperation.isBidirectional());
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

		final UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		final Actor oldActor = RequirementFactory.eINSTANCE.createActor();
		final Actor newActor = RequirementFactory.eINSTANCE.createActor();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(oldActor);
				getProject().addModelElement(newActor);
				useCase.setInitiatingActor(oldActor);
				assertEquals(oldActor, useCase.getInitiatingActor());
				EList<UseCase> initiatedUseCases = oldActor.getInitiatedUseCases();
				assertEquals(1, initiatedUseCases.size());
				assertEquals(useCase, initiatedUseCases.get(0));

				clearOperations();

				useCase.setInitiatingActor(newActor);
				assertEquals(newActor, useCase.getInitiatingActor());
				initiatedUseCases = newActor.getInitiatedUseCases();
				assertEquals(1, initiatedUseCases.size());
				assertEquals(useCase, initiatedUseCases.get(0));
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());

		assertEquals(1, operations.size());
		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail("composite operation expected");
		}

		assertEquals(3, operations.size());

		// note: skipping multireferenceop at index 0 in test, as it is not interesting in this context
		AbstractOperation operation = operations.get(2);
		assertEquals(true, operation instanceof SingleReferenceOperation);
		SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) operation;

		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		ModelElementId oldActorId = ModelUtil.getProject(oldActor).getModelElementId(oldActor);
		ModelElementId newActorId = ModelUtil.getProject(newActor).getModelElementId(newActor);

		assertEquals(oldActorId, singleReferenceOperation.getOldValue());
		assertEquals(newActorId, singleReferenceOperation.getNewValue());
		assertEquals("initiatingActor", singleReferenceOperation.getFeatureName());
		assertEquals(useCaseId, singleReferenceOperation.getModelElementId());
		assertEquals("initiatedUseCases", singleReferenceOperation.getOppositeFeatureName());
		assertEquals(true, singleReferenceOperation.isBidirectional());
		Set<ModelElementId> otherInvolvedModelElements = singleReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(2, otherInvolvedModelElements.size());
		assertEquals(true, otherInvolvedModelElements.contains(oldActorId));
		assertEquals(true, otherInvolvedModelElements.contains(newActorId));

		AbstractOperation reverse = singleReferenceOperation.reverse();
		assertEquals(true, reverse instanceof SingleReferenceOperation);
		final SingleReferenceOperation reversedSingleReferenceOperation = (SingleReferenceOperation) reverse;

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				reversedSingleReferenceOperation.apply(getProject());
			}
		}.run(false);

		assertEquals(oldActor, useCase.getInitiatingActor());

		assertEquals(newActorId, reversedSingleReferenceOperation.getOldValue());
		assertEquals(oldActorId, reversedSingleReferenceOperation.getNewValue());
		assertEquals("initiatingActor", reversedSingleReferenceOperation.getFeatureName());
		assertEquals(useCaseId, reversedSingleReferenceOperation.getModelElementId());
		assertEquals("initiatedUseCases", reversedSingleReferenceOperation.getOppositeFeatureName());
		assertEquals(true, reversedSingleReferenceOperation.isBidirectional());
		otherInvolvedModelElements = reversedSingleReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(2, otherInvolvedModelElements.size());
		assertEquals(true, otherInvolvedModelElements.contains(oldActorId));
		assertEquals(true, otherInvolvedModelElements.contains(newActorId));
	}

	/**
	 * Tests reversibility of 1:n single reference feature.
	 */
	@Test
	public void containmentSingleReferenceReversibilityTest() {

		final UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		final Actor actor = RequirementFactory.eINSTANCE.createActor();
		final LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		final LeafSection oldSection = DocumentFactory.eINSTANCE.createLeafSection();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);
				getProject().addModelElement(section);
				getProject().addModelElement(oldSection);
				useCase.setLeafSection(oldSection);
				actor.setLeafSection(oldSection);

				expectedProject = ModelUtil.clone(getProject());
				assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

				clearOperations();
				useCase.setLeafSection(section);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// composite operation containing a multiref operation and a singleref operation expected
		assertEquals(operations.size(), 1);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				AbstractOperation reverse = operations.get(0).reverse();
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
		final Issue oldIssue = RationaleFactory.eINSTANCE.createIssue();
		final Issue newIssue = RationaleFactory.eINSTANCE.createIssue();
		final Proposal proposal = RationaleFactory.eINSTANCE.createProposal();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(oldIssue);
				getProject().addModelElement(newIssue);
				getProject().addModelElement(proposal);
				proposal.setIssue(oldIssue);
				clearOperations();

				assertEquals(0, newIssue.getProposals().size());
				assertEquals(1, oldIssue.getProposals().size());
				assertEquals(proposal, oldIssue.getProposals().get(0));
				assertEquals(oldIssue, proposal.getIssue());

				proposal.setIssue(newIssue);

				assertEquals(0, oldIssue.getProposals().size());
				assertEquals(1, newIssue.getProposals().size());
				assertEquals(proposal, newIssue.getProposals().get(0));
				assertEquals(newIssue, proposal.getIssue());
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());

		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail("composite operation expected");
		}

		assertEquals(3, operations.size());

		AbstractOperation op0 = operations.get(0);
		assertTrue(op0 instanceof MultiReferenceOperation);
		MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) op0;

		ModelElementId oldIssueId = ModelUtil.getProject(oldIssue).getModelElementId(oldIssue);
		ModelElementId proposalId = ModelUtil.getProject(proposal).getModelElementId(proposal);
		ModelElementId newIssueId = ModelUtil.getProject(newIssue).getModelElementId(newIssue);

		assertEquals(multiReferenceOperation.getModelElementId(), oldIssueId);
		assertFalse(multiReferenceOperation.isAdd());
		assertEquals(multiReferenceOperation.getReferencedModelElements().get(0), proposalId);
		assertEquals(multiReferenceOperation.getReferencedModelElements().size(), 1);
		assertEquals(multiReferenceOperation.getIndex(), 0);

		AbstractOperation op1 = operations.get(1);
		assertTrue(op1 instanceof MultiReferenceOperation);
		multiReferenceOperation = (MultiReferenceOperation) op1;
		assertEquals(multiReferenceOperation.getModelElementId(), newIssueId);
		assertTrue(multiReferenceOperation.isAdd());
		assertEquals(multiReferenceOperation.getReferencedModelElements().get(0), proposalId);
		assertEquals(multiReferenceOperation.getReferencedModelElements().size(), 1);
		assertEquals(multiReferenceOperation.getIndex(), 0);

		AbstractOperation operation = operations.get(2);
		assertEquals(true, operation instanceof SingleReferenceOperation);
		SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) operation;

		assertEquals(oldIssueId, singleReferenceOperation.getOldValue());
		assertEquals(newIssueId, singleReferenceOperation.getNewValue());
		assertEquals(RationalePackage.eINSTANCE.getProposal_Issue().getName(),
			singleReferenceOperation.getFeatureName());
		assertEquals(proposalId, singleReferenceOperation.getModelElementId());
		assertEquals(RationalePackage.eINSTANCE.getIssue_Proposals().getName(),
			singleReferenceOperation.getOppositeFeatureName());
		assertEquals(true, singleReferenceOperation.isBidirectional());
		Set<ModelElementId> otherInvolvedModelElements = singleReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(2, otherInvolvedModelElements.size());
		assertEquals(true, otherInvolvedModelElements.contains(newIssueId));
		assertEquals(true, otherInvolvedModelElements.contains(oldIssueId));
	}

	/**
	 * Test containment removing.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 */
	@Test
	public void removeContainment() throws UnsupportedOperationException {
		final Issue issue = RationaleFactory.eINSTANCE.createIssue();
		final Proposal proposal = RationaleFactory.eINSTANCE.createProposal();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				proposal.setName("proposal");
				getProject().addModelElement(issue);
				getProject().addModelElement(proposal);
				proposal.setIssue(issue);
				clearOperations();
			}
		}.run(false);

		assertEquals(1, issue.getProposals().size());
		assertEquals(proposal, issue.getProposals().get(0));
		assertEquals(issue, proposal.getIssue());
		assertEquals(true, getProject().contains(issue));
		assertEquals(true, getProject().contains(proposal));
		assertEquals(getProject(), ModelUtil.getProject(issue));
		assertEquals(getProject(), ModelUtil.getProject(proposal));
		assertEquals(issue, proposal.eContainer());

		ModelElementId proposalId = ModelUtil.getProject(proposal).getModelElementId(proposal);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				proposal.setIssue(null);
			}
		}.run(false);

		assertEquals(true, getProject().contains(issue));
		assertEquals(false, getProject().contains(proposal));
		assertEquals(getProject(), ModelUtil.getProject(issue));
		// assertEquals(null, ModelUtil.getProject(proposal));
		assertEquals(0, issue.getProposals().size());
		assertEquals(null, proposal.getIssue());
		// assertEquals(null, proposal.eContainer());

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());

		ModelElementId issueId = ModelUtil.getProject(issue).getModelElementId(issue);

		List<ReferenceOperation> subOperations = checkAndCast(operations.get(0), CreateDeleteOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		AbstractOperation op0 = subOperations.get(0);
		assertTrue(op0 instanceof MultiReferenceOperation);
		MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) op0;
		assertEquals(multiReferenceOperation.getModelElementId(), issueId);
		assertFalse(multiReferenceOperation.isAdd());
		assertEquals(multiReferenceOperation.getReferencedModelElements().get(0), proposalId);
		assertEquals(multiReferenceOperation.getReferencedModelElements().size(), 1);
		assertEquals(multiReferenceOperation.getIndex(), 0);

		AbstractOperation operation = subOperations.get(1);
		assertEquals(true, operation instanceof SingleReferenceOperation);
		SingleReferenceOperation singleReferenceOperation = (SingleReferenceOperation) operation;

		assertEquals(issueId, singleReferenceOperation.getOldValue());
		assertEquals(null, singleReferenceOperation.getNewValue());
		assertEquals(RationalePackage.eINSTANCE.getProposal_Issue().getName(),
			singleReferenceOperation.getFeatureName());
		assertEquals(proposalId, singleReferenceOperation.getModelElementId());
		assertEquals(RationalePackage.eINSTANCE.getIssue_Proposals().getName(),
			singleReferenceOperation.getOppositeFeatureName());
		assertEquals(true, singleReferenceOperation.isBidirectional());
		Set<ModelElementId> otherInvolvedModelElements = singleReferenceOperation.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements.size());
		assertEquals(true, otherInvolvedModelElements.contains(issueId));
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
		assertEquals(true, fan.isSetFavouritePlayer());

		clearOperations();
		Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFavouritePlayer();
			}
		}.run(false);

		assertFalse(favPlayer.equals(fan.getFavouritePlayer()));
		assertEquals(null, fan.getFavouritePlayer());
		assertTrue(getProject().getAllModelElements().contains(favPlayer));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof SingleReferenceOperation);
		SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		ModelElementId playerId = ModelUtil.getProject(favPlayer).getModelElementId(favPlayer);
		assertEquals(playerId, singleRefOp.getOldValue());
		assertEquals(null, singleRefOp.getNewValue());
		assertEquals(true, singleRefOp.getUnset() == UnsetType.IS_UNSET);
		assertEquals("favouritePlayer", singleRefOp.getFeatureName());

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
		assertEquals(true, fan.isSetFavouritePlayer());

		clearOperations();
		Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFavouritePlayer();
			}
		}.run(false);

		assertFalse(favPlayer.equals(fan.getFavouritePlayer()));
		assertEquals(null, fan.getFavouritePlayer());
		assertTrue(getProject().getAllModelElements().contains(favPlayer));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		ModelElementId playerId = ModelUtil.getProject(favPlayer).getModelElementId(favPlayer);
		assertEquals(playerId, singleRefOp.getOldValue());
		assertEquals(null, singleRefOp.getNewValue());
		assertEquals(true, singleRefOp.getUnset() == UnsetType.IS_UNSET);
		assertEquals("favouritePlayer", singleRefOp.getFeatureName());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				singleRefOp.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(favPlayer, fan.getFavouritePlayer());
		assertEquals(true, fan.isSetFavouritePlayer());

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
		assertEquals(true, fan.isSetFavouritePlayer());

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

		Project secondProject = ModelUtil.clone(getProject());
		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		ModelElementId playerId = ModelUtil.getProject(favPlayer).getModelElementId(favPlayer);
		assertEquals(playerId, singleRefOp.getOldValue());
		assertEquals(null, singleRefOp.getNewValue());
		assertEquals(true, singleRefOp.getUnset() == UnsetType.IS_UNSET);
		assertEquals("favouritePlayer", singleRefOp.getFeatureName());

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
		Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.setFavouritePlayer(favPlayer);
			}
		}.run(false);

		assertTrue(favPlayer.equals(fan.getFavouritePlayer()));
		assertEquals(favPlayer, fan.getFavouritePlayer());
		assertTrue(getProject().getAllModelElements().contains(favPlayer));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		ModelElementId playerId = ModelUtil.getProject(favPlayer).getModelElementId(favPlayer);
		assertEquals(null, singleRefOp.getOldValue());
		assertEquals(playerId, singleRefOp.getNewValue());
		assertEquals("favouritePlayer", singleRefOp.getFeatureName());

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
		assertEquals(true, fan.isSetFavouriteMerchandise());

		clearOperations();
		Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFavouriteMerchandise();
			}
		}.run(false);

		assertFalse(merch.equals(fan.getFavouriteMerchandise()));
		assertEquals(null, fan.getFavouriteMerchandise());
		assertFalse(getProject().getAllModelElements().contains(merch));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CreateDeleteOperation);
		CreateDeleteOperation creaDelOp = (CreateDeleteOperation) operation;

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
		assertEquals(true, fan.isSetFavouriteMerchandise());

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

		Project secondProject = ModelUtil.clone(getProject());
		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CreateDeleteOperation);
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
		assertEquals(true, fan.isSetFavouriteMerchandise());

		clearOperations();
		Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetFavouriteMerchandise();
			}
		}.run(false);

		assertFalse(merch.equals(fan.getFavouriteMerchandise()));
		assertEquals(null, fan.getFavouriteMerchandise());
		assertFalse(getProject().getAllModelElements().contains(merch));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CreateDeleteOperation);
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
		assertEquals(true, fan.isSetFavouriteMerchandise());
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
		Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.setFavouriteMerchandise(merch);
				assertEquals(merch, fan.getFavouriteMerchandise());
				assertEquals(true, fan.isSetFavouriteMerchandise());
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		ModelElementId merchId = ModelUtil.getProject(merch).getModelElementId(merch);
		assertEquals(null, singleRefOp.getOldValue());
		assertEquals(merchId, singleRefOp.getNewValue());
		assertEquals("favouriteMerchandise", singleRefOp.getFeatureName());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				singleRefOp.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(null, fan.getFavouriteMerchandise());
		assertEquals(false, fan.isSetFavouriteMerchandise());
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

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof SingleReferenceOperation);
		final SingleReferenceOperation singleRefOp = (SingleReferenceOperation) operation;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				singleRefOp.apply(secondProject);
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}
}
