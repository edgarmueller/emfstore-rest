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
package org.eclipse.emf.emfstore.client.changetracking.test.toplogy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.emfstore.client.test.common.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Delete;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests operations in n:n topologies.
 * 
 * @author chodnick
 */
public class TopologyNtoNTest extends ESTest {

	/**
	 * Add to an empty annotation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void addToEmpty() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		final TestElement mileStone = Create.testElement();

		Add.toProject(getLocalProject(), useCase);
		Add.toProject(getLocalProject(), mileStone);

		clearOperations();

		Add.toNonContainedNToM(useCase, mileStone);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;

		final List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId mileStoneId = ModelUtil.getProject(mileStone).getModelElementId(mileStone);

		assertEquals(useCaseId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), mileStoneId);
		assertEquals(op.getIndex(), 0);

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		op = (MultiReferenceOperation) subOperations.get(1);
		assertTrue(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());
		assertEquals(mileStoneId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), useCaseId);
		assertEquals(op.getIndex(), 0);

	}

	/**
	 * Add many to an empty annotation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void addManyToEmpty() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		final TestElement mileStone1 = Create.testElement();
		final TestElement mileStone2 = Create.testElement();

		Add.toProject(getLocalProject(), useCase);
		Add.toProject(getLocalProject(), mileStone1);
		Add.toProject(getLocalProject(), mileStone2);

		final TestElement[] stones = { mileStone1, mileStone2 };

		clearOperations();

		Add.toNonContainedNToM(useCase, Arrays.asList(stones));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;

		final List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId mileStone1Id = ModelUtil.getProject(mileStone1).getModelElementId(mileStone1);
		final ModelElementId mileStone2Id = ModelUtil.getProject(mileStone2).getModelElementId(mileStone2);

		for (int i = 0; i < 2; i++) {
			assertTrue(subOperations.get(i) instanceof MultiReferenceOperation);
			final MultiReferenceOperation op = (MultiReferenceOperation) subOperations.get(i);
			assertTrue(op.isAdd());
			assertEquals(1, op.getReferencedModelElements().size());
			assertEquals(TestElementFeatures.nonContainedMToN().getName(), op.getFeatureName());
			assertEquals(op.getModelElementId(), ModelUtil.getProject(stones[i]).getModelElementId(stones[i]));
			assertEquals(op.getIndex(), 0);
		}

		assertTrue(subOperations.get(2) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op = (MultiReferenceOperation) subOperations.get(2);
		assertTrue(op.isAdd());
		assertEquals(2, op.getReferencedModelElements().size());
		assertEquals(mileStone1Id, op.getReferencedModelElements().get(0));
		assertEquals(mileStone2Id, op.getReferencedModelElements().get(1));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), useCaseId);
		assertEquals(op.getIndex(), 0);
	}

	/**
	 * Add to a non-empty annotation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void addToNonEmpty() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		final TestElement mileStone = Create.testElement();
		final TestElement otherMileStone = Create.testElement();

		Add.toProject(getLocalProject(), useCase);
		Add.toProject(getLocalProject(), mileStone);
		Add.toProject(getLocalProject(), otherMileStone);

		Add.toNonContainedNToM(useCase, otherMileStone);

		clearOperations();

		Add.toNonContainedNToM(useCase, mileStone);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;

		final List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId mileStoneId = ModelUtil.getProject(mileStone).getModelElementId(mileStone);

		assertEquals(useCaseId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), mileStoneId);
		assertEquals(op.getIndex(), 0);

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		op = (MultiReferenceOperation) subOperations.get(1);
		assertTrue(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());
		assertEquals(mileStoneId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), useCaseId);
		assertEquals(op.getIndex(), 1);

	}

	/**
	 * Add many to an nonempty annotation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void addManyToNonEmpty() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		final TestElement mileStone1 = Create.testElement();
		final TestElement mileStone2 = Create.testElement();
		final TestElement otherMileStone = Create.testElement();

		Add.toProject(getLocalProject(), useCase);
		Add.toProject(getLocalProject(), mileStone1);
		Add.toProject(getLocalProject(), mileStone2);
		Add.toProject(getLocalProject(), otherMileStone);

		final TestElement[] stones = { mileStone1, mileStone2 };

		Add.toNonContainedNToM(useCase, otherMileStone);

		clearOperations();

		Add.toNonContainedNToM(useCase, Arrays.asList(stones));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId mileStone1Id = ModelUtil.getProject(mileStone1).getModelElementId(mileStone1);
		final ModelElementId mileStone2Id = ModelUtil.getProject(mileStone2).getModelElementId(mileStone2);

		final List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		for (int i = 0; i < 2; i++) {
			assertTrue(subOperations.get(i) instanceof MultiReferenceOperation);
			final MultiReferenceOperation op = (MultiReferenceOperation) subOperations.get(i);
			assertTrue(op.isAdd());
			assertEquals(1, op.getReferencedModelElements().size());
			assertEquals(TestElementFeatures.nonContainedMToN().getName(), op.getFeatureName());
			assertEquals(op.getModelElementId(), ModelUtil.getProject(stones[i]).getModelElementId(stones[i]));
			assertEquals(op.getIndex(), 0);
		}

		assertTrue(subOperations.get(2) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op = (MultiReferenceOperation) subOperations.get(2);
		assertTrue(op.isAdd());
		assertEquals(2, op.getReferencedModelElements().size());
		assertEquals(mileStone1Id, op.getReferencedModelElements().get(0));
		assertEquals(mileStone2Id, op.getReferencedModelElements().get(1));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), useCaseId);
		assertEquals(op.getIndex(), 1);

	}

	/**
	 * Remove an element to empty annotations.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void removeAndEmpty() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		final TestElement mileStone = Create.testElement();

		Add.toProject(getLocalProject(), useCase);
		Add.toProject(getLocalProject(), mileStone);
		Add.toNonContainedNToM(useCase, mileStone);

		clearOperations();

		Delete.fromNonContainedNToM(useCase, mileStone);

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		assertTrue(operations.get(0) instanceof CompositeOperation);

		operations = ((CompositeOperation) operations.get(0)).getSubOperations();

		assertTrue(operations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op = (MultiReferenceOperation) operations.get(0);
		assertFalse(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId mileStoneId = ModelUtil.getProject(mileStone).getModelElementId(mileStone);

		assertEquals(useCaseId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), mileStoneId);
		assertEquals(op.getIndex(), 0);

		assertTrue(operations.get(1) instanceof MultiReferenceOperation);
		op = (MultiReferenceOperation) operations.get(1);
		assertFalse(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());
		assertEquals(mileStoneId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), useCaseId);
		assertEquals(op.getIndex(), 0);

	}

	/**
	 * Remove an element and leave non-empty annotations.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void removePart() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		final TestElement mileStone = Create.testElement();
		final TestElement otherMileStone = Create.testElement();

		Add.toProject(getLocalProject(), useCase);
		Add.toProject(getLocalProject(), mileStone);
		Add.toProject(getLocalProject(), otherMileStone);

		Add.toNonContainedNToM(useCase, mileStone);
		Add.toNonContainedNToM(useCase, otherMileStone);

		clearOperations();

		Delete.fromNonContainedNToM(useCase, mileStone);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		assertTrue(operations.get(0) instanceof CompositeOperation);

		operations = ((CompositeOperation) operations.get(0)).getSubOperations();

		assertTrue(operations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op = (MultiReferenceOperation) operations.get(0);
		assertFalse(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId mileStoneId = ModelUtil.getProject(mileStone).getModelElementId(mileStone);

		assertEquals(useCaseId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), mileStoneId);
		assertEquals(op.getIndex(), 0);

		assertTrue(operations.get(1) instanceof MultiReferenceOperation);
		op = (MultiReferenceOperation) operations.get(1);
		assertFalse(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());
		assertEquals(mileStoneId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), useCaseId);
		assertEquals(op.getIndex(), 0);

	}

	/**
	 * Remove some element and leave empty annotations.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void removeManyAndEmpty() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		final TestElement mileStone = Create.testElement();
		final TestElement otherMileStone = Create.testElement();

		Add.toProject(getLocalProject(), useCase);
		Add.toProject(getLocalProject(), mileStone);
		Add.toProject(getLocalProject(), otherMileStone);

		Add.toNonContainedNToM(useCase, mileStone);
		Add.toNonContainedNToM(useCase, otherMileStone);

		final TestElement[] stones = { mileStone, otherMileStone };
		clearOperations();

		Delete.fromNonContainedNToM(useCase, Arrays.asList(stones));

		// if you use clear instead of explicit removal, op.getIndex() will be -1
		// useCase.getNonContained_NToM().clear();

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;

		final List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op = (MultiReferenceOperation) subOperations.get(0);
		assertFalse(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId mileStoneId = ModelUtil.getProject(mileStone).getModelElementId(mileStone);
		final ModelElementId otherMileStoneId = ModelUtil.getProject(otherMileStone).getModelElementId(otherMileStone);

		assertEquals(useCaseId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), mileStoneId);
		assertEquals(op.getIndex(), 0);

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		op = (MultiReferenceOperation) subOperations.get(1);
		assertFalse(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());
		assertEquals(useCaseId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), otherMileStoneId);
		assertEquals(op.getIndex(), 0);

		assertTrue(subOperations.get(2) instanceof MultiReferenceOperation);
		op = (MultiReferenceOperation) subOperations.get(2);
		assertFalse(op.isAdd());
		assertEquals(2, op.getReferencedModelElements().size());
		assertEquals(mileStoneId, op.getReferencedModelElements().get(0));
		assertEquals(otherMileStoneId, op.getReferencedModelElements().get(1));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), useCaseId);
		assertEquals(op.getIndex(), 0);
	}

	/**
	 * Remove some element and leave non-empty annotations.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void removeManyPart() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		final TestElement mileStone1 = Create.testElement();
		final TestElement mileStone2 = Create.testElement();
		final TestElement mileStone3 = Create.testElement();

		Add.toProject(getLocalProject(), useCase);
		Add.toProject(getLocalProject(), mileStone1);
		Add.toProject(getLocalProject(), mileStone2);
		Add.toProject(getLocalProject(), mileStone3);

		Add.toNonContainedNToM(useCase, mileStone1);
		Add.toNonContainedNToM(useCase, mileStone2);
		Add.toNonContainedNToM(useCase, mileStone3);

		final TestElement[] stones = { mileStone1, mileStone2 };
		clearOperations();

		Delete.fromNonContainedNToM(useCase, Arrays.asList(stones));

		// if you use clear instead of explicit removal, op.getIndex() will be -1
		// useCase.getNonContained_NToM().clear();

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;

		final List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		MultiReferenceOperation op = (MultiReferenceOperation) subOperations.get(0);
		assertFalse(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId mileStone1Id = ModelUtil.getProject(mileStone1).getModelElementId(mileStone1);
		final ModelElementId mileStone2Id = ModelUtil.getProject(mileStone2).getModelElementId(mileStone2);

		assertEquals(useCaseId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), mileStone1Id);
		assertEquals(op.getIndex(), 0);

		assertTrue(subOperations.get(1) instanceof MultiReferenceOperation);
		op = (MultiReferenceOperation) subOperations.get(1);
		assertFalse(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());
		assertEquals(useCaseId, op.getReferencedModelElements().get(0));
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), mileStone2Id);
		assertEquals(op.getIndex(), 0);

		assertTrue(subOperations.get(2) instanceof MultiReferenceOperation);
		op = (MultiReferenceOperation) subOperations.get(2);
		assertFalse(op.isAdd());
		assertEquals(2, op.getReferencedModelElements().size());
		assertEquals(mileStone1Id, op.getReferencedModelElements().get(0));
		assertEquals(mileStone2Id, op.getReferencedModelElements().get(1));
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), op.getFeatureName());
		assertEquals(op.getModelElementId(), useCaseId);
		assertEquals(op.getIndex(), 0);

	}

}
