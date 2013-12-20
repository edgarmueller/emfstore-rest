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
package org.eclipse.emf.emfstore.client.changetracking.test.toplogy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.test.common.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Delete;
import org.eclipse.emf.emfstore.client.test.common.dsl.Update;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.ReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests operations in 1:n topologies.
 * 
 * @author chodnick
 * @author emueller
 */
public class Topology1toNTest extends ESTest {

	private static final String ACTOR2 = "actor2"; //$NON-NLS-1$
	private static final String ACTOR = "actor"; //$NON-NLS-1$

	/**
	 * Add an uncontained child to an empty containment feature.
	 */
	@Test
	public void containmentAddUncontainedChildToEmpty() {

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), section);
		final ModelElementId actorId = getProject().getModelElementId(actor);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		clearOperations();

		Add.toContainedElements(section, actor);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		final CompositeOperation operation = checkAndCast(operations.get(0), CompositeOperation.class);

		final List<AbstractOperation> subOperations = operation.getSubOperations();
		assertEquals(2, subOperations.size());

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.container().getName(), op0.getFeatureName());
		assertEquals(actorId, op0.getModelElementId());
		assertEquals(sectionId, op0.getNewValue());
		assertNull(op0.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op1.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), section);
		final ModelElementId actorId = getProject().getModelElementId(actor);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		Add.toContainedElements(section, actor);

		clearOperations();

		Add.toProject(getLocalProject(), actor);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		final EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		assertEquals(TestElementFeatures.containedElements().getName(), op0.getFeatureName());
		assertEquals(sectionId, op0.getModelElementId());
		assertEquals(actorId, op0.getReferencedModelElements().get(0));

		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertEquals(op1.getModelElementId(), actorId);

	}

	/**
	 * Reverse orphan creation.
	 */
	@Test
	public void reverseContainmentOrphan() {

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), section);

		final ModelElementId actorId = getProject().getModelElementId(actor);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		Add.toContainedElements(section, actor);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		clearOperations();

		// create orphan
		Add.toProject(getLocalProject(), actor);

		final List<AbstractOperation> operations2 = getProjectSpace().getOperations();

		assertEquals(1, operations2.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations2.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(2, subOperations.size());

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);

		assertEquals(TestElementFeatures.containedElements().getName(), op0.getFeatureName());
		assertEquals(sectionId, op0.getModelElementId());
		assertEquals(actorId, op0.getReferencedModelElements().get(0));

		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertEquals(actorId, op1.getModelElementId());

		// test the reversibility of what has happened
		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				op1.reverse().apply(getProject());
				op0.reverse().apply(getProject());
			}
		});

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Reverse orphan creation.
	 */
	@Test
	public void reverseContainmentOrphanIndexed() {

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement actor2 = Create.testElement();

		actor.setName(ACTOR);
		actor2.setName(ACTOR2);

		Add.toProject(getLocalProject(), section);

		Add.toContainedElements(section, actor);
		Add.toContainedElements(section, actor2);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		clearOperations();
		// create orphan
		Add.toProject(getLocalProject(), actor);

		final ModelElementId actorId = getProject().getModelElementId(actor);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);

		assertEquals(TestElementFeatures.containedElements().getName(), op0.getFeatureName());
		assertEquals(sectionId, op0.getModelElementId());
		assertEquals(actorId, op0.getReferencedModelElements().get(0));

		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertEquals(actorId, op1.getModelElementId());

		// test the reversibility of what has happened
		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				op1.reverse().apply(getProject());
				op0.reverse().apply(getProject());
			}
		});

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Add an uncontained child to a non-empty containment feature.
	 * 
	 */
	@Test
	public void containmentAddUncontainedChildToNonEmpty() {

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement oldTestElement = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), oldTestElement);
		Add.toProject(getLocalProject(), section);

		final ModelElementId actorId = getProject().getModelElementId(actor);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		Add.toContainedElements(section, oldTestElement);

		clearOperations();

		Add.toContainedElements(section, actor);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(2, subOperations.size());
		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.container().getName(), op0.getFeatureName());
		assertEquals(actorId, op0.getModelElementId());
		assertEquals(sectionId, op0.getNewValue());
		assertNull(op0.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op1.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();

		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), section);
		final ModelElementId actor1Id = getProject().getModelElementId(actor1);
		final ModelElementId actor2Id = getProject().getModelElementId(actor2);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		final TestElement[] actors = { actor1, actor2 };

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(3, subOperations.size());

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.container().getName(), op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(sectionId, op0.getNewValue());
		assertNull(op0.getOldValue());

		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(sectionId, op1.getNewValue());
		assertNull(op1.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op2.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();
		final TestElement oldTestElement = Create.testElement();

		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), oldTestElement);

		final ModelElementId actor1Id = getProject().getModelElementId(actor1);
		final ModelElementId actor2Id = getProject().getModelElementId(actor2);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		final TestElement[] actors = { actor1, actor2 };

		Add.toContainedElements(section, oldTestElement);

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(3, subOperations.size());
		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.container().getName(), op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(sectionId, op0.getNewValue());
		assertNull(op0.getOldValue());

		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(sectionId, op1.getNewValue());
		assertNull(op1.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op2.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement oldTestElement = Create.testElement();

		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), oldTestElement);
		Add.toProject(getLocalProject(), section);

		final ModelElementId sectionId = getProject().getModelElementId(section);
		final ModelElementId actor1Id = getProject().getModelElementId(actor1);

		final TestElement[] actors = { actor1 };

		Add.toContainedElements(section, oldTestElement);

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(2, subOperations.size());
		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.container().getName(), op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(sectionId, op0.getNewValue());
		assertNull(op0.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op1.getFeatureName());
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

		final TestElement section1 = Create.testElement();
		final TestElement section2 = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();

		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), section1);
		Add.toProject(getLocalProject(), section2);

		final ModelElementId section1Id = getProject().getModelElementId(section1);
		final ModelElementId section2Id = getProject().getModelElementId(section2);
		final ModelElementId actor2Id = getProject().getModelElementId(actor2);

		Add.toContainedElements(section1, actor1);
		Add.toContainedElements(section2, actor2);

		clearOperations();

		Add.toContainedElements(section1, actor2);

		assertFalse(section2.getContainedElements().contains(actor2));
		assertTrue(section1.getContainedElements().contains(actor2));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(3, subOperations.size());

		// 1st op: maintain change in section2, preserving index of actor 2
		// 2nd op: TestElement change on actor2 (preserving old parent)
		// 3rd op: Section2 welcomes its new child

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.containedElements().getName(), op0.getFeatureName());
		assertEquals(section2Id, op0.getModelElementId());
		assertEquals(actor2Id, op0.getReferencedModelElements().get(0));

		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertEquals(section2Id, op1.getOldValue());
		assertEquals(section1Id, op1.getNewValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op2.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement oldSection = Create.testElement();
		final TestElement oldSection2 = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();
		final TestElement actor3 = Create.testElement();
		final TestElement actor4 = Create.testElement();

		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), actor3);
		Add.toProject(getLocalProject(), actor4);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), oldSection);
		Add.toProject(getLocalProject(), oldSection2);

		final ModelElementId actor1Id = getProject().getModelElementId(actor1);
		final ModelElementId actor2Id = getProject().getModelElementId(actor2);
		final ModelElementId actor3Id = getProject().getModelElementId(actor3);
		final ModelElementId actor4Id = getProject().getModelElementId(actor4);
		final ModelElementId sectionId = getProject().getModelElementId(section);
		final ModelElementId oldSectionId = getProject().getModelElementId(oldSection);
		final ModelElementId oldSection2Id = getProject().getModelElementId(oldSection2);

		final TestElement[] actors = { actor1, actor2, actor3, actor4 };

		Add.toContainedElements(oldSection, Arrays.asList(actors));
		Add.toContainedElements(oldSection2, actor4);

		assertTrue(oldSection.getContainedElements().contains(actor1));
		assertTrue(oldSection.getContainedElements().contains(actor2));
		assertTrue(oldSection.getContainedElements().contains(actor3));
		assertTrue(oldSection2.getContainedElements().contains(actor4));
		assertTrue(section.getContainedElements().isEmpty());

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		// now expectation is: we get 4 messages preserving the info on former parents for the actors
		// and one additional one, indicating the new parent for all of them
		assertEquals(7, subOperations.size());

		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		final SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final MultiReferenceOperation op4 = checkAndCast(subOperations.get(4), MultiReferenceOperation.class);
		final MultiReferenceOperation op6 = checkAndCast(subOperations.get(6), MultiReferenceOperation.class);

		assertEquals(actor1Id, op1.getModelElementId());
		assertEquals(actor2Id, op2.getModelElementId());
		assertEquals(actor3Id, op3.getModelElementId());
		assertEquals(actor4Id, op5.getModelElementId());

		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op2.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op3.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op5.getFeatureName());

		assertEquals(oldSectionId, op1.getOldValue());
		assertEquals(oldSectionId, op2.getOldValue());
		assertEquals(oldSectionId, op3.getOldValue());
		assertEquals(oldSection2Id, op5.getOldValue());

		assertEquals(sectionId, op1.getNewValue());
		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op3.getNewValue());
		assertEquals(sectionId, op5.getNewValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op0.getFeatureName());
		assertEquals(oldSectionId, op0.getModelElementId());
		assertFalse(op0.isAdd());
		assertEquals(3, op0.getReferencedModelElements().size());
		assertEquals(actor1Id, op0.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op0.getReferencedModelElements().get(1));
		assertEquals(actor3Id, op0.getReferencedModelElements().get(2));
		assertEquals(0, op0.getIndex());

		assertEquals(TestElementFeatures.containedElements().getName(), op4.getFeatureName());
		assertEquals(oldSection2Id, op4.getModelElementId());
		assertEquals(1, op4.getReferencedModelElements().size());
		assertEquals(actor4Id, op4.getReferencedModelElements().get(0));
		assertEquals(0, op4.getIndex());
		assertFalse(op4.isAdd());

		assertEquals(TestElementFeatures.containedElements().getName(), op6.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement oldSection = Create.testElement();
		final TestElement oldSection2 = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();
		final TestElement actor3 = Create.testElement();
		final TestElement actor4 = Create.testElement();
		final TestElement oldTestElement = Create.testElement();

		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), actor3);
		Add.toProject(getLocalProject(), actor4);
		Add.toProject(getLocalProject(), oldTestElement);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), oldSection);
		Add.toProject(getLocalProject(), oldSection2);

		final TestElement[] actors = { actor1, actor2, actor3, actor4 };

		Add.toContainedElements(section, oldTestElement);
		Add.toContainedElements(oldSection, Arrays.asList(actors));
		Add.toContainedElements(oldSection2, actor4);// relocate to other section

		assertTrue(oldSection.getContainedElements().contains(actor1));
		assertTrue(oldSection.getContainedElements().contains(actor2));
		assertTrue(oldSection.getContainedElements().contains(actor3));
		assertTrue(section.getContainedElements().contains(oldTestElement));
		assertTrue(oldSection2.getContainedElements().contains(actor4));

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());

		// now expectation is: we get 4 messages preserving the info on former parents for the actors
		// and one additional one, indicating the new parent for all of them
		// refactoring: addional operations expected

		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(7, subOperations.size());

		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		final SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final MultiReferenceOperation op4 = checkAndCast(subOperations.get(4), MultiReferenceOperation.class);
		final MultiReferenceOperation op6 = checkAndCast(subOperations.get(6), MultiReferenceOperation.class);

		final ModelElementId sectionId = getProject().getModelElementId(section);
		final ModelElementId oldSectionId = getProject().getModelElementId(oldSection);
		final ModelElementId oldSection2Id = getProject().getModelElementId(oldSection2);
		final ModelElementId actor1Id = getProject().getModelElementId(actor1);
		final ModelElementId actor2Id = getProject().getModelElementId(actor2);
		final ModelElementId actor3Id = getProject().getModelElementId(actor3);
		final ModelElementId actor4Id = getProject().getModelElementId(actor4);

		assertEquals(actor1Id, op1.getModelElementId());
		assertEquals(actor2Id, op2.getModelElementId());
		assertEquals(actor3Id, op3.getModelElementId());
		assertEquals(actor4Id, op5.getModelElementId());

		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op2.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op3.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op5.getFeatureName());

		assertEquals(oldSectionId, op1.getOldValue());
		assertEquals(oldSectionId, op2.getOldValue());
		assertEquals(oldSectionId, op3.getOldValue());
		assertEquals(oldSection2Id, op5.getOldValue());

		assertEquals(sectionId, op1.getNewValue());
		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op3.getNewValue());
		assertEquals(sectionId, op5.getNewValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op0.getFeatureName());
		assertEquals(oldSectionId, op0.getModelElementId());
		assertEquals(actor1Id, op0.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op0.getReferencedModelElements().get(1));
		assertEquals(actor3Id, op0.getReferencedModelElements().get(2));
		assertEquals(0, op0.getIndex());
		assertEquals(3, op0.getReferencedModelElements().size());
		assertFalse(op0.isAdd());

		assertEquals(TestElementFeatures.containedElements().getName(), op4.getFeatureName());
		assertEquals(oldSection2Id, op4.getModelElementId());
		assertEquals(1, op4.getReferencedModelElements().size());
		assertEquals(actor4Id, op4.getReferencedModelElements().get(0));
		assertEquals(0, op4.getIndex());
		assertFalse(op4.isAdd());

		assertEquals(TestElementFeatures.containedElements().getName(), op6.getFeatureName());

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

		final TestElement section = Create.testElement();
		final TestElement workPackage = Create.testElement();
		final TestElement bugReport = Create.testElement();

		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), workPackage);
		Add.toProject(getLocalProject(), bugReport);

		final ModelElementId sectionId = getProject().getModelElementId(section);
		final ModelElementId workPackageId = getProject().getModelElementId(workPackage);
		final ModelElementId bugReportId = getProject().getModelElementId(bugReport);

		Update.testElement(TestElementFeatures.container(), bugReport, section);

		assertTrue(section.getContainedElements().contains(bugReport));

		clearOperations();

		Add.toContainedElements2(workPackage, bugReport);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertFalse(section.getContainedElements().contains(bugReport));
		assertTrue(workPackage.getContainedElements2().contains(bugReport));
		assertEquals(1, operations.size());

		final EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(4, subOperations.size());

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final MultiReferenceOperation op3 = checkAndCast(subOperations.get(3), MultiReferenceOperation.class);

		assertEquals(sectionId, op0.getModelElementId());
		assertEquals(TestElementFeatures.containedElements().getName(), op0.getFeatureName());
		assertEquals(op0.getReferencedModelElements().get(0), bugReportId);

		assertEquals(op1.getOldValue(), sectionId);
		assertNull(op1.getNewValue());
		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());

		assertEquals(TestElementFeatures.container2().getName(), op2.getFeatureName());
		assertEquals(workPackageId, op2.getNewValue());
		assertNull(op2.getOldValue());

		assertEquals(TestElementFeatures.containedElements2().getName(), op3.getFeatureName());
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

		final TestElement issue = Create.testElement();
		final TestElement section = Create.testElement();
		final TestElement solution = Create.testElement();

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), solution);

		final ModelElementId sectionId = getProject().getModelElementId(section);
		final ModelElementId issueId = getProject().getModelElementId(issue);
		final ModelElementId solutionId = getProject().getModelElementId(solution);
		Update.testElement(TestElementFeatures.containedElement(), issue, solution);

		clearOperations();

		Add.toContainedElements(section, solution);

		assertTrue(section.getContainedElements().contains(solution));
		assertNull(issue.getContainer());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(4, subOperations.size());

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final MultiReferenceOperation op3 = checkAndCast(subOperations.get(3), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.containedElement().getName(), op0.getFeatureName());
		assertEquals(issueId, op0.getModelElementId());
		assertEquals(solutionId, op0.getOldValue());
		assertNull(op0.getNewValue());

		assertEquals(TestElementFeatures.srefContainer().getName(), op1.getFeatureName());
		assertEquals(issueId, op1.getOldValue());
		assertEquals(solutionId, op1.getModelElementId());
		assertNull(op1.getNewValue());

		assertEquals(solutionId, op2.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), op2.getFeatureName());
		assertEquals(op2.getNewValue(), sectionId);
		assertNull(op2.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op3.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement oldWorkPackage = Create.testElement();
		final TestElement oldWorkPackage2 = Create.testElement();

		final TestElement bugReport1 = Create.testElement();
		final TestElement bugReport2 = Create.testElement();
		final TestElement bugReport3 = Create.testElement();
		final TestElement bugReport4 = Create.testElement();

		Add.toProject(getLocalProject(), bugReport1);
		Add.toProject(getLocalProject(), bugReport2);
		Add.toProject(getLocalProject(), bugReport3);
		Add.toProject(getLocalProject(), bugReport4);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), oldWorkPackage);
		Add.toProject(getLocalProject(), oldWorkPackage2);

		final ModelElementId bugReport1Id = getProject().getModelElementId(bugReport1);
		final ModelElementId bugReport2Id = getProject().getModelElementId(bugReport2);
		final ModelElementId bugReport3Id = getProject().getModelElementId(bugReport3);
		final ModelElementId bugReport4Id = getProject().getModelElementId(bugReport4);
		final ModelElementId sectionId = getProject().getModelElementId(section);
		final ModelElementId oldWorkPackageId = getProject().getModelElementId(oldWorkPackage);
		final ModelElementId oldWorkPackageId2 = getProject().getModelElementId(oldWorkPackage2);

		final TestElement[] actors = { bugReport1, bugReport2, bugReport3, bugReport4 };

		Add.toContainedElements2(oldWorkPackage, Arrays.asList(actors));
		Add.toContainedElements2(oldWorkPackage2, bugReport4); // relocate to other section

		assertTrue(oldWorkPackage.getContainedElements2().contains(bugReport1));
		assertTrue(oldWorkPackage.getContainedElements2().contains(bugReport2));
		assertTrue(oldWorkPackage.getContainedElements2().contains(bugReport3));
		assertTrue(oldWorkPackage2.getContainedElements2().contains(bugReport4));
		assertTrue(section.getContainedElements().isEmpty());

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());

		final EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		// now expectation is: we get 4 messages preserving the info on former parents for the actors
		// and one additional one, indicating the new parent for all of them

		assertEquals(11, subOperations.size());

		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		final SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		final SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		final SingleReferenceOperation op6 = checkAndCast(subOperations.get(6), SingleReferenceOperation.class);
		final SingleReferenceOperation op8 = checkAndCast(subOperations.get(8), SingleReferenceOperation.class);
		final SingleReferenceOperation op9 = checkAndCast(subOperations.get(9), SingleReferenceOperation.class);
		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final MultiReferenceOperation op7 = checkAndCast(subOperations.get(7), MultiReferenceOperation.class);
		final MultiReferenceOperation op10 = checkAndCast(subOperations.get(10), MultiReferenceOperation.class);

		assertEquals(bugReport1Id, op1.getModelElementId());
		assertEquals(bugReport2Id, op3.getModelElementId());
		assertEquals(bugReport3Id, op5.getModelElementId());
		assertEquals(bugReport4Id, op8.getModelElementId());

		assertEquals(TestElementFeatures.container2().getName(), op1.getFeatureName());
		assertEquals(TestElementFeatures.container2().getName(), op3.getFeatureName());
		assertEquals(TestElementFeatures.container2().getName(), op5.getFeatureName());
		assertEquals(TestElementFeatures.container2().getName(), op8.getFeatureName());

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

		assertEquals(TestElementFeatures.container().getName(), op2.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op4.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op6.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op9.getFeatureName());

		assertNull(op2.getOldValue());
		assertNull(op4.getOldValue());
		assertNull(op6.getOldValue());
		assertNull(op9.getOldValue());

		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op4.getNewValue());
		assertEquals(sectionId, op6.getNewValue());
		assertEquals(sectionId, op9.getNewValue());

		assertEquals(TestElementFeatures.containedElements2().getName(), op0.getFeatureName());
		assertEquals(oldWorkPackageId, op0.getModelElementId());
		assertEquals(bugReport1Id, op0.getReferencedModelElements().get(0));
		assertEquals(bugReport2Id, op0.getReferencedModelElements().get(1));
		assertEquals(bugReport3Id, op0.getReferencedModelElements().get(2));
		assertEquals(3, op0.getReferencedModelElements().size());
		assertFalse(op0.isAdd());

		assertEquals(TestElementFeatures.containedElements2().getName(), op7.getFeatureName());
		assertEquals(oldWorkPackageId2, op7.getModelElementId());
		assertEquals(bugReport4Id, op7.getReferencedModelElements().get(0));
		assertFalse(op7.isAdd());
		assertEquals(1, op7.getReferencedModelElements().size());

		assertEquals(TestElementFeatures.containedElements().getName(), op10.getFeatureName());
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

		final TestElement section = Create.testElement();
		section.getContainedElements().add(Create.testElement());

		final TestElement oldWorkPackage = Create.testElement();
		final TestElement oldWorkPackage2 = Create.testElement();

		final TestElement bugReport1 = Create.testElement();
		final TestElement bugReport2 = Create.testElement();
		final TestElement bugReport3 = Create.testElement();
		final TestElement bugReport4 = Create.testElement();

		Add.toProject(getLocalProject(), bugReport1);
		Add.toProject(getLocalProject(), bugReport2);
		Add.toProject(getLocalProject(), bugReport3);
		Add.toProject(getLocalProject(), bugReport4);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), oldWorkPackage);
		Add.toProject(getLocalProject(), oldWorkPackage2);

		final ModelElementId sectionId = getProject().getModelElementId(section);
		final ModelElementId oldWorkPackageId = getProject().getModelElementId(oldWorkPackage);
		final ModelElementId oldWorkPackage2Id = getProject().getModelElementId(oldWorkPackage2);
		final ModelElementId bugReport1Id = getProject().getModelElementId(bugReport1);
		final ModelElementId bugReport2Id = getProject().getModelElementId(bugReport2);
		final ModelElementId bugReport3Id = getProject().getModelElementId(bugReport3);
		final ModelElementId bugReport4Id = getProject().getModelElementId(bugReport4);

		final TestElement[] bugreports = { bugReport1, bugReport2, bugReport3, bugReport4 };

		Add.toContainedElements2(oldWorkPackage, Arrays.asList(bugreports));
		Add.toContainedElements2(oldWorkPackage2, bugReport4);

		assertTrue(oldWorkPackage.getContainedElements2().contains(bugReport1));
		assertTrue(oldWorkPackage.getContainedElements2().contains(bugReport2));
		assertTrue(oldWorkPackage.getContainedElements2().contains(bugReport3));
		assertTrue(oldWorkPackage2.getContainedElements2().contains(bugReport4));
		assertFalse(section.getContainedElements().isEmpty()); // one item is there initially

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(bugreports));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());

		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		// now expectation is: we get 4 messages preserving the info on former parents for the actors
		// and one additional one, indicating the new parent for all of them

		assertEquals(11, subOperations.size());

		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		final SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		final SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		final SingleReferenceOperation op6 = checkAndCast(subOperations.get(6), SingleReferenceOperation.class);
		final SingleReferenceOperation op8 = checkAndCast(subOperations.get(8), SingleReferenceOperation.class);
		final SingleReferenceOperation op9 = checkAndCast(subOperations.get(9), SingleReferenceOperation.class);
		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final MultiReferenceOperation op7 = checkAndCast(subOperations.get(7), MultiReferenceOperation.class);
		final MultiReferenceOperation op10 = checkAndCast(subOperations.get(10), MultiReferenceOperation.class);

		assertEquals(bugReport1Id, op1.getModelElementId());
		assertEquals(bugReport2Id, op3.getModelElementId());
		assertEquals(bugReport3Id, op5.getModelElementId());
		assertEquals(bugReport4Id, op8.getModelElementId());

		assertEquals(TestElementFeatures.container2().getName(), op1.getFeatureName());
		assertEquals(TestElementFeatures.container2().getName(), op3.getFeatureName());
		assertEquals(TestElementFeatures.container2().getName(), op5.getFeatureName());
		assertEquals(TestElementFeatures.container2().getName(), op8.getFeatureName());

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

		assertEquals(TestElementFeatures.container().getName(), op2.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op4.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op6.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op9.getFeatureName());

		assertNull(op2.getOldValue());
		assertNull(op4.getOldValue());
		assertNull(op6.getOldValue());
		assertNull(op9.getOldValue());

		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op4.getNewValue());
		assertEquals(sectionId, op6.getNewValue());
		assertEquals(sectionId, op9.getNewValue());

		assertEquals(TestElementFeatures.containedElements2().getName(), op0.getFeatureName());
		assertEquals(oldWorkPackageId, op0.getModelElementId());
		assertEquals(bugReport1Id, op0.getReferencedModelElements().get(0));
		assertEquals(bugReport2Id, op0.getReferencedModelElements().get(1));
		assertEquals(bugReport3Id, op0.getReferencedModelElements().get(2));
		assertEquals(3, op0.getReferencedModelElements().size());
		assertFalse(op0.isAdd());

		assertEquals(TestElementFeatures.containedElements2().getName(), op7.getFeatureName());
		assertEquals(oldWorkPackage2Id, op7.getModelElementId());
		assertEquals(bugReport4Id, op7.getReferencedModelElements().get(0));
		assertEquals(1, op7.getReferencedModelElements().size());
		assertFalse(op7.isAdd());

		assertEquals(TestElementFeatures.containedElements().getName(), op10.getFeatureName());
		assertEquals(sectionId, op10.getModelElementId());
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

		final TestElement section = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement solution2 = Create.testElement();
		final TestElement issue1 = Create.testElement();
		final TestElement issue2 = Create.testElement();

		Add.toProject(getLocalProject(), issue1);
		Add.toProject(getLocalProject(), issue2);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), solution1);
		Add.toProject(getLocalProject(), solution2);

		final ModelElementId issue1Id = getProject().getModelElementId(issue1);
		final ModelElementId issue2Id = getProject().getModelElementId(issue2);
		final ModelElementId sectionId = getProject().getModelElementId(section);
		final ModelElementId solution1Id = getProject().getModelElementId(solution1);
		final ModelElementId solution2Id = getProject().getModelElementId(solution2);

		Update.testElement(TestElementFeatures.containedElement(), issue1, solution1);
		Update.testElement(TestElementFeatures.containedElement(), issue2, solution2);

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(new TestElement[] { solution1, solution2 }));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		// now expectation is: we get 2 messages preserving the info on former parents for the solutions
		// and one additional one, indicating the new parent for both of them

		assertEquals(7, subOperations.size());

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		final SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		final SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		final MultiReferenceOperation op6 = checkAndCast(subOperations.get(6), MultiReferenceOperation.class);

		assertEquals(solution1Id, op1.getModelElementId());
		assertEquals(solution2Id, op4.getModelElementId());
		assertEquals(TestElementFeatures.srefContainer().getName(), op1.getFeatureName());
		assertEquals(TestElementFeatures.srefContainer().getName(), op4.getFeatureName());
		assertEquals(issue1Id, op1.getOldValue());
		assertEquals(issue2Id, op4.getOldValue());
		assertNull(op1.getNewValue());
		assertNull(op4.getNewValue());

		assertEquals(issue1Id, op0.getModelElementId());
		assertEquals(issue2Id, op3.getModelElementId());
		assertEquals(TestElementFeatures.containedElement().getName(), op0.getFeatureName());
		assertEquals(TestElementFeatures.containedElement().getName(), op3.getFeatureName());
		assertEquals(solution1Id, op0.getOldValue());
		assertEquals(solution2Id, op3.getOldValue());
		assertNull(op0.getNewValue());
		assertNull(op3.getNewValue());

		assertEquals(solution1Id, op2.getModelElementId());
		assertEquals(solution2Id, op5.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), op2.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op5.getFeatureName());
		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op5.getNewValue());
		assertNull(op2.getOldValue());
		assertNull(op5.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op6.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement issue1 = Create.testElement();
		final TestElement issue2 = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement solution2 = Create.testElement();
		section.getContainedElements().add(Create.testElement()); // prefill section

		Add.toProject(getLocalProject(), issue1);
		Add.toProject(getLocalProject(), issue2);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), solution1);
		Add.toProject(getLocalProject(), solution2);

		Update.testElement(TestElementFeatures.containedElement(), issue1, solution1);
		Update.testElement(TestElementFeatures.containedElement(), issue2, solution2);

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(new TestElement[] { solution1, solution2 }));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());

		// now expectation is: we get 2 messages preserving the info on former parents for the solutions
		// and one additional one, indicating the new parent for both of them

		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(7, subOperations.size());

		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);
		final ModelElementId solution1Id = ModelUtil.getProject(solution1).getModelElementId(solution1);
		final ModelElementId solution2Id = ModelUtil.getProject(solution2).getModelElementId(solution2);
		final ModelElementId issue1Id = ModelUtil.getProject(issue1).getModelElementId(issue1);
		final ModelElementId issue2Id = ModelUtil.getProject(issue2).getModelElementId(issue2);

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		final SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		final SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		final MultiReferenceOperation op6 = checkAndCast(subOperations.get(6), MultiReferenceOperation.class);

		assertEquals(solution1Id, op1.getModelElementId());
		assertEquals(solution2Id, op4.getModelElementId());
		assertEquals(TestElementFeatures.srefContainer().getName(), op1.getFeatureName());
		assertEquals(TestElementFeatures.srefContainer().getName(), op4.getFeatureName());
		assertEquals(issue1Id, op1.getOldValue());
		assertEquals(issue2Id, op4.getOldValue());
		assertNull(op1.getNewValue());
		assertNull(op4.getNewValue());

		assertEquals(issue1Id, op0.getModelElementId());
		assertEquals(issue2Id, op3.getModelElementId());
		assertEquals(TestElementFeatures.containedElement().getName(), op0.getFeatureName());
		assertEquals(TestElementFeatures.containedElement().getName(), op3.getFeatureName());
		assertEquals(solution1Id, op0.getOldValue());
		assertEquals(solution2Id, op3.getOldValue());
		assertNull(op0.getNewValue());
		assertNull(op3.getNewValue());

		assertEquals(solution1Id, op2.getModelElementId());
		assertEquals(solution2Id, op5.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), op2.getFeatureName());
		assertEquals(TestElementFeatures.container().getName(), op5.getFeatureName());
		assertEquals(sectionId, op2.getNewValue());
		assertEquals(sectionId, op5.getNewValue());
		assertNull(op2.getOldValue());
		assertNull(op5.getOldValue());

		assertEquals(sectionId, op6.getModelElementId());
		assertEquals(TestElementFeatures.containedElements().getName(), op6.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement oldSection1 = Create.testElement();
		final TestElement oldSection2 = Create.testElement();
		final TestElement issue1 = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement issue2 = Create.testElement();
		final TestElement solution2 = Create.testElement();
		final TestElement newTestElement = Create.testElement();
		final TestElement sectionTestElement1 = Create.testElement();
		final TestElement sectionTestElement2 = Create.testElement();
		final TestElement sectionTestElement3 = Create.testElement();
		final TestElement workPackage = Create.testElement();
		final TestElement bugReport = Create.testElement();

		Add.toProject(getLocalProject(), issue1);
		Add.toProject(getLocalProject(), issue2);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), oldSection1);
		Add.toProject(getLocalProject(), oldSection2);
		Add.toProject(getLocalProject(), newTestElement);
		Add.toProject(getLocalProject(), sectionTestElement1);
		Add.toProject(getLocalProject(), sectionTestElement2);
		Add.toProject(getLocalProject(), sectionTestElement3);
		Add.toProject(getLocalProject(), solution1);
		Add.toProject(getLocalProject(), solution2);
		Add.toProject(getLocalProject(), workPackage);
		Add.toProject(getLocalProject(), bugReport);

		final ModelElementId sectionId = getProject().getModelElementId(section);
		final ModelElementId solution1Id = getProject().getModelElementId(solution1);
		final ModelElementId solution2Id = getProject().getModelElementId(solution2);
		final ModelElementId issue1Id = getProject().getModelElementId(issue1);
		final ModelElementId issue2Id = getProject().getModelElementId(issue2);
		final ModelElementId newTestElementId = getProject().getModelElementId(newTestElement);
		final ModelElementId oldSection1Id = getProject().getModelElementId(oldSection1);
		final ModelElementId oldSection2Id = getProject().getModelElementId(oldSection2);
		final ModelElementId sectionTestElement1Id = getProject().getModelElementId(sectionTestElement1);
		final ModelElementId sectionTestElement2Id = getProject().getModelElementId(sectionTestElement2);
		final ModelElementId sectionTestElement3Id = getProject().getModelElementId(sectionTestElement3);
		final ModelElementId workPackageId = getProject().getModelElementId(workPackage);
		final ModelElementId bugReportId = getProject().getModelElementId(bugReport);

		final TestElement[] addedElements = { solution1, solution2, newTestElement, sectionTestElement1,
			sectionTestElement2,
			sectionTestElement3, bugReport };

		Update.testElement(TestElementFeatures.containedElement(), issue1, solution1);
		Update.testElement(TestElementFeatures.containedElement(), issue2, solution2);

		Add.toContainedElements2(workPackage, bugReport);
		Add.toContainedElements(oldSection1, sectionTestElement1);
		Add.toContainedElements(oldSection1, sectionTestElement2);
		Add.toContainedElements(oldSection2, sectionTestElement3);

		assertTrue(oldSection1.getContainedElements().contains(sectionTestElement1));
		assertTrue(oldSection1.getContainedElements().contains(sectionTestElement2));
		assertTrue(oldSection2.getContainedElements().contains(sectionTestElement3));
		assertTrue(workPackage.getContainedElements2().contains(bugReport));

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(addedElements));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		// now expectation is: we get 6 messages preserving the info on former parents for the solutions
		// and one additional one, indicating the new parent for both of them

		assertEquals(16, subOperations.size());

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		final SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		final SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		final SingleReferenceOperation op6 = checkAndCast(subOperations.get(6), SingleReferenceOperation.class);
		final MultiReferenceOperation op7 = checkAndCast(subOperations.get(7), MultiReferenceOperation.class);
		final SingleReferenceOperation op8 = checkAndCast(subOperations.get(8), SingleReferenceOperation.class);
		final SingleReferenceOperation op9 = checkAndCast(subOperations.get(9), SingleReferenceOperation.class);
		final MultiReferenceOperation op10 = checkAndCast(subOperations.get(10), MultiReferenceOperation.class);
		final SingleReferenceOperation op11 = checkAndCast(subOperations.get(11), SingleReferenceOperation.class);
		final MultiReferenceOperation op12 = checkAndCast(subOperations.get(12), MultiReferenceOperation.class);
		final SingleReferenceOperation op13 = checkAndCast(subOperations.get(13), SingleReferenceOperation.class);
		final SingleReferenceOperation op14 = checkAndCast(subOperations.get(14), SingleReferenceOperation.class);
		final MultiReferenceOperation op15 = checkAndCast(subOperations.get(15), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.containedElement().getName(), op0.getFeatureName());
		assertEquals(issue1Id, op0.getModelElementId());
		assertEquals(solution1Id, op0.getOldValue());
		assertNull(op0.getNewValue());

		assertEquals(TestElementFeatures.srefContainer().getName(), op1.getFeatureName());
		assertEquals(solution1Id, op1.getModelElementId());
		assertEquals(issue1Id, op1.getOldValue());
		assertNull(op1.getNewValue());

		assertEquals(TestElementFeatures.container().getName(), op2.getFeatureName());
		assertEquals(solution1Id, op2.getModelElementId());
		assertEquals(sectionId, op2.getNewValue());
		assertNull(op2.getOldValue());

		assertEquals(TestElementFeatures.containedElement().getName(), op3.getFeatureName());
		assertEquals(issue2Id, op3.getModelElementId());
		assertNull(op3.getNewValue());
		assertEquals(solution2Id, op3.getOldValue());

		assertEquals(TestElementFeatures.srefContainer().getName(), op4.getFeatureName());
		assertEquals(solution2Id, op4.getModelElementId());
		assertEquals(issue2Id, op4.getOldValue());
		assertNull(op4.getNewValue());

		assertEquals(solution2Id, op5.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), op5.getFeatureName());
		assertEquals(sectionId, op5.getNewValue());
		assertNull(op5.getOldValue());

		assertEquals(newTestElementId, op6.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), op6.getFeatureName());
		assertEquals(sectionId, op6.getNewValue());
		assertNull(op6.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op7.getFeatureName());
		assertEquals(oldSection1Id, op7.getModelElementId());
		assertEquals(sectionTestElement1Id, op7.getReferencedModelElements().get(0));
		assertEquals(sectionTestElement2Id, op7.getReferencedModelElements().get(1));
		assertEquals(2, op7.getReferencedModelElements().size());
		assertFalse(op7.isAdd());

		assertEquals(TestElementFeatures.container().getName(), op8.getFeatureName());
		assertEquals(sectionTestElement1Id, op8.getModelElementId());
		assertEquals(sectionId, op8.getNewValue());
		assertEquals(oldSection1Id, op8.getOldValue());

		assertEquals(TestElementFeatures.container().getName(), op9.getFeatureName());
		assertEquals(sectionTestElement2Id, op9.getModelElementId());
		assertEquals(sectionId, op9.getNewValue());
		assertEquals(oldSection1Id, op9.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op10.getFeatureName());
		assertEquals(oldSection2Id, op10.getModelElementId());
		assertEquals(sectionTestElement3Id, op10.getReferencedModelElements().get(0));
		assertEquals(1, op10.getReferencedModelElements().size());
		assertFalse(op10.isAdd());

		assertEquals(TestElementFeatures.container().getName(), op11.getFeatureName());
		assertEquals(sectionTestElement3Id, op11.getModelElementId());
		assertEquals(sectionId, op11.getNewValue());
		assertEquals(oldSection2Id, op11.getOldValue());

		assertEquals(TestElementFeatures.containedElements2().getName(), op12.getFeatureName());
		assertEquals(workPackageId, op12.getModelElementId());
		assertEquals(bugReportId, op12.getReferencedModelElements().get(0));
		assertEquals(1, op12.getReferencedModelElements().size());
		assertFalse(op12.isAdd());

		assertEquals(TestElementFeatures.container2().getName(), op13.getFeatureName());
		assertEquals(bugReportId, op13.getModelElementId());
		assertEquals(workPackageId, op13.getOldValue());
		assertNull(op13.getNewValue());

		assertEquals(TestElementFeatures.container().getName(), op14.getFeatureName());
		assertEquals(bugReportId, op14.getModelElementId());
		assertEquals(sectionId, op14.getNewValue());
		assertNull(op14.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op15.getFeatureName());
		assertEquals(7, op15.getReferencedModelElements().size());
		assertEquals(0, op15.getIndex());
		assertEquals(sectionId, op15.getModelElementId());
		assertEquals(solution1Id, op15.getReferencedModelElements().get(0));
		assertEquals(solution2Id, op15.getReferencedModelElements().get(1));
		assertEquals(newTestElementId, op15.getReferencedModelElements().get(2));
		assertEquals(sectionTestElement1Id, op15.getReferencedModelElements().get(3));
		assertEquals(sectionTestElement2Id, op15.getReferencedModelElements().get(4));
		assertEquals(sectionTestElement3Id, op15.getReferencedModelElements().get(5));
		assertEquals(bugReportId, op15.getReferencedModelElements().get(6));
		assertTrue(op15.isAdd());

	}

	/**
	 * Add several already contained children to an empty containment feature.
	 */
	@Test
	public void containmentAddMixedChildrenToNonEmpty() {

		final TestElement section = Create.testElement();
		section.getContainedElements().add(Create.testElement()); // prefill section
		final TestElement oldSection1 = Create.testElement();
		final TestElement oldSection2 = Create.testElement();
		final TestElement issue1 = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement issue2 = Create.testElement();
		final TestElement solution2 = Create.testElement();
		final TestElement newTestElement = Create.testElement();
		final TestElement sectionTestElement1 = Create.testElement();
		final TestElement sectionTestElement2 = Create.testElement();
		final TestElement sectionTestElement3 = Create.testElement();
		final TestElement workPackage = Create.testElement();
		final TestElement bugReport = Create.testElement();

		Add.toProject(getLocalProject(), issue1);
		Add.toProject(getLocalProject(), issue2);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), oldSection1);
		Add.toProject(getLocalProject(), oldSection2);
		Add.toProject(getLocalProject(), newTestElement);
		Add.toProject(getLocalProject(), sectionTestElement1);
		Add.toProject(getLocalProject(), sectionTestElement2);
		Add.toProject(getLocalProject(), sectionTestElement3);
		Add.toProject(getLocalProject(), solution1);
		Add.toProject(getLocalProject(), solution2);
		Add.toProject(getLocalProject(), workPackage);
		Add.toProject(getLocalProject(), bugReport);

		final ModelElementId issue1Id = getProject().getModelElementId(issue1);
		final ModelElementId issue2Id = getProject().getModelElementId(issue2);
		final ModelElementId oldSection1Id = getProject().getModelElementId(oldSection1);
		final ModelElementId oldSection2Id = getProject().getModelElementId(oldSection2);
		final ModelElementId sectionTestElement1Id = getProject().getModelElementId(sectionTestElement1);
		final ModelElementId sectionTestElement2Id = getProject().getModelElementId(sectionTestElement2);
		final ModelElementId sectionTestElement3Id = getProject().getModelElementId(sectionTestElement3);
		final ModelElementId sectionId = getProject().getModelElementId(section);
		final ModelElementId newTestElementId = getProject().getModelElementId(newTestElement);
		final ModelElementId solution1Id = getProject().getModelElementId(solution1);
		final ModelElementId solution2Id = getProject().getModelElementId(solution2);
		final ModelElementId bugReportId = getProject().getModelElementId(bugReport);
		final ModelElementId workPackageId = getProject().getModelElementId(workPackage);

		final TestElement[] addedElements = { solution1, solution2, newTestElement, sectionTestElement1,
			sectionTestElement2,
			sectionTestElement3, bugReport };

		Update.testElement(TestElementFeatures.containedElement(), issue1, solution1);
		Update.testElement(TestElementFeatures.containedElement(), issue2, solution2);

		Add.toContainedElements2(workPackage, bugReport);
		Add.toContainedElements(oldSection1, sectionTestElement1);
		Add.toContainedElements(oldSection1, sectionTestElement2);
		Add.toContainedElements(oldSection2, sectionTestElement3);

		assertTrue(oldSection1.getContainedElements().contains(sectionTestElement1));
		assertTrue(oldSection1.getContainedElements().contains(sectionTestElement2));
		assertTrue(oldSection2.getContainedElements().contains(sectionTestElement3));
		assertTrue(workPackage.getContainedElements2().contains(bugReport));

		clearOperations();

		Add.toContainedElements(section, Arrays.asList(addedElements));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		// now expectation is: we get 6 messages preserving the info on former parents for the solutions
		// and one additional one, indicating the new parent for both of them

		assertEquals(16, subOperations.size());

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation op2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation op3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		final SingleReferenceOperation op4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);
		final SingleReferenceOperation op5 = checkAndCast(subOperations.get(5), SingleReferenceOperation.class);
		final SingleReferenceOperation op6 = checkAndCast(subOperations.get(6), SingleReferenceOperation.class);
		final MultiReferenceOperation op7 = checkAndCast(subOperations.get(7), MultiReferenceOperation.class);
		final SingleReferenceOperation op8 = checkAndCast(subOperations.get(8), SingleReferenceOperation.class);
		final SingleReferenceOperation op9 = checkAndCast(subOperations.get(9), SingleReferenceOperation.class);
		final MultiReferenceOperation op10 = checkAndCast(subOperations.get(10), MultiReferenceOperation.class);
		final SingleReferenceOperation op11 = checkAndCast(subOperations.get(11), SingleReferenceOperation.class);
		final MultiReferenceOperation op12 = checkAndCast(subOperations.get(12), MultiReferenceOperation.class);
		final SingleReferenceOperation op13 = checkAndCast(subOperations.get(13), SingleReferenceOperation.class);
		final SingleReferenceOperation op14 = checkAndCast(subOperations.get(14), SingleReferenceOperation.class);
		final MultiReferenceOperation op15 = checkAndCast(subOperations.get(15), MultiReferenceOperation.class);

		assertEquals(issue1Id, op0.getModelElementId());
		assertEquals(TestElementFeatures.containedElement().getName(), op0.getFeatureName());
		assertEquals(solution1Id, op0.getOldValue());
		assertNull(op0.getNewValue());

		assertEquals(solution1Id, op1.getModelElementId());
		assertEquals(TestElementFeatures.srefContainer().getName(), op1.getFeatureName());
		assertEquals(issue1Id, op1.getOldValue());
		assertNull(op1.getNewValue());

		assertEquals(op2.getModelElementId(), solution1Id);
		assertEquals(op2.getFeatureName(), TestElementFeatures.container().getName());
		assertEquals(op2.getNewValue(), sectionId);
		assertEquals(op2.getOldValue(), null);

		assertEquals(op3.getModelElementId(), issue2Id);
		assertEquals(TestElementFeatures.containedElement().getName(), op3.getFeatureName());
		assertEquals(op3.getNewValue(), null);
		assertEquals(op3.getOldValue(), solution2Id);

		assertEquals(op4.getModelElementId(), solution2Id);
		assertEquals(op4.getFeatureName(), TestElementFeatures.srefContainer().getName());
		assertEquals(op4.getNewValue(), null);
		assertEquals(op4.getOldValue(), issue2Id);

		assertEquals(op5.getModelElementId(), solution2Id);
		assertEquals(op5.getFeatureName(), TestElementFeatures.container().getName());
		assertEquals(op5.getNewValue(), sectionId);
		assertEquals(op5.getOldValue(), null);

		assertEquals(op6.getModelElementId(), newTestElementId);
		assertEquals(op6.getFeatureName(), TestElementFeatures.container().getName());
		assertEquals(op6.getNewValue(), sectionId);
		assertEquals(op6.getOldValue(), null);

		assertEquals(op7.getModelElementId(), oldSection1Id);
		assertEquals(op7.getFeatureName(), TestElementFeatures.containedElements().getName());
		assertEquals(op7.isAdd(), false);
		assertEquals(op7.getReferencedModelElements().size(), 2);
		assertEquals(op7.getReferencedModelElements().get(0), sectionTestElement1Id);
		assertEquals(op7.getReferencedModelElements().get(1), sectionTestElement2Id);

		assertEquals(TestElementFeatures.container().getName(), op8.getFeatureName());
		assertEquals(sectionTestElement1Id, op8.getModelElementId());
		assertEquals(sectionId, op8.getNewValue());
		assertEquals(oldSection1Id, op8.getOldValue());

		assertEquals(TestElementFeatures.container().getName(), op9.getFeatureName());
		assertEquals(sectionTestElement2Id, op9.getModelElementId());
		assertEquals(sectionId, op9.getNewValue());
		assertEquals(oldSection1Id, op9.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op10.getFeatureName());
		assertEquals(oldSection2Id, op10.getModelElementId());
		assertEquals(sectionTestElement3Id, op10.getReferencedModelElements().get(0));
		assertEquals(1, op10.getReferencedModelElements().size());
		assertFalse(op10.isAdd());

		assertEquals(TestElementFeatures.container().getName(), op11.getFeatureName());
		assertEquals(sectionTestElement3Id, op11.getModelElementId());
		assertEquals(sectionId, op11.getNewValue());
		assertEquals(oldSection2Id, op11.getOldValue());

		assertEquals(TestElementFeatures.containedElements2().getName(), op12.getFeatureName());
		assertEquals(workPackageId, op12.getModelElementId());
		assertEquals(bugReportId, op12.getReferencedModelElements().get(0));
		assertEquals(1, op12.getReferencedModelElements().size());
		assertFalse(op12.isAdd());

		assertEquals(TestElementFeatures.container2().getName(), op13.getFeatureName());
		assertEquals(bugReportId, op13.getModelElementId());
		assertEquals(workPackageId, op13.getOldValue());
		assertNull(op13.getNewValue());

		assertEquals(TestElementFeatures.container().getName(), op14.getFeatureName());
		assertEquals(bugReportId, op14.getModelElementId());
		assertEquals(sectionId, op14.getNewValue());
		assertNull(op14.getOldValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op15.getFeatureName());
		assertEquals(sectionId, op15.getModelElementId());
		assertTrue(op15.isAdd());
		assertEquals(1, op15.getIndex());
		assertEquals(7, op15.getReferencedModelElements().size());
		assertEquals(solution1Id, op15.getReferencedModelElements().get(0));
		assertEquals(solution2Id, op15.getReferencedModelElements().get(1));
		assertEquals(newTestElementId, op15.getReferencedModelElements().get(2));
		assertEquals(sectionTestElement1Id, op15.getReferencedModelElements().get(3));
		assertEquals(sectionTestElement2Id, op15.getReferencedModelElements().get(4));
		assertEquals(sectionTestElement3Id, op15.getReferencedModelElements().get(5));
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

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), section);
		Add.toContainedElements(section, actor);

		clearOperations();
		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);

		Delete.fromContainedElements(section, actor);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final List<ReferenceOperation> subOperations = checkAndCast(operations.get(0), CreateDeleteOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		final ModelElementId sectionId = getProject().getModelElementId(section);

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals(actorId, op0.getModelElementId());
		assertEquals(sectionId, op0.getOldValue());
		assertEquals(TestElementFeatures.container().getName(), op0.getFeatureName());
		assertNull(op0.getNewValue());

		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.containedElements().getName(), op1.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();
		final TestElement[] actors = { actor1, actor2 };

		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), section);

		Add.toContainedElements(section, Arrays.asList(actors));

		clearOperations();

		final ModelElementId actor1Id = getProject().getModelElementId(actor1);
		final ModelElementId actor2Id = getProject().getModelElementId(actor2);

		Delete.fromContainedElements(section, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(3, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		checkAndCast(operations.get(1), CreateDeleteOperation.class);
		checkAndCast(operations.get(2), CreateDeleteOperation.class);
		assertEquals(3, subOperations.size());

		final ModelElementId sectionId = getProject().getModelElementId(section);

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(sectionId, op0.getOldValue());
		assertEquals(TestElementFeatures.container().getName(), op0.getFeatureName());
		assertNull(op0.getNewValue());

		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(sectionId, op1.getOldValue());
		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertNull(op1.getNewValue());

		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals(TestElementFeatures.containedElements().getName(), op2.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement oldTestElement = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), oldTestElement);
		Add.toProject(getLocalProject(), section);

		Add.toContainedElements(section, oldTestElement);
		Add.toContainedElements(section, actor);

		clearOperations();
		final ModelElementId actorId = getProject().getModelElementId(actor);

		Delete.fromContainedElements(section, actor);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		final List<ReferenceOperation> subOperations = checkAndCast(operations.get(0), CreateDeleteOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		final ModelElementId sectionId = getProject().getModelElementId(section);

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals(actorId, op0.getModelElementId());
		assertEquals(sectionId, op0.getOldValue());
		assertEquals(TestElementFeatures.container().getName(), op0.getFeatureName());
		assertNull(op0.getNewValue());

		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals(sectionId, op1.getModelElementId());
		assertEquals(TestElementFeatures.containedElements().getName(), op1.getFeatureName());
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

		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), useCase);

		clearOperations();

		Add.toNonContainedNToM(useCase, actor);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		final List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(2, subOperations.size());

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op0 = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op0.isAdd());
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op0.getFeatureName());

		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(actorId, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op1 = (MultiReferenceOperation) subOperations.get(1);
		assertTrue(op1.isAdd());
		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op1.getFeatureName());
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

		final TestElement useCase = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();

		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), useCase);

		final TestElement[] actors = { actor1, actor2 };

		clearOperations();

		Add.toNonContainedNToM(useCase, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		final List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(3, subOperations.size());

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op0 = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op0.isAdd());
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op0.getFeatureName());

		final ModelElementId actor1Id = ModelUtil.getProject(actor1).getModelElementId(actor1);
		final ModelElementId actor2Id = ModelUtil.getProject(actor2).getModelElementId(actor2);
		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op1 = (MultiReferenceOperation) subOperations.get(1);
		assertTrue(op1.isAdd());
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(useCaseId, op1.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(2) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op2 = (MultiReferenceOperation) subOperations.get(2);
		assertTrue(op2.isAdd());
		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op2.getFeatureName());
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

		final TestElement useCase = Create.testElement();
		final TestElement oldTestElement = Create.testElement();
		final TestElement actor = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), oldTestElement);
		Add.toProject(getLocalProject(), useCase);

		Add.toNonContainedNToM(useCase, oldTestElement);

		clearOperations();

		Add.toNonContainedNToM(useCase, actor);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		final List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(2, subOperations.size());

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op0 = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op0.isAdd());
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op0.getFeatureName());

		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(actorId, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op1 = (MultiReferenceOperation) subOperations.get(1);
		assertTrue(op1.isAdd());
		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op1.getFeatureName());
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

		final TestElement useCase = Create.testElement();
		final TestElement oldTestElement = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();

		Add.toProject(getLocalProject(), oldTestElement);
		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), useCase);

		final TestElement[] actors = { actor1, actor2 };
		Add.toNonContainedNToM(useCase, oldTestElement);
		clearOperations();

		Add.toNonContainedNToM(useCase, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		final List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(3, subOperations.size());

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op0 = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op0.isAdd());
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op0.getFeatureName());

		final ModelElementId actor1Id = ModelUtil.getProject(actor1).getModelElementId(actor1);
		final ModelElementId actor2Id = ModelUtil.getProject(actor2).getModelElementId(actor2);
		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op1 = (MultiReferenceOperation) subOperations.get(1);
		assertTrue(op1.isAdd());
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(useCaseId, op1.getReferencedModelElements().get(0));

		assertTrue(subOperations.get(2) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op2 = (MultiReferenceOperation) subOperations.get(2);
		assertTrue(op2.isAdd());
		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op2.getFeatureName());
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

		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), useCase);

		Add.toNonContainedNToM(useCase, actor);

		clearOperations();

		Delete.fromNonContainedNToM(useCase, actor);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		// expecting a composite operation here
		assertEquals(1, operations.size());
		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail("composite operation expected"); //$NON-NLS-1$
		}

		assertEquals(2, operations.size());

		assertTrue(operations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op0 = (MultiReferenceOperation) operations.get(0);
		assertFalse(op0.isAdd());
		assertEquals(1, op0.getReferencedModelElements().size());

		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op0.getFeatureName());
		assertEquals(op0.getModelElementId(), actorId);
		assertEquals(op0.getIndex(), 0);

		assertTrue(operations.get(1) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op1 = (MultiReferenceOperation) operations.get(1);
		assertFalse(op1.isAdd());
		assertEquals(1, op1.getReferencedModelElements().size());
		assertEquals(actorId, op1.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op1.getFeatureName());
		assertEquals(op1.getModelElementId(), useCaseId);
		assertEquals(op1.getIndex(), 0);

	}

	/**
	 * Remove non-last child from non-containment feature.
	 */
	@Test
	public void nonContainmentRemoveChildPart() {

		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement oldTestElement = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), oldTestElement);
		Add.toProject(getLocalProject(), useCase);

		final ModelElementId actorId = getProject().getModelElementId(actor);
		final ModelElementId useCaseId = getProject().getModelElementId(useCase);

		Add.toNonContainedNToM(useCase, oldTestElement);
		Add.toNonContainedNToM(useCase, actor);

		clearOperations();

		Delete.fromNonContainedNToM(useCase, actor);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);

		assertEquals(1, op0.getReferencedModelElements().size());
		assertFalse(op0.isAdd());

		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op0.getFeatureName());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));
		assertEquals(actorId, op0.getModelElementId());
		assertEquals(0, op0.getIndex());

		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op1.getFeatureName());
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

		final TestElement useCase = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();

		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), useCase);

		final ModelElementId actor1Id = getProject().getModelElementId(actor1);
		final ModelElementId actor2Id = getProject().getModelElementId(actor2);
		final ModelElementId useCaseId = getProject().getModelElementId(useCase);

		final TestElement[] actors = { actor1, actor2 };

		Add.toNonContainedNToM(useCase, Arrays.asList(actors));

		clearOperations();

		Delete.fromNonContainedNToM(useCase, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(3, subOperations.size());

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);
		final MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));
		assertFalse(op0.isAdd());

		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(useCaseId, op1.getReferencedModelElements().get(0));
		assertFalse(op1.isAdd());

		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op2.getFeatureName());
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

		final TestElement useCase = Create.testElement();
		final TestElement oldTestElement = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();

		Add.toProject(getLocalProject(), oldTestElement);
		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), useCase);

		final ModelElementId actor1Id = getProject().getModelElementId(actor1);
		final ModelElementId actor2Id = getProject().getModelElementId(actor2);
		final ModelElementId useCaseId = getProject().getModelElementId(useCase);
		final TestElement[] actors = { actor1, actor2 };

		Add.toNonContainedNToM(useCase, oldTestElement);
		Add.toNonContainedNToM(useCase, Arrays.asList(actors));

		clearOperations();

		Delete.fromNonContainedNToM(useCase, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		assertEquals(3, subOperations.size());

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final MultiReferenceOperation op1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);
		final MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(useCaseId, op0.getReferencedModelElements().get(0));
		assertFalse(op0.isAdd());

		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(useCaseId, op1.getReferencedModelElements().get(0));
		assertFalse(op1.isAdd());

		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op2.getFeatureName());
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

		final TestElement section = Create.testElement();
		final TestElement oldTestElement = Create.testElement();
		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();
		final TestElement[] actors = { actor1, actor2 };

		Add.toProject(getLocalProject(), oldTestElement);
		Add.toProject(getLocalProject(), actor1);
		Add.toProject(getLocalProject(), actor2);
		Add.toProject(getLocalProject(), section);

		Add.toContainedElements(section, oldTestElement);
		Add.toContainedElements(section, Arrays.asList(actors));

		clearOperations();
		final ModelElementId actor1Id = getProject().getModelElementId(actor1);
		final ModelElementId actor2Id = getProject().getModelElementId(actor2);

		Delete.fromContainedElements(section, Arrays.asList(actors));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(3, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		checkAndCast(operations.get(1), CreateDeleteOperation.class);
		checkAndCast(operations.get(2), CreateDeleteOperation.class);
		assertEquals(3, subOperations.size());

		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);

		final SingleReferenceOperation op0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final MultiReferenceOperation op2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);

		assertEquals(TestElementFeatures.container().getName(), op0.getFeatureName());
		assertEquals(actor1Id, op0.getModelElementId());
		assertEquals(sectionId, op0.getOldValue());
		assertNull(op0.getNewValue());

		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertEquals(actor2Id, op1.getModelElementId());
		assertEquals(sectionId, op1.getOldValue());
		assertNull(op1.getNewValue());

		assertEquals(TestElementFeatures.containedElements().getName(), op2.getFeatureName());
		assertEquals(actor1Id, op2.getReferencedModelElements().get(0));
		assertEquals(actor2Id, op2.getReferencedModelElements().get(1));
		assertEquals(sectionId, op2.getModelElementId());
		assertEquals(2, op2.getReferencedModelElements().size());
		assertEquals(1, op2.getIndex());
		assertFalse(op2.isAdd());
	}

}