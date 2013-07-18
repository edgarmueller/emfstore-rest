/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * chodnick - initial API and implementatin
 * Edgar Mueller - refactorings to reduce code duplication
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.conflictDetection;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.test.model.UnicaseModelElement;
import org.eclipse.emf.emfstore.client.test.model.document.DocumentFactory;
import org.eclipse.emf.emfstore.client.test.model.document.LeafSection;
import org.eclipse.emf.emfstore.client.test.model.requirement.Actor;
import org.eclipse.emf.emfstore.client.test.model.requirement.RequirementFactory;
import org.eclipse.emf.emfstore.client.test.model.requirement.UseCase;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.junit.Test;

/**
 * Tests conflict detection behaviour on attributes.
 * 
 * @author chodnick
 */
public class ConflictDetectionMultiReferenceTest extends ConflictDetectionTest {

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictAddAddSameObjectSameIndex() {

		ModelElementId actorId = createActor();
		ModelElementId sectionId = createLeafSection(true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getModelElements().add(actor1);
				section2.getModelElements().add(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// same operations going on in both working copies, no conflicts expected
		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddAddSameObjectSameIndexNonZero() {

		final LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		final Actor actor = RequirementFactory.eINSTANCE.createActor();
		final Actor dummy = RequirementFactory.eINSTANCE.createActor();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.getModelElements().add(dummy);
				getProject().addModelElement(actor);
				clearOperations();
			}
		}.run(false);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		ModelElementId actorId = getProject().getModelElementId(actor);
		ModelElementId sectionId = getProject().getModelElementId(section);

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getModelElements().add(actor1);
				section2.getModelElements().add(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());
		// same operations going on in both working copies, no conflicts expected
		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddAddDifferentObjectDifferentIndex() {

		ModelElementId actorId = createActor();
		ModelElementId dummyId = createActor();
		ModelElementId sectionId = createLeafSection(true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummyId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getModelElements().add(actor1);
				section2.getModelElements().add(actor2);
				section2.getModelElements().add(dummy2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// obviously an index-integrity conflict
		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddRemoveSameObject() {

		ModelElementId actorId = createActor();
		ModelElementId sectionId = createLeafSection(true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getModelElements().add(actor1);
				section2.getModelElements().add(actor2);
				section2.getModelElements().remove(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddParentSetRemoveSameObject() {

		ModelElementId actorId = createActor();
		ModelElementId sectionId = createLeafSection();
		ModelElementId otherSectionId = createLeafSection(true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getModelElements().add(actor1);
				section2.getModelElements().add(actor2);
				actor2.setLeafSection(otherSection2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddRemoveIndirectlySameObject() {

		ModelElementId actorId = createActor();
		ModelElementId sectionId = createLeafSection();
		ModelElementId otherSectionId = createLeafSection(true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getModelElements().add(actor1);
				section2.getModelElements().add(actor2);
				otherSection2.getModelElements().add(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetAddParentSetRemoveSameObject() {

		ModelElementId sectionId = createLeafSection();
		ModelElementId otherSectionId = createLeafSection();
		ModelElementId actorId = createActor(true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		setLeafSection(actor1, section1);
		setLeafSection(actor2, section2);
		setLeafSection(actor2, otherSection2);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);
	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetAddRemoveIndirectlySameObject() {

		ModelElementId actorId = createActor();
		ModelElementId sectionId = createLeafSection();
		ModelElementId otherSectionId = createLeafSection(true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setLeafSection(section1);
				actor2.setLeafSection(section2);
				otherSection2.getModelElements().add(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetAddRemoveSameObject() {

		ModelElementId actorId = createActor();
		ModelElementId sectionId = createLeafSection(true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setLeafSection(section1);
				actor2.setLeafSection(section2);
				section2.getModelElements().remove(actor2);
			}
		}.run(false);
		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetAddIndirectRemoveSameObject() {

		ModelElementId actorId = createActor();
		ModelElementId sectionId = createLeafSection();
		ModelElementId otherSectionId = createLeafSection(true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setLeafSection(section1);
				actor2.setLeafSection(section2);
				otherSection2.getModelElements().add(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddIndirectRemoveSameObject() {

		ModelElementId actorId = createActor();
		ModelElementId sectionId = createLeafSection();
		ModelElementId otherSectionId = createLeafSection(true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getModelElements().add(actor1);
				section2.getModelElements().add(actor2);
				otherSection2.getModelElements().add(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictRemoveRemoveSameObject() {

		ModelElementId actorId = createActor();
		ModelElementId sectionId = createLeafSection();

		createLeafSection();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);

		setLeafSection(actor1, section1, true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getModelElements().remove(actor1);
				section2.getModelElements().remove(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// no hard conflict
		Set<AbstractOperation> hardConflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(1, hardConflicts.size());

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetRemoveRemoveIndirectlySameObject() {

		ModelElementId sectionId = createLeafSection();
		ModelElementId otherSectionId = createLeafSection();
		ModelElementId actorId = createActor();

		final Actor actor = (Actor) getProject().getModelElement(actorId);
		final LeafSection section = (LeafSection) getProject().getModelElement(sectionId);

		setLeafSection(actor, section, true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.setLeafSection(section);
				otherSection2.getModelElements().add(actor2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// no index conflict
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetRemoveParentSetRemoveSameObject() {

		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();
		final ModelElementId anotherSectionId = createLeafSection();
		final ModelElementId actorId = createActor();

		final Actor actor = (Actor) getProject().getModelElement(actorId);
		final LeafSection section = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection otherSection = (LeafSection) getProject().getModelElement(otherSectionId);
		final LeafSection anotherSection = (LeafSection) getProject().getModelElement(anotherSectionId);

		setLeafSection(actor, section, true);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		ModelElementId anotherSection2Id = clonedProject.getModelElementId(anotherSection);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);
		final LeafSection anotherSection2 = (LeafSection) clonedProject.getModelElement(anotherSection2Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor.setLeafSection(otherSection);
				actor2.setLeafSection(anotherSection2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// a hard conflict, though. serialization matters
		Set<AbstractOperation> hardConflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(hardConflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddMoveSameObject() {

		ModelElementId actorId = createActor();
		ModelElementId dummyId = createActor();
		ModelElementId sectionId = createLeafSection();

		final Actor dummy = (Actor) getProject().getModelElement(dummyId);
		final LeafSection section = (LeafSection) getProject().getModelElement(sectionId);

		addToSection(section, true, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor1 = (Actor) getProject().getModelElement(actorId);
		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);

		final LeafSection section1 = (LeafSection) getProject().getModelElement(sectionId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getModelElements().add(actor1);
				section2.getModelElements().add(actor2);
				section2.getModelElements().move(0, actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// no index conflict
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());

		// index conflict arises: if the add happens before the move, the move will work
		// if it does after the move, the move could be ineffective
		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetAddMoveSameObject() {

		ModelElementId actorId = createActor();
		ModelElementId dummyId = createActor();
		ModelElementId sectionId = createLeafSection();

		final Actor actor = (Actor) getProject().getModelElement(actorId);
		final Actor dummy = (Actor) getProject().getModelElement(dummyId);
		final LeafSection section = (LeafSection) getProject().getModelElement(sectionId);

		addToSection(section, true, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.setLeafSection(section);
				section2.getModelElements().add(actor2);
				section2.getModelElements().move(0, actor2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// no index conflict
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());

		// index conflict arises: if the add happens before the move, the move will work
		// if it does after the move, the move could be ineffective
		assertEquals(conflicts.size(), 1);
	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictMoveMoveSameObjectDifferentIndex() {

		final ModelElementId sectionId = createLeafSection();
		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();

		final LeafSection section = getModelElement(sectionId);
		final Actor actor = getModelElement(actorId);
		final Actor dummy1 = getModelElement(dummyId);
		final Actor dummy2 = getModelElement(otherDummyId);

		addToSection(section, true, dummy1, dummy2, actor);

		assertEquals(section.getModelElements().get(2), actor);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor actor2 = (Actor) clonedProject.getModelElement(actorId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().move(1, actor);
				section2.getModelElements().move(0, actor2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// no index conflict
		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());

		// an index conflict arises: result depends on which move comes last
		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddParentSetRemoveDifferentObject() {

		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();
		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();

		final LeafSection section = getModelElement(sectionId);
		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);

		addToSection(section, true, otherDummy, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final LeafSection otherSection2 = getModelElement(clonedProject, otherSectionId);
		final Actor dummy2 = getModelElement(clonedProject, dummyId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().add(1, actor);
				dummy2.setLeafSection(otherSection2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		assertEquals(conflicts.size(), 0);
	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictAddParentSetRemoveDifferentObject() {

		final ModelElementId actorId = createActor();
		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();
		final ModelElementId dummyId = createActor();

		final Actor actor = getModelElement(actorId);
		final LeafSection section = getModelElement(sectionId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(dummyId);

		addToSection(section, true, otherDummy, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummyId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().add(1, actor);
				dummy2.setLeafSection(otherSection2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict (the change happens at the boundary)
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictParentSetAddRemoveDifferentObject() {

		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId sectionId = createLeafSection();

		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final LeafSection section = getModelElement(sectionId);

		addToSection(section, true, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		ModelElementId dummy2Id = clonedProject.getModelElementId(dummy);
		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummy2Id);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor.setLeafSection(section);
				section2.getModelElements().remove(dummy2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict (outcome does not depend on serialization)
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictParentSetAddRemoveIndirectlyDifferentObject() {

		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();

		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final LeafSection section = getModelElement(sectionId);

		addToSection(section, true, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummyId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.setLeafSection(section);
				otherSection2.getModelElements().add(dummy2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict (outcome does not depend on serialization)
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictParentSetAddParentSetRemoveDifferentObject() {

		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId sectionId = createLeafSection();

		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final LeafSection section = getModelElement(sectionId);

		addToSection(section, true, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummyId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.setLeafSection(section);
				dummy2.setLeafSection(otherSection2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict (outcome does not depend on serialization)
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictRemoveRemoveDifferentObject() {

		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId sectionId = createLeafSection();

		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final LeafSection section = getModelElement(sectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		ModelElementId dummy2Id = clonedProject.getModelElementId(dummy);
		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummy2Id);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().remove(actor);
				section2.getModelElements().remove(dummy2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictRemoveParentSetRemoveDifferentObject() {

		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();

		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final LeafSection section = getModelElement(sectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummyId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().remove(actor);
				otherSection2.getModelElements().add(dummy2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictRemoveRemoveIndirectlyDifferentObject() {

		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();

		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final LeafSection section = getModelElement(sectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummyId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section.getModelElements().remove(actor);
				otherSection2.getModelElements().add(dummy2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictParentSetRemoveRemoveIndirectlyDifferentObject() {

		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();
		final ModelElementId anotherSectionId = createLeafSection();

		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final LeafSection section = getModelElement(sectionId);
		final LeafSection anotherSection = getModelElement(anotherSectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummyId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.setLeafSection(anotherSection);
				otherSection2.getModelElements().add(dummy2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictParentSetRemoveParentSetRemoveDifferentObject() {

		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();
		final ModelElementId anotherSectionId = createLeafSection();

		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final LeafSection section = getModelElement(sectionId);
		final LeafSection anotherSection = getModelElement(anotherSectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummyId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor.setLeafSection(anotherSection);
				dummy2.setLeafSection(otherSection2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictRemoveIndirectlyRemoveIndirectlyDifferentObject() {

		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();
		final ModelElementId anotherSectionId = createLeafSection();

		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final LeafSection section = getModelElement(sectionId);
		final LeafSection anotherSection = getModelElement(anotherSectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor dummy2 = (Actor) clonedProject.getModelElement(dummyId);
		final LeafSection otherSection2 = (LeafSection) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				anotherSection.getModelElements().add(actor);
				otherSection2.getModelElements().add(dummy2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictAddMoveDifferentObject() {

		final ModelElementId sectionId = createLeafSection();
		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId anotherDummyId = createActor();

		final LeafSection section = getModelElement(sectionId);
		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final Actor anotherDummy = getModelElement(anotherDummyId);

		addToSection(section, true, dummy, otherDummy, anotherDummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor anotherDummy2 = (Actor) clonedProject.getModelElement(anotherDummyId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().add(0, actor);
				section2.getModelElements().move(1, anotherDummy2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);
	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictRemoveMoveDifferentObject() {

		final ModelElementId sectionId = createLeafSection();
		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId anotherDummyId = createActor();

		final LeafSection section = getModelElement(sectionId);
		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final Actor anotherDummy = getModelElement(anotherDummyId);

		addToSection(section, true, actor, dummy, otherDummy, anotherDummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor anotherDummy2 = (Actor) clonedProject.getModelElement(anotherDummyId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().remove(actor);
				section2.getModelElements().move(0, anotherDummy2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictRemoveMoveDifferentObjectSameIndex() {

		final ModelElementId sectionId = createLeafSection();
		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId anotherDummyId = createActor();

		final LeafSection section = getModelElement(sectionId);
		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final Actor anotherDummy = getModelElement(anotherDummyId);

		addToSection(section, true, actor, dummy, otherDummy, anotherDummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor anotherDummy2 = (Actor) clonedProject.getModelElement(anotherDummyId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().remove(dummy);
				section2.getModelElements().move(1, anotherDummy2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetRemoveMoveDifferentObject() {

		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();
		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId anotherDummyId = createActor();

		final LeafSection section = getModelElement(sectionId);
		final LeafSection otherSection = getModelElement(otherSectionId);
		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final Actor anotherDummy = getModelElement(anotherDummyId);

		addToSection(section, true, actor, dummy, otherDummy, anotherDummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor anotherDummy2 = (Actor) clonedProject.getModelElement(anotherDummyId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				dummy.setLeafSection(otherSection);
				section2.getModelElements().move(1, anotherDummy2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictRemoveIndirectlyMoveDifferentObject() {

		final ModelElementId sectionId = createLeafSection();
		final ModelElementId otherSectionId = createLeafSection();
		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId anotherDummyId = createActor();

		final LeafSection section = getModelElement(sectionId);
		final LeafSection otherSection = getModelElement(otherSectionId);
		final Actor actor = getModelElement(actorId);
		final Actor dummy = getModelElement(dummyId);
		final Actor otherDummy = getModelElement(otherDummyId);
		final Actor anotherDummy = getModelElement(anotherDummyId);

		addToSection(section, true, actor, dummy, otherDummy, anotherDummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor anotherDummy2 = (Actor) clonedProject.getModelElement(anotherDummyId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				otherSection.getModelElements().add(dummy);
				section2.getModelElements().move(1, anotherDummy2);
			}
		}.run(false);
		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictMoveMoveDifferentObject() {

		final ModelElementId sectionId = createLeafSection();

		final ModelElementId actorId = createActor();
		final ModelElementId dummyId = createActor();
		final ModelElementId otherDummyId = createActor();
		final ModelElementId anotherDummyId = createActor();

		final LeafSection section = (LeafSection) getProject().getModelElement(sectionId);
		final Actor actor = (Actor) getProject().getModelElement(actorId);
		final Actor dummy = (Actor) getProject().getModelElement(dummyId);
		final Actor otherDummy = (Actor) getProject().getModelElement(otherDummyId);
		final Actor anotherDummy = (Actor) getProject().getModelElement(anotherDummyId);

		addToSection(section, true, actor, dummy, otherDummy, anotherDummy);

		ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		Project clonedProject = clonedProjectSpace.getProject();

		final Actor anotherDummy2 = (Actor) clonedProject.getModelElement(anotherDummyId);
		final LeafSection section2 = (LeafSection) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getModelElements().move(2, actor);
				section2.getModelElements().move(0, anotherDummy2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
			.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(0, conflicts.size());

	}

	private ModelElementId createActor(final boolean shouldClearOperations) {
		final Actor actor = RequirementFactory.eINSTANCE.createActor();
		return addToProject(actor, shouldClearOperations);
	}

	private ModelElementId createLeafSection(final boolean shouldClearOperations) {
		final LeafSection section = DocumentFactory.eINSTANCE.createLeafSection();
		return addToProject(section, shouldClearOperations);
	}

	private ModelElementId createUseCase(final boolean shouldClearOperations) {
		final UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		return addToProject(useCase, shouldClearOperations);
	}

	private ModelElementId addToProject(final EObject eObject, final boolean shouldClearOperations) {
		return RunESCommand.runWithResult(new Callable<ModelElementId>() {
			public ModelElementId call() throws Exception {
				getProject().addModelElement(eObject);
				if (shouldClearOperations) {
					clearOperations();
				}
				return getProject().getModelElementId(eObject);
			}
		});
	}

	private ModelElementId createLeafSection() {
		return createLeafSection(false);
	}

	private ModelElementId createActor() {
		return createActor(false);
	}

	private ModelElementId createUseCase() {
		return createUseCase(false);
	}

	@SuppressWarnings({ "unchecked" })
	private <T> T getModelElement(Project project, ModelElementId id) {
		return (T) project.getModelElement(id);
	}

	@SuppressWarnings("unchecked")
	private <T> T getModelElement(ModelElementId id) {
		return (T) getProject().getModelElement(id);
	}

	private <U extends UnicaseModelElement> void addToSection(
		final LeafSection section,
		final boolean shouldClearOperations,
		final U... children) {

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				for (U child : children) {
					section.getModelElements().add(child);
				}
				if (shouldClearOperations) {
					clearOperations();
				}
				return null;
			}
		});
	}

	private <U extends UnicaseModelElement> void addToUseCase(
		final UseCase useCase,
		final boolean shouldClearOperations,
		final Actor... actors) {

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				for (Actor child : actors) {
					useCase.getParticipatingActors().add(child);
				}
				if (shouldClearOperations) {
					clearOperations();
				}
				return null;
			}
		});
	}

	private void setLeafSection(final Actor actor, final LeafSection leafSection) {
		setLeafSection(actor, leafSection, false);
	}

	private void setLeafSection(final Actor actor, final LeafSection leafSection, final boolean shouldClearOperation) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				actor.setLeafSection(leafSection);
				if (shouldClearOperation) {
					clearOperations();
				}
				return null;
			}
		});
	}

	@Override
	protected void configureCompareAtEnd() {
		setCompareAtEnd(false);
	}
}
