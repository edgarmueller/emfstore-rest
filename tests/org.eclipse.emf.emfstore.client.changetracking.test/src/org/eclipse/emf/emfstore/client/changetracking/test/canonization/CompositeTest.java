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
package org.eclipse.emf.emfstore.client.changetracking.test.canonization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.internal.client.model.CompositeOperationHandle;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.InvalidHandleException;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.util.OperationsCanonizer;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests canonization of composite operations.
 * 
 * @author chodnick
 */
public class CompositeTest extends ESTest {

	private static final String DESCRIPTION2 = "description"; //$NON-NLS-1$
	private static final String SECTION_CREATION = "sectionCreation"; //$NON-NLS-1$
	private static final String NEW_NAME = "newName"; //$NON-NLS-1$
	private static final String BLIBB = "blibb"; //$NON-NLS-1$
	private static final String BLUBB = "blubb"; //$NON-NLS-1$
	private static final String OLD_NAME = "oldName"; //$NON-NLS-1$
	private static final String OLD_DESCRIPTION = "oldDescription"; //$NON-NLS-1$
	private static final String C = "C"; //$NON-NLS-1$
	private static final String Y = "Y"; //$NON-NLS-1$
	private static final String B = "B"; //$NON-NLS-1$
	private static final String X = "X"; //$NON-NLS-1$
	private static final String A = "A"; //$NON-NLS-1$

	/**
	 * Tests canonization of empty composite operations.
	 * 
	 * @throws InvalidHandleException if an error occurrs
	 */
	@Test
	public void emptyComposite() throws InvalidHandleException {

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				// create an empty composite, should be canonized out
				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();
				try {
					handle.end(SECTION_CREATION, DESCRIPTION2, null);
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(operations.size(), 1);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		assertEquals(operations.size(), 0);

	}

	/**
	 * Tests canonization for consecutive attribute changes, resulting in a noop.
	 * 
	 * @throws InvalidHandleException if error occurs
	 */
	@Test
	public void noOpComposite() throws InvalidHandleException {

		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(OLD_NAME);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();

				useCase.setName(A);
				useCase.setName(B);
				useCase.setName(C);
				useCase.setName(OLD_NAME);

				assertEquals(OLD_NAME, useCase.getName());

				assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

				try {
					handle.end(BLUBB, BLIBB, null);
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(operations.size(), 1);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// should not have left any operations, we were just resetting the name to its original value
		assertEquals(operations.size(), 0);

	}

	/**
	 * Tests canonization for consecutive attribute changes, resulting in a noop.
	 * 
	 * @throws InvalidHandleException if error occurs
	 */
	@Test
	public void multiFeatureNoOpComposite() throws InvalidHandleException {

		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(OLD_NAME);
				useCase.setDescription(OLD_DESCRIPTION);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();

				useCase.setName(A);
				useCase.setDescription(X);
				useCase.setName(B);
				useCase.setDescription(Y);
				useCase.setName(C);

				useCase.setDescription(OLD_DESCRIPTION);
				useCase.setName(OLD_NAME);

				assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

				try {
					handle.end(BLUBB, BLIBB, null);
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(operations.size(), 1);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// should not have left any operations, we were just resetting the name to its original value
		assertEquals(operations.size(), 0);

	}

	/**
	 * Tests canonization for composite ops, where main operation might be canonized away.
	 * 
	 * @throws InvalidHandleException if error occurs
	 */
	@Test
	public void mainDeleteCompositeImplicitRestore() throws InvalidHandleException {

		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(OLD_NAME);
				clearOperations();

				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();

				useCase.setName(A);
				useCase.setName(B);
				useCase.setName(NEW_NAME);

				try {
					handle.end(BLUBB, BLIBB, null);
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(operations.size(), 1);
		final CompositeOperation comp = (CompositeOperation) operations.get(0);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				comp.setMainOperation(comp.getSubOperations().get(1)); // setName to from "A" to "B"
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// the main one was a candidate for removal, but since it is the main one, it may not be touched
		// in this case it will not even be modified
		assertTrue(comp.getSubOperations().contains(comp.getMainOperation()));
	}

	/**
	 * Tests canonization for composite ops, where main operation might be canonized away.
	 * 
	 * @throws InvalidHandleException if error occurs
	 */
	@Test
	public void mainDeleteCompositeImplicitMainOpModification() throws InvalidHandleException {

		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(OLD_NAME);

				clearOperations();

				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();

				useCase.setName(A);
				useCase.setName(B);
				useCase.setName(NEW_NAME);

				try {
					handle.end(BLUBB, BLIBB, null);
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(operations.size(), 1);
		final CompositeOperation comp = (CompositeOperation) operations.get(0);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				comp.setMainOperation(comp.getSubOperations().get(0)); // setName to from "oldName" to "A"
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// the main one was a candidate for removal, but since it is the main one, it may not be removed
		// it might have been altered though (newValue, oldValue etc., might have changed in the canonization
		// process)
		assertTrue(comp.getSubOperations().contains(comp.getMainOperation()));
	}

	/**
	 * Tests canonization for composite ops, where main operation might be canonized away.
	 * 
	 * @throws InvalidHandleException if error occurs
	 */
	@Test
	public void mainDeleteNoOpComposite() throws InvalidHandleException {

		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(OLD_NAME);

				clearOperations();

				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();

				useCase.setName(A);
				useCase.setName(B);
				useCase.setName(OLD_NAME);

				try {
					handle.end(BLUBB, BLIBB, null);
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(operations.size(), 1);
		final CompositeOperation comp = (CompositeOperation) operations.get(0);
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				comp.setMainOperation(comp.getSubOperations().get(1)); // setName to from "A" to "B"
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);
		// since this composite is a noop, everything should have been removed
		assertEquals(comp.getSubOperations().size(), 0);
	}

}
