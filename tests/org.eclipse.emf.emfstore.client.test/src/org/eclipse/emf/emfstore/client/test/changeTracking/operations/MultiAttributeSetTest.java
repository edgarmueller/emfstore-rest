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
package org.eclipse.emf.emfstore.client.test.changeTracking.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeSetOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.OperationsFactory;
import org.junit.Test;

/**
 * Tests for multiattributeset operations.
 * 
 * @author wesendon
 */
public class MultiAttributeSetTest extends WorkspaceTest {

	protected TestElement element;

	/**
	 * Set value test.
	 */
	@Test
	public void setValueToFilledTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				element = createTestElementWithoutTransaction();
				element.getStrings().add("oldValue");
				clearOperations();
			}
		}.run(false);

		assertTrue(element.getStrings().size() == 1);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				element.getStrings().set(0, "settedValue");
			}
		}.run(false);

		assertTrue(element.getStrings().size() == 1);
		assertTrue(element.getStrings().get(0).equals("settedValue"));

		assertTrue(getProjectSpace().getOperations().size() == 1);
		assertTrue(getProjectSpace().getOperations().get(0) instanceof MultiAttributeSetOperation);
	}

	/**
	 * Apply setoperation to element.
	 */
	@Test
	public void applyValueToFilledTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				TestElement testElement = createTestElementWithoutTransaction();
				testElement.getStrings().add("oldValue");
				assertTrue(testElement.getStrings().size() == 1);

				MultiAttributeSetOperation operation = OperationsFactory.eINSTANCE.createMultiAttributeSetOperation();
				operation.setFeatureName("strings");
				operation.setIndex(0);
				operation.setNewValue("inserted");
				operation.setOldValue("oldValue");
				operation.setModelElementId(ModelUtil.getProject(testElement).getModelElementId(testElement));

				operation.apply(getProject());

				assertTrue(testElement.getStrings().size() == 1);
				assertTrue(testElement.getStrings().get(0).equals("inserted"));
			}
		}.run(false);
	}

	/**
	 * apply setoperation with wrong index.
	 */
	@Test
	public void applyValueToFilledWrongIndexTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				TestElement testElement = createTestElementWithoutTransaction();
				testElement.getStrings().add("oldValue");
				assertTrue(testElement.getStrings().size() == 1);

				MultiAttributeSetOperation operation = OperationsFactory.eINSTANCE.createMultiAttributeSetOperation();
				operation.setFeatureName("strings");
				operation.setIndex(42);
				operation.setNewValue("inserted");
				operation.setOldValue("oldValue");
				operation.setModelElementId(ModelUtil.getProject(testElement).getModelElementId(testElement));

				operation.apply(getProject());

				assertTrue(testElement.getStrings().size() == 1);
				assertTrue(testElement.getStrings().get(0).equals("oldValue"));
			}
		}.run(false);
	}

	/**
	 * Apply to filled list.
	 */
	@Test
	public void applyValueToMultiFilledTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				TestElement testElement = createTestElementWithoutTransaction();
				testElement.getStrings().addAll(Arrays.asList("first", "second", "third"));
				assertTrue(testElement.getStrings().size() == 3);

				MultiAttributeSetOperation operation = OperationsFactory.eINSTANCE.createMultiAttributeSetOperation();
				operation.setFeatureName("strings");
				operation.setIndex(1);
				operation.setNewValue("inserted");
				operation.setOldValue("second");
				operation.setModelElementId(ModelUtil.getProject(testElement).getModelElementId(testElement));

				operation.apply(getProject());

				assertTrue(testElement.getStrings().size() == 3);
				assertTrue(testElement.getStrings().get(0).equals("first"));
				assertTrue(testElement.getStrings().get(1).equals("inserted"));
				assertTrue(testElement.getStrings().get(2).equals("third"));
			}
		}.run(false);
	}

	/**
	 * Set and reverse.
	 */
	@Test
	public void setAndReverseTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				element = createTestElementWithoutTransaction();
				element.getStrings().add("oldValue");

				clearOperations();
			}
		}.run(false);

		assertTrue(element.getStrings().size() == 1);
		assertTrue(element.getStrings().get(0).equals("oldValue"));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				element.getStrings().set(0, "newValue");

				assertTrue(element.getStrings().size() == 1);
				assertTrue(element.getStrings().get(0).equals("newValue"));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				AbstractOperation operation = getProjectSpace().getOperations().get(0).reverse();
				operation.apply(getProject());
			}
		}.run(false);

		assertTrue(element.getStrings().size() == 1);
		assertTrue(element.getStrings().get(0).equals("oldValue"));
	}

	@Test
	public void unsetMultiAttributeTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				fan.getEMails().add("foo@bar1.com");
				fan.getEMails().add("foo@bar2.com");
				fan.getEMails().add("foo@bar3.com");
				assertEquals(3, fan.getEMails().size());
				assertTrue(fan.isSetEMails());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetEMails();
				assertEquals(0, fan.getEMails().size());
				assertTrue(!fan.isSetEMails());
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(2, operations.size());

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiAttributeOperation);
		final MultiAttributeOperation multAttOp = (MultiAttributeOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiAttributeSetOperation);
		final MultiAttributeSetOperation multAttSetOp = (MultiAttributeSetOperation) operation2;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, multAttOp.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				multAttOp.apply(secondProject);
				multAttSetOp.apply(secondProject);
			}
		}.run(false);
		assertEquals(0, ((Fan) secondProject.getModelElements().get(0)).getEMails().size());
		assertTrue(!((Fan) secondProject.getModelElements().get(0)).isSetEMails());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));

	}

	@Test
	public void reverseUnsetMultiAttributeTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				fan.getEMails().add("foo@bar1.com");
				fan.getEMails().add("foo@bar2.com");
				fan.getEMails().add("foo@bar3.com");
				assertEquals(3, fan.getEMails().size());
				assertTrue(fan.isSetEMails());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetEMails();
				assertEquals(0, fan.getEMails().size());
				assertTrue(!fan.isSetEMails());
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(2, operations.size());

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiAttributeOperation);
		final MultiAttributeOperation multAttOp = (MultiAttributeOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiAttributeSetOperation);
		final MultiAttributeSetOperation multAttSetOp = (MultiAttributeSetOperation) operation2;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, multAttOp.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				multAttSetOp.reverse().apply(getProject());
				multAttOp.reverse().apply(getProject());

			}
		}.run(false);

		assertEquals(3, fan.getEMails().size());
		assertTrue(fan.isSetEMails());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void doubleReverseUnsetMultiAttributeTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				fan.getEMails().add("foo@bar1.com");
				fan.getEMails().add("foo@bar2.com");
				fan.getEMails().add("foo@bar3.com");
				assertEquals(3, fan.getEMails().size());
				assertTrue(fan.isSetEMails());
			}
		}.run(false);

		clearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.unsetEMails();
				assertEquals(0, fan.getEMails().size());
				assertTrue(!fan.isSetEMails());
			}
		}.run(false);

		final Project secondProject = ModelUtil.clone(getProject());

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(2, operations.size());

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiAttributeOperation);
		final MultiAttributeOperation multAttOp = (MultiAttributeOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiAttributeSetOperation);
		final MultiAttributeSetOperation multAttSetOp = (MultiAttributeSetOperation) operation2;

		ModelElementId fanId = ModelUtil.getProject(fan).getModelElementId(fan);
		assertEquals(fanId, multAttOp.getModelElementId());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				multAttOp.reverse().reverse().apply(getProject());
				multAttSetOp.reverse().reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(0, fan.getEMails().size());
		assertTrue(!fan.isSetEMails());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void reverseSetOfUnsettedMultiAttributeTest() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				assertEquals(0, fan.getEMails().size());
				assertTrue(!fan.isSetEMails());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.getEMails().add("foo@bar1.com");
				fan.getEMails().add("foo@bar2.com");
				fan.getEMails().add("foo@bar3.com");
				assertEquals(3, fan.getEMails().size());
				assertTrue(fan.isSetEMails());
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(3, operations.size());

		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiAttributeOperation);
		final MultiAttributeOperation multAttOp1 = (MultiAttributeOperation) operation;

		AbstractOperation operation2 = operations.get(1);
		assertEquals(true, operation2 instanceof MultiAttributeOperation);
		final MultiAttributeOperation multAttOp2 = (MultiAttributeOperation) operation2;

		AbstractOperation operation3 = operations.get(2);
		assertEquals(true, operation3 instanceof MultiAttributeOperation);
		final MultiAttributeOperation multAttOp3 = (MultiAttributeOperation) operation3;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				multAttOp3.reverse().apply(getProject());
				multAttOp2.reverse().apply(getProject());
				multAttOp1.reverse().apply(getProject());
			}
		}.run(false);

		assertEquals(0, fan.getEMails().size());
		assertTrue(!fan.isSetEMails());
		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}

	@Test
	public void setUnsetMultiAttributeToEmpty() {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(fan);
				assertEquals(0, fan.getEMails().size());
				assertTrue(!fan.isSetEMails());
			}
		}.run(false);

		clearOperations();
		final Project secondProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				fan.getEMails().clear();
				assertEquals(0, fan.getEMails().size());
				assertTrue(fan.isSetEMails());
			}
		}.run(false);

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertEquals(true, operation instanceof MultiAttributeOperation);
		final MultiAttributeOperation multAttOp = (MultiAttributeOperation) operation;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				multAttOp.apply(secondProject);
				assertEquals(0, fan.getEMails().size());
				assertTrue(fan.isSetEMails());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), secondProject));
	}
}
