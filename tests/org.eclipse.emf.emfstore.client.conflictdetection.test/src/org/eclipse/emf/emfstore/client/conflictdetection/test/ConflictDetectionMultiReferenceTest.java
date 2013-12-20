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
package org.eclipse.emf.emfstore.client.conflictdetection.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests conflict detection behaviour on attributes.
 * 
 * @author chodnick
 */
public class ConflictDetectionMultiReferenceTest extends ConflictDetectionTest {

	private static final String SECTION2 = "section"; //$NON-NLS-1$
	private static final String DUMMY3 = "dummy"; //$NON-NLS-1$
	private static final String ACTOR2 = "actor"; //$NON-NLS-1$

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictAddAddSameObjectSameIndex() {

		final ModelElementId actorId = createTestElement();
		final ModelElementId sectionId = createTestElement(true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getContainedElements().add(actor1);
				section2.getContainedElements().add(actor2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement dummy = Create.testElement();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.getContainedElements().add(dummy);
				getProject().addModelElement(actor);
				clearOperations();
			}
		}.run(false);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final ModelElementId actorId = getProject().getModelElementId(actor);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getContainedElements().add(actor1);
				section2.getContainedElements().add(actor2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummyId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getContainedElements().add(actor1);
				section2.getContainedElements().add(actor2);
				section2.getContainedElements().add(dummy2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId sectionId = createTestElement(true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getContainedElements().add(actor1);
				section2.getContainedElements().add(actor2);
				section2.getContainedElements().remove(actor2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddParentSetRemoveSameObject() {

		final ModelElementId actorId = createTestElement();
		final ModelElementId sectionId = createTestElement();
		final ModelElementId otherSectionId = createTestElement(true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getContainedElements().add(actor1);
				section2.getContainedElements().add(actor2);
				actor2.setContainer(otherSection2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddRemoveIndirectlySameObject() {

		final ModelElementId actorId = createTestElement();
		final ModelElementId sectionId = createTestElement();
		final ModelElementId otherSectionId = createTestElement(true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getContainedElements().add(actor1);
				section2.getContainedElements().add(actor2);
				otherSection2.getContainedElements().add(actor2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetAddParentSetRemoveSameObject() {

		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement(false);
		final ModelElementId actorId = createTestElement(true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		setTestElement(actor1, section1);
		setTestElement(actor2, section2);
		setTestElement(actor2, otherSection2);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);
	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetAddRemoveIndirectlySameObject() {

		final ModelElementId actorId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement(true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setContainer(section1);
				actor2.setContainer(section2);
				otherSection2.getContainedElements().add(actor2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetAddRemoveSameObject() {

		final ModelElementId actorId = createTestElement();
		final ModelElementId sectionId = createTestElement(true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setContainer(section1);
				actor2.setContainer(section2);
				section2.getContainedElements().remove(actor2);
			}
		}.run(false);
		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetAddIndirectRemoveSameObject() {

		final ModelElementId actorId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement(true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setContainer(section1);
				actor2.setContainer(section2);
				otherSection2.getContainedElements().add(actor2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddIndirectRemoveSameObject() {

		final ModelElementId actorId = createTestElement();
		final ModelElementId sectionId = createTestElement();
		final ModelElementId otherSectionId = createTestElement(true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getContainedElements().add(actor1);
				section2.getContainedElements().add(actor2);
				otherSection2.getContainedElements().add(actor2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// hard conflict between add and remove, serialization matters
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictRemoveRemoveSameObject() {

		final ModelElementId actorId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);

		createTestElement();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);

		setTestElement(actor1, section1, true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getContainedElements().remove(actor1);
				section2.getContainedElements().remove(actor2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// no hard conflict
		final Set<AbstractOperation> hardConflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(1, hardConflicts.size());

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetRemoveRemoveIndirectlySameObject() {

		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement();
		final ModelElementId actorId = createTestElement();

		final TestElement actor = (TestElement) getProject().getModelElement(actorId);
		final TestElement section = (TestElement) getProject().getModelElement(sectionId);

		setTestElement(actor, section, true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.setContainer(section);
				otherSection2.getContainedElements().add(actor2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// no index conflict
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictParentSetRemoveParentSetRemoveSameObject() {

		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement(false);
		final ModelElementId anotherSectionId = createTestElement(false);
		final ModelElementId actorId = createTestElement();

		final TestElement actor = (TestElement) getProject().getModelElement(actorId);
		final TestElement section = (TestElement) getProject().getModelElement(sectionId);
		final TestElement otherSection = (TestElement) getProject().getModelElement(otherSectionId);
		final TestElement anotherSection = (TestElement) getProject().getModelElement(anotherSectionId);

		setTestElement(actor, section, true);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final ModelElementId anotherSection2Id = clonedProject.getModelElementId(anotherSection);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);
		final TestElement anotherSection2 = (TestElement) clonedProject.getModelElement(anotherSection2Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor.setContainer(otherSection);
				actor2.setContainer(anotherSection2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// a hard conflict, though. serialization matters
		final Set<AbstractOperation> hardConflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(), getConflicts(oclonedProjectSpace, ops1)
			.size());

		assertEquals(hardConflicts.size(), 1);

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictAddMoveSameObject() {

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);

		final TestElement dummy = (TestElement) getProject().getModelElement(dummyId);
		final TestElement section = (TestElement) getProject().getModelElement(sectionId);

		addToSection(section, true, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				section1.getContainedElements().add(actor1);
				section2.getContainedElements().add(actor2);
				section2.getContainedElements().move(0, actor2);
			}
		});

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// no index conflict
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);

		final TestElement actor = (TestElement) getProject().getModelElement(actorId);
		final TestElement dummy = (TestElement) getProject().getModelElement(dummyId);
		final TestElement section = (TestElement) getProject().getModelElement(sectionId);

		addToSection(section, true, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.setContainer(section);
				section2.getContainedElements().add(actor2);
				section2.getContainedElements().move(0, actor2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// no index conflict
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
				.size());

		// index conflict arises: if the add happens before the move, the move will work
		// if it does after the move, the move could be ineffective
		assertEquals(1, conflicts.size());
	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void conflictMoveMoveSameObjectDifferentIndex() {

		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();

		final TestElement section = getModelElement(sectionId);
		final TestElement actor = getModelElement(actorId);
		final TestElement dummy1 = getModelElement(dummyId);
		final TestElement dummy2 = getModelElement(otherDummyId);

		addToSection(section, true, dummy1, dummy2, actor);

		assertEquals(section.getContainedElements().get(2), actor);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement actor2 = (TestElement) clonedProject.getModelElement(actorId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getContainedElements().move(1, actor);
				section2.getContainedElements().move(0, actor2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		// no index conflict
		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement(false);
		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();

		final TestElement section = getModelElement(sectionId);
		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);

		addToSection(section, true, otherDummy, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement otherSection2 = getModelElement(clonedProject, otherSectionId);
		final TestElement dummy2 = getModelElement(clonedProject, dummyId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getContainedElements().add(1, actor);
				dummy2.setContainer(otherSection2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement(false);
		final ModelElementId dummyId = createTestElement();

		final TestElement actor = getModelElement(actorId);
		final TestElement section = getModelElement(sectionId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(dummyId);

		addToSection(section, true, otherDummy, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummyId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getContainedElements().add(1, actor);
				dummy2.setContainer(otherSection2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);

		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement section = getModelElement(sectionId);

		addToSection(section, true, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final ModelElementId dummy2Id = clonedProject.getModelElementId(dummy);
		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummy2Id);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor.setContainer(section);
				section2.getContainedElements().remove(dummy2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement();

		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement section = getModelElement(sectionId);

		addToSection(section, true, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummyId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.setContainer(section);
				otherSection2.getContainedElements().add(dummy2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement(ACTOR2);
		final ModelElementId dummyId = createTestElement(DUMMY3);
		final ModelElementId sectionId = createTestElement(SECTION2);

		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement section = getModelElement(sectionId);

		addToSection(section, true, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummyId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.setContainer(section);
				dummy2.setContainer(otherSection2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
				.size());
		// no index-integrity conflict (outcome does not depend on serialization)
		assertEquals(0, conflicts.size());

	}

	/**
	 * Tests if manipulating multi-features is detected as a conflict.
	 */
	@Test
	public void noConflictRemoveRemoveDifferentObject() {

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);

		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement section = getModelElement(sectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final ModelElementId dummy2Id = clonedProject.getModelElementId(dummy);
		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummy2Id);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getContainedElements().remove(actor);
				section2.getContainedElements().remove(dummy2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement();

		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement section = getModelElement(sectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummyId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getContainedElements().remove(actor);
				otherSection2.getContainedElements().add(dummy2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement();

		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement section = getModelElement(sectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummyId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section.getContainedElements().remove(actor);
				otherSection2.getContainedElements().add(dummy2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement();
		final ModelElementId anotherSectionId = createTestElement(false);

		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement section = getModelElement(sectionId);
		final TestElement anotherSection = getModelElement(anotherSectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummyId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor.setContainer(anotherSection);
				otherSection2.getContainedElements().add(dummy2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement(false);
		final ModelElementId anotherSectionId = createTestElement(false);

		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement section = getModelElement(sectionId);
		final TestElement anotherSection = getModelElement(anotherSectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummyId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor.setContainer(anotherSection);
				dummy2.setContainer(otherSection2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement();
		final ModelElementId anotherSectionId = createTestElement();

		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement section = getModelElement(sectionId);
		final TestElement anotherSection = getModelElement(anotherSectionId);

		addToSection(section, true, otherDummy, actor, dummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement dummy2 = (TestElement) clonedProject.getModelElement(dummyId);
		final TestElement otherSection2 = (TestElement) clonedProject.getModelElement(otherSectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				anotherSection.getContainedElements().add(actor);
				otherSection2.getContainedElements().add(dummy2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId anotherDummyId = createTestElement();

		final TestElement section = getModelElement(sectionId);
		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement anotherDummy = getModelElement(anotherDummyId);

		addToSection(section, true, dummy, otherDummy, anotherDummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement anotherDummy2 = (TestElement) clonedProject.getModelElement(anotherDummyId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getContainedElements().add(0, actor);
				section2.getContainedElements().move(1, anotherDummy2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId anotherDummyId = createTestElement();

		final TestElement section = getModelElement(sectionId);
		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement anotherDummy = getModelElement(anotherDummyId);

		addToSection(section, true, actor, dummy, otherDummy, anotherDummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement anotherDummy2 = (TestElement) clonedProject.getModelElement(anotherDummyId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getContainedElements().remove(actor);
				section2.getContainedElements().move(0, anotherDummy2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId anotherDummyId = createTestElement();

		final TestElement section = getModelElement(sectionId);
		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement anotherDummy = getModelElement(anotherDummyId);

		addToSection(section, true, actor, dummy, otherDummy, anotherDummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement anotherDummy2 = (TestElement) clonedProject.getModelElement(anotherDummyId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getContainedElements().remove(dummy);
				section2.getContainedElements().move(1, anotherDummy2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement(false);
		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId anotherDummyId = createTestElement();

		final TestElement section = getModelElement(sectionId);
		final TestElement otherSection = getModelElement(otherSectionId);
		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement anotherDummy = getModelElement(anotherDummyId);

		addToSection(section, true, actor, dummy, otherDummy, anotherDummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement anotherDummy2 = (TestElement) clonedProject.getModelElement(anotherDummyId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				dummy.setContainer(otherSection);
				section2.getContainedElements().move(1, anotherDummy2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId sectionId = createTestElement(false);
		final ModelElementId otherSectionId = createTestElement();
		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId anotherDummyId = createTestElement();

		final TestElement section = getModelElement(sectionId);
		final TestElement otherSection = getModelElement(otherSectionId);
		final TestElement actor = getModelElement(actorId);
		final TestElement dummy = getModelElement(dummyId);
		final TestElement otherDummy = getModelElement(otherDummyId);
		final TestElement anotherDummy = getModelElement(anotherDummyId);

		addToSection(section, true, actor, dummy, otherDummy, anotherDummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement anotherDummy2 = (TestElement) clonedProject.getModelElement(anotherDummyId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				otherSection.getContainedElements().add(dummy);
				section2.getContainedElements().move(1, anotherDummy2);
			}
		}.run(false);
		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
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

		final ModelElementId sectionId = createTestElement(false);

		final ModelElementId actorId = createTestElement();
		final ModelElementId dummyId = createTestElement();
		final ModelElementId otherDummyId = createTestElement();
		final ModelElementId anotherDummyId = createTestElement();

		final TestElement section = (TestElement) getProject().getModelElement(sectionId);
		final TestElement actor = (TestElement) getProject().getModelElement(actorId);
		final TestElement dummy = (TestElement) getProject().getModelElement(dummyId);
		final TestElement otherDummy = (TestElement) getProject().getModelElement(otherDummyId);
		final TestElement anotherDummy = (TestElement) getProject().getModelElement(anotherDummyId);

		addToSection(section, true, actor, dummy, otherDummy, anotherDummy);

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final Project clonedProject = clonedProjectSpace.getProject();

		final TestElement anotherDummy2 = (TestElement) clonedProject.getModelElement(anotherDummyId);
		final TestElement section2 = (TestElement) clonedProject.getModelElement(sectionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getContainedElements().move(2, actor);
				section2.getContainedElements().move(0, anotherDummy2);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> oclonedProjectSpace = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, oclonedProjectSpace);
		assertEquals(getConflicts(ops1, oclonedProjectSpace).size(),
			getConflicts(oclonedProjectSpace, ops1)
				.size());
		// no index-integrity conflict: result independent of serialization
		assertEquals(0, conflicts.size());

	}

	private ModelElementId createTestElement(final boolean shouldClearOperations) {
		final TestElement actor = Create.testElement();
		return addToProject(actor, shouldClearOperations);
	}

	/**
	 * Creates the test element and adds it to the project.
	 * 
	 * @param name
	 *            the name of the test element
	 * @return the ID of the created test element as assigned by the project
	 */
	private ModelElementId createTestElement(final String name) {
		final TestElement actor = Create.testElement(name);
		return addToProject(actor, false);
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

	private ModelElementId createTestElement() {
		return createTestElement(false);
	}

	@SuppressWarnings({ "unchecked" })
	private <T> T getModelElement(Project project, ModelElementId id) {
		return (T) project.getModelElement(id);
	}

	@SuppressWarnings("unchecked")
	private <T> T getModelElement(ModelElementId id) {
		return (T) getProject().getModelElement(id);
	}

	private void addToSection(
		final TestElement section,
		final boolean shouldClearOperations,
		final TestElement... children) {

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				section.getContainedElements().addAll(Arrays.asList(children));
				if (shouldClearOperations) {
					clearOperations();
				}
				return null;
			}
		});
	}

	private void setTestElement(final TestElement actor, final TestElement leafSection) {
		setTestElement(actor, leafSection, false);
	}

	private void setTestElement(final TestElement actor, final TestElement leafSection,
		final boolean shouldClearOperation) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				actor.setContainer(leafSection);
				if (shouldClearOperation) {
					clearOperations();
				}
				return null;
			}
		});
	}

}
