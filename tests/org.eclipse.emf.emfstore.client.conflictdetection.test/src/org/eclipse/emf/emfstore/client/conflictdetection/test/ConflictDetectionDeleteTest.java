/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * chodnick
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.conflictdetection.test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
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
public class ConflictDetectionDeleteTest extends ConflictDetectionTest {

	private static final String CHANGE_TO_UNRELATED_OBJECT_ON_ANOTHER_WORKING_COPY = "change to unrelated object on another working copy"; //$NON-NLS-1$
	private static final String CHANGE_TO_OBJECT_INSIDE_DELTREE_ON_ANOTHER_WORKING_COPY = "change to object inside deltree on another working copy"; //$NON-NLS-1$
	private static final String CHANGE_TO_THE_DELETED_OBJECT_ON_ANOTHER_WORKING_COPY = "change to the deleted object on another working copy"; //$NON-NLS-1$
	private static final String OLD_NAME = "old name"; //$NON-NLS-1$

	/**
	 * Tests if deleting an object is detected as conflict.
	 */
	@Test
	public void conflictDelete() {

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();
		actor.setName(OLD_NAME);

		Add.toProject(getLocalProject(), section);
		Add.toContainedElements(section, actor);
		clearOperations();

		final ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		final ModelElementId actorId = getProject().getModelElementId(actor);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) project2.getModelElement(actorId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().deleteModelElement(actor1);
				actor2.setName(CHANGE_TO_THE_DELETED_OBJECT_ON_ANOTHER_WORKING_COPY);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = ps2.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if deleting an object is detected as conflict.
	 */
	@Test
	public void conflictDeleteAttributeChangesInDeltree() {

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();
		actor.setName(OLD_NAME);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.getContainedElements().add(actor);
				clearOperations();

			}
		}.run(false);

		final ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		final ModelElementId section1Id = getProject().getModelElementId(section);
		final ModelElementId actorId = getProject().getModelElementId(actor);

		final TestElement section1 = (TestElement) getProject().getModelElement(section1Id);
		final TestElement actor2 = (TestElement) project2.getModelElement(actorId);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().deleteModelElement(section1);
				actor2.setName(CHANGE_TO_OBJECT_INSIDE_DELTREE_ON_ANOTHER_WORKING_COPY);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = ps2.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if deleting an object is detected as conflict.
	 */
	@Test
	public void conflictDeleteAttributeChangesInDelObject() {

		final TestElement section = Create.testElement();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				clearOperations();

			}
		}.run(false);

		final ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		final ModelElementId sectionId = getProject().getModelElementId(section);

		final TestElement section1 = (TestElement) getProject().getModelElement(sectionId);
		final TestElement section2 = (TestElement) project2.getModelElement(sectionId);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().deleteModelElement(section1);
				section2.setName(CHANGE_TO_OBJECT_INSIDE_DELTREE_ON_ANOTHER_WORKING_COPY);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = ps2.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if deleting an object is detected as conflict.
	 */
	@Test
	public void noConflictDeleteUnrelated() {

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				getProject().addModelElement(actor);
				clearOperations();

			}
		}.run(false);

		final ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		final ModelElementId actorId = getProject().getModelElementId(actor);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement section2 = (TestElement) project2.getModelElement(sectionId);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setName(CHANGE_TO_UNRELATED_OBJECT_ON_ANOTHER_WORKING_COPY);
				project2.deleteModelElement(section2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = ps2.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if deleting an object is detected as conflict.
	 */
	@Test
	public void conflictDeleteContainmentChangesInDeltree() {

		final TestElement section = Create.testElement();
		final TestElement pack = Create.testElement();
		final TestElement br = Create.testElement();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				getProject().addModelElement(pack);
				getProject().addModelElement(br);
				section.getContainedElements().add(pack);
				clearOperations();
			}
		}.run(false);

		final ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		final ModelElementId brId = getProject().getModelElementId(br);
		final ModelElementId packId = getProject().getModelElementId(pack);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		final TestElement br1 = (TestElement) getProject().getModelElement(brId);
		final TestElement pack1 = (TestElement) getProject().getModelElement(packId);
		final TestElement section2 = (TestElement) project2.getModelElement(sectionId);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				br1.setContainer(pack1);
				project2.deleteModelElement(section2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = ps2.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if deleting an object is detected as conflict.
	 */
	@Test
	public void conflictDeleteNonContainmentChangesInDeltree() {

		final TestElement section = Create.testElement();
		final TestElement useCase = Create.testElement();
		final TestElement mileStone = Create.testElement();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				getProject().addModelElement(useCase);
				getProject().addModelElement(mileStone);
				section.getContainedElements().add(useCase);
				clearOperations();
			}
		}.run(false);

		final ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		final ModelElementId mileStoneId = getProject().getModelElementId(mileStone);
		final ModelElementId useCaseId = getProject().getModelElementId(useCase);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		final TestElement mileStone1 = (TestElement) getProject().getModelElement(mileStoneId);
		final TestElement useCase1 = (TestElement) getProject().getModelElement(useCaseId);
		final TestElement section2 = (TestElement) project2.getModelElement(sectionId);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				useCase1.getContainedElements().add(mileStone1);
				project2.deleteModelElement(section2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = ps2.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		// technically no conflict, since annotated milestone will not be deleted,
		// but there is no way to tell containment from non-containment changes,
		// therefore it is expected that this will be detected as a hard conflict
		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if deleting an object is detected as conflict.
	 */
	@Test
	public void conflictDeleteMoveChangesInDeltree() {

		final TestElement section = Create.testElement();
		final TestElement pack = Create.testElement();
		final TestElement br1 = Create.testElement();
		final TestElement br2 = Create.testElement();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				getProject().addModelElement(pack);
				getProject().addModelElement(br1);

				br1.setContainer(pack);
				br2.setContainer(pack);

			}
		}.run(false);

		assertEquals(pack.getContainedElements().get(0), br1);
		assertEquals(pack.getContainedElements().get(1), br2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				section.getContainedElements().add(pack);
				clearOperations();
			}
		}.run(false);

		final ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		final ModelElementId packId = getProject().getModelElementId(pack);
		final ModelElementId sectionId = getProject().getModelElementId(section);

		final TestElement pack1 = (TestElement) getProject().getModelElement(packId);
		final TestElement section2 = (TestElement) project2.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				pack1.getContainedElements().move(1, 0);
				project2.deleteModelElement(section2);

			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = ps2.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		// a move change is a change... from users perspective it should not be lost, probably..
		// currently considered to be a hard conflict, because the user should know
		assertEquals(1, conflicts.size());

	}
}
