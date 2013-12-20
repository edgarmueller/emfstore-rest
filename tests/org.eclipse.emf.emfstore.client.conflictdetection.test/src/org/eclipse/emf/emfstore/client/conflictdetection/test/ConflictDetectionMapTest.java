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
package org.eclipse.emf.emfstore.client.conflictdetection.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.test.common.util.TestLogListener;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.eclipse.emf.emfstore.test.model.TestmodelFactory;
import org.eclipse.emf.emfstore.test.model.impl.TestElementToStringMapImpl;
import org.eclipse.emf.emfstore.test.model.impl.TestmodelFactoryImpl;
import org.junit.Test;

/**
 * Conflict detection tests for map entries.
 * 
 * @author emueller
 */
public class ConflictDetectionMapTest extends ConflictDetectionTest {

	private static final String HELLO2 = "hello2"; //$NON-NLS-1$
	private static final String HELLO1 = "hello1"; //$NON-NLS-1$
	private static final String BAR2 = "bar2"; //$NON-NLS-1$
	private static final String KEY_IS_NULL_CAN_NOT_BE_USED_FOR_CONFLICT_DETECTION = "Key is null. Can not be used for conflict detection."; //$NON-NLS-1$
	private static final String FOO = "foo"; //$NON-NLS-1$
	private static final String ORG_ECLIPSE_EMF_EMFSTORE_COMMON_MODEL = "org.eclipse.emf.emfstore.common.model"; //$NON-NLS-1$
	private static final String SINGLE_REFERENCE_SUB_OPERATION_OF_CREATE_OPERATION = "Single reference sub operation of create operation"; //$NON-NLS-1$
	private static final String QUUX = "quux"; //$NON-NLS-1$
	private static final String BAR = "bar"; //$NON-NLS-1$

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
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(keyId);

		updateMapEntryNonContainedKey(testElement, key, BAR);
		updateMapEntryNonContainedKey(clonedTestElement, clonedKey, QUUX);

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
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
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
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(keyId);

		updateMapEntryNonContainedKey(testElement, key, BAR);
		updateMapEntryNonContainedKey(clonedTestElement, clonedKey, QUUX);

		final EList<AbstractOperation> operations = getProjectSpace().getLocalChangePackage().getOperations();
		final CreateDeleteOperation createDeleteOperation = CreateDeleteOperation.class.cast(operations.get(0));
		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				createDeleteOperation.getSubOperations().clear();
			}
		});

		// expect part of the log message
		final TestLogListener logListener = new TestLogListener(SINGLE_REFERENCE_SUB_OPERATION_OF_CREATE_OPERATION);
		Platform.getLog(Platform
			.getBundle(ORG_ECLIPSE_EMF_EMFSTORE_COMMON_MODEL)).addLogListener(logListener);

		getConflicts(
			getProjectSpace().getLocalChangePackage().getOperations(),
			clonedProjectSpace.getLocalChangePackage().getOperations(), getProject());

		assertTrue(logListener.didReceive());
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
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(keyId);

		updateMapEntryNonContainedKey(testElement, key, BAR);
		updateMapEntryNonContainedKey(clonedTestElement, clonedKey, QUUX);

		final List<AbstractOperation> operations = ModelUtil.clone(
			getProjectSpace().getLocalChangePackage().getOperations());
		final CreateDeleteOperation createDeleteOperation = CreateDeleteOperation.class.cast(operations.get(0));
		final SingleReferenceOperation singleReferenceOperation = SingleReferenceOperation.class.cast(
			createDeleteOperation.getSubOperations().get(0));
		// causes null to be returned when trying to find the key
		singleReferenceOperation.getOtherInvolvedModelElements().iterator().next().setId(FOO);

		final List<AbstractOperation> operations2 = ModelUtil.clone(
			clonedProjectSpace.getLocalChangePackage().getOperations());
		// causes null to be returned when trying to find the key
		singleReferenceOperation.getOtherInvolvedModelElements().iterator().next().setId(BAR);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProject().getModelElements().remove(key);
				clonedProjectSpace.getProject().getModelElements().remove(clonedKey);
				return null;
			}
		});

		// expect part of the log message
		final TestLogListener logListener = new TestLogListener(KEY_IS_NULL_CAN_NOT_BE_USED_FOR_CONFLICT_DETECTION);
		Platform.getLog(Platform
			.getBundle(ORG_ECLIPSE_EMF_EMFSTORE_COMMON_MODEL)).addLogListener(logListener);

		getConflicts(operations, operations2, getProject());

		assertTrue(logListener.didReceive());
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

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			testElementId);
		final TestElement clonedSecondKey = (TestElement) clonedProjectSpace.getProject().getModelElement(secondKeyId);

		updateMapEntryNonContainedKey(testElement, key, FOO);
		updateMapEntryNonContainedKey(clonedTestElement, clonedSecondKey, BAR);

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

		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			testElementId);
		final TestElement clonedSecondKey = (TestElement) clonedProjectSpace.getProject().getModelElement(secondKeyId);

		updateMapEntryNonContainedKey(testElement, key, FOO);
		updateMapEntryNonContainedKey(clonedTestElement, clonedSecondKey, BAR);

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

		updateMapEntryNonContainedKey(testElement, key, BAR);
		clearOperations();
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(keyId);

		updateMapEntryNonContainedKey(testElement, key, BAR2);
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

		updateMapEntryNonContainedKey(testElement, key, BAR);
		clearOperations();
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(
			keyId);

		updateMapEntryNonContainedKey(testElement, key, HELLO1);
		updateMapEntryNonContainedKey(clonedTestElement, clonedKey, HELLO2);

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

		updateMapEntryNonContainedKey(testElement, key, BAR);
		clearOperations();
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);
		final TestElement clonedKey = (TestElement) clonedProjectSpace.getProject().getModelElement(
			keyId);

		updateMapEntryNonContainedKey(testElement, key, HELLO1);
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

		updateMapEntryNonContainedKey(testElement, key, BAR);
		clearOperations();
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
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
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);

		updateMapEntry(testElement, FOO, BAR);
		updateMapEntry(clonedTestElement, FOO, QUUX);

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

		updateMapEntry(testElement, FOO, BAR);
		clearOperations();
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);

		updateMapEntry(testElement, FOO, BAR2);
		deleteMapEntry(clonedTestElement, FOO);

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

		updateMapEntry(testElement, FOO, BAR);
		clearOperations();
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);

		updateMapEntry(testElement, FOO, HELLO1);
		updateMapEntry(clonedTestElement, FOO, HELLO2);

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

		updateMapEntry(testElement, FOO, BAR);
		clearOperations();
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);

		updateMapEntry(testElement, FOO, HELLO1);
		deleteMapEntry(clonedTestElement, FOO);

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

		updateMapEntry(testElement, FOO, BAR);
		clearOperations();
		final ProjectSpace clonedProjectSpace = cloneProjectSpace(getProjectSpace());
		final TestElement clonedTestElement = (TestElement) clonedProjectSpace.getProject().getModelElement(
			modelElementId);

		deleteMapEntry(testElement, FOO);
		deleteMapEntry(clonedTestElement, FOO);

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
}
