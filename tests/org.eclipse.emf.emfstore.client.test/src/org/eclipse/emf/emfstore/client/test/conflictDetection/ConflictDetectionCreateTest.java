/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - intial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.conflictDetection;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author emueller
 */
public class ConflictDetectionCreateTest extends ConflictDetectionTest {

	/**
	 * Tests if creating map entries with the same key conflict.
	 */
	@Ignore
	@Test
	public void conflictCreateMapEntry() {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProject().addModelElement(testElement);
				clearOperations();
				return null;
			}
		});

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		final ProjectSpace secondProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) secondProjectSpace.getProject().getModelElement(
			modelElementId);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getStringToStringMap().put("foo", "bar");
				return null;
			}
		});

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				clonedTestElement.getStringToStringMap().put("foo", "quux");
				return null;
			}
		});

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = secondProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);

		assertTrue(conflicts.size() > 0);
	}

	@Ignore
	@Test
	public void conflictCreateAttribute() {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProject().addModelElement(testElement);
				clearOperations();
				return null;
			}
		});

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		final ProjectSpace secondProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) secondProjectSpace.getProject().getModelElement(
			modelElementId);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.setName("foo");
				return null;
			}
		});

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				clonedTestElement.setName("foo");
				return null;
			}
		});

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = secondProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);

		assertTrue(conflicts.size() > 0);
	}

	@Override
	protected void configureCompareAtEnd() {
		setCompareAtEnd(false);
	}

}
