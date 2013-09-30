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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.junit.Test;

/**
 * @author emueller
 */
public class ConflictDetectionMapTest extends ConflictDetectionTest {

	/**
	 * Tests if creating map entries with the same key conflict.
	 * 
	 * @throws ChangeConflictException
	 */
	@Test
	public void testConflictCreateVSCreateMapEntry() throws ChangeConflictException {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);

		updateMapEntry(testElement, "foo", "bar");
		updateMapEntry(clonedTestElement, "foo", "quux");

		// final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		// final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final ChangeConflictSet changeConflictSet = getChangeConflictSet(
			getProjectSpace().getLocalChangePackage().getOperations(),
			clonedProjectSpace.getLocalChangePackage().getOperations());

		assertTrue(changeConflictSet.getConflictBuckets().size() > 0);

		// resolve conflict
		resolveConflict(changeConflictSet);
	}

	private void resolveConflict(final ChangeConflictSet changeConflictSet) throws ChangeConflictException {
		for (final ConflictBucket bucket : changeConflictSet.getConflictBuckets()) {
			bucket.resolveConflict(new LinkedHashSet<AbstractOperation>(
				getProjectSpace().getLocalChangePackage().getOperations()),
				new LinkedHashSet<AbstractOperation>(
					clonedProjectSpace.getLocalChangePackage().getOperations()));
		}

		final ChangePackage mergeResolvedConflicts = clonedProjectSpace.mergeResolvedConflicts(changeConflictSet,
			Arrays.asList(getProjectSpace().getLocalChangePackage()),
			Arrays.asList(clonedProjectSpace.getLocalChangePackage()));

		clonedProjectSpace.applyOperations(mergeResolvedConflicts.getCopyOfOperations(), false);
	}

	@Test
	public void testConflictCreateVSDeleteMapEntry() throws ChangeConflictException {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);

		updateMapEntry(testElement, "foo", "bar");
		clearOperations();
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);

		updateMapEntry(testElement, "foo", "bar2");
		deleteMapEntry(clonedTestElement, "foo");

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);

		final ChangeConflictSet changeConflictSet = getChangeConflictSet(
			getProjectSpace().getLocalChangePackage().getOperations(),
			clonedProjectSpace.getLocalChangePackage().getOperations());

		assertTrue(conflicts.size() > 0);

		resolveConflict(changeConflictSet);
	}

	@Test
	public void testConflictUpdateVSUpdateMapEntry() {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);

		updateMapEntry(testElement, "foo", "bar");
		clearOperations();
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);

		updateMapEntry(testElement, "foo", "hello1");
		updateMapEntry(clonedTestElement, "foo", "hello2");

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);

		assertTrue(conflicts.size() > 0);
	}

	@Test
	public void testConflictUpdateVSDeleteMapEntry() throws ChangeConflictException {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);

		updateMapEntry(testElement, "foo", "bar");
		clearOperations();
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);

		updateMapEntry(testElement, "foo", "hello1");
		deleteMapEntry(clonedTestElement, "foo");

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);

		final ChangeConflictSet changeConflictSet = getChangeConflictSet(
			getProjectSpace().getLocalChangePackage().getOperations(),
			clonedProjectSpace.getLocalChangePackage().getOperations());

		assertTrue(conflicts.size() > 0);

		resolveConflict(changeConflictSet);
	}

	@Test
	public void testConflictDeleteVSDeleteMapEntry() {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);

		updateMapEntry(testElement, "foo", "bar");
		clearOperations();
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);

		deleteMapEntry(testElement, "foo");
		deleteMapEntry(clonedTestElement, "foo");

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);

		assertEquals(1, conflicts.size());
	}

	private void deleteMapEntry(final TestElement testElement, final String key) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getStringToStringMap().remove(key);
				return null;
			}
		});
	}

	private void updateMapEntry(final TestElement testElement, final String key, final String value) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getStringToStringMap().put(key, value);
				return null;
			}
		});
	}

	private void addTestElement(final TestElement testElement) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProject().addModelElement(testElement);
				clearOperations();
				return null;
			}
		});
	}

	@Override
	protected void configureCompareAtEnd() {
		setCompareAtEnd(true);
	}

}
