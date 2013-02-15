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
package org.eclipse.emf.emfstore.client.test.integration.reversibility;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.test.integration.forward.IntegrationTestHelper;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.SerializationException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Test;

/**
 * @author Hodaie
 */

public class CreateDeleteOperationsReversibilityTest extends OperationsReversibilityTest {

	private long randomSeed = 1;

	/**
	 * create a random ME and change one of its attributes.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void createAndChangeAttributeReversibilityTest() throws SerializationException, ESException {
		System.out.println("CreateAndChangeAttributeReversibilityTest");

		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testHelper.doCreateAndChangeAttribute();
				getTestProjectSpace().revert();
			}

		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getTestProjectSpace().revert();
			}

		}.run(false);

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * Create a random ME and change one of its references.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void createAndChangeRefReversibilityTest() throws SerializationException, ESException {
		System.out.println("CreateAndChangeRefReversibilityTest");

		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {

				testHelper.doCreateAndChangeRef();
				getTestProjectSpace().revert();
			}

		}.run(false);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getTestProjectSpace().revert();
			}

		}.run(false);

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * Create a random ME. Change one of its non-containment references. Delete ME.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void createChangeRefDeleteReversibilityTest() throws SerializationException, ESException {
		System.out.println("CreateChangeRefDeleteReversibilityTest");
		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doCreateChangeRefDelete();
				getTestProjectSpace().revert();
			}

		}.run(false);

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * Create a random ME. Delete ME.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void createDeleteReversibilityTest() throws SerializationException, ESException {
		System.out.println("CreateDeleteReversibilityTest");
		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doCreateDelete();
				getTestProjectSpace().revert();
			}

		}.run(false);

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

}