/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.conflictDetection;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithParameter;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeMoveOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeSetOperation;
import org.junit.Test;

/**
 * Conflicttests for MultiAttribute, -Set and -Move operations.
 * 
 * @author wesendon
 */
public class ConflictDetectionMultiAttributeTest extends ConflictDetectionTest {

	/**
	 * Remove vs add.
	 */
	@Test
	public void multiAttRemoveVsAdd() {

		TestElement testElement = new EMFStoreCommandWithResult<TestElement>() {
			@Override
			protected TestElement doRun() {
				TestElement testElement = createFilledTestElement(3);
				clearOperations();
				testElement.getStrings().remove(0);
				return testElement;
			}
		}.run(false);

		AbstractOperation removeOp = new EMFStoreCommandWithResult<AbstractOperation>() {

			@Override
			protected AbstractOperation doRun() {
				AbstractOperation removeOp = checkAndGetOperation(MultiAttributeOperation.class);
				return removeOp;
			}
		}.run(false);

		new EMFStoreCommandWithParameter<TestElement>() {
			@Override
			public void doRun(TestElement testElement) {
				testElement.getStrings().add(1, "inserted");
			}
		}.run(testElement, false);

		AbstractOperation addOp = new EMFStoreCommandWithResult<AbstractOperation>() {

			@Override
			protected AbstractOperation doRun() {
				AbstractOperation addOp = checkAndGetOperation(MultiAttributeOperation.class);
				return addOp;
			}
		}.run(false);

		assertEquals(true, doConflict(removeOp, addOp));
		assertEquals(true, doConflict(addOp, removeOp));
	}

