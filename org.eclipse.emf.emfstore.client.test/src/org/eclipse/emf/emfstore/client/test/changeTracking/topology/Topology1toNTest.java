/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.changeTracking.topology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.test.model.UnicaseModelElement;
import org.eclipse.emf.emfstore.client.test.model.bug.BugFactory;
import org.eclipse.emf.emfstore.client.test.model.bug.BugReport;
import org.eclipse.emf.emfstore.client.test.model.document.DocumentFactory;
import org.eclipse.emf.emfstore.client.test.model.document.LeafSection;
import org.eclipse.emf.emfstore.client.test.model.rationale.Issue;
import org.eclipse.emf.emfstore.client.test.model.rationale.RationaleFactory;
import org.eclipse.emf.emfstore.client.test.model.rationale.Solution;
import org.eclipse.emf.emfstore.client.test.model.requirement.Actor;
import org.eclipse.emf.emfstore.client.test.model.requirement.RequirementFactory;
import org.eclipse.emf.emfstore.client.test.model.requirement.UseCase;
import org.eclipse.emf.emfstore.client.test.model.task.TaskFactory;
import org.eclipse.emf.emfstore.client.test.model.task.WorkPackage;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.ReferenceOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.SingleReferenceOperation;
import org.junit.Test;

/**
 * Tests operations in 1:n topologies.
 * 
 * @author chodnick
 * @author emueller
 */
public class Topology1toNTest extends TopologyTest {

