/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.recording.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.client.test.common.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Update;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AttributeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.UnsetType;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.util.OperationsCanonizer;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests the Attribute Operation.
 * 
 * @author koegel
 */
public class AttributeOperationTest extends ESTest {

	private static final String FAN = "Fan"; //$NON-NLS-1$
	private static final String OLD_NAME = "oldName"; //$NON-NLS-1$
	private static final String OTHER_NAME = "otherName"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String NEW_NAME = "newName"; //$NON-NLS-1$

	/**
	 * Change an attribute and check the generated operation.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void changeAttribute() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement testElement = Create.testElement();
		ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
		ProjectUtil.clearOperations(getProjectSpace().toAPI());
		Update.testElement(TestElementFeatures.name(), testElement, NEW_NAME);
		assertEquals(NEW_NAME, testElement.getName());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof AttributeOperation);
		final AttributeOperation attributeOperation = (AttributeOperation) operation;

		assertNull(attributeOperation.getOldValue());
		assertEquals(NEW_NAME, attributeOperation.getNewValue());
		assertEquals(NAME, attributeOperation.getFeatureName());

		final ModelElementId testElementId = ModelUtil.getProject(testElement).getModelElementId(testElement);

		assertEquals(testElementId, attributeOperation.getModelElementId());

	}

	/**
	 * Change an attribute twice and check the generated operations after cannonization.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void changeAttributeTwice() throws UnsupportedOperationException, UnsupportedNotificationException {
		final TestElement testElement = Create.testElement();
		ProjectUtil.addElement(getProjectSpace().toAPI(), testElement);
		ProjectUtil.clearOperations(getProjectSpace().toAPI());
		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				testElement.setName(NEW_NAME);
			}
		});

		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				testElement.setName(OTHER_NAME);
				assertEquals(OTHER_NAME, testElement.getName());
			}
		});

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				OperationsCanonizer.canonize(operations);
			}
		});

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof AttributeOperation);
		final AttributeOperation attributeOperation = (AttributeOperation) operation;

		assertNull(attributeOperation.getOldValue());
		assertEquals(OTHER_NAME, attributeOperation.getNewValue());
		assertEquals(NAME, attributeOperation.getFeatureName());

		final ModelElementId testElementId = ModelUtil.getProject(testElement).getModelElementId(testElement);

		assertEquals(testElementId, attributeOperation.getModelElementId());
	}

	/**
	 * Change an attribute and reverse the operation and check the result.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void changeAttributeAndReverse() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement testElement = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName(OLD_NAME);

				clearOperations();

				testElement.setName(NEW_NAME);
				assertEquals(NEW_NAME, testElement.getName());
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof AttributeOperation);
		final AttributeOperation attributeOperation = (AttributeOperation) operation;

		assertEquals(OLD_NAME, attributeOperation.getOldValue());
		assertEquals(NEW_NAME, attributeOperation.getNewValue());
		assertEquals(NAME, attributeOperation.getFeatureName());

		final ModelElementId testElementId = ModelUtil.getProject(testElement).getModelElementId(testElement);

		assertEquals(testElementId, attributeOperation.getModelElementId());

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				final AbstractOperation reverse = operation.reverse();
				reverse.apply(getProject());
				assertEquals(true, reverse instanceof AttributeOperation);
				final AttributeOperation reversedAttributeOperation = (AttributeOperation) reverse;
				assertEquals(NEW_NAME, reversedAttributeOperation.getOldValue());
				assertEquals(OLD_NAME, reversedAttributeOperation.getNewValue());
				assertEquals(NAME, reversedAttributeOperation.getFeatureName());
				final ModelElementId testElementId = ModelUtil.getProject(testElement).getModelElementId(testElement);
				assertEquals(testElementId, reversedAttributeOperation.getModelElementId());
			}
		}.run(false);

		assertEquals(OLD_NAME, testElement.getName());
	}

	@Test
	public void changeAttributeDoubleReversal() throws IOException {

		final TestElement testElement = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName(OLD_NAME);

				clearOperations();

				testElement.setName(NEW_NAME);
				assertEquals(NEW_NAME, testElement.getName());
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);

		assertEquals(true, operation instanceof AttributeOperation);
		final AttributeOperation attributeOperation = (AttributeOperation) operation;

		final AttributeOperation cmpOperation = (AttributeOperation) attributeOperation.reverse().reverse();

		assertEquals(attributeOperation.getFeatureName(), cmpOperation.getFeatureName());
		assertEquals(attributeOperation.getModelElementId(), cmpOperation.getModelElementId());
		assertEquals(attributeOperation.getNewValue(), cmpOperation.getNewValue());
		assertEquals(attributeOperation.getOldValue(), cmpOperation.getOldValue());

		final Project expectedProject = ModelUtil.clone(getProject());

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		final AbstractOperation r = attributeOperation.reverse();
		final AbstractOperation rr = r.reverse();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				r.apply(getProject());
				rr.apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				attributeOperation.reverse().apply(getProject());
				attributeOperation.reverse().reverse().apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
		getProjectSpace().save();

		final Project loadedProject = ModelUtil.loadEObjectFromResource(
			org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE.getModelPackage().getProject(),
			getProject().eResource().getURI(), false);

		// String eObjectToString = ModelUtil.eObjectToString(loadedProject);
		// String eObjectToString2 = ModelUtil.eObjectToString(expectedProject);

		assertTrue(ModelUtil.areEqual(loadedProject, expectedProject));
	}

	@Test
	public void unsetAttribute() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				fan.setName(FAN);
				clearOperations();
			}
		}.run(false);

		final Project secondProject = ModelUtil.clone(getProject());

		// Test unsetting name
		assertEquals(true, fan.isSetName());
		assertEquals(FAN, fan.getName());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetName();
			}
		}.run(false);

		assertEquals(false, fan.isSetName());
		assertEquals(null, fan.getName());

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof AttributeOperation);
		AttributeOperation attributeOperation = (AttributeOperation) operation;

		assertEquals(FAN, attributeOperation.getOldValue());
		assertEquals(null, attributeOperation.getNewValue());
		assertEquals(NAME, attributeOperation.getFeatureName());
		assertEquals(true, attributeOperation.getUnset() == UnsetType.IS_UNSET);

		attributeOperation.apply(secondProject);
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));

		// test setting name to default value
		clearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.setName(null);
			}
		}.run(false);

		assertEquals(true, fan.isSetName());
		assertEquals(null, fan.getName());

		operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		operation = operations.get(0);
		assertEquals(true, operation instanceof AttributeOperation);
		attributeOperation = (AttributeOperation) operation;

		assertEquals(null, attributeOperation.getOldValue());
		assertEquals(null, attributeOperation.getNewValue());
		assertEquals(NAME, attributeOperation.getFeatureName());
		assertEquals(false, attributeOperation.getUnset() == UnsetType.IS_UNSET);
	}

	@Test
	public void unsetAttributeReverse() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				fan.setName(FAN);
				clearOperations();
			}
		}.run(false);

		assertEquals(FAN, fan.getName());
		assertEquals(true, fan.isSetName());

		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetName();
			}
		}.run(false);

		assertEquals(false, fan.isSetName());
		assertEquals(null, fan.getName());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof AttributeOperation);
		final AttributeOperation attributeOperation = (AttributeOperation) operation;

		assertEquals(FAN, attributeOperation.getOldValue());
		assertEquals(null, attributeOperation.getNewValue());
		assertEquals(NAME, attributeOperation.getFeatureName());
		assertEquals(true, attributeOperation.getUnset() == UnsetType.IS_UNSET);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				attributeOperation.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(FAN, fan.getName());
		assertEquals(true, fan.isSetName());

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void unsetAttributeDoubleReverse() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				fan.setName(FAN);
				clearOperations();
			}
		}.run(false);

		assertEquals(FAN, fan.getName());
		assertEquals(true, fan.isSetName());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetName();
			}
		}.run(false);

		final Project secondProject = ModelUtil.clone(getProject());

		assertEquals(false, fan.isSetName());
		assertEquals(null, fan.getName());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof AttributeOperation);
		final AttributeOperation attributeOperation = (AttributeOperation) operation;

		assertEquals(FAN, attributeOperation.getOldValue());
		assertEquals(null, attributeOperation.getNewValue());
		assertEquals(NAME, attributeOperation.getFeatureName());
		assertEquals(true, attributeOperation.getUnset() == UnsetType.IS_UNSET);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				attributeOperation.reverse().reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(null, fan.getName());
		assertEquals(false, fan.isSetName());

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void setOfUnsettedAttributeReverse() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				clearOperations();
			}
		}.run(false);

		assertEquals(null, fan.getName());
		assertEquals(false, fan.isSetName());

		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.setName(FAN);
			}
		}.run(false);

		assertEquals(true, fan.isSetName());
		assertEquals(FAN, fan.getName());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof AttributeOperation);
		final AttributeOperation attributeOperation = (AttributeOperation) operation;

		assertEquals(null, attributeOperation.getOldValue());
		assertEquals(FAN, attributeOperation.getNewValue());
		assertEquals(NAME, attributeOperation.getFeatureName());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				attributeOperation.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(null, fan.getName());
		assertEquals(false, fan.isSetName());

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}
}
