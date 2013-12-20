/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
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
import org.eclipse.emf.emfstore.client.test.common.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Update;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
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
 * Tests operations in 1:1 topologies.
 * 
 * @author chodnick
 * @author emueller
 */
public class Topology1to1Test extends ESTest {

	/**
	 * Change an containment attribute from null to some reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentNullToValueNotContainedAlreadyOperateOnParent() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement solution = Create.testElement();

		assertEquals(issue.getContainedElement(), null);

		ProjectUtil.addElement(getProjectSpace().toAPI(), issue);
		ProjectUtil.addElement(getProjectSpace().toAPI(), solution);

		final ModelElementId srefContainerId = getProject().getModelElementId(issue);
		final ModelElementId solutionId = getProject().getModelElementId(solution);

		clearOperations();

		Update.testElement(TestElementFeatures.containedElement(), issue, solution);
		assertSame(solution, issue.getContainedElement());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		final List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(2, subOperations.size());

		AbstractOperation op = subOperations.get(0);
		assertEquals(true, op instanceof SingleReferenceOperation);
		final SingleReferenceOperation op0 = (SingleReferenceOperation) op;
		assertEquals(null, op0.getOldValue());
		assertEquals(srefContainerId, op0.getNewValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), op0.getFeatureName());
		assertEquals(solutionId, op0.getModelElementId());

		op = subOperations.get(1);
		assertEquals(true, op instanceof SingleReferenceOperation);
		final SingleReferenceOperation op1 = (SingleReferenceOperation) op;
		assertEquals(null, op1.getOldValue());
		assertEquals(solutionId, op1.getNewValue());
		assertEquals(TestElementFeatures.containedElement().getName(), op1.getFeatureName());
		assertEquals(srefContainerId, op1.getModelElementId());
	}

	/**
	 * Change an containment attribute from null to some reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentNullToValueNotContainedAlreadyOperateOnChild() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement solution = Create.testElement();

		assertNull(issue.getContainedElement());

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), solution);

		clearOperations();

		Update.testElement(TestElementFeatures.srefContainer(), solution, issue);

		assertSame(solution, issue.getContainedElement());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);

		final List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();

		assertEquals(2, subOperations.size());

		final ModelElementId solutionId = ModelUtil.getProject(solution).getModelElementId(solution);
		final ModelElementId srefContainerId = ModelUtil.getProject(issue).getModelElementId(issue);

		AbstractOperation op = subOperations.get(0);
		assertEquals(true, op instanceof SingleReferenceOperation);
		final SingleReferenceOperation op0 = (SingleReferenceOperation) op;
		assertEquals(null, op0.getOldValue());
		assertEquals(solutionId, op0.getNewValue());
		assertEquals(TestElementFeatures.containedElement().getName(), op0.getFeatureName());
		assertEquals(srefContainerId, op0.getModelElementId());

		op = subOperations.get(1);
		assertEquals(true, op instanceof SingleReferenceOperation);
		final SingleReferenceOperation op1 = (SingleReferenceOperation) op;
		assertEquals(null, op1.getOldValue());
		assertEquals(srefContainerId, op1.getNewValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), op1.getFeatureName());
		assertEquals(solutionId, op1.getModelElementId());
	}

	/**
	 * Change an containment attribute from some reference to some other reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentValueToOtherValueNotContainedAlreadyOperateOnParent() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement solutionOld = Create.testElement();
		final TestElement solutionNew = Create.testElement();

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), solutionOld);
		Add.toProject(getLocalProject(), solutionNew);

		Update.testElement(
			TestElementFeatures.containedElement(),
			issue, solutionOld);

		assertEquals(issue.getContainedElement(), solutionOld);

		clearOperations();

		// fetch id here, before oldTestElement is removed from project
		final ModelElementId solutionOldId = ModelUtil.getProject(solutionOld).getModelElementId(solutionOld);

		Update.testElement(TestElementFeatures.containedElement(), issue, solutionNew);

		assertSame(solutionNew, issue.getContainedElement());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(2, operations.size());
		final List<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();

		assertEquals(3, subOperations.size());

		final ModelElementId solutionNewId = ModelUtil.getProject(solutionNew).getModelElementId(solutionNew);
		final ModelElementId srefContainerId = ModelUtil.getProject(issue).getModelElementId(issue);

		AbstractOperation op = subOperations.get(0);
		assertEquals(true, op instanceof SingleReferenceOperation);
		final SingleReferenceOperation op0 = (SingleReferenceOperation) op;
		assertEquals(srefContainerId, op0.getOldValue());
		assertEquals(null, op0.getNewValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), op0.getFeatureName());
		assertEquals(solutionOldId, op0.getModelElementId());

		op = subOperations.get(1);
		assertEquals(true, op instanceof SingleReferenceOperation);
		final SingleReferenceOperation op1 = (SingleReferenceOperation) op;
		assertEquals(null, op1.getOldValue());
		assertEquals(srefContainerId, op1.getNewValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), op1.getFeatureName());
		assertEquals(solutionNewId, op1.getModelElementId());

		op = subOperations.get(2);
		assertEquals(true, op instanceof SingleReferenceOperation);
		final SingleReferenceOperation op2 = (SingleReferenceOperation) op;
		assertEquals(solutionOldId, op2.getOldValue());
		assertEquals(solutionNewId, op2.getNewValue());
		assertEquals(TestElementFeatures.containedElement().getName(), op2.getFeatureName());
		assertEquals(srefContainerId, op2.getModelElementId());
	}

	/**
	 * Change an containment attribute from some reference to some other reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentValueToOtherValueNotContainedAlreadyOperateOnChild() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement solutionOld = Create.testElement();
		final TestElement solutionNew = Create.testElement();

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), solutionOld);
		Add.toProject(getLocalProject(), solutionNew);

		final ModelElementId solutionOldId = ModelUtil.getProject(solutionOld).getModelElementId(solutionOld);

		Update.testElement(TestElementFeatures.containedElement(), issue, solutionOld);

		assertEquals(issue.getContainedElement(), solutionOld);

		clearOperations();

		Update.testElement(TestElementFeatures.srefContainer(), solutionNew, issue);
		assertSame(solutionNew, issue.getContainedElement());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(2, operations.size());
		final EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		checkAndCast(operations.get(1), CreateDeleteOperation.class);

		// please note: the perspective (operation called on containee) implies the following EMF notifications:

		// OldTestElement: SET srefContainer from srefContainer to null
		// TestElement: SET solution from oldTestElement to newTestElement
		// NewTestElement: SET srefContainer from null to srefContainer
		//
		// Since we are operating on newTestElement we expect an operation :
		// solutionNew: got new TestElement
		//
		// we need to preserve the fact, that "oldTestElement" was the former solution of "srefContainer"
		// following the "last bidirectional message wins" rule, the first message has to
		// go away, since it is the bidirectional of the second message

		// refactor: bidirectional filter is disabled

		assertEquals(3, subOperations.size());

		final ModelElementId solutionNewId = ModelUtil.getProject(solutionNew).getModelElementId(solutionNew);
		final ModelElementId srefContainerId = ModelUtil.getProject(issue).getModelElementId(issue);

		final SingleReferenceOperation refOp0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);

		assertEquals(solutionOldId, refOp0.getModelElementId());
		assertEquals(srefContainerId, refOp0.getOldValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp0.getFeatureName());
		assertNull(refOp0.getNewValue());

		assertEquals(srefContainerId, refOp1.getModelElementId());
		assertEquals(solutionOldId, refOp1.getOldValue());
		assertEquals(solutionNewId, refOp1.getNewValue());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp1.getFeatureName());

		assertEquals(solutionNewId, refOp2.getModelElementId());
		assertEquals(srefContainerId, refOp2.getNewValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp2.getFeatureName());
		assertNull(refOp2.getOldValue());

	}

	/**
	 * Change an containment attribute from some reference to some other reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentValueToOtherValueContainedAlready1OperateOnParent() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue1 = Create.testElement();
		final TestElement issue2 = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement solution2 = Create.testElement();

		Add.toProject(getLocalProject(), issue1);
		Add.toProject(getLocalProject(), issue2);
		Add.toProject(getLocalProject(), solution1);
		Add.toProject(getLocalProject(), solution2);

		Update.testElement(TestElementFeatures.containedElement(), issue1, solution1);
		Update.testElement(TestElementFeatures.containedElement(), issue2, solution2);

		assertEquals(issue1.getContainedElement(), solution1);
		assertEquals(issue2.getContainedElement(), solution2);

		final ModelElementId solution1Id = ModelUtil.getProject(solution1).getModelElementId(solution1);

		clearOperations();

		Update.testElement(TestElementFeatures.containedElement(), issue1, solution2);

		assertSame(solution2, issue1.getContainedElement());
		assertNull(issue2.getContainedElement());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(2, operations.size());
		final EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		checkAndCast(operations.get(1), CreateDeleteOperation.class);

		assertEquals(4, subOperations.size());
		final SingleReferenceOperation refOp1 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp2 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp3 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp4 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);

		// please note: 2 ops are necessary, this is because the oldvalues are necessary for
		// the ops to be reversible! we need to track the parent of solution 2!
		// 4 due to refactoring

		final ModelElementId solution2Id = ModelUtil.getProject(solution2).getModelElementId(solution2);
		final ModelElementId srefContainer1Id = ModelUtil.getProject(issue1).getModelElementId(issue1);
		final ModelElementId srefContainer2Id = ModelUtil.getProject(issue2).getModelElementId(issue2);

		assertEquals(srefContainer1Id, refOp1.getOldValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp1.getFeatureName());
		assertEquals(solution1Id, refOp1.getModelElementId());
		assertNull(refOp1.getNewValue());

		assertEquals(solution2Id, refOp2.getOldValue());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp2.getFeatureName());
		assertEquals(srefContainer2Id, refOp2.getModelElementId());
		assertNull(refOp2.getNewValue());

		assertEquals(srefContainer2Id, refOp3.getOldValue());
		assertEquals(srefContainer1Id, refOp3.getNewValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp3.getFeatureName());
		assertEquals(solution2Id, refOp3.getModelElementId());

		assertEquals(solution1Id, refOp4.getOldValue());
		assertEquals(solution2Id, refOp4.getNewValue());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp4.getFeatureName());
		assertEquals(srefContainer1Id, refOp4.getModelElementId());
	}

	/**
	 * Change an containment attribute from some reference to some other reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentValueToOtherValueContainedAlready1OperateOnChild() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue1 = Create.testElement();
		final TestElement issue2 = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement solution2 = Create.testElement();

		Add.toProject(getLocalProject(), issue1);
		Add.toProject(getLocalProject(), issue2);
		Add.toProject(getLocalProject(), solution1);
		Add.toProject(getLocalProject(), solution2);

		Update.testElement(TestElementFeatures.containedElement(), issue1, solution1);
		Update.testElement(TestElementFeatures.containedElement(), issue2, solution2);

		assertEquals(issue1.getContainedElement(), solution1);
		assertEquals(issue2.getContainedElement(), solution2);

		clearOperations();

		final ModelElementId solution1Id = ModelUtil.getProject(solution1).getModelElementId(solution1);

		Update.testElement(TestElementFeatures.srefContainer(), solution2, issue1);

		assertSame(solution2, issue1.getContainedElement());
		assertNull(issue2.getContainedElement());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(2, operations.size());
		final EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		checkAndCast(operations.get(1), CreateDeleteOperation.class);
		assertEquals(4, subOperations.size());

		final SingleReferenceOperation refOp1 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp2 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp3 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp4 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);

		// please note: 2 ops are necessary, this is because the oldvalues are necessary for
		// the ops to be reversible! we need to track the parent of solution 2!
		// 4 due to refactoring

		final ModelElementId solution2Id = ModelUtil.getProject(solution2).getModelElementId(solution2);
		final ModelElementId srefContainer1Id = ModelUtil.getProject(issue1).getModelElementId(issue1);
		final ModelElementId srefContainer2Id = ModelUtil.getProject(issue2).getModelElementId(issue2);

		assertEquals(solution2Id, refOp1.getOldValue());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp1.getFeatureName());
		assertEquals(srefContainer2Id, refOp1.getModelElementId());
		assertNull(refOp1.getNewValue());

		assertEquals(srefContainer1Id, refOp2.getOldValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp2.getFeatureName());
		assertEquals(solution1Id, refOp2.getModelElementId());
		assertNull(refOp2.getNewValue());

		assertEquals(solution1Id, refOp3.getOldValue());
		assertEquals(solution2Id, refOp3.getNewValue());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp3.getFeatureName());
		assertEquals(srefContainer1Id, refOp3.getModelElementId());

		assertEquals(srefContainer2Id, refOp4.getOldValue());
		assertEquals(srefContainer1Id, refOp4.getNewValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp4.getFeatureName());
		assertEquals(solution2Id, refOp4.getModelElementId());
	}

	/**
	 * Change an containment attribute from some reference to some other reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentNullToOtherValueContainedAlready1OperateOnChild() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue1 = Create.testElement();
		final TestElement issue2 = Create.testElement();
		final TestElement solution = Create.testElement();

		Add.toProject(getLocalProject(), issue1);
		Add.toProject(getLocalProject(), issue2);
		Add.toProject(getLocalProject(), solution);

		Update.testElement(TestElementFeatures.containedElement(), issue1, solution);
		assertEquals(issue1.getContainedElement(), solution);

		clearOperations();

		Update.testElement(TestElementFeatures.srefContainer(), solution, issue2);

		assertNull(issue1.getContainedElement());
		assertSame(issue2.getContainedElement(), solution);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof CompositeOperation);
		final List<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();
		assertEquals(3, subOperations.size());

		final ModelElementId solutionId = ModelUtil.getProject(solution).getModelElementId(solution);
		final ModelElementId srefContainer1Id = ModelUtil.getProject(issue1).getModelElementId(issue1);
		final ModelElementId srefContainer2Id = ModelUtil.getProject(issue2).getModelElementId(issue2);

		AbstractOperation op = subOperations.get(0);
		assertEquals(true, op instanceof SingleReferenceOperation);
		final SingleReferenceOperation op0 = (SingleReferenceOperation) op;
		assertEquals(solutionId, op0.getOldValue());
		assertEquals(null, op0.getNewValue());
		assertEquals(TestElementFeatures.containedElement().getName(), op0.getFeatureName());
		assertEquals(srefContainer1Id, op0.getModelElementId());

		op = subOperations.get(1);
		assertEquals(true, op instanceof SingleReferenceOperation);
		final SingleReferenceOperation op1 = (SingleReferenceOperation) op;
		assertEquals(null, op1.getOldValue());
		assertEquals(solutionId, op1.getNewValue());
		assertEquals(TestElementFeatures.containedElement().getName(), op1.getFeatureName());
		assertEquals(srefContainer2Id, op1.getModelElementId());

		op = subOperations.get(2);
		assertTrue(op instanceof SingleReferenceOperation);
		final SingleReferenceOperation op2 = (SingleReferenceOperation) op;
		assertEquals(solutionId, op2.getModelElementId());
		assertEquals(TestElementFeatures.srefContainer().getName(), op2.getFeatureName());
		assertEquals(srefContainer1Id, op2.getOldValue());
		assertEquals(srefContainer2Id, op2.getNewValue());
	}

	/**
	 * Change an containment attribute from some reference to some other reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentNullToOtherValueContainedAlready1OperateOnParent() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue1 = Create.testElement();
		final TestElement issue2 = Create.testElement();
		final TestElement solution = Create.testElement();

		Add.toProject(getLocalProject(), issue1);
		Add.toProject(getLocalProject(), issue2);
		Add.toProject(getLocalProject(), solution);

		Update.testElement(TestElementFeatures.containedElement(), issue1, solution);

		assertEquals(issue1.getContainedElement(), solution);

		clearOperations();
		Update.testElement(TestElementFeatures.containedElement(), issue2, solution);

		assertNull(issue1.getContainedElement());
		assertSame(issue2.getContainedElement(), solution);

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail("composite operation expected"); //$NON-NLS-1$
		}

		assertEquals(3, operations.size());

		final AbstractOperation op0 = operations.get(0);
		assertTrue(op0 instanceof SingleReferenceOperation);
		final SingleReferenceOperation refOp0 = (SingleReferenceOperation) op0;

		final ModelElementId solutionId = ModelUtil.getProject(solution).getModelElementId(solution);
		final ModelElementId srefContainer1Id = ModelUtil.getProject(issue1).getModelElementId(issue1);
		final ModelElementId srefContainer2Id = ModelUtil.getProject(issue2).getModelElementId(issue2);

		assertEquals(srefContainer1Id, refOp0.getModelElementId());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp0.getFeatureName());
		assertEquals(solutionId, refOp0.getOldValue());
		assertNull(refOp0.getNewValue());

		final AbstractOperation op1 = operations.get(1);
		assertTrue(op1 instanceof SingleReferenceOperation);
		final SingleReferenceOperation refOp1 = (SingleReferenceOperation) op1;
		assertEquals(solutionId, refOp1.getModelElementId());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp1.getFeatureName());
		assertEquals(srefContainer1Id, refOp1.getOldValue());
		assertEquals(srefContainer2Id, refOp1.getNewValue());

		final AbstractOperation op2 = operations.get(2);
		assertTrue(op2 instanceof SingleReferenceOperation);
		final SingleReferenceOperation refOp2 = (SingleReferenceOperation) op2;
		assertEquals(srefContainer2Id, refOp2.getModelElementId());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp2.getFeatureName());
		assertNull(refOp2.getOldValue());
		assertEquals(solutionId, refOp2.getNewValue());

	}

	/**
	 * Change an containment attribute from some reference to some other reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentValueToOtherValueContainedAlreadyNOperateOnParent() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement leafSection = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement solution2 = Create.testElement();

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), leafSection);
		Add.toProject(getLocalProject(), solution1);
		Add.toProject(getLocalProject(), solution2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				leafSection.getContainedElements().add(solution2);
				issue.setContainedElement(solution1);
			}
		}.run(false);

		assertEquals(issue.getContainedElement(), solution1);
		assertTrue(leafSection.getContainedElements().contains(solution2));

		clearOperations();
		final ModelElementId solution1Id = ModelUtil.getProject(solution1).getModelElementId(solution1);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				issue.setContainedElement(solution2);
			}
		}.run(false);

		assertSame(solution2, issue.getContainedElement());
		assertTrue(leafSection.getContainedElements().isEmpty());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(2, operations.size());
		final EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		checkAndCast(operations.get(1), CreateDeleteOperation.class);
		assertEquals(5, subOperations.size());

		final SingleReferenceOperation refOp0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final MultiReferenceOperation refOp1 = checkAndCast(subOperations.get(1), MultiReferenceOperation.class);
		final SingleReferenceOperation refOp2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);

		// please note: 3 ops are necessary, this is because the oldvalues are necessary for
		// the ops to be reversible! we also need track the index of srefContainer 2 inside its former parent!

		final ModelElementId solution2Id = ModelUtil.getProject(solution2).getModelElementId(solution2);
		final ModelElementId srefContainerId = ModelUtil.getProject(issue).getModelElementId(issue);
		final ModelElementId leafSectionId = ModelUtil.getProject(leafSection).getModelElementId(leafSection);

		assertEquals(srefContainerId, refOp0.getOldValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp0.getFeatureName());
		assertEquals(solution1Id, refOp0.getModelElementId());
		assertNull(refOp0.getNewValue());

		// leaf section annouces loss of solution2 at index 0
		assertEquals(TestElementFeatures.containedElements().getName(), refOp1.getFeatureName());
		assertEquals(leafSectionId, refOp1.getModelElementId());
		assertEquals(1, refOp1.getReferencedModelElements().size());
		assertEquals(solution2Id, refOp1.getReferencedModelElements().get(0));
		assertFalse(refOp1.isAdd());

		assertEquals(leafSectionId, refOp2.getOldValue());
		assertEquals(TestElementFeatures.container().getName(), refOp2.getFeatureName());
		assertEquals(solution2Id, refOp2.getModelElementId());
		assertNull(refOp2.getNewValue());

		assertEquals(solution2Id, refOp3.getModelElementId());
		assertEquals(srefContainerId, refOp3.getNewValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp3.getFeatureName());
		assertNull(refOp3.getOldValue());

		// the srefContainer 1 is getting its new child
		assertEquals(solution1Id, refOp4.getOldValue());
		assertEquals(solution2Id, refOp4.getNewValue());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp4.getFeatureName());
		assertEquals(srefContainerId, refOp4.getModelElementId());
	}

	/**
	 * Change an containment attribute from some reference to some other reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentValueToOtherValueContainedAlreadyNOperateOnChild() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement leafSection = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement solution2 = Create.testElement();

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), leafSection);
		Add.toProject(getLocalProject(), solution1);
		Add.toProject(getLocalProject(), solution2);

		Add.toContainedElements(leafSection, solution2);

		Update.testElement(TestElementFeatures.containedElement(), issue, solution1);

		assertEquals(issue.getContainedElement(), solution1);
		assertTrue(leafSection.getContainedElements().contains(solution2));

		clearOperations();

		final ModelElementId solution1Id = ModelUtil.getProject(solution1).getModelElementId(solution1);

		Update.testElement(TestElementFeatures.srefContainer(), solution2, issue);

		assertSame(solution2, issue.getContainedElement());
		assertTrue(leafSection.getContainedElements().isEmpty());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(2, operations.size());
		final EList<AbstractOperation> subOperations = checkAndCast(operations.get(0), CompositeOperation.class)
			.getSubOperations();
		checkAndCast(operations.get(1), CreateDeleteOperation.class);
		assertEquals(5, subOperations.size());

		final MultiReferenceOperation refOp0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		final SingleReferenceOperation refOp1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp2 = checkAndCast(subOperations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp3 = checkAndCast(subOperations.get(3), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp4 = checkAndCast(subOperations.get(4), SingleReferenceOperation.class);

		// due to refactoring additional operation

		// please note: 4 ops are necessary from this perspective
		// 0. index inside old parent of solution 2 must be tracked (index inside leafsection.modelElements)
		// 1. old solution of srefContainer must be tracked
		// 2. old parent of solution 2 must be tracked (the leafsection)
		// 3. solution2 must announce its new srefContainer

		final ModelElementId solution2Id = ModelUtil.getProject(solution2).getModelElementId(solution2);
		final ModelElementId srefContainerId = ModelUtil.getProject(issue).getModelElementId(issue);
		final ModelElementId leafSectionId = ModelUtil.getProject(leafSection).getModelElementId(leafSection);

		// leaf section annouces loss of solution2 at index 0
		assertEquals(TestElementFeatures.containedElements().getName(), refOp0.getFeatureName());
		assertEquals(leafSectionId, refOp0.getModelElementId());
		assertEquals(1, refOp0.getReferencedModelElements().size());
		assertEquals(solution2Id, refOp0.getReferencedModelElements().get(0));
		assertFalse(refOp0.isAdd());

		assertEquals(solution1Id, refOp1.getModelElementId());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp1.getFeatureName());
		assertEquals(srefContainerId, refOp1.getOldValue());
		assertNull(refOp1.getNewValue());

		assertEquals(srefContainerId, refOp2.getModelElementId());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp2.getFeatureName());
		assertEquals(solution1Id, refOp2.getOldValue());
		assertEquals(solution2Id, refOp2.getNewValue());

		assertEquals(solution2Id, refOp3.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), refOp3.getFeatureName());
		assertEquals(leafSectionId, refOp3.getOldValue());
		assertNull(refOp3.getNewValue());

		assertEquals(solution2Id, refOp4.getModelElementId());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp4.getFeatureName());
		assertEquals(srefContainerId, refOp4.getNewValue());
		assertNull(refOp4.getOldValue());

	}

	/**
	 * Change an containment attribute from some reference to some other reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentNullToValueContainedAlreadyNOperateOnParent() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement leafSection = Create.testElement();
		final TestElement solution = Create.testElement();

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), leafSection);
		Add.toProject(getLocalProject(), solution);

		Add.toContainedElements(leafSection, solution);
		assertTrue(leafSection.getContainedElements().contains(solution));

		clearOperations();

		Update.testElement(TestElementFeatures.containedElement(), issue, solution);

		assertSame(solution, issue.getContainedElement());
		assertTrue(leafSection.getContainedElements().isEmpty());

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail("composite operation expected"); //$NON-NLS-1$
		}

		assertEquals(4, operations.size());

		final MultiReferenceOperation refOp0 = checkAndCast(operations.get(0), MultiReferenceOperation.class);
		final SingleReferenceOperation refOp1 = checkAndCast(operations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp2 = checkAndCast(operations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp3 = checkAndCast(operations.get(3), SingleReferenceOperation.class);

		// due to refactoring one addtional operation

		// please note: 3 ops are necessary, this is because the oldvalues are necessary for
		// the ops to be reversible! we also need track the index of srefContainer 2 inside its former parent!

		final ModelElementId solutionId = ModelUtil.getProject(solution).getModelElementId(solution);
		final ModelElementId srefContainerId = ModelUtil.getProject(issue).getModelElementId(issue);
		final ModelElementId leafSectionId = ModelUtil.getProject(leafSection).getModelElementId(leafSection);

		// leaf section announces loss of solution2 at index 0
		assertEquals(TestElementFeatures.containedElements().getName(), refOp0.getFeatureName());
		assertEquals(solutionId, refOp0.getReferencedModelElements().get(0));
		assertEquals(leafSectionId, refOp0.getModelElementId());
		assertEquals(1, refOp0.getReferencedModelElements().size());
		assertFalse(refOp0.isAdd());

		// first solution is losing its old leaf section parent
		assertEquals(TestElementFeatures.container().getName(), refOp1.getFeatureName());
		assertEquals(solutionId, refOp1.getModelElementId());
		assertEquals(leafSectionId, refOp1.getOldValue());
		assertNull(refOp1.getNewValue());

		assertEquals(TestElementFeatures.srefContainer().getName(), refOp2.getFeatureName());
		assertEquals(solutionId, refOp2.getModelElementId());
		assertEquals(srefContainerId, refOp2.getNewValue());
		assertNull(refOp2.getOldValue());

		// second the solution is getting its new srefContainer
		assertEquals(TestElementFeatures.containedElement().getName(), refOp3.getFeatureName());
		assertEquals(srefContainerId, refOp3.getModelElementId());
		assertEquals(solutionId, refOp3.getNewValue());
		assertNull(refOp3.getOldValue());

	}

	/**
	 * Change an containment attribute from some reference to some other reference, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentNullToValueContainedAlreadyNOperateOnChild() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement leafSection = Create.testElement();
		final TestElement solution = Create.testElement();

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), leafSection);
		Add.toProject(getLocalProject(), solution);

		Add.toContainedElements(leafSection, solution);

		assertTrue(leafSection.getContainedElements().contains(solution));

		clearOperations();

		Update.testElement(TestElementFeatures.srefContainer(), solution, issue);

		assertSame(solution, issue.getContainedElement());
		assertTrue(leafSection.getContainedElements().isEmpty());

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a composite operation here
		assertEquals(1, operations.size());
		if (operations.get(0) instanceof CompositeOperation) {
			operations = ((CompositeOperation) operations.get(0)).getSubOperations();
		} else {
			fail("composite operation expected"); //$NON-NLS-1$
		}

		assertEquals(4, operations.size());

		final MultiReferenceOperation refOp0 = checkAndCast(operations.get(0), MultiReferenceOperation.class);
		final SingleReferenceOperation refOp1 = checkAndCast(operations.get(1), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp2 = checkAndCast(operations.get(2), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp3 = checkAndCast(operations.get(3), SingleReferenceOperation.class);

		// due to refactoring one additional operation

		// please note: 3 ops are necessary, this is because the oldvalues are necessary for
		// the ops to be reversible! we also need track the index of srefContainer 2 inside its former parent!

		// please note: 3 ops are necessary from this perspective
		// 1. old solution of srefContainer must be tracked
		// 2. old parent of solution 2 must be tracked (the leafsection)
		// 3. solution2 must announce its new srefContainer

		final ModelElementId solutionId = ModelUtil.getProject(solution).getModelElementId(solution);
		final ModelElementId srefContainerId = ModelUtil.getProject(issue).getModelElementId(issue);
		final ModelElementId leafSectionId = ModelUtil.getProject(leafSection).getModelElementId(leafSection);

		// leaf section announces loss of solution at index 0
		assertEquals(TestElementFeatures.containedElements().getName(), refOp0.getFeatureName());
		assertEquals(leafSectionId, refOp0.getModelElementId());
		assertFalse(refOp0.isAdd());
		assertEquals(1, refOp0.getReferencedModelElements().size());
		assertEquals(refOp0.getReferencedModelElements().get(0), solutionId);

		assertEquals(srefContainerId, refOp1.getModelElementId());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp1.getFeatureName());
		assertEquals(solutionId, refOp1.getNewValue());
		assertNull(null, refOp1.getOldValue());

		assertEquals(solutionId, refOp2.getModelElementId());
		assertEquals(TestElementFeatures.container().getName(), refOp2.getFeatureName());
		assertNull(refOp2.getNewValue());
		assertEquals(leafSectionId, refOp2.getOldValue());

		assertEquals(solutionId, refOp3.getModelElementId());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp3.getFeatureName());
		assertNull(refOp3.getOldValue());
		assertEquals(srefContainerId, refOp3.getNewValue());

	}

	/**
	 * Change an containment attribute from some reference to null, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentValueToNullOperateOnParent() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement solution = Create.testElement();

		assertEquals(issue.getContainedElement(), null);

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), solution);

		Update.testElement(TestElementFeatures.containedElement(), issue, solution);
		clearOperations();

		assertSame(solution, issue.getContainedElement());
		final ModelElementId solutionId = ModelUtil.getProject(solution).getModelElementId(solution);

		Update.testElement(TestElementFeatures.containedElement(), issue, null);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		final List<ReferenceOperation> subOperations = checkAndCast(
			operations.get(0), CreateDeleteOperation.class).getSubOperations();

		assertEquals(2, subOperations.size());

		final ModelElementId srefContainerId = ModelUtil.getProject(issue).getModelElementId(issue);
		final SingleReferenceOperation refOp0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);

		assertEquals(srefContainerId, refOp0.getOldValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp0.getFeatureName());
		assertEquals(solutionId, refOp0.getModelElementId());
		assertNull(refOp0.getNewValue());

		assertEquals(solutionId, refOp1.getOldValue());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp1.getFeatureName());
		assertEquals(srefContainerId, refOp1.getModelElementId());
		assertNull(refOp1.getNewValue());
	}

	/**
	 * Change an containment attribute from some reference to null, and check resulting op.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void containmentValueToNullOperateOnChild() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement issue = Create.testElement();
		final TestElement solution = Create.testElement();

		assertEquals(issue.getContainedElement(), null);

		Add.toProject(getLocalProject(), issue);
		Add.toProject(getLocalProject(), solution);

		Update.testElement(TestElementFeatures.containedElement(), issue, solution);

		clearOperations();

		assertSame(solution, issue.getContainedElement());
		final ModelElementId solutionId = ModelUtil.getProject(solution).getModelElementId(solution);

		Update.testElement(TestElementFeatures.srefContainer(), solution, null);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		final List<ReferenceOperation> subOperations = checkAndCast(operations.get(0), CreateDeleteOperation.class)
			.getSubOperations();
		assertEquals(2, subOperations.size());

		final ModelElementId srefContainerId = getProject().getModelElementId(issue);

		final SingleReferenceOperation refOp0 = checkAndCast(subOperations.get(0), SingleReferenceOperation.class);
		final SingleReferenceOperation refOp1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);

		assertEquals(solutionId, refOp0.getOldValue());
		assertEquals(TestElementFeatures.containedElement().getName(), refOp0.getFeatureName());
		assertEquals(srefContainerId, refOp0.getModelElementId());
		assertNull(refOp0.getNewValue());

		assertEquals(srefContainerId, refOp1.getOldValue());
		assertEquals(TestElementFeatures.srefContainer().getName(), refOp1.getFeatureName());
		assertEquals(solutionId, refOp1.getModelElementId());
		assertNull(refOp1.getNewValue());
	}
}