	/**
	 * Add an uncontained child to an empty containment feature.
	 */
	@Test
	public void containmentAddUncontainedChildToEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor);
		getProject().addModelElement(section);
		ModelElementId actorId = getProject().getModelElementId(actor);
		ModelElementId sectionId = getProject().getModelElementId(section);

		clearOperations();

		section.getModelElements().add(actor);

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		CompositeOperation operation = checkAndCast(operations.get(0), CompositeOperation.class);

		List<AbstractOperation> subOperations = operation.getSubOperations();
		assertEquals(2, subOperations.size());

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals("leafSection", op0.getFeatureName());
		assertEquals(actorId, op0.getModelElementId());
		assertEquals(sectionId, op0.getNewValue());
		assertNull(op0.getOldValue());

		assertEquals("modelElements", op1.getFeatureName());
		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals(op1.getModelElementId(), sectionId);
		assertEquals(op1.getIndex(), 0);
		assertTrue(op1.isAdd());
	}

	/**
	 * Create orphan.
	 */
	@Test
	public void createContainmentOrphan() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor);
		getProject().addModelElement(section);
		ModelElementId actorId = getProject().getModelElementId(actor);
		ModelElementId sectionId = getProject().getModelElementId(section);

		section.getModelElements().add(actor);

		clearOperations();

		getProject().addModelElement(actor);

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		assertEquals("modelElements", op0.getFeatureName());
		assertEquals(sectionId, op0.getModelElementId());
		assertEquals(actorId, op0.getReferencedModelElements().get(0));

		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		assertEquals("leafSection", op1.getFeatureName());
		assertEquals(op1.getModelElementId(), actorId);

	}

	/**
	 * Reverse orphan creation.
	 */
	@Test
	public void reverseContainmentOrphan() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor);
		getProject().addModelElement(section);
		ModelElementId actorId = getProject().getModelElementId(actor);
		ModelElementId sectionId = getProject().getModelElementId(section);
		section.getModelElements().add(actor);

		Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		clearOperations();
		// create orphan
		getProject().addModelElement(actor);

		List<AbstractOperation> operations2 = getProjectSpace().getOperations();

		assertEquals(1, operations2.size());
		List<AbstractOperation> subOperations = checkAndCast(operations2.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(2, subOperations.size());

		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);

		assertEquals("modelElements", op0.getFeatureName());
		assertEquals(sectionId, op0.getModelElementId());
		assertEquals(actorId, op0.getReferencedModelElements().get(0));

		assertEquals("leafSection", op1.getFeatureName());
		assertEquals(actorId, op1.getModelElementId());

		// test the reversibility of what has happened
		op1.reverse().apply(getProject());
		op0.reverse().apply(getProject());

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Reverse orphan creation.
	 */
	@Test
	public void reverseContainmentOrphanIndexed() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor = RequirementFactory.eINSTANCE.createActor();
		Actor actor2 = RequirementFactory.eINSTANCE.createActor();
		actor.setName("actor");
		actor2.setName("actor2");

		getProject().addModelElement(section);
		section.getModelElements().add(actor);
		section.getModelElements().add(actor2);

		Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		clearOperations();
		// create orphan
		getProject().addModelElement(actor);

		ModelElementId actorId = getProject().getModelElementId(actor);
		ModelElementId sectionId = getProject().getModelElementId(section);

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);

		assertEquals("modelElements", op0.getFeatureName());
		assertEquals(sectionId, op0.getModelElementId());
		assertEquals(actorId, op0.getReferencedModelElements().get(0));

		assertEquals("leafSection", op1.getFeatureName());
		assertEquals(actorId, op1.getModelElementId());

		// test the reversibility of what has happened
		op1.reverse().apply(getProject());
		op0.reverse().apply(getProject());

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Add an uncontained child to a non-empty containment feature.
	 * 
	 */
	@Test
	public void containmentAddUncontainedChildToNonEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor = RequirementFactory.eINSTANCE.createActor();
		Actor oldActor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor);
		getProject().addModelElement(oldActor);
		getProject().addModelElement(section);

		ModelElementId actorId = getProject().getModelElementId(actor);
		ModelElementId sectionId = getProject().getModelElementId(section);
		section.getModelElements().add(oldActor);

		clearOperations();

		section.getModelElements().add(actor);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(2, subOperations.size());
		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals("leafSection", op0.getFeatureName());
		assertEquals(actorId, op0.getModelElementId());
		assertEquals(sectionId, op0.getNewValue());
		assertNull(op0.getOldValue());

		assertEquals("modelElements", op1.getFeatureName());
		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals(sectionId, op1.getModelElementId());
		assertEquals(1, op1.getIndex());
		assertTrue(op1.isAdd());

	}

	/**
	 * Add several uncontained children to an empty containment feature.
	 * 
	 */
	@Test
	public void containmentAddUncontainedChildrenToEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		Actor actor2 = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(section);
		ModelElementId actor1Id = getProject().getModelElementId(actor1);
		ModelElementId actor2Id = getProject().getModelElementId(actor2);
		ModelElementId sectionId = getProject().getModelElementId(section);

		Actor[] actors = { actor1, actor2 };

		clearOperations();

		section.getModelElements().addAll(Arrays.asList(actors));

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(3, subOperations.size());

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals("leafSection", op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(sectionId, op0.getNewValue());
		assertNull(op0.getOldValue());

		assertEquals("leafSection", op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(sectionId, op1.getNewValue());
		assertNull(op1.getOldValue());

		assertEquals("modelElements", op2.getFeatureName());
		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals(sectionId, op2.getModelElementId());
		assertEquals(0, op2.getIndex());
		assertTrue(op2.isAdd());

	}

	/**
	 * Add several uncontained children to a non-empty containment feature.
	 */
	@Test
	public void containmentAddUncontainedChildrenToNonEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		Actor actor2 = RequirementFactory.eINSTANCE.createActor();
		Actor oldActor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(section);
		getProject().addModelElement(oldActor);

		ModelElementId actor1Id = getProject().getModelElementId(actor1);
		ModelElementId actor2Id = getProject().getModelElementId(actor2);
		ModelElementId sectionId = getProject().getModelElementId(section);

		Actor[] actors = { actor1, actor2 };
		section.getModelElements().add(oldActor);
		clearOperations();

		section.getModelElements().addAll(Arrays.asList(actors));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(3, subOperations.size());
		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals("leafSection", op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(sectionId, op0.getNewValue());
		assertNull(op0.getOldValue());

		assertEquals("leafSection", op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(sectionId, op1.getNewValue());
		assertNull(op1.getOldValue());

		assertEquals("modelElements", op2.getFeatureName());
		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals(sectionId, op2.getModelElementId());
		assertEquals(1, op2.getIndex());
		assertTrue(op2.isAdd());

	}

	/**
	 * Add several uncontained children to a non-empty containment feature.
	 */
	@Test
	public void containmentAddUncontainedChildrenFakeManyToNonEmpty() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		Actor oldActor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor1);
		getProject().addModelElement(oldActor);
		getProject().addModelElement(section);

		ModelElementId sectionId = getProject().getModelElementId(section);
		ModelElementId actor1Id = getProject().getModelElementId(actor1);

		Actor[] actors = { actor1 };
		section.getModelElements().add(oldActor);
		clearOperations();

		section.getModelElements().addAll(Arrays.asList(actors));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(2, subOperations.size());
		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals("leafSection", op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(sectionId, op0.getNewValue());
		assertNull(op0.getOldValue());

		assertEquals("modelElements", op1.getFeatureName());
		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actor1Id, op1.getReferencedModelElements().get(0));
		assertEquals(sectionId, op1.getModelElementId());
		assertEquals(1, op1.getIndex());
		assertTrue(op1.isAdd());

	}

	/**
	 * Add an contained child to a non-empty containment feature.
	 */
	@Test
	public void containmentAddSameFeatureContainedChildToNonEmpty() {

		LeafSection section1 = DocumentFactory.eINSTANCE.createLeafSection();
		LeafSection section2 = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		Actor actor2 = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(section1);
		getProject().addModelElement(section2);

		ModelElementId section1Id = getProject().getModelElementId(section1);
		ModelElementId section2Id = getProject().getModelElementId(section2);
		ModelElementId actor2Id = getProject().getModelElementId(actor2);

		section1.getModelElements().add(actor1);
		section2.getModelElements().add(actor2);

		clearOperations();

		section1.getModelElements().add(actor2);
		assertFalse(section2.getModelElements().contains(actor2));
		assertTrue(section1.getModelElements().contains(actor2));

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(3, subOperations.size());

		// 1st op: maintain change in section2, preserving index of actor 2
		// 2nd op: LeafSection change on actor2 (preserving old parent)
		// 3rd op: Section2 welcomes its new child

		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals("modelElements", op0.getFeatureName());
		assertEquals(section2Id, op0.getModelElementId());
		assertEquals(actor2Id, op0.getReferencedModelElements().get(0));

		assertEquals("leafSection", op1.getFeatureName());
		assertEquals(section2Id, op1.getOldValue());
		assertEquals(section1Id, op1.getNewValue());

		assertEquals("modelElements", op2.getFeatureName());
		assertEquals(actor2Id, op2.getReferencedModelElements().get(0));
		assertEquals(section1Id, op2.getModelElementId());
		assertEquals(1, op2.getReferencedModelElements().size());
		assertEquals(1, op2.getIndex());
		assertTrue(op2.isAdd());
	}

	/**
	 * Add several already contained children to an empty containment feature.
	 */
	@Test
	public void containmentAddSameFeatureContainedChildrenToEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		LeafSection oldSection = DocumentFactory.eINSTANCE.createLeafSection();
		LeafSection oldSection2 = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		Actor actor2 = RequirementFactory.eINSTANCE.createActor();
		Actor actor3 = RequirementFactory.eINSTANCE.createActor();
		Actor actor4 = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(actor3);
		getProject().addModelElement(actor4);
		getProject().addModelElement(section);
		getProject().addModelElement(oldSection);
		getProject().addModelElement(oldSection2);

		ModelElementId actor1Id = getProject().getModelElementId(actor1);
		ModelElementId actor2Id = getProject().getModelElementId(actor2);
		ModelElementId actor3Id = getProject().getModelElementId(actor3);
		ModelElementId actor4Id = getProject().getModelElementId(actor4);
		ModelElementId sectionId = getProject().getModelElementId(section);
		ModelElementId oldSectionId = getProject().getModelElementId(oldSection);
		ModelElementId oldSection2Id = getProject().getModelElementId(oldSection2);

		Actor[] actors = { actor1, actor2, actor3, actor4 };
		oldSection.getModelElements().addAll(Arrays.asList(actors));
		oldSection2.getModelElements().add(actor4); // relocate to other section
		assertTrue(oldSection.getModelElements().contains(actor1));
		assertTrue(oldSection.getModelElements().contains(actor2));
		assertTrue(oldSection.getModelElements().contains(actor3));
		assertTrue(oldSection2.getModelElements().contains(actor4));
		assertTrue(section.getModelElements().isEmpty());

		clearOperations();

		section.getModelElements().addAll(Arrays.asList(actors));

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		// now expectation is: we get 4 messages preserving the info on former parents for the actors
		// and one additional one, indicating the new parent for all of them
		assertEquals(7, subOperations.size());

		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);

		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		MultiReferenceOperation op4 = checkAndCast(subOperations.get(4), MultiReferenceOperation.class);
		MultiReferenceOperation op6 = checkAndCast(subOperations.get(6), MultiReferenceOperation.class);

		assertEquals(actor1Id, op1.getModelElementId());
		assertEquals(actor2Id, op2.getModelElementId());
		assertEquals(actor3Id, op3.getModelElementId());
		assertEquals(actor4Id, op5.getModelElementId());

		assertEquals("leafSection", op1.getFeatureName());
		assertEquals("leafSection", op2.getFeatureName());
		assertEquals("leafSection", op3.getFeatureName());
		assertEquals("leafSection", op5.getFeatureName());

		assertEquals(oldSectionId, op1.getOldValue());
		assertEquals(oldSectionId, op2.getOldValue());
		assertEquals(oldSectionId, op3.getOldValue());
		assertEquals(oldSection2Id, op5.getOldValue());

		assertEquals(sectionId, op1.getNewValue());
		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op3.getNewValue());
		assertEquals(sectionId, op5.getNewValue());

		assertEquals("modelElements", op0.getFeatureName());
		assertEquals(oldSectionId, op0.getModelElementId());
		assertFalse(op0.isAdd());
		assertEquals(3, op0.getReferencedModelElements().size());
		assertEquals(actor1Id, op0.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op0.getReferencedModelElements().get(1));
		assertEquals(actor3Id, op0.getReferencedModelElements().get(2));
		assertEquals(0, op0.getIndex());

		assertEquals("modelElements", op4.getFeatureName());
		assertEquals(oldSection2Id, op4.getModelElementId());
		assertEquals(1, op4.getReferencedModelElements().size());
		assertEquals(actor4Id, op4.getReferencedModelElements().get(0));
		assertEquals(0, op4.getIndex());
		assertFalse(op4.isAdd());

		assertEquals("modelElements", op6.getFeatureName());
		assertEquals(sectionId, op6.getModelElementId());
		assertEquals(4, op6.getReferencedModelElements().size());
		assertEquals(actor1Id, op6.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op6.getReferencedModelElements().get(1));
		assertEquals(actor3Id, op6.getReferencedModelElements().get(2));
		assertEquals(actor4Id, op6.getReferencedModelElements().get(3));
		assertEquals(0, op6.getIndex());
		assertTrue(op6.isAdd());
	}

	/**
	 * Add several already contained children to an empty containment feature.
	 */
	@Test
	public void containmentAddSameFeatureContainedChildrenToNonEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		LeafSection oldSection = DocumentFactory.eINSTANCE.createLeafSection();
		LeafSection oldSection2 = DocumentFactory.eINSTANCE.createLeafSection();
		Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		Actor actor2 = RequirementFactory.eINSTANCE.createActor();
		Actor actor3 = RequirementFactory.eINSTANCE.createActor();
		Actor actor4 = RequirementFactory.eINSTANCE.createActor();
		Actor oldActor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(actor3);
		getProject().addModelElement(actor4);
		getProject().addModelElement(oldActor);
		getProject().addModelElement(section);
		getProject().addModelElement(oldSection);
		getProject().addModelElement(oldSection2);

		Actor[] actors = { actor1, actor2, actor3, actor4 };
		section.getModelElements().add(oldActor);
		oldSection.getModelElements().addAll(Arrays.asList(actors));
		oldSection2.getModelElements().add(actor4); // relocate to other section

		assertTrue(oldSection.getModelElements().contains(actor1));
		assertTrue(oldSection.getModelElements().contains(actor2));
		assertTrue(oldSection.getModelElements().contains(actor3));
		assertTrue(section.getModelElements().contains(oldActor));
		assertTrue(oldSection2.getModelElements().contains(actor4));

		clearOperations();

		section.getModelElements().addAll(Arrays.asList(actors));

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());

		// now expectation is: we get 4 messages preserving the info on former parents for the actors
		// and one additional one, indicating the new parent for all of them
		// refactoring: addional operations expected

		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(7, subOperations.size());

		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);

		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		MultiReferenceOperation op4 = checkAndCast(subOperations.get(4), MultiReferenceOperation.class);
		MultiReferenceOperation op6 = checkAndCast(subOperations.get(6), MultiReferenceOperation.class);

		ModelElementId sectionId = getProject().getModelElementId(section);
		ModelElementId oldSectionId = getProject().getModelElementId(oldSection);
		ModelElementId oldSection2Id = getProject().getModelElementId(oldSection2);
		ModelElementId actor1Id = getProject().getModelElementId(actor1);
		ModelElementId actor2Id = getProject().getModelElementId(actor2);
		ModelElementId actor3Id = getProject().getModelElementId(actor3);
		ModelElementId actor4Id = getProject().getModelElementId(actor4);

		assertEquals(actor1Id, op1.getModelElementId());
		assertEquals(actor2Id, op2.getModelElementId());
		assertEquals(actor3Id, op3.getModelElementId());
		assertEquals(actor4Id, op5.getModelElementId());

		assertEquals("leafSection", op1.getFeatureName());
		assertEquals("leafSection", op2.getFeatureName());
		assertEquals("leafSection", op3.getFeatureName());
		assertEquals("leafSection", op5.getFeatureName());

		assertEquals(oldSectionId, op1.getOldValue());
		assertEquals(oldSectionId, op2.getOldValue());
		assertEquals(oldSectionId, op3.getOldValue());
		assertEquals(oldSection2Id, op5.getOldValue());

		assertEquals(sectionId, op1.getNewValue());
		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op3.getNewValue());
		assertEquals(sectionId, op5.getNewValue());

		assertEquals("modelElements", op0.getFeatureName());
		assertEquals(oldSectionId, op0.getModelElementId());
		assertEquals(actor1Id, op0.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op0.getReferencedModelElements().get(1));
		assertEquals(actor3Id, op0.getReferencedModelElements().get(2));
		assertEquals(0, op0.getIndex());
		assertEquals(3, op0.getReferencedModelElements().size());
		assertFalse(op0.isAdd());

		assertEquals("modelElements", op4.getFeatureName());
		assertEquals(oldSection2Id, op4.getModelElementId());
		assertEquals(1, op4.getReferencedModelElements().size());
		assertEquals(actor4Id, op4.getReferencedModelElements().get(0));
		assertEquals(0, op4.getIndex());
		assertFalse(op4.isAdd());

		assertEquals("modelElements", op6.getFeatureName());

		assertEquals(sectionId, op6.getModelElementId());
		assertEquals(4, op6.getReferencedModelElements().size());
		assertEquals(actor1Id, op6.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op6.getReferencedModelElements().get(1));
		assertEquals(actor3Id, op6.getReferencedModelElements().get(2));
		assertEquals(actor4Id, op6.getReferencedModelElements().get(3));
		assertEquals(1, op6.getIndex());
		assertTrue(op6.isAdd());

	}

	/**
	 * Add an contained child to a non-empty containment feature.
	 */
	@Test
	public void containmentAddDifferentFeatureContainedNChildToNonEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		WorkPackage workPackage = TaskFactory.eINSTANCE.createWorkPackage();
		BugReport bugReport = BugFactory.eINSTANCE.createBugReport();

		getProject().addModelElement(section);
		getProject().addModelElement(workPackage);
		getProject().addModelElement(bugReport);

		ModelElementId sectionId = getProject().getModelElementId(section);
		ModelElementId workPackageId = getProject().getModelElementId(workPackage);
		ModelElementId bugReportId = getProject().getModelElementId(bugReport);

		bugReport.setLeafSection(section);

		assertTrue(section.getModelElements().contains(bugReport));

		clearOperations();

		workPackage.getContainedWorkItems().add(bugReport);
		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertFalse(section.getModelElements().contains(bugReport));
		assertTrue(workPackage.getContainedWorkItems().contains(bugReport));
		assertEquals(1, operations.size());

		EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(4, subOperations.size());

		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		MultiReferenceOperation op3 = checkAndCast(subOperations.get(3), MultiReferenceOperation.class);

		assertEquals(sectionId, op0.getModelElementId());
		assertEquals("modelElements", op0.getFeatureName());
		assertEquals(op0.getReferencedModelElements().get(0), bugReportId);

		assertEquals(op1.getOldValue(), sectionId);
		assertNull(op1.getNewValue());
		assertEquals("leafSection", op1.getFeatureName());

		assertEquals("containingWorkpackage", op2.getFeatureName());
		assertEquals(workPackageId, op2.getNewValue());
		assertNull(op2.getOldValue());

		assertEquals("containedWorkItems", op3.getFeatureName());
		assertEquals(1, op3.getReferencedModelElements().size());
		assertEquals(bugReportId, op3.getReferencedModelElements().get(0));
		assertEquals(workPackageId, op3.getModelElementId());
		assertEquals(0, op3.getIndex());
		assertTrue(op3.isAdd());

	}

	/**
	 * Add an contained child to a non-empty containment feature.
	 */
	@Test
	public void containmentAddDifferentFeatureContained1ChildToNonEmpty() {

		Issue issue = RationaleFactory.eINSTANCE.createIssue();
		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Solution solution = RationaleFactory.eINSTANCE.createSolution();

		getProject().addModelElement(issue);
		getProject().addModelElement(section);
		getProject().addModelElement(solution);
		ModelElementId sectionId = getProject().getModelElementId(section);
		ModelElementId issueId = getProject().getModelElementId(issue);
		ModelElementId solutionId = getProject().getModelElementId(solution);
		issue.setSolution(solution);

		clearOperations();

		section.getModelElements().add(solution);

		assertTrue(section.getModelElements().contains(solution));
		assertNull(issue.getSolution());

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(4, subOperations.size());

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		MultiReferenceOperation op3 = checkAndCast(subOperations.get(3), MultiReferenceOperation.class);

		assertEquals("solution", op0.getFeatureName());
		assertEquals(issueId, op0.getModelElementId());
		assertEquals(solutionId, op0.getOldValue());
		assertNull(op0.getNewValue());

		assertEquals("issue", op1.getFeatureName());
		assertEquals(issueId, op1.getOldValue());
		assertEquals(solutionId, op1.getModelElementId());
		assertNull(op1.getNewValue());

		assertEquals(solutionId, op2.getModelElementId());
		assertEquals("leafSection", op2.getFeatureName());
		assertEquals(op2.getNewValue(), sectionId);
		assertNull(op2.getOldValue());

		assertEquals("modelElements", op3.getFeatureName());
		assertEquals(sectionId, op3.getModelElementId());
		assertEquals(solutionId, op3.getReferencedModelElements().get(0));
		assertEquals(1, op3.getReferencedModelElements().size());
		assertEquals(0, op3.getIndex());
		assertTrue(op3.isAdd());
	}

	/**
	 * Add several already contained children to an empty containment feature.
	 */
	@Test
	public void containmentAddDifferentFeatureContainedNChildrenToEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		WorkPackage oldWorkPackage = TaskFactory.eINSTANCE.createWorkPackage();
		WorkPackage oldWorkPackage2 = TaskFactory.eINSTANCE.createWorkPackage();

		BugReport bugReport1 = BugFactory.eINSTANCE.createBugReport();
		BugReport bugReport2 = BugFactory.eINSTANCE.createBugReport();
		BugReport bugReport3 = BugFactory.eINSTANCE.createBugReport();
		BugReport bugReport4 = BugFactory.eINSTANCE.createBugReport();

		getProject().addModelElement(bugReport1);
		getProject().addModelElement(bugReport2);
		getProject().addModelElement(bugReport3);
		getProject().addModelElement(bugReport4);
		getProject().addModelElement(section);
		getProject().addModelElement(oldWorkPackage);
		getProject().addModelElement(oldWorkPackage2);

		ModelElementId bugReport1Id = getProject().getModelElementId(bugReport1);
		ModelElementId bugReport2Id = getProject().getModelElementId(bugReport2);
		ModelElementId bugReport3Id = getProject().getModelElementId(bugReport3);
		ModelElementId bugReport4Id = getProject().getModelElementId(bugReport4);
		ModelElementId sectionId = getProject().getModelElementId(section);
		ModelElementId oldWorkPackageId = getProject().getModelElementId(oldWorkPackage);
		ModelElementId oldWorkPackageId2 = getProject().getModelElementId(oldWorkPackage2);

		BugReport[] actors = { bugReport1, bugReport2, bugReport3, bugReport4 };
		oldWorkPackage.getContainedWorkItems().addAll(Arrays.asList(actors));
		oldWorkPackage2.getContainedWorkItems().add(bugReport4); // relocate to other section

		assertTrue(oldWorkPackage.getContainedWorkItems().contains(bugReport1));
		assertTrue(oldWorkPackage.getContainedWorkItems().contains(bugReport2));
		assertTrue(oldWorkPackage.getContainedWorkItems().contains(bugReport3));
		assertTrue(oldWorkPackage2.getContainedWorkItems().contains(bugReport4));
		assertTrue(section.getModelElements().isEmpty());

		clearOperations();

		section.getModelElements().addAll(Arrays.asList(actors));

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());

		EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		// now expectation is: we get 4 messages preserving the info on former parents for the actors
		// and one additional one, indicating the new parent for all of them

		assertEquals(11, subOperations.size());

		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		SingleReferenceOperation op6 = checkAndCast(subOperations.get(6), SingleReferenceOperation.class);
		SingleReferenceOperation op8 = checkAndCast(subOperations.get(8), SingleReferenceOperation.class);
		SingleReferenceOperation op9 = checkAndCast(subOperations.get(9), SingleReferenceOperation.class);
		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		MultiReferenceOperation op7 = checkAndCast(subOperations.get(7), MultiReferenceOperation.class);
		MultiReferenceOperation op10 = checkAndCast(subOperations.get(10), MultiReferenceOperation.class);

		assertEquals(bugReport1Id, op1.getModelElementId());
		assertEquals(bugReport2Id, op3.getModelElementId());
		assertEquals(bugReport3Id, op5.getModelElementId());
		assertEquals(bugReport4Id, op8.getModelElementId());

		assertEquals("containingWorkpackage", op1.getFeatureName());
		assertEquals("containingWorkpackage", op3.getFeatureName());
		assertEquals("containingWorkpackage", op5.getFeatureName());
		assertEquals("containingWorkpackage", op8.getFeatureName());

		assertEquals(oldWorkPackageId, op1.getOldValue());
		assertEquals(oldWorkPackageId, op3.getOldValue());
		assertEquals(oldWorkPackageId, op5.getOldValue());
		assertEquals(oldWorkPackageId2, op8.getOldValue());

		assertNull(op1.getNewValue());
		assertNull(op3.getNewValue());
		assertNull(op5.getNewValue());
		assertNull(op8.getNewValue());

		assertEquals(bugReport1Id, op2.getModelElementId());
		assertEquals(bugReport2Id, op4.getModelElementId());
		assertEquals(bugReport3Id, op6.getModelElementId());
		assertEquals(bugReport4Id, op9.getModelElementId());

		assertEquals("leafSection", op2.getFeatureName());
		assertEquals("leafSection", op4.getFeatureName());
		assertEquals("leafSection", op6.getFeatureName());
		assertEquals("leafSection", op9.getFeatureName());

		assertNull(op2.getOldValue());
		assertNull(op4.getOldValue());
		assertNull(op6.getOldValue());
		assertNull(op9.getOldValue());

		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op4.getNewValue());
		assertEquals(sectionId, op6.getNewValue());
		assertEquals(sectionId, op9.getNewValue());

		assertEquals("containedWorkItems", op0.getFeatureName());
		assertEquals(oldWorkPackageId, op0.getModelElementId());
		assertEquals(bugReport1Id, op0.getReferencedModelElements().get(0));
		assertEquals(bugReport2Id, op0.getReferencedModelElements().get(1));
		assertEquals(bugReport3Id, op0.getReferencedModelElements().get(2));
		assertEquals(3, op0.getReferencedModelElements().size());
		assertFalse(op0.isAdd());

		assertEquals("containedWorkItems", op7.getFeatureName());
		assertEquals(oldWorkPackageId2, op7.getModelElementId());
		assertEquals(bugReport4Id, op7.getReferencedModelElements().get(0));
		assertFalse(op7.isAdd());
		assertEquals(1, op7.getReferencedModelElements().size());

		assertEquals("modelElements", op10.getFeatureName());
		assertEquals(sectionId, op10.getModelElementId());
		assertEquals(bugReport1Id, op10.getReferencedModelElements().get(0));
		assertEquals(bugReport2Id, op10.getReferencedModelElements().get(1));
		assertEquals(bugReport3Id, op10.getReferencedModelElements().get(2));
		assertEquals(bugReport4Id, op10.getReferencedModelElements().get(3));
		assertEquals(4, op10.getReferencedModelElements().size());
		assertEquals(0, op10.getIndex());
		assertTrue(op10.isAdd());

	}

	/**
	 * Add several already contained children to a non-empty containment feature.
	 */
	@Test
	public void containmentAddDifferentFeatureContainedNChildrenToNonEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		section.getModelElements().add(BugFactory.eINSTANCE.createBugReport());

		WorkPackage oldWorkPackage = TaskFactory.eINSTANCE.createWorkPackage();
		WorkPackage oldWorkPackage2 = TaskFactory.eINSTANCE.createWorkPackage();

		BugReport bugReport1 = BugFactory.eINSTANCE.createBugReport();
		BugReport bugReport2 = BugFactory.eINSTANCE.createBugReport();
		BugReport bugReport3 = BugFactory.eINSTANCE.createBugReport();
		BugReport bugReport4 = BugFactory.eINSTANCE.createBugReport();

		getProject().addModelElement(bugReport1);
		getProject().addModelElement(bugReport2);
		getProject().addModelElement(bugReport3);
		getProject().addModelElement(bugReport4);
		getProject().addModelElement(section);
		getProject().addModelElement(oldWorkPackage);
		getProject().addModelElement(oldWorkPackage2);

		ModelElementId sectionId = getProject().getModelElementId(section);
		ModelElementId oldWorkPackageId = getProject().getModelElementId(oldWorkPackage);
		ModelElementId oldWorkPackage2Id = getProject().getModelElementId(oldWorkPackage2);
		ModelElementId bugReport1Id = getProject().getModelElementId(bugReport1);
		ModelElementId bugReport2Id = getProject().getModelElementId(bugReport2);
		ModelElementId bugReport3Id = getProject().getModelElementId(bugReport3);
		ModelElementId bugReport4Id = getProject().getModelElementId(bugReport4);

		BugReport[] bugreports = { bugReport1, bugReport2, bugReport3, bugReport4 };
		oldWorkPackage.getContainedWorkItems().addAll(Arrays.asList(bugreports));
		oldWorkPackage2.getContainedWorkItems().add(bugReport4); // relocate to other section

		assertTrue(oldWorkPackage.getContainedWorkItems().contains(bugReport1));
		assertTrue(oldWorkPackage.getContainedWorkItems().contains(bugReport2));
		assertTrue(oldWorkPackage.getContainedWorkItems().contains(bugReport3));
		assertTrue(oldWorkPackage2.getContainedWorkItems().contains(bugReport4));
		assertFalse(section.getModelElements().isEmpty()); // one item is there initially

		clearOperations();

		section.getModelElements().addAll(Arrays.asList(bugreports));

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());

		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		// now expectation is: we get 4 messages preserving the info on former parents for the actors
		// and one additional one, indicating the new parent for all of them

		assertEquals(11, subOperations.size());

		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		SingleReferenceOperation op6 = checkAndCast(subOperations.get(6), SingleReferenceOperation.class);
		SingleReferenceOperation op8 = checkAndCast(subOperations.get(8), SingleReferenceOperation.class);
		SingleReferenceOperation op9 = checkAndCast(subOperations.get(9), SingleReferenceOperation.class);
		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		MultiReferenceOperation op7 = checkAndCast(subOperations.get(7), MultiReferenceOperation.class);
		MultiReferenceOperation op10 = checkAndCast(subOperations.get(10), MultiReferenceOperation.class);

		assertEquals(bugReport1Id, op1.getModelElementId());
		assertEquals(bugReport2Id, op3.getModelElementId());
		assertEquals(bugReport3Id, op5.getModelElementId());
		assertEquals(bugReport4Id, op8.getModelElementId());

		assertEquals("containingWorkpackage", op1.getFeatureName());
		assertEquals("containingWorkpackage", op3.getFeatureName());
		assertEquals("containingWorkpackage", op5.getFeatureName());
		assertEquals("containingWorkpackage", op8.getFeatureName());

		assertEquals(oldWorkPackageId, op1.getOldValue());
		assertEquals(oldWorkPackageId, op3.getOldValue());
		assertEquals(oldWorkPackageId, op5.getOldValue());
		assertEquals(oldWorkPackage2Id, op8.getOldValue());

		assertNull(op1.getNewValue());
		assertNull(op3.getNewValue());
		assertNull(op5.getNewValue());
		assertNull(op8.getNewValue());

		assertEquals(bugReport1Id, op2.getModelElementId());
		assertEquals(bugReport2Id, op4.getModelElementId());
		assertEquals(bugReport3Id, op6.getModelElementId());
		assertEquals(bugReport4Id, op9.getModelElementId());

		assertEquals("leafSection", op2.getFeatureName());
		assertEquals("leafSection", op4.getFeatureName());
		assertEquals("leafSection", op6.getFeatureName());
		assertEquals("leafSection", op9.getFeatureName());

		assertNull(op2.getOldValue());
		assertNull(op4.getOldValue());
		assertNull(op6.getOldValue());
		assertNull(op9.getOldValue());

		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op4.getNewValue());
		assertEquals(sectionId, op6.getNewValue());
		assertEquals(sectionId, op9.getNewValue());

		assertEquals("containedWorkItems", op0.getFeatureName());
		assertEquals(oldWorkPackageId, op0.getModelElementId());
		assertEquals(bugReport1Id, op0.getReferencedModelElements().get(0));
		assertEquals(bugReport2Id, op0.getReferencedModelElements().get(1));
		assertEquals(bugReport3Id, op0.getReferencedModelElements().get(2));
		assertEquals(3, op0.getReferencedModelElements().size());
		assertFalse(op0.isAdd());

		assertEquals("containedWorkItems", op7.getFeatureName());
		assertEquals(oldWorkPackage2Id, op7.getModelElementId());
		assertEquals(bugReport4Id, op7.getReferencedModelElements().get(0));
		assertEquals(1, op7.getReferencedModelElements().size());
		assertFalse(op7.isAdd());

		assertEquals("modelElements", op10.getFeatureName());
		assertEquals(op10.getModelElementId(), sectionId);
		assertEquals(bugReport1Id, op10.getReferencedModelElements().get(0));
		assertEquals(bugReport2Id, op10.getReferencedModelElements().get(1));
		assertEquals(bugReport3Id, op10.getReferencedModelElements().get(2));
		assertEquals(bugReport4Id, op10.getReferencedModelElements().get(3));
		assertEquals(4, op10.getReferencedModelElements().size());
		assertEquals(1, op10.getIndex());
		assertTrue(op10.isAdd());
	}

	/**
	 * Add several already contained children to an empty containment feature.
	 */
	@Test
	public void containmentAddDifferentFeatureContained1ChildrenToEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Solution solution1 = RationaleFactory.eINSTANCE.createSolution();
		Solution solution2 = RationaleFactory.eINSTANCE.createSolution();
		Issue issue1 = RationaleFactory.eINSTANCE.createIssue();
		Issue issue2 = RationaleFactory.eINSTANCE.createIssue();

		getProject().addModelElement(issue1);
		getProject().addModelElement(issue2);
		getProject().addModelElement(section);
		getProject().addModelElement(solution1);
		getProject().addModelElement(solution2);

		ModelElementId issue1Id = getProject().getModelElementId(issue1);
		ModelElementId issue2Id = getProject().getModelElementId(issue2);
		ModelElementId sectionId = getProject().getModelElementId(section);
		ModelElementId solution1Id = getProject().getModelElementId(solution1);
		ModelElementId solution2Id = getProject().getModelElementId(solution2);

		issue1.setSolution(solution1);
		issue2.setSolution(solution2);

		clearOperations();

		section.getModelElements().addAll(Arrays.asList(new Solution[] { solution1, solution2 }));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		// now expectation is: we get 2 messages preserving the info on former parents for the solutions
		// and one additional one, indicating the new parent for both of them

		assertEquals(7, subOperations.size());

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		MultiReferenceOperation op6 = checkAndCast(subOperations.get(6), MultiReferenceOperation.class);

		assertEquals(solution1Id, op1.getModelElementId());
		assertEquals(solution2Id, op4.getModelElementId());
		assertEquals("issue", op1.getFeatureName());
		assertEquals("issue", op4.getFeatureName());
		assertEquals(issue1Id, op1.getOldValue());
		assertEquals(issue2Id, op4.getOldValue());
		assertNull(op1.getNewValue());
		assertNull(op4.getNewValue());

		assertEquals(issue1Id, op0.getModelElementId());
		assertEquals(issue2Id, op3.getModelElementId());
		assertEquals("solution", op0.getFeatureName());
		assertEquals("solution", op3.getFeatureName());
		assertEquals(solution1Id, op0.getOldValue());
		assertEquals(solution2Id, op3.getOldValue());
		assertNull(op0.getNewValue());
		assertNull(op3.getNewValue());

		assertEquals(solution1Id, op2.getModelElementId());
		assertEquals(solution2Id, op5.getModelElementId());
		assertEquals("leafSection", op2.getFeatureName());
		assertEquals("leafSection", op5.getFeatureName());
		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op5.getNewValue());
		assertNull(op2.getOldValue());
		assertNull(op5.getOldValue());

		assertEquals("modelElements", op6.getFeatureName());
		assertEquals(sectionId, op6.getModelElementId());
		assertEquals(solution1Id, op6.getReferencedModelElements().get(0));
		assertEquals(solution2Id, op6.getReferencedModelElements().get(1));
		assertEquals(2, op6.getReferencedModelElements().size());
		assertEquals(0, op6.getIndex());
		assertTrue(op6.isAdd());

	}

	/**
	 * add several already contained children to a non-empty containment feature.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentAddDifferentFeatureContained1ChildrenToNonEmpty() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		Issue issue1 = RationaleFactory.eINSTANCE.createIssue();
		Issue issue2 = RationaleFactory.eINSTANCE.createIssue();
		Solution solution1 = RationaleFactory.eINSTANCE.createSolution();
		Solution solution2 = RationaleFactory.eINSTANCE.createSolution();
		section.getModelElements().add(RationaleFactory.eINSTANCE.createSolution()); // prefill section

		getProject().addModelElement(issue1);
		getProject().addModelElement(issue2);
		getProject().addModelElement(section);
		getProject().addModelElement(solution1);
		getProject().addModelElement(solution2);

		issue1.setSolution(solution1);
		issue2.setSolution(solution2);

		clearOperations();

		section.getModelElements().addAll(Arrays.asList(new Solution[] { solution1, solution2 }));

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());

		// now expectation is: we get 2 messages preserving the info on former parents for the solutions
		// and one additional one, indicating the new parent for both of them

		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(7, subOperations.size());

		ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);
		ModelElementId solution1Id = ModelUtil.getProject(solution1).getModelElementId(solution1);
		ModelElementId solution2Id = ModelUtil.getProject(solution2).getModelElementId(solution2);
		ModelElementId issue1Id = ModelUtil.getProject(issue1).getModelElementId(issue1);
		ModelElementId issue2Id = ModelUtil.getProject(issue2).getModelElementId(issue2);

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		MultiReferenceOperation op6 = checkAndCast(subOperations.get(6), MultiReferenceOperation.class);

		assertEquals(solution1Id, op1.getModelElementId());
		assertEquals(solution2Id, op4.getModelElementId());
		assertEquals("issue", op1.getFeatureName());
		assertEquals("issue", op4.getFeatureName());
		assertEquals(issue1Id, op1.getOldValue());
		assertEquals(issue2Id, op4.getOldValue());
		assertNull(op1.getNewValue());
		assertNull(op4.getNewValue());

		assertEquals(issue1Id, op0.getModelElementId());
		assertEquals(issue2Id, op3.getModelElementId());
		assertEquals("solution", op0.getFeatureName());
		assertEquals("solution", op3.getFeatureName());
		assertEquals(solution1Id, op0.getOldValue());
		assertEquals(solution2Id, op3.getOldValue());
		assertNull(op0.getNewValue());
		assertNull(op3.getNewValue());

		assertEquals(solution1Id, op2.getModelElementId());
		assertEquals(solution2Id, op5.getModelElementId());
		assertEquals("leafSection", op2.getFeatureName());
		assertEquals("leafSection", op5.getFeatureName());
		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op5.getNewValue());
		assertNull(op2.getOldValue());
		assertNull(op5.getOldValue());

		assertEquals(sectionId, op6.getModelElementId());
		assertEquals("modelElements", op6.getFeatureName());
		assertEquals(solution1Id, op6.getReferencedModelElements().get(0));
		assertEquals(solution2Id, op6.getReferencedModelElements().get(1));
		assertEquals(2, op6.getReferencedModelElements().size());
		assertEquals(1, op6.getIndex());
		assertTrue(op6.isAdd());
	}

	// BEGIN COMPLEX CODE
	/**
	 * Add several already contained children to an empty containment feature.
	 */
	@Test
	public void containmentAddMixedChildrenToEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		LeafSection oldSection1 = DocumentFactory.eINSTANCE.createLeafSection();
		LeafSection oldSection2 = DocumentFactory.eINSTANCE.createLeafSection();
		Issue issue1 = RationaleFactory.eINSTANCE.createIssue();
		Solution solution1 = RationaleFactory.eINSTANCE.createSolution();
		Issue issue2 = RationaleFactory.eINSTANCE.createIssue();
		Solution solution2 = RationaleFactory.eINSTANCE.createSolution();
		Solution newSolution = RationaleFactory.eINSTANCE.createSolution();
		Solution sectionSolution1 = RationaleFactory.eINSTANCE.createSolution();
		Solution sectionSolution2 = RationaleFactory.eINSTANCE.createSolution();
		Solution sectionSolution3 = RationaleFactory.eINSTANCE.createSolution();
		WorkPackage workPackage = TaskFactory.eINSTANCE.createWorkPackage();
		BugReport bugReport = BugFactory.eINSTANCE.createBugReport();

		getProject().addModelElement(issue1);
		getProject().addModelElement(issue2);
		getProject().addModelElement(section);
		getProject().addModelElement(oldSection1);
		getProject().addModelElement(oldSection2);
		getProject().addModelElement(newSolution);
		getProject().addModelElement(sectionSolution1);
		getProject().addModelElement(sectionSolution2);
		getProject().addModelElement(sectionSolution3);
		getProject().addModelElement(solution1);
		getProject().addModelElement(solution2);
		getProject().addModelElement(workPackage);
		getProject().addModelElement(bugReport);

		ModelElementId sectionId = getProject().getModelElementId(section);
		ModelElementId solution1Id = getProject().getModelElementId(solution1);
		ModelElementId solution2Id = getProject().getModelElementId(solution2);
		ModelElementId issue1Id = getProject().getModelElementId(issue1);
		ModelElementId issue2Id = getProject().getModelElementId(issue2);
		ModelElementId newSolutionId = getProject().getModelElementId(newSolution);
		ModelElementId oldSection1Id = getProject().getModelElementId(oldSection1);
		ModelElementId oldSection2Id = getProject().getModelElementId(oldSection2);
		ModelElementId sectionSolution1Id = getProject().getModelElementId(sectionSolution1);
		ModelElementId sectionSolution2Id = getProject().getModelElementId(sectionSolution2);
		ModelElementId sectionSolution3Id = getProject().getModelElementId(sectionSolution3);
		ModelElementId workPackageId = getProject().getModelElementId(workPackage);
		ModelElementId bugReportId = getProject().getModelElementId(bugReport);

		UnicaseModelElement[] addedElements = { solution1, solution2, newSolution, sectionSolution1, sectionSolution2,
			sectionSolution3, bugReport };
		issue1.setSolution(solution1);
		issue2.setSolution(solution2);
		workPackage.getContainedWorkItems().add(bugReport);
		oldSection1.getModelElements().add(sectionSolution1);
		oldSection1.getModelElements().add(sectionSolution2);
		oldSection2.getModelElements().add(sectionSolution3);

		assertTrue(oldSection1.getModelElements().contains(sectionSolution1));
		assertTrue(oldSection1.getModelElements().contains(sectionSolution2));
		assertTrue(oldSection2.getModelElements().contains(sectionSolution3));
		assertTrue(workPackage.getContainedWorkItems().contains(bugReport));

		clearOperations();

		section.getModelElements().addAll(Arrays.asList(addedElements));

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		// now expectation is: we get 6 messages preserving the info on former parents for the solutions
		// and one additional one, indicating the new parent for both of them

		assertEquals(16, subOperations.size());

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		SingleReferenceOperation op6 = checkAndCast(subOperations.get(6), SingleReferenceOperation.class);
		MultiReferenceOperation op7 = checkAndCast(subOperations.get(7), MultiReferenceOperation.class);
		SingleReferenceOperation op8 = checkAndCast(subOperations.get(8), SingleReferenceOperation.class);
		SingleReferenceOperation op9 = checkAndCast(subOperations.get(9), SingleReferenceOperation.class);
		MultiReferenceOperation op10 = checkAndCast(subOperations.get(10), MultiReferenceOperation.class);
		SingleReferenceOperation op11 = checkAndCast(subOperations.get(11), SingleReferenceOperation.class);
		MultiReferenceOperation op12 = checkAndCast(subOperations.get(12), MultiReferenceOperation.class);
		SingleReferenceOperation op13 = checkAndCast(subOperations.get(13), SingleReferenceOperation.class);
		SingleReferenceOperation op14 = checkAndCast(subOperations.get(14), SingleReferenceOperation.class);
		MultiReferenceOperation op15 = checkAndCast(subOperations.get(15), MultiReferenceOperation.class);

		assertEquals("solution", op0.getFeatureName());
		assertEquals(issue1Id, op0.getModelElementId());
		assertEquals(solution1Id, op0.getOldValue());
		assertNull(op0.getNewValue());

		assertEquals("issue", op1.getFeatureName());
		assertEquals(solution1Id, op1.getModelElementId());
		assertEquals(issue1Id, op1.getOldValue());
		assertNull(op1.getNewValue());

		assertEquals("leafSection", op2.getFeatureName());
		assertEquals(solution1Id, op2.getModelElementId());
		assertEquals(sectionId, op2.getNewValue());
		assertNull(op2.getOldValue());

		assertEquals("solution", op3.getFeatureName());
		assertEquals(issue2Id, op3.getModelElementId());
		assertNull(op3.getNewValue());
		assertEquals(solution2Id, op3.getOldValue());

		assertEquals("issue", op4.getFeatureName());
		assertEquals(solution2Id, op4.getModelElementId());
		assertEquals(issue2Id, op4.getOldValue());
		assertNull(op4.getNewValue());

		assertEquals(solution2Id, op5.getModelElementId());
		assertEquals("leafSection", op5.getFeatureName());
		assertEquals(sectionId, op5.getNewValue());
		assertNull(op5.getOldValue());

		assertEquals(newSolutionId, op6.getModelElementId());
		assertEquals("leafSection", op6.getFeatureName());
		assertEquals(sectionId, op6.getNewValue());
		assertNull(op6.getOldValue());

		assertEquals("modelElements", op7.getFeatureName());
		assertEquals(oldSection1Id, op7.getModelElementId());
		assertEquals(sectionSolution1Id, op7.getReferencedModelElements().get(0));
		assertEquals(sectionSolution2Id, op7.getReferencedModelElements().get(1));
		assertEquals(2, op7.getReferencedModelElements().size());
		assertFalse(op7.isAdd());

		assertEquals("leafSection", op8.getFeatureName());
		assertEquals(sectionSolution1Id, op8.getModelElementId());
		assertEquals(sectionId, op8.getNewValue());
		assertEquals(oldSection1Id, op8.getOldValue());

		assertEquals("leafSection", op9.getFeatureName());
		assertEquals(sectionSolution2Id, op9.getModelElementId());
		assertEquals(sectionId, op9.getNewValue());
		assertEquals(oldSection1Id, op9.getOldValue());

		assertEquals("modelElements", op10.getFeatureName());
		assertEquals(oldSection2Id, op10.getModelElementId());
		assertEquals(sectionSolution3Id, op10.getReferencedModelElements().get(0));
		assertEquals(1, op10.getReferencedModelElements().size());
		assertFalse(op10.isAdd());

		assertEquals("leafSection", op11.getFeatureName());
		assertEquals(sectionSolution3Id, op11.getModelElementId());
		assertEquals(sectionId, op11.getNewValue());
		assertEquals(oldSection2Id, op11.getOldValue());

		assertEquals("containedWorkItems", op12.getFeatureName());
		assertEquals(workPackageId, op12.getModelElementId());
		assertEquals(bugReportId, op12.getReferencedModelElements().get(0));
		assertEquals(1, op12.getReferencedModelElements().size());
		assertFalse(op12.isAdd());

		assertEquals("containingWorkpackage", op13.getFeatureName());
		assertEquals(bugReportId, op13.getModelElementId());
		assertEquals(workPackageId, op13.getOldValue());
		assertNull(op13.getNewValue());

		assertEquals("leafSection", op14.getFeatureName());
		assertEquals(bugReportId, op14.getModelElementId());
		assertEquals(sectionId, op14.getNewValue());
		assertNull(op14.getOldValue());

		assertEquals("modelElements", op15.getFeatureName());
		assertEquals(7, op15.getReferencedModelElements().size());
		assertEquals(0, op15.getIndex());
		assertEquals(sectionId, op15.getModelElementId());
		assertEquals(solution1Id, op15.getReferencedModelElements().get(0));
		assertEquals(solution2Id, op15.getReferencedModelElements().get(1));
		assertEquals(newSolutionId, op15.getReferencedModelElements().get(2));
		assertEquals(sectionSolution1Id, op15.getReferencedModelElements().get(3));
		assertEquals(sectionSolution2Id, op15.getReferencedModelElements().get(4));
		assertEquals(sectionSolution3Id, op15.getReferencedModelElements().get(5));
		assertEquals(bugReportId, op15.getReferencedModelElements().get(6));
		assertTrue(op15.isAdd());

	}

	/**
	 * Add several already contained children to an empty containment feature.
	 */
	@Test
	public void containmentAddMixedChildrenToNonEmpty() {

		LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		section.getModelElements().add(RationaleFactory.eINSTANCE.createIssue()); // prefill section
		LeafSection oldSection1 = DocumentFactory.eINSTANCE.createLeafSection();
		LeafSection oldSection2 = DocumentFactory.eINSTANCE.createLeafSection();
		Issue issue1 = RationaleFactory.eINSTANCE.createIssue();
		Solution solution1 = RationaleFactory.eINSTANCE.createSolution();
		Issue issue2 = RationaleFactory.eINSTANCE.createIssue();
		Solution solution2 = RationaleFactory.eINSTANCE.createSolution();
		Solution newSolution = RationaleFactory.eINSTANCE.createSolution();
		Solution sectionSolution1 = RationaleFactory.eINSTANCE.createSolution();
		Solution sectionSolution2 = RationaleFactory.eINSTANCE.createSolution();
		Solution sectionSolution3 = RationaleFactory.eINSTANCE.createSolution();
		WorkPackage workPackage = TaskFactory.eINSTANCE.createWorkPackage();
		BugReport bugReport = BugFactory.eINSTANCE.createBugReport();

		getProject().addModelElement(issue1);
		getProject().addModelElement(issue2);
		getProject().addModelElement(section);
		getProject().addModelElement(oldSection1);
		getProject().addModelElement(oldSection2);
		getProject().addModelElement(newSolution);
		getProject().addModelElement(sectionSolution1);
		getProject().addModelElement(sectionSolution2);
		getProject().addModelElement(sectionSolution3);
		getProject().addModelElement(solution1);
		getProject().addModelElement(solution2);
		getProject().addModelElement(workPackage);
		getProject().addModelElement(bugReport);

		ModelElementId issue1Id = getProject().getModelElementId(issue1);
		ModelElementId issue2Id = getProject().getModelElementId(issue2);
		ModelElementId oldSection1Id = getProject().getModelElementId(oldSection1);
		ModelElementId oldSection2Id = getProject().getModelElementId(oldSection2);
		ModelElementId sectionSolution1Id = getProject().getModelElementId(sectionSolution1);
		ModelElementId sectionSolution2Id = getProject().getModelElementId(sectionSolution2);
		ModelElementId sectionSolution3Id = getProject().getModelElementId(sectionSolution3);
		ModelElementId sectionId = getProject().getModelElementId(section);
		ModelElementId newSolutionId = getProject().getModelElementId(newSolution);
		ModelElementId solution1Id = getProject().getModelElementId(solution1);
		ModelElementId solution2Id = getProject().getModelElementId(solution2);
		ModelElementId bugReportId = getProject().getModelElementId(bugReport);
		ModelElementId workPackageId = getProject().getModelElementId(workPackage);

		UnicaseModelElement[] addedElements = { solution1, solution2, newSolution, sectionSolution1, sectionSolution2,
			sectionSolution3, bugReport };
		issue1.setSolution(solution1);
		issue2.setSolution(solution2);
		workPackage.getContainedWorkItems().add(bugReport);
		oldSection1.getModelElements().add(sectionSolution1);
		oldSection1.getModelElements().add(sectionSolution2);
		oldSection2.getModelElements().add(sectionSolution3);

		assertTrue(oldSection1.getModelElements().contains(sectionSolution1));
		assertTrue(oldSection1.getModelElements().contains(sectionSolution2));
		assertTrue(oldSection2.getModelElements().contains(sectionSolution3));
		assertTrue(workPackage.getContainedWorkItems().contains(bugReport));

		clearOperations();

		section.getModelElements().addAll(Arrays.asList(addedElements));

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		// now expectation is: we get 6 messages preserving the info on former parents for the solutions
		// and one additional one, indicating the new parent for both of them

		assertEquals(16, subOperations.size());

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		SingleReferenceOperation op6 = checkAndCast(subOperations.get(6), SingleReferenceOperation.class);
		MultiReferenceOperation op7 = checkAndCast(subOperations.get(7), MultiReferenceOperation.class);
		SingleReferenceOperation op8 = checkAndCast(subOperations.get(8), SingleReferenceOperation.class);
		SingleReferenceOperation op9 = checkAndCast(subOperations.get(9), SingleReferenceOperation.class);
		MultiReferenceOperation op10 = checkAndCast(subOperations.get(10), MultiReferenceOperation.class);
		SingleReferenceOperation op11 = checkAndCast(subOperations.get(11), SingleReferenceOperation.class);
		MultiReferenceOperation op12 = checkAndCast(subOperations.get(12), MultiReferenceOperation.class);
		SingleReferenceOperation op13 = checkAndCast(subOperations.get(13), SingleReferenceOperation.class);
		SingleReferenceOperation op14 = checkAndCast(subOperations.get(14), SingleReferenceOperation.class);
		MultiReferenceOperation op15 = checkAndCast(subOperations.get(15), MultiReferenceOperation.class);

		assertEquals(op0.getModelElementId(), issue1Id);
		assertEquals(op0.getFeatureName(), "solution");
		assertEquals(op0.getNewValue(), null);
		assertEquals(op0.getOldValue(), solution1Id);

		assertEquals(op1.getModelElementId(), solution1Id);
		assertEquals(op1.getFeatureName(), "issue");
		assertEquals(op1.getNewValue(), null);
		assertEquals(op1.getOldValue(), issue1Id);

		assertEquals(op2.getModelElementId(), solution1Id);
		assertEquals(op2.getFeatureName(), "leafSection");
		assertEquals(op2.getNewValue(), sectionId);
		assertEquals(op2.getOldValue(), null);

		assertEquals(op3.getModelElementId(), issue2Id);
		assertEquals(op3.getFeatureName(), "solution");
		assertEquals(op3.getNewValue(), null);
		assertEquals(op3.getOldValue(), solution2Id);

		assertEquals(op4.getModelElementId(), solution2Id);
		assertEquals(op4.getFeatureName(), "issue");
		assertEquals(op4.getNewValue(), null);
		assertEquals(op4.getOldValue(), issue2Id);

		assertEquals(op5.getModelElementId(), solution2Id);
		assertEquals(op5.getFeatureName(), "leafSection");
		assertEquals(op5.getNewValue(), sectionId);
		assertEquals(op5.getOldValue(), null);

		assertEquals(op6.getModelElementId(), newSolutionId);
		assertEquals(op6.getFeatureName(), "leafSection");
		assertEquals(op6.getNewValue(), sectionId);
		assertEquals(op6.getOldValue(), null);

		assertEquals(op7.getModelElementId(), oldSection1Id);
		assertEquals(op7.getFeatureName(), "modelElements");
		assertEquals(op7.isAdd(), false);
		assertEquals(op7.getReferencedModelElements().size(), 2);
		assertEquals(op7.getReferencedModelElements().get(0), sectionSolution1Id);
		assertEquals(op7.getReferencedModelElements().get(1), sectionSolution2Id);

		assertEquals("leafSection", op8.getFeatureName());
		assertEquals(sectionSolution1Id, op8.getModelElementId());
		assertEquals(sectionId, op8.getNewValue());
		assertEquals(oldSection1Id, op8.getOldValue());

		assertEquals("leafSection", op9.getFeatureName());
		assertEquals(sectionSolution2Id, op9.getModelElementId());
		assertEquals(sectionId, op9.getNewValue());
		assertEquals(oldSection1Id, op9.getOldValue());

		assertEquals("modelElements", op10.getFeatureName());
		assertEquals(oldSection2Id, op10.getModelElementId());
		assertEquals(sectionSolution3Id, op10.getReferencedModelElements().get(0));
		assertEquals(1, op10.getReferencedModelElements().size());
		assertFalse(op10.isAdd());

		assertEquals("leafSection", op11.getFeatureName());
		assertEquals(sectionSolution3Id, op11.getModelElementId());
		assertEquals(sectionId, op11.getNewValue());
		assertEquals(oldSection2Id, op11.getOldValue());

		assertEquals("containedWorkItems", op12.getFeatureName());
		assertEquals(workPackageId, op12.getModelElementId());
		assertEquals(bugReportId, op12.getReferencedModelElements().get(0));
		assertEquals(1, op12.getReferencedModelElements().size());
		assertFalse(op12.isAdd());

		assertEquals("containingWorkpackage", op13.getFeatureName());
		assertEquals(bugReportId, op13.getModelElementId());
		assertEquals(workPackageId, op13.getOldValue());
		assertNull(op13.getNewValue());

		assertEquals("leafSection", op14.getFeatureName());
		assertEquals(bugReportId, op14.getModelElementId());
		assertEquals(sectionId, op14.getNewValue());
		assertNull(op14.getOldValue());

		assertEquals("modelElements", op15.getFeatureName());
		assertEquals(sectionId, op15.getModelElementId());
		assertTrue(op15.isAdd());
		assertEquals(1, op15.getIndex());
		assertEquals(7, op15.getReferencedModelElements().size());
		assertEquals(solution1Id, op15.getReferencedModelElements().get(0));
		assertEquals(solution2Id, op15.getReferencedModelElements().get(1));
		assertEquals(newSolutionId, op15.getReferencedModelElements().get(2));
		assertEquals(sectionSolution1Id, op15.getReferencedModelElements().get(3));
		assertEquals(sectionSolution2Id, op15.getReferencedModelElements().get(4));
		assertEquals(sectionSolution3Id, op15.getReferencedModelElements().get(5));
		assertEquals(bugReportId, op15.getReferencedModelElements().get(6));

	}

	/**
	 * remove last child from a containment feature.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentRemoveChildAndEmpty() throws UnsupportedOperationException, UnsupportedNotificationException {

		final LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		final Actor actor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor);
		getProject().addModelElement(section);
		section.getModelElements().add(actor);

		clearOperations();
		ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().remove(actor);
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		List<ReferenceOperation> subOperations = checkAndCast(operations.get(0), CreateDeleteOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		ModelElementId sectionId = getProject().getModelElementId(section);

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals(actorId, op0.getModelElementId());
		assertEquals(sectionId, op0.getOldValue());
		assertEquals("leafSection", op0.getFeatureName());
		assertNull(op0.getNewValue());

		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals("modelElements", op1.getFeatureName());
		assertEquals(sectionId, op1.getModelElementId());
		assertEquals(0, op1.getIndex());
		assertFalse(op1.isAdd());

	}

	/**
	 * remove all children from a containment feature.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentRemoveChildrenAndEmpty() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		final Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		final Actor actor2 = RequirementFactory.eINSTANCE.createActor();
		final Actor[] actors = { actor1, actor2 };

		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(section);

		section.getModelElements().addAll(Arrays.asList(actors));

		clearOperations();

		ModelElementId actor1Id = getProject().getModelElementId(actor1);
		ModelElementId actor2Id = getProject().getModelElementId(actor2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().removeAll(Arrays.asList(actors));
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(3, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		checkAndCast(operations.get(1), CreateDeleteOperation.class);
		checkAndCast(operations.get(2), CreateDeleteOperation.class);
		assertEquals(3, subOperations.size());

		ModelElementId sectionId = getProject().getModelElementId(section);

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(sectionId, op0.getOldValue());
		assertEquals("leafSection", op0.getFeatureName());
		assertNull(op0.getNewValue());

		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(sectionId, op1.getOldValue());
		assertEquals("leafSection", op1.getFeatureName());
		assertNull(op1.getNewValue());

		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals("modelElements", op2.getFeatureName());
		assertEquals(sectionId, op2.getModelElementId());
		assertEquals(0, op2.getIndex());
		assertFalse(op2.isAdd());
	}

	/**
	 * remove non-last child from a containment feature.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentRemoveChildPart() throws UnsupportedOperationException, UnsupportedNotificationException {

		final LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		final Actor actor = RequirementFactory.eINSTANCE.createActor();
		final Actor oldActor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor);
		getProject().addModelElement(oldActor);
		getProject().addModelElement(section);

		section.getModelElements().add(oldActor);
		section.getModelElements().add(actor);

		clearOperations();
		ModelElementId actorId = getProject().getModelElementId(actor);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().remove(actor);
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		List<ReferenceOperation> subOperations = checkAndCast(operations.get(0), CreateDeleteOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		ModelElementId sectionId = getProject().getModelElementId(section);

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals(actorId, op0.getModelElementId());
		assertEquals(sectionId, op0.getOldValue());
		assertEquals("leafSection", op0.getFeatureName());
		assertNull(op0.getNewValue());

		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals(sectionId, op1.getModelElementId());
		assertEquals("modelElements", op1.getFeatureName());
		assertEquals(1, op1.getIndex());
		assertFalse(op1.isAdd());

	}

	/**
	 * add a child to an empty non-containment feature.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void nonContainmentAddChildToEmpty() throws UnsupportedOperationException, UnsupportedNotificationException {

		UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		Actor actor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor);
		getProject().addModelElement(useCase);

		clearOperations();

		useCase.getParticipatingActors().add(actor);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(2, subOperations.size());

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op0 = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op0.isAdd());
		assertEquals("participatedUseCases", op0.getFeatureName());

		ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(actorId, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		MultiReferenceOperation op1 = (MultiReferenceOperation) subOperations.get(1);
		assertTrue(op1.isAdd());
		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals("participatingActors", op1.getFeatureName());
		assertEquals(op1.getModelElementId(), useCaseId);
		assertEquals(op1.getIndex(), 0);

	}

	/**
	 * add some children to an empty non-containment feature.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void nonContainmentAddChildrenToEmpty() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		Actor actor2 = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(useCase);

		Actor[] actors = { actor1, actor2 };

		clearOperations();

		useCase.getParticipatingActors().addAll(Arrays.asList(actors));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(3, subOperations.size());

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op0 = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op0.isAdd());
		assertEquals("participatedUseCases", op0.getFeatureName());

		ModelElementId actor1Id = ModelUtil.getProject(actor1).getModelElementId(actor1);
		ModelElementId actor2Id = ModelUtil.getProject(actor2).getModelElementId(actor2);
		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		MultiReferenceOperation op1 = (MultiReferenceOperation) subOperations.get(1);
		assertTrue(op1.isAdd());
		assertEquals("participatedUseCases", op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(useCaseId, op1.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(2) instanceof MultiReferenceOperation);
		MultiReferenceOperation op2 = (MultiReferenceOperation) subOperations.get(2);
		assertTrue(op2.isAdd());
		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals("participatingActors", op2.getFeatureName());
		assertEquals(op2.getModelElementId(), useCaseId);
		assertEquals(op2.getIndex(), 0);

	}

	/**
	 * add a child to a non-empty non-containment feature.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void nonContainmentAddChildToNonEmpty() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		Actor oldActor = RequirementFactory.eINSTANCE.createActor();
		Actor actor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor);
		getProject().addModelElement(oldActor);
		getProject().addModelElement(useCase);

		useCase.getParticipatingActors().add(oldActor);

		clearOperations();

		useCase.getParticipatingActors().add(actor);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(2, subOperations.size());

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op0 = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op0.isAdd());
		assertEquals("participatedUseCases", op0.getFeatureName());

		ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(actorId, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		MultiReferenceOperation op1 = (MultiReferenceOperation) subOperations.get(1);
		assertTrue(op1.isAdd());
		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals("participatingActors", op1.getFeatureName());
		assertEquals(op1.getModelElementId(), useCaseId);
		assertEquals(op1.getIndex(), 1);

	}

	/**
	 * add some children to a non-empty non-containment feature.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void nonContainmentAddChildrenToNonEmpty() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		Actor oldActor = RequirementFactory.eINSTANCE.createActor();
		Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		Actor actor2 = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(oldActor);
		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(useCase);

		Actor[] actors = { actor1, actor2 };
		useCase.getParticipatingActors().add(oldActor);
		clearOperations();

		useCase.getParticipatingActors().addAll(Arrays.asList(actors));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(3, subOperations.size());

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op0 = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op0.isAdd());
		assertEquals("participatedUseCases", op0.getFeatureName());

		ModelElementId actor1Id = ModelUtil.getProject(actor1).getModelElementId(actor1);
		ModelElementId actor2Id = ModelUtil.getProject(actor2).getModelElementId(actor2);
		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		MultiReferenceOperation op1 = (MultiReferenceOperation) subOperations.get(1);
		assertTrue(op1.isAdd());
		assertEquals("participatedUseCases", op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(useCaseId, op1.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(2) instanceof MultiReferenceOperation);
		MultiReferenceOperation op2 = (MultiReferenceOperation) subOperations.get(2);
		assertTrue(op2.isAdd());
		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals("participatingActors", op2.getFeatureName());
		assertEquals(op2.getModelElementId(), useCaseId);
		assertEquals(op2.getIndex(), 1);

	}

	/**
	 * remove last child from non-containment feature.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void nonContainmentRemoveChildAndEmpty() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		Actor actor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor);
		getProject().addModelElement(useCase);
		useCase.getParticipatingActors().add(actor);

		clearOperations();

		useCase.getParticipatingActors().remove(actor);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		// expecting a composite operation here
		assertEquals(1, operations.size());
		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail("composite operation expected");
		}

		assertEquals(2, operations.size());

		assertTrue(operations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op0 = (MultiReferenceOperation) operations.get(0);
		assertFalse(op0.isAdd());
		assertEquals(1, op0.getReferencedModelElements().size());

		ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));
		assertEquals("participatedUseCases", op0.getFeatureName());
		assertEquals(op0.getModelElementId(), actorId);
		assertEquals(op0.getIndex(), 0);

		assertTrue(operations.get(1) instanceof MultiReferenceOperation);
		MultiReferenceOperation op1 = (MultiReferenceOperation) operations.get(1);
		assertFalse(op1.isAdd());
		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals("participatingActors", op1.getFeatureName());
		assertEquals(op1.getModelElementId(), useCaseId);
		assertEquals(op1.getIndex(), 0);

	}

	/**
	 * Remove non-last child from non-containment feature.
	 */
	@Test
	public void nonContainmentRemoveChildPart() {

		UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		Actor actor = RequirementFactory.eINSTANCE.createActor();
		Actor oldActor = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor);
		getProject().addModelElement(oldActor);
		getProject().addModelElement(useCase);

		ModelElementId actorId = getProject().getModelElementId(actor);
		ModelElementId useCaseId = getProject().getModelElementId(useCase);

		useCase.getParticipatingActors().add(oldActor);
		useCase.getParticipatingActors().add(actor);

		clearOperations();

		useCase.getParticipatingActors().remove(actor);

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals(1, op0.getReferencedModelElements().size());
		assertFalse(op0.isAdd());

		assertEquals("participatedUseCases", op0.getFeatureName());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));
		assertEquals(actorId, op0.getModelElementId());
		assertEquals(0, op0.getIndex());

		assertEquals("participatingActors", op1.getFeatureName());
		assertEquals(1, op1.getIndex());
		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals(useCaseId, op1.getModelElementId());
		assertFalse(op1.isAdd());
	}

	/**
	 * Remove all children from non-containment feature.
	 */
	@Test
	public void nonContainmentRemoveChildrenAndEmpty() {

		UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		Actor actor2 = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(useCase);

		ModelElementId actor1Id = getProject().getModelElementId(actor1);
		ModelElementId actor2Id = getProject().getModelElementId(actor2);
		ModelElementId useCaseId = getProject().getModelElementId(useCase);

		Actor[] actors = { actor1, actor2 };

		useCase.getParticipatingActors().addAll(Arrays.asList(actors));

		clearOperations();

		useCase.getParticipatingActors().removeAll(Arrays.asList(actors));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(3, subOperations.size());

		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);
		MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals("participatedUseCases", op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));
		assertFalse(op0.isAdd());

		assertEquals("participatedUseCases", op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(useCaseId, op1.getReferencedModelElements().get(0));
		assertFalse(op1.isAdd());

		assertEquals("participatingActors", op2.getFeatureName());
		assertEquals(0, op2.getIndex());
		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals(useCaseId, op2.getModelElementId());
		assertFalse(op2.isAdd());
	}

	/**
	 * Remove some children from non-containment feature.
	 */
	@Test
	public void nonContainmentRemoveChildrenPart() {

		UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		Actor oldActor = RequirementFactory.eINSTANCE.createActor();
		Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		Actor actor2 = RequirementFactory.eINSTANCE.createActor();

		getProject().addModelElement(oldActor);
		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(useCase);

		ModelElementId actor1Id = getProject().getModelElementId(actor1);
		ModelElementId actor2Id = getProject().getModelElementId(actor2);
		ModelElementId useCaseId = getProject().getModelElementId(useCase);
		Actor[] actors = { actor1, actor2 };

		useCase.getParticipatingActors().add(oldActor);
		useCase.getParticipatingActors().addAll(Arrays.asList(actors));

		clearOperations();

		useCase.getParticipatingActors().removeAll(Arrays.asList(actors));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(3, subOperations.size());

		MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);
		MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals("participatedUseCases", op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));
		assertFalse(op0.isAdd());

		assertEquals("participatedUseCases", op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(useCaseId, op1.getReferencedModelElements().get(0));
		assertFalse(op1.isAdd());

		assertEquals("participatingActors", op2.getFeatureName());
		assertEquals(1, op2.getIndex());
		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals(useCaseId, op2.getModelElementId());
		assertFalse(op2.isAdd());

	}

	/**
	 * remove some children from a containment feature.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentRemoveChildrenPart() throws UnsupportedOperationException, UnsupportedNotificationException {

		final LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		final Actor oldActor = RequirementFactory.eINSTANCE.createActor();
		final Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		final Actor actor2 = RequirementFactory.eINSTANCE.createActor();
		final Actor[] actors = { actor1, actor2 };

		getProject().addModelElement(oldActor);
		getProject().addModelElement(actor1);
		getProject().addModelElement(actor2);
		getProject().addModelElement(section);

		section.getModelElements().add(oldActor);
		section.getModelElements().addAll(Arrays.asList(actors));

		clearOperations();
		ModelElementId actor1Id = getProject().getModelElementId(actor1);
		ModelElementId actor2Id = getProject().getModelElementId(actor2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().removeAll(Arrays.asList(actors));
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(3, operations.size());
		List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		checkAndCast(operations.get(1), CreateDeleteOperation.class);
		checkAndCast(operations.get(2), CreateDeleteOperation.class);
		assertEquals(3, subOperations.size());

		ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);

		SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals("leafSection", op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(sectionId, op0.getOldValue());
		assertNull(op0.getNewValue());

		assertEquals("leafSection", op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(sectionId, op1.getOldValue());
		assertNull(op1.getNewValue());

		assertEquals("modelElements", op2.getFeatureName());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals(sectionId, op2.getModelElementId());
		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(1, op2.getIndex());
		assertFalse(op2.isAdd());
	}

}