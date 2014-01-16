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
import org.eclipse.emf.emfstore.client.test.common.dsl.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.dsl.Update;
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
public class ConflictDetectionAttributeTest extends ConflictDetectionTest {

	private static final String UNRELATED_CHANGE = "unrelated change"; //$NON-NLS-1$
	private static final String CHANGE_1 = "change 1"; //$NON-NLS-1$
	private static final String CHANGE_2 = "change 2"; //$NON-NLS-1$
	private static final String OLD_NAME = "old name"; //$NON-NLS-1$

	/**
	 * Tests if overwriting of attributes is detected as conflict.
	 */
	@Test
	public void conflictAttribute() {

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement();
		actor.setName(OLD_NAME);

		Add.toProject(getLocalProject(), section);
		Add.toContainedElements(section, actor);

		clearOperations();

		final ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		final ModelElementId actor1Id = getProject().getModelElementId(actor);
		final ModelElementId actor2Id = actor1Id;

		final TestElement actor1 = (TestElement) getProject().getModelElement(actor1Id);
		final TestElement actor2 = (TestElement) project2.getModelElement(actor2Id);

		Update.testElement(TestElementFeatures.name(), actor1, CHANGE_1);
		Update.testElement(TestElementFeatures.name(), actor2, CHANGE_2);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = ps2.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if overwriting of attributes is detected as conflict.
	 */
	@Test
	public void noConflictAttributeSameValue() {

		final TestElement testElement = Create.testElement();
		final TestElement containedElement = Create.testElement();
		containedElement.setName(OLD_NAME);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.getContainedElements().add(containedElement);
				clearOperations();
			}
		}.run(false);

		final ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		final ModelElementId actor1Id = getProject().getModelElementId(containedElement);
		final ModelElementId actor2Id = actor1Id;

		final TestElement testElement1 = (TestElement) getProject().getModelElement(actor1Id);
		final TestElement testElement2 = (TestElement) project2.getModelElement(actor2Id);
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testElement1.setName(CHANGE_1);
				testElement2.setName(CHANGE_1);
			}
		}.run(false);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = ps2.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());
		// should not conflict, the same change happens on both sides
		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if overwriting of attributes is detected as conflict.
	 */
	@Test
	public void noConflictAttribute() {

		final TestElement section = Create.testElement();
		final TestElement actor = Create.testElement(OLD_NAME);

		Add.toProject(getLocalProject(), section);
		Add.toContainedElements(section, actor);
		clearOperations();

		final ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		final ModelElementId actorId = getProject().getModelElementId(actor);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) project2.getModelElement(actorId);

		Update.testElement(TestElementFeatures.name(), actor1, CHANGE_1);
		Update.testElement(TestElementFeatures.description(), actor2, UNRELATED_CHANGE);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = ps2.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 0);

	}
}
