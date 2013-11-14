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

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.client.test.testmodel.impl.TestElementToStringMapImpl;
import org.eclipse.emf.emfstore.client.test.testmodel.impl.TestmodelFactoryImpl;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
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
	public void testConflictCreateVSCreateMapEntryNonContainedKey() throws ChangeConflictException {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement key = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);
		addTestElement(key);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		final ModelElementId keyId = getProjectSpace().getProject().getModelElementId(key);
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(keyId);

		updateMapEntryNonContainedKey(testElement, key, "bar");
		updateMapEntryNonContainedKey(clonedTestElement, clonedKey, "quux");

		final Set<AbstractOperation> conflicts = getConflicts(
			getProjectSpace().getLocalChangePackage().getOperations(),
			clonedProjectSpace.getLocalChangePackage().getOperations(), getProject());

		assertTrue(conflicts.size() > 0);

	}

	/**
	 * Tests if creating map entries with the same key conflict.
	 * 
	 * @throws ChangeConflictException
	 */
	@Test
	public void testConflictCreateVsMoveNonContainedKey() throws ChangeConflictException {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement key = TestmodelFactory.eINSTANCE.createTestElement();
		final TestmodelFactoryImpl factory = (TestmodelFactoryImpl) TestmodelFactory.eINSTANCE;
		final TestElementToStringMapImpl newEntry = (TestElementToStringMapImpl) factory.createTestElementToStringMap();
		addTestElement(testElement);
		addTestElement(key);
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProject().getModelElements().add(newEntry);
				clearOperations();
				return null;
			}
		});

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		final ModelElementId keyId = getProjectSpace().getProject().getModelElementId(key);
		final ModelElementId newEntryId = getProjectSpace().getProject().getModelElementId(newEntry);
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElementToStringMapImpl clonedNewEntry = (TestElementToStringMapImpl) clonedProjectSpace.getProject()
			.getModelElement(newEntryId);

		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(keyId);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				newEntry.setKey(key);
				testElement.getElementToStringMap().add(newEntry);
				return null;
			}
		});

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				clonedNewEntry.setKey(clonedKey);
				clonedTestElement.getElementToStringMap().add(clonedNewEntry);
				return null;
			}
		});

		final Set<AbstractOperation> conflicts = getConflicts(
			getProjectSpace().getLocalChangePackage().getOperations(),
			clonedProjectSpace.getLocalChangePackage().getOperations(), getProject());

		assertTrue(conflicts.size() > 0);

	}

	/**
	 * Tests if creating map entries with the same key conflict.
	 * 
	 * @throws ChangeConflictException
	 */
	@Test
	public void testConflictCreateVSCreateMapEntryNonContainedKeySingleRefSubOpMissing() throws ChangeConflictException {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement key = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);
		addTestElement(key);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		final ModelElementId keyId = getProjectSpace().getProject().getModelElementId(key);
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(keyId);

		updateMapEntryNonContainedKey(testElement, key, "bar");
		updateMapEntryNonContainedKey(clonedTestElement, clonedKey, "quux");

		final EList<AbstractOperation> operations = getProjectSpace().getLocalChangePackage().getOperations();
		final CreateDeleteOperation createDeleteOperation = CreateDeleteOperation.class.cast(operations.get(0));
		createDeleteOperation.getSubOperations().clear();

		// expect part of the log message
		final TestLogListener logListener = new TestLogListener("Single reference sub operation of create operation");
		Platform.getLog(Platform
			.getBundle("org.eclipse.emf.emfstore.common.model")).addLogListener(logListener);

		getConflicts(
			getProjectSpace().getLocalChangePackage().getOperations(),
			clonedProjectSpace.getLocalChangePackage().getOperations(), getProject());

		assertTrue(logListener.didReceive);
	}

	/**
	 * Tests if creating map entries with the same key conflict.
	 * 
	 * @throws ChangeConflictException
	 */
	@Test
	public void testConflictCreateVSCreateMapEntryNonContainedKeyKeyIsNull() throws ChangeConflictException {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement key = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);
		addTestElement(key);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		final ModelElementId keyId = getProjectSpace().getProject().getModelElementId(key);
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(keyId);

		updateMapEntryNonContainedKey(testElement, key, "bar");
		updateMapEntryNonContainedKey(clonedTestElement, clonedKey, "quux");

		final List<AbstractOperation> operations = ModelUtil.clone(
			getProjectSpace().getLocalChangePackage().getOperations());
		final CreateDeleteOperation createDeleteOperation = CreateDeleteOperation.class.cast(operations.get(0));
		final SingleReferenceOperation singleReferenceOperation = SingleReferenceOperation.class.cast(
			createDeleteOperation.getSubOperations().get(0));
		// causes null to be returned when trying to find the key
		singleReferenceOperation.getOtherInvolvedModelElements().iterator().next().setId("foo");

		final List<AbstractOperation> operations2 = ModelUtil.clone(
			clonedProjectSpace.getLocalChangePackage().getOperations());
		final CreateDeleteOperation createDeleteOperation2 = CreateDeleteOperation.class.cast(operations2.get(0));
		final SingleReferenceOperation singleReferenceOperation2 = SingleReferenceOperation.class.cast(
			createDeleteOperation.getSubOperations().get(0));
		// causes null to be returned when trying to find the key
		singleReferenceOperation.getOtherInvolvedModelElements().iterator().next().setId("bar");

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProject().getModelElements().remove(key);
				clonedProjectSpace.getProject().getModelElements().remove(clonedKey);
				return null;
			}
		});

		// expect part of the log message
		final TestLogListener logListener = new TestLogListener("Key is null. Can not be used for conflict detection.");
		Platform.getLog(Platform
			.getBundle("org.eclipse.emf.emfstore.common.model")).addLogListener(logListener);

		getConflicts(operations, operations2, getProject());

		assertTrue(logListener.didReceive);
	}

	class TestLogListener implements ILogListener {

		private final String expectedMessage;
		private boolean didReceive;

		public TestLogListener(String expectedMessage) {
			this.expectedMessage = expectedMessage;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.ILogListener#logging(org.eclipse.core.runtime.IStatus, java.lang.String)
		 */
		public void logging(IStatus status, String plugin) {
			if (status.getMessage().contains(expectedMessage)) {
				didReceive = true;
			}
		}

		public boolean didReceiveExpectedMessage() {
			return didReceive;
		}
	}

	@Test
	public void testNonConflictingCreateVsCreate() {
		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement key = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement secondKey = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);
		addTestElement(key);
		addTestElement(secondKey);

		final ModelElementId testElementId = getProject().getModelElementId(testElement);
		final ModelElementId secondKeyId = getProject().getModelElementId(secondKey);

		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			testElementId);
		final TestElement clonedSecondKey = (TestElement) clonedProjectSpace.getProject().getModelElement(secondKeyId);

		updateMapEntryNonContainedKey(testElement, key, "foo");
		updateMapEntryNonContainedKey(clonedTestElement, clonedSecondKey, "bar");

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2, getProject());

		assertEquals(0, conflicts.size());
	}

	@Test
	public void testNonConflictingRemoveVsRemove() {
		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement key = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement secondKey = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);
		addTestElement(key);
		addTestElement(secondKey);

		final ModelElementId testElementId = getProject().getModelElementId(testElement);
		final ModelElementId secondKeyId = getProject().getModelElementId(secondKey);

		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			testElementId);
		final TestElement clonedSecondKey = (TestElement) clonedProjectSpace.getProject().getModelElement(secondKeyId);

		updateMapEntryNonContainedKey(testElement, key, "foo");
		updateMapEntryNonContainedKey(clonedTestElement, clonedSecondKey, "bar");

		clearOperations();

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getElementToStringMap().clear();
				clonedTestElement.getElementToStringMap().clear();
				return null;
			}
		});

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2, getProject());

		assertEquals(0, conflicts.size());
	}

	@Test
	public void testConflictCreateVSDeleteMapEntryNonContainedKey() throws ChangeConflictException {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement key = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);
		addTestElement(key);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		final ModelElementId keyId = getProjectSpace().getProject().getModelElementId(key);

		updateMapEntryNonContainedKey(testElement, key, "bar");
		clearOperations();
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(keyId);

		updateMapEntryNonContainedKey(testElement, key, "bar2");
		deleteMapEntryNonContainedKey(clonedTestElement, clonedKey);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2, getProject());

		assertTrue(conflicts.size() > 0);
	}

	@Test
	public void testConflictUpdateVSUpdateMapEntryNonContainedKey() {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement key = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);
		addTestElement(key);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		final ModelElementId keyId = getProjectSpace().getProject().getModelElementId(key);

		updateMapEntryNonContainedKey(testElement, key, "bar");
		clearOperations();
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(
			keyId);

		updateMapEntryNonContainedKey(testElement, key, "hello1");
		updateMapEntryNonContainedKey(clonedTestElement, clonedKey, "hello2");

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2, getProject());

		assertTrue(conflicts.size() > 0);
	}

	@Test
	public void testConflictUpdateVSDeleteMapEntryNonContainedKey() throws ChangeConflictException {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement key = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);
		addTestElement(key);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		final ModelElementId keyId = getProjectSpace().getProject().getModelElementId(key);

		updateMapEntryNonContainedKey(testElement, key, "bar");
		clearOperations();
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(
			keyId);

		updateMapEntryNonContainedKey(testElement, key, "hello1");
		deleteMapEntryNonContainedKey(clonedTestElement, clonedKey);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2, getProject());

		assertTrue(conflicts.size() > 0);
	}

	@Test
	public void testConflictDeleteVSDeleteMapEntryNonContainedKey() {

		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		final TestElement key = TestmodelFactory.eINSTANCE.createTestElement();
		addTestElement(testElement);
		addTestElement(key);

		final ModelElementId modelElementId = getProjectSpace().getProject().getModelElementId(testElement);
		final ModelElementId keyId = getProjectSpace().getProject().getModelElementId(key);

		updateMapEntryNonContainedKey(testElement, key, "bar");
		clearOperations();
		clonedProjectSpace = (ProjectSpaceBase) cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(
			keyId);

		deleteMapEntryNonContainedKey(testElement, key);
		deleteMapEntryNonContainedKey(clonedTestElement, clonedKey);

		final List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		final List<AbstractOperation> ops2 = clonedProjectSpace.getOperations();

		final Set<AbstractOperation> conflicts = getConflicts(ops1, ops2, getProject());

		assertEquals(1, conflicts.size());
	}

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

		final Set<AbstractOperation> conflicts = getConflicts(
			getProjectSpace().getLocalChangePackage().getOperations(),
			clonedProjectSpace.getLocalChangePackage().getOperations());

		assertTrue(conflicts.size() > 0);

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

		assertTrue(conflicts.size() > 0);
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

		assertTrue(conflicts.size() > 0);
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

	private void deleteMapEntryNonContainedKey(final TestElement testElement, final TestElement key) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getElementToStringMap().remove(key);
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

	private void updateMapEntryNonContainedKey(final TestElement testElement, final TestElement key, final String value) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getElementToStringMap().put(key, value);
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
		setCompareAtEnd(false);
	}

}
