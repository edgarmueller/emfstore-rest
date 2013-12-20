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

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.OperationsFactory;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests for MultiAttributes.
 * 
 * @author wesendon
 */
public class MultiAttributeTest extends ESTest {

	private static final String INSERTED = "inserted"; //$NON-NLS-1$
	private static final String INSERTED2 = "inserted2"; //$NON-NLS-1$
	private static final String STRINGS = "strings"; //$NON-NLS-1$
	private static final String THIRD = "third"; //$NON-NLS-1$
	private static final String SECOND = "second"; //$NON-NLS-1$
	private static final String FIRST = "first"; //$NON-NLS-1$

	protected TestElement testElement;

	/**
	 * Add value to empty list.
	 */
	@Test
	public void addValueToEmptyTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				final TestElement testElement = Create.testElement();
				ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
				assertTrue(testElement.getStrings().size() == 0);

				final MultiAttributeOperation operation = OperationsFactory.eINSTANCE.createMultiAttributeOperation();
				operation.setAdd(true);
				operation.setFeatureName(STRINGS);
				operation.getIndexes().add(0);
				operation.getReferencedValues().add(INSERTED);
				operation.setModelElementId(ModelUtil.getProject(testElement).getModelElementId(testElement));

				operation.apply(getProject());

				assertTrue(testElement.getStrings().size() == 1);
				assertTrue(testElement.getStrings().get(0).equals(INSERTED));
			}
		}.run(false);
	}

	/**
	 * Add value to filled list.
	 */
	@Test
	public void addValueToFilledTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				final TestElement testElement = Create.testElement();
				ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
				testElement.getStrings().add(FIRST);

				assertTrue(testElement.getStrings().size() == 1);

				final MultiAttributeOperation operation = OperationsFactory.eINSTANCE.createMultiAttributeOperation();
				operation.setAdd(true);
				operation.setFeatureName(STRINGS);
				operation.getIndexes().add(0);
				operation.getReferencedValues().add(INSERTED);
				operation.setModelElementId(ModelUtil.getProject(testElement).getModelElementId(testElement));

				operation.apply(getProject());

				assertTrue(testElement.getStrings().size() == 2);
				assertTrue(testElement.getStrings().get(0).equals(INSERTED));
				assertTrue(testElement.getStrings().get(1).equals(FIRST));
			}
		}.run(false);
	}

	/**
	 * Add multiple values.
	 */
	@Test
	public void addMultipleValueToFilledTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				final TestElement testElement = Create.testElement();
				ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
				testElement.getStrings().add(FIRST);

				assertTrue(testElement.getStrings().size() == 1);

				final MultiAttributeOperation operation = OperationsFactory.eINSTANCE.createMultiAttributeOperation();
				operation.setAdd(true);
				operation.setFeatureName(STRINGS);
				operation.getIndexes().add(0);
				operation.getIndexes().add(2);
				operation.getReferencedValues().add(INSERTED);
				operation.getReferencedValues().add(INSERTED2);
				operation.setModelElementId(ModelUtil.getProject(testElement).getModelElementId(testElement));

				operation.apply(getProject());

				assertTrue(testElement.getStrings().size() == 3);
				assertTrue(testElement.getStrings().get(0).equals(INSERTED));
				assertTrue(testElement.getStrings().get(1).equals(FIRST));
				assertTrue(testElement.getStrings().get(2).equals(INSERTED2));
			}
		}.run(false);
	}

	/**
	 * Remove last value.
	 */
	@Test
	public void removeValueToEmptyTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				final TestElement testElement = Create.testElement();
				ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
				testElement.getStrings().add(FIRST);

				assertTrue(testElement.getStrings().size() == 1);

				final MultiAttributeOperation operation = OperationsFactory.eINSTANCE.createMultiAttributeOperation();
				operation.setAdd(false);
				operation.setFeatureName(STRINGS);
				operation.getIndexes().add(0);
				operation.getReferencedValues().add(FIRST);
				operation.setModelElementId(ModelUtil.getProject(testElement).getModelElementId(testElement));

				operation.apply(getProject());

				assertTrue(testElement.getStrings().size() == 0);
			}
		}.run(false);
	}

	/**
	 * Test recorded operation.
	 */
	@Test
	public void recordedAddOperationsTest() {
		testElement = Create.testElement();
		ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();
				testElement.getStrings().add(FIRST);
				testElement.getStrings().addAll(Arrays.asList(SECOND, THIRD));
			}
		}.run(false);

		AbstractOperation abstractOperation = getProjectSpace().getOperations().get(0);
		assertTrue(abstractOperation instanceof MultiAttributeOperation);
		MultiAttributeOperation ao = (MultiAttributeOperation) abstractOperation;
		assertTrue(ao.getIndexes().size() == 1);
		assertTrue(ao.getIndexes().get(0) == 0);
		assertTrue(ao.getReferencedValues().get(0).equals(FIRST));
		assertTrue(ao.isAdd());

		abstractOperation = getProjectSpace().getOperations().get(1);
		assertTrue(abstractOperation instanceof MultiAttributeOperation);
		ao = (MultiAttributeOperation) abstractOperation;
		assertTrue(ao.getIndexes().size() == 2);
		assertTrue(ao.getIndexes().get(0) == 1);
		assertTrue(ao.getIndexes().get(1) == 2);
		assertTrue(ao.getReferencedValues().get(0).equals(SECOND));
		assertTrue(ao.getReferencedValues().get(1).equals(THIRD));
		assertTrue(ao.isAdd());

	}

	/**
	 * Test recorded remove operation.
	 */
	@Test
	public void recordedRemoveOperationsTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement = Create.testElement();
				ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
				testElement.getStrings().add(FIRST);
				testElement.getStrings().addAll(Arrays.asList(SECOND, THIRD));

				clearOperations();
				testElement.getStrings().removeAll(Arrays.asList(SECOND, THIRD));
			}
		}.run(false);

		assertTrue(getProjectSpace().getOperations().size() == 1);
		final AbstractOperation abstractOperation = getProjectSpace().getOperations().get(0);
		assertTrue(abstractOperation instanceof MultiAttributeOperation);
		final MultiAttributeOperation ao = (MultiAttributeOperation) abstractOperation;
		assertTrue(ao.getIndexes().get(0) == 1);
		assertTrue(ao.getIndexes().get(1) == 2);
		assertTrue(ao.getReferencedValues().get(0).equals(SECOND));
		assertTrue(ao.getReferencedValues().get(1).equals(THIRD));
		assertTrue(!ao.isAdd());
	}

	/**
	 * Remove and reverse operation.
	 */
	@Test
	public void removeAndReverseTest() {
		testElement = Create.testElement();
		ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().add(FIRST);
				testElement.getStrings().add(SECOND);

				assertTrue(testElement.getStrings().size() == 2);
				assertTrue(testElement.getStrings().get(0).equals(FIRST));
				assertTrue(testElement.getStrings().get(1).equals(SECOND));
				clearOperations();
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().remove(SECOND);
				assertTrue(testElement.getStrings().size() == 1);
				assertTrue(testElement.getStrings().get(0).equals(FIRST));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				final AbstractOperation ao = getProjectSpace().getOperations().get(0).reverse();
				ao.apply(getProject());
			}
		}.run(false);

		assertTrue(testElement.getStrings().size() == 2);
		assertTrue(testElement.getStrings().get(0).equals(FIRST));
		assertTrue(testElement.getStrings().get(1).equals(SECOND));
	}
}
