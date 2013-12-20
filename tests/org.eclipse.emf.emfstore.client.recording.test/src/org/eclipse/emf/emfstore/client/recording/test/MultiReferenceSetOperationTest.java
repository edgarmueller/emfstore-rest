/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.recording.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceSetOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.OperationsFactory;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests for multireferenceset operations.
 * 
 * @author wesendon
 */
public class MultiReferenceSetOperationTest extends ESTest {

	private static final String REFERENCES = "references"; //$NON-NLS-1$

	private TestElement element;
	private TestElement newValue;
	private TestElement oldValue;

	/**
	 * Set reference to filled list.
	 */
	@Test
	public void setValueToFilledTest() {
		element = Create.testElement();
		oldValue = Create.testElement();
		newValue = Create.testElement();

		ProjectUtil.addElement(getProjectSpace().toAPI(), element);
		ProjectUtil.addElement(getProjectSpace().toAPI(), oldValue);
		ProjectUtil.addElement(getProjectSpace().toAPI(), newValue);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				element.getReferences().add(oldValue);
				clearOperations();
			}
		}.run(false);

		assertTrue(element.getReferences().size() == 1);
		assertTrue(element.getReferences().get(0).equals(oldValue));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				element.getReferences().set(0, newValue);
			}
		}.run(false);

		assertEquals(1, element.getReferences().size());
		assertEquals(newValue, element.getReferences().get(0));

		assertEquals(1, getProjectSpace().getOperations().size());
		assertTrue(getProjectSpace().getOperations().get(0) instanceof MultiReferenceSetOperation);
	}

	/**
	 * Apply setoperation.
	 */
	@Test
	public void applyValueToFilledTest() {
		final TestElement testElement = Create.testElement();
		final TestElement oldValue = Create.testElement();
		final TestElement newValue = Create.testElement();
		testElement.getReferences().add(oldValue);

		ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
		ProjectUtil.addElement(getProjectSpace().toAPI(), oldValue);
		ProjectUtil.addElement(getProjectSpace().toAPI(), newValue);

		assertTrue(testElement.getReferences().size() == 1);
		assertTrue(testElement.getReferences().get(0).equals(oldValue));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {

				final MultiReferenceSetOperation operation = OperationsFactory.eINSTANCE
					.createMultiReferenceSetOperation();
				operation.setFeatureName(REFERENCES);
				operation.setIndex(0);
				operation.setNewValue(ModelUtil.getProject(newValue).getModelElementId(newValue));
				operation.setOldValue(ModelUtil.getProject(oldValue).getModelElementId(oldValue));
				operation.setModelElementId(ModelUtil.getProject(testElement).getModelElementId(testElement));

				operation.apply(getProject());

				assertTrue(testElement.getReferences().size() == 1);
				assertTrue(testElement.getReferences().get(0).equals(newValue));
			}
		}.run(false);
	}

	/**
	 * Apply setoperation with wrong index. Note: The set function now operates with mainly the model element ids, the
	 * index is only used for soft conflict detection.
	 */
	@Test
	public void applyValueToFilledWrongIndexTest() {
		final TestElement testElement = Create.testElement();
		final TestElement oldValue = Create.testElement();
		testElement.getReferences().add(oldValue);
		final TestElement newValue = Create.testElement();
		ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
		ProjectUtil.addElement(getProjectSpace().toAPI(), oldValue);
		ProjectUtil.addElement(getProjectSpace().toAPI(), newValue);

		assertTrue(testElement.getReferences().size() == 1);
		assertTrue(testElement.getReferences().get(0).equals(oldValue));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {

				final MultiReferenceSetOperation operation = OperationsFactory.eINSTANCE
					.createMultiReferenceSetOperation();
				operation.setFeatureName(REFERENCES);
				operation.setIndex(42);
				operation.setNewValue(ModelUtil.getProject(newValue).getModelElementId(newValue));
				operation.setOldValue(ModelUtil.getProject(oldValue).getModelElementId(oldValue));
				operation.setModelElementId(ModelUtil.getProject(testElement).getModelElementId(testElement));

				operation.apply(getProject());

				assertTrue(testElement.getReferences().size() == 1);
				assertTrue(testElement.getReferences().get(0).equals(newValue));
			}
		}.run(false);
	}

	/**
	 * Set value to filled list.
	 */
	@Test
	public void applyValueToMultiFilledTest() {
		final TestElement testElement = Create.testElement();
		final TestElement first = Create.testElement();
		final TestElement second = Create.testElement();
		final TestElement third = Create.testElement();
		final TestElement newValue = Create.testElement();

		ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
		ProjectUtil.addElement(getProjectSpace().toAPI(), first);
		ProjectUtil.addElement(getProjectSpace().toAPI(), second);
		ProjectUtil.addElement(getProjectSpace().toAPI(), third);
		ProjectUtil.addElement(getProjectSpace().toAPI(), newValue);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {

				testElement.getReferences().addAll(Arrays.asList(first, second, third));
				assertTrue(testElement.getReferences().size() == 3);

				final MultiReferenceSetOperation operation = OperationsFactory.eINSTANCE
					.createMultiReferenceSetOperation();
				operation.setFeatureName(REFERENCES);
				operation.setIndex(1);
				operation.setNewValue(ModelUtil.getProject(newValue).getModelElementId(newValue));
				operation.setOldValue(ModelUtil.getProject(second).getModelElementId(second));
				operation.setModelElementId(ModelUtil.getProject(testElement).getModelElementId(testElement));
				operation.apply(getProject());
				assertTrue(testElement.getReferences().size() == 3);
				assertTrue(testElement.getReferences().get(0).equals(first));
				assertTrue(testElement.getReferences().get(1).equals(newValue));
				assertTrue(testElement.getReferences().get(2).equals(third));
			}
		}.run(false);
	}

	/**
	 * Set and reverse operation.
	 */
	@Test
	public void setAndReverseTest() {
		element = Create.testElement();
		oldValue = Create.testElement();
		element.getReferences().add(oldValue);
		newValue = Create.testElement();

		ProjectUtil.addElement(getProjectSpace().toAPI(), element);
		ProjectUtil.addElement(getProjectSpace().toAPI(), oldValue);
		ProjectUtil.addElement(getProjectSpace().toAPI(), newValue);
		ProjectUtil.clearOperations(getProjectSpace().toAPI());

		assertTrue(element.getReferences().size() == 1);
		assertTrue(element.getReferences().get(0).equals(oldValue));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				element.getReferences().set(0, newValue);
			}
		}.run(false);

		assertTrue(element.getReferences().size() == 1);
		assertTrue(element.getReferences().get(0).equals(newValue));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				final AbstractOperation operation = getProjectSpace().getOperations().get(0).reverse();
				operation.apply(getProject());
			}
		}.run(false);

		assertTrue(element.getReferences().size() == 1);
		assertTrue(element.getReferences().get(0).equals(oldValue));
	}
}