	/**
	 * Add vs add.
	 */
	@Test
	public void multiAttAddVsAdd() {

		final TestElement testElement = createFilledTestElement(3);
		myClearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().add(0, "inserted1");
			}
		}.run(false);

		AbstractOperation add1 = myCheckAndGetOperation(MultiAttributeOperation.class);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testElement.getStrings().add(1, "inserted2");
			}
		}.run(false);

		AbstractOperation add2 = myCheckAndGetOperation(MultiAttributeOperation.class);

		assertEquals(true, doConflict(add1, add2));
		assertEquals(true, doConflict(add2, add1));
	}

	/**
	 * remove vs remove.
	 */
	@Test
	public void multiAttRemoveVsRemove() {

		final TestElement testElement = createFilledTestElement(3);
		myClearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().remove(2);
			}
		}.run(false);

		AbstractOperation remove1 = myCheckAndGetOperation(MultiAttributeOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().remove(1);
			}
		}.run(false);

		AbstractOperation remove2 = myCheckAndGetOperation(MultiAttributeOperation.class);

		assertEquals(true, doConflict(remove1, remove2));
		assertEquals(true, doConflict(remove2, remove1));
	}

	/**
	 * Move vs add.
	 */
	@Test
	public void multiAttMoveVsAdd() {

		final TestElement testElement = createFilledTestElement(3);
		myClearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().add(0, "inserted");
			}
		}.run(false);

		AbstractOperation add = myCheckAndGetOperation(MultiAttributeOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().move(1, 2);
			}
		}.run(false);

		AbstractOperation move = myCheckAndGetOperation(MultiAttributeMoveOperation.class);

		assertEquals(true, doConflict(add, move));
		assertEquals(true, doConflict(move, add));
	}

	/**
	 * Move vs remove.
	 */
	@Test
	public void multiAttMoveVsRemove() {

		final TestElement testElement = createFilledTestElement(3);
		myClearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().move(2, 0);
			}
		}.run(false);

		AbstractOperation move = myCheckAndGetOperation(MultiAttributeMoveOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().remove(0);
			}
		}.run(false);

		AbstractOperation add = myCheckAndGetOperation(MultiAttributeOperation.class);

		assertEquals(true, doConflict(add, move));
		assertEquals(true, doConflict(move, add));
	}

	/**
	 * Move vs move - conflict.
	 */
	// Move vs move is a soft conflict
	// @Test
	// public void multiAttMoveVsMoveConflict() {
	// new EMFStoreCommand() {
	// @Override
	// protected void doRun() {
	// TestElement testElement = getFilledTestElement(3);
	// clearOperations();
	//
	// testElement.getStrings().move(1, 2);
	// AbstractOperation move1 = checkAndGetOperation(MultiAttributeMoveOperation.class);
	//
	// testElement.getStrings().move(1, 0);
	// AbstractOperation move2 = checkAndGetOperation(MultiAttributeMoveOperation.class);
	//
	// assertEquals(true, doConflict(move2, move1));
	// assertEquals(true, doConflict(move1, move2));
	// }
	// }.run(false);
	// }

	/**
	 * Move vs move - no conflict.
	 */
	@Test
	public void multiAttMoveVsMoveNoConflict() {

		final TestElement testElement = createFilledTestElement(4);
		myClearOperations();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testElement.getStrings().move(0, 1);
			}
		}.run(false);

		AbstractOperation move1 = myCheckAndGetOperation(MultiAttributeMoveOperation.class);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testElement.getStrings().move(2, 3);
			}
		}.run(false);

		AbstractOperation move2 = myCheckAndGetOperation(MultiAttributeMoveOperation.class);

		assertEquals(false, doConflict(move2, move1));
		assertEquals(false, doConflict(move1, move2));
	}

	/**
	 * Set vs add - no conflict.
	 */
	@Test
	public void multiAttSetVsAddNoConflict() {
		final TestElement testElement = createFilledTestElement(4);
		myClearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().set(0, "set");
			}
		}.run(false);

		AbstractOperation set = myCheckAndGetOperation(MultiAttributeSetOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().add(1, "added");
			}
		}.run(false);

		AbstractOperation add = myCheckAndGetOperation(MultiAttributeOperation.class);

		assertEquals(false, doConflict(set, add));
		assertEquals(false, doConflict(add, set));
	}

	/**
	 * Set vs add - conflict.
	 */
	@Test
	public void multiAttSetVsAddConflict() {

		final TestElement testElement = createFilledTestElement(4);
		myClearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().set(1, "set");
			}
		}.run(false);

		AbstractOperation set = myCheckAndGetOperation(MultiAttributeSetOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().add(0, "added");
			}
		}.run(false);

		AbstractOperation add = myCheckAndGetOperation(MultiAttributeOperation.class);

		assertEquals(true, doConflict(set, add));
		assertEquals(true, doConflict(add, set));
	}

	/**
	 * Set vs remove - no conflict.
	 */
	@Test
	public void multiAttSetVsRemoveNoConflict() {

		final TestElement testElement = createFilledTestElement(4);
		myClearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().set(0, "set");
			}
		}.run(false);

		AbstractOperation set = myCheckAndGetOperation(MultiAttributeSetOperation.class);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testElement.getStrings().remove(1);
			}
		}.run(false);

		AbstractOperation remove = myCheckAndGetOperation(MultiAttributeOperation.class);

		assertEquals(false, doConflict(set, remove));
		assertEquals(false, doConflict(remove, set));
	}

	/**
	 * Set vs remove - conflict.
	 */
	@Test
	public void multiAttSetVsRemoveConflict() {

		final TestElement testElement = createFilledTestElement(4);
		myClearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().set(1, "set");
			}
		}.run(false);

		AbstractOperation set = myCheckAndGetOperation(MultiAttributeSetOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().remove(0);
			}
		}.run(false);

		AbstractOperation remove = myCheckAndGetOperation(MultiAttributeOperation.class);

		assertEquals(true, doConflict(set, remove));
		assertEquals(true, doConflict(remove, set));
	}

	/**
	 * Move vs Set - conflict.
	 */
	@Test
	public void multiAttSetVsMoveConflict() {

		final TestElement testElement = createFilledTestElement(4);
		myClearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().set(1, "set");
			}
		}.run(false);

		AbstractOperation set = myCheckAndGetOperation(MultiAttributeSetOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().move(0, 2);
			}
		}.run(false);

		AbstractOperation move = myCheckAndGetOperation(MultiAttributeMoveOperation.class);

		assertEquals(true, doConflict(set, move));
		assertEquals(true, doConflict(move, set));
	}

	/**
	 * Move vs Set - no conflict.
	 */
	@Test
	public void multiAttSetVsMoveNoConflict() {

		final TestElement testElement = createFilledTestElement(4);
		myClearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().set(1, "set");
			}
		}.run(false);

		AbstractOperation set = myCheckAndGetOperation(MultiAttributeSetOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getStrings().move(2, 3);
			}
		}.run(false);

		AbstractOperation move = myCheckAndGetOperation(MultiAttributeMoveOperation.class);

		assertEquals(false, doConflict(set, move));
		assertEquals(false, doConflict(move, set));
	}
}