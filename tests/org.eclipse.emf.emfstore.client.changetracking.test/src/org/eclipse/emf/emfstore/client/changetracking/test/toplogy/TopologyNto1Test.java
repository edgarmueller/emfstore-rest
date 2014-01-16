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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.dsl.Update;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
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
 * Tests operations in n:1 topologies.
 * 
 * @author chodnick
 */
public class TopologyNto1Test extends ESTest {

	private static final String COMPOSITE_OPERATION_EXPECTED = "composite operation expected"; //$NON-NLS-1$

	/**
	 * Set a container from null to some value.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void setContainerFromNullToValue() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement section = Create.testElement();
		final TestElement useCase = Create.testElement();

		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), useCase);

		clearOperations();

		Update.testElement(TestElementFeatures.container(), useCase, section);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;

		final List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);

		assertEquals(TestElementFeatures.containedElements().getName(), op.getFeatureName());
		assertEquals(useCaseId, op.getReferencedModelElements().get(0));
		assertEquals(op.getModelElementId(), sectionId);
		assertEquals(op.getIndex(), 0);

		assertTrue(subOperations.get(1) instanceof SingleReferenceOperation);
		final SingleReferenceOperation sop = (SingleReferenceOperation) subOperations.get(1);
		assertEquals(useCaseId, sop.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), sop.getFeatureName());
		assertNull(sop.getOldValue());
		assertEquals(sop.getNewValue(), sectionId);

	}

	/**
	 * Set a container from some value to null.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void setContainerFromValueToNull() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement section = Create.testElement();
		final TestElement useCase = Create.testElement();

		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), useCase);

		Update.testElement(TestElementFeatures.container(), useCase, section);

		assertTrue(section.getContainedElements().contains(useCase));

		clearOperations();
		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		Update.testElement(TestElementFeatures.container(), useCase, null);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		final EList<ReferenceOperation> subOperations = checkAndCast(operations.get(0), CreateDeleteOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);

		final MultiReferenceOperation op0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final SingleReferenceOperation op1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);

		assertEquals(sectionId, op0.getModelElementId());
		assertEquals(TestElementFeatures.containedElements().getName(), op0.getFeatureName());
		assertEquals(op0.getReferencedModelElements().get(0), useCaseId);

		assertEquals(useCaseId, op1.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), op1.getFeatureName());
		assertNull(op1.getNewValue());
		assertEquals(op1.getOldValue(), sectionId);

	}

	/**
	 * Set a non-containing parent from null to some value.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void setNoncontainingParentFromNullToValue() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement actor = Create.testElement();
		final TestElement useCase = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), useCase);

		assertNull(useCase.getNonContained_NTo1());

		clearOperations();

		Update.testElement(TestElementFeatures.nonContainedNTo1(), useCase, actor);

		assertSame(useCase.getNonContained_NTo1(), actor);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;

		final List<AbstractOperation> subOperations = compositeOperation.getSubOperations();

		assertTrue(subOperations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op = (MultiReferenceOperation) subOperations.get(0);
		assertTrue(op.isAdd());
		assertEquals(1, op.getReferencedModelElements().size());
		assertEquals(TestElementFeatures.nonContained1ToN().getName(), op.getFeatureName());

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);

		assertEquals(useCaseId, op.getReferencedModelElements().get(0));
		assertEquals(op.getModelElementId(), actorId);
		assertEquals(op.getIndex(), 0);

		assertTrue(subOperations.get(1) instanceof SingleReferenceOperation);
		final SingleReferenceOperation sop = (SingleReferenceOperation) subOperations.get(1);
		assertEquals(useCaseId, sop.getModelElementId());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), sop.getFeatureName());
		assertNull(sop.getOldValue());
		assertEquals(sop.getNewValue(), actorId);

	}

	/**
	 * Set a non-containing parent from some value to null.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void setNoncontainingParentFromValueToNull() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement actor = Create.testElement();
		final TestElement useCase = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), useCase);

		Update.testElement(TestElementFeatures.nonContainedNTo1(), useCase, actor);

		assertSame(useCase.getNonContained_NTo1(), actor);

		clearOperations();

		Update.testElement(TestElementFeatures.nonContainedNTo1(), useCase, null);

		assertNull(useCase.getNonContained_NTo1());

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail(COMPOSITE_OPERATION_EXPECTED);
		}

		assertEquals(2, operations.size());

		assertTrue(operations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op0 = (MultiReferenceOperation) operations.get(0);

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);

		assertEquals(actorId, op0.getModelElementId());
		assertEquals(TestElementFeatures.nonContained1ToN().getName(), op0.getFeatureName());
		assertEquals(op0.getReferencedModelElements().get(0), useCaseId);

		assertTrue(operations.get(1) instanceof SingleReferenceOperation);
		final SingleReferenceOperation op = (SingleReferenceOperation) operations.get(1);
		assertEquals(useCaseId, op.getModelElementId());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), op.getFeatureName());
		assertNull(op.getNewValue());
		assertEquals(op.getOldValue(), actorId);

	}

	/**
	 * Set a non-containing parent from some value to some.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void setNoncontainingParentFromValueToOtherValue() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement actor = Create.testElement();
		final TestElement otherTestElement = Create.testElement();
		final TestElement useCase = Create.testElement();

		Add.toProject(getLocalProject(), actor);
		Add.toProject(getLocalProject(), otherTestElement);
		Add.toProject(getLocalProject(), useCase);

		Update.testElement(TestElementFeatures.nonContainedNTo1(), useCase, actor);

		assertSame(useCase.getNonContained_NTo1(), actor);

		clearOperations();

		Update.testElement(TestElementFeatures.nonContainedNTo1(), useCase, otherTestElement);

		assertSame(otherTestElement, useCase.getNonContained_NTo1());

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());

		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail(COMPOSITE_OPERATION_EXPECTED);
		}

		assertEquals(3, operations.size());

		assertTrue(operations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op0 = (MultiReferenceOperation) operations.get(0);

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId actorId = ModelUtil.getProject(actor).getModelElementId(actor);
		final ModelElementId otherTestElementId = ModelUtil.getProject(otherTestElement).getModelElementId(
			otherTestElement);

		assertEquals(actorId, op0.getModelElementId());
		assertEquals(TestElementFeatures.nonContained1ToN().getName(), op0.getFeatureName());
		assertEquals(op0.getReferencedModelElements().get(0), useCaseId);
		assertFalse(op0.isAdd());

		assertTrue(operations.get(1) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op1 = (MultiReferenceOperation) operations.get(1);
		assertEquals(otherTestElementId, op1.getModelElementId());
		assertEquals(TestElementFeatures.nonContained1ToN().getName(), op1.getFeatureName());
		assertEquals(op1.getReferencedModelElements().get(0), useCaseId);
		assertTrue(op1.isAdd());

		assertTrue(operations.get(2) instanceof SingleReferenceOperation);
		final SingleReferenceOperation op = (SingleReferenceOperation) operations.get(2);
		assertEquals(useCaseId, op.getModelElementId());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), op.getFeatureName());
		assertEquals(op.getNewValue(), otherTestElementId);
		assertEquals(op.getOldValue(), actorId);
	}

	/**
	 * Set a container from some value to some other value on same feature though.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */

	@Test
	public void setContainerFromValueToOtherValueSameFeature() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement section1 = Create.testElement();
		final TestElement section2 = Create.testElement();
		final TestElement useCase = Create.testElement();

		Add.toProject(getLocalProject(), section1);
		Add.toProject(getLocalProject(), section2);
		Add.toProject(getLocalProject(), useCase);

		Update.testElement(TestElementFeatures.container(), useCase, section1);

		assertTrue(section1.getContainedElements().contains(useCase));

		clearOperations();

		Update.testElement(TestElementFeatures.container(), useCase, section2);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());

		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail(COMPOSITE_OPERATION_EXPECTED);
		}

		assertEquals(3, operations.size());

		assertTrue(operations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op0 = (MultiReferenceOperation) operations.get(0);

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId section1Id = ModelUtil.getProject(section1).getModelElementId(section1);
		final ModelElementId section2Id = ModelUtil.getProject(section2).getModelElementId(section2);

		assertEquals(section1Id, op0.getModelElementId());
		assertEquals(TestElementFeatures.containedElements().getName(), op0.getFeatureName());
		assertEquals(op0.getReferencedModelElements().get(0), useCaseId);
		assertFalse(op0.isAdd());

		assertTrue(operations.get(1) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op1 = (MultiReferenceOperation) operations.get(1);
		assertEquals(section2Id, op1.getModelElementId());
		assertEquals(TestElementFeatures.containedElements().getName(), op1.getFeatureName());
		assertEquals(op1.getReferencedModelElements().get(0), useCaseId);
		assertTrue(op1.isAdd());

		assertTrue(operations.get(2) instanceof SingleReferenceOperation);
		final SingleReferenceOperation op = (SingleReferenceOperation) operations.get(2);
		assertEquals(useCaseId, op.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), op.getFeatureName());
		assertEquals(op.getNewValue(), section2Id);
		assertEquals(op.getOldValue(), section1Id);

	}

	/**
	 * Set a container from some value to some other value on different features though.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */

	@Test
	public void setContainerFromValueToOtherValueDifferentFeatureN() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement section = Create.testElement();
		final TestElement pack = Create.testElement();
		final TestElement br = Create.testElement();

		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), pack);
		Add.toProject(getLocalProject(), br);

		Update.testElement(TestElementFeatures.container(), br, section);

		assertTrue(section.getContainedElements().contains(br));

		clearOperations();

		Update.testElement(TestElementFeatures.container2(), br, pack);

		assertFalse(section.getContainedElements().contains(br));
		assertTrue(pack.getContainedElements2().contains(br));

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail(COMPOSITE_OPERATION_EXPECTED);
		}

		assertEquals(4, operations.size());

		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);
		final ModelElementId packId = ModelUtil.getProject(pack).getModelElementId(pack);
		final ModelElementId brId = ModelUtil.getProject(br).getModelElementId(br);

		assertTrue(operations.get(0) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op0 = (MultiReferenceOperation) operations.get(0);
		assertEquals(sectionId, op0.getModelElementId());
		assertEquals(TestElementFeatures.containedElements().getName(), op0.getFeatureName());
		assertEquals(op0.getReferencedModelElements().get(0), brId);

		assertTrue(operations.get(1) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op1 = (MultiReferenceOperation) operations.get(1);
		assertEquals(packId, op1.getModelElementId());
		assertEquals(TestElementFeatures.containedElements2().getName(), op1.getFeatureName());
		assertEquals(op1.getReferencedModelElements().get(0), brId);

		assertTrue(operations.get(2) instanceof SingleReferenceOperation);
		final SingleReferenceOperation op2 = (SingleReferenceOperation) operations.get(2);
		assertEquals(brId, op2.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), op2.getFeatureName());
		assertNull(op2.getNewValue());
		assertEquals(op2.getOldValue(), sectionId);

		assertTrue(operations.get(3) instanceof SingleReferenceOperation);
		final SingleReferenceOperation op3 = (SingleReferenceOperation) operations.get(3);
		assertEquals(brId, op3.getModelElementId());
		assertEquals(TestElementFeatures.container2().getName(), op3.getFeatureName());
		assertNull(op3.getOldValue());
		assertEquals(op3.getNewValue(), packId);

	}

	/**
	 * Set a container from some value to some other value on different features though.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */

	@Test
	public void setContainerFromValueToOtherValueDifferentFeature1() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement section = Create.testElement();
		final TestElement solution = Create.testElement();

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), section);
		Add.toProject(getLocalProject(), solution);

		Update.testElement(TestElementFeatures.containedElement(), issue, solution);

		clearOperations();

		Update.testElement(TestElementFeatures.container(), solution, section);

		assertTrue(section.getContainedElements().contains(solution));
		assertNull(issue.getContainedElement());

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail(COMPOSITE_OPERATION_EXPECTED);
		}

		assertEquals(4, operations.size());

		assertTrue(operations.get(0) instanceof SingleReferenceOperation);
		final SingleReferenceOperation op0 = (SingleReferenceOperation) operations.get(0);

		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);
		final ModelElementId issueId = ModelUtil.getProject(issue).getModelElementId(issue);
		final ModelElementId solutionId = ModelUtil.getProject(solution).getModelElementId(solution);

		assertEquals(issueId, op0.getModelElementId());
		assertEquals(TestElementFeatures.containedElement().getName(), op0.getFeatureName());
		assertEquals(op0.getOldValue(), solutionId);
		assertNull(op0.getNewValue());

		assertTrue(operations.get(1) instanceof MultiReferenceOperation);
		final MultiReferenceOperation op1 = (MultiReferenceOperation) operations.get(1);
		assertEquals(sectionId, op1.getModelElementId());
		assertEquals(TestElementFeatures.containedElements().getName(), op1.getFeatureName());
		assertEquals(op1.getReferencedModelElements().get(0), solutionId);

		assertTrue(operations.get(2) instanceof SingleReferenceOperation);
		final SingleReferenceOperation op2 = (SingleReferenceOperation) operations.get(2);
		assertEquals(solutionId, op2.getModelElementId());
		assertEquals(TestElementFeatures.srefContainer().getName(), op2.getFeatureName());
		assertEquals(op2.getOldValue(), issueId);
		assertNull(op2.getNewValue());

		assertTrue(operations.get(3) instanceof SingleReferenceOperation);
		final SingleReferenceOperation op3 = (SingleReferenceOperation) operations.get(3);
		assertEquals(solutionId, op3.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), op3.getFeatureName());
		assertEquals(op3.getNewValue(), sectionId);
		assertNull(op3.getOldValue());

	}

}